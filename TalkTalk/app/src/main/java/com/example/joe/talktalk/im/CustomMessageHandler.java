package com.example.joe.talktalk.im;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.joe.talktalk.common.Constants;

/**
 * 消息处理总线（所有发送过来的消息都经过这里）
 * Created by Administrator on 2018/7/3 0003.
 */

public class CustomMessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

    private static final String TAG = "CustomMessageHandler";
    private Context context;

    public CustomMessageHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
        String clientId;
        try {
            clientId = AVImClientManager.getInstance().getClientId();
            if (client.getClientId().equals(clientId)) {

                // 过滤掉自己发的消息
                if (!message.getFrom().equals(client.getClientId())) {
                    Intent intent = new Intent();
                    intent.setAction(Constants.HANDLER_MESSAGE);
                    intent.putExtra("message", message);
                    intent.putExtra("conversation_id", conversation.getConversationId());
                    context.sendBroadcast(intent);
                }
            } else {
                client.close(null);
            }
        } catch (Exception ex) {
            Log.d(TAG, "初始化CustomMessageHandler失败");
        }
    }

    @Override
    public void onMessageReceipt(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
        super.onMessageReceipt(message, conversation, client);
    }
}
