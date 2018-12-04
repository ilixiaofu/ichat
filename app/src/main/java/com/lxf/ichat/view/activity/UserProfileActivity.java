package com.lxf.ichat.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONObject;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.lxf.ichat.R;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.httpclient.HttpResponseCallback;
import com.lxf.ichat.po.MessagePO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.service.UserService;
import com.lxf.ichat.service.UserServiceImpl;
import com.lxf.ichat.view.CustomView.MessageBox;
import com.lxf.ichat.util.UserPOTransferListUtil;
import com.lxf.ichat.view.adapter.UserProfileAdapter;
import com.lxf.ichat.view.CustomView.ProgressDialogBox;
import com.lxf.ichat.view.CustomView.SelectSexAlertDialog;
import com.lxf.ichat.view.CustomView.UpdateUserAlertDialog;
import com.lxf.ichat.view.viewholder.UserProfileActivityViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = UserProfileActivity.class.getName();

    private UserProfileActivityViewHolder mViewHolder;
    private SelectSexAlertDialog mSelectSexAlertDialog;
    private UpdateUserAlertDialog mUpdateUserAlertDialog;
    private ProgressDialogBox mProgressDialog;
    private UserService mUserService;
    private List<Map<String, String>> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
    }

    private void init() {
        mViewHolder = new UserProfileActivityViewHolder(this);
        mViewHolder.back_TV.setOnClickListener(this);
        mViewHolder.listView.setOnItemClickListener( this );

        mSelectSexAlertDialog = new SelectSexAlertDialog(this);
        mSelectSexAlertDialog.mViewHolder.ok_btn.setOnClickListener(this);
        mSelectSexAlertDialog.mViewHolder.cancel_btn.setOnClickListener(this);

        mUpdateUserAlertDialog = new UpdateUserAlertDialog(this);
        mUpdateUserAlertDialog.mViewHolder.ok_btn.setOnClickListener(this);
        mUpdateUserAlertDialog.mViewHolder.cancel_btn.setOnClickListener(this);

        mProgressDialog = new ProgressDialogBox(this);
        mProgressDialog.setMessage("更新中...");

        UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
        String[] keys = getResources().getStringArray( R.array.ichat_user_profile_key );
        mList = new ArrayList<>(0);
        UserPOTransferListUtil.getUserInfoList(keys, userPO, mList);
        UserProfileAdapter adapter = new UserProfileAdapter( this, mList);
        mViewHolder.listView.setAdapter( adapter );

        mUserService = new UserServiceImpl();

    }

    @Override
    public void onClick(View view) {
        UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
        UserPO temp = new UserPO();
        temp.setUID(userPO.getUID());
        temp.setSex(userPO.getSex());
        switch (view.getId()) {
            // 返回上一个页面
            case R.id.activity_user_profile_TV_back:
                finish();
                break;
            case R.id.alert_dialog_select_sex_BTN_cancel:
                mSelectSexAlertDialog.cancel();
                break;
            case R.id.alert_dialog_select_sex_BTN_ok:
            {
                mSelectSexAlertDialog.cancel();
                if (mSelectSexAlertDialog.mViewHolder.male_rb.isChecked()) {
                    if ( !("男".equals(temp.getSex())) ) {
                        temp.setSex("男");
                        mProgressDialog.show();
                        mUserService.updateUserInfo(temp, new UpdateInfoHttpResponseCallback());
                    }
                }
                if (mSelectSexAlertDialog.mViewHolder.female_rb.isChecked()) {
                    if ( !("女".equals(temp.getSex())) ) {
                        temp.setSex("女");
                        mProgressDialog.show();
                        mUserService.updateUserInfo(temp, new UpdateInfoHttpResponseCallback());
                    }
                }
                break;
            }
            case R.id.alert_dialog_update_user_BTN_cancel:
                mUpdateUserAlertDialog.cancel();
                break;
            case R.id.alert_dialog_update_user_BTN_ok:
            {
                mUpdateUserAlertDialog.cancel();
                String key = mUpdateUserAlertDialog.mViewHolder.title_tv.getText().toString();
                if ("昵称".equals(key)) {
                    temp.setNickname(mUpdateUserAlertDialog.mViewHolder.content_et.getText().toString());
                }

                if ("个性签名".equals(key)) {
                    temp.setSignature(mUpdateUserAlertDialog.mViewHolder.content_et.getText().toString());
                }

                if ("地区".equals(key)) {
                    temp.setPlace(mUpdateUserAlertDialog.mViewHolder.content_et.getText().toString());
                }

                if ("故乡".equals(key)) {
                    temp.setHometown(mUpdateUserAlertDialog.mViewHolder.content_et.getText().toString());
                }

                if ("邮箱".equals(key)) {
                    temp.setEmail(mUpdateUserAlertDialog.mViewHolder.content_et.getText().toString());
                }
                mProgressDialog.show();
                mUserService.updateUserInfo(temp, new UpdateInfoHttpResponseCallback());
                break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyDown: ");
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 修改资料
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        final UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
        final UserPO temp = new UserPO();
        temp.setUID(userPO.getUID());
        Map<String, String> map = mList.get(position);
        switch (position) {
            case 1:
            {
                mUpdateUserAlertDialog.mViewHolder.title_tv.setText(map.get("key"));
                mUpdateUserAlertDialog.mViewHolder.content_et.setText(map.get("value"));
                mUpdateUserAlertDialog.show();
                break;
            }
            case 2:
                {
                    mUpdateUserAlertDialog.mViewHolder.title_tv.setText(map.get("key"));
                    mUpdateUserAlertDialog.mViewHolder.content_et.setText(map.get("value"));
                    mUpdateUserAlertDialog.show();
                    break;
                }
            case 3:
            {
                mSelectSexAlertDialog.mViewHolder.title_tv.setText(map.get("key"));
                mSelectSexAlertDialog.show();
                break;
            }
            case 4:
            {
                final DatePickDialog dialog = new DatePickDialog(this);
                //设置上下年分限制
                dialog.setYearLimt(50);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog.setOnChangeLisener(null);
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        dialog.cancel();
                        if (date.compareTo(new Date()) == 1) {
                            MessageBox.showMessage(UserProfileActivity.this, "时间不能超过当前时间");
                        } else {
                            temp.setBirthday(date.getTime());
                            mProgressDialog.show();
                            mUserService.updateUserInfo(temp, new UpdateInfoHttpResponseCallback());
                        }
                    }
                });
                dialog.show();
                break;
            }
            case 5:
            {
                mUpdateUserAlertDialog.mViewHolder.title_tv.setText(map.get("key"));
                mUpdateUserAlertDialog.mViewHolder.content_et.setText(map.get("value"));
                mUpdateUserAlertDialog.show();
                break;
            }
            case 6:
            {
                mUpdateUserAlertDialog.mViewHolder.title_tv.setText(map.get("key"));
                mUpdateUserAlertDialog.mViewHolder.content_et.setText(map.get("value"));
                mUpdateUserAlertDialog.show();
                break;
            }
            case 7:
            {
                mUpdateUserAlertDialog.mViewHolder.title_tv.setText(map.get("key"));
                mUpdateUserAlertDialog.mViewHolder.content_et.setText(map.get("value"));
                mUpdateUserAlertDialog.show();
                break;
            }
        }
    }


    private class UserHttpResponseCallback implements HttpResponseCallback {
        private final String TAG = UserHttpResponseCallback.class.getName();

        @Override
        public void onFailure(IOException e) {
            MessageBox.showMessage(UserProfileActivity.this, "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String result) {
            JSONObject json = JSONObject.parseObject( result );
            String code = json.getString( "code" );
            String msg = json.getString( "msg" );
            if (!code.equals("200")) {
                MessageBox.showMessage(UserProfileActivity.this, msg);
            } else {
                String data = json.getString( "data" );
                UserPO userPO = JSONObject.parseObject(data, UserPO.class);
                BaseExecutorService.getExecutorServiceInstance().setUserPO(userPO);
                // 清空数据
                mList.clear();
                UserProfileAdapter adapter = (UserProfileAdapter) mViewHolder.listView.getAdapter();
                // 通知数据改变
                adapter.notifyDataSetChanged();
                String[] keys = getResources().getStringArray( R.array.ichat_user_profile_key );
                // 数据改变
                UserPOTransferListUtil.getUserInfoList(keys, userPO, mList);
                // 通知数据改变
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onErrorParam(MessagePO messagePO) {
            MessageBox.showMessage(UserProfileActivity.this, messagePO.getContent());
        }
    }

    // 修改资料请求
    private class UpdateInfoHttpResponseCallback implements HttpResponseCallback {
        private final String TAG = UpdateInfoHttpResponseCallback.class.getName();

        @Override
        public void onFailure(IOException e) {
            mProgressDialog.cancel();
            MessageBox.showMessage(UserProfileActivity.this, "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String data) {
            mProgressDialog.cancel();
            JSONObject json = JSONObject.parseObject( data );
            String code = json.getString( "code" );
            String msg = json.getString( "msg" );
            MessageBox.showMessage(UserProfileActivity.this, msg);
            if (code.equals("200")) {
                setResult(200);
                UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
                mUserService.findUser(userPO.getUID(), new UserHttpResponseCallback());
            }
        }

        @Override
        public void onErrorParam(MessagePO messagePO) {
            mProgressDialog.cancel();
            MessageBox.showMessage(UserProfileActivity.this, messagePO.getContent());
        }
    }
}
