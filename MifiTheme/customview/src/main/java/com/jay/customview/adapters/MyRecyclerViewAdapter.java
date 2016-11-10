package com.jay.customview.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jay.customview.R;
import com.jay.customview.widgets.MyAlertDialog;

import java.util.List;

/**
 * Created by Jay on 2016/11/10.
 * MyRecyclerViewAdapter
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "MyAdapter";

    private List<String> mDatas;
    private LayoutInflater mLayoutInflater;
    private MyAlertDialog.MyOnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(MyAlertDialog.MyOnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public MyRecyclerViewAdapter(Context context, List<String> datas) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mItemText.setText(mDatas.get(position));
        if(position == mDatas.size() - 1) {
            holder.mDividerLine.setVisibility(View.GONE);
        }
        if(mOnItemClickListener != null) {
            holder.mItemText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemText;
        private View mDividerLine;

        MyViewHolder(View itemView) {
            super(itemView);
            mItemText = (TextView) itemView.findViewById(R.id.text_item);
            mDividerLine = itemView.findViewById(R.id.divider_line);
        }
    }
}
