package com.lxf.ichat.po;

import java.io.Serializable;

public class MsgPO implements Serializable {

    public static final int A_VIEW_TYPE = 0;
    public static final int B_VIEW_TYPE = 1;
    public static final int C_VIEW_TYPE = 2;

    private String datetime;
    private String content;
    private int viewType;
    private UserPO user;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public UserPO getUser() {
        return user;
    }

    public void setUser(UserPO user) {
        this.user = user;
    }
}
