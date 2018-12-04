package com.lxf.ichat.view.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class SelectSexAlertDialogViewHolder extends BaseViewHolder {

    public TextView title_tv;
    public RadioButton male_rb;
    public RadioButton female_rb;
    public Button ok_btn;
    public Button cancel_btn;

    public SelectSexAlertDialogViewHolder(View view) {
        super(view);
        this.title_tv = view.findViewById(R.id.alert_dialog_select_sex_TV_title);
        this.male_rb = view.findViewById(R.id.alert_dialog_select_sex_RB_male);
        this.female_rb = view.findViewById(R.id.alert_dialog_select_sex_RB_female);
        this.ok_btn = view.findViewById(R.id.alert_dialog_select_sex_BTN_ok);
        this.cancel_btn = view.findViewById(R.id.alert_dialog_select_sex_BTN_cancel);
    }
}
