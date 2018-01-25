package com.example.hugo.njupter.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.example.hugo.njupter.R;
import com.example.hugo.njupter.adapter.NearByAdapter;
import com.example.hugo.njupter.adapter.NearUserAdapter;
import com.example.hugo.njupter.bean.ULocation;
import com.example.hugo.njupter.bean.User;
import com.example.hugo.njupter.fragment.ContainFragment;
import com.example.hugo.njupter.provider.CallBackListener;
import com.example.hugo.njupter.provider.LocationPrvdr;
import com.example.hugo.njupter.utils.MySortList;
import com.example.hugo.njupter.utils.SharePrefUtils;
import com.example.hugo.njupter.utils.TimeUtils;
import com.example.hugo.njupter.utils.ToastUtil;
import com.example.hugo.njupter.view.RecycleViewDivider;
import com.example.hugo.njupter.widget.MySwipeRefreshLayout;
import com.example.hugo.njupter.widget.listView.view.WaterDropListView;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by hugo on 2017/3/21.
 */

@EActivity(R.layout.activity_nearby)
public class NearPeopleActivity extends BasicActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final int ACCESS_COARSE_LOCATION_REQUEST_CODE=100 ;
    private final Context context = NearPeopleActivity.this;
    private final String TAG = "--NearbyActivity--";
    @ViewById(R.id.tv_result)
    protected TextView mResultText;

    @ViewById(R.id.uitl_listView)
    RecyclerView listView;

    @ViewById(R.id.util_refresh)
    MySwipeRefreshLayout refreshLayout;

    @ViewById(R.id.empty_view1)
    LinearLayout emptyView;

    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    private SharePrefUtils sharePrefUtils;


    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private ULocation uLocation;

    private LocationPrvdr prvdr;

    User user;

    NearUserAdapter userAdapter;

    LatLonPoint latLonPoint;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    //获取用户的当前位置
                    if (uLocation == null) {
                        uLocation = new ULocation();

                    }
                    //获取坐标
                    if (uLocation.getUid() == null) {
                        uLocation.setUid(user.getId());
                    }
                    uLocation.setLocation(amapLocation.getLongitude()+","+amapLocation.getLatitude() );
                    uLocation.setOnTime(String.valueOf(TimeUtils.getCurrentTimeInLong()));
                    sharePrefUtils.putString("user-location",uLocation.getLocation());
                    prvdr.upload(uLocation, new Callback() {
                        @Override
                        public void onFailure(final Call call, final IOException e) {
                            if (call != null || e != null) {
                                ToastUtil.showLongToast(context, R.string.network_error);
                            }
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //解析数据
                            String resultStr = response.body().string();
                            Logger.json(resultStr);

                            prvdr.getNearBy(uLocation, null, new CallBackListener<List<ULocation>>() {
                                @Override
                                public void onFailure(Call call, Exception e) {

                                }

                                @Override
                                public void onComplete(int code, String reason, List<ULocation> data) {
                                    List<ULocation> temp=data;
                                    MySortList<ULocation> mySortList=new MySortList<ULocation>();
                                    mySortList.sortByMethod(temp,"toIntDist",false);
                                    userAdapter=new NearUserAdapter(temp,emptyView);
                                    listView.setAdapter(userAdapter);

                                }
                            });

                        }
                    });

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @AfterViews
    public void intiViews() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_COARSE_LOCATION_REQUEST_CODE);
        }else{
            initLocation();
        }


        setToolBarText("附近的人");
        setBackImg(R.id.title_back);
        sharePrefUtils=new SharePrefUtils(context);
        user = User.getCurrentUser(NearPeopleActivity.this);
        prvdr = new LocationPrvdr(context);
        refreshLayout.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(context);
        listView.setLayoutManager(linearLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL));
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == userAdapter.getItemCount()) {
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
            }
        });


    }

    private void loadMoreData() {
        ToastUtil.showShortToast(context,"加载更多数据");
    }


    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        //60s更新一次数据
        mLocationOption.setInterval(1000);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mLocationClient!=null)
        mLocationClient.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stopLocation();
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onRefresh() {
        prvdr.getNearBy(uLocation, null, new CallBackListener<List<ULocation>>() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onComplete(int code, String reason, List<ULocation> data) {
                refreshLayout.setRefreshing(false);
                List<ULocation> temp=data;
                MySortList<ULocation> mySortList=new MySortList<ULocation>();
                mySortList.sortByMethod(temp,"toIntDist",false);
                userAdapter.update(data);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_COARSE_LOCATION_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                initLocation();
            }else{
                Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();

            }
        }
    }

}

