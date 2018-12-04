package com.lxf.ichat.view.viewholder;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class FriendProfileActivityViewHolder extends BaseViewHolder {

    public Button back_BTN;
    public Button sendMsg_BTN;
    public ListView listView;

    public FriendProfileActivityViewHolder(AppCompatActivity activity) {
        super(activity);
        back_BTN = activity.findViewById( R.id.friend_profile_BTN_back );
        sendMsg_BTN = activity.findViewById( R.id.friend_profile_BTN_send_msg );
        listView = activity.findViewById( R.id.friend_profile_LV );
    }
}
