package com.example.hugo.njupter;

import android.app.Application;
import android.content.Context;



/**
 * Created by Shine on 2016/3/1.
 */
public class App extends Application {
    private static String token;
    private static String uuid;
    public static Context s;


    @Override
    public void onCreate() {
        s=this;
        super.onCreate();




    }
}
