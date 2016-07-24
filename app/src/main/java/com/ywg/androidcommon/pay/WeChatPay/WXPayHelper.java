package com.ywg.androidcommon.pay.WeChatPay;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by longf on 2016/5/18.
 */
public class WXPayHelper {
    private IWXAPI msgApi;
    private PayReq req;
    private StringBuilder sbuff;
    private String out_tradNO = "";
    private String WXNotifyUrl = "";
    private String wxmchid = "";//商户id
    private String wxAppId;//appid
    private String wxkey;//密匙
    private String payMoney = "0";
    private String goodsName = "";
    private String goodsDetail = "";
    private Context mContext;
    private Map<String, String> resultunifiedorder;

    public WXPayHelper(Context context, String wxAppId, String wxkey, String wxmchid, String WXNotifyUrl, String payMoney, String goodsName, String goodsDetail, String out_tradNO) {
        mContext = context;
        this.wxAppId = wxAppId;
        this.wxkey = wxkey;
        this.wxmchid = wxmchid;
        this.WXNotifyUrl = WXNotifyUrl;
        this.payMoney = payMoney;
        this.goodsName = goodsName;
        this.goodsDetail = goodsDetail;
        this.out_tradNO = out_tradNO;
        Log.d("TempWXPayHelper", "wxAppId=" + wxAppId + "\nwxkey=" + wxkey + "\nwxmchid=" + wxmchid + "\nWXNotifyUrl=" + WXNotifyUrl + "\npayMoney=" + payMoney + "\ngoodsName=" + goodsName + "\ngoodsDetail=" + goodsDetail + "\nout_tradNO=" + out_tradNO);
    }

    //微信支付
    //{"respcode":1,"respmessage":"加载数据成功","data":{
    // "wxmchid":"1259416401",
    // "wxappid":"wx0a08e66b28913383",
    // "wxkey":"CD219FFB2828A89D2F85B7229D1AEA58",
    // "WXNotifyUrl":"http://192.168.0.7:8099/qijianban/wxnotify.do"}}
    public void pay() {
        msgApi = WXAPIFactory.createWXAPI(mContext, null);
        // 将该app注册到微信
        msgApi.registerApp(wxAppId);
        req = new PayReq();
//        req.appId = WX_APPID;
//        req.partnerId = wxmchid;
        sbuff = new StringBuilder();
        GetPrepayTask task = new GetPrepayTask();
        task.execute();
    }

    private class GetPrepayTask extends AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            //dialog = ProgressDialog.show(getApplicationContext(), getString(R.string.app_tip), getString(R.string.getting_prepayid));
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();


            byte[] buf = Util.httpPost(url, entity);

            String content = new String(buf);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
//            if (dialog != null) {
//                dialog.dismiss();
//            }
            sbuff.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            resultunifiedorder = result;


//            dialgo.dismiss();
            //发起支付0-----------------------------------------------------------------------------
            genPayReq();

        }
    }

    private void genPayReq() {
        req.appId = wxAppId;
        req.partnerId = wxmchid;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
//        req.packageValue = "prepay_id="+resultunifiedorder.get("prepay_id");
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("package", "Sign=WXPay"));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams, wxkey);

        sbuff.append("sign\n" + req.sign + "\n\n");
        Log.e("TempWXPayHelper", "微信pay生成订单返回genPayReq>>" + sbuff.toString());
        //发起支付-----------------------------------------------------------------------------
//        Toast.makeText(mContext, "正在启动微信支付"+sbuff.toString(), Toast.LENGTH_LONG).show();
        msgApi.registerApp(wxAppId);
        msgApi.sendReq(req);

    }

    private String genAppSign(List<NameValuePair> params, String wxkey) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(wxkey);

        sbuff.append("sign str\n" + sb.toString() + "\n\n");
        // 生成签名
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    /**
     * 获取订单请求的xml数据
     *
     * @return
     */
    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();
        try {
//            String	nonceStr = genNonceStr();
            //微信支付总价转换
            DecimalFormat df = new DecimalFormat("0");
            String d = df.format(Double.parseDouble(payMoney) * 100);
//            String paymoney = Integer.valueOf((Float.valueOf(payMoney)*100))+"";//转换成分
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", wxAppId));
            packageParams.add(new BasicNameValuePair("body", goodsName));
            packageParams.add(new BasicNameValuePair("detail", goodsDetail));
            packageParams.add(new BasicNameValuePair("mch_id", wxmchid));
            packageParams.add(new BasicNameValuePair("nonce_str", genNonceStr()));
            packageParams.add(new BasicNameValuePair("notify_url", WXNotifyUrl));
//            out_tradNO = genOutTradNo();
            packageParams.add(new BasicNameValuePair("out_trade_no", out_tradNO));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee", d));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));


            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));


            String xmlstring = toXml(packageParams);
//            return xmlstring;
            //改变拼接之后xml字符串格式
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");

        } catch (Exception e) {
            return null;
        }

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(wxkey);//-------  1


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        return sb.toString();
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            Log.e("TempWXPayHelper", "temp_lf微信pay生成订单xml返回" + xml.toString());
            return xml;
        } catch (Exception e) {
        }
        return null;

    }

    public IWXAPI getMsgApi() {
        return msgApi;
    }

    public void setMsgApi(IWXAPI msgApi) {
        this.msgApi = msgApi;
    }

    public PayReq getReq() {
        return req;
    }

    public void setReq(PayReq req) {
        this.req = req;
    }

    public StringBuilder getSb() {
        return sbuff;
    }

    public void setSb(StringBuilder sb) {
        this.sbuff = sb;
    }

    public String getOut_tradNO() {
        return out_tradNO;
    }

    public void setOut_tradNO(String out_tradNO) {
        this.out_tradNO = out_tradNO;
    }

    public String getWXNotifyUrl() {
        return WXNotifyUrl;
    }

    public void setWXNotifyUrl(String WXNotifyUrl) {
        this.WXNotifyUrl = WXNotifyUrl;
    }

    public String getWxmchid() {
        return wxmchid;
    }

    public void setWxmchid(String wxmchid) {
        this.wxmchid = wxmchid;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getWxkey() {
        return wxkey;
    }

    public void setWxkey(String wxkey) {
        this.wxkey = wxkey;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public Map<String, String> getResultunifiedorder() {
        return resultunifiedorder;
    }

    public void setResultunifiedorder(Map<String, String> resultunifiedorder) {
        this.resultunifiedorder = resultunifiedorder;
    }

    @Override
    public String toString() {
        return "WXPayHelper{" +
                "out_tradNO='" + out_tradNO + '\'' +
                ", WXNotifyUrl='" + WXNotifyUrl + '\'' +
                ", wxmchid='" + wxmchid + '\'' +
                ", wxAppId='" + wxAppId + '\'' +
                ", wxkey='" + wxkey + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                '}';
    }
}