package com.lxf.ichat.view.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.po.MineFragmentFunPO;
import com.lxf.ichat.view.base.BaseCircleImageView;
import com.lxf.ichat.view.base.BaseListAdapter;

import java.util.List;

public class MineFragmentFunAdapter extends BaseListAdapter<MineFragmentFunPO> {

    private static final String TAG = MineFragmentFunAdapter.class.getName();

    public MineFragmentFunAdapter(Context context, List<MineFragmentFunPO> data) {
        super(context, data);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        try {
            mViewHolder  = (ViewHolder) convertView.getTag();
        } catch (NullPointerException e) {
            convertView = this.layoutInflater.inflate(R.layout.item_list_fragment_mine_other, null);
            mViewHolder = new ViewHolder();
            mViewHolder.icon_CIV = convertView.findViewById( R.id.item_list_fragment_mine_other_CIV_icon );
            mViewHolder.textView = convertView.findViewById( R.id.item_list_fragment_mine_other_TV );
            convertView.setTag( mViewHolder );
        }

        MineFragmentFunPO mineFragmentFunPO = data.get( position );
        mViewHolder.icon_CIV.setImageUrl(mineFragmentFunPO.getIconURL(), new Rect(), R.mipmap.setting);
        mViewHolder.textView.setText( mineFragmentFunPO.getName() );
        return convertView;
    }

    private static class ViewHolder {
        public TextView textView;
        public BaseCircleImageView icon_CIV;
    }
}
