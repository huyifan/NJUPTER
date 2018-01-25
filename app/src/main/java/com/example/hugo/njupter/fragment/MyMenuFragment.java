package com.example.hugo.njupter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugo.njupter.CircleTransformation;
import com.example.hugo.njupter.activity.NearPeopleActivity;
import com.example.hugo.njupter.R;
import com.example.hugo.njupter.activity.NearPeopleActivity_;
import com.example.hugo.njupter.activity.login.LoginActivity;
import com.example.hugo.njupter.bean.User;
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
    private TextView tvAm;
    private TextView tvUserName;
    private User user;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tvAm.setText(user.getAm());
            tvUserName.setText(user.getNickName());
        }
    };
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
        mNavigationView= (NavigationView) view.findViewById(R.id.vNavigation);
        View viewHeader=mNavigationView.getHeaderView(0);

        tvUserName= (TextView) viewHeader.findViewById(R.id.lf_menu_user_name);
        tvAm= (TextView) viewHeader.findViewById(R.id.lf_menu_am);
        ivMenuUserProfilePhoto = (ImageView)viewHeader.findViewById(R.id.ivMenuUserProfilePhoto);

        user=User.getCurrentUser(getContext());

        if(user!=null){
            tvAm.setText(user.getAm());
            tvUserName.setText(user.getNickName());
        }else{
            ToastUtil.showShortToast(getContext(),"还未登录,请先登录~");
        }



        mNavigationView.setClickable(true);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(user!=null) {
                    switch (item.getTitle().toString()) {
                        case "附近的人":
                            NearPeopleActivity_.intent(getContext()).start();
                            break;
                        case "退出登录":
                            User.logout(getContext());
                            ToastUtil.showShortToast(getContext(),"已登出~");
                            tvUserName.setText("未登录");
                            tvAm.setText("");
                            user=null;
                            break;
                    }

                    return false;
                }else{
                    ToastUtil.showShortToast(getContext(),"还未登录,请先登录~");
                    startActivityForResult(new Intent(getContext(),LoginActivity.class),111);
                }
               return true;
            }
        });


        viewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user==null){
                    startActivityForResult(new Intent(getActivity(),LoginActivity.class),111);
                }else{
                    //跳转到用户信息的页面
                }

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

    @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==111){
            user=User.getCurrentUser(getContext());
            if(user!=null){
                Message message=new Message();
                message.arg1=1;
                handler.sendMessage(message);
//                tvAm.setText(user.getAm());
//                tvUserName.setText(user.getNickName());

            }
        }
    }
}
