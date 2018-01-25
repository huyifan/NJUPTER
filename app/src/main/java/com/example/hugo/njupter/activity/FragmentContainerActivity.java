package com.example.hugo.njupter.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.fragment.AddLostFragment;
import com.example.hugo.njupter.fragment.AddLostFragment_;
import com.example.hugo.njupter.fragment.TemplateFragment;
import com.example.hugo.njupter.fragment.TemplateFragment_;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * fragment的容器act
 * Created by hugo on 2017/3/21.
 */
@EActivity(R.layout.activity_fragment_container)
public class FragmentContainerActivity extends BasicActivity {
    @ViewById(R.id.fragment_container)
    FrameLayout container;

    @ViewById(R.id.bt_float)
    FloatingActionButton btFloat;

    @Extra
    String page;

    private FragmentManager fm;
    private FragmentTransaction fragmentTransaction;

    @AfterViews
    public void initViews(){
         fm = getSupportFragmentManager();

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setBackImg();

        switch (page){
            case "add_lost":

                setToolbarleftImg(R.drawable.ic_lost);
                setToolBarText("发布失物信息");
                btFloat.setVisibility(View.GONE);

                AddLostFragment fragment=AddLostFragment_.builder().page(page).build();
                setFrameLayout(fragment);
                break;
            case "add_second":
                setToolbarleftImg(R.drawable.ic_second);
                setToolBarText("发布二手信息");
                btFloat.setVisibility(View.GONE);
                AddLostFragment fragment1=AddLostFragment_.builder().page(page).build();
                setFrameLayout(fragment1);

                break;

            case "all_lost":
                setToolbarleftImg(R.drawable.ic_lost);
                setToolBarText("失物招领");
                btFloat.setVisibility(View.VISIBLE);
                btFloat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentContainerActivity_.intent(FragmentContainerActivity.this).page("add_lost").start();
                    }
                });

                TemplateFragment templateFragment= TemplateFragment_.builder().flag("失物招领").cid("5").build();
                setFrameLayout(templateFragment);
                break;

            case "":
                break;
        }
    }

    protected void setFrameLayout(Fragment fragment){
        fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

}
