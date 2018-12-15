package com.lxf.ichat.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.R;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.po.MessagePO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.service.UserService;
import com.lxf.ichat.service.UserServiceImpl;
import com.lxf.ichat.util.OKHttpUtil;
import com.lxf.ichat.view.CustomView.MessageBox;
import com.lxf.ichat.view.CustomView.ProgressDialogBox;
import com.lxf.ichat.view.viewholder.LoginActivityViewHolder;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = LoginActivity.class.getName();
    private static final String AUTO_LOGIN = "AUTO_LOGIN";
    private static final String KEEP_PWD = "KEEP_PWD";
    private static final String UID = "UID";
    private static final String PWD = "PWD";
    private static boolean isFirstStart = false;

    private LoginActivityViewHolder mViewHolder;
    private UserService userService;
    private ProgressDialogBox mProgressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        Log.i(TAG, "init: ");
        isFirstStart = true;
        sharedPreferences = getSharedPreferences( "caches", MODE_PRIVATE);
        mProgressDialog = new ProgressDialogBox(this);
        mProgressDialog.setCancelable(true);
        mViewHolder = new LoginActivityViewHolder(this);
        mViewHolder.login_BTN.setOnClickListener(this);
        mViewHolder.newUser_TV.setOnClickListener(this);
        mViewHolder.FPWD_TV.setOnClickListener(this);
        mViewHolder.keepPwd_CB.setOnClickListener(this);
        mViewHolder.autoLogin_CB.setOnClickListener(this);

        userService = new UserServiceImpl();

        boolean keepPwd = sharedPreferences.getBoolean(KEEP_PWD, false);
        boolean autoLogin = sharedPreferences.getBoolean(AUTO_LOGIN, false);
        if (keepPwd) {
            String uid = sharedPreferences.getString( UID, "" );
            String pwd = sharedPreferences.getString( PWD, "" );
            mViewHolder.UID_ET.setText(uid);
            mViewHolder.PWD_ET.setText(pwd);
            mViewHolder.keepPwd_CB.setChecked(true);
            if (autoLogin) {
                mViewHolder.autoLogin_CB.setChecked(true);
            }
        }
    }

    // 自动登录
    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstStart) {
            isFirstStart = false;
            autoLogin();
        }
    }


    private void autoLogin() {
        if (mViewHolder.autoLogin_CB.isChecked() && mViewHolder.keepPwd_CB.isChecked()) {
            boolean keepPwd = sharedPreferences.getBoolean(KEEP_PWD, false);
            boolean autoLogin = sharedPreferences.getBoolean(AUTO_LOGIN, false);
            if (keepPwd && autoLogin) {
                mProgressDialog.setMessage("自动登录中按返回键取消...");
                mProgressDialog.show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (mProgressDialog.isShowing()) {
                            userService.login(mViewHolder, new LoginResponseCallback());
                        }
                    }
                }, 3 * 1000);
            }
        }
    }


    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: ");
        switch (v.getId()) {
            case R.id.login_BTN_login:
                // 登录
                mProgressDialog.setMessage("登录中...");
                mProgressDialog.show();
                userService.login(mViewHolder, new LoginResponseCallback());
                break;
            case R.id.login_CB_keep_pwd:
                if (!mViewHolder.keepPwd_CB.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEEP_PWD, false);
                    editor.apply();
                }
                break;
            case R.id.login_CB_auto_login:
                if (!mViewHolder.autoLogin_CB.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(AUTO_LOGIN, false);
                    editor.apply();
                }
                break;
            case R.id.login_TV_FPWD:
                MessageBox.showMessage(this, "该功能暂未实现");
                break;
            case R.id.login_TV_new_user:
                register();
                break;
        }
    }

    /**
     * <pre>
     * @Author      lxf
     * @Description 注册
     * @Date        2018/11/23
     * @MethdName   register
     * @Param       []
     * @return      void
     * </pre>
     */
    private void register() {
        Log.i(TAG, "register: ");
        Intent intent = new Intent(  );
        intent.setClass( LoginActivity.this, RegisterActivity.class );
        startActivityForResult( intent, 201 );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 201) {
            if (resultCode == 200) {
                String UID = data.getStringExtra("UID");
                String PWD = data.getStringExtra("PWD");
                mViewHolder.UID_ET.setText(UID);
                mViewHolder.PWD_ET.setText(PWD);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class LoginResponseCallback implements OKHttpUtil.ResponseCallback {

        @Override
        public void onFailure(IOException e) {
            mProgressDialog.cancel();
            MessageBox.showMessage(LoginActivity.this, "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String data) {
            mProgressDialog.cancel();
            JSONObject json = JSONObject.parseObject( data );
            String code = json.getString( "code" );
            String msg = json.getString( "msg" );
            if (!code.equals("200")) {
                MessageBox.showMessage(LoginActivity.this, msg);
            } else {
                // 记住密码被选择
                if (mViewHolder.keepPwd_CB.isChecked()) {
                    // 缓存密码
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(UID, mViewHolder.UID_ET.getText().toString());
                    editor.putString(PWD, mViewHolder.PWD_ET.getText().toString());
                    editor.putBoolean(KEEP_PWD, true);
                    // 自动登录被选择
                    if (mViewHolder.autoLogin_CB.isChecked()) {
                        editor.putBoolean(AUTO_LOGIN, true);
                    }
                    editor.apply();
                }
                String UID = mViewHolder.UID_ET.getText().toString();
                userService.findUser(UID, new UserResponseCallback());
            }
        }

        @Override
        public void onIllegalArgumentException(MessagePO messagePO) {
            mProgressDialog.cancel();
            MessageBox.showMessage(LoginActivity.this, messagePO.getContent());
        }
    }

    private class UserResponseCallback implements OKHttpUtil.ResponseCallback {
        @Override
        public void onFailure(IOException e) {
            mProgressDialog.cancel();
            MessageBox.showMessage(LoginActivity.this, "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String message) {
            mProgressDialog.cancel();
            JSONObject json = JSONObject.parseObject(message);
            String code = json.getString("code");
            String msg = json.getString("msg");
            if (!code.equals("200")) {
                MessageBox.showMessage(LoginActivity.this, msg);
            } else {
                String data = json.getString("data");
                UserPO userPO = JSONObject.parseObject(data, UserPO.class);
                BaseExecutorService.getExecutorServiceInstance().setUserPO(userPO);
                Intent intent = new Intent(  );
                intent.setClass( LoginActivity.this, MainActivity.class );
                LoginActivity.this.startActivity( intent );
                LoginActivity.this.finish();
            }
        }

        @Override
        public void onIllegalArgumentException(MessagePO messagePO) {
            mProgressDialog.cancel();
            MessageBox.showMessage(LoginActivity.this, messagePO.getContent());
        }
    }
}
