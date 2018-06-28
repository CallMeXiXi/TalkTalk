package com.example.joe.talktalk.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.joe.talktalk.ui.LoadingDialog;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity
        implements View.OnClickListener {

    private LoadingDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        init();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        init();
    }

    private void init() {
        initData();
        initView();
        initTitle();
        initListener();
    }

    public abstract void initData();

    public abstract void initView();

    public abstract void initTitle();

    public abstract void initListener();

    public <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    public <T extends View> T $(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }


    public void click(@IdRes int id) {
        findViewById(id).setOnClickListener(this);
    }

    public void click(View v) {
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        doClick(v.getId());
    }

    public abstract void doClick(int viewId);

    public void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showShortToast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showLongToast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    public void showLoadingDialog() {
        if (dialog == null) {
            dialog = new LoadingDialog(this);
        }
        dialog.show();
    }

    public void showLoadingDialog(String message) {
        if (dialog == null) {
            dialog = new LoadingDialog(this);
        }
        dialog.setMessage(message);
        dialog.show();
    }

    public void dismissLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
            showLongToast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }
}
