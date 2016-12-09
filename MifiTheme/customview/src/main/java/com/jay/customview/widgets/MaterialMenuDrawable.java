package com.jay.customview.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Jay on 2016/12/7.
 * material menu drawable
 */

public class MaterialMenuDrawable extends MyAnimatableDrawable {
    private static final String TAG = "MaterialMenuDrawable";

    private Paint mPaint;
    private Context mContext;

    private int mLineWidth;
    private int mGapWidth;

    private int mLineColor = Color.WHITE;

    private Point startP1;
    private Point startP2;
    private Point startP3;

    private Point endP1;
    private Point endP2;
    private Point endP3;

    private Rect mBounds;
    private int mWidth;
    private int mHeight;

    private ValueAnimator mAnimator;

    private int rotateAngle1 = 0;
    private int rotateAngle1_2 = 0;
    private int rotateAngle2 = 0;
    private int rotateAngle3 = 0;
    private int rotateAngle3_2 = 0;

    private int endscale1 = 0;
    private int endscale3 = 0;
    private int startscale2 = 0;
    private int startscale1 = 0;
    private int endscale2 = 0;
    private int startscale3 = 0;

    private int alpha1 = 255;
    private int alpha2 = 255;

    //第二条线的第一次旋转中心（比例）
    private double mRotateCenter2X = 0;
    private double mRotateCenter2Y = 0;

    //第一条线的第一次旋转中心（比例）
    private double mRotateCenter1X = 0;
    private double mRotateCenter1Y = 0;
    //第二条线的第二次旋转中心（比例）
    private double mRotateCenter1_2X = 0;
    private double mRotateCenter1_2Y = 0;

    //第三条线的第一次旋转中心（比例）
    private double mRotateCenter3X = 0;
    private double mRotateCenter3Y = 0;

    private static final float LINE_WIDTH_PERCENT = 0.55f;
    private static final float GAP_HEIGHT_PERCENT = 0.09f;
    private static final float LINE_HEIGHT_PERCENT = 0.05f;

    private static final double genhao2 = 1.41421356;

    public enum State {
        BURGER,
        ARROW,
        X,
        CHECK
    }

    public enum AnimationState {
        BURGER_ARROW,
        ARROW_BURGER,
        BURGER_X,
        ARROW_X,
        ARROW_CHECK,
        BURGER_CHECK,
        CHECK_BURGER,
        CHECK_ARROW,
        X_CHECK
    }

    //the default state is BURGER
    private State mState = State.BURGER;

    private AnimationState mAnimatonState = AnimationState.BURGER_ARROW;

    public State getCurrentState() {
        return mState;
    }

