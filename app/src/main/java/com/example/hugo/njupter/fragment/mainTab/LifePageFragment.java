package com.example.hugo.njupter.fragment.mainTab;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.activity.SecondMarketActivity;
import com.example.hugo.njupter.utils.ToastUtil;
import com.example.hugo.njupter.widget.convenientbanner.ConvenientBanner;
import com.example.hugo.njupter.widget.convenientbanner.holder.CBViewHolderCreator;
import com.example.hugo.njupter.widget.convenientbanner.holder.ImageHolder;
import com.example.hugo.njupter.widget.convenientbanner.listener.OnItemClickListener;
import com.shizhefei.view.indicator.BannerComponent;

import net.frakbot.jumpingbeans.JumpingBeans;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LifePageFragment extends Fragment implements View.OnClickListener {

    private LinearLayout lostLy;

    private LinearLayout secondLy;

    private LinearLayout actLy;
    //顶部滑动的Banner
//    public ConvenientBanner convenientBanner;
    private BannerComponent bannerComponent;
    private List<Integer> images;

    private int[] imageRes = {R.drawable.pic_5, R.drawable.pic_4, R.drawable.pic_3, R.drawable.pic_2, R.drawable.pic_1};
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

        images = new ArrayList<>();
        images.add(R.drawable.pic_5);
        images.add(R.drawable.pic_4);
        images.add(R.drawable.pic_3);
        images.add(R.drawable.pic_2);
        images.add(R.drawable.pic_1);

        CBViewHolderCreator<ImageHolder> creator = new CBViewHolderCreator() {

            @Override
            public ImageHolder createHolder() {
                return new ImageHolder();
            }
        };

        convenientBanner.setPages(creator, images)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //每一项的点击事件
                    }
                });


        return content;
    }

    private void initViews(View content) {
        waitTextView = (TextView) content.findViewById(R.id.tv_waiting);
        convenientBanner= (ConvenientBanner) content.findViewById(R.id.banner);
        actLy= (LinearLayout) content.findViewById(R.id.activity_ly);

        actLy.setOnClickListener(this);
        lostLy= (LinearLayout) content.findViewById(R.id.lost_ly);
        lostLy.setOnClickListener(this);
        secondLy= (LinearLayout) content.findViewById(R.id.second_ly);
        secondLy.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lost_ly:
                startActivity(new Intent(getActivity(), SecondMarketActivity.class));
                break;
            case R.id.second_ly:
                ToastUtil.showShortToast(getContext(),"2");
                startActivity(new Intent(getActivity(), SecondMarketActivity.class));
                break;
            case R.id.activity_ly:
                ToastUtil.showShortToast(getContext(),"3");
                break;

        }
    }
}
