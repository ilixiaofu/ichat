package com.lxf.ichat.view.CustomView;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogBox extends ProgressDialog {

    public ProgressDialogBox(Context context) {
        super(context);
        // 设置进度条的形式为圆形转动的进度条
        this.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        //设置是否可以通过点击Back键取消
        this.setCancelable(false);

        // 设置在点击Dialog外是否取消Dialog进度条
        this.setCanceledOnTouchOutside(false);

        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        // mProgressDialog.setIcon(R.mipmap.ic_launcher);
    }
}
