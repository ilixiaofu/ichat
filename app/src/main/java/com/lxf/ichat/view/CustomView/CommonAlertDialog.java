package com.lxf.ichat.view.CustomView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.lxf.ichat.R;
import com.lxf.ichat.view.viewholder.AlertDialogBoxViewHolder;

public class CommonAlertDialog extends AlertDialog {

    private LayoutInflater layoutInflater;
    public AlertDialogBoxViewHolder mViewHolder;

    public CommonAlertDialog(@NonNull Context context) {
        super(context);
        this.layoutInflater = LayoutInflater.from(context);
        View view = this.layoutInflater.inflate( R.layout.alert_dialog_common, null );
        mViewHolder = new AlertDialogBoxViewHolder(view);
        this.setView(view);
    }

    public CommonAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
