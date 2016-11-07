package com.huami.mifitheme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.huami.mifitheme.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ButtonFragment extends Fragment {


    @BindView(R.id.btn_small)
    Button btnSmall;

    public static ButtonFragment newInstance() {
        ButtonFragment fragment = new ButtonFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }


    @OnClick({R.id.btn_small})
    public void OnViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_small:

                break;
        }
    }
}
