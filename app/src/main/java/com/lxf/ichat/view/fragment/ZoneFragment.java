package com.lxf.ichat.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.viewholder.ZoneFragmentViewHolder;


public class ZoneFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = ZoneFragment.class.getName();
    private static ZoneFragment mZoneFragment;
    private ZoneFragmentViewHolder mViewHolder;

    public ZoneFragment() {
    }


    public static ZoneFragment getInstance() {
        if (mZoneFragment == null) {
            synchronized (ZoneFragment.class) {
                if (mZoneFragment == null) {
                    mZoneFragment = new ZoneFragment();
                }
            }
        }
        return mZoneFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_zone, container, false);
        init(view);
        return view;
    }


    private void init(View v) {
        Log.i(TAG, "init: ");
        mViewHolder = new ZoneFragmentViewHolder(v);
        mViewHolder.listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
