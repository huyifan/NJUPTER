package com.example.hugo.njupter.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.adapter.GoodAdapter;
import com.example.hugo.njupter.bean.GoodBean;
import com.example.hugo.njupter.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 2016/12/4.
 */

public class ContainFragment extends Fragment {
    public String text;

    public String flag;

    private RecyclerView listView;


    private GoodAdapter goodAdapter;

    private View emptyView;

    public ContainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contain_page,container,false);
        listView= (RecyclerView) view.findViewById(R.id.my_list);
        emptyView=view.findViewById(R.id.empty_view);
        ArrayList<GoodBean> goods=new ArrayList<>();
        for(int i=0;i<3;i++){
            GoodBean goodBean=new GoodBean();
            goodBean.setNickName("1231");
            goodBean.setDate("2016-12-12");
            goodBean.setDes("31adadadadaadasdsadsadasdasdasdsadasdasdasdda");
            goodBean.setTitle("第"+i+"个");
            goodBean.setPoint("213");
            goods.add(goodBean);


        }

        goodAdapter=new GoodAdapter(goods,emptyView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(layoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.addItemDecoration(new Decoration());
        listView.setAdapter(goodAdapter);
        return view;

    }
    public static class Decoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int offset = ScreenUtils.dp2px(parent.getContext(), 0.5f);
            outRect.set(0, offset, 0, offset);
        }
    }

}
