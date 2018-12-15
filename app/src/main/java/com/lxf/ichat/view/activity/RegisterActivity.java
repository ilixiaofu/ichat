package com.lxf.ichat.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.R;
import com.lxf.ichat.po.MessagePO;
import com.lxf.ichat.service.UserService;
import com.lxf.ichat.service.UserServiceImpl;
import com.lxf.ichat.util.OKHttpUtil;
import com.lxf.ichat.view.CustomView.MessageBox;
import com.lxf.ichat.view.CustomView.ProgressDialogBox;
import com.lxf.ichat.view.viewholder.RegisterActivityViewHolder;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getName();

    private RegisterActivityViewHolder mViewHolder;
    private UserService mUserService;
    private ProgressDialogBox mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        Log.i(TAG, "init: ");
        mViewHolder = new RegisterActivityViewHolder( this );
        mViewHolder.back_BTN.setOnClickListener( this );
        mViewHolder.regist_BTN.setOnClickListener( this );
        mUserService = new UserServiceImpl();
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: ");
        switch (v.getId()) {
            case R.id.register_BTN_back:
                finish();
                break;
            case R.id.register_BTN_regist:
                mProgressDialog = new ProgressDialogBox(this);
                mProgressDialog.setMessage("注册中...请稍等");
                mProgressDialog.show();
                mUserService.register(mViewHolder, new RegisterResponseCallback());
                break;
        }
    }

    private class RegisterResponseCallback implements OKHttpUtil.ResponseCallback {
        private final String TAG = RegisterResponseCallback.class.getName();

        @Override
        public void onFailure(IOException e) {
            mProgressDialog.cancel();
            MessageBox.showMessage(RegisterActivity.this, "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String data) {
            mProgressDialog.cancel();
            JSONObject json = JSONObject.parseObject( data );
            String code = json.getString("code");
            String msg = json.getString("msg");
            MessageBox.showMessage(RegisterActivity.this, msg);
            if ("200".equals(code)) {
                Intent intent = getIntent();
                intent.putExtra("UID", mViewHolder.UID_ET.getText().toString());
                intent.putExtra("PWD", mViewHolder.PASWD_ET.getText().toString());
                setResult(200, intent);
                finish();
            }
        }

        @Override
        public void onIllegalArgumentException(MessagePO messagePO) {
            mProgressDialog.cancel();
            MessageBox.showMessage(RegisterActivity.this, messagePO.getContent());
        }
    }
}
