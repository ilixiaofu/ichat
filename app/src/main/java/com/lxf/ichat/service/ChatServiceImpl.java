package com.lxf.ichat.service;

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.po.MsgPO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.websocket.ChatWebSocketClient;

public class ChatServiceImpl implements ChatService {

    @Override
    public void sendToOneUser(MsgPO fromMsgPO, UserPO toUserPO) {
        final JSONObject data = new JSONObject( true );
        data.put("from", fromMsgPO.getUser());
        data.put("to", toUserPO);
        data.put( "msg", fromMsgPO.getContent() );
        BaseExecutorService.getExecutorServiceInstance().
                execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ChatWebSocketClient.getWebSocketClient().send(data.toJSONString());
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void sendToAllUser(MsgPO fromMsgPO) {
        final JSONObject data = new JSONObject( true );
        data.put( "from", fromMsgPO.getUser() );
        data.put( "msg", fromMsgPO.getContent() );
        BaseExecutorService.getExecutorServiceInstance().
                execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ChatWebSocketClient.getWebSocketClient().send(data.toJSONString());
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
