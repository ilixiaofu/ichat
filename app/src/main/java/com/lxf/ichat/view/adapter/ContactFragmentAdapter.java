package com.lxf.ichat.view.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.po.UserPO;
import com.lxf.ichat.view.base.BaseCircleImageView;
import com.lxf.ichat.view.base.BaseListAdapter;

import java.util.List;

public class ContactFragmentAdapter extends BaseListAdapter<UserPO> {

    private static final String TAG = ContactFragmentAdapter.class.getName();


    public ContactFragmentAdapter(Context context, List<UserPO> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        try {
            mViewHolder  = (ViewHolder) convertView.getTag();
        } catch (NullPointerException e) {
            convertView = this.layoutInflater.inflate(R.layout.item_list_fragment_contact, null);
            mViewHolder = new ViewHolder();
            mViewHolder.nickname_TV = convertView.findViewById( R.id.item_list_fragment_contract_TV_nickname );
            mViewHolder.status_TV = convertView.findViewById( R.id.item_list_fragment_contract_TV__status );
            mViewHolder.head_CIV = convertView.findViewById( R.id.item_list_fragment_contract_CIV_head );
            convertView.setTag( mViewHolder );
        }

        UserPO userPO = data.get( position );
        mViewHolder.nickname_TV.setText( userPO.getNickname() );
        mViewHolder.status_TV.setText( "[" + userPO.getStatus() + "]");
        mViewHolder.head_CIV.setImageUrl( userPO.getHeadportraitURL(), new Rect(  ));
        return convertView;
    }

    private static class ViewHolder {
        public TextView nickname_TV;
        public TextView status_TV;
        public BaseCircleImageView head_CIV;
    }
}
