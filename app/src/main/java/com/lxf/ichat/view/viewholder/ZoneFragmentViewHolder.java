package com.lxf.ichat.view.viewholder;

import android.view.View;
import android.widget.ListView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class ZoneFragmentViewHolder extends BaseViewHolder {

    public ListView listView;

    public ZoneFragmentViewHolder(View view) {
        super(view);
        listView = view.findViewById( R.id.fragment_settings_LV );
    }
}
