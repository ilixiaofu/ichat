package com.lxf.ichat.view.viewholder;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseViewHolder;

public class SearchUserActivityViewHolder extends BaseViewHolder {

    public Button search_BTN;
    public Button add_BTN;
    public Button back_BTN;
    public EditText UID_ET;
    public ListView listView;

    public SearchUserActivityViewHolder(AppCompatActivity activity) {
        super(activity);
        search_BTN = activity.findViewById( R.id.search_user_BTN_search );
        add_BTN = activity.findViewById( R.id.search_user_BTN_add );
        back_BTN = activity.findViewById( R.id.search_user_BTN_back );
        UID_ET = activity.findViewById( R.id.search_user_ET_UID);
        listView = activity.findViewById( R.id.search_user_LV );
    }
}
