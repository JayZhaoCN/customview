package com.jay.customview.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jay.customview.R;
import com.jay.customview.utils.Utils;

/**
 * @author zhaojiabao 2017/7/12
 *         仿百度贴吧的LoadingView
 */

public class CoolLoadingView extends View {
    private static final float CREST_PERCENT = 0.15f;

    private Context mContext;
    private int mWidth, mHeight;
    private Paint mPaint;
    private Paint mTextPaint;
    private float mPercent = 0;
    private Path mPath;
    private Path mCirclePath;
    private float mTextY;

    public CoolLoadingView(Context context) {
        this(context, null);
    }

    public CoolLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoolLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.blue_light));
        mPath = new Path();

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(ContextCompat.getColor(mContext, R.color.blue_light));
        mTextPaint.setTextSize(Utils.sp2px(mContext, 48));
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        startLoading();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextY = (mHeight - metrics.bottom - metrics.top) / 2;
        mCirclePath = new Path();
        mCirclePath.addCircle(mWidth / 2, mHeight / 2, mWidth / 2, Path.Direction.CCW);
    }

    private Path getPath(float percent) {
        float x = (percent - 1) * mWidth;
        mPath.reset();
        mPath.moveTo(x, mHeight / 2);
        //第一段曲线，第二象限
        mPath.rQuadTo(mWidth / 4, mHeight * CREST_PERCENT, mWidth / 2, 0);
        mPath.rQuadTo(mWidth / 4, -mHeight * CREST_PERCENT, mWidth / 2, 0);

        //第二段曲线，在第一象限
        mPath.rQuadTo(mWidth / 4, mHeight * CREST_PERCENT, mWidth / 2, 0);
        mPath.rQuadTo(mWidth / 4, -mHeight * CREST_PERCENT, mWidth / 2, 0);

        mPath.lineTo(x + mWidth * 2, mHeight);
        mPath.lineTo(x, mHeight);
        mPath.close();
        return mPath;
    }

    private void startLoading() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                postInvalidateOnAnimation();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mTextPaint.setColor(ContextCompat.getColor(mContext, R.color.blue_light));
        canvas.drawText("贴", mWidth / 2, mTextY, mTextPaint);
        canvas.save();
        canvas.clipPath(mCirclePath);
        canvas.drawPath(getPath(mPercent), mPaint);
        canvas.clipPath(mPath);
        mTextPaint.setColor(Color.WHITE);
        canvas.drawText("贴", mWidth / 2, mTextY, mTextPaint);
        canvas.restore();
    }
}