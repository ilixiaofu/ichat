package com.lxf.ichat.websocket;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.lxf.ichat.base.BaseObservable;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class ChatWebSocketClient extends WebSocketClient {

    private static final String TAG = ChatWebSocketClient.class.getName();

    private static volatile ChatWebSocketClient socketClient = null;
    private static volatile Handler handler;

    private ChatWebSocketClient(URI serverUri) {
        super(serverUri);
        handler = new Handler(Looper.getMainLooper());
    }

    public static ChatWebSocketClient getWebSocketClient(URI serverUri) {
        if (socketClient == null) {
            synchronized (ChatWebSocketClient.class) {
                if (socketClient == null) {
                    socketClient = new ChatWebSocketClient(serverUri);
                }
            }
        }
        return socketClient;
    }

    public static ChatWebSocketClient getWebSocketClient() {
        synchronized (ChatWebSocketClient.class) {
            return socketClient;
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.i(TAG, "onOpen: ");
    }

    @Override
    public void onMessage(String message) {
        Log.i(TAG, "onMessage: message=" + message);
        handler.post(new MessageTask(message));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.i(TAG, "onClose: ");
        handler.post(new CloseTask(reason));
    }

    @Override
    public void onError(Exception ex) {
        Log.i(TAG, "onError: ");
        handler.post(new ErrorTask(ex));
    }

    private static class MessageTask implements Runnable {

        private static final String TAG = MessageTask.class.getName();

        private String message;

        public MessageTask(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            Log.i(TAG, "run: ");
            WebSocketMsg webSocketMsg = new WebSocketMsg();
            webSocketMsg.setType(WebSocketMsg.MESSAGE);
            webSocketMsg.setMessage(message);
            BaseObservable.getObservable().notifyObservers(webSocketMsg);
        }
    }

    private static class CloseTask implements Runnable {

        private static final String TAG = CloseTask.class.getName();

        private String reason;

        public CloseTask(String reason) {
            this.reason = reason;
        }

        @Override
        public void run() {
            Log.i(TAG, "run: ");
            WebSocketMsg webSocketMsg = new WebSocketMsg();
            webSocketMsg.setType(WebSocketMsg.CLOSE);
            webSocketMsg.setMessage(reason);
            BaseObservable.getObservable().notifyObservers(webSocketMsg);
        }
    }

    private static class ErrorTask implements Runnable {

        private static final String TAG = ErrorTask.class.getName();

        private Exception ex;

        public ErrorTask(Exception ex) {
            this.ex = ex;
        }

        @Override
        public void run() {
            Log.i(TAG, "run: ");
            WebSocketMsg webSocketMsg = new WebSocketMsg();
            webSocketMsg.setType(WebSocketMsg.ERROR);
            webSocketMsg.setMessage(ex.getMessage());
            BaseObservable.getObservable().notifyObservers(webSocketMsg);
        }
    }
}
