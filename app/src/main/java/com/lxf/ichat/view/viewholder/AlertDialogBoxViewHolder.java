package com.lxf.ichat.view.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class AlertDialogBoxViewHolder extends BaseViewHolder {

    public TextView tipinfo_tv;
    public Button ok_btn;
    public Button cancel_btn;

    public AlertDialogBoxViewHolder(View view) {
        super(view);
        this.tipinfo_tv = view.findViewById(R.id.alert_dialog_common_TV_title);
        this.ok_btn = view.findViewById(R.id.alert_dialog_common_BTN_ok);
        this.cancel_btn = view.findViewById(R.id.alert_dialog_common_BTN_cancel);
    }
}
