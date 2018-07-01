package com.example.joe.talktalk.account.resetPassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.joe.talktalk.R;
import com.example.joe.talktalk.account.resetPassword.fragment.ResetPassword1Fragment;
import com.example.joe.talktalk.base.BaseAppCompatActivity;

public class ResetPasswordActivity extends BaseAppCompatActivity {

    private Toolbar mToolbar;
    private TextView mTvTips1;
    private TextView mTvTips2;
    private FragmentTransaction mTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_layout);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mToolbar = $(R.id.toolbar);
        mTvTips1 = $(R.id.tv_tips_1);
        mTvTips2 = $(R.id.tv_tips_2);
        changeFragment(ResetPassword1Fragment.getInstance());
    }

    @Override
    public void initTitle() {
        mToolbar.setTitle("重置密码");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void doClick(int viewId) {

    }

    /**
     * 更换顶上提示的颜色
     *
     * @param f
     */
    private void switchStatus(Fragment f) {
        if (f instanceof ResetPassword1Fragment) {
            mTvTips1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mTvTips2.setTextColor(ContextCompat.getColor(this, R.color.gray));
        } else {
            mTvTips1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mTvTips2.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    /**
     * 切换Fragment
     *
     * @param f
     */
    public void changeFragment(Fragment f) {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.ll_container, f);
        mTransaction.commit();
        switchStatus(f);
    }

    /**
     * 跳转
     *
     * @param context
     */
    public static void launch(Context context) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }
}
