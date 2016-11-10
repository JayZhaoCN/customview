package com.jay.customview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.jay.customview.R;
import com.jay.customview.widgets.CustomProgressBar;

/**
 * Created by Jay on 2016/11/10.
 * CustomProgressBarFragment
 */

public class CustomProgressBarFragment extends BaseFragment {
    private static final String TAG = "CustomProgressBarFragment";

    private String[] mStyleDatas;
    private String[] mTypeDatas;

    private CustomProgressBar mCustomProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if(view != null) {
            mCustomProgressBar = (CustomProgressBar) view.findViewById(R.id.custom_progressbar);
            mStyleDatas = getResources().getStringArray(R.array.custom_progressbar_styles);
            mTypeDatas = getResources().getStringArray(R.array.custom_progressbar_types);

            Spinner mStyleSpinner = (Spinner) view.findViewById(R.id.style_spinner);
            mStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i(TAG, mStyleDatas[i]);
                    switch (mStyleDatas[i]) {
                        case "SingleColor":
                            mCustomProgressBar.setStyle(CustomProgressBar.STYLE_SINGLE_COLOR);
                            break;
                        case "MultiColor":
                            mCustomProgressBar.setStyle(CustomProgressBar.STYLE_MULTI_COLOR);
                            break;
                        case "GradualColor":
                            mCustomProgressBar.setStyle(CustomProgressBar.STYLE_GRADUAL_COLOR);
                            break;
                        default:
                            break;
                    }
                    mCustomProgressBar.postInvalidate();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            Spinner mTypeSpinner = (Spinner) view.findViewById(R.id.type_spinner);
            mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i(TAG, mTypeDatas[i]);
                    switch (mTypeDatas[i]) {
                        case "UNMOVABLE":
                            mCustomProgressBar.setType(CustomProgressBar.TYPE_UNMOVABLE);
                            break;
                        case "MOVABLE":
                            mCustomProgressBar.setType(CustomProgressBar.TYPE_MOVABLE);
                            break;
                        default:
                            break;
                    }
                    mCustomProgressBar.postInvalidate();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        return view;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_custom_progressbar;
    }
}
