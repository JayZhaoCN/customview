package com.jay.customview.widgets;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jay.customview.R;
import com.jay.customview.adapters.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 16-4-12.
 * MyAlertDialog
 */
public class MyAlertDialog {
    private Context mContext;

    private MyRecyclerViewAdapter mAdapter;

    public MyAlertDialog(Context context, List<String> datas) {
        mContext = context;
        mAdapter = new MyRecyclerViewAdapter(mContext, datas);
    }

    public Dialog getDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.dialog2);
        Window window = mDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            //设置窗口弹出动画
            window.setWindowAnimations(R.style.dialogWindowAnim);
            window.setGravity(Gravity.END | Gravity.TOP);
            //默认是居中显示的，y为正，则向下移动，y为负，则向上移动。x正，则向右移动，x负，则向左移动。
            lp.y = mContext.getResources().getDimensionPixelSize(R.dimen.title_height);
            lp.x = 0;
            window.setAttributes(lp);
        }

        mDialog.setContentView(R.layout.dialog_item);
        RecyclerView mRecyclerView = (RecyclerView) mDialog.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        return mDialog;
    }

    public void setOnItemClickListener(MyOnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
    }

    public interface MyOnItemClickListener {
        void onClick(int position);
    }
}
