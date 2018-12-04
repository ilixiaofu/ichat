package com.lxf.ichat.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.R;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.base.BaseObservable;
import com.lxf.ichat.po.MsgPO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.service.ChatService;
import com.lxf.ichat.service.ChatServiceImpl;
import com.lxf.ichat.view.adapter.ChatRecyclerViewAdapter;
import com.lxf.ichat.view.viewholder.GroupChatActivityViewHolder;
import com.lxf.ichat.websocket.WebSocketMsg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GroupChatActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, Observer {

    private static final String TAG = GroupChatActivity.class.getName();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private GroupChatActivityViewHolder mViewHolder;
    private List<MsgPO> mList;
    private ChatService mChatService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        init();
    }

    private void init() {
        mViewHolder = new GroupChatActivityViewHolder(this);
        mViewHolder.back_TV.setOnClickListener(this);
        mViewHolder.send_BTN.setOnClickListener(this);
        mViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(GroupChatActivity.this));
        mViewHolder.title_TV.setText("欢迎进入ichat群聊大厅");

        mList = new ArrayList<>();
        ChatRecyclerViewAdapter adapter = new ChatRecyclerViewAdapter(this, mList);
        mViewHolder.recyclerView.setAdapter(adapter);

        mChatService = new ChatServiceImpl();
        BaseObservable.getObservable().addObserver(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: ");
        switch (v.getId()) {
            case R.id.group_chat_BTN_send:
                String msg = mViewHolder.input_ET.getText().toString();
                mViewHolder.input_ET.setText("");
                if (!msg.isEmpty()) {
                    UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
                    MsgPO msgPO = new MsgPO();
                    msgPO.setContent(msg);
                    msgPO.setDatetime(dateFormat.format(new Date()));
                    msgPO.setViewType(0);
                    msgPO.setUser(userPO);
                    mList.add(msgPO);
                    mViewHolder.recyclerView.getAdapter().notifyDataSetChanged();
                    mViewHolder.recyclerView.smoothScrollToPosition(mList.size() - 1);
                    mChatService.sendToAllUser(msgPO);
                }
                break;

            case R.id.group_chat_TV_back:
                setResult(0);
                finish();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyDown: ");
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                setResult(0);
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.i(TAG, "update: ");
        WebSocketMsg webSocketMsg = (WebSocketMsg) arg;
        if (WebSocketMsg.MESSAGE == webSocketMsg.getType()) {
            JSONObject data = JSONObject.parseObject( webSocketMsg.getMessage() );
            String msgType = data.getString( "msgType" );
            String datetime = data.getString( "time" );

            // 处理系统广播消息
            if ( "SYS".equals(msgType) ) {
                JSONObject msg = data.getJSONObject("msg");
                String extra = msg.getString("extra");
                if (extra.contains("当前在线人数")) {
                    mViewHolder.title_TV.setText(extra);
                }
            }

            // 处理群发广播消息
            if ( "P2M".equals(msgType) ) {
                JSONObject from = data.getJSONObject( "from" );
                String content = data.getString( "msg" );
                UserPO friend = JSONObject.parseObject(from.toJSONString(), UserPO.class);
                MsgPO msgPO = new MsgPO();
                msgPO.setContent(content);
                msgPO.setDatetime(datetime);
                msgPO.setUser(friend);
                msgPO.setViewType( 1 );
                mList.add( msgPO );
                mViewHolder.recyclerView.getAdapter().notifyDataSetChanged();
                mViewHolder.recyclerView.smoothScrollToPosition( mList.size() - 1 );
            }
        }
    }
}
