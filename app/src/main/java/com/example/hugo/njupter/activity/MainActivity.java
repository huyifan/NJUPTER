package com.example.hugo.njupter.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugo.njupter.fragment.MyMenuFragment;
import com.example.hugo.njupter.R;
import com.example.hugo.njupter.fragment.mainTab.CollegeFragment;
import com.example.hugo.njupter.fragment.mainTab.LifePageFragment;
import com.example.hugo.njupter.fragment.mainTab.LookPageFragment;
import com.example.hugo.njupter.fragment.mainTab.QuestionAnswerFragment;
import com.example.hugo.njupter.fragment.mainTab.UserCenterFragment;
import com.mxn.soul.flowingdrawer_core.FlowingView;
import com.mxn.soul.flowingdrawer_core.LeftDrawerLayout;


public class MainActivity extends BasicActivity implements View.OnClickListener {
    private Context context = MainActivity.this;
    //动画的持续时间
    private final int ANIMATION_DURATION = 200;
    private static final float ACTIVE_SCALE = 1.2f;
    private static final float IN_ACTIVE_SCALE = 1.0f;
    private final float activeAlpha = 1f;

    //左侧的menu
    private LeftDrawerLayout mLeftDrawerLayout;
    //顶部的menu图标
    private ImageView toolbarMenu;
    //顶部的titile
    private TextView titleText;

    //底部的5个imageView和TextView
    private ImageView lifeImage;
    private ImageView questionImage;
    private ImageView lookImage;
    private ImageView collegeImage;
    private ImageView userImage;
    private TextView lifeTextView;
    private TextView questionTextView;
    private TextView lookTextView;
    private TextView collegeTextView;
    private TextView userTextView;


    //底部5个tab的relativeLayout
    private LinearLayout lifeRy;
    private LinearLayout questionRy;
    private LinearLayout lookRy;
    private LinearLayout collegeRy;
    private LinearLayout userRy;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private LifePageFragment lifeFragment;
    private QuestionAnswerFragment questionFragment;
    private CollegeFragment collegeFragment;
    private UserCenterFragment userFragment;
    private LookPageFragment lookPageFragment;

    private long exitTime = 0;
    private ObjectAnimator animator;
    private RelativeLayout outerContainer;
    private View backgroundOverlay;

    private RelativeLayout toolBar;
    private boolean isSameTab=false;

