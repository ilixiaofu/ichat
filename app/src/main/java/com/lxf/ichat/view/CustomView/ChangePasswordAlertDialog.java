package com.lxf.ichat.view.CustomView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.lxf.ichat.R;
import com.lxf.ichat.view.viewholder.ChangePasswordAlertDialogViewHolder;

public class ChangePasswordAlertDialog extends AlertDialog {

    private LayoutInflater layoutInflater;
    public ChangePasswordAlertDialogViewHolder mViewHolder;

    public ChangePasswordAlertDialog(@NonNull Context context) {
        super(context);
        this.layoutInflater = LayoutInflater.from(context);
        View view = this.layoutInflater.inflate( R.layout.alert_dialog_change_password, null );
        mViewHolder = new ChangePasswordAlertDialogViewHolder(view);
        this.setView(view);
    }

    public ChangePasswordAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
