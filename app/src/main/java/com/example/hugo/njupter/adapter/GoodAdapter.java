package com.example.hugo.njupter.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.hugo.njupter.bean.GoodBean;
import com.example.hugo.njupter.view.GoogsView;
import com.example.hugo.njupter.view.GoogsView_;


import java.util.List;

/**
 * Created by hugo on 2017/3/10.
 */

public class GoodAdapter  extends RecyclerAdapter<GoodBean> {

    public GoodAdapter(List<GoodBean> list, View emptyView) {
        super(list, emptyView);
    }


    public View onCreateView(ViewGroup parent, int viewType) {
        return GoogsView_.build(parent.getContext(),null);
    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        ((GoogsView)holder.itemView).setData(list.get(position));
//    }


    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        ((GoogsView)holder.itemView).setData(list.get(position));
    }

    public void update(List<GoodBean> data) {
        list.clear();
        if (data != null && data.size() > 0) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }


}
