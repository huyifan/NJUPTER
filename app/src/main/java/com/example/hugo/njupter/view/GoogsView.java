package com.example.hugo.njupter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.bean.GoodBean;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by hugo on 2017/3/10.
 */
@EViewGroup(R.layout.view_good)
public class GoogsView extends AutoLinearLayout{
    public GoodBean goodBean;
    @ViewById(R.id.good_image)
    public ImageView gImage;
    @ViewById(R.id.id_ic_money)
    public ImageView iMoney;
    @ViewById(R.id.good_title)
    public TextView gTitle;
    @ViewById(R.id.good_des)
    public TextView gDes;
    @ViewById(R.id.good_date)
    public TextView gDate;
    @ViewById(R.id.good_author)
    public TextView gAuthor;

    @ViewById(R.id.good_money)
    public TextView gMoney;


    public GoogsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initViews() {

    }


    public void setData(GoodBean goodBean){
        this.goodBean=goodBean;
        gTitle.setText(goodBean.getTitle());
        gDes.setText(goodBean.getDes());
        gAuthor.setText(goodBean.getNickName());
        gDate.setText(goodBean.getDate());
        gMoney.setText(goodBean.getPoint());
        if(goodBean.isGood()){
            iMoney.setBackgroundResource(R.drawable.ic_money);
        }else{
            iMoney.setBackgroundResource(R.drawable.ic_point);
        }
    }



}
