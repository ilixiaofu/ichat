package com.lxf.ichat.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.po.UserSettingPO;
import com.lxf.ichat.view.base.BaseListAdapter;

import java.util.List;

public class UserSettingAdapter extends BaseListAdapter<UserSettingPO> {

    private static final String TAG = UserSettingAdapter.class.getName();

    public UserSettingAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder;
        try {
            mViewHolder = (ViewHolder) view.getTag();
        } catch (NullPointerException e) {
            view = this.layoutInflater.inflate( R.layout.item_list_user_setting, null );
            mViewHolder = new ViewHolder();
            mViewHolder.textView = view.findViewById( R.id.item_list_user_setting_TV );
            view.setTag( mViewHolder );
        }
        UserSettingPO userSettingPO = this.data.get(position);
        mViewHolder.textView.setText(userSettingPO.getName());
        return view;
    }

    private static class ViewHolder {
        public TextView textView;
    }
}
