package com.example.hugo.njupter.provider;

import android.content.Context;
import android.text.TextUtils;

import com.bluelinelabs.logansquare.LoganSquare;
import com.example.hugo.njupter.R;
import com.example.hugo.njupter.bean.GoodBean;
import com.example.hugo.njupter.utils.ToastUtil;
import com.example.hugo.njupter.utils.Worker;
import com.orhanobut.logger.Logger;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by hugo on 2017/3/26.
 */

public class GoodsPrvdr extends BasePrvdr {
    public GoodsPrvdr(Context context) {
        super(context);
    }


    public void getGoodsList(final String flag , final String cid,String page, final CallBackListener<List<GoodBean>> listener){
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(page)) {
            params.put("offset", page);
        }
        params.put("flag",flag);
        params.put("cid",cid);

        post(APIConstants.getGoodsList, params, new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                if (call != null || e != null) {
                    ToastUtil.showLongToast(context, R.string.network_error);
                }
                Worker.postMain(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(call, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                Logger.json(resultStr);
                final GoodBean.Pojo pojo= LoganSquare.parse(resultStr, GoodBean.Pojo.class);
                Worker.postMain(new Runnable() {
                    @Override
                    public void run() {
                        listener.onComplete(pojo.code, pojo.message, pojo.goods);
                    }
                });
            }
        });


    }

}
