package com.example.hugo.njupter.activity;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.fragment.TemplateFragment;
import com.example.hugo.njupter.fragment.TemplateFragment_;
import com.example.hugo.njupter.interf.OnRefreshListener;
import com.example.hugo.njupter.widget.MyViewPager;
import com.example.hugo.njupter.widget.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class SecondMarketActivity extends BasicActivity {
    private final String TAG="-SecondMarketActivity-";
    private final int downEv=MotionEvent.ACTION_DOWN;
    private final int moveEv=MotionEvent.ACTION_MOVE;

    private MyViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private List<TemplateFragment> fragments;
    private PagerTabStrip pagerTabStrip;
    private List<String> titleLists;
    private float downY;
    private float upY;
    private OnRefreshListener listener;
    private FloatingActionButton btAddSec;
    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_market);
        initView();
        setToolbarleftImg(R.id.tool_bar_menu,R.drawable.ic_second);
        setToolBarText(R.id.header_title,"二手市场");
        setBackImg(R.id.title_back);

        myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setCurrentItem(1);
    }

    private void initView() {
        setBackImg(R.id.title_back);

        titleLists=new ArrayList<>();
        fragments=new ArrayList<>();

      //  mSwipeLayout= (MySwipeRefreshLayout) findViewById(R.id.refresh_layout);
        viewPager= (MyViewPager) findViewById(R.id.lost_view_page);
        pagerTabStrip= (PagerTabStrip) findViewById(R.id.my_pager_tab_strip);
        btAddSec= (FloatingActionButton) findViewById(R.id.bt_add_second);
        btAddSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentContainerActivity_.intent(SecondMarketActivity.this).page("add_second").start();
            }
        });
        //   mSwipeLayout.setOnRefreshListener(this)
        // mSwipeLayout.setTouchSlop(200);
       //viewPager.setNestParent();

        pagerTabStrip.setTabIndicatorColorResource(R.color.colorWhite);

        titleLists.add("生活用品");
        titleLists.add("图书资料");
        titleLists.add("数码产品");
        titleLists.add("其他物品");
        TemplateFragment templateFragment1= TemplateFragment_.builder().flag("二手市场").cid("生活用品").build();
        TemplateFragment templateFragment2= TemplateFragment_.builder().flag("二手市场").cid("图书资料").build();
        TemplateFragment templateFragment3 = TemplateFragment_.builder().flag("二手市场").cid("数码产品").build();
        TemplateFragment templateFragment4 = TemplateFragment_.builder().flag("二手市场").cid("其他物品").build();
        fragments.add(templateFragment1);
        fragments.add(templateFragment2);
        fragments.add(templateFragment3);
        fragments.add(templateFragment4);

    }


    public class MyPagerAdapter extends FragmentPagerAdapter{
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(titleLists.get(position)); // space added before text
            ForegroundColorSpan fcs = new ForegroundColorSpan(Color.WHITE);// 字体颜色设置为绿色
            ssb.setSpan(fcs, 0, ssb.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);// 设置字体颜色
            return ssb;
        }

    }
}
