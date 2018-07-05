package com.example.joe.talktalk.im.adapter.chatViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public abstract class ChatCommonViewHolder<T> extends RecyclerView.ViewHolder {

    public ChatCommonViewHolder(Context context, ViewGroup parent, int resId) {
        super(LayoutInflater.from(context).inflate(resId, parent, false));
        init();
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public abstract void init();

    /**
     * 绑定数据
     *
     * @param t
     */
    public abstract void initData(T t);

    public void setData(T t) {
        initData(t);
    }
}
