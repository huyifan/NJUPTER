package com.example.hugo.njupter.activity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.fragment.ContainFragment;
import com.example.hugo.njupter.widget.DepthPageTransformer;
import com.example.hugo.njupter.widget.MyViewPager;
import com.example.hugo.njupter.widget.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class SecondMarketActivity extends BasicActivity implements SwipeRefreshLayout.OnRefreshListener  {
    private MyViewPager viewPager;
    private SwipeRefreshLayout mSwipeLayout;
    private MyPagerAdapter myPagerAdapter;
    private List<ContainFragment> fragments;
    private PagerTabStrip pagerTabStrip;
    private List<String> titleLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_market);
        initView();
        setToolbarleftImg(R.id.tool_bar_menu,R.drawable.ic_second);
        setToolBarText(R.id.header_title,"二手市场");
        setBackImg(R.id.title_back);
        myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    private void initView() {
        titleLists=new ArrayList<>();
        fragments=new ArrayList<>();
        mSwipeLayout= (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        viewPager= (MyViewPager) findViewById(R.id.lost_view_page);
        pagerTabStrip= (PagerTabStrip) findViewById(R.id.my_pager_tab_strip);
        mSwipeLayout.setOnRefreshListener(this);

        pagerTabStrip.setTabIndicatorColorResource(R.color.colorWhite);

//        titleLists.add("饭卡");
//        titleLists.add("图书资料");
//        titleLists.add("数码产品");
//        titleLists.add("其他遗失");

        titleLists.add("生活用品");
        titleLists.add("图书资料");
        titleLists.add("数码产品");
        titleLists.add("其他物品");

        ContainFragment containFragment1=new ContainFragment("我是第1个页面");
        ContainFragment containFragment2=new ContainFragment("我是第2个页面");
        ContainFragment containFragment3=new ContainFragment("我是第3个页面");
        ContainFragment containFragment4=new ContainFragment("我是第4个页面");


        fragments.add(containFragment1);
        fragments.add(containFragment2);
        fragments.add(containFragment3);
        fragments.add(containFragment4);



    }


    @Override
    public void onRefresh() {
        Toast.makeText(SecondMarketActivity.this,"refresh ",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 停止刷新
                mSwipeLayout.setRefreshing(false);
            }
        }, 5000); // 5秒后发送消息，停止刷新
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
