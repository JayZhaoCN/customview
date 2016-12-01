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

import com.jay.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 2016/11/30.
 * vertical line scale drawable
 */

public class VerticalLineScaleDrawable extends MyAnimatableDrawable {
    private static final String TAG = "VerticalLineScaleDrawable";

    //private ValueAnimator mScaleAnimator;
    private Context mContext;
    //the number of column
    private int mColumnNum = 5;
    //delay time
    private long[] mStartDelays = {0, 100, 200, 300, 400};
    private float[] mScale = new float[5];

    private Paint mPaint;

    private List<ValueAnimator> mAnimators = new ArrayList<>();

    private Rect mBounds;

    private int perScale;
    private int maxHeight;
    private int currentHeight;
    private RectF rectF = new RectF();

    private int mDrawableAlpha = 255;

    public VerticalLineScaleDrawable(Context context) {
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(context, R.color.blue_light));
        initAnimator();
    }

    private void initAnimator() {
        for(int i=0; i<mColumnNum; i++) {
            ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.4f, 1f);
            animator.setDuration(1000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setStartDelay(mStartDelays[i]);
            final int finalI = i;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mScale[finalI] = (float) animation.getAnimatedValue();
                    if(finalI == 0) {
                        invalidateSelf();
                    }
                }
            });
            mAnimators.add(animator);
        }
    }

    /**
     * ValueAnimator.isRunning() VS ValueAnimator.isStarted()
     */
    @Override
    public void start() {
        for (ValueAnimator animator : mAnimators) {
            if(!animator.isStarted()) {
                animator.start();
            }
        }
    }

    @Override
    public void stop() {
        stopAnimator();
    }

    public void stopAnimator() {
        for(ValueAnimator animator : mAnimators) {
            if(animator.isRunning()) {
                animator.removeAllUpdateListeners();
                animator.end();
            }
        }
    }

    @Override
    public boolean isRunning() {
        for(ValueAnimator animator : mAnimators) {
            if(animator.isRunning()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        for(int i=0; i<mColumnNum; i++) {
            currentHeight = (int) (maxHeight * mScale[i]);
            rectF.left = perScale * (i * 2 + 1);
            rectF.right = rectF.left + perScale;
            rectF.top = (getHeight() / 2 - currentHeight / 2);
            rectF.bottom = rectF.top + currentHeight;
            canvas.drawRoundRect(rectF, 7, 7, mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mDrawableAlpha = alpha;
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
        super.onBoundsChange(bounds);
        mBounds = new Rect(bounds);
        perScale = getWidth() / (mColumnNum * 2 + 1);
        maxHeight = (int) (getHeight() * 0.7);
    }

    public int getHeight() {
        if(mBounds != null) {
            return mBounds.height();
        }
        return -1;
    }

    public int getWidth() {
        if(mBounds != null) {
            return mBounds.width();
        }
        return -1;
    }
}
