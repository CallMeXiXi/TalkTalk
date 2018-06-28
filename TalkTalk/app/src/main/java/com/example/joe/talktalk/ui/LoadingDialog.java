package com.example.joe.talktalk.ui;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.joe.talktalk.R;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class LoadingDialog extends AlertDialog {

    private Context context;
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
        init("加载中..");
    }

    protected LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init(msg);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        init(msg);
    }

    private void init(String msg) {
        setCancelable(true);
        View view =LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
        setView(view);
        TextView tvTips = (TextView) view.findViewById(R.id.tv_tips);
        tvTips.setText(msg);
    }
}
