package com.jay.customview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jay.customview.R;
import com.jay.customview.utils.CustomTypeface;
import com.jay.customview.utils.CustomTypefaceBuilder;
import com.jay.customview.utils.Font;

/**
 * Created by Jay on 2016/11/14.
 * custom typeface fragment
 */

public class CustomTypefaceFragment extends BaseFragment {
    private static final String TAG = "CustomTypefaceFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(view != null) {
            TextView mText = (TextView) view.findViewById(R.id.text);

            CustomTypefaceBuilder builder = new CustomTypefaceBuilder(getActivity());
            builder.append("33", new CustomTypeface(Font.PT_DIN, ContextCompat.getColor(getActivity(), R.color.agree_text_color), 21))
                    .append(" kg", new CustomTypeface(Font.PT_DIN, ContextCompat.getColor(getActivity(), R.color.bg_color_red), 11));

            mText.setText(builder.build());

            TextView text1 = (TextView) view.findViewById(R.id.text1);

            CustomTypefaceBuilder builder1 = new CustomTypefaceBuilder(getActivity());
            builder1.append("8", new CustomTypeface(Font.PT_DIN, ContextCompat.getColor(getActivity(), R.color.blue_light),30))
                    .append(" 时 ", new CustomTypeface(Font.DEFAULT, ContextCompat.getColor(getActivity(), R.color.agree_text_color), 9))
                    .append("57", new CustomTypeface(Font.PT_DIN, ContextCompat.getColor(getActivity(), R.color.blue_light), 30))
                    .append(" 分 ", new CustomTypeface(Font.DEFAULT, ContextCompat.getColor(getActivity(), R.color.agree_text_color), 9));

            text1.setText(builder1.build());

            TextView text2 = (TextView) view.findViewById(R.id.text2);

            CustomTypefaceBuilder builder2 = new CustomTypefaceBuilder(getActivity());
            builder2.append("Hi",
                            new CustomTypeface(Font.DIN_MED, ContextCompat.getColor(getActivity(), R.color.agree_text_color), 50))
                    .append("Android",
                            new CustomTypeface(Font.DEFAULT, ContextCompat.getColor(getActivity(), R.color.blue_light), 25))
                    .append("Custom",
                            new CustomTypeface(Font.DIN_MED, ContextCompat.getColor(getActivity(), R.color.colorPrimary), 30))
                    .append("Typeface",
                            new CustomTypeface(Font.PT_DIN, ContextCompat.getColor(getActivity(), R.color.device_shoes_bg), 40));

            text2.setText(builder2.build());
        }

        return view;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_custom_typeface;
    }
}
