package com.huami.mifitheme.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huami.mifitheme.R;
import com.huami.mifitheme.TintUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ImageViewFragment extends Fragment {

    @BindView(R.id.imv_image1)
    ImageView imvImage1;
    @BindView(R.id.imv_image2)
    ImageView imvImage2;
    @BindView(R.id.imv_image3)
    ImageView imvImage3;
    @BindView(R.id.imv_image4)
    ImageView imvImage4;
    @BindView(R.id.imv_image5)
    ImageView imvImage5;
    @BindView(R.id.imv_image6)
    ImageView imvImage6;
    @BindView(R.id.imv_image7)
    ImageView imvImage7;
    @BindView(R.id.imv_image8)
    ImageView imvImage8;

    public static ImageViewFragment newInstance() {
        ImageViewFragment fragment = new ImageViewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getActivity().setTheme(R.style.WindowBase_DeepPurpleTealTheme);
        View view = inflater.inflate(R.layout.fragment_image_view, container, false);
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
        TintUtils.tintDrawable(imvImage1, drawable);
        TintUtils.tintDrawable(imvImage2, drawable);
        ColorStateList drawableLight = ContextCompat.getColorStateList(getContext(), R.color.drawable_light_tint);
        TintUtils.tintDrawable(imvImage3, drawableLight);
        TintUtils.tintDrawable(imvImage4, drawableLight);



    }


    @OnClick({R.id.imv_image1, R.id.imv_image2, R.id.imv_image3, R.id.imv_image4, R.id.imv_image5, R.id.imv_image6, R.id.imv_image7, R.id.imv_image8})
    public void onViewClick(View view) {
        Log.d("TAG", "ImageView:" + view.getId());
    }
}
