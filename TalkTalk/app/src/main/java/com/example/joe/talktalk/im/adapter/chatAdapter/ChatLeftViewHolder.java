package com.example.joe.talktalk.im.adapter.chatAdapter;

import android.content.Context;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.example.joe.talktalk.R;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class ChatLeftViewHolder extends ChatCommonViewHolder<AVIMMessage> {

    public ChatLeftViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_chat_left_layout);
    }

    @Override
    public void init() {

    }

    @Override
    public void initData(AVIMMessage avimMessage) {

    }

    @Override
    public void setData(AVIMMessage avimMessage) {
        super.setData(avimMessage);
    }
}
