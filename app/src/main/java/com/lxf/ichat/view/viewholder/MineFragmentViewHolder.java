package com.lxf.ichat.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class MineFragmentViewHolder extends BaseViewHolder {

    public RecyclerView recyclerView;
    public ListView listView;

    public MineFragmentViewHolder(View view) {
        super(view);
        recyclerView = view.findViewById( R.id.fragment_mine_RV);
        listView = view.findViewById( R.id.fragment_mine_LV );
    }
}
