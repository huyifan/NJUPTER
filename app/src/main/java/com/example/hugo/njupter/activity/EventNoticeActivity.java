package com.example.hugo.njupter.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.adapter.RecyclerAdapter;
import com.example.hugo.njupter.fragment.ContainFragment;
import com.example.hugo.njupter.fragment.TemplateFragment;
import com.example.hugo.njupter.fragment.TemplateFragment_;
import com.example.hugo.njupter.widget.MyRefreshViewPager;
import com.example.hugo.njupter.widget.MyRefreshViewPager_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 2017/3/20.
 */

@EActivity(R.layout.activity_event_notice)
public class EventNoticeActivity extends BasicActivity{
//    @ViewById(R.id.list_event)
//    public RecyclerView mList;
//    @ViewById(R.id.tv_no_event)
//    public TextView tvNOtice;

    @ViewById(R.id.contain1)
    public RelativeLayout relativeLayout;

    protected RecyclerAdapter adapter;
    private List<TemplateFragment> fragments;

    @AfterViews
    public void initViews(){
        setToolbarleftImg(R.id.tool_bar_menu,R.drawable.ic_yugao);
        setToolBarText(R.id.header_title,"活动预告");
        setBackImg(R.id.title_back);
        //mList.setVisibility(View.GONE);



        List<String> titles=new ArrayList<>();

        titles.add("往期活动");
        titles.add("近期活动");
        titles.add("我的参与");

        TemplateFragment containFragment1=TemplateFragment_.builder().flag("活动预告").cid("1").build();
        TemplateFragment containFragment2=TemplateFragment_.builder().flag("活动预告").cid("2").build();
        TemplateFragment containFragment3= TemplateFragment_.builder().flag("活动预告").cid("3").build();

        fragments=new ArrayList<>();
        fragments.add(containFragment1);
        fragments.add(containFragment2);
        fragments.add(containFragment3);

        MyRefreshViewPager viewPager= MyRefreshViewPager_.build(EventNoticeActivity.this,null,titles,getSupportFragmentManager(),fragments);

        viewPager.setFragments(fragments);
        viewPager.setFragmentManager(getSupportFragmentManager());
        viewPager.setTitles(titles);
        viewPager.setCurrentPage(1);
        relativeLayout.addView(viewPager);

    }

}
