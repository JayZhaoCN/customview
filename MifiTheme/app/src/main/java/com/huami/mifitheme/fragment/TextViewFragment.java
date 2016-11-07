package com.huami.mifitheme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huami.mifitheme.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TextViewFragment extends Fragment {

    @BindView(R.id.text1)
    TextView text1;

    public static TextViewFragment newInstance() {
        TextViewFragment fragment = new TextViewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getActivity().setTheme(R.style.WindowBase_DeepPurpleTealTheme);
        return inflater.inflate(R.layout.fragment_text_view, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
//        int themeColor = new ThemeUtils().getThemeColor(getContext(), R.attr.colorAccent);
//        text1.setTextColor(themeColor);
    }


    @OnClick({R.id.text2})
    public void OnViewClick(View view) {
        switch (view.getId()) {
            case R.id.text2:
                
                break;
        }
    }
}
