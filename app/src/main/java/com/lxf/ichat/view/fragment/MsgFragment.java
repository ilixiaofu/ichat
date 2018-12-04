package com.lxf.ichat.view.fragment;


import android.content.Intent;
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

import com.alibaba.fastjson.JSONObject;
import com.lxf.ichat.R;
import com.lxf.ichat.base.BaseObservable;
import com.lxf.ichat.constant.BundleConstant;
import com.lxf.ichat.po.MsgFragmentAdapterPO;
import com.lxf.ichat.po.MsgPO;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.view.activity.ChatActivity;
import com.lxf.ichat.view.activity.GroupChatActivity;
import com.lxf.ichat.view.adapter.MsgFragmentAdapter;
import com.lxf.ichat.view.viewholder.MsgFragmentViewHolder;
import com.lxf.ichat.websocket.WebSocketMsg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MsgFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, Observer {

    private static final String TAG = MsgFragment.class.getName();
    private static MsgFragment msgFragment;

    private MsgFragmentViewHolder mViewHolder;
    private List<MsgFragmentAdapterPO> mList;

    public MsgFragment() {
    }

    public static MsgFragment getInstance() {
        if (msgFragment == null) {
            synchronized (MsgFragment.class) {
                if (msgFragment == null) {
                    msgFragment = new MsgFragment();
                }
            }
        }
        return msgFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        Log.i(TAG, "init: ");
        mViewHolder = new MsgFragmentViewHolder(v);
        mViewHolder.listView.setOnItemClickListener(this);
        mViewHolder.listView.setOnItemLongClickListener(this);

        mList = new ArrayList<>(0);
        MsgFragmentAdapterPO sys = new MsgFragmentAdapterPO();
        sys.setLastMsg("暂无消息");
        sys.setLastTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        UserPO ichat = new UserPO();
        ichat.setUID("ichat_sys");
        ichat.setNickname("ichat群聊大厅");
        ichat.setHeadportraitURL("");
        sys.setUserPO(ichat);
        mList.add(sys);

        MsgFragmentAdapter adapter = new MsgFragmentAdapter(MsgFragment.this.getContext(), mList);
        mViewHolder.listView.setAdapter(adapter);

        BaseObservable.getObservable().addObserver(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //Fragment隐藏时调用
        } else {
            MsgFragmentAdapter adapter = (MsgFragmentAdapter) mViewHolder.listView.getAdapter();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * <pre>
     * @Author lxf
     * @Description 移除记录上下文菜单
     * @Date 2018/11/29
     * @MethdName onCreateContextMenu
     * @Param [menu, v, menuInfo]
     * @return void
     * </pre>
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_msg_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MsgFragmentAdapterPO item = mList.get(position);
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        if (position == 0) {
            intent.setClass(MsgFragment.this.getContext(), GroupChatActivity.class);
        } else {
            MsgPO msgPO = new MsgPO();
            msgPO.setViewType(1);
            msgPO.setUser(item.getUserPO());
            msgPO.setContent(item.getLastMsg());
            msgPO.setDatetime(item.getLastTime());
            bundle.putSerializable(BundleConstant.MSGPO_KEY, msgPO);
            bundle.putSerializable(BundleConstant.FRIEND_KEY, item.getUserPO());
            intent.setClass(MsgFragment.this.getContext(), ChatActivity.class);
        }
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i > 0) {
            showPopupMenu(view, i);
        }
        return true;
    }

    private void showPopupMenu(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        getActivity().getMenuInflater().inflate(R.menu.menu_msg_list, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_msg_list_remove:
                        mList.remove(position);
                        MsgFragmentAdapter adapter = (MsgFragmentAdapter) mViewHolder.listView.getAdapter();
                        adapter.notifyDataSetChanged();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.i(TAG, "update: ");
        WebSocketMsg webSocketMsg = (WebSocketMsg) arg;
        if (WebSocketMsg.MESSAGE == webSocketMsg.getType()) {
            JSONObject data = JSONObject.parseObject(webSocketMsg.getMessage());
            String msgType = data.getString("msgType");
            String datetime = data.getString("time");

            // 处理系统广播消息
            if ("SYS".equals(msgType)) {
                JSONObject msg = data.getJSONObject("msg");
                String extra = msg.getString("extra");
                MsgFragmentAdapterPO item = mList.get(0);
                item.setLastTime(datetime);
                item.setLastMsg("系统消息:" + extra);
                MsgFragmentAdapter adapter = (MsgFragmentAdapter) mViewHolder.listView.getAdapter();
                adapter.notifyDataSetChanged();
            }

            // 处理群发广播消息
            if ("P2M".equals(msgType)) {
                JSONObject from = data.getJSONObject("from");
                String content = data.getString("msg");
                UserPO friend = JSONObject.parseObject(from.toJSONString(), UserPO.class);
                MsgFragmentAdapterPO item = mList.get(0);
                item.setLastTime(datetime);
                item.setLastMsg(friend.getNickname() + ":" + content);
                MsgFragmentAdapter adapter = (MsgFragmentAdapter) mViewHolder.listView.getAdapter();
                adapter.notifyDataSetChanged();
            }

            // 处理点对点广播
            if ("P2P".equals(msgType)) {
                if ( mList.size() > 100 ) {
                    MsgFragmentAdapterPO msgFragmentAdapterPO = mList.get(0);
                    mList.clear();
                    mList.add(msgFragmentAdapterPO);
                }
                JSONObject from = data.getJSONObject("from");
                String content = data.getString("msg");
                UserPO friend = JSONObject.parseObject(from.toJSONString(), UserPO.class);
                boolean isContain = false;
                for (MsgFragmentAdapterPO item : mList) {
                    if (item.getUserPO().getUID().equals(friend.getUID())) {
                        item.setLastTime(datetime);
                        item.setLastMsg(content);
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) {
                    MsgFragmentAdapterPO msgFragmentAdapterPO = new MsgFragmentAdapterPO();
                    msgFragmentAdapterPO.setLastTime(datetime);
                    msgFragmentAdapterPO.setLastMsg(content);
                    msgFragmentAdapterPO.setUserPO(friend);
                    mList.add(msgFragmentAdapterPO);
                }
                MsgFragmentAdapter adapter = (MsgFragmentAdapter) mViewHolder.listView.getAdapter();
                adapter.notifyDataSetChanged();
            }
        }
    }
}
