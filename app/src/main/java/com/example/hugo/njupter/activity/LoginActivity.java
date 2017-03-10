package com.example.hugo.njupter.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hugo.njupter.R;

/**
 * Created by hugo on 2017/2/5.
 */

public class LoginActivity extends BasicActivity {
    private Context mContext=LoginActivity.this;
    private EditText usernameEd;
    private EditText passwordEd;
    private TextView nameHintTx;
    private TextView pwdHintTx;

    private ImageView imgUsername;
    private ImageView imgPassword;

    private  Animation mAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        usernameEd= (EditText) findViewById(R.id.ed_username);
        passwordEd= (EditText) findViewById(R.id.ed_password);
        imgPassword= (ImageView) findViewById(R.id.img_password);
        imgUsername= (ImageView) findViewById(R.id.img_username);
        nameHintTx= (TextView) findViewById(R.id.view_name_hint);

        pwdHintTx= (TextView) findViewById(R.id.view_password_hint);
        passwordEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    //获取焦点
                        Animator animator= AnimatorInflater.loadAnimator(mContext, R.animator.fade_in);
                        animator.setTarget(pwdHintTx);
                        animator.start();



                    Animator animator1=AnimatorInflater.loadAnimator(mContext, R.animator.fade_in_half);
                    animator1.setTarget(imgPassword);
                    animator1.start();

                    passwordEd.setHint("");

                }else{
                    //失去焦点
                    if(passwordEd.getText().toString().equals("")){
                        Animator animator= AnimatorInflater.loadAnimator(mContext, R.animator.fade_out);
                        animator.setTarget(pwdHintTx);
                        animator.start();
                    }

                    Animator animator1=AnimatorInflater.loadAnimator(mContext, R.animator.fade_out_half);
                    animator1.setTarget(imgPassword);
                    animator1.start();
                    passwordEd.setHint("密码");
                }
            }
        });

        usernameEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    //获取焦点
                    if(usernameEd.getText().toString().equals("")) {
                        Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.fade_in);
                        animator.setTarget(nameHintTx);
                        animator.start();
                    }



                    Animator animator1=AnimatorInflater.loadAnimator(mContext, R.animator.fade_in_half);
                    animator1.setTarget(imgUsername);
                    animator1.start();

                    usernameEd.setHint("");
                }else{
                    //失去焦点
                    if(usernameEd.getText().toString().equals("")){
                        Animator animator= AnimatorInflater.loadAnimator(mContext, R.animator.fade_out);
                        animator.setTarget(nameHintTx);
                        animator.start();
                    }

                    Animator animator1=AnimatorInflater.loadAnimator(mContext, R.animator.fade_out_half);
                    animator1.setTarget(imgUsername);
                    animator1.start();
                   usernameEd.setHint("用户名");
                }
            }
        });
    }


}
