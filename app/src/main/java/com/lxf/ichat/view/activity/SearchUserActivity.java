package com.lxf.ichat.view.activity;

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
import com.lxf.ichat.util.UserPOTransferListUtil;
import com.lxf.ichat.view.CustomView.MessageBox;
import com.lxf.ichat.view.CustomView.ProgressDialogBox;
import com.lxf.ichat.view.adapter.UserProfileAdapter;
import com.lxf.ichat.view.viewholder.SearchUserActivityViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchUserActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SearchUserActivity.class.getName();

    private SearchUserActivityViewHolder mViewHolder;
    private ProgressDialogBox mProgressDialog;
    private UserService mUserService;
    private List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        init();
    }

    private void init() {
        Log.i(TAG, "init: ");
        mViewHolder = new SearchUserActivityViewHolder(this);
        mViewHolder.search_BTN.setOnClickListener(this);
        mViewHolder.add_BTN.setOnClickListener(this);
        mViewHolder.back_BTN.setOnClickListener(this);

        list = new ArrayList<>(0);
        UserProfileAdapter adapter = new UserProfileAdapter( SearchUserActivity.this, list );
        mViewHolder.listView.setAdapter( adapter );
        mUserService = new UserServiceImpl();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_user_BTN_search:
                UserProfileAdapter adapter = (UserProfileAdapter) mViewHolder.listView.getAdapter();
                // 清空数据
                list.clear();
                // 通知数据改变
                adapter.notifyDataSetChanged();

                mProgressDialog = new ProgressDialogBox(this);
                mProgressDialog.setMessage("查找中...");
                mProgressDialog.show();
                String FID = mViewHolder.UID_ET.getText().toString().trim();
                mUserService.findUser(FID, new SearchUserResponseCallback());
                break;
            case R.id.search_user_BTN_add:
                int count = mViewHolder.listView.getAdapter().getCount();
                if (count < 1) {
                    MessageBox.showMessage(this, "请先查找");
                } else {
                    mProgressDialog.setMessage("请稍等...");
                    mProgressDialog.show();
                    Map<String, String> map = (Map<String, String>) mViewHolder.listView.getAdapter().getItem(0);
                    String mFID = map.get("value");
                    UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
                    mUserService.addFriend(userPO.getUID(), mFID, new AddFriendResponseCallback());
                }
                break;
            case R.id.search_user_BTN_back:
                finish();
                break;
        }
    }

    private class SearchUserResponseCallback implements OKHttpUtil.ResponseCallback {
        @Override
        public void onFailure(IOException e) {
            mProgressDialog.cancel();
            MessageBox.showMessage(SearchUserActivity.this, "onFailure: " + e.toString());
        }

        @Override
        public void onResponse(String message) {
            mProgressDialog.cancel();
            JSONObject json = JSONObject.parseObject(message);
            String code = json.getString("code");
            String msg = json.getString("msg");
            if (!code.equals("200")) {
                MessageBox.showMessage(SearchUserActivity.this, msg);
            } else {
                UserProfileAdapter adapter = (UserProfileAdapter) mViewHolder.listView.getAdapter();
                // 清空数据
                list.clear();
                // 通知数据改变
                adapter.notifyDataSetChanged();

                String data = json.getString( "data" );
                UserPO friend = JSONObject.parseObject(data, UserPO.class);
                String[] keys = getResources().getStringArray( R.array.ichat_user_profile_key );

                // 数据改变
                UserPOTransferListUtil.getUserInfoList(keys, friend, list);
                // 通知数据改变
                adapter.notifyDataSetChanged();

                mViewHolder.UID_ET.setText("");
            }
        }

        @Override
        public void onIllegalArgumentException(MessagePO messagePO) {
            mProgressDialog.cancel();
            MessageBox.showMessage(SearchUserActivity.this, messagePO.getContent());
        }
    }

    private class AddFriendResponseCallback implements OKHttpUtil.ResponseCallback {
        @Override
        public void onFailure(IOException e) {
            mProgressDialog.cancel();
            MessageBox.showMessage(SearchUserActivity.this, "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String data) {
            mProgressDialog.cancel();
            JSONObject json = JSONObject.parseObject(data);
            String code = json.getString("code");
            String msg = json.getString("msg");
            MessageBox.showMessage(SearchUserActivity.this, msg);
            if (code.equals("200")) {
                // 返回上一个页面
                setResult(200);
            }
        }

        @Override
        public void onIllegalArgumentException(MessagePO messagePO) {
            mProgressDialog.cancel();
            MessageBox.showMessage(SearchUserActivity.this, messagePO.getContent());
        }
    }
}
