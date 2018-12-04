package com.lxf.ichat.httpclient;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.lxf.ichat.base.BaseExecutorService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IchatOkHttpClient {

    private static final String TAG = IchatOkHttpClient.class.getName();
    private static IchatOkHttpClient mIchatOkHttpClient;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    private IchatOkHttpClient() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static IchatOkHttpClient getInstance() {
        if (mIchatOkHttpClient == null) {
            synchronized (IchatOkHttpClient.class) {
                if (mIchatOkHttpClient == null) {
                    mIchatOkHttpClient = new IchatOkHttpClient();
                }
            }
        }
        return mIchatOkHttpClient;
    }

    public void doGet(String url, HttpResponseCallback responseCallback) {
        Log.i(TAG, "doGet: ");
        BaseExecutorService.getExecutorServiceInstance().
                execute(new GetRequestTask(url, responseCallback));
    }

    public void doPost(String url, String paramName, String paramValue, HttpResponseCallback responseCallback) {
        Log.i(TAG, "doPost: ");
        BaseExecutorService.getExecutorServiceInstance().
                execute(new PostRequestTask(url, paramName, paramValue, responseCallback));
    }

    private class GetRequestTask implements Runnable {

        private final String TAG = GetRequestTask.class.getName();

        private String url;
        private HttpResponseCallback responseCallback;

        public GetRequestTask(String url, HttpResponseCallback responseCallback) {
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
            final Call call = mOkHttpClient.newCall(request);
            final Response response;
            try {
                response = call.execute();
                Log.i(TAG, "run: " + response.toString());
                if (response.code() == 200) {
                    final String message = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            responseCallback.onResponse(message);
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
    }

    private class PostRequestTask implements Runnable {

        private final String TAG = PostRequestTask.class.getName();

        private String url;
        private String paramName;
        private String paramValue;
        private HttpResponseCallback responseCallback;

        public PostRequestTask(String url, String paramName, String paramValue, HttpResponseCallback responseCallback) {
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
            final Call call = mOkHttpClient.newCall(request);
            final Response response;
            try {
                response = call.execute();
                Log.i(TAG, "run: " + response.toString());
                if (response.code() == 200) {
                    final String message = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            responseCallback.onResponse(message);
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
    }
}
