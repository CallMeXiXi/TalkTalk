package com.example.joe.talktalk.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.model.UserInfoModel;
import com.example.joe.talktalk.utils.ToastUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class UserCenterActivity extends BaseAppCompatActivity {

    //控件
    private Toolbar mToolbar;
    private CircleImageView civHeader;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvSignature;

    private UserInfoModel user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail_layout);
    }


    @Override
    public void initData() {
        user = AVUser.getCurrentUser(UserInfoModel.class);
    }

    @Override
    public void initView() {
        click(R.id.rl_edit_user_image);
        click(R.id.rl_edit_user_nickname);
        click(R.id.rl_edit_user_sex);
        click(R.id.rl_edit_user_signature);
        mToolbar = $(R.id.toolbar);
        civHeader = $(R.id.civ_header);
        tvName = $(R.id.tv_user_nickname);
        tvSex = $(R.id.tv_user_sex);
        tvSignature = $(R.id.tv_user_signature);

        Glide.with(this).load(user.getHeader()).into(civHeader);
        tvName.setText(user.getNickname());
        tvSignature.setText(user.getSignature());
        if (user.getSex() == 0) {
            tvSex.setText("保密");
        } else if (user.getSex() == 1) {
            tvSex.setText("男");
        } else if (user.getSex() == 2) {
            tvSex.setText("女");
        }
    }

    @Override
    public void initTitle() {
        mToolbar.setTitle(R.string.personal_info);
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
            case R.id.rl_edit_user_image:
                ToastUtil.showShortToast(this, "rl_edit_user_image");
                break;
            case R.id.rl_edit_user_nickname:
                ToastUtil.showShortToast(this, "rl_edit_user_nickname");
                break;
            case R.id.rl_edit_user_sex:
                ToastUtil.showShortToast(this, "rl_edit_user_sex");
                break;
            case R.id.rl_edit_user_signature:
                ToastUtil.showShortToast(this, "rl_edit_user_signature");
                break;
        }
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, UserCenterActivity.class);
        context.startActivity(intent);
    }
}
