package com.example.hugo.njupter.utils;

import android.content.Context;
import android.util.DisplayMetrics;


public class ScreenUtils {
    private static int screenW = 0;
    private static int screenH = 0;
    private static Object lock = new Object();
    private static float screenDensity;

    private static void initScreen(Context context) {
        synchronized (lock) {
            DisplayMetrics metric = context.getResources().getDisplayMetrics();
            screenW = metric.widthPixels;
            screenH = metric.heightPixels;
            screenDensity = metric.density;
        }
    }

    public static int getScreenW(Context context) {
        if (screenW == 0)
            initScreen(context);
        return screenW;
    }

    public static int getScreenH(Context context) {
        if (screenH == 0)
            initScreen(context);
        return screenH;
    }

    public static float getScreenDensity(Context context) {
        if (screenDensity == 0)
            initScreen(context);
        return screenDensity;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) (dpValue * getScreenDensity(context) + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        return (int) (pxValue / getScreenDensity(context) + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
