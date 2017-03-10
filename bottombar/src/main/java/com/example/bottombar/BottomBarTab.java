package com.example.bottombar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hugo on 2017/1/29.
 */

public class BottomBarTab extends LinearLayout {
    private static final long ANIMATION_DURATION = 150;
    private static final float ACTIVE_TITLE_SCALE = 1;
    private static final float INACTIVE_FIXED_TITLE_SCALE = 0.86f;

    private String tabString;
    private int iconResId;
    private AppCompatImageView iconView;
    private TextView titleView;
    private Type type = Type.FIXED;
    private boolean isActive;

    //尺寸
    private final int sixDps;
    private final int eightDps;
    private final int sixteenDps;

    //属性
    private float inActiveAlpha;
    private float activeAlpha;
    private int inActiveColor;
    private int activeColor;
    private int barColorWhenSelected;


    enum Type {
        FIXED, SHIFTING, TABLET
    }




    public BottomBarTab(Context context) {
        super(context);
        sixDps = MiscUtils.dpToPixel(context, 6);
        eightDps = MiscUtils.dpToPixel(context, 8);
        sixteenDps = MiscUtils.dpToPixel(context, 16);
    }


    public void setTabString(String tabString) {
        this.tabString = tabString;
    }

    public void setIconResId(int iconId) {
        this.iconResId = iconId;
    }

    /**
     * 初始化布局
     */
    void prepareLayout() {
        int layoutResource;
        //根据type选择对应的布局
        layoutResource = getLayoutResource();
        //加载布局
        inflate(getContext(), layoutResource, this);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        iconView = (AppCompatImageView) findViewById(R.id.bb_bottom_bar_icon);
        iconView.setImageResource(iconResId);

        if (type != Type.TABLET) {
            titleView = (TextView) findViewById(R.id.bb_bottom_bar_title);
            titleView.setText(tabString);
        }

    }

    @VisibleForTesting
    int getLayoutResource() {
        int layoutResource;
        switch (type) {
            case FIXED:
                layoutResource = R.layout.bb_bottom_bar_item_fixed;
                break;
            case SHIFTING:
                layoutResource = R.layout.bb_bottom_bar_item_shifting;
                break;
            case TABLET:
                layoutResource = R.layout.bb_bottom_bar_item_fixed_tablet;
                break;
            default:
                // should never happen
                throw new RuntimeException("Unknown BottomBarTab type.");
        }
        return layoutResource;
    }

    /**
     * tab被选择的时
     * @param animate
     */
    void select(boolean animate) {
        isActive = true;

        if (animate) {
            setTopPaddingAnimated(iconView.getPaddingTop(), sixDps);
            animateIcon(activeAlpha);
            animateTitle(ACTIVE_TITLE_SCALE, activeAlpha);
            animateColors(inActiveColor, activeColor);
        } else {
            setTitleScale(ACTIVE_TITLE_SCALE);
            setTopPadding(sixDps);
            setColors(activeColor);
            setAlphas(activeAlpha);
        }

//        if (badge != null) {
//            badge.hide();
//        }
    }

    /**
     * tab没有被选择时
     * @param animate
     */
    void deselect(boolean animate) {
        isActive = false;

        boolean isShifting = type == Type.SHIFTING;

        float scale = isShifting ? 0 : INACTIVE_FIXED_TITLE_SCALE;
        int iconPaddingTop = isShifting ? sixteenDps : eightDps;

        if (animate) {
            setTopPaddingAnimated(iconView.getPaddingTop(), iconPaddingTop);
            animateTitle(scale, inActiveAlpha);
            animateIcon(inActiveAlpha);
            animateColors(activeColor, inActiveColor);
        } else {
            setTitleScale(scale);
            setTopPadding(iconPaddingTop);
            setColors(inActiveColor);
            setAlphas(inActiveAlpha);
        }

//        if (!isShifting && badge != null) {
//            badge.show();
//        }
    }

