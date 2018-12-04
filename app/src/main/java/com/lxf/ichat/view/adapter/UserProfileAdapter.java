package com.lxf.ichat.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseListAdapter;

import java.util.List;
import java.util.Map;

public class UserProfileAdapter extends BaseListAdapter<Map<String, String>> {

    private static final String TAG = UserProfileAdapter.class.getName();

    public UserProfileAdapter(Context context, List<Map<String, String>> data) {
        super(context, data);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        try {
            mViewHolder = (ViewHolder) convertView.getTag();
        } catch (NullPointerException e) {
            convertView = this.layoutInflater.inflate( R.layout.item_list_user_profile, null );
            mViewHolder = new ViewHolder();
            mViewHolder.key_TV = convertView.findViewById( R.id.item_list_user_profile_TV_key );
            mViewHolder.value_TV = convertView.findViewById( R.id.item_list_user_profile_TV_value );
            convertView.setTag( mViewHolder );
        }
        Map<String, String> map = data.get( position );
        mViewHolder.key_TV.setText( map.get( "key" ) );
        mViewHolder.value_TV.setText( map.get( "value" ) );
        return convertView;
    }

    private static class ViewHolder {
        public TextView key_TV;
        public TextView value_TV;
    }
}
