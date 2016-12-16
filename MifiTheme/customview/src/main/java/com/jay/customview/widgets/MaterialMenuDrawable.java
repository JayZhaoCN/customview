package com.jay.customview.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Jay on 2016/12/7.
 * material menu drawable
 */

public class MaterialMenuDrawable extends MyAnimatableDrawable {
    private static final String TAG = "MaterialMenuDrawable";

    private Paint mPaint;

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

    private int endScale1 = 0;
    private int endScale3 = 0;
    private int startScale2 = 0;
    private int startScale1 = 0;
    private int endScale2 = 0;
    private int startScale3 = 0;

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

    //第三条线的第二次旋转中心（比例）
    private double mRotateCenter3_2X = 0;
    private double mRotateCenter3_2Y = 0;

    private static final float LINE_WIDTH_PERCENT = 0.55f;
    private static final float GAP_HEIGHT_PERCENT = 0.09f;
    private static final float LINE_HEIGHT_PERCENT = 0.06f;
    private static final double SQUARE_ROOT_TWO = 1.41421356;

    private static final int ANIMATION_DURATION = 600;

    public enum State {
        BURGER,
        ARROW,
        X,
        CHECK
    }

    private enum AnimationState {
        BURGER_ARROW,
        ARROW_BURGER,
        BURGER_X,
        X_BURGER,
        BURGER_CHECK,
        CHECK_BURGER,
    }

    //the default state is BURGER
    private State mState = State.BURGER;

    private AnimationState mAnimationState = AnimationState.BURGER_ARROW;

    public AnimationState getAnimationState() {
        return mAnimationState;
    }

    public State getCurrentState() {
        return mState;
    }

