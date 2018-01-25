package com.example.hugo.njupter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Shine on 2016/4/18.
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final View emptyView;
    public List<T> list;

    public RecyclerAdapter(List<T> list, View emptyView) {
        this.list = list;
        this.emptyView = emptyView;
    }
    public void setList(List<T> list){
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = onCreateView(parent, viewType);
        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        int count = list == null ? 0 : list.size();
        if (count == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);

        }
        return count;
    }

    public void update(List<T> data) {
        list.clear();
        if (data != null && data.size() > 0) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    public abstract View onCreateView(ViewGroup parent, int viewType);

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
