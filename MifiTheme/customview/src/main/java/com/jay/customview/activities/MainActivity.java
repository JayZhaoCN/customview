package com.jay.customview.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import com.jay.customview.R;
import com.jay.customview.fragments.BaseFragment;
import com.jay.customview.fragments.CustomProgressBarFragment;
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
        mDatas.add("to be done");
        mDatas.add("to be done");
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
                if(mDatas.get(position).equals("CustomProgressBar")) {
                    if(mBaseFragment != null && mBaseFragment instanceof CustomProgressBarFragment) {
                        //already added
                        Log.i(TAG, "CustomProgressBarFragment already added!");
                        dialog.dismiss();
                        return;
                    }
                    setTitle(mDatas.get(position));
                    FragmentManager manager = MainActivity.this.getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, mBaseFragment = new CustomProgressBarFragment());
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
