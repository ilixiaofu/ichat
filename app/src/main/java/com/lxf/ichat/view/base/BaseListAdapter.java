package com.lxf.ichat.view.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class BaseListAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> data;
    protected LayoutInflater layoutInflater;

    public BaseListAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
