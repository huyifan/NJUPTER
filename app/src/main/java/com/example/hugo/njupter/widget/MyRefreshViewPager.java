package com.example.hugo.njupter.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.adapter.MyPagerAdapter;
import com.example.hugo.njupter.fragment.ContainFragment;
import com.example.hugo.njupter.fragment.TemplateFragment;
import com.rey.material.widget.TabPageIndicator;
import com.zhy.autolayout.AutoLinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by hugo on 2017/3/26.
 */
@EViewGroup(R.layout.view_refresh_pager)
public class MyRefreshViewPager extends AutoLinearLayout implements SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;

    private FragmentManager fragmentManager;

    @ViewById(R.id.m_pager_tab_strip)
    public PagerTabStrip pagerTabStrip;


    @ViewById(R.id.m_view_page)
    public MyViewPager viewPager;

    private List<TemplateFragment> fragments;
    private List<String> titles;
    private MyPagerAdapter myPagerAdapter;

    public MyRefreshViewPager(Context context) {
        super(context);
        mContext=context;
    }

    public MyRefreshViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    public MyRefreshViewPager(Context context, AttributeSet attrs,List<String> titles,
                              FragmentManager manager,List<TemplateFragment> fragments) {
        super(context, attrs);
        mContext=context;
        this.titles=titles;
        fragmentManager=manager;
        this.fragments=fragments;
    }

    @AfterViews
    public void initViews(){
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        pagerTabStrip.setTabIndicatorColorResource(R.color.colorWhite);

        myPagerAdapter=new MyPagerAdapter(fragmentManager);
        myPagerAdapter.setTitleLists(titles);
        myPagerAdapter.setFragments(fragments);
        viewPager.setAdapter(myPagerAdapter);

    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public void setFragments(List<TemplateFragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public void onRefresh() {

    }

    public void setCurrentPage(int i){
        viewPager.setCurrentItem(i);
    }


}

