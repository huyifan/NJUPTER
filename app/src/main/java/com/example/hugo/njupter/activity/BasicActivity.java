package com.example.hugo.njupter.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by hugo on 2016/12/2.
 */

public class BasicActivity extends AutoLayoutActivity {
    protected ImageView toolBarLeftImg;
    protected TextView toolBarText;
    protected ImageView backImg;

    protected void setToolbarleftImg(int imgId,int imgRes){
        toolBarLeftImg= (ImageView)findViewById(imgId);
        toolBarLeftImg.setImageResource(imgRes);
    }

    protected void setToolBarText(int textId,String string){
        toolBarText= (TextView) findViewById(textId);
        toolBarText.setText(string);
    }

    protected void setBackImg(int res){
        backImg= (ImageView) findViewById(res);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
