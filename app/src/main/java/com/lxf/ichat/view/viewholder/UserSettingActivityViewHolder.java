package com.lxf.ichat.view.viewholder;

import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class UserSettingActivityViewHolder extends BaseViewHolder {

    public TextView back_TV;
    public ListView listView;

    public UserSettingActivityViewHolder(AppCompatActivity activity) {
        super(activity);
        back_TV = activity.findViewById(R.id.activity_user_setting_TV_back);
        listView = activity.findViewById(R.id.activity_user_setting_LV);
    }
}
