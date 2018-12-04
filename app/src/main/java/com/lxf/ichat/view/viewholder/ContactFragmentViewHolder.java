package com.lxf.ichat.view.viewholder;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lxf.ichat.R;
import com.lxf.ichat.view.base.BaseCircleImageView;
import com.lxf.ichat.view.base.BaseViewHolder;

public class ContactFragmentViewHolder extends BaseViewHolder {

    public BaseCircleImageView head_CIV;
    public TextView title_TV;
    public TextView addContact_TV;
    public ListView listView;

    public ContactFragmentViewHolder(View view) {
        super(view);
        head_CIV = view.findViewById( R.id.fragment_contract__CIV_head);
        title_TV = view.findViewById( R.id.fragment_contract_TV_title );
        addContact_TV = view.findViewById( R.id.fragment_contract_TV_add_contact);
        listView = view.findViewById( R.id.fragment_contract_LV);
    }
}
