package com.lxf.ichat.view.viewholder;

import android.support.v7.app.AppCompatActivity;

import com.github.snowdream.android.widget.SmartImageView;
import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class MainActivityViewHolder extends BaseViewHolder {

    public SmartImageView msg_SIV;
    public SmartImageView contact_SIV;
    public SmartImageView mine_SIV;
//    public SmartImageView setting_SIV;

    public MainActivityViewHolder(AppCompatActivity activity) {
        super(activity);
        msg_SIV = activity.findViewById( R.id.main_SIV_msg );
        contact_SIV = activity.findViewById( R.id.main_SIV_contact);
        mine_SIV = activity.findViewById( R.id.main_SIV_mine );
//        setting_SIV = activity.findViewById( R.id.main_SIV_setting );
    }
}
