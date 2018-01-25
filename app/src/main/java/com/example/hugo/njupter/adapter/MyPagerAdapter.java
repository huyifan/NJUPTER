package com.example.hugo.njupter.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.example.hugo.njupter.fragment.ContainFragment;
import com.example.hugo.njupter.fragment.TemplateFragment;

import java.util.List;

/**
 * Created by hugo on 2017/3/26.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<TemplateFragment> fragments;
    private List<String> titleLists;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<TemplateFragment> fragments) {
        this.fragments = fragments;
    }

    public void setTitleLists(List<String> titleLists) {
        this.titleLists = titleLists;
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