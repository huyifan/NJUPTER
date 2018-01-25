package com.example.hugo.njupter.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bluelinelabs.logansquare.LoganSquare;
import com.example.hugo.njupter.R;
import com.example.hugo.njupter.adapter.EventAdapter;
import com.example.hugo.njupter.adapter.GoodAdapter;
import com.example.hugo.njupter.bean.EventBean;
import com.example.hugo.njupter.bean.GoodBean;
import com.example.hugo.njupter.interf.OnRefreshListener;
import com.example.hugo.njupter.provider.APIConstants;
import com.example.hugo.njupter.provider.BasePrvdr;
import com.example.hugo.njupter.provider.CallBackListener;
import com.example.hugo.njupter.provider.GoodsPrvdr;
import com.example.hugo.njupter.utils.ToastUtil;
import com.example.hugo.njupter.utils.Worker;
import com.example.hugo.njupter.widget.MySwipeRefreshLayout;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 实现下拉刷新的listview
 * Created by hugo on 2017/3/26.
 */

@EFragment(R.layout.fragment_contain_page)
public class TemplateFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewById(R.id.my_list)
    public RecyclerView listView;
    @ViewById(R.id.empty_view)
    public TextView emptyView;

    @ViewById(R.id.refresh_layout)
    public MySwipeRefreshLayout refreshLayout;
    @FragmentArg
    public String flag;

    @FragmentArg
    public String cid;

    private GoodsPrvdr goodsPrvdr;

    private GoodAdapter goodAdapter;
    private EventAdapter eventAdapter;
    private BasePrvdr basePrvdr;
    private String f;
    private String c;


    private List<EventBean> events;
    private String offset="1";

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            events= (List<EventBean>) msg.obj;
            eventAdapter.update(events);
            refreshLayout.setRefreshing(false);

        }
    };
    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        getData();
    }


    private CallBackListener<List<GoodBean>> myListener=new CallBackListener<List<GoodBean>>() {
        @Override
        public void onFailure(Call call, Exception e) {
            ToastUtil.showShortToast(getContext(),"网络错误!");
            refreshLayout.setRefreshing(false);
        }


        @Override
        public void onComplete(int code, String reason, List<GoodBean> data) {
            super.onComplete(code, reason, data);
            goodAdapter.update(data);
            refreshLayout.setRefreshing(false);
        }

    };

    @AfterViews
    public void initView(){
        goodsPrvdr=new GoodsPrvdr(getContext());
        basePrvdr=new BasePrvdr(getContext());
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);

        ArrayList<GoodBean> goods=new ArrayList<>();
        goodAdapter=new GoodAdapter(goods,emptyView);

        events=new ArrayList<>();
        eventAdapter=new EventAdapter(events,emptyView);
        //获取数据
        getData();

        if(f.equals("0")||f.equals("1")){
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.setLayoutManager(layoutManager);
            listView.setItemAnimator(new DefaultItemAnimator());
            listView.addItemDecoration(new ContainFragment.Decoration());
            listView.setAdapter(goodAdapter);
        }else{

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.setLayoutManager(layoutManager);
            listView.setItemAnimator(new DefaultItemAnimator());
            listView.addItemDecoration(new ContainFragment.Decoration());
            listView.setAdapter(eventAdapter);
        }


    }

    private void getData() {

        if(flag.equals("二手市场")){
            f="1";
            switch (cid){
                case "生活用品":
                    c="1";
                    break;
                case "图书资料":
                    c="2";
                    break;
                case "数码产品":
                    c="3";
                    break;
                case "其他物品":
                    c="4";
                    break;
            }

            goodsPrvdr.getGoodsList(f,c,offset,myListener);
        }else if(flag.equals("失物招领")){
            f="0";
            c=cid;
            goodsPrvdr.getGoodsList(f,c,offset,myListener);


        }else if(flag.equals("活动预告")){
            f="3";
            c=cid;


            Map<String,String> param=new HashMap<>();
            param.put("cid",c);
            param.put("offset",offset);
            basePrvdr.post(APIConstants.getEventList, param, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String resultStr = response.body().string();
                    Logger.json(resultStr);
                    final EventBean.Pojo pojo= LoganSquare.parse(resultStr, EventBean.Pojo.class);
                    Message msg=new Message();
                    msg.obj=pojo.events;
                    handler.sendMessage(msg);


                }
            });

        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
