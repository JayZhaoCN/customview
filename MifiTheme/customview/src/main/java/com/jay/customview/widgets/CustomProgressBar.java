package com.jay.customview.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.jay.customview.R;


/**
 * Created by Jay on 2016/11/4.
 * custom progressbar
 */

public class CustomProgressBar extends View {
    private static final String TAG = "CustomProgressBar";
    private float mHeight;
    private float mWidth;
    private float mPerLength;
    //the current location of seek circle center
    private float mSeekLocation;

    private static float mDefaultHeight;
    private static float mDefaultWidth;

    private float mProgressHeight;

    private float mSeekRadius;

    private float mProgressTop;

    private Context mContext;

    private Paint mBgPaint;
    private Paint mSeekPaint;
    private Paint mTransparentPaint;

    private int[] mColors;
    private int mCount = 5;

    //movable
    public static final int TYPE_MOVABLE = 0;
    //unmovable
    public static final int TYPE_UNMOVABLE = 1;
    //default type: movable
    private int mType = TYPE_MOVABLE;

    //multi color
    public static final int STYLE_MULTI_COLOR = 0;
    //single color
    public static final int STYLE_SINGLE_COLOR = 1;
    //gradual color
    public static final int STYLE_GRADUAL_COLOR = 2;
    //default style: STYLE_MULTI_COLOR
    private int mStyle = STYLE_MULTI_COLOR;

    //这个属性默认是false，当该属性设置为true，seek_color属性将不起作用，因为seekColor是随着progressbar渐变的，无法为其指定固定的颜色
    private boolean isSeekColorChangeable = false;

    //default min progress
    private int mMinProgress = 80;
    //default max progress
    private int mMaxProgress = 180;
    //current progress
    private int mProgress;

    private int mSeekColor;

    private int mSingleProgressColor;

    private int mStartColor;
    private int mEndColor;

    private int mTransparentColor;

    LinearGradient mGradient;

