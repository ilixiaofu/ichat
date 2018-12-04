package com.lxf.ichat.view.viewholder;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class GroupChatActivityViewHolder extends BaseViewHolder {

    public TextView back_TV;
    public TextView title_TV;
    public Button send_BTN;
    public EditText input_ET;
    public RecyclerView recyclerView;

    public GroupChatActivityViewHolder(AppCompatActivity activity) {
        super(activity);
        back_TV = activity.findViewById(R.id.group_chat_TV_back);
        title_TV = activity.findViewById(R.id.group_chat_TV_title);
        send_BTN = activity.findViewById(R.id.group_chat_BTN_send);
        input_ET = activity.findViewById(R.id.group_chat_ET_input);
        recyclerView = activity.findViewById(R.id.group_chat_RV);
    }
}
