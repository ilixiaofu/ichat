package com.lxf.ichat.view.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {

    protected Context context;
    protected List<T> data;
    protected LayoutInflater layoutInflater;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;

    public BaseRecyclerViewAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {

        void onItemLongClick(View view, int position);
    }
}
