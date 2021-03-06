package com.jay.customview.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jay on 2016/11/30.
 * animatable view
 */

public class AnimatableView extends View {
    private static final String TAG = "AnimatableView";

    private Context mContext;
    private MyAnimatableDrawable mAnimatableDrawable;

    public AnimatableView(Context context) {
        this(context, null);
    }

    public AnimatableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAnimatableDrawable(MyAnimatableDrawable animatableDrawable) {
        this.mAnimatableDrawable = animatableDrawable;
        mAnimatableDrawable.setCallback(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mAnimatableDrawable.setBounds(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mAnimatableDrawable.start();
        mAnimatableDrawable.draw(canvas);
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mAnimatableDrawable
                || super.verifyDrawable(who);
    }
}
