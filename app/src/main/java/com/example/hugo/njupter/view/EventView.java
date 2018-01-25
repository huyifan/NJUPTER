package com.example.hugo.njupter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.bean.EventBean;
import com.zhy.autolayout.AutoLinearLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by hugo on 2017/3/30.
 */

@EViewGroup(R.layout.view_event)
public class EventView extends AutoLinearLayout {

    @ViewById(R.id.event_date)
    TextView date;
    @ViewById(R.id.event_host)
    TextView host;
    @ViewById(R.id.event_des)
    TextView des;
    @ViewById(R.id.event_title)
    TextView title;
    @ViewById(R.id.view_num)
    TextView viewNum;
    @ViewById(R.id.join_num)
    TextView joinNum;

    public EventView(Context context) {
        super(context);
    }

    public EventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(EventBean eventBean){
        title.setText(eventBean.getTitle());
        host.setText(eventBean.getHost());
        des.setText(eventBean.getDes());
        date.setText(eventBean.getDate());
        joinNum.setText(String.valueOf(eventBean.getnJoin())+"参加");
        viewNum.setText(String.valueOf(eventBean.getnView())+"浏览");


    }
}
