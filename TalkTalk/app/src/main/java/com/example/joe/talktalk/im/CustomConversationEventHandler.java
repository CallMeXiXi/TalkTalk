package com.example.joe.talktalk.im;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationEventHandler;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.example.joe.talktalk.common.Constants;

import java.util.List;

/**
 * Created by Administrator on 2018/7/7 0007.
 */

public class CustomConversationEventHandler extends AVIMConversationEventHandler {

    private Context context;

    public CustomConversationEventHandler(Context context) {
        this.context = context;
    }

    /**
     * // conversation.getUnreadMessagesCount() 即该 conversation 的未读消息数量
     *
     * @param client
     * @param conversation
     */
    @Override
    public void onUnreadMessagesCountUpdated(AVIMClient client, final AVIMConversation conversation) {
        if (conversation != null) {
            conversation.queryMessages(new AVIMMessagesQueryCallback() {
                @Override
                public void done(List<AVIMMessage> list, AVIMException e) {
                    if (list != null) {
                        Intent intent = new Intent();
                        intent.setAction(Constants.UN_READ_MESSAGE_COUNT);
                        intent.putExtra("conversationId", conversation.getConversationId());
                        intent.putExtra("lastMessage", list.get(list.size() - 1));
                        intent.putExtra("unreadMessageCount", list.size());
                        context.sendBroadcast(intent);
                    }
                }
            });
        } else {
            super.onUnreadMessagesCountUpdated(client, conversation);
        }
    }


    @Override
    public void onMemberLeft(AVIMClient client, AVIMConversation conversation, List<String> members,
                             String kickedBy) {
        // 有其他成员离开时，执行此处逻辑
    }

    @Override
    public void onMemberJoined(AVIMClient client, AVIMConversation conversation,
                               List<String> members, String invitedBy) {
        // 手机屏幕上会显示一小段文字：Tom 加入到 551260efe4b01608686c3e0f ；操作者为：Tom
        Toast.makeText(AVOSCloud.applicationContext,
                members + "加入到" + conversation.getConversationId() + "；操作者为： "
                        + invitedBy, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onKicked(AVIMClient client, AVIMConversation conversation, String kickedBy) {
        // 当前 ClientId(Bob) 被踢出对话，执行此处逻辑
    }

    @Override
    public void onInvited(AVIMClient client, AVIMConversation conversation, String invitedBy) {
        // 当前 ClientId(Bob) 被邀请到对话，执行此处逻辑
    }
}
