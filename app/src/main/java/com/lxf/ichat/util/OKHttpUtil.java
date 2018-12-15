package com.lxf.ichat.util;


import android.os.Handler;
import android.os.Looper;

import com.lxf.ichat.po.MessagePO;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OKHttpUtil {

    private OkHttpClient mOkHttpClient;
    private ExecutorService mExecutorService;
    private Handler mHandler;

    private static OKHttpUtil mOKHttpUtil;

    private OKHttpUtil() {
        mOkHttpClient = new OkHttpClient();
        mExecutorService = Executors.newCachedThreadPool();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OKHttpUtil getInstance() {
        if (mOKHttpUtil == null) {
            synchronized (OKHttpUtil.class) {
                if (mOKHttpUtil == null) {
                    mOKHttpUtil = new OKHttpUtil();
                }
            }
        }
        return mOKHttpUtil;
    }

    public void doGet(String url, ResponseCallback responseCallback) {
        mExecutorService.
                execute(new GetRequestTask(url, responseCallback));
    }

    public void doPost(String url, String paramName, String paramValue, ResponseCallback responseCallback) {
        mExecutorService.
                execute(new PostRequestTask(url, paramName, paramValue, responseCallback));
    }


    private class GetRequestTask implements Runnable {
        private String url;
        private ResponseCallback responseCallback;

        public GetRequestTask(String url, ResponseCallback responseCallback) {
            this.url = url;
            this.responseCallback = responseCallback;
        }

        @Override
        public void run() {
            //创建一个Request
            Request request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
            execute(request, responseCallback);
        }
    }

    private class PostRequestTask implements Runnable {

        private final String TAG = PostRequestTask.class.getName();

        private String url;
        private String paramName;
        private String paramValue;
        private ResponseCallback responseCallback;

        public PostRequestTask(String url, String paramName, String paramValue, ResponseCallback responseCallback) {
            this.url = url;
            this.paramName = paramName;
            this.paramValue = paramValue;
            this.responseCallback = responseCallback;
        }

        @Override
        public void run() {
            RequestBody requestBody = new FormBody.Builder().add(paramName, paramValue).build();
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url(url).build();
            execute(request, responseCallback);
        }
    }

    private void execute(Request request, final ResponseCallback responseCallback) {
        Call call = mOkHttpClient.newCall(request);
        try {
            final Response response = call.execute();
            if (response.code() == 200) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            responseCallback.onResponse(response.body().string());
                        } catch (IOException e) {
                            responseCallback.onFailure(e);
                        }
                    }
                });

            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            responseCallback.onResponse(response.body().string());
                        } catch (IOException e) {
                            responseCallback.onFailure(e);
                        }
                    }
                });
            }
        } catch (final IOException e) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    responseCallback.onFailure(e);
                }
            });
        }
    }

    public interface ResponseCallback {
        void onFailure(IOException e);
        void onResponse(String msg);
        void onIllegalArgumentException(MessagePO messagePO);
    }
}