    public MaterialMenuDrawable(Context context, @ColorInt int lineColor) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(lineColor);
    }

    private static double BURGER_ARROW_SCALE_END_1_3;
    private static double BURGER_ARROW_SCALE_END_2;
    private static double BURGER_ARROW_SCALE_START_1_3;

    private static double BURGER_CHECK_SCALE_END_3;
    private static double BURGER_CHECK_SCALE_START_2;
    private static double BURGER_CHECK_SCALE_START_3;

    private static double BURGER_CHECK_PIVOT_X_2;
    private static double BURGER_CHECK_PIVOT_Y_2;

    private static double BURGER_X_SCALE_PERCENT;

    @Override
    public void draw(Canvas canvas) {

        //line 1
        canvas.save();
        mPaint.setAlpha(alpha1);
        canvas.rotate(rotateAngle1, (float)(mWidth * mRotateCenter1X), (float)(mHeight * mRotateCenter1Y));
        canvas.rotate(rotateAngle1_2, (float)(mWidth * mRotateCenter1_2X), (float)(mHeight * mRotateCenter1_2Y));
        canvas.drawLine(startP1.x + startScale1, startP1.y, endP1.x + endScale1, endP1.y, mPaint);
        mPaint.setAlpha(255);
        canvas.restore();

        //line 2
        canvas.save();
        mPaint.setAlpha(alpha2);
        canvas.rotate(rotateAngle2, (float)(mWidth * mRotateCenter2X), (float)(mHeight * mRotateCenter2Y));
        canvas.drawLine(startP2.x + startScale2, startP2.y, endP2.x + endScale2, endP2.y, mPaint);
        mPaint.setAlpha(255);
        canvas.restore();

        //line 3
        canvas.save();
        canvas.rotate(rotateAngle3, (float)(mWidth * mRotateCenter3X), (float)(mHeight * mRotateCenter3Y));
        canvas.rotate(rotateAngle3_2, (float)(mWidth * mRotateCenter3_2X), (float)(mHeight * mRotateCenter3_2Y));
        canvas.drawLine(startP3.x + startScale3, startP3.y, endP3.x + endScale3, endP3.y, mPaint);
        canvas.restore();
        super.draw(canvas);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
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

                    mAnimationState = AnimationState.BURGER_ARROW;

                    mAnimator = ValueAnimator.ofFloat(0, 1);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            rotateAngle1 = (int) (225 * (float)animation.getAnimatedValue());
                            rotateAngle2 = (int) (180 * (float)animation.getAnimatedValue());
                            rotateAngle3 = (int) (135 * (float)animation.getAnimatedValue());

                            endScale1 = - (int) (BURGER_ARROW_SCALE_END_1_3 * (float)animation.getAnimatedValue());
                            endScale3 = - (int) (BURGER_ARROW_SCALE_END_1_3 * (float)animation.getAnimatedValue());
                            endScale2 = - (int) (BURGER_ARROW_SCALE_END_2 * (float)animation.getAnimatedValue());
                            startScale3 = (int) (BURGER_ARROW_SCALE_START_1_3 * (float)animation.getAnimatedValue());
                            startScale1 = (int) (BURGER_ARROW_SCALE_START_1_3 * (float)animation.getAnimatedValue());

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

                    mAnimationState = AnimationState.BURGER_CHECK;

                    mAnimator = ValueAnimator.ofFloat(0, 1);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            alpha1 = (int) ((1 - (float) animation.getAnimatedValue()) * 255);

                            rotateAngle3 = (int) (45 * (float)animation.getAnimatedValue());
                            rotateAngle2 = (int) (135 * (float)animation.getAnimatedValue());

                            startScale2 = (int) (BURGER_CHECK_SCALE_START_2 * (float)animation.getAnimatedValue());
                            endScale3 = - (int) (BURGER_CHECK_SCALE_END_3 * (float)animation.getAnimatedValue());
                            startScale3 = (int) (BURGER_CHECK_SCALE_START_3 * (float)animation.getAnimatedValue());

                            invalidateSelf();
                        }
                    });
                } else if(state == State.X) {
                    mRotateCenter1X = 279f / 720f;
                    mRotateCenter1Y = 252f / 720f;

                    mRotateCenter1_2X = 387f / 720f;
                    mRotateCenter1_2Y = 261f / 720f;

                    mRotateCenter3X = 252f / 720f;
                    mRotateCenter3Y = 441f / 720f;

                    mRotateCenter3_2X = 387f / 720f;
                    mRotateCenter3_2Y = 459f / 720f;

                    mAnimationState = AnimationState.BURGER_X;

                    mAnimator = ValueAnimator.ofFloat(0, 1);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            rotateAngle1 = (int) (44 * (float)animation.getAnimatedValue());
                            rotateAngle1_2 = (int) (90 * (float)animation.getAnimatedValue());

                            alpha2 = (int) ((1 - (float)animation.getAnimatedValue()) * 255);

                            rotateAngle3 = - (int) (44 * (float)animation.getAnimatedValue());
                            rotateAngle3_2 = - (int) (90 * (float)animation.getAnimatedValue());

                            startScale1 = (int) ((BURGER_X_SCALE_PERCENT * mWidth) * (float)animation.getAnimatedValue());
                            startScale3 = (int) ((BURGER_X_SCALE_PERCENT * mWidth) * (float)animation.getAnimatedValue());

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

                    mAnimationState = AnimationState.CHECK_BURGER;
                    mAnimator = ValueAnimator.ofFloat(1, 0);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            alpha1 = (int) ((1 - (float) animation.getAnimatedValue()) * 255);

                            rotateAngle3 = (int) (45 * (float)animation.getAnimatedValue());
                            rotateAngle2 = (int) (135 * (float)animation.getAnimatedValue());

                            startScale2 = (int) (BURGER_CHECK_SCALE_START_2 * (float)animation.getAnimatedValue());
                            endScale3 = - (int) (BURGER_CHECK_SCALE_END_3 * (float)animation.getAnimatedValue());
                            startScale3 = (int) (BURGER_CHECK_SCALE_START_3 * (float)animation.getAnimatedValue());

                            startScale1 = (int)(54f / 720f * mWidth * (float) animation.getAnimatedValue());
                            startScale2 = (int)(54f / 720f * mWidth * (float) animation.getAnimatedValue());

                            invalidateSelf();
                        }
                    });
                }
                break;
            case ARROW:
                if(state == State.BURGER) {
                    mRotateCenter2X = 0.5;
                    mRotateCenter2Y = 0.5;

                    mRotateCenter3X = 0.5;
                    mRotateCenter3Y = 0.5;

                    mAnimationState = AnimationState.ARROW_BURGER;

                    mAnimator = ValueAnimator.ofFloat(1, 0);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            rotateAngle1 = (int) (225 * (float)animation.getAnimatedValue());
                            rotateAngle2 = (int) (180 * (float)animation.getAnimatedValue());
                            rotateAngle3 = (int) (135 * (float)animation.getAnimatedValue());
                            endScale1 = - (int) (BURGER_ARROW_SCALE_END_1_3 * (float)animation.getAnimatedValue());
                            endScale3 = - (int) (BURGER_ARROW_SCALE_END_1_3 * (float)animation.getAnimatedValue());
                            endScale2 = - (int) (BURGER_ARROW_SCALE_END_2 * (float)animation.getAnimatedValue());
                            startScale3 = (int) (BURGER_ARROW_SCALE_START_1_3 * (float)animation.getAnimatedValue());
                            startScale1 = (int) (BURGER_ARROW_SCALE_START_1_3 * (float)animation.getAnimatedValue());

                            invalidateSelf();
                        }
                    });
                }
                break;
            case X:
                if(state == State.BURGER) {
                    mRotateCenter1X = 279f / 720f;
                    mRotateCenter1Y = 252f / 720f;

                    mRotateCenter1_2X = 387f / 720f;
                    mRotateCenter1_2Y = 261f / 720f;

                    mRotateCenter3X = 252f / 720f;
                    mRotateCenter3Y = 441f / 720f;

                    mRotateCenter3_2X = 387f / 720f;
                    mRotateCenter3_2Y = 459f / 720f;

                    mAnimationState = AnimationState.X_BURGER;

                    mAnimator = ValueAnimator.ofFloat(1, 0);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {

                            rotateAngle1 = (int) (44 * (float)animation.getAnimatedValue());
                            rotateAngle1_2 = (int) (90 * (float)animation.getAnimatedValue());

                            alpha2 = (int) ((1 - (float)animation.getAnimatedValue()) * 255);

                            rotateAngle3 = - (int) (44 * (float)animation.getAnimatedValue());
                            rotateAngle3_2 = - (int) (90 * (float)animation.getAnimatedValue());

                            startScale1 = (int) (BURGER_X_SCALE_PERCENT * mWidth * (float)animation.getAnimatedValue());
                            startScale3 = (int) (BURGER_X_SCALE_PERCENT * mWidth * (float)animation.getAnimatedValue());

                            invalidateSelf();
                        }
                    });
                }
            default:
                break;
        }

        setAnimationParam();

        mState = state;
        start();
    }

    private void setAnimationParam() {
        if(mAnimator != null) {
            mAnimator.setDuration(ANIMATION_DURATION);
            mAnimator.setRepeatCount(0);
            mAnimator.setInterpolator(new DecelerateInterpolator(2));
        }
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

        int gapWidth = (int) (mHeight * GAP_HEIGHT_PERCENT);
        int lineWidth = (int) (mHeight * LINE_HEIGHT_PERCENT);
        mPaint.setStrokeWidth(lineWidth);

        BURGER_X_SCALE_PERCENT = 82f / 720f;

        BURGER_ARROW_SCALE_END_1_3 = mWidth * LINE_WIDTH_PERCENT * 0.5 - gapWidth - lineWidth * 1.5;
        BURGER_ARROW_SCALE_END_2 = mWidth * LINE_WIDTH_PERCENT * 0.5 - SQUARE_ROOT_TWO * (gapWidth + lineWidth);
        BURGER_ARROW_SCALE_START_1_3 = (mWidth * 0.08);

        BURGER_CHECK_PIVOT_X_2 = 70.5 / 120;
        BURGER_CHECK_PIVOT_Y_2 = 0.5;

        BURGER_CHECK_SCALE_END_3 = mWidth * LINE_WIDTH_PERCENT / 2 - (BURGER_CHECK_PIVOT_X_2 * mWidth - mWidth / 2) * SQUARE_ROOT_TWO / 2 - lineWidth / 2;
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
        startP1.y = endP1.y = mHeight / 2 - lineWidth - gapWidth;
        startP3.y = endP3.y = mHeight / 2 + lineWidth + gapWidth;
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
