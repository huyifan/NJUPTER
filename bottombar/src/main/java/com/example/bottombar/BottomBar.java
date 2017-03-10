package com.example.bottombar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by hugo on 2017/1/29.
 */

public class BottomBar extends LinearLayout implements View.OnClickListener, View.OnLongClickListener {
    // Behaviors
    private static final int BEHAVIOR_NONE = 0;
    private static final int BEHAVIOR_SHIFTING = 1;
    private static final int BEHAVIOR_SHY = 2;
    private static final int BEHAVIOR_DRAW_UNDER_NAV = 4;

    private static final float DEFAULT_INACTIVE_SHIFTING_TAB_ALPHA = 0.6f;


    private int primaryColor;
    private int screenWidth;
    private int tenDp;
    private int maxFixedItemWidth;



    private int tabXmlResource; //bottom底部的tab xml属性
    private int behaviors;

    private float inActiveTabAlpha;
    private float activeTabAlpha;

    private int inActiveTabColor;
    private int activeTabColor;

    private boolean isTabletMode;
    private int currentTabPosition;
    private int defaultBackgroundColor = Color.RED;
    private int currentBackgroundColor;

    private View backgroundOverlay;
    private ViewGroup outerContainer;
    private ViewGroup tabContainer;
    private View shadowView;
    private boolean showShadow;


    public BottomBar(Context context) {
        super(context);
        init(context, null);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setItems(tabXmlResource);
    }

    private void init(Context context, AttributeSet attrs) {
        populateAttributes(context, attrs);
        initializeViews();
        determineInitialBackgroundColor();
    }

