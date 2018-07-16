package com.example.joe.talktalk.me.usercenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseAppCompatActivity;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.me.UserCenterActivity;
import com.example.joe.talktalk.utils.ToastUtil;

/**
 * Created by Administrator on 2018/7/14 0014.
 */

public class EditUserSignatureActivity extends BaseAppCompatActivity implements TextWatcher {

    //控件
    private Toolbar mToolbar;
    private EditText etSignature;
    private TextView tvNumber;
    //个签
    private String signature;
    private String newSignature;

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
        etSignature.setSelection(etSignature.length());
        tvNumber.setText(35 - etSignature.length() + "");
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
        etSignature.addTextChangedListener(this);
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (Integer.parseInt(tvNumber.getText().toString()) > 0) {
            tvNumber.setText(Integer.parseInt(tvNumber.getText().toString()) - 1 + "");
        } else {
            ToastUtil.showShortToast(this, "不能再输入啦");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
