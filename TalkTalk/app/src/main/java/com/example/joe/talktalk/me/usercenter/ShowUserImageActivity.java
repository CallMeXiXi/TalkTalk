package com.example.joe.talktalk.me.usercenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.model.UserInfoModel;

/**
 * Created by Administrator on 2018/7/14 0014.
 */

public class ShowUserImageActivity extends BaseAppCompatActivity {

    //控件
    private Toolbar mToolbar;
    private ImageView ivHeader;

    private UserInfoModel user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_user_image_layout);
    }

    @Override
    public void initData() {
        user = AVUser.getCurrentUser(UserInfoModel.class);
    }

    @Override
    public void initView() {
        ivHeader = $(R.id.iv_header);
        mToolbar = $(R.id.toolbar);
        //赋值
        Glide.with(this).load(user.getHeader()).into(ivHeader);
    }

    @Override
    public void initTitle() {
        mToolbar.setTitle(R.string.user_image_icon);
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

    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, ShowUserImageActivity.class);
        context.startActivity(intent);
    }
}
