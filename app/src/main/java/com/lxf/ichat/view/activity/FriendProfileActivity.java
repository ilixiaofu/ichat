package com.lxf.ichat.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.R;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.constant.BundleConstant;
import com.lxf.ichat.po.MessagePO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.service.UserService;
import com.lxf.ichat.service.UserServiceImpl;
import com.lxf.ichat.util.OKHttpUtil;
import com.lxf.ichat.util.UserPOTransferListUtil;
import com.lxf.ichat.view.CustomView.CommonAlertDialog;
import com.lxf.ichat.view.CustomView.MessageBox;
import com.lxf.ichat.view.CustomView.ProgressDialogBox;
import com.lxf.ichat.view.adapter.UserProfileAdapter;
import com.lxf.ichat.view.viewholder.FriendProfileActivityViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FriendProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = FriendProfileActivity.class.getName();

    private FriendProfileActivityViewHolder mViewHolder;
    private ProgressDialogBox mProgressDialog;
    private CommonAlertDialog mAlertDialog;
    private UserService mUserService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        init();
    }

    private void init() {
        mViewHolder = new FriendProfileActivityViewHolder( this );
        mViewHolder.back_BTN.setOnClickListener( this );
        mViewHolder.sendMsg_BTN.setOnClickListener( this );

        mProgressDialog = new ProgressDialogBox(this);
        mAlertDialog = new CommonAlertDialog(this);
        mAlertDialog.mViewHolder.ok_btn.setOnClickListener(this);
        mAlertDialog.mViewHolder.cancel_btn.setOnClickListener(this);
        mAlertDialog.setMessage("提示");

        mUserService = new UserServiceImpl();

        UserPO friend = (UserPO) getIntent().getExtras().getSerializable(BundleConstant.FRIEND_KEY);
        List<Map<String, String>> list = new ArrayList<>(0);
        String[] keys = getResources().getStringArray( R.array.ichat_user_profile_key );
        UserPOTransferListUtil.getUserInfoList(keys, friend, list);
        UserProfileAdapter adapter = new UserProfileAdapter( this, list );
        mViewHolder.listView.setAdapter( adapter );
    }

    /**
     * <pre>
     * @Author      lxf
     * @Description 删除好友选项菜单
     * @Date        2018/11/29
     * @MethdName   onCreateOptionsMenu
     * @Param       [menu]
     * @return      boolean
     * </pre>
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_friend_profile, menu );
//        menu.add( Menu.NONE,
//                1,
//                Menu.NONE,
//                "新增菜单"
//        );
        return true;
    }

    /**
     * <pre>
     * @Author      lxf
     * @Description 选项菜单点击事件处理
     * @Date        2018/11/29
     * @MethdName   onOptionsItemSelected
     * @Param       [item]
     * @return      boolean
     * </pre>
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_friend_profile_delete_friend:
                mAlertDialog.show();
                break;
        }
        return super.onOptionsItemSelected( item );
    }

    private static boolean delete = false;
    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: ");
        switch (v.getId()) {
            case R.id.friend_profile_BTN_back:
                finish();
                break;
            case R.id.alert_dialog_common_BTN_ok:
                mAlertDialog.dismiss();
                deleteFriend();
                break;
            case R.id.alert_dialog_common_BTN_cancel:
                mAlertDialog.dismiss();
                break;
            case R.id.friend_profile_BTN_send_msg:
            {
                UserPO friend = (UserPO) getIntent().getExtras().getSerializable("friend");
                if ("离线".equals(friend.getStatus())) {
                    MessageBox.showMessage(this, "暂不支持发送离线消息");
                } else {
                    Intent intent = getIntent();
                    intent.setClass(this, ChatActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            }
        }
    }

    private void deleteFriend() {
        UserProfileAdapter adapter = (UserProfileAdapter) mViewHolder.listView.getAdapter();
        int count = adapter.getCount();
        if (count < 1) {
            MessageBox.showMessage(this, "error");
        } else {
            mProgressDialog.setMessage("删除中...");
            mProgressDialog.show();
            Bundle bundle = getIntent().getExtras();
            UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
            UserPO friend = (UserPO) bundle.getSerializable(BundleConstant.FRIEND_KEY);
            mUserService.delFriend(userPO.getUID(), friend.getUID(), new DeleteFriendResponseCallback());
        }
    }

    private class DeleteFriendResponseCallback implements OKHttpUtil.ResponseCallback {

        private final String TAG = DeleteFriendResponseCallback.class.getName();

        @Override
        public void onFailure(IOException e) {
            mProgressDialog.cancel();
            MessageBox.showMessage(FriendProfileActivity.this, "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String data) {
            mProgressDialog.cancel();
            JSONObject json = JSONObject.parseObject( data );
            String code = json.getString( "code" );
            String msg = json.getString( "msg" );
            if (!code.equals("200")) {
                MessageBox.showMessage(FriendProfileActivity.this, msg);
            } else {
                delete = true;
                setResult(200);
                finish();
            }
        }

        @Override
        public void onIllegalArgumentException(MessagePO messagePO) {
            mProgressDialog.cancel();
            MessageBox.showMessage(FriendProfileActivity.this, messagePO.getContent());
        }
    }
}
