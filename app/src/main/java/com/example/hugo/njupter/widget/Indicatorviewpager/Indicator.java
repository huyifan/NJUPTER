package com.example.hugo.njupter.widget.Indicatorviewpager;

import android.view.View;

/**
 * Created by hugo on 2017/2/9.
 * 指示器
 */

public interface Indicator  {
    public  void setAdapter(IndicatorAdapter adapter);

    /**
     * 设置选中监听
     *
     * @param onItemSelectedListener
     */
    public void setOnItemSelectListener(OnItemSelectedListener onItemSelectedListener);


    /**
     * ViewPager切换变化的函数
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    /**
     * tab项选中监听
     */
    public  interface OnItemSelectedListener {
        /**
         * 注意 preItem 可能为 -1。表示之前没有选中过,每次adapter.notifyDataSetChanged也会将preItem
         * 设置为-1；
         *
         * @param selectItemView 当前选中的view
         * @param select         当前选中项的索引
         * @param preSelect      之前选中项的索引
         */
        void onItemSelected(View selectItemView, int select, int preSelect);
    }

    /**
     * 设置当前项.<br>
     * 如果使用IndicatorViewPager则使用IndicatorViewPager.setCurrentItem而不是在调用该方法
     *
     * @param item
     */
    public void setCurrentItem(int item);

    public void setCurrentItem(int item, boolean anim);

    /**
     * 获取之前选中的项,可能返回-1，表示之前没有选中
     *
     * @return PreSelectItem
     */
    public int getPreSelectItem();

    /**
     * tab滑动变化的转换监听，tab在切换过程中会调用此监听。<br>
     * 通过它可以自定义实现在滑动过程中，tab项的字体变化，颜色变化等等效果<br>
     * 目前提供的子类
     *
     */
    public  interface OnTransitionListener {
        void onTransition(View view, int position, float selectPercent);
    }

    public void onPageScrollStateChanged(int state);

    public void setItemClickable(boolean clickable);

    public boolean isItemClickable();
}
