package com.example.joe.talktalk.me.usercenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.me.UserCenterActivity;

/**
 * Created by Administrator on 2018/7/14 0014.
 */

public class EditUserSignatureActivity extends BaseAppCompatActivity {

    //控件
    private Toolbar mToolbar;
    private EditText etSignature;
    private TextView tvNumber;

    private String signature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_signature_layout);
    }

    @Override
    public void initData() {
        signature = getIntent().getStringExtra(Constants.EDIT_SIGNATURE);
    }

    @Override
    public void initView() {
        click(R.id.tv_save);
        mToolbar = $(R.id.toolbar);
        etSignature = $(R.id.et_user_signature);
        tvNumber = $(R.id.tv_number);

        etSignature.setText(signature);
    }

    @Override
    public void initTitle() {
        mToolbar.setTitle(R.string.tv_edit_user_signature);
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
        }
    }


    /**
     * 保存操作
     */
    private void save() {
        signature = etSignature.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Constants.EDIT_SIGNATURE, signature);
        setResult(RESULT_OK, intent);
    }
}
