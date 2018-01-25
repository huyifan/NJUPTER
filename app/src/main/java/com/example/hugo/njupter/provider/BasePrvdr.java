package com.example.hugo.njupter.provider;

import android.content.Context;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;
import com.example.hugo.njupter.R;
import com.example.hugo.njupter.utils.NetWorkUtils;
import com.example.hugo.njupter.utils.ToastUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


/**
 * Created by Shine on 2016/3/11.
 */
public class BasePrvdr {
    public static OkHttpClient httpClient;
    public final Context context;

    public BasePrvdr(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(5, TimeUnit.MINUTES);
        httpClient = builder.build();
        this.context = context;
    }


    public void post(String url, Map<String, String> params, Callback responseCallback) {
        try {
            if (!NetWorkUtils.isNetworkAvailable(context)) {
                ToastUtil.showLongToast(context, R.string.network_connect_error);
                responseCallback.onFailure(null, null);
                return;
            }


            FormBody.Builder formBody = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue());
            }
            FormBody body = formBody.build();
            Log.i("http-request", LoganSquare.serialize(params) + url);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            httpClient.newCall(request).enqueue(responseCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void post(String url, Map<String, String> params, final CallBackListener callback) {
        post(url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onFailure(call, e);
                if (call != null || e != null) {
                    ToastUtil.showLongToast(context, R.string.network_error);
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    callback.onResponse(call, response);
                } catch (Exception e) {
                    onFailure(null, null);
                }
            }
        });
    }

    public void get(String url,Callback responseCallback) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Log.i("http-request",url);
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    public void get(String url, final CallBackListener callback){
        get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call, e);
                if (call != null || e != null) {
                    ToastUtil.showLongToast(context, R.string.network_error);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    callback.onResponse(call, response);
                } catch (Exception e) {
                    onFailure(null, null);
                }
            }
        });
    }


}
