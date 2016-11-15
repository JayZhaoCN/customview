package com.jay.customview.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jay.customview.R;

/**
 * Created by Jay on 2016/11/14.
 * tint fragment
 */

public class TintFragment extends BaseFragment {
    private static final String TAG = "TintFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        /*
        if(view != null) {
            *//**
             * 如何在代码中使用Tint
             *//*
            ImageView image1 = (ImageView) view.findViewById(R.id.image1);
            ImageView image2 = (ImageView) view.findViewById(R.id.image2);

            //两个ImageView引用的是同一个drawable资源，系统为了优化资源，在内存中只保存了一份drawable拷贝
            //修改drawable会导致两个imageView同时被修改
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.icon_status_weight);
            image2.setImageDrawable(drawable);
            //使用Drawable.mutate()避免这一问题
            DrawableCompat.setTint(drawable.mutate(), ContextCompat.getColor(getActivity(), R.color.personinfo_color_yellow));
            image1.setImageDrawable(drawable);

            //Drawable.ConstantState state = drawable.getConstantState();
            //Log.i(TAG, "state is null: " + (state == null));
            //Drawable drawable1 = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
            //drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            //DrawableCompat.setTint(drawable1, ContextCompat.getColor(getActivity(), R.color.personinfo_color_yellow));
            //image1.setImageDrawable(drawable1);
            //image2.setImageDrawable(drawable1);
        }
        */
        return view;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_tint;
    }
}
