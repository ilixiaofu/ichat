package com.lxf.ichat.view.CustomView;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lxf.ichat.R;

public class MessageBox {

    public static void showMessage(Context context, CharSequence message) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.toast_layout, null);
        TextView textView = view.findViewById(R.id.toast_layout_TV_msg);
        textView.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
