package com.lxf.ichat.httpclient;

import com.lxf.ichat.po.MessagePO;

import java.io.IOException;

public interface HttpResponseCallback {

    void onFailure(IOException e);

    void onResponse(String message);

    void onErrorParam(MessagePO messagePO);
}
