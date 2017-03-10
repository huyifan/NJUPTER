package com.example.hugo.njupter.widget.Indicatorviewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by hugo on 2017/2/9.
 * 将indicatorView，ViewPager联合使用
 */

public class IndicatorViewPager {
    protected Indicator indicatorView;
    protected ViewPager viewPager;
    private IndicatorPagerAdapter adapter;
    protected OnIndicatorPageChangeListener onIndicatorPageChangeListener;
    private boolean anim = true;

    public IndicatorViewPager(Indicator indicator, ViewPager viewPager) {
        this(indicator, viewPager, true);
    }

    public IndicatorViewPager(Indicator indicator, ViewPager viewPager, boolean indicatorClickable) {
        super();
        this.indicatorView = indicator;
        this.viewPager = viewPager;
        indicator.setItemClickable(indicatorClickable);
        iniOnItemSelectedListener();
        iniOnPageChangeListener();
    }


    protected void iniOnItemSelectedListener() {
        indicatorView.setOnItemSelectListener(new Indicator.OnItemSelectedListener() {

            @Override
            public void onItemSelected(View selectItemView, int select, int preSelect) {
                if (viewPager instanceof SViewPager) {
                    viewPager.setCurrentItem(select, ((SViewPager) viewPager).isCanScroll());
                } else {
                    viewPager.setCurrentItem(select, anim);
                }
            }
        });
    }

    protected void iniOnPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                indicatorView.setCurrentItem(position, true);
                if (onIndicatorPageChangeListener != null) {
                    onIndicatorPageChangeListener.onIndicatorPageChange(indicatorView.getPreSelectItem(), position);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                indicatorView.onPageScrollStateChanged(state);
            }
        });
    }

    public interface IndicatorPagerAdapter {

        PagerAdapter getPagerAdapter();

        IndicatorAdapter getIndicatorAdapter();

        void notifyDataSetChanged();

    }

    public IndicatorPagerAdapter getAdapter() {
        return this.adapter;
    }

    static abstract class LoopAdapter implements IndicatorPagerAdapter {

        abstract int getRealPosition(int position);

        abstract void setLoop(boolean loop);

        abstract int getCount();
    }


    public interface OnIndicatorPageChangeListener {
        /**
         * 注意 preItem 可能为 -1。表示之前没有选中过,每次adapter.notifyDataSetChanged也会将preItem
         * 设置为-1；
         *
         * @param preItem
         * @param currentItem
         */
        void onIndicatorPageChange(int preItem, int currentItem);
    }

}
