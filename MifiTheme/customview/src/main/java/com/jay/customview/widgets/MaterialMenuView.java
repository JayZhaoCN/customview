package com.jay.customview.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.jay.customview.R;

/**
 * Created by Jay on 2016/12/8.
 * material menu
 */

public class MaterialMenuView extends View {

    private MaterialMenuDrawable mDrawable;

    private int mIndex = 0;

    private MaterialMenuDrawable.State[] states = {MaterialMenuDrawable.State.X, MaterialMenuDrawable.State.BURGER};

    public void setPossibleState(MaterialMenuDrawable.State ... states) {
        this.states = states;
    }

    public MaterialMenuView(Context context) {
        this(context, null);
    }

    public MaterialMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MaterialMenuView, 0, defStyleAttr);
        int lineColor = ta.getColor(R.styleable.MaterialMenuView_line_color, ContextCompat.getColor(context, R.color.blue_light));
        ta.recycle();

        mDrawable = new MaterialMenuDrawable(context, lineColor);
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
        mIndex = mIndex % states.length;
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mDrawable
                || super.verifyDrawable(who);
    }
}