    /**
     * 填充属性
     * @param context
     * @param attrs
     */
    private void populateAttributes(Context context, AttributeSet attrs) {
        //获取颜色
        primaryColor = MiscUtils.getColor(getContext(), R.attr.colorPrimary);
        //获取屏幕宽度
        screenWidth = MiscUtils.getScreenWidth(getContext());
        tenDp = MiscUtils.dpToPixel(getContext(), 10);

        maxFixedItemWidth = MiscUtils.dpToPixel(getContext(), 168);

        TypedArray ta = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.BottomBar, 0, 0);
        //获取自定义属性
        try {
            tabXmlResource = ta.getResourceId(R.styleable.BottomBar_bb_tabXmlResource, 0);
            isTabletMode = ta.getBoolean(R.styleable.BottomBar_bb_tabletMode, false);
            behaviors = ta.getInteger(R.styleable.BottomBar_bb_behavior, BEHAVIOR_NONE);
            inActiveTabAlpha = ta.getFloat(R.styleable.BottomBar_bb_inActiveTabAlpha,
                    isShiftingMode() ? DEFAULT_INACTIVE_SHIFTING_TAB_ALPHA : 1);
            activeTabAlpha = ta.getFloat(R.styleable.BottomBar_bb_activeTabAlpha, 1);

            @ColorInt
            int defaultInActiveColor = isShiftingMode() ?
                    Color.WHITE : ContextCompat.getColor(context, R.color.bb_inActiveBottomBarItemColor);
            int defaultActiveColor = isShiftingMode() ? Color.WHITE : primaryColor;
            inActiveTabColor = ta.getColor(R.styleable.BottomBar_bb_inActiveTabColor, defaultInActiveColor);
            activeTabColor = ta.getColor(R.styleable.BottomBar_bb_activeTabColor, defaultActiveColor);


        } finally {
            ta.recycle();
        }
    }

    private void initializeViews() {
        int width = isTabletMode ? LayoutParams.WRAP_CONTENT : LayoutParams.MATCH_PARENT;
        int height = isTabletMode ? LayoutParams.MATCH_PARENT : LayoutParams.WRAP_CONTENT;
        LayoutParams params = new LayoutParams(width, height);

        setLayoutParams(params);
        setOrientation(isTabletMode ? HORIZONTAL : VERTICAL);
        ViewCompat.setElevation(this, MiscUtils.dpToPixel(getContext(), 8));

        View rootView = inflate(getContext(),
                isTabletMode ? R.layout.bb_bottom_bar_item_container_tablet : R.layout.bb_bottom_bar_item_container, this);
        rootView.setLayoutParams(params);

        backgroundOverlay = rootView.findViewById(R.id.bb_bottom_bar_background_overlay);
        outerContainer = (ViewGroup) rootView.findViewById(R.id.bb_bottom_bar_outer_container);
        tabContainer = (ViewGroup) rootView.findViewById(R.id.bb_bottom_bar_item_container);
        shadowView = rootView.findViewById(R.id.bb_bottom_bar_shadow);

        if (!showShadow) {
            shadowView.setVisibility(GONE);
        }
    }

    private void determineInitialBackgroundColor() {
        if (isShiftingMode()) {
            defaultBackgroundColor = primaryColor;
        }

        Drawable userDefinedBackground = getBackground();

        boolean userHasDefinedBackgroundColor = userDefinedBackground != null
                && userDefinedBackground instanceof ColorDrawable;

        if (userHasDefinedBackgroundColor) {
            defaultBackgroundColor = ((ColorDrawable) userDefinedBackground).getColor();
            setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void setItems(int xmlRes) {
        if (xmlRes == 0) {
            throw new RuntimeException("No items specified for the BottomBar!");
        }
        //解析tab资源
        TabParser parser = new TabParser(getContext(), xmlRes);
        updateItems(parser.getTabs());
    }


    private void updateItems(List<BottomBarTab> bottomBarItems) {
        int index = 0;
        int biggestWidth = 0;

        BottomBarTab[] viewsToAdd = new BottomBarTab[bottomBarItems.size()];

        for (BottomBarTab bottomBarTab : bottomBarItems) {
            BottomBarTab.Type type;

            if (isShiftingMode()) {
                type = BottomBarTab.Type.SHIFTING;
            } else if (isTabletMode) {
                type = BottomBarTab.Type.TABLET;
            } else {
                type = BottomBarTab.Type.FIXED;
            }

            bottomBarTab.setType(type);
            bottomBarTab.prepareLayout();

            if (index == currentTabPosition) {
                bottomBarTab.select(false);
                handleBackgroundColorChange(bottomBarTab, false);
            } else {
                bottomBarTab.deselect(false);
            }

            if (!isTabletMode) {
                if (bottomBarTab.getWidth() > biggestWidth) {
                    biggestWidth = bottomBarTab.getWidth();
                }

                viewsToAdd[index] = bottomBarTab;
            } else {
                tabContainer.addView(bottomBarTab);
            }

            bottomBarTab.setOnClickListener(this);
            bottomBarTab.setOnLongClickListener(this);
            index++;
        }
    }

    private boolean isShiftingMode() {
        return !isTabletMode && hasBehavior(BEHAVIOR_SHIFTING);
    }

    private boolean hasBehavior(int behavior) {
        return (behaviors | behavior) == behaviors;
    }

    private void handleBackgroundColorChange(BottomBarTab tab, boolean animate) {
        int newColor = tab.getBarColorWhenSelected();

        if (currentBackgroundColor == newColor) {
            return;
        }

        if (!animate) {
            outerContainer.setBackgroundColor(newColor);
            return;
        }

        View clickedView = tab;

//        if (tab.hasActiveBadge()) {
//            clickedView = tab.getOuterView();
//        }

        animateBGColorChange(clickedView, newColor);
        currentBackgroundColor = newColor;
    }

    private void animateBGColorChange(View clickedView, final int newColor) {
        prepareForBackgroundColorAnimation(newColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!outerContainer.isAttachedToWindow()) {
                return;
            }

            backgroundCircularRevealAnimation(clickedView, newColor);
        } else {
            backgroundCrossfadeAnimation(newColor);
        }
    }

    private void prepareForBackgroundColorAnimation(int newColor) {
        outerContainer.clearAnimation();
        backgroundOverlay.clearAnimation();

        backgroundOverlay.setBackgroundColor(newColor);
        backgroundOverlay.setVisibility(View.VISIBLE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void backgroundCircularRevealAnimation(View clickedView, final int newColor) {
        int centerX = (int) (ViewCompat.getX(clickedView) + (clickedView.getMeasuredWidth() / 2));
        int yOffset = isTabletMode ? (int) ViewCompat.getY(clickedView) : 0;
        int centerY = yOffset + clickedView.getMeasuredHeight() / 2;
        int startRadius = 0;
        int finalRadius = isTabletMode ? outerContainer.getHeight() : outerContainer.getWidth();

        Animator animator = ViewAnimationUtils.createCircularReveal(
                backgroundOverlay,
                centerX,
                centerY,
                startRadius,
                finalRadius
        );

        if (isTabletMode) {
            animator.setDuration(500);
        }

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onEnd();
            }

            private void onEnd() {
                outerContainer.setBackgroundColor(newColor);
                backgroundOverlay.setVisibility(View.INVISIBLE);
                ViewCompat.setAlpha(backgroundOverlay, 1);
            }
        });

        animator.start();
    }

    private void backgroundCrossfadeAnimation(final int newColor) {
        ViewCompat.setAlpha(backgroundOverlay, 0);
        ViewCompat.animate(backgroundOverlay)
                .alpha(1)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        onEnd();
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        onEnd();
                    }

                    private void onEnd() {
                        outerContainer.setBackgroundColor(newColor);
                        backgroundOverlay.setVisibility(View.INVISIBLE);
                        ViewCompat.setAlpha(backgroundOverlay, 1);
                    }
                }).start();
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
