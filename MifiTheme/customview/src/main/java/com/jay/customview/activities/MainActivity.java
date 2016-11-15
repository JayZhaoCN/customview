package com.jay.customview.activities;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jay.customview.R;
import com.jay.customview.fragments.BaseFragment;
import com.jay.customview.fragments.CustomProgressBarFragment;
import com.jay.customview.fragments.CustomTypefaceFragment;
import com.jay.customview.fragments.TintFragment;
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
        mDatas.add("to be done");
        mDatas.add("to be done");
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

    private void showDialog() {
        MyAlertDialog alertDialog = new MyAlertDialog(MainActivity.this, mDatas);

        final Dialog dialog = alertDialog.getDialog();
        alertDialog.setOnItemClickListener(new MyAlertDialog.MyOnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.i(TAG, "position is: " + position);
                setTitle(mDatas.get(position));
                if(mDatas.get(position).equals("CustomProgressBar")) {
                    if(mBaseFragment != null && mBaseFragment instanceof CustomProgressBarFragment) {
                        //already added
                        Log.i(TAG, "CustomProgressBarFragment already added!");
                        dialog.dismiss();
                        return;
                    }
                    FragmentManager manager = MainActivity.this.getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, mBaseFragment = new CustomProgressBarFragment());
                    fragmentTransaction.commit();
                    dialog.dismiss();
                } else if(mDatas.get(position).equals("CustomTypeface")) {
                    FragmentManager manager = MainActivity.this.getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, mBaseFragment = new CustomTypefaceFragment());
                    fragmentTransaction.commit();
                    dialog.dismiss();
                } else if(mDatas.get(position).equals("Tint")) {
                    if(mBaseFragment != null && mBaseFragment instanceof TintFragment) {
                        //already added
                        Log.i(TAG, "TintFragment already added!");
                        dialog.dismiss();
                        return;
                    }
                    FragmentManager manager = MainActivity.this.getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, mBaseFragment = new TintFragment());
                    fragmentTransaction.commit();
                    dialog.dismiss();
                } else if(mDatas.get(position).equals("to be done")) {
                    Log.i(TAG, "item to be done");
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}
