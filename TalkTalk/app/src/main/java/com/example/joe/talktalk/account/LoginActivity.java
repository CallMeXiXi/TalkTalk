package com.example.joe.talktalk.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.register.RegisterActivity;
import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.utils.ToastUtil;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class LoginActivity extends BaseAppCompatActivity {

    private TextView mTvRegister;
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
        click(R.id.tv_register);
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {
        switch (viewId){
            case R.id.tv_register:
                RegisterActivity.launch(this);
                break;
        }
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
