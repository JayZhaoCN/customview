package com.jay.customview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jay.customview.R;
import com.jay.customview.widgets.MaterialMenuDrawable;
import com.jay.customview.widgets.MaterialMenuView;

/**
 * Created by Jay on 2016/11/21.
 * card view fragment
 */

public class MaterialMenuFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(getLayout(), container, false);
        if(view != null) {
            final MaterialMenuView materialMenuView = (MaterialMenuView) view.findViewById(R.id.material_view);
            materialMenuView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialMenuView.changeState();
                }
            });
        }
        return view;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_material_menu;
    }
}