    private void setTopPaddingAnimated(int start, int end) {
        if (type == Type.TABLET) {
            return;
        }

        ValueAnimator paddingAnimator = ValueAnimator.ofInt(start, end);
        paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                iconView.setPadding(
                        iconView.getPaddingLeft(),
                        (Integer) animation.getAnimatedValue(),
                        iconView.getPaddingRight(),
                        iconView.getPaddingBottom()
                );
            }
        });

        paddingAnimator.setDuration(ANIMATION_DURATION);
        paddingAnimator.start();
    }

    /**
     * 没有动画时，设置字体缩放
     * @param scale
     */
    private void setTitleScale(float scale) {
        if (type == Type.TABLET) {
            return;
        }

        ViewCompat.setScaleX(titleView, scale);
        ViewCompat.setScaleY(titleView, scale);
    }

    /**
     * 没有动画时，设置顶部间距
     * @param topPadding
     */
    private void setTopPadding(int topPadding) {
        if (type == Type.TABLET) {
            return;
        }

        iconView.setPadding(
                iconView.getPaddingLeft(),
                topPadding,
                iconView.getPaddingRight(),
                iconView.getPaddingBottom()
        );
    }

    /**
     * 对title进行动画
     * @param finalScale
     * @param finalAlpha
     */
    private void animateTitle(float finalScale, float finalAlpha) {
        if (type == Type.TABLET) {
            return;
        }

        ViewPropertyAnimatorCompat titleAnimator = ViewCompat.animate(titleView)
                .setDuration(ANIMATION_DURATION)
                .scaleX(finalScale)
                .scaleY(finalScale);
        titleAnimator.alpha(finalAlpha);
        titleAnimator.start();
    }

    /**
     * 对icon进行动画
     * @param finalAlpha
     */
    private void animateIcon(float finalAlpha) {
        ViewCompat.animate(iconView)
                .setDuration(ANIMATION_DURATION)
                .alpha(finalAlpha)
                .start();
    }

    /**
     * 对颜色进行动画
     * @param previousColor
     * @param color
     */
    private void animateColors(int previousColor, int color) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(previousColor, color);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setColors((Integer) valueAnimator.getAnimatedValue());
            }
        });

        anim.setDuration(150);
        anim.start();
    }

    /**
     * 设置颜色
     * @param color
     */
    private void setColors(int color) {
        if (iconView != null) {
            iconView.setColorFilter(color);
            iconView.setTag(color);
        }

        if (titleView != null) {
            titleView.setTextColor(color);
        }
    }

    /**
     * 设置alpha
     * @param alpha
     */
    private void setAlphas(float alpha) {
        if (iconView != null) {
            ViewCompat.setAlpha(iconView, alpha);
        }

        if (titleView != null) {
            ViewCompat.setAlpha(titleView, alpha);
        }
    }



    Type getType() {
        return type;
    }

    void setType(Type type) {
        this.type = type;
    }

    int getInActiveColor() {
        return inActiveColor;
    }

    void setInActiveColor(int inActiveColor) {
        this.inActiveColor = inActiveColor;

        if (!isActive) {
            setColors(inActiveColor);
        }
    }

    int getActiveColor() {
        return activeColor;
    }

    void setActiveColor(int activeIconColor) {
        this.activeColor = activeIconColor;

        if (isActive) {
            setColors(activeColor);
        }
    }

    float getInActiveAlpha() {
        return inActiveAlpha;
    }

    void setInActiveAlpha(float inActiveAlpha) {
        this.inActiveAlpha = inActiveAlpha;

        if (!isActive) {
            setAlphas(inActiveAlpha);
        }
    }

    float getActiveAlpha() {
        return activeAlpha;
    }

    void setActiveAlpha(float activeAlpha) {
        this.activeAlpha = activeAlpha;

        if (isActive) {
            setAlphas(activeAlpha);
        }
    }

    int getBarColorWhenSelected() {
        return barColorWhenSelected;
    }

    void setBarColorWhenSelected(int barColorWhenSelected) {
        this.barColorWhenSelected = barColorWhenSelected;
    }


    void setConfig(Config config) {
        setInActiveAlpha(config.inActiveTabAlpha);
        setActiveAlpha(config.activeTabAlpha);
        setInActiveColor(config.inActiveTabColor);
        setActiveColor(config.activeTabColor);


    }


    public static class Config {
        private final float inActiveTabAlpha;
        private final float activeTabAlpha;
        private final int inActiveTabColor;
        private final int activeTabColor;
        private final int barColorWhenSelected;

        private Config(Config builder) {
            this.inActiveTabAlpha = builder.inActiveTabAlpha;
            this.activeTabAlpha = builder.activeTabAlpha;
            this.inActiveTabColor = builder.inActiveTabColor;
            this.activeTabColor = builder.activeTabColor;
            this.barColorWhenSelected = builder.barColorWhenSelected;
        }

        public static class Builder {
            private float inActiveTabAlpha;
            private float activeTabAlpha;
            private int inActiveTabColor;
            private int activeTabColor;
            private int barColorWhenSelected;

            public Builder inActiveTabAlpha(float alpha) {
                this.inActiveTabAlpha = alpha;
                return this;
            }

            public Builder activeTabAlpha(float alpha) {
                this.activeTabAlpha = alpha;
                return this;
            }

            public Builder inActiveTabColor(@ColorInt int color) {
                this.inActiveTabColor = color;
                return this;
            }

            public Builder activeTabColor(@ColorInt int color) {
                this.activeTabColor = color;
                return this;
            }

            public Builder barColorWhenSelected(@ColorInt int color) {
                this.barColorWhenSelected = color;
                return this;
            }


            }

            public Config build() {
                return new Config(this);
            }
        }
    }

