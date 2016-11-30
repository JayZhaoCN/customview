package com.jay.customview.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.jay.customview.R;

/**
 * Created by Jay on 2016/11/29.
 * pacman drawable
 */

public class PacmanDrawable extends MyAnimatableDrawable {
    private static final String TAG = "PacmanDrawable";

    private Rect mBounds;

    private Paint mPaint;

    private ValueAnimator mAnimator;
    private ValueAnimator mCircleTranslateAnimator;
    private ValueAnimator mCircleAlphaAnimator;

    private int mAngle;
    private int mTranslateX;
    private int mAlpha;

    //TODO alpha of drawable
    private int mDrawableAlpha = 255;

    private static final int ANIMATOR_DURATION = 400;

    public PacmanDrawable(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(context, R.color.blue_dark));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void start() {
        Log.i(TAG, "start");
        initAnimator();
        if(!mAnimator.isStarted()) {
            mAnimator.start();
        }
        if(!mCircleTranslateAnimator.isStarted()) {
            mCircleTranslateAnimator.start();
        }
        if(!mCircleAlphaAnimator.isStarted()) {
            mCircleAlphaAnimator.start();
        }
    }



    private void initAnimator() {

        if(mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(45, 0);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mAngle = (int) valueAnimator.getAnimatedValue();
                    invalidateSelf();
                }
            });
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setDuration(ANIMATOR_DURATION);
            mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        }

        if(mCircleTranslateAnimator == null) {
            //TODO how to get the size of drawable?
            mCircleTranslateAnimator = ValueAnimator.ofInt(getWidth()- 50, 150 + 24);
            mCircleTranslateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mTranslateX = (int) valueAnimator.getAnimatedValue();
                    invalidateSelf();
                }
            });
            mCircleTranslateAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mCircleTranslateAnimator.setDuration(ANIMATOR_DURATION*2);
            mCircleTranslateAnimator.setRepeatMode(ValueAnimator.RESTART);
        }

        if(mCircleAlphaAnimator == null) {
            mCircleAlphaAnimator = ValueAnimator.ofInt(255, 0);
            mCircleAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mAlpha = (int) valueAnimator.getAnimatedValue();
                    invalidateSelf();
                }
            });
            mCircleAlphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mCircleAlphaAnimator.setDuration(ANIMATOR_DURATION*2);
            mCircleAlphaAnimator.setRepeatMode(ValueAnimator.RESTART);
        }

    }

    @Override
    public void stop() {
        Log.i(TAG, "stop");
        stopAnimators();
    }

    private void stopAnimators() {
        if(mAnimator != null && mAnimator.isStarted()) {
            mAnimator.removeAllUpdateListeners();
            mAnimator.end();
        }
        if(mCircleAlphaAnimator != null && mCircleAlphaAnimator.isStarted()) {
            mCircleAlphaAnimator.removeAllUpdateListeners();
            mCircleAlphaAnimator.end();
        }
        if(mCircleTranslateAnimator != null && mCircleTranslateAnimator.isStarted()) {
            mCircleTranslateAnimator.removeAllUpdateListeners();
            mCircleTranslateAnimator.end();
        }
    }

    @Override
    public boolean isRunning() {
        return mAnimator.isRunning() || mCircleAlphaAnimator.isRunning() || mCircleTranslateAnimator.isRunning();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Log.i(TAG, "draw");
        float x = getWidth() / 2;
        float y = getHeight() / 2;

        //main thread
        Log.i(TAG, "current thread is: " + Thread.currentThread());

        canvas.save();
        mPaint.setAlpha(255);
        canvas.translate(x, y);
        RectF rectF = new RectF(-x/1.7f, -y/1.7f, x/1.7f, y/1.7f);
        canvas.drawArc(rectF, mAngle, 270 + (45 - mAngle) * 2, true, mPaint);
        canvas.restore();

        mPaint.setAlpha(mAlpha);
        canvas.drawCircle(mTranslateX, getHeight() / 2, 20, mPaint);
    }

    @Override
    public void setAlpha(int i) {
        mDrawableAlpha = i;
    }

    @Override
    public int getAlpha() {
        return mDrawableAlpha;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        Log.i(TAG, "onBoundsChange");
        super.onBoundsChange(bounds);
        mBounds = new Rect(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    private int getHeight() {
        if(mBounds != null) {
            return mBounds.height();
        }
        return -1;
    }

    private int getWidth() {
        if(mBounds != null) {
            return mBounds.width();
        }
        return -1;
    }
}
