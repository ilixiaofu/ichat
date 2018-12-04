package com.lxf.ichat.httpclient;

import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BaseOkHttpClient {

    private static final String TAG = "OkHttpClientManager";
    private static BaseOkHttpClient mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private BaseOkHttpClient()
    {
        mOkHttpClient = new OkHttpClient();
//        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static BaseOkHttpClient getInstance()
    {
        if (mInstance == null)
        {
            synchronized (BaseOkHttpClient.class)
            {
                if (mInstance == null)
                {
                    mInstance = new BaseOkHttpClient();
                }
            }
        }
        return mInstance;
    }

    public void doGet(String url, final OKCallback responseCallback) {
        //创建一个Request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        try {
            Response response = call.execute();
            responseCallback.onResponse(call, response);
        } catch (IOException e) {
            responseCallback.onFailure(call, e);
        }

//        mDelivery.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response = call.execute();
//                    responseCallback.onResponse(call, response);
//                } catch (IOException e) {
//                    responseCallback.onFailure(call, e);
//                }
//            }
//        });
    }

    public void doPost(String url, String parametersName, String parametersValue, OKCallback responseCallback) {
//        RequestBody requestBody = RequestBody.create(
//                MediaType.parse("application/json; charset=utf-8"), parameters);
//        Request request = new Request.Builder()
//                .post(requestBody)
//                .url(url).build();

        RequestBody requestBody = new FormBody.Builder().add(parametersName,parametersValue).build();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url).build();
        Call call = mOkHttpClient.newCall(request);
        try {
            Response response = call.execute();
            responseCallback.onResponse(call, response);
        } catch (IOException e) {
            responseCallback.onFailure(call, e);
        }
    }

    public interface OKCallback<T> extends Callback {

        @Override
        void onFailure(Call call, IOException e);

        @Override
        void onResponse(Call call, Response response) throws IOException;
    }
}
