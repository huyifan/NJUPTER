package com.example.hugo.njupter.widget.Indicatorviewpager;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by hugo on 2017/2/9.
 */

public abstract class IndicatorAdapter {
    private Set<DataSetObserver> observers=new LinkedHashSet<>();
    public abstract int getCount();
    public abstract View getView(int position, View convertView, ViewGroup parent);

    public void notifyDataSetChanged() {
        for (DataSetObserver dataSetObserver : observers) {
            dataSetObserver.onChange();
        }
    }

    public void registDataSetObserver(DataSetObserver observer) {
        observers.add(observer);
    }

    public void unRegistDataSetObserver(DataSetObserver observer) {
        observers.remove(observer);
    }

    /**
     * 数据源观察者
     */
    static interface DataSetObserver {
        public void onChange();
    }

}
