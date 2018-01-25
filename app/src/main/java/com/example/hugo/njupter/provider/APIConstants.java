package com.example.hugo.njupter.provider;

import com.example.hugo.njupter.bean.ULocation;

/**
 * Created by Shine on 2016/3/10.
 */
public class APIConstants {

    public static final String base="http://118.89.166.244:8080/hugo";
    public static String login=base+"/user/login";
    public static String APIKEY="f7e319445b42db9a69f5dcca9b92e7fc";
    public static String register=base+"/user/register";
    public static String getGoodsList=base+"/goods/getGoods/p";
    public static String addGoods=base+"/goods/addGoods";
    public static String getEventList=base+"/event/get";

    public static String parseUpAPI(ULocation uLocation){
        return base+"/location/up?uid="+uLocation.getUid()+"&location="+uLocation.getLocation()+
                "&ontime="+uLocation.getOnTime();

    }

    public static String parseNearByAPI(ULocation location,String range) {
        if(range==null||range==""){
            range="50000";
        }
        return base+"/location/nearby/+"+location.getLocation()+"/"+range;
    }

    public static String getByUserId(String uid) {
        return base+"/location/getById/uid";
    }
}
