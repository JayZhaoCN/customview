package com.jay.customview.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Jay on 2016/11/30.
 * animatable view
 */

public class AnimatableView extends View {
    private static final String TAG = "AnimatableView";

    private PacmanDrawable mPacmanDrawable;
    private Context mContext;

    public AnimatableView(Context context) {
        this(context, null);
    }

    public AnimatableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPacmanDrawable = new PacmanDrawable(context);
        mPacmanDrawable.setCallback(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPacmanDrawable.setBounds(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw");
        super.onDraw(canvas);
        mPacmanDrawable.draw(canvas);
        mPacmanDrawable.start();

    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mPacmanDrawable
                || super.verifyDrawable(who);
    }
}
