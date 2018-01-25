package com.example.hugo.njupter.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hugo.njupter.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Map;

/**
 * Created by hugo on 2016/12/2.
 */

public class BasicActivity extends AutoLayoutActivity {
    public static final int INPUT_METHOD_SHOW = 0;
    public static final int INPUT_METHOD_DISAPPEAR = 1;
    public static final int LIMIT_TIME = 3000;
    public static final int IDLE = 50;
    public Map<Integer, Fragment> fragments;
    public int nowIndex = -1;
    private int totalTime = 0;
    private boolean isFinish = false;
    private Fragment currentFragment = null;
    private InputMethodManager mInputMan;

    private ImageView toolBarLeftImg;
    private TextView toolBarText;


    /**
     * 该Handler主要处理软键盘的弹出跟隐藏
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            View view = (View) msg.obj;
            // 显示软键盘
            if (msg.what == INPUT_METHOD_SHOW) {
                boolean result = mInputMan.showSoftInput(view, 0);
                if (!result && totalTime < LIMIT_TIME) {
                    totalTime += IDLE;
                    Message message = Message.obtain(msg);
                    mHandler.sendMessageDelayed(message, IDLE);
                } else if (!isFinish) {
                    totalTime = 0;
                    result = view.requestFocus();
                    isFinish = true;
                }
            } else if (msg.what == INPUT_METHOD_DISAPPEAR) {
                // 隐藏软键盘
                mInputMan.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInputMan = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    protected void setToolbarleftImg(int imgId,int imgRes){
        toolBarLeftImg= (ImageView)findViewById(imgId);
        toolBarLeftImg.setImageResource(imgRes);
    }

    protected void setToolBarText(int textId,String string){
        toolBarText= (TextView) findViewById(textId);
        toolBarText.setText(string);
    }


    protected void setBackImg(int id){
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void setToolbarleftImg(int imgRes){
        toolBarLeftImg= (ImageView)findViewById(R.id.tool_bar_menu);
        toolBarLeftImg.setImageResource(imgRes);
    }

    protected void setToolBarText(String string){
        toolBarText= (TextView)findViewById(R.id.header_title);
        toolBarText.setText(string);
    }


    protected void setBackImg(){
        findViewById(R.id.title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 显示输入法
     *
     * @param view
     */
    public void showInputMethod(View view) {
        sendInputMethodMessage(INPUT_METHOD_SHOW, view);
    }

    /**
     * 隐藏输入法
     */
    public void hideInputMethod(View view) {
        sendInputMethodMessage(INPUT_METHOD_DISAPPEAR, view);
    }

    /**
     * 发送show or hide输入法消息</br>
     *
     * @param type
     * @param view
     */
    private void sendInputMethodMessage(int type, View view) {
        Message message = mHandler.obtainMessage(type);
        message.obj = view;
        mHandler.sendMessage(message);
    }

}
