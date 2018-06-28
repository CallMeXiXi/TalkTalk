package com.example.joe.talktalk.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseAppCompatActivity;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class LoginActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {

    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
