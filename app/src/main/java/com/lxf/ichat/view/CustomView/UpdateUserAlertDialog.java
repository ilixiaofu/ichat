package com.lxf.ichat.view.CustomView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.lxf.ichat.R;
import com.lxf.ichat.view.viewholder.UpdateUserAlertDialogViewHolder;

public class UpdateUserAlertDialog extends AlertDialog {

    private LayoutInflater layoutInflater;
    public UpdateUserAlertDialogViewHolder mViewHolder;

    public UpdateUserAlertDialog(@NonNull Context context) {
        super(context);
        this.layoutInflater = LayoutInflater.from(context);
        View view = this.layoutInflater.inflate( R.layout.alert_dialog_update_user, null );
        mViewHolder = new UpdateUserAlertDialogViewHolder(view);
        this.setView(view);
    }

    public UpdateUserAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
