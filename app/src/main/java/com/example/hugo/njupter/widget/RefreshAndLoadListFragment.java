package com.example.hugo.njupter.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.hugo.njupter.R;
import com.example.hugo.njupter.adapter.RecyclerAdapter;
import com.example.hugo.njupter.fragment.BaseFragment;
import com.zhy.autolayout.AutoLinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by hugo on 2017/3/31.
 */
@EFragment(R.layout.view_refresh_load_list)
public class RefreshAndLoadListFragment<T> extends BaseFragment {
    @ViewById(R.id.uitl_listView)
    RecyclerView listView;

    @ViewById(R.id.util_refresh)
    MySwipeRefreshLayout refreshLayout;

    private List<T> datas;

    private RecyclerAdapter<T> adapter;

    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;

    public CallBack callBack;

    public interface CallBack{
      void loadMoreData();
    }

   @AfterViews
    public void initViews(){
       linearLayoutManager=new LinearLayoutManager(getContext());
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    callBack.loadMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
            }
        });
   }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
