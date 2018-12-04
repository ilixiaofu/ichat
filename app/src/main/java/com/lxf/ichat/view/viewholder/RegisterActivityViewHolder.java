package com.lxf.ichat.view.viewholder;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class RegisterActivityViewHolder extends BaseViewHolder {

    public Button back_BTN;
    public Button regist_BTN;

    public RadioButton male_RBTN;
    public RadioButton female_RBTN;

    public EditText UID_ET;
    public EditText nickname_ET;
    public EditText PASWD_ET;
    public EditText CFPWD_ET;


    public RegisterActivityViewHolder(AppCompatActivity activity) {
        super(activity);
        back_BTN = activity.findViewById( R.id.register_BTN_back );
        regist_BTN = activity.findViewById( R.id.register_BTN_regist );
        male_RBTN = activity.findViewById( R.id.register_RB_male );
        female_RBTN = activity.findViewById( R.id.register_RB_female );
        UID_ET = activity.findViewById( R.id.register_ET_UID);
        nickname_ET = activity.findViewById( R.id.register_ET_nickname );
        PASWD_ET = activity.findViewById( R.id.register_ET_PASWD );
        CFPWD_ET = activity.findViewById( R.id.register_ET_CFPWD );
    }
}
