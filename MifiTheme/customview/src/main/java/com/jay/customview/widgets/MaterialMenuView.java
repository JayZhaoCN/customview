package com.jay.customview.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.jay.customview.R;

/**
 * Created by Jay on 2016/12/8.
 */

public class MaterialMenuView extends View {
    private static final String TAG = "MaterialMenuView";

    private Context mContext;
    private MaterialMenuDrawable mDrawable;

    private int mIndex = 0;

    private MaterialMenuDrawable.State[] states = {MaterialMenuDrawable.State.ARROW, MaterialMenuDrawable.State.BURGER, MaterialMenuDrawable.State.CHECK, MaterialMenuDrawable.State.BURGER};

    public MaterialMenuView(Context context) {
        this(context, null);
    }

    public MaterialMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDrawable = new MaterialMenuDrawable(context, R.color.blue_dark);
        mDrawable.setCallback(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawable.setBounds(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawable.draw(canvas);
    }

    public void changeState() {
        mDrawable.setState(states[mIndex]);
        mIndex ++;
        mIndex = mIndex % 4;
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mDrawable
                || super.verifyDrawable(who);
    }
}
