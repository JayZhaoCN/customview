package com.huami.mifitheme.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.huami.mifitheme.R;
import com.huami.mifitheme.TintUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ImageButtonFragment extends Fragment {


    @BindView(R.id.imb_button1)
    ImageButton imbButton1;
    @BindView(R.id.imb_button2)
    ImageButton imbButton2;
    @BindView(R.id.imb_button3)
    ImageButton imbButton3;
    @BindView(R.id.imb_button4)
    ImageButton imbButton4;

    public static ImageButtonFragment newInstance() {
        ImageButtonFragment fragment = new ImageButtonFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getActivity().setTheme(R.style.WindowBase_DeepPurpleTealTheme);
        View view = inflater.inflate(R.layout.fragment_image_button, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
//        int themeColor = new ThemeUtils().getThemeColor(getContext(), R.attr.colorAccent);
//        text1.setTextColor(themeColor);
        ColorStateList drawable = ContextCompat.getColorStateList(getContext(), R.color.drawable_tint);
        TintUtils.tintDrawable(imbButton1, drawable);
        TintUtils.tintDrawable(imbButton2, drawable);
        ColorStateList drawableLight = ContextCompat.getColorStateList(getContext(), R.color.drawable_light_tint);
        TintUtils.tintDrawable(imbButton3, drawableLight);
        TintUtils.tintDrawable(imbButton4, drawableLight);
    }


    @OnClick({R.id.imb_button1, R.id.imb_button2, R.id.imb_button3, R.id.imb_button4})
    public void OnViewClick(View view) {
        switch (view.getId()) {
            case R.id.imb_button1:

                break;
        }
    }
}
