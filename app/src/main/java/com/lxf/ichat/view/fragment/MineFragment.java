package com.lxf.ichat.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.R;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.po.MessagePO;
import com.lxf.ichat.po.MineFragmentFunPO;
import com.lxf.ichat.po.MsgPO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.service.UserService;
import com.lxf.ichat.service.UserServiceImpl;
import com.lxf.ichat.util.OKHttpUtil;
import com.lxf.ichat.view.CustomView.MessageBox;
import com.lxf.ichat.view.activity.UserProfileActivity;
import com.lxf.ichat.view.activity.UserSettingActivity;
import com.lxf.ichat.view.adapter.ChatRecyclerViewAdapter;
import com.lxf.ichat.view.adapter.MineFragmentFunAdapter;
import com.lxf.ichat.view.base.BaseRecyclerViewAdapter;
import com.lxf.ichat.view.viewholder.MineFragmentViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MineFragment extends Fragment implements AdapterView.OnItemClickListener, BaseRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = MineFragment.class.getName();
    private static MineFragment mineFragment;
    private MineFragmentViewHolder mViewHolder;
    private UserService mUserService;
    private List<MsgPO> mlist_RV;
    private List<MineFragmentFunPO> mList_LV;


    public MineFragment() {
        Log.i(TAG, "MineFragment: ");
    }

    public static MineFragment getInstance() {
        if (mineFragment == null) {
            synchronized (MineFragment.class) {
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
            }
        }
        return mineFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate( R.layout.fragment_mine, container, false );
        init(view);
        return view;
    }

    private void init(View view) {
        mViewHolder = new MineFragmentViewHolder( view);
        mViewHolder.listView.setOnItemClickListener( this );
        mViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(MineFragment.this.getContext()));

        UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
        mlist_RV = new ArrayList<>(0);
        MsgPO msgPO = new MsgPO();
        msgPO.setViewType(MsgPO.C_VIEW_TYPE);
        msgPO.setUser(userPO);
        mlist_RV.add(msgPO);
        ChatRecyclerViewAdapter rvAdapter = new ChatRecyclerViewAdapter(MineFragment.this.getContext(), mlist_RV);
        rvAdapter.setOnItemClickListner(this);
        mViewHolder.recyclerView.setAdapter(rvAdapter);

        mList_LV = new ArrayList<>(0);
        MineFragmentFunPO mineFragmentFunPO = new MineFragmentFunPO();
        mineFragmentFunPO.setIconURL("");
        mineFragmentFunPO.setName("设置");
        mList_LV.add(mineFragmentFunPO);
        MineFragmentFunAdapter lvAdapter = new MineFragmentFunAdapter(MineFragment.this.getContext(), mList_LV);
        mViewHolder.listView.setAdapter(lvAdapter);

        mUserService = new UserServiceImpl();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            //Fragment隐藏时调用
        }else {

        }
    }

    // ListView ItemClick
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.setClass(MineFragment.this.getContext(), UserSettingActivity.class);
        startActivityForResult(intent, 211);
    }

    // RecyclerView ItemClick
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(MineFragment.this.getContext(), UserProfileActivity.class);
        startActivityForResult(intent, 212);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 200) {
            // 如果有做修改操作重新加载用户信息
            UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
            mUserService.findUser(userPO.getUID(), new UserResponseCallback());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private class UserResponseCallback implements OKHttpUtil.ResponseCallback {
        @Override
        public void onFailure(IOException e) {
            MessageBox.showMessage(MineFragment.this.getContext(), "onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(String result) {
            JSONObject json = JSONObject.parseObject( result );
            String code = json.getString( "code" );
            String msg = json.getString( "msg" );
            if (!code.equals("200")) {
                MessageBox.showMessage(MineFragment.this.getContext(), msg);
            } else {
                String data = json.getString( "data" );
                UserPO userPO = JSONObject.parseObject(data, UserPO.class);
                BaseExecutorService.getExecutorServiceInstance().setUserPO(userPO);
                mlist_RV.get(0).getUser().setNickname(userPO.getNickname());
                mViewHolder.recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        @Override
        public void onIllegalArgumentException(MessagePO messagePO) {
            MessageBox.showMessage(MineFragment.this.getContext(), messagePO.getContent());
        }
    }
}
