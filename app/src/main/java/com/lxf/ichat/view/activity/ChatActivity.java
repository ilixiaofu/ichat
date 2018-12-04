package com.lxf.ichat.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.R;
import com.lxf.ichat.base.BaseExecutorService;
import com.lxf.ichat.view.adapter.ChatRecyclerViewAdapter;
import com.lxf.ichat.base.BaseObservable;
import com.lxf.ichat.constant.BundleConstant;
import com.lxf.ichat.po.MsgPO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.service.ChatService;
import com.lxf.ichat.service.ChatServiceImpl;
import com.lxf.ichat.view.viewholder.ChatActivityViewHolder;
import com.lxf.ichat.websocket.WebSocketMsg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, Observer {

    private static final String TAG = ChatActivity.class.getName();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private ChatActivityViewHolder mViewHolder;
    private List<MsgPO> list;
    private ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init() {
        mViewHolder = new ChatActivityViewHolder(this);
        mViewHolder.back_TV.setOnClickListener(this);
        mViewHolder.send_BTN.setOnClickListener(this);
        mViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        chatService = new ChatServiceImpl();
        Bundle bundle = getIntent().getExtras();
        UserPO friend = (UserPO) bundle.getSerializable(BundleConstant.FRIEND_KEY);
        mViewHolder.title_TV.setText(friend.getNickname());
        MsgPO msgPO = (MsgPO) bundle.getSerializable(BundleConstant.MSGPO_KEY);

        list = new ArrayList<>();
        if (msgPO != null) {
            list.add(msgPO);
        }
        ChatRecyclerViewAdapter adapter = new ChatRecyclerViewAdapter(this, list);
        mViewHolder.recyclerView.setAdapter(adapter);

        BaseObservable.getObservable().addObserver(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: ");
        switch (v.getId()) {
            case R.id.chat_BTN_send:
                String msg = mViewHolder.input_ET.getText().toString();
                if (!msg.isEmpty()) {
                    mViewHolder.input_ET.setText("");
                    Bundle bundle = getIntent().getExtras();
                    UserPO userPO = BaseExecutorService.getExecutorServiceInstance().getUserPO();
                    UserPO friend = (UserPO) bundle.getSerializable(BundleConstant.FRIEND_KEY);
                    MsgPO msgPO = new MsgPO();
                    msgPO.setViewType(0);
                    msgPO.setDatetime(dateFormat.format(new Date()));
                    msgPO.setContent(msg);
                    msgPO.setUser(userPO);
                    list.add(msgPO);
                    mViewHolder.recyclerView.getAdapter().notifyDataSetChanged();
                    mViewHolder.recyclerView.smoothScrollToPosition(list.size() - 1);
                    chatService.sendToOneUser(msgPO, friend);
                }
                break;
            case R.id.chat_TV_back:
                finish();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    /**
     * <pre>
     * @Author      lxf
     * @Description 处理点对点广播
     * @Date        2018/11/25
     * @MethdName   update
     * @Param       [o, arg]
     * @return      void
     * </pre>
     */
    @Override
    public void update(Observable o, Object arg) {
        Log.i(TAG, "update: ");
        WebSocketMsg webSocketMsg = (WebSocketMsg) arg;
        if (WebSocketMsg.MESSAGE == webSocketMsg.getType()) {
            JSONObject data = JSONObject.parseObject( webSocketMsg.getMessage() );
            String msgType = data.getString( "msgType" );
            if ( "P2P".equals(msgType) ) {
                String datetime = data.getString( "time" );
                String content = data.getString( "msg" );
                JSONObject from = data.getJSONObject( "from" );
                UserPO friend = JSONObject.parseObject(from.toJSONString(), UserPO.class);
                MsgPO msgPO = new MsgPO();
                msgPO.setContent(content);
                msgPO.setDatetime(datetime);
                msgPO.setUser(friend);
                msgPO.setViewType( 1 );
                list.add( msgPO );
                mViewHolder.recyclerView.getAdapter().notifyDataSetChanged();
                mViewHolder.recyclerView.smoothScrollToPosition( list.size() - 1 );
            }
        }
    }

}
