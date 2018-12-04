package com.lxf.ichat.view.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class ChangePasswordAlertDialogViewHolder extends BaseViewHolder {

    public TextView title_tv;
    public EditText oldPWD_et;
    public EditText newPWD_et;
    public EditText CFPWD_et;
    public Button ok_btn;
    public Button cancel_btn;

    public ChangePasswordAlertDialogViewHolder(View view) {
        super(view);
        this.title_tv = view.findViewById(R.id.alert_dialog_change_password_TV_title);
        this.oldPWD_et = view.findViewById(R.id.alert_dialog_change_password_ET_OLDPASWD);
        this.newPWD_et = view.findViewById(R.id.alert_dialog_change_password_ET_NEWPWD);
        this.CFPWD_et = view.findViewById(R.id.alert_dialog_change_password_ET_CFPWD);
        this.ok_btn = view.findViewById(R.id.alert_dialog_change_password_BTN_ok);
        this.cancel_btn = view.findViewById(R.id.alert_dialog_change_password_BTN_cancel);
    }
}
