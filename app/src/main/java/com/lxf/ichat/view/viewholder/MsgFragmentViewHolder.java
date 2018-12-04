package com.lxf.ichat.view.viewholder;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class MsgFragmentViewHolder extends BaseViewHolder {

    public TextView title_TV;
    public ListView listView;

    public MsgFragmentViewHolder(View view) {
        super(view);
        title_TV = view.findViewById( R.id.fragment_msg_TV_title );
        listView = view.findViewById( R.id.fragment_msg_LV);
    }
}
