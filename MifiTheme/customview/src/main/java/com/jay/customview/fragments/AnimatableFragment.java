package com.jay.customview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jay.customview.R;
import com.jay.customview.widgets.PacmanDrawable;

/**
 * Created by Jay on 2016/11/29.
 */

public class AnimatableFragment extends BaseFragment {
    private static final String TAG = "AnimatableFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        if(view != null) {
            ImageView img = (ImageView) view.findViewById(R.id.img);
            PacmanDrawable pacmanDrawable = new PacmanDrawable(getActivity());
            img.setImageDrawable(pacmanDrawable);
            pacmanDrawable.start();

            pacmanDrawable.setAlpha(25);
        }

        return view;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_animatable;
    }
}
