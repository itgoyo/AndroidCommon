package com.ywg.androidcommon.pay.AliPay;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by longf on 2016/5/18.
 */
public class AliPayHelper {
    private Handler mHandler;
    private String mOrderInfo;
    private Context mContext;
    private int mWhat;
    /**create the order info. 创建订单信息
     * @param subject 商品名称
     * @param body 商品详情
     * @param price 商品金额
     * @param PARTNER 签约合作者身份ID
     * @param SELLER 签约卖家支付宝账号
     * @param orderNumber 商户网站唯一订单号
     * @param alipayNotifyUrl 服务器异步通知页面路径
     * @param RSA_PRIVATE 商户私钥，pkcs8格式
     */
    public AliPayHelper(Context context, Handler handler, int handlerWhat, String subject, String body, String price, String PARTNER, String SELLER, String orderNumber, String alipayNotifyUrl, String RSA_PRIVATE){
        mContext=context;
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            Toast.makeText(mContext, "支付信息有误，请重试", Toast.LENGTH_SHORT).show();
            return;
        }
        mHandler=handler;
        mWhat = handlerWhat;
        mOrderInfo = getOrderInfo(subject,body,price,PARTNER,SELLER,orderNumber,alipayNotifyUrl,RSA_PRIVATE);

    }

    /**
     * call alipay sdk pay. 调用支付宝SDK支付
     *
     */
    public void pay() {
        if (TextUtils.isEmpty(mOrderInfo))
            return;
        Log.d("lf_支付宝支付", "配置支付参数");
//         订单 传入商品名称详情 和金额
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                Log.d("lf_支付宝支付", "启动支付宝");
                PayTask alipay = new PayTask((AppCompatActivity)mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(mOrderInfo,true);

                Message msg = new Message();
                msg.what = mWhat;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    /**create the order info. 创建订单信息
     * @param subject 商品名称
     * @param body 商品详情
     * @param price 商品金额
     * @param PARTNER 签约合作者身份ID
     * @param SELLER 签约卖家支付宝账号
     * @param orderNumber 商户网站唯一订单号
     * @param alipayNotifyUrl 服务器异步通知页面路径
     * @param RSA_PRIVATE 商户私钥，pkcs8格式
     * @return
     */
    private String getOrderInfo(String subject, String body, String price,String PARTNER,String SELLER,String orderNumber,String alipayNotifyUrl,String RSA_PRIVATE) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderNumber + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + alipayNotifyUrl + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        // 对订单做RSA 签名
        String sign = SignUtils.sign(orderInfo, RSA_PRIVATE);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//         完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + "sign_type=\"RSA\"";
        Log.e("lf", "支付宝支付订单参数："+payInfo);
        return payInfo;
    }
}