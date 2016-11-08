package com.jay.customview;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jay.customview.widgets.MyAlertDialog;

public class MainActivity extends BaseTitleActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStyle(STYLE.BACK_AND_MORE, R.color.bg_color_red_dark);
        setTitle("Custom View");
        getRightButton().setOnClickListener(this);
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
        MyAlertDialog dialog = new MyAlertDialog(MainActivity.this);
        final Dialog myDialog = dialog.getDialog(3, "DialogItem1", "DialogItem2", "DialogItem3", "DialogItem4");
        dialog.setmOnItemClickListener(new MyAlertDialog.MyOnItemClickListener() {
            @Override
            public void firstItemClick(View v, Dialog realDialog) {
                Log.i(TAG, "firstClick");
                myDialog.dismiss();
            }

            @Override
            public void secondItemClick(View v, Dialog realDialog) {
                Log.i(TAG, "secondClick");
                myDialog.dismiss();
            }

            @Override
            public void thirdItemClick(View v, Dialog realDialog) {
                Log.i(TAG, "thirdClick");
                myDialog.dismiss();
            }

            @Override
            public void fourthItemClick(View v, Dialog realDialog) {
                Log.i(TAG, "fourthClick");
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
}