    public void setProgress(int progress) {
        //这里要加一个判断，如果设置的progress大于max_progress，则将progress设置为max_progress
        if(progress > mMaxProgress) {
            mProgress = mMaxProgress;
            Log.i(TAG, "progress is bigger than max_progress, so set progress as max_progress.");
        } else if(progress < mMinProgress) {
            mProgress = mMinProgress;
            Log.i(TAG, "progress is smaller than min_progress, so set progress as min_progress.");
        } else  mProgress = progress;
        mSeekLocation = (mProgress - mMinProgress) * mWidth / (mMaxProgress - mMinProgress) + mSeekRadius;
        postInvalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void setStyle(int style) {
        mStyle = style;
    }

    public int getStyle() {
        return mStyle;
    }

    public void setSeekColorChangeable(boolean changeable) {
        isSeekColorChangeable = changeable;
    }

    public boolean isSeekColorChangeable() {
        return isSeekColorChangeable;
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    public void setMinProgress(int minProgress) {
        mMinProgress = minProgress;
    }

    public void setSeekColor(@ColorInt int color) {
        mSeekColor = color;
    }

    public int getSeekColor() {
        return mSeekColor;
    }

    public void setSingleProgressColor(@ColorInt int color) {
        mSingleProgressColor = color;
    }

    public int getSingleProgressColor() {
        return mSingleProgressColor;
    }

    public void setStartColor(@ColorInt int color) {
        mStartColor = color;
    }

    public int getStartColor() {
        return mStartColor;
    }

    public void setEndColor(@ColorInt int color) {
        mEndColor = color;
    }

    public int getEndColor() {
        return mEndColor;
    }

    public int getTransparentColor() {
        return mTransparentColor;
    }

    public void setTransparentColor(@ColorInt int color) {
        mTransparentColor = color;
    }

    private OnProgressChangeListener mListener;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        obtainStyledAttr(attrs, defStyleAttr);
        init();
    }

    public void setColors(@ColorInt int[] colors) {
        mColors = colors;
        mCount = mColors.length;
    }

    private void obtainStyledAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, 0, defStyleAttr);
        mProgressHeight = ta.getDimensionPixelOffset(R.styleable.CustomProgressBar_progress_height, (int) dp2px(mContext, 10f));
        mSeekRadius = ta.getDimensionPixelOffset(R.styleable.CustomProgressBar_seek_radius, (int) dp2px(mContext, 9f));
        setType(ta.getInt(R.styleable.CustomProgressBar_type, TYPE_MOVABLE));
        setMaxProgress(ta.getInt(R.styleable.CustomProgressBar_max_progress, 100));
        setMinProgress(ta.getInt(R.styleable.CustomProgressBar_min_progress, 0));
        if(mMinProgress >= mMaxProgress) {
            throw new IllegalArgumentException("minProgress must be less than maxProgress, please check your input!");
        }
        setProgress(ta.getInt(R.styleable.CustomProgressBar_progress, mMinProgress));
        setSeekColorChangeable(ta.getBoolean(R.styleable.CustomProgressBar_seek_color_changeable, false));
        mStyle = ta.getInt(R.styleable.CustomProgressBar_style, STYLE_MULTI_COLOR);
        setSeekColor(ta.getColor(R.styleable.CustomProgressBar_seek_color, Color.rgb(206, 115, 6)));
        setSingleProgressColor(ta.getColor(R.styleable.CustomProgressBar_single_progress_color, Color.rgb(206, 115, 6)));
        setStartColor(ta.getColor(R.styleable.CustomProgressBar_gradual_start_color, Color.rgb(214, 197, 114)));
        setEndColor(ta.getColor(R.styleable.CustomProgressBar_gradual_end_color, Color.rgb(255, 41, 25)));
        setTransparentColor(ta.getColor(R.styleable.CustomProgressBar_transparent_color, Color.WHITE));
        ta.recycle();
    }

    private void init() {
        //default color of progressbar
        mColors = new int[]{
                Color.rgb(214, 197, 114),
                Color.rgb(214, 184, 21),
                Color.rgb(229, 146, 23),
                Color.rgb(242, 89, 24),
                Color.rgb(255, 41, 25)
        };
        mCount = mColors.length;

        //init paints
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSeekPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSeekPaint.setColor(mSeekColor);
        mTransparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTransparentPaint.setColor(mTransparentColor);

        mDefaultHeight = dp2px(mContext, 18f);
        mDefaultWidth = dp2px(mContext, 300f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure");

        float desireHeight = mDefaultHeight + getPaddingStart() + getPaddingEnd();
        float desireWidth = mDefaultWidth + getPaddingTop() + getPaddingBottom();

        Log.i(TAG, "paddingStart: " + getPaddingStart());
        Log.i(TAG, "paddingEnd: " + getPaddingEnd());

        mHeight = View.resolveSize((int) desireHeight, heightMeasureSpec);
        mWidth = View.resolveSize((int) desireWidth, widthMeasureSpec);
        Log.i(TAG, "mHeight: " + mHeight);
        Log.i(TAG, "mWidth: " + mWidth);

        mPerLength = mWidth / mCount;
        mProgressTop = (mHeight - mProgressHeight) / 2;

        setMeasuredDimension((int)(mWidth + mSeekRadius * 2), (int)mHeight);

        if(mProgress != 0) {
            setProgress(mProgress);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBgPaint.setShader(null);
        mSeekPaint.setShader(null);
        switch (mStyle) {
            case STYLE_MULTI_COLOR:
                drawMultiColorProgressBar(canvas);
                drawSeek(canvas);
                break;
            case STYLE_GRADUAL_COLOR:
                drawGradualColorProgressBar(canvas);
                drawSeek(canvas);
                break;
            case STYLE_SINGLE_COLOR:
                drawSingleColorProgressBar(canvas);
                drawSeek(canvas);
                break;
            default:
                break;
        }
    }

    private void drawGradualColorProgressBar(Canvas canvas) {
        mGradient = new LinearGradient(-2, 0, mWidth + mSeekRadius * 2 + 2, 0, new int[] {mStartColor,mEndColor}, null, Shader.TileMode.REPEAT);
        mBgPaint.setShader(mGradient);
        if(isSeekColorChangeable) mSeekPaint.setShader(mGradient);
        canvas.drawCircle(mHeight / 2 - mProgressTop + mSeekRadius, mHeight / 2, mHeight / 2 - mProgressTop, mBgPaint);
        canvas.drawRect(mHeight / 2 - mProgressTop + mSeekRadius, mProgressTop,
                mWidth - mHeight / 2 + mProgressTop + mSeekRadius, mHeight - mProgressTop, mBgPaint);
        canvas.drawCircle(mWidth - mHeight / 2 + mProgressTop + mSeekRadius,
                mHeight / 2, mHeight / 2 - mProgressTop, mBgPaint);
    }

    private void drawSingleColorProgressBar(Canvas canvas) {
        mBgPaint.setColor(mSingleProgressColor);
        canvas.drawCircle(mHeight / 2 - mProgressTop + mSeekRadius, mHeight / 2, mHeight / 2 - mProgressTop, mBgPaint);
        canvas.drawRect(mHeight / 2 - mProgressTop + mSeekRadius, mProgressTop,
                mWidth - mHeight / 2 + mProgressTop + mSeekRadius, mHeight - mProgressTop, mBgPaint);
        canvas.drawCircle(mWidth - mHeight / 2 + mProgressTop + mSeekRadius,
                mHeight / 2, mHeight / 2 - mProgressTop, mBgPaint);
    }

    private void drawMultiColorProgressBar(Canvas canvas) {
        for(int i=0; i<mCount; i++) {
            float mIndex = mPerLength * i;
            mBgPaint.setColor(mColors[i]);
            if(i == 0) {
                canvas.drawCircle(mHeight / 2 - mProgressTop + mSeekRadius, mHeight / 2, mHeight / 2 - mProgressTop, mBgPaint);
                canvas.drawRect(mHeight / 2 - mProgressTop + mSeekRadius, mProgressTop,
                        mIndex + mPerLength + mSeekRadius, mHeight - mProgressTop, mBgPaint);
                continue;
            } else if(i== mCount - 1) {
                canvas.drawRect(mIndex + mSeekRadius, mProgressTop,
                        mIndex + mPerLength - mHeight / 2 + mProgressTop + mSeekRadius, mHeight - mProgressTop, mBgPaint);
                canvas.drawCircle(mIndex + mPerLength - mHeight / 2 + mProgressTop + mSeekRadius,
                        mHeight / 2, mHeight / 2 - mProgressTop, mBgPaint);
                continue;
            }
            canvas.drawRect(mIndex + mSeekRadius, mProgressTop, mIndex + mPerLength + mSeekRadius, mHeight - mProgressTop, mBgPaint);
        }
    }

    private void drawSeek(Canvas canvas) {
        if(isSeekColorChangeable && mStyle == STYLE_MULTI_COLOR) setSeekPaintColor();

        mTransparentPaint.setColor(mTransparentColor);
        if(mSeekLocation <= mSeekRadius) {
            canvas.drawCircle(mSeekRadius, mHeight / 2, mSeekRadius + 3, mTransparentPaint);
            canvas.drawCircle(mSeekRadius, mHeight / 2, mSeekRadius, mSeekPaint);
        } else if(mSeekLocation >= mWidth + mSeekRadius) {
            canvas.drawCircle(mWidth + mSeekRadius, mHeight / 2, mSeekRadius + 3, mTransparentPaint);
            canvas.drawCircle(mWidth + mSeekRadius, mHeight / 2, mSeekRadius, mSeekPaint);
        } else {
            canvas.drawCircle(mSeekLocation, mHeight / 2, mSeekRadius + 3, mTransparentPaint);
            canvas.drawCircle(mSeekLocation, mHeight / 2, mSeekRadius, mSeekPaint);
        }
    }

    private void setSeekPaintColor() {
        int section = (int) ((mSeekLocation - mSeekRadius) / mPerLength);
        if(section >= mCount - 1) section = mCount - 1;
        mSeekPaint.setColor(mColors[section]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mType == TYPE_MOVABLE) {
            mSeekLocation = event.getX();
            if (mListener != null) {
                mListener.onProgressChange(calculateProgress());
            }
            postInvalidate();
        } else if(mType == TYPE_UNMOVABLE) {
            Log.i(TAG, "type is TYPE_UNMOVABLE, so can not move!");
        }
        return true;
    }

    private int calculateProgress() {
        if(mSeekLocation > mWidth + mSeekRadius) mProgress = mMaxProgress;
        else if(mSeekLocation < mSeekRadius) mProgress = mMinProgress;
        else mProgress = (int) ((mSeekLocation - mSeekRadius) / mWidth * (mMaxProgress - mMinProgress) + mMinProgress);
        return mProgress;
    }


    public static float dp2px(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnProgressChangeListener {
        void onProgressChange(int progress);
    }
}
