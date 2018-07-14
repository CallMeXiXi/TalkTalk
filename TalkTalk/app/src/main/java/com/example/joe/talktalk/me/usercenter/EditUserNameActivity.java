package com.example.joe.talktalk.me.usercenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.me.UserCenterActivity;
import com.example.joe.talktalk.model.UserInfoModel;
import com.example.joe.talktalk.utils.ToastUtil;

/**
 * Created by Administrator on 2018/7/14 0014.
 */

public class EditUserNameActivity extends BaseAppCompatActivity {

    //控件
    private Toolbar mToolbar;
    private EditText etName;

    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_name_layout);
    }

    @Override
    public void initData() {
        name = getIntent().getStringExtra(Constants.EDIT_NICK_NAME);
    }

    @Override
    public void initView() {
        click(R.id.tv_save);
        click(R.id.iv_clear);
        mToolbar = $(R.id.toolbar);
        etName = $(R.id.et_user_nickname);

        etName.setText(name);
        etName.setSelection(name.length());
    }

    @Override
    public void initTitle() {
        mToolbar.setTitle(R.string.tv_edit_user_name);
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
            case R.id.tv_save:
                save();//保存
                break;
            case R.id.iv_clear:
                etName.setText("");
                etName.setSelection(0);
                break;
        }
    }

    /**
     * 保存操作
     */
    private void save() {
        name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShortToast(this, "用户昵称不能为空啦");
            return;
        }
        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                AVUser.getCurrentUser().put("nickname", name);
                AVUser.getCurrentUser().saveInBackground();

                Intent intent = new Intent();
                intent.putExtra(Constants.EDIT_NICK_NAME, name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
