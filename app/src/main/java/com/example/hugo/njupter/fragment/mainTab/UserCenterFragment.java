package com.example.hugo.njupter.fragment.mainTab;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.utils.PhotoUtil;
import com.github.siyamed.shapeimageview.RoundedImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


/**
 * Created by hugo on 2016/12/1.
 */

@EFragment(R.layout.fragment_user_page)
public class UserCenterFragment extends Fragment {
    @ViewById(R.id.img_t1)
    public ImageView imgT1;
    @ViewById(R.id.img_t1_1)
    public ImageView imgT11;
    @ViewById(R.id.img_t1_2)
    public ImageView imgT12;
    @ViewById(R.id.img_t1_3)
    public ImageView imgT13;
    @ViewById(R.id.img_t2_1)
    public ImageView imgT21;
    @ViewById(R.id.img_t2_2)
    public ImageView imgT22;
    @ViewById(R.id.img_t2_3)
    public ImageView imgT23;
    @ViewById(R.id.img_t2)
    public ImageView imgT2;
    @ViewById(R.id.img_t3_1)
    public ImageView imgT31;
    @ViewById(R.id.img_t3_2)
    public ImageView imgT32;
    @ViewById(R.id.img_t3_3)
    public ImageView imgT33;
    @ViewById(R.id.img_t3)
    public ImageView imgT3;
    @ViewById(R.id.img_bg)
    public ImageView imgBg;

    @ViewById(R.id.img_user_center)
    RoundedImageView imageView;

     @AfterViews
    public void initViews(){
         initImage();

     }

    private void initImage() {
        imgT1.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_p1));
        imgT2.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_p2));
        imgT3.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_p3));

        imgT11.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_m_upload));
        imgT12.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_m_info));
        imgT13.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_m_acount));

        imgT21.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_m_lost));
        imgT22.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_m_second));
        imgT23.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_m_act));

        imgT31.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_m_question));
        imgT32.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_m_answer));
        imgT33.setImageDrawable(PhotoUtil.readBitMap2Drawable(getContext(),R.drawable.ic_m_collection));


    }
}
