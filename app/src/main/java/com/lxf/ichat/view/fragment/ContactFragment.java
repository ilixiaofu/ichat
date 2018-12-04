package com.lxf.ichat.view.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.R;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.base.BaseObservable;
import com.lxf.ichat.constant.BundleConstant;
import com.lxf.ichat.httpclient.HttpResponseCallback;
import com.lxf.ichat.po.MessagePO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.service.UserService;
import com.lxf.ichat.service.UserServiceImpl;
import com.lxf.ichat.view.activity.FriendProfileActivity;
import com.lxf.ichat.view.activity.SearchUserActivity;
import com.lxf.ichat.view.adapter.ContactFragmentAdapter;
import com.lxf.ichat.view.CustomView.CommonAlertDialog;
import com.lxf.ichat.view.CustomView.MessageBox;
import com.lxf.ichat.view.CustomView.ProgressDialogBox;
import com.lxf.ichat.view.viewholder.ContactFragmentViewHolder;
import com.lxf.ichat.websocket.WebSocketMsg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class ContactFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, Observer {

    private static final String TAG = ContactFragment.class.getName();

    private ContactFragmentViewHolder mViewHolder;
    private ProgressDialogBox mProgressDialog;
    private CommonAlertDialog mAlertDialog;
    private UserService mUserService;
    private List<UserPO> mList;

    private static ContactFragment contactFragment = null;

    public ContactFragment() {
    }


    public static ContactFragment getInstance() {
        if (contactFragment == null) {
            synchronized (ContactFragment.class) {
                if (contactFragment == null) {
                    contactFragment = new ContactFragment();
                }
            }
        }
        return contactFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        init(view);
//        registerForContextMenu( button );
        return view;
    }

    private void init(View v) {
        Log.i(TAG, "init: ");
        mViewHolder = new ContactFragmentViewHolder(v);
        mViewHolder.addContact_TV.setOnClickListener(this);
        mViewHolder.listView.setOnItemClickListener(this);
        mViewHolder.listView.setOnItemLongClickListener(this);

        mList = new ArrayList<>(0);
        ContactFragmentAdapter adapter = new ContactFragmentAdapter(ContactFragment.this.getContext(), mList);
        mViewHolder.listView.setAdapter(adapter);

        UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
        mViewHolder.head_CIV.setImageUrl(userPO.getHeadportraitURL(), new Rect(), R.mipmap.ichat_logo_icon);

        mAlertDialog = new CommonAlertDialog(ContactFragment.this.getContext());
        mAlertDialog.mViewHolder.ok_btn.setOnClickListener(this);
        mAlertDialog.mViewHolder.cancel_btn.setOnClickListener(this);
        mAlertDialog.setMessage("提示");

        mProgressDialog = new ProgressDialogBox(this.getContext());
        mProgressDialog.setMessage("加载好友列表中...");
        mProgressDialog.show();
        mUserService = new UserServiceImpl();
        mUserService.loadFriends(userPO.getUID(), new FriendListHttpResponseCallback());

        BaseObservable.getObservable().addObserver(this);
    }

    /**
     * <pre>
     * @Author lxf
     * @Description 删除好友上下文菜单
     * @Date 2018/11/29
     * @MethdName onCreateContextMenu
     * @Param [menu, v, menuInfo]
     * @return void
     * </pre>
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_friend_profile, menu);
    }

    /**
     * <pre>
     * @Author lxf
     * @Description 上下文菜单点击时间处理
     * @Date 2018/11/29
     * @MethdName onContextItemSelected
     * @Param [item]
     * @return boolean
     * </pre>
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_friend_profile_delete_friend:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
        } else {
            ContactFragmentAdapter adapter = (ContactFragmentAdapter) mViewHolder.listView.getAdapter();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_contract_TV_add_contact:
                // 跳转到查找页面
                Intent intent = new Intent();
                intent.setClass(getContext(), SearchUserActivity.class);
                startActivityForResult(intent, 201);
                break;
            case R.id.alert_dialog_common_BTN_ok:
                mAlertDialog.dismiss();
                UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
                UserPO friend = mList.get(index);
                mUserService.delFriend(userPO.getUID(), friend.getUID(), new DeleteFriendHttpResponseCallback());
                break;
            case R.id.alert_dialog_common_BTN_cancel:
                mAlertDialog.dismiss();
                break;
        }
    }

    // 添加联系人和删除联系人页面返回
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 201 || requestCode == 202) {
            if (resultCode == 200) {
                // 如果有做添加和删除操作重新加载好友列表
                mProgressDialog.setMessage("刷新好友列表中...");
                mProgressDialog.show();
                UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
                mUserService.loadFriends(userPO.getUID(), new FriendListHttpResponseCallback());
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserPO friend = mList.get(position);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        // 传入UserPO friend
        bundle.putSerializable(BundleConstant.FRIEND_KEY, friend);
        intent.putExtras(bundle);
        intent.setClass(ContactFragment.this.getContext(), FriendProfileActivity.class);
        startActivityForResult(intent, 202);
    }

    private static int index = -1;
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        showPopupMenu(view, position);
        return true;
    }

    private void showPopupMenu(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        getActivity().getMenuInflater().inflate(R.menu.menu_friend_profile, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_friend_profile_delete_friend:
                        mAlertDialog.show();
                        index = position;
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }

    // 观察者监听来自服务端系统广播
    @Override
    public void update(Observable observable, Object arg) {
        Log.i(TAG, "update: ");
        WebSocketMsg webSocketMsg = (WebSocketMsg) arg;
        if (WebSocketMsg.MESSAGE == webSocketMsg.getType()) {
            JSONObject data = JSONObject.parseObject(webSocketMsg.getMessage());
            String msgType = data.getString("msgType");

            // 处理系统广播消息
            if ("SYS".equals(msgType)) {
                JSONObject msg = data.getJSONObject("msg");
                String UID = msg.getString("uID");
                for (UserPO listPO : mList) {
                    // 好友上线刷新好友列表
                    if (listPO.getUID().equals(UID)) {
                        UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
                        mUserService.loadFriends(userPO.getUID(), new FriendListHttpResponseCallback());
                        break;
                    }
                }
            }
        }
    }


    private class FriendListHttpResponseCallback implements HttpResponseCallback {
        private final String TAG = FriendListHttpResponseCallback.class.getName();

        @Override
        public void onFailure(IOException e) {
            mProgressDialog.cancel();
            MessageBox.showMessage(ContactFragment.this.getContext(), "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String result) {
            mProgressDialog.cancel();
            JSONObject json = JSONObject.parseObject(result);
            String code = json.getString("code");
            String msg = json.getString("msg");
            if (!code.equals("200")) {
                MessageBox.showMessage(ContactFragment.this.getContext(), msg);
            } else {
                JSONArray array = json.getJSONArray("data");
                mList.clear();
                mList.addAll(array.toJavaList(UserPO.class));
                ContactFragmentAdapter adapter = (ContactFragmentAdapter) mViewHolder.listView.getAdapter();
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onErrorParam(MessagePO messagePO) {
            mProgressDialog.cancel();
            MessageBox.showMessage(ContactFragment.this.getContext(), messagePO.getContent());
        }
    }


    private class DeleteFriendHttpResponseCallback implements HttpResponseCallback {

        private final String TAG = DeleteFriendHttpResponseCallback.class.getName();

        @Override
        public void onFailure(IOException e) {
            mProgressDialog.cancel();
            MessageBox.showMessage(ContactFragment.this.getContext(), "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String result) {
            mProgressDialog.cancel();
            JSONObject json = JSONObject.parseObject(result);
            String code = json.getString("code");
            String msg = json.getString("msg");
            if (!code.equals("200")) {
                MessageBox.showMessage(ContactFragment.this.getContext(), msg);
            } else {
                mProgressDialog.setMessage("刷新好友列表中...");
                mProgressDialog.show();
                UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
                mUserService.loadFriends(userPO.getUID(), new FriendListHttpResponseCallback());
            }
        }

        @Override
        public void onErrorParam(MessagePO messagePO) {
            mProgressDialog.cancel();
            MessageBox.showMessage(ContactFragment.this.getContext(), messagePO.getContent());
        }
    }
}
