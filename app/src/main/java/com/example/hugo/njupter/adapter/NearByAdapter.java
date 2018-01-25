package com.example.hugo.njupter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.hugo.njupter.bean.ULocation;
import com.example.hugo.njupter.utils.TimeUtils;
import com.example.hugo.njupter.view.NearbyPeopleView;
import com.example.hugo.njupter.view.NearbyPeopleView_;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.List;

/**
 * Created by hugo on 2017/4/15.
 */

public class NearByAdapter extends BaseAdapter {
    private List<ULocation> locations;
    private NearbyPeopleView nearbyPeopleView;
    private Context context;
    public NearByAdapter(Context context,List<ULocation> locations) {
        this.locations = locations;
        this.context=context;
    }

    public List<ULocation> getLocations() {
        return locations;
    }

    public void setLocations(List<ULocation> locations) {
        this.locations = locations;
    }

    @Override
    public int getCount() {
        if(locations!=null)
        return locations.size();
        else
            return 0;
    }

    public void update(List<ULocation> data) {
        locations.clear();
        if (data != null && data.size() > 0) {
            locations.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            NearbyPeopleView view=NearbyPeopleView_.build(context,null);
            convertView=view;
            viewHolder.ageTv=view.getAgeTv();
            viewHolder.amTv=view.getAmTv();
            viewHolder.distTv=view.getDistTv();
            viewHolder.dongtai=view.getDongtai();
            viewHolder.imgUser=view.getImgUser();
            viewHolder.nameTv=view.getNameTv();
            viewHolder.timeTv=view.getTimeTv();
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.amTv.setText(locations.get(position).getAm());
        viewHolder.dongtai.setText(locations.get(position).getDongTai());
        viewHolder.timeTv.setText(TimeUtils.getRecentTime(Long.parseLong(locations.get(position).getOnTime())));
        viewHolder.nameTv.setText(locations.get(position).getNickName());
        viewHolder.distTv.setText(locations.get(position).getDistance()+"米");
        viewHolder.ageTv.setText(locations.get(position).getAge());
        viewHolder.dongtai.setText(locations.get(position).getDongTai());

        return convertView;
    }

    public class ViewHolder{
        TextView ageTv;
        TextView distTv;
        TextView nameTv;
        TextView timeTv;
        TextView dongtai;
        TextView amTv;
        RoundedImageView imgUser;
    }
}
