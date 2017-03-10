package com.example.hugo.njupter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hugo.njupter.CircleTransformation;
import com.example.hugo.njupter.R;
import com.example.hugo.njupter.activity.LoginActivity;
import com.example.hugo.njupter.utils.ToastUtil;
import com.mxn.soul.flowingdrawer_core.MenuFragment;
import com.squareup.picasso.Picasso;

/**
 * Created by hugo on 2016/12/2.
 */
public class MyMenuFragment extends MenuFragment {
    //用户头像
    private ImageView ivMenuUserProfilePhoto;
    private NavigationView mNavigationView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);

        View menuHeader=inflater.inflate(R.layout.view_global_menu_header, container,
                false);

        ivMenuUserProfilePhoto = (ImageView)menuHeader.findViewById(R.id.ivMenuUserProfilePhoto);
        mNavigationView= (NavigationView) view.findViewById(R.id.vNavigation);
        mNavigationView.setClickable(true);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getTitle().toString()){
//
//                }
                Toast.makeText(getContext()," "+item.getTitle(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        View viewHeader=mNavigationView.getHeaderView(0);
        viewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });

        setupHeader();
        return setupReveal(view);
    }

    /**
     * 设置menu的头部信息
     */
    private void setupHeader() {
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
        String profilePhoto = getResources().getString(R.string.user_profile_photo);
        Picasso.with(getActivity())
                .load(profilePhoto)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivMenuUserProfilePhoto);

    }

    public void onOpenMenu(){
        //Toast.makeText(getActivity(),"onOpenMenu",Toast.LENGTH_SHORT).show();
    }
    public void onCloseMenu(){
       // Toast.makeText(getActivity(),"onCloseMenu",Toast.LENGTH_SHORT).show();
    }

}
