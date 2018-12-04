package com.lxf.ichat.websocket;

public class WebSocketMsg {

    public static final int OPEN = 0;
    public static final int MESSAGE = 1;
    public static final int CLOSE = 2;
    public static final int ERROR = 3;

    private int type;
    private String message;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WebSocketMsg{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}
