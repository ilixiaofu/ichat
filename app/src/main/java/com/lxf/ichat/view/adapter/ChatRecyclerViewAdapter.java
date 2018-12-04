package com.lxf.ichat.view.adapter;


import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.po.MsgPO;
import com.lxf.ichat.view.base.BaseCircleImageView;
import com.lxf.ichat.view.base.BaseRecyclerViewAdapter;
import com.lxf.ichat.view.viewholder.BaseRecyclerViewHolder;

import java.util.List;

public class ChatRecyclerViewAdapter extends BaseRecyclerViewAdapter <MsgPO> {

    private static final String TAG = ChatRecyclerViewAdapter.class.getName();

    public ChatRecyclerViewAdapter(Context context, List<MsgPO> data) {
        super(context, data);
    }

    @Override
    public int getItemViewType(int position) {
        return this.data.get(position).getViewType();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (MsgPO.A_VIEW_TYPE == viewType) {
            View view = this.layoutInflater.inflate(R.layout.item_list_chat_a, null);
            return new AViewHolder( view );
        }
        if (MsgPO.B_VIEW_TYPE == viewType) {
            View view = this.layoutInflater.inflate(R.layout.item_list_chat_b, null);
            return new BViewHolder( view );
        }
        if (MsgPO.C_VIEW_TYPE == viewType) {
            View view = View.inflate( ChatRecyclerViewAdapter.this.context, R.layout.item_list_fragment_mine_profile, null);
            return new CViewHolder( view );
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((BaseRecyclerViewHolder) holder).setData( this.data.get(position) );
        if (this.onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChatRecyclerViewAdapter.this.onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    public  void setOnItemClickListner(OnItemClickListener onItemClickListner){
        this.onItemClickListener=onItemClickListner;
    }

    private class AViewHolder extends BaseRecyclerViewHolder<MsgPO> {

        public BaseCircleImageView imageView;
        public TextView nickname_TV;
        public TextView datetime_TV;
        public TextView content_TV;

        public AViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.chat_A_head);
            datetime_TV = itemView.findViewById(R.id.chat_A_datetime);
            nickname_TV = itemView.findViewById(R.id.chat_A_nickname);
            content_TV = itemView.findViewById(R.id.chat_A_content);
        }

        @Override
        public void setData(MsgPO msgPO) {
            super.setData(msgPO);
            imageView.setImageUrl(msgPO.getUser().getHeadportraitURL(), new Rect());
            datetime_TV.setText(msgPO.getDatetime());
            nickname_TV.setText(msgPO.getUser().getNickname() + ":");
            content_TV.setText(msgPO.getContent());
        }
    }

    private class BViewHolder extends BaseRecyclerViewHolder<MsgPO> {

        public BaseCircleImageView imageView;
        public TextView nickname_TV;
        public TextView datetime_TV;
        public TextView content_TV;

        public BViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById( R.id.chat_B_head );
            datetime_TV = itemView.findViewById( R.id.chat_B_datetime );
            nickname_TV = itemView.findViewById( R.id.chat_B_nickname );
            content_TV = itemView.findViewById( R.id.chat_B_content );
        }

        @Override
        public void setData(MsgPO msgPO) {
            super.setData(msgPO);
            imageView.setImageUrl(msgPO.getUser().getHeadportraitURL(), new Rect());
            datetime_TV.setText(msgPO.getDatetime());
            nickname_TV.setText(msgPO.getUser().getNickname() + ":");
            content_TV.setText(msgPO.getContent());
        }
    }

    private class CViewHolder extends BaseRecyclerViewHolder<MsgPO> {

        public BaseCircleImageView imageView;
        public TextView nickname_TV;
        public TextView UID_TV;

        public CViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById( R.id.item_list_fragment_mine_profile_CIV_head );
            nickname_TV = itemView.findViewById( R.id.item_list_fragment_mine_profile_TV_nickname );
            UID_TV = itemView.findViewById( R.id.item_list_fragment_mine_profile_TV_uid );
        }

        @Override
        public void setData(MsgPO msgPO) {
            super.setData(msgPO);
            imageView.setImageUrl(msgPO.getUser().getHeadportraitURL(), new Rect(), R.mipmap.ichat_logo_icon);
            nickname_TV.setText("昵称:" + msgPO.getUser().getNickname());
            UID_TV.setText("id:" + msgPO.getUser().getUID());
        }
    }
}
