package com.example.hugo.njupter.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by hugo on 2017/2/10.
 */

public class MyBasicVIewPager extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
