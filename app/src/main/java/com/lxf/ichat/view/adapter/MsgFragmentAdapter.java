package com.lxf.ichat.view.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseCircleImageView;
import com.lxf.ichat.view.base.BaseListAdapter;
import com.lxf.ichat.po.MsgFragmentAdapterPO;

import java.util.List;

public class MsgFragmentAdapter extends BaseListAdapter<MsgFragmentAdapterPO> {

    private static final String TAG = MsgFragmentAdapter.class.getName();

    public MsgFragmentAdapter(Context context, List<MsgFragmentAdapterPO> data) {
        super(context, data);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        try {
            mViewHolder  = (ViewHolder) convertView.getTag();
        } catch (NullPointerException e) {
            convertView =this.layoutInflater.inflate( R.layout.item_list_fragment_msg, null );
            mViewHolder = new ViewHolder();
            mViewHolder.imageView = convertView.findViewById( R.id.item_list_fragment_msg_CIV_head );
            mViewHolder.nickname_TV = convertView.findViewById( R.id.item_list_fragment_msg_TV_nickname );
            mViewHolder.lastMsg_TV = convertView.findViewById( R.id.item_list_fragment_msg_TV_last_msg );
            mViewHolder.lastTime_TV = convertView.findViewById( R.id.item_list_fragment_msg_TV_last_time );
            convertView.setTag( mViewHolder );
        }
        MsgFragmentAdapterPO msgFragmentAdapterPO = data.get( position );
        mViewHolder.imageView.setImageUrl(msgFragmentAdapterPO.getUserPO().getHeadportraitURL(),
                new Rect(),
                R.mipmap.ichat_logo_icon);
        mViewHolder.nickname_TV.setText( msgFragmentAdapterPO.getUserPO().getNickname() );
        mViewHolder.lastMsg_TV.setText( msgFragmentAdapterPO.getLastMsg() );
        mViewHolder.lastTime_TV.setText( msgFragmentAdapterPO.getLastTime() );
        return convertView;
    }

    private static class ViewHolder {
        public BaseCircleImageView imageView;
        public TextView nickname_TV;
        public TextView lastMsg_TV;
        public TextView lastTime_TV;
    }
}
