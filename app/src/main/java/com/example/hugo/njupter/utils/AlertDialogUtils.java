package com.example.hugo.njupter.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hugo.njupter.R;


/**
 * Created by ljy on 15/12/11.
 */
public class AlertDialogUtils {
    private static AlertDialog alertDialog = null;

    private AlertDialogUtils() {
    }

    public static Builder builder(Context context) {
        Builder builder = new Builder(context);
        return builder;
    }

    public static void dismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public static class Builder {

        private Context mContext;

        private String title = "";
        private String content = "";

        private String strPositive = "确定";
        private String strNegative = "取消";

        private boolean isCanceledOnTouchOutside = true;

        private AlertDialog.OnClickListener onPositiveClickListener;
        private AlertDialog.OnClickListener onNegativeClickListener;
        private View.OnClickListener onDismissListener;

        private boolean cancelable = true;
        private View viewGroup;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTitle(String value) {
            title = value;
            return this;
        }

        public Builder setMessage(String value) {
            content = value;
            return this;
        }

        public Builder setOnPositiveClickListener(AlertDialog.OnClickListener value) {
            onPositiveClickListener = value;
            return this;
        }

        public Builder setOnNegativeClickListener(AlertDialog.OnClickListener value) {
            onNegativeClickListener = value;
            return this;
        }

        public Builder setOnDismissListener(View.OnClickListener value) {
            onDismissListener = value;
            return this;
        }

        public Builder setStrPositive(String value) {
            strPositive = value;
            return this;
        }

        public Builder setStrNegative(String value) {
            strNegative = value;
            return this;
        }

        public Builder setIsCanceledOnTouchOutside(boolean isCanceledOnTouchOutside) {
            this.isCanceledOnTouchOutside = isCanceledOnTouchOutside;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public void onCancel() {
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
        }

        public Builder show() {
            if (onNegativeClickListener == null) {
                onNegativeClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();

                    }
                };
            }
            if (onPositiveClickListener == null) {
                onPositiveClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                };
            }
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext)
                    .setPositiveButton(strPositive, onPositiveClickListener)
                    .setNegativeButton(strNegative, onNegativeClickListener);
            if (!TextUtils.isEmpty(title)) {
                dialogBuilder.setTitle(title);
            }
            if (!TextUtils.isEmpty(content)) {
                dialogBuilder.setMessage(content);
            }
            if (viewGroup != null) {

                dialogBuilder.setView(viewGroup);

            }
            alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(cancelable);
            alertDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
            alertDialog.show();
            return this;
        }

        public Builder setView(@LayoutRes int dialog_export) {
            viewGroup = LayoutInflater.from(mContext).inflate(R.layout.dialog_choose_photo, null);
            return this;
        }

        public Builder setView(View viewGroup) {
            this.viewGroup = viewGroup;
            return this;
        }

        public View getView(View viewGroup) {
            return viewGroup;
        }
    }
}