    //尺寸
    //  private final int sixDps=dpToPixel(getBaseContext(), 6);
//    private final int eightDps=dpToPixel(MainActivity.this, 8);
//    private final int sixteenDps=dpToPixel(MainActivity.this, 16);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化顶部导航栏
        setupToolbar();
        //初始化View
        initViews();
        //设置滑动的menu和content
        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
        FragmentManager fm = getSupportFragmentManager();
        MyMenuFragment mMenuFragment = (MyMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        FlowingView mFlowingView = (FlowingView) findViewById(R.id.sv);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new MyMenuFragment()).commit();
        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);

        setSelect(1);
    }

    /**
     * 初始化View
     */
    private void initViews() {
        lifeImage = (ImageView) findViewById(R.id.img_tab_life);
        lifeTextView = (TextView) findViewById(R.id.tv_tab_life);
        questionImage = (ImageView) findViewById(R.id.img_tab_question);
        questionTextView = (TextView) findViewById(R.id.tv_tab_question);
        lookImage = (ImageView) findViewById(R.id.img_tab_look);
        lookTextView = (TextView) findViewById(R.id.tv_tab_look);
        collegeImage = (ImageView) findViewById(R.id.img_tab_college);
        collegeTextView = (TextView) findViewById(R.id.tv_tab_college);
        userImage = (ImageView) findViewById(R.id.img_tab_user);
        userTextView = (TextView) findViewById(R.id.tv_tab_user);

        titleText = (TextView) findViewById(R.id.header_title);


        lifeRy =(LinearLayout) findViewById(R.id.rela_life);
        questionRy = (LinearLayout) findViewById(R.id.rela_answer);
        lookRy = (LinearLayout) findViewById(R.id.eye_rela);
        collegeRy = (LinearLayout) findViewById(R.id.rela_college);
        userRy = (LinearLayout) findViewById(R.id.rela_user);

        outerContainer = (RelativeLayout) findViewById(R.id.bottom_part);
        backgroundOverlay = findViewById(R.id.background_overlay);

        toolBar= (RelativeLayout) findViewById(R.id.titlebar_layout);

        lifeRy.setOnClickListener(this);
        questionRy.setOnClickListener(this);
        lookRy.setOnClickListener(this);
        collegeRy.setOnClickListener(this);
        userRy.setOnClickListener(this);


    }


    /**
     * 设置toolBar
     */
    private void setupToolbar() {

        toolbarMenu = (ImageView) findViewById(R.id.tool_bar_menu);
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftDrawerLayout.toggle();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_life:
                setSelect(1);
                break;
            case R.id.rela_answer:
                setSelect(2);
                break;
            case R.id.eye_rela:
                setSelect(3);

                break;
            case R.id.rela_college:
                setSelect(4);

                break;
            case R.id.rela_user:
                setSelect(5);

                break;
        }
    }

    public void setSelect(final int select) {
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        //重置所有的图标
        resetImage();
        hiddenAllFragmemt(fragmentTransaction);

        animator = ObjectAnimator.ofFloat(titleText, "alpha", 0f, 1f);
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                switch (select) {
                    case 1:
                        isSameTab=checkIsSameTab(getBackgroundColor(toolBar),R.color.life_tab);
                        animateBGColorChange(lifeRy, ContextCompat.getColor(context, R.color.life_tab));

                        animateScale(ACTIVE_SCALE, activeAlpha, lifeRy);
                        titleText.setText("掌上南邮·柚生活");
                        break;
                    case 2:
                        isSameTab=checkIsSameTab(getBackgroundColor(toolBar),R.color.question_tab);

                        animateBGColorChange(questionRy, ContextCompat.getColor(context, R.color.question_tab));

                        animateScale(ACTIVE_SCALE, activeAlpha, questionRy);
                        titleText.setText("掌上南邮·柚问答");
                        break;
                    case 3:
                        isSameTab=checkIsSameTab(getBackgroundColor(toolBar),R.color.look_tab);

                        animateBGColorChange(lookRy, ContextCompat.getColor(context, R.color.look_tab));

                        animateScale(ACTIVE_SCALE, activeAlpha, lookRy);
                        titleText.setText("掌上南邮·柚查查");
                        break;
                    case 4:
                        isSameTab=checkIsSameTab(getBackgroundColor(toolBar),R.color.college_tab);

                        animateBGColorChange(collegeRy, ContextCompat.getColor(context, R.color.college_tab));

                        animateScale(ACTIVE_SCALE, activeAlpha, collegeRy);
                        titleText.setText("掌上南邮·柚学院");
                        break;
                    case 5:
                        isSameTab=checkIsSameTab(getBackgroundColor(toolBar),R.color.user_tab);

                        animateBGColorChange(userRy, ContextCompat.getColor(context, R.color.user_tab));

                        animateScale(ACTIVE_SCALE, activeAlpha, userRy);
                        titleText.setText("掌上南邮·柚子中心");
                        break;



                }
            }
        });
        animator.start();

            switch (select) {
                case 1:
                    lifeImage.setImageResource(R.drawable.ic_life_selected);
                    lifeTextView.setTextColor(Color.YELLOW);

                    if (lifeFragment == null) {
                        lifeFragment = new LifePageFragment();
                        fragmentTransaction.add(R.id.fragment_container, lifeFragment);
                    } else {
                        fragmentTransaction.show(lifeFragment);
                    }
                    fragmentTransaction.commit();
                    break;
                case 2:

                    questionImage.setImageResource(R.drawable.ic_question_selected);
                    questionTextView.setTextColor(Color.YELLOW);
                    if (questionFragment == null) {
                        questionFragment = new QuestionAnswerFragment();
                        fragmentTransaction.add(R.id.fragment_container, questionFragment);
                    } else {
                        fragmentTransaction.show(questionFragment);
                    }

                    fragmentTransaction.commit();
                    break;
                case 3:

                    lookImage.setImageResource(R.drawable.ic_look_select);
                    lookTextView.setTextColor(Color.YELLOW);
                    if (lookPageFragment == null) {
                        lookPageFragment = new LookPageFragment();
                        fragmentTransaction.add(R.id.fragment_container, lookPageFragment);
                    } else {
                        fragmentTransaction.show(lookPageFragment);
                    }
                    fragmentTransaction.commit();
                    break;

                case 4:

                    collegeImage.setImageResource(R.drawable.ic_college_selected);
                    collegeTextView.setTextColor(Color.YELLOW);
                    if (collegeFragment == null) {
                        collegeFragment = new CollegeFragment();
                        fragmentTransaction.add(R.id.fragment_container, collegeFragment);
                    } else {
                        fragmentTransaction.show(collegeFragment);
                    }
                    fragmentTransaction.commit();
                    break;
                case 5:

                    userImage.setImageResource(R.drawable.ic_user_selected);
                    userTextView.setTextColor(Color.YELLOW);

                    if (userFragment == null) {
                        userFragment = new UserCenterFragment();
                        fragmentTransaction.add(R.id.fragment_container, userFragment);
                    } else {
                        fragmentTransaction.show(userFragment);
                    }
                    fragmentTransaction.commit();
                    break;
            }


    }

    /**
     * 检查当前点击的是否是相同的tab
     * @param backgroundColor
     * @param life_tab
     * @param lifeRy
     */
    private boolean checkIsSameTab(int backgroundColor, int life_tab) {
        if(backgroundColor==life_tab){
            return false;
        }else{
            return true;
        }
    }

    private void hiddenAllFragmemt(FragmentTransaction fragmentTransaction) {
        if (lifeFragment != null) {
            fragmentTransaction.hide(lifeFragment);
        }

        if (questionFragment != null) {
            fragmentTransaction.hide(questionFragment);
        }
        if (userFragment != null) {
            fragmentTransaction.hide(userFragment);
        }

        if (collegeFragment != null) {
            fragmentTransaction.hide(collegeFragment);
        }

        if (lookPageFragment != null) {
            fragmentTransaction.hide(lookPageFragment);
        }
    }


    /**
     * 重置图标
     */
    private void resetImage() {
        lifeImage.setImageResource(R.drawable.ic_life_default);
        lifeTextView.setTextColor(Color.WHITE);
        questionImage.setImageResource(R.drawable.ic_question_default);
        questionTextView.setTextColor(Color.WHITE);
        lookImage.setImageResource(R.drawable.ic_look_default);
        lookTextView.setTextColor(Color.WHITE);
        collegeImage.setImageResource(R.drawable.ic_college_default);
        collegeTextView.setTextColor(Color.WHITE);
        userImage.setImageResource(R.drawable.ic_user_default);
        userTextView.setTextColor(Color.WHITE);

        animate2Normal();
    }

    private void animate2Normal() {
        animateScale(IN_ACTIVE_SCALE, activeAlpha, lifeRy);
        animateScale(IN_ACTIVE_SCALE, activeAlpha, questionRy);
        animateScale(IN_ACTIVE_SCALE, activeAlpha, collegeRy);
        animateScale(IN_ACTIVE_SCALE, activeAlpha, userRy);
        animateScale(IN_ACTIVE_SCALE, activeAlpha, lookRy);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
            if (System.currentTimeMillis() - exitTime > 1000) {
                Toast.makeText(getApplicationContext(), "再按一次退出应用",
                        Toast.LENGTH_SHORT).show();
                // 将系统当前的时间赋值给exitTime
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void animateScale(float finalScale, float finalAlpha, View titleView) {

        ViewPropertyAnimatorCompat titleAnimator = ViewCompat.animate(titleView)
                .setDuration(ANIMATION_DURATION)
                .scaleX(finalScale)
                .scaleY(finalScale);
        titleAnimator.alpha(finalAlpha);
        titleAnimator.start();



    }


    private void animateBGColorChange(View clickedView, final int newColor) {
        prepareForBackgroundColorAnimation(newColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (!outerContainer.isAttachedToWindow()) {
                return;
            }


            backgroundCircularRevealAnimation(clickedView, newColor);
        } else {
            backgroundCrossfadeAnimation(newColor);
        }
    }

    private void prepareForBackgroundColorAnimation(int newColor) {

        outerContainer.clearAnimation();
        backgroundOverlay.clearAnimation();

        backgroundOverlay.setBackgroundColor(newColor);
        backgroundOverlay.setVisibility(View.VISIBLE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void backgroundCircularRevealAnimation(View clickedView, final int newColor) {
        int centerX = (int) (ViewCompat.getX(clickedView) + (clickedView.getMeasuredWidth() / 2));
        int yOffset = (int) ViewCompat.getY(clickedView);
        int centerY = yOffset + clickedView.getMeasuredHeight() / 2;
        int startRadius = 0;
        int finalRadius = outerContainer.getWidth();
        Log.d("---", "bg:centerX=" + centerX + " centerY=" + centerY);
        Animator animator = ViewAnimationUtils.createCircularReveal(
                backgroundOverlay,
                centerX,
                centerY,
                startRadius,
                finalRadius
        );


        ValueAnimator anim2 = ObjectAnimator.ofObject(new ArgbEvaluator(),getBackgroundColor(toolBar),newColor);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (int) animation.getAnimatedValue();//之后就可以得到动画的颜色了
                toolBar.setBackgroundColor(color);//设置一下, 就可以看到效果.
            }
        });
        anim2.setDuration(500);
        anim2.start();

        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());


        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onEnd();
            }

            private void onEnd() {
                allowAllBottomBarClick();
                outerContainer.setBackgroundColor(newColor);
                backgroundOverlay.setVisibility(View.GONE);
                ViewCompat.setAlpha(backgroundOverlay, 1);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                stopAllBottomBarClick();
            }
        });



        animator.start();
    }

    /**
     * 让所有底部按钮可以被点击
     */
    private void allowAllBottomBarClick() {
        lifeRy.setClickable(true);
        questionRy.setClickable(true);
        collegeRy.setClickable(true);
        userRy.setClickable(true);
        lookRy.setClickable(true);
    }

    /**
     * 让其他的底部按钮无法点击
     */
    private void stopAllBottomBarClick() {
        lifeRy.setClickable(false);
        questionRy.setClickable(false);
        collegeRy.setClickable(false);
        userRy.setClickable(false);
        lookRy.setClickable(false);
    }

    private int getBackgroundColor(RelativeLayout toolBar) {
        int color = Color.TRANSPARENT;
        Drawable background = toolBar.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        return color;
    }

    private void backgroundCrossfadeAnimation(final int newColor) {

        ViewCompat.setAlpha(backgroundOverlay, 0);
        ViewCompat.animate(backgroundOverlay)
                .alpha(1)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        onEnd();
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        onEnd();
                    }

                    private void onEnd() {
                        allowAllBottomBarClick();
                        outerContainer.setBackgroundColor(newColor);
                        backgroundOverlay.setVisibility(View.INVISIBLE);
                        ViewCompat.setAlpha(backgroundOverlay, 1);
                    }

                    @Override
                    public void onAnimationStart(View view) {
                        stopAllBottomBarClick();
                    }
                }).start();
    }


}
