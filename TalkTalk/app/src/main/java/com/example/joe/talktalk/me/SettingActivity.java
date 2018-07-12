package com.example.joe.talktalk.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.LoginActivity;
import com.example.joe.talktalk.base.BaseAppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class SettingActivity extends BaseAppCompatActivity {

    //控件
    private Toolbar mToolbar;
    private SwitchCompat scPhone;
    private SwitchCompat scEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        click(R.id.rl_change_password);
        click(R.id.rl_about_us);
        click(R.id.btn_logout);
        mToolbar = $(R.id.toolbar);
        scPhone = $(R.id.sc_phone);
        scEmail = $(R.id.sc_email);
    }

    @Override
    public void initTitle() {
        mToolbar.setTitle(R.string.setting);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void doClick(int viewId) {
        switch (viewId) {
            case R.id.rl_change_password:
                break;
            case R.id.rl_about_us:
                break;
            case R.id.btn_logout:
                AVUser.logOut();
                LoginActivity.launch(this);
                finish();
                break;
        }
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }
}
