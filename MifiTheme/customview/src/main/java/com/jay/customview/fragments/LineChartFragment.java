package com.jay.customview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jay.customview.R;

/**
 * Created by Jay on 2016/11/24.
 * line chart fragment
 */

public class LineChartFragment extends BaseFragment {
    private static final String TAG = "LineChartFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_line_chart;
    }
}
