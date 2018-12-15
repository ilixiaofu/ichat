package com.lxf.ichat.view.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.lxf.ichat.R;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.po.MessagePO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.service.MyService;
import com.lxf.ichat.service.UserService;
import com.lxf.ichat.service.UserServiceImpl;
import com.lxf.ichat.util.OKHttpUtil;
import com.lxf.ichat.view.fragment.ContactFragment;
import com.lxf.ichat.view.fragment.MineFragment;
import com.lxf.ichat.view.fragment.MsgFragment;
import com.lxf.ichat.view.fragment.ZoneFragment;
import com.lxf.ichat.view.viewholder.MainActivityViewHolder;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private static final String TAG = MainActivity.class.getName();

    private MainActivityViewHolder mViewHolder;

    private FragmentManager fmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mViewHolder = new MainActivityViewHolder( this);
        mViewHolder.msg_SIV.setOnClickListener( this );
        mViewHolder.contact_SIV.setOnClickListener( this );
        mViewHolder.mine_SIV.setOnClickListener( this );
//        mViewHolder.setting_SIV.setOnClickListener( this );

        fmanager = getSupportFragmentManager();
        FragmentTransaction transaction = fmanager.beginTransaction();
        transaction.add( R.id.main_FL, MsgFragment.getInstance() );
        transaction.add( R.id.main_FL, ContactFragment.getInstance() );
        transaction.add( R.id.main_FL, MineFragment.getInstance() );
        transaction.add( R.id.main_FL, ZoneFragment.getInstance() );
        transaction.hide(MsgFragment.getInstance());
        transaction.hide(MineFragment.getInstance());
        transaction.hide(ZoneFragment.getInstance());
        transaction.show(ContactFragment.getInstance());
        transaction.commit();

        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: ");
        FragmentTransaction transaction = fmanager.beginTransaction();
        switch (v.getId()) {
            case R.id.main_SIV_msg:
                transaction.hide(ContactFragment.getInstance());
                transaction.hide(MineFragment.getInstance());
                transaction.hide(ZoneFragment.getInstance());
                transaction.show(MsgFragment.getInstance());
                break;
            case R.id.main_SIV_contact:
                transaction.hide(MsgFragment.getInstance());
                transaction.hide(MineFragment.getInstance());
                transaction.hide(ZoneFragment.getInstance());
                transaction.show(ContactFragment.getInstance());
                break;
            case R.id.main_SIV_mine:
                transaction.hide(MsgFragment.getInstance());
                transaction.hide(ContactFragment.getInstance());
                transaction.hide(ZoneFragment.getInstance());
                transaction.show(MineFragment.getInstance());
                break;
//            case R.id.main_SIV_setting:
//                transaction.hide(MsgFragment.getInstance());
//                transaction.hide(ContactFragment.getInstance());
//                transaction.hide(MineFragment.getInstance());
//                transaction.show(ZoneFragment.getInstance());
//                break;
        }
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyDown: ");
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                moveTaskToBack(true);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
        stopService(serviceIntent);

        UserService userService = new UserServiceImpl();
        UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
        userService.logout(userPO.getUID(), new LogoutResponseCallback());
        super.onDestroy();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//        MyService.MyBinder myBinder = (MyService.MyBinder) service;
//        myService = myBinder.getMyService();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    // 退出登录
    private class LogoutResponseCallback implements OKHttpUtil.ResponseCallback {
        @Override
        public void onFailure(IOException e) {

        }

        @Override
        public void onResponse(String data) {

        }

        @Override
        public void onIllegalArgumentException(MessagePO messagePO) {

        }
    }
}
