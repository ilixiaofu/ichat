package com.lxf.ichat.view.viewholder;

import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class UserProfileActivityViewHolder extends BaseViewHolder {

    public TextView back_TV;
    public ListView listView;

    public UserProfileActivityViewHolder(AppCompatActivity activity) {
        super(activity);
        back_TV = activity.findViewById(R.id.activity_user_profile_TV_back);
        listView = activity.findViewById(R.id.activity_user_profile_LV);
    }
}
