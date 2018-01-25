package com.example.hugo.njupter.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.hugo.njupter.bean.ULocation;
import com.example.hugo.njupter.bean.User;
import com.example.hugo.njupter.view.NearbyPeopleView;
import com.example.hugo.njupter.view.NearbyPeopleView_;

import java.util.List;

/**
 * Created by hugo on 2017/3/31.
 */

public class NearUserAdapter extends RecyclerAdapter<ULocation> {

    public NearUserAdapter(List<ULocation> list, View emptyView) {
        super(list, emptyView);
    }

    @Override
    public View onCreateView(ViewGroup parent, int viewType) {

        return NearbyPeopleView_.build(parent.getContext(),null);
    }


    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        ((NearbyPeopleView)holder.itemView).setData(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void update(List<ULocation> data) {
        list.clear();
        if (data != null && data.size() > 0) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }
}
