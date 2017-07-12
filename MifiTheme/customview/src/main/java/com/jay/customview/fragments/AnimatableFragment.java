package com.jay.customview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jay.customview.R;
import com.jay.customview.widgets.AnimatableView;
import com.jay.customview.widgets.JayProgressView;
import com.jay.customview.widgets.PacmanDrawable;
import com.jay.customview.widgets.VerticalLineScaleDrawable;

/**
 * Created by Jay on 2016/11/29.
 * animatable fragment
 */

public class AnimatableFragment extends BaseFragment {
    private static final String TAG = "AnimatableFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);

        if (view != null) {
            AnimatableView animatableView1 = (AnimatableView) view.findViewById(R.id.animatable_view_1);
            animatableView1.setAnimatableDrawable(new PacmanDrawable(getActivity()));

            AnimatableView animatableView2 = (AnimatableView) view.findViewById(R.id.animatable_view_2);
            animatableView2.setAnimatableDrawable(new VerticalLineScaleDrawable(getActivity()));

            JayProgressView jayProgressView = (JayProgressView) view.findViewById(R.id.progress_view);
            jayProgressView.setProgress(90);
        }

        return view;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_animatable;
    }
}
