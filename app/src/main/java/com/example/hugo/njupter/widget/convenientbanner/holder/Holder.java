package com.example.hugo.njupter.widget.convenientbanner.holder;

/**
 * @param <T> 任何你指定的对象
 */

import android.content.Context;
import android.view.View;

public interface Holder<T> {
    View createView(Context context);

    void UpdateUI(Context context, int position, T data);
}