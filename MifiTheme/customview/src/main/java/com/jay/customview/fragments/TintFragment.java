package com.jay.customview.fragments;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
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
        Log.i(TAG, "onCreateView");

        /*
        if(view != null) {
            //使用Drawable.mutate()方法可以避免Drawable实例被关联修改的问题。
            //如果直接使用drawable设置tint，第一次打开应用，两个imageView似乎并不会出现同时被修改的情况。
            //但第二次打开应用，就会出现上述问题。

            ImageView image1 = (ImageView) view.findViewById(R.id.image1);
            //ImageView image2 = (ImageView) view.findViewById(R.id.image2);

            //两个ImageView引用的是同一个drawable资源，系统为了优化资源，在内存中只保存了一份drawable拷贝
            //修改drawable会导致两个imageView同时被修改
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.step_new_record);
            //使用Drawable.mutate()避免这一问题
            DrawableCompat.setTint(drawable.mutate(), ContextCompat.getColor(getActivity(), R.color.bg_color_steps));
            image1.setImageDrawable(drawable);
        }
        */

        //总结：ImageView的tint属性，在4.0.3上就开始支持，但只支持单色的tint
        //5.0之后，可以支持colorlist。
        //DrawableCompat类：是Drawable的向下兼容类，为了在6.0以下兼容tint属性而使用的。

        //代码设置selector tint，6.0以下可以使用
        if(view != null) {
            ImageView imageView = (ImageView) view.findViewById(R.id.image1);
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.step_new_record);
            int[] colors = new int[]{ContextCompat.getColor(getActivity(), R.color.blue_light), ContextCompat.getColor(getActivity(), R.color.bg_color_red)};
            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_pressed};
            states[1] = new int[]{};
            ColorStateList colorList = new ColorStateList(states, colors);
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(states[0], drawable);
            stateListDrawable.addState(states[1], drawable);
            Drawable.ConstantState state = stateListDrawable.getConstantState();
            drawable = DrawableCompat.wrap(state == null ? stateListDrawable : state.newDrawable()).mutate();
            DrawableCompat.setTintList(drawable, colorList);
            imageView.setImageDrawable(drawable);
        }


        return view;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_tint;
    }
}
