package com.example.hugo.njupter.provider;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Shine on 2016/3/13.
 */
public class UserPrvdr extends BasePrvdr {
    public UserPrvdr(Context context) {
        super(context);
    }

    public void login(Map<String, String> params, CallBackListener listener) {
        post(APIConstants.login, params, listener);
    }

    public void register(String phone, String password,CallBackListener listener) {
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        params.put("password",password);
        params.put("nickName","");
        post(APIConstants.register,params,listener);
    }
}
