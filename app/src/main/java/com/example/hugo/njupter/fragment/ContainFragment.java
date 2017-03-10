package com.example.hugo.njupter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hugo.njupter.R;

/**
 * Created by hugo on 2016/12/4.
 */

public class ContainFragment extends Fragment {
    public String text;

    public ContainFragment() {
    }

    public ContainFragment(String text) {
        this.text=text;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contain_page,container,false);
        TextView textView= (TextView) view.findViewById(R.id.contain_text);
        textView.setText(text);
        return view;

    }
}
