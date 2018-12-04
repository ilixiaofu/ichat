package com.lxf.ichat.po;

import java.io.Serializable;

public class MsgFragmentAdapterPO implements Serializable {

    private String lastMsg;
    private String lastTime;
    private UserPO userPO;

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    @Override
    public String toString() {
        return "MsgFragmentAdapterPO{" +
                "lastMsg='" + lastMsg + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", userPO=" + userPO +
                '}';
    }
}