    public MaterialMenuDrawable(Context context, @ColorRes int lineColorRes) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(context, lineColorRes));
    }

    private static double BURGER_ARROW_SCALE_END_1_3;
    private static double BURGER_ARROW_SCALE_END_2;
    private static double BURGER_ARROW_SCALE_START_1_3;

    private static double BURGER_CHECK_SCALE_END_3;
    private static double BURGER_CHECK_SCALE_START_2;
    private static double BURGER_CHECK_SCALE_START_3;

    private static double BURGER_CHECK_PIVOT_X_2;
    private static double BURGER_CHECK_PIVOT_Y_2;

    @Override
    public void draw(Canvas canvas) {
        Log.i(TAG, "draw");

        //line 1
        canvas.save();
        mPaint.setAlpha(alpha1);
        canvas.rotate(rotateAngle1, (float)(mWidth * mRotateCenter1X), (float)(mHeight * mRotateCenter1Y));
        canvas.drawLine(startP1.x + startscale1, startP1.y, endP1.x + endscale1, endP1.y, mPaint);
        mPaint.setAlpha(255);
        canvas.restore();

        //line 2
        canvas.save();
        mPaint.setAlpha(alpha2);
        canvas.rotate(rotateAngle2, (float)(mWidth * mRotateCenter2X), (float)(mHeight * mRotateCenter2Y));
        canvas.drawLine(startP2.x + startscale2, startP2.y, endP2.x + endscale2, endP2.y, mPaint);
        mPaint.setAlpha(255);
        canvas.restore();

        //line 3
        canvas.save();
        canvas.rotate(rotateAngle3, (float)(mWidth * mRotateCenter3X), (float)(mHeight / 2));
        canvas.drawLine(startP3.x + startscale3, startP3.y, endP3.x + endscale3, endP3.y, mPaint);
        canvas.restore();
        super.draw(canvas);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        Log.i(TAG, "onBoundsChange");
        super.onBoundsChange(bounds);
        initValues(bounds);
    }

    public void setState(State state) {
        if(mState == state) {
            return;
        }

        switch (mState) {
            case BURGER:
                if(state == State.ARROW) {
                    mRotateCenter1X = 0.5;
                    mRotateCenter1Y = 0.5;

                    mRotateCenter2X = 0.5;
                    mRotateCenter2Y = 0.5;

                    mRotateCenter3X = 0.5;
                    mRotateCenter3Y = 0.5;

                    mAnimatonState = AnimationState.BURGER_ARROW;

                    mAnimator = ValueAnimator.ofFloat(0, 1);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            rotateAngle1 = (int) (225 * (float)animation.getAnimatedValue());
                            rotateAngle2 = (int) (180 * (float)animation.getAnimatedValue());
                            rotateAngle3 = (int) (135 * (float)animation.getAnimatedValue());

                            endscale1 = - (int) (BURGER_ARROW_SCALE_END_1_3 * (float)animation.getAnimatedValue());
                            endscale3 = - (int) (BURGER_ARROW_SCALE_END_1_3 * (float)animation.getAnimatedValue());
                            endscale2 = - (int) (BURGER_ARROW_SCALE_END_2 * (float)animation.getAnimatedValue());
                            startscale3 = (int) (BURGER_ARROW_SCALE_START_1_3 * (float)animation.getAnimatedValue());
                            startscale1 = (int) (BURGER_ARROW_SCALE_START_1_3 * (float)animation.getAnimatedValue());

                            invalidateSelf();
                        }
                    });
                } else if(state == State.CHECK) {
                    mRotateCenter1X = 0.5;
                    mRotateCenter1Y = 0.5;

                    mRotateCenter2X = BURGER_CHECK_PIVOT_X_2;
                    mRotateCenter2Y = BURGER_CHECK_PIVOT_Y_2;

                    mRotateCenter3X = 0.5;
                    mRotateCenter3Y = 0.5;

                    mAnimatonState = AnimationState.BURGER_CHECK;

                    mAnimator = ValueAnimator.ofFloat(0, 1);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            alpha1 = (int) ((1 - (float) animation.getAnimatedValue()) * 255);

                            rotateAngle3 = (int) (45 * (float)animation.getAnimatedValue());
                            rotateAngle2 = (int) (135 * (float)animation.getAnimatedValue());

                            startscale2 = (int) (BURGER_CHECK_SCALE_START_2 * (float)animation.getAnimatedValue());
                            endscale3 = - (int) (BURGER_CHECK_SCALE_END_3 * (float)animation.getAnimatedValue());
                            startscale3 = (int) (BURGER_CHECK_SCALE_START_3 * (float)animation.getAnimatedValue());

                            invalidateSelf();
                        }
                    });
                }
                break;
            case CHECK:
                if(state == State.BURGER) {
                    mRotateCenter1X = 0.5;
                    mRotateCenter1Y = 0.5;

                    mRotateCenter3X = 0.5;
                    mRotateCenter3Y = 0.5;

                    mRotateCenter2X = BURGER_CHECK_PIVOT_X_2;
                    mRotateCenter2Y = BURGER_CHECK_PIVOT_Y_2;

                    mAnimatonState = AnimationState.CHECK_BURGER;
                    mAnimator = ValueAnimator.ofFloat(1, 0);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            alpha1 = (int) ((1 - (float) animation.getAnimatedValue()) * 255);

                            rotateAngle3 = (int) (45 * (float)animation.getAnimatedValue());
                            rotateAngle2 = (int) (135 * (float)animation.getAnimatedValue());

                            startscale2 = (int) (BURGER_CHECK_SCALE_START_2 * (float)animation.getAnimatedValue());
                            endscale3 = - (int) (BURGER_CHECK_SCALE_END_3 * (float)animation.getAnimatedValue());
                            startscale3 = (int) (BURGER_CHECK_SCALE_START_3 * (float)animation.getAnimatedValue());

                            invalidateSelf();
                        }
                    });
                } else if(state == State.ARROW) {
                    //TODO
                    mAnimatonState = AnimationState.CHECK_ARROW;
                }
                break;
            case ARROW:
                if(state == State.BURGER) {
                    mRotateCenter2X = 0.5;
                    mRotateCenter2Y = 0.5;

                    mRotateCenter3X = 0.5;
                    mRotateCenter3Y = 0.5;

                    mAnimatonState = AnimationState.ARROW_BURGER;

                    mAnimator = ValueAnimator.ofFloat(1, 0);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            rotateAngle1 = (int) (225 * (float)animation.getAnimatedValue());
                            rotateAngle2 = (int) (180 * (float)animation.getAnimatedValue());
                            rotateAngle3 = (int) (135 * (float)animation.getAnimatedValue());
                            endscale1 = - (int) (BURGER_ARROW_SCALE_END_1_3 * (float)animation.getAnimatedValue());
                            endscale3 = - (int) (BURGER_ARROW_SCALE_END_1_3 * (float)animation.getAnimatedValue());
                            endscale2 = - (int) (BURGER_ARROW_SCALE_END_2 * (float)animation.getAnimatedValue());
                            startscale3 = (int) (BURGER_ARROW_SCALE_START_1_3 * (float)animation.getAnimatedValue());
                            startscale1 = (int) (BURGER_ARROW_SCALE_START_1_3 * (float)animation.getAnimatedValue());

                            invalidateSelf();
                        }
                    });
                } else if(state == State.CHECK) {
                    //TODO
                }
                break;
            default:
                break;
        }

        mAnimator.setDuration(800);
        mAnimator.setRepeatCount(0);
        mAnimator.setInterpolator(new LinearInterpolator());

        mState = state;
        start();
    }

    @Override
    public void start() {
        super.start();
        if(mAnimator != null) {
            if(!mAnimator.isStarted()) {
                mAnimator.start();
            }
        }
    }

    private void initValues(Rect bounds) {
        mBounds = new Rect(bounds);

        mWidth = mBounds.width();
        mHeight = mBounds.height();

        mGapWidth = (int) (mHeight * GAP_HEIGHT_PERCENT);
        mLineWidth = (int) (mHeight * LINE_HEIGHT_PERCENT);
        mPaint.setStrokeWidth(mLineWidth);

        BURGER_ARROW_SCALE_END_1_3 = mWidth * LINE_WIDTH_PERCENT * 0.5 - mGapWidth - mLineWidth * 1.5;
        BURGER_ARROW_SCALE_END_2 = mWidth * LINE_WIDTH_PERCENT * 0.5 - genhao2 * (mGapWidth + mLineWidth);
        BURGER_ARROW_SCALE_START_1_3 = (mWidth * 0.08);

        BURGER_CHECK_PIVOT_X_2 = 70.5 / 120;
        BURGER_CHECK_PIVOT_Y_2 = 0.5;

        BURGER_CHECK_SCALE_END_3 = mWidth * LINE_WIDTH_PERCENT / 2 - (BURGER_CHECK_PIVOT_X_2 * mWidth - mWidth / 2) * genhao2 / 2 - mLineWidth / 2;
        BURGER_CHECK_SCALE_START_2 = mWidth * 0.08;
        BURGER_CHECK_SCALE_START_3 = mWidth * 0.08;

        startP1 = new Point();
        startP2 = new Point();
        startP3 = new Point();
        endP1 = new Point();
        endP2 = new Point();
        endP3 = new Point();

        startP1.x = startP2.x = startP3.x = (int) (mWidth * (1 - LINE_WIDTH_PERCENT) / 2);
        endP1.x = endP2.x = endP3.x = (int) (mWidth * (0.5 + LINE_WIDTH_PERCENT / 2));

        startP2.y = endP2.y = mHeight / 2;
        startP1.y = endP1.y = mHeight / 2 - mLineWidth - mGapWidth;
        startP3.y = endP3.y = mHeight / 2 + mLineWidth + mGapWidth;
    }

    public int getWidth() {
        if(mBounds != null)
            return mBounds.width();
        return -1;
    }

    public int getHeight() {
        if(mBounds != null)
            return mBounds.height();
        return -1;
    }
}
