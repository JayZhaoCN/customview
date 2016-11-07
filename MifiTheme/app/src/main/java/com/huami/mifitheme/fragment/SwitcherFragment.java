package com.huami.mifitheme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huami.mifitheme.R;

import butterknife.ButterKnife;


public class SwitcherFragment extends Fragment {


    public static SwitcherFragment newInstance() {
        SwitcherFragment fragment = new SwitcherFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getActivity().setTheme(R.style.WindowBase_DeepPurpleTealTheme);
        return inflater.inflate(R.layout.fragment_switcher, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
//        int themeColor = new ThemeUtils().getThemeColor(getContext(), R.attr.colorAccent);
//        text1.setTextColor(themeColor);
    }


}
