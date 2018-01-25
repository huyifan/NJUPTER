package com.example.hugo.njupter.provider;

import android.content.Context;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;
import com.example.hugo.njupter.R;
import com.example.hugo.njupter.bean.ULocation;
import com.example.hugo.njupter.utils.ToastUtil;
import com.example.hugo.njupter.utils.Worker;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by hugo on 2017/4/14.
 */

public class LocationPrhttp://172.20.183.62:9000/vdr extends BasePrvdr {
    public LocationPrvdr(Context context) {
        super(context);
    }

    public void getNearBy(ULocation location,String range,final CallBackListener<List<ULocation>> listener){
        Log.e("---", "getNearBy: "+APIConstants.parseNearByAPI(location, range));
        get(APIConstants.parseNearByAPI(location, range), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (call != null || e != null) {
                    ToastUtil.showLongToast(context, R.string.network_error);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                Logger.json(resultStr);
                final ULocation.Pojo pojo = LoganSquare.parse(resultStr, ULocation.Pojo.class);
                if (pojo != null) {
                    Worker.postMain(new Runnable() {
                        @Override
                        public void run() {
                            listener.onComplete(pojo.code, pojo.message, pojo.locations);
                        }
                    });
                }
            }
        });
    }
    public void upload(ULocation uLocation,Callback callBack){
        Log.e("--", "upload: "+APIConstants.parseUpAPI(uLocation));
        get(APIConstants.parseUpAPI(uLocation), callBack);
    }

    public void getByUserId(String uid,Callback call){
        Log.e("--", "upload: "+APIConstants.getByUserId(uid));
        get(APIConstants.getByUserId(uid),call);

    }

}
