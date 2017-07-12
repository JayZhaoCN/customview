package com.jay.customview.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jay.customview.R;
import com.jay.customview.utils.Utils;

/**
 * @author zhaojiabao 2017/7/14
 *         带指示标志的ProgressView
 */

public class JayProgressView extends View {
    private Context mContext;

    private Paint mProgressPaint;
    private Paint mBubblePaint;
    private Paint mTextPaint;

    private int mProgressColor;
    private int mTextColor;
    private int mBubbleColor;
    private int mDisableColor;
    private int mWidth, mHeight;
    private int mProgressInt = 50;
    private int mProgressMax = 100;

    private Path mPath, mSlidePath;

    private float mValue1, mValue2, mValue3, mValue4, mValue5;
    private float mSliderHeight;
    //0~1
    private float mProgress = 0.5f;
    private float mTextBaseLine;

    private RectF mContentRect, mLeftCapRect, mRightCapRect, mSlideRect;

    private boolean mMovable = true;

    private OnProgressChangeListener mListener;

    public JayProgressView(Context context) {
        this(context, null);
    }

    public JayProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JayProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        obtainAttrs(attrs, defStyleAttr);
        init();
    }

    private void obtainAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.JayProgressView, defStyleAttr, 0);
        mProgressColor = ta.getColor(R.styleable.JayProgressView_progress_color,
                ContextCompat.getColor(mContext, R.color.personinfo_color_yellow));
        mBubbleColor = ta.getColor(R.styleable.JayProgressView_bubble_color,
                ContextCompat.getColor(mContext, R.color.personinfo_color_yellow));
        mTextColor = ta.getColor(R.styleable.JayProgressView_text_color,
                ContextCompat.getColor(mContext, R.color.white_100_percent));
        mDisableColor = ta.getColor(R.styleable.JayProgressView_text_color,
                ContextCompat.getColor(mContext, R.color.black_5_percent));
        mSliderHeight = ta.getDimension(R.styleable.JayProgressView_slider_height, Utils.dp2px(mContext, 6));
        mMovable = ta.getBoolean(R.styleable.JayProgressView_movable, true);
        ta.recycle();
    }

    private void init() {
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.FILL);

        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(mBubbleColor);
        mBubblePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(Utils.sp2px(mContext, 8));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        initNumber();
        mContentRect = new RectF();
        mContentRect.top = getPaddingTop();
        mContentRect.left = getPaddingLeft() + mValue5 / 2;
        mContentRect.bottom = mHeight - getPaddingBottom();
        mContentRect.right = mWidth - getPaddingRight() - mValue5 / 2;

        mLeftCapRect = new RectF(mContentRect.left, (mHeight - mSliderHeight) / 2,
                mContentRect.left + mSliderHeight, (mHeight + mSliderHeight) / 2);
        mRightCapRect = new RectF(mContentRect.right - mSliderHeight, (mHeight - mSliderHeight) / 2,
                mContentRect.right, (mHeight + mSliderHeight) / 2);
        mSlideRect = new RectF(mContentRect.left, (mHeight - mSliderHeight) / 2,
                mContentRect.right, (mHeight + mSliderHeight) / 2);

        getTextBaseLine();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(getDialogPath(mContentRect.left + mContentRect.width() * mProgress, (mHeight - mSliderHeight) / 2), mBubblePaint);
        drawSlider(canvas);
        canvas.drawText(getPercentText(), mContentRect.left + mContentRect.width() * mProgress, mTextBaseLine, mTextPaint);
    }

    private String getPercentText() {
        int percent = (int) (mProgress * 100);
        return percent + "%";
    }

    private void drawSlider(Canvas canvas) {
        canvas.save();
        canvas.clipPath(getSlidePath());
        mProgressPaint.setColor(mProgressColor);
        canvas.drawRect(mContentRect.left, (mHeight - mSliderHeight) / 2,
                mContentRect.left + mContentRect.width() * mProgress, (mHeight + mSliderHeight) / 2, mProgressPaint);
        mProgressPaint.setColor(mDisableColor);
        canvas.drawRect(mContentRect.left + mContentRect.width() * mProgress, (mHeight - mSliderHeight) / 2,
                mContentRect.right, (mHeight + mSliderHeight) / 2, mProgressPaint);
        canvas.restore();
    }

    private Path getSlidePath() {
        if (mSlidePath == null) {
            mSlidePath = new Path();
        }
        mSlidePath.reset();
        mSlidePath.moveTo(mContentRect.left + mSliderHeight / 2, (mHeight + mSliderHeight) / 2);
        mSlidePath.addArc(mLeftCapRect, 90, 180);
        mSlidePath.rLineTo(mContentRect.width() - mSliderHeight, 0);
        mSlidePath.arcTo(mRightCapRect, 270, 180);
        mSlidePath.close();
        return mSlidePath;
    }

    public void setProgress(int progress) {
        mProgressInt = progress;
        mProgress = mProgressInt / (float) mProgressMax;
        invalidate();
    }

    public void setProgressMax(int progressMax) {
        mProgressMax = progressMax;
        mProgress = mProgressInt / (float) mProgressMax;
        invalidate();
    }


    private void initNumber() {
        mValue1 = Utils.dp2px(mContext, 3);
        mValue2 = Utils.dp2px(mContext, 5);
        mValue3 = Utils.dp2px(mContext, 7);
        mValue4 = Utils.dp2px(mContext, 10);
        mValue5 = Utils.dp2px(mContext, 20);
    }

    private Path getDialogPath(float x, float y) {
        if (mPath == null) {
            mPath = new Path();
        }
        mPath.reset();
        mPath.moveTo(x, y);
        mPath.rLineTo(-mValue1, -mValue2);
        mPath.rLineTo(-mValue3, 0);
        mPath.rLineTo(0, -mValue4);
        mPath.rLineTo(mValue5, 0);
        mPath.rLineTo(0, mValue4);
        mPath.rLineTo(-mValue3, 0);
        mPath.close();

        return mPath;
    }

    private void getTextBaseLine() {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float rectTop = (mHeight - mSliderHeight) / 2 - mValue2 - mValue4;
        float rectBottom = rectTop + mValue4;
        mTextBaseLine = (rectTop + rectBottom - fontMetrics.top - fontMetrics.bottom) / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mMovable) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                mProgress = (event.getX() - mContentRect.left) / mContentRect.width();
                if (mProgress > 1) {
                    mProgress = 1;
                } else if (mProgress < 0) {
                    mProgress = 0;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mListener != null) {
                    mListener.onProgressChange(mProgress);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        mListener = listener;
    }

    public interface OnProgressChangeListener {
        void onProgressChange(float progress);
    }
}
