package com.lxf.ichat.view.CustomView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.lxf.ichat.R;
import com.lxf.ichat.view.viewholder.SelectSexAlertDialogViewHolder;

public class SelectSexAlertDialog extends AlertDialog {

    private LayoutInflater layoutInflater;
    public SelectSexAlertDialogViewHolder mViewHolder;

    public SelectSexAlertDialog(@NonNull Context context) {
        super(context);
        this.layoutInflater = LayoutInflater.from(context);
        View view = this.layoutInflater.inflate( R.layout.alert_dialog_select_sex, null );
        mViewHolder = new SelectSexAlertDialogViewHolder(view);
        this.setView(view);
    }

    public SelectSexAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
