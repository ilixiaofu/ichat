package com.lxf.ichat.view.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.R;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.httpclient.HttpResponseCallback;
import com.lxf.ichat.po.ChangePasswordPO;
import com.lxf.ichat.po.MessagePO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.po.UserSettingPO;
import com.lxf.ichat.service.UserService;
import com.lxf.ichat.service.UserServiceImpl;
import com.lxf.ichat.view.CustomView.ChangePasswordAlertDialog;
import com.lxf.ichat.view.CustomView.CommonAlertDialog;
import com.lxf.ichat.view.CustomView.MessageBox;
import com.lxf.ichat.view.CustomView.ProgressDialogBox;
import com.lxf.ichat.view.adapter.UserSettingAdapter;
import com.lxf.ichat.view.viewholder.UserSettingActivityViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserSettingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = UserSettingActivity.class.getName();

    private UserSettingActivityViewHolder mViewHolder;
    private ChangePasswordAlertDialog mChangePasswordAlertDialog;
    private ProgressDialogBox mProgressDialog;
    private CommonAlertDialog mAlertDialog;
    private List<UserSettingPO> mList;
    private UserService mUserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        init();
    }

    private void init() {
        mViewHolder = new UserSettingActivityViewHolder(this);
        mViewHolder.back_TV.setOnClickListener(this);
        mViewHolder.listView.setOnItemClickListener(this);

        mAlertDialog = new CommonAlertDialog(this);
        mAlertDialog.mViewHolder.ok_btn.setOnClickListener(this);
        mAlertDialog.mViewHolder.cancel_btn.setOnClickListener(this);
        mAlertDialog.mViewHolder.tipinfo_tv.setText("确定退出？");
        mAlertDialog.setMessage("提示");

        mChangePasswordAlertDialog = new ChangePasswordAlertDialog(this);
        mChangePasswordAlertDialog.mViewHolder.ok_btn.setOnClickListener(this);
        mChangePasswordAlertDialog.mViewHolder.cancel_btn.setOnClickListener(this);

        mProgressDialog = new ProgressDialogBox(this);
        mProgressDialog.setMessage("请稍后...");

        mUserService = new UserServiceImpl();

        mList = new ArrayList<>();

        UserSettingPO accountSecurityPO = new UserSettingPO();
        accountSecurityPO.setCode(UserSettingPO.UPDATE_PWD);
        accountSecurityPO.setName("修改密码");

        UserSettingPO aboutIchatPO = new UserSettingPO();
        aboutIchatPO.setCode(UserSettingPO.ABOUT_APP);
        aboutIchatPO.setName("关于ichat");

        UserSettingPO exitAppPO = new UserSettingPO();
        exitAppPO.setCode(UserSettingPO.EXTI_SYSTEM);
        exitAppPO.setName("退出");

        mList.add(accountSecurityPO);
        mList.add(aboutIchatPO);
        mList.add(exitAppPO);
        UserSettingAdapter adapter = new UserSettingAdapter(this, mList);
        mViewHolder.listView.setAdapter( adapter );

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_user_setting_TV_back:
                finish();
                break;
            case R.id.alert_dialog_common_BTN_ok:
                mAlertDialog.dismiss();
                UserSettingPO userSettingPO = mList.get(exitIndex);
                // 退出app
                if (UserSettingPO.EXTI_SYSTEM.equals(userSettingPO.getCode())) {
                    moveTaskToBack(false );
                    ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
                    am.restartPackage(getPackageName());
                }
                break;
            case R.id.alert_dialog_common_BTN_cancel:
                mAlertDialog.dismiss();
                break;
            case R.id.alert_dialog_change_password_BTN_ok:
            {
                mProgressDialog.show();
                mChangePasswordAlertDialog.dismiss();
                ChangePasswordPO changePasswordPO = new ChangePasswordPO();
                UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
                changePasswordPO.setuID(userPO.getUID());
                changePasswordPO.setOldPassword(mChangePasswordAlertDialog.mViewHolder.oldPWD_et.getText().toString());
                changePasswordPO.setNewPassword(mChangePasswordAlertDialog.mViewHolder.newPWD_et.getText().toString());
                changePasswordPO.setConfirmPassword(mChangePasswordAlertDialog.mViewHolder.CFPWD_et.getText().toString());
                mUserService.changePassword(changePasswordPO, new ChangePwdHttpResponseCallback());
                break;
            }
            case R.id.alert_dialog_change_password_BTN_cancel:
                mChangePasswordAlertDialog.dismiss();
                break;
        }
    }

    private static int exitIndex = -1;
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        UserSettingPO userSettingPO = mList.get(position);
        // 退出app
        if (UserSettingPO.EXTI_SYSTEM.equals(userSettingPO.getCode())) {
            mAlertDialog.show();
        }

        if (UserSettingPO.ABOUT_APP.equals(userSettingPO.getCode())) {
            MessageBox.showMessage(this, "敬请期待");
        }

        if (UserSettingPO.UPDATE_PWD.equals(userSettingPO.getCode())) {
            mChangePasswordAlertDialog.show();
        }
    }

    // 修改密码
    private class ChangePwdHttpResponseCallback implements HttpResponseCallback {
        private final String TAG = ChangePwdHttpResponseCallback.class.getName();

        @Override
        public void onFailure(IOException e) {
            mProgressDialog.cancel();
            MessageBox.showMessage(UserSettingActivity.this, "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String data) {
            mProgressDialog.cancel();
            JSONObject json = JSONObject.parseObject( data );
            String code = json.getString( "code" );
            if (code.equals("200")) {
                MessageBox.showMessage(UserSettingActivity.this, "修改成功请退出重新登录");
            } else {
                String msg = json.getString( "msg" );
                MessageBox.showMessage(UserSettingActivity.this, msg);
            }
        }

        @Override
        public void onErrorParam(MessagePO messagePO) {
            mProgressDialog.cancel();
            MessageBox.showMessage(UserSettingActivity.this, messagePO.getContent());
        }
    }
}
