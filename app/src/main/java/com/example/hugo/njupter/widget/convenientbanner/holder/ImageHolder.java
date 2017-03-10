package com.example.hugo.njupter.widget.convenientbanner.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by shine on 16-2-16.
 */
public class ImageHolder implements Holder<Integer> {


    private ImageView itemView;

    @Override
    public View createView(Context context) {
        ImageView imageView = new ImageView(context, null);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.itemView = imageView;
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        itemView.setImageResource(data);
    }
}
