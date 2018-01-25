package com.example.hugo.njupter.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Shine on 2016/3/13.
 */
public class ToastUtil {

    /**
     * 产生一个progressDialog
     * @param context
     * @param title
     * @param message
     * @return
     */
    public static ProgressDialog makeProgressDialog(final Context context,String title,String message){
        final ProgressDialog proDialog = new ProgressDialog(context);
        proDialog.setMessage(message);
        proDialog.setTitle(title);
        //触摸其他地方是否取消？
        proDialog.setCanceledOnTouchOutside(false);
        proDialog.setCancelable(false);
        return proDialog;
    }

    public static void showLongToast(final Context context, final String msg) {
        showToast(context, msg, Toast.LENGTH_LONG);
    }

    public static void showShortToast(final Context context, final int msgId) {
        showToast(context, msgId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(final Context context, final int msgId) {
        showToast(context, msgId, Toast.LENGTH_LONG);
    }

    public static void showShortToast(final Context context, final String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    private static void showToast(final Context context, final String msg, final int duration) {
        Worker.postMain(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, duration).show();
            }
        });
    }

    private static void showToast(final Context context, final int msg, final int duration) {
        Worker.postMain(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, duration).show();
            }
        });
    }
}
