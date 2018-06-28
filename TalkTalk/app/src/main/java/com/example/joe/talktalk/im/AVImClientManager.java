package com.example.joe.talktalk.im;

import android.text.TextUtils;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

/**
 * IM客户端管理类
 * Created by Administrator on 2018/6/28 0028.
 */

public class AVImClientManager {
    private static AVImClientManager instance;

    private AVIMClient avimClient;
    private String clientId;

    public AVImClientManager() {
    }

    public static AVImClientManager getInstance() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new AVImClientManager();
                }
            }
        }
        return instance;
    }

    public void open(String clientId, AVIMClientCallback callback) {
        this.clientId = clientId;
        this.avimClient = AVIMClient.getInstance(clientId);
        avimClient.open(callback);
    }

    public AVIMClient getAvimClient() {
        return avimClient;
    }

    public String getClientId() {
        if (TextUtils.isEmpty(clientId)) {
            throw new IllegalStateException("clientId is null");
        }
        return clientId;
    }
}
