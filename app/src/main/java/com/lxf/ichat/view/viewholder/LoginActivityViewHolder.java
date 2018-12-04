package com.lxf.ichat.view.viewholder;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class LoginActivityViewHolder extends BaseViewHolder {

    public EditText UID_ET;
    public EditText PWD_ET;
    public CheckBox keepPwd_CB;
    public CheckBox autoLogin_CB;
    public TextView FPWD_TV;
    public TextView newUser_TV;
    public Button login_BTN;

    public LoginActivityViewHolder(AppCompatActivity activity) {
        super(activity);
        this.UID_ET = activity.findViewById(R.id.login_ET_UID);
        this.PWD_ET = activity.findViewById(R.id.login_ET_password);
        this.keepPwd_CB = activity.findViewById(R.id.login_CB_keep_pwd);
        this.autoLogin_CB = activity.findViewById(R.id.login_CB_auto_login);
        this.FPWD_TV = activity.findViewById(R.id.login_TV_FPWD);
        this.newUser_TV = activity.findViewById(R.id.login_TV_new_user);
        this.login_BTN = activity.findViewById(R.id.login_BTN_login);
    }
}
