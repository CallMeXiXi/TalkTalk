package com.example.joe.talktalk.base;

import android.app.Application;
import android.os.StrictMode;

import com.avos.avoscloud.AVOSCloud;
import com.example.joe.talktalk.common.Constants;
import com.mob.MobSDK;

/**
 * Created by Joe_PC on 2018/6/27.
 */

public class TTApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 初始化Application
     */
    private void init() {
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, Constants.APP_ID, Constants.APP_KEY);

        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        // 在应用发布之前，请关闭调试日志，以免暴露敏感数据。
        AVOSCloud.setDebugLogEnabled(true);

        //mob短信
        MobSDK.init(this);

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
