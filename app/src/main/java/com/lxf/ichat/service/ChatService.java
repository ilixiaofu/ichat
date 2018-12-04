package com.lxf.ichat.service;

import com.lxf.ichat.po.MsgPO;
import com.lxf.ichat.po.UserPO;

public interface ChatService {

    void sendToOneUser(MsgPO fromMsgPO, UserPO toUserPO);

    void sendToAllUser(MsgPO fromMsgPO);
}
