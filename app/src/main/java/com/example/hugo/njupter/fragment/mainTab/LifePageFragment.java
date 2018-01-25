package com.example.hugo.njupter.fragment.mainTab;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.hugo.njupter.R;
import com.example.hugo.njupter.activity.EventNoticeActivity_;
import com.example.hugo.njupter.activity.FragmentContainerActivity_;
import com.example.hugo.njupter.activity.SecondMarketActivity;
import com.example.hugo.njupter.utils.PhotoUtil;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import net.frakbot.jumpingbeans.JumpingBeans;


/**
 * A simple {@link Fragment} subclass.
 */
public class LifePageFragment extends Fragment implements View.OnClickListener {

    private LinearLayout lostLy;

    private LinearLayout secondLy;

    private LinearLayout actLy;

    private LayoutInflater inflate;
    private BannerComponent bannerComponent;
    private ViewPager viewPager;
    private Indicator indicator;
    private int unSelectColor;
   // private List<Integer> images;

    private int[] images = {R.drawable.pic_5, R.drawable.pic_4, R.drawable.pic_3, R.drawable.pic_2, R.drawable.pic_1};
    private TextView waitTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View content = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_life_page,container,false);
        //初始化控件
        initViews(content);

        JumpingBeans jumpingBeans1 = JumpingBeans.with(waitTextView)
                .appendJumpingDots()
                .build();

        return content;
    }

    private void initViews(View content) {
        waitTextView = (TextView) content.findViewById(R.id.tv_waiting);



        actLy= (LinearLayout) content.findViewById(R.id.activity_ly);

        actLy.setOnClickListener(this);
        lostLy= (LinearLayout) content.findViewById(R.id.lost_ly);
        lostLy.setOnClickListener(this);
        secondLy= (LinearLayout) content.findViewById(R.id.second_ly);
        secondLy.setOnClickListener(this);

        ViewPager viewPager = (ViewPager)content.findViewById(R.id.banner_viewPager);
        Indicator indicator = (ScrollIndicatorView) content.findViewById(R.id.banner_indicator);

        viewPager.setOffscreenPageLimit(4);

        inflate = LayoutInflater.from(getContext());

        bannerComponent = new BannerComponent(indicator, viewPager, false);
        bannerComponent.setAdapter(adapter);
        bannerComponent.setAutoPlayTime(2500);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lost_ly:
                FragmentContainerActivity_.intent(getContext()).page("all_lost").start();
                break;
            case R.id.second_ly:
                startActivity(new Intent(getActivity(), SecondMarketActivity.class));
                break;
            case R.id.activity_ly:
                EventNoticeActivity_.intent(getContext()).start();
                break;

        }
    }

    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_guide, container, false);

            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new View(getContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            convertView.setBackground(PhotoUtil.readBitMap2Drawable(getContext(),images[position]));
            return convertView;
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return images.length;
        }
    };

    @Override
    public  void onStart() {
        super.onStart();
        bannerComponent.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        bannerComponent.stopAutoPlay();
    }

}
