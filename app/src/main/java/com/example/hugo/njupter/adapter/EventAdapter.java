package com.example.hugo.njupter.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.hugo.njupter.bean.EventBean;
import com.example.hugo.njupter.view.EventView;
import com.example.hugo.njupter.view.EventView_;


import java.util.List;

/**
 * Created by hugo on 2017/3/30.
 */

public class EventAdapter extends  RecyclerAdapter<EventBean> {

    public EventAdapter(List<EventBean> list, View emptyView) {
        super(list, emptyView);
    }

    @Override
    public View onCreateView(ViewGroup parent, int viewType) {
        return EventView_.build(parent.getContext(),null);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        ((EventView)holder.itemView).setData(list.get(position));
    }

    public void update(List<EventBean> data) {
        list.clear();
        if (data != null && data.size() > 0) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }
}
