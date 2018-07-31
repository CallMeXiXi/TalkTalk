package com.example.joe.talktalk.base;

import android.app.Application;
import android.os.StrictMode;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.example.joe.talktalk.common.Constants;
import com.example.joe.talktalk.im.AVImClientManager;
import com.example.joe.talktalk.im.CustomConversationEventHandler;
import com.example.joe.talktalk.im.CustomMessageHandler;
import com.example.joe.talktalk.model.UserInfoModel;
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
        //注册消息类型
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new CustomMessageHandler(this));
        //开启未读消息
        AVIMClient.setUnreadNotificationEnabled(true);
        //注册未读数量
        //AVIMMessageManager.setConversationEventHandler(new CustomConversationEventHandler(this));

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
