package com.jay.customview.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.jay.customview.R;
import com.jay.customview.fragments.AnimatableFragment;
import com.jay.customview.fragments.BaseFragment;
import com.jay.customview.fragments.MaterialMenuFragment;
import com.jay.customview.fragments.CustomProgressBarFragment;
import com.jay.customview.fragments.CustomTypefaceFragment;
import com.jay.customview.fragments.LineChartFragment;
import com.jay.customview.fragments.TintFragment;
import com.jay.customview.utils.CustomTypeface;
import com.jay.customview.utils.CustomTypefaceBuilder;
import com.jay.customview.utils.Font;
import com.jay.customview.widgets.MyAlertDialog;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseTitleActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    private List<String> mDatas;
    private BaseFragment mBaseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStyle(STYLE.BACK_AND_MORE, R.color.bg_color_red_dark);
        setTitle("Custom View");
        getRightButton().setOnClickListener(this);

        //init colorful actionbar
        initAnimator();

        mDatas = new ArrayList<>();
        mDatas.add("CustomProgressBar");
        mDatas.add("CustomTypeface");
        mDatas.add("Tint");
        mDatas.add("MaterialDesign");
        mDatas.add("LineChart");
        mDatas.add("LoadingAnimation");

        setTitleText("CustomTypeface");

        replaceFragment(new CustomTypefaceFragment());

        //snackbar
        Snackbar.make(findViewById(R.id.fragment_container), "Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Snackbar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "snackbar clicked.");
                    }
                })
                .show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.right:
                showDialog();
                break;
            default:
                break;
        }
    }

    private void setTitleText(String title) {
        CustomTypefaceBuilder builder = new CustomTypefaceBuilder(MainActivity.this);
        builder.append(title, new CustomTypeface(Font.PT_DIN, ContextCompat.getColor(MainActivity.this, R.color.white_70_percent), 18));
        setTitle(builder.build());
    }

    private void showDialog() {
        MyAlertDialog alertDialog = new MyAlertDialog(MainActivity.this, mDatas);
        final Dialog dialog = alertDialog.getDialog();
        alertDialog.setOnItemClickListener(new MyAlertDialog.MyOnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.i(TAG, "position is: " + position);
               setTitleText(mDatas.get(position));
                if(mDatas.get(position).equals("CustomProgressBar")) {
                    if(mBaseFragment != null && mBaseFragment instanceof CustomProgressBarFragment) {
                        //already added
                        Log.i(TAG, "CustomProgressBarFragment already added!");
                        dialog.dismiss();
                        return;
                    }
                    replaceFragment(new CustomProgressBarFragment());
                    dialog.dismiss();
                } else if(mDatas.get(position).equals("CustomTypeface")) {
                    replaceFragment(new CustomTypefaceFragment());
                    dialog.dismiss();
                } else if(mDatas.get(position).equals("Tint")) {
                    if(mBaseFragment != null && mBaseFragment instanceof TintFragment) {
                        //already added
                        Log.i(TAG, "TintFragment already added!");
                        dialog.dismiss();
                        return;
                    }
                    replaceFragment(new TintFragment());
                    dialog.dismiss();
                } else if(mDatas.get(position).equals("MaterialDesign")) {
                    if(mBaseFragment != null && mBaseFragment instanceof MaterialMenuFragment) {
                        //already added
                        Log.i(TAG, "MaterialMenuFragment already added!");
                        dialog.dismiss();
                        return;
                    }
                    replaceFragment(new MaterialMenuFragment());
                    dialog.dismiss();
                } else if(mDatas.get(position).equals("LineChart")) {
                   if(mBaseFragment != null && mBaseFragment instanceof LineChartFragment) {
                       Log.i(TAG, "LineChartFragment already added!");
                       dialog.dismiss();
                       return;
                   }
                    replaceFragment(new LineChartFragment());
                    dialog.dismiss();
                } else if(mDatas.get(position).equals("LoadingAnimation")) {
                    if(mBaseFragment != null && mBaseFragment instanceof AnimatableFragment) {
                        Log.i(TAG, "AnimatableFragment already added!");
                        dialog.dismiss();
                        return;
                    }
                    replaceFragment(new AnimatableFragment());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void replaceFragment(BaseFragment fragment) {
        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mBaseFragment = fragment);
        fragmentTransaction.replace(R.id.fragment_container, mBaseFragment = fragment);
        fragmentTransaction.commit();
    }
}
