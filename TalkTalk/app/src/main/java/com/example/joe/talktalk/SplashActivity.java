package com.example.joe.talktalk;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.signature.Base64Encoder;
import com.example.joe.talktalk.account.LoginActivity;
import com.example.joe.talktalk.im.AVImClientManager;
import com.example.joe.talktalk.model.UserInfoModel;
import com.example.joe.talktalk.utils.ToastUtil;


/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        init();
    }

    /**
     * 初始化判断
     */
    private void init() {
        Handler handler = new Handler();
        final UserInfoModel user = AVUser.getCurrentUser(UserInfoModel.class);
        if (user == null) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoginActivity.launch(SplashActivity.this);
                                finish();
                            }
                        }, 500);
                    } else {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //加密手机号码
                                String clientId = Base64Encoder.encode(user.getMobilePhoneNumber());
                                AVImClientManager.getInstance().open(clientId, new AVIMClientCallback() {
                                    @Override
                                    public void done(AVIMClient avimClient, AVIMException e) {
                                        if (e != null) {
                                            Log.e(TAG, e.getCode() + " : " + e.getMessage() + " :　" + e.getAppCode());
                                            e.printStackTrace();
                                            ToastUtil.showLongToast(SplashActivity.this, e.getMessage());
                                        } else {
                                            MainActivity.launch(SplashActivity.this);
                                            SplashActivity.this.finish();
                                        }
                                    }
                                });
                }
            }, 500);
        }
    }
}
