package com.ywg.androidcommon.recyclerview.chat;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.recyclerview.BaseHolder;

/**
 * 聊天界面的ViewHolder
 * Created by zyz on 2016/5/18.
 */
public class ChatHolder extends BaseHolder {

    TextView senderNameTv;
    TextView createTimeTv;

    public ChatHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);

        senderNameTv = getView(R.id.name_tv);
        createTimeTv = getView(R.id.create_time_tv);
    }
}