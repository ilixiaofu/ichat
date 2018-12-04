package com.lxf.ichat.view.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class UpdateUserAlertDialogViewHolder extends BaseViewHolder {

    public TextView title_tv;
    public EditText content_et;
    public Button ok_btn;
    public Button cancel_btn;

    public UpdateUserAlertDialogViewHolder(View view) {
        super(view);
        this.title_tv = view.findViewById(R.id.alert_dialog_update_user_TV_title);
        this.content_et = view.findViewById(R.id.alert_dialog_update_user_ET_content);
        this.ok_btn = view.findViewById(R.id.alert_dialog_update_user_BTN_ok);
        this.cancel_btn = view.findViewById(R.id.alert_dialog_update_user_BTN_cancel);
    }
}
