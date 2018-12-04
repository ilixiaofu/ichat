package com.lxf.ichat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.constant.URLConstant;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.websocket.ChatWebSocketClient;

import org.java_websocket.WebSocket;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class MyService extends Service {

    private static final String TAG = MyService.class.getName();

    private MyBinder mBinder;

    public MyService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        mBinder = new MyBinder();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
        BaseExecutorService.getExecutorServiceInstance().
                execute(new ConnectWebSocketTask(userPO));
    }

    /**
     * <pre>
     * @Author      lxf
     * @Description 当使用startService的时候，再次点击开启服务，只会走onStartCommand()方法
     * @Date        2018/11/24
     * @MethdName   onStartCommand
     * @Param       [intent, flags, startId]
     * @return      int
     * </pre>
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        ChatWebSocketClient.getWebSocketClient().close();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MyBinder extends Binder {
        /**
         * <pre>
         * @Author      lxf
         * @Description 获得当前service状态
         * @Date        2018/11/24
         * @MethdName   getService
         * @Param       []
         * @return      com.lxf.ichat.service.MyService
         * </pre>
         */
        public MyService getService() {
            return MyService.this;
        }
    }

    private class ConnectWebSocketTask implements Runnable {

        private UserPO userPO;

        public ConnectWebSocketTask(UserPO userPO) {
            this.userPO = new UserPO();
            this.userPO.setUID(userPO.getUID());
            this.userPO.setNickname(userPO.getNickname());
        }

        @Override
        public void run() {
            String user = JSONObject.toJSONString(userPO);
            try {
                URI uri = new URI(URLConstant.WS_URL + URLEncoder.encode(user, "UTF-8" ) );
                ChatWebSocketClient webSocketClient = ChatWebSocketClient.getWebSocketClient(uri);
                webSocketClient.connect();
                while (!webSocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN )) {
                    Log.i( TAG, "连接服务端中:" );
                }
                Log.i( TAG, "连接成功" );
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
