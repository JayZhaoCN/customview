package com.jay.customview.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import com.jay.customview.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 2016/11/24.
 * line chart
 */

public class LineChart extends View {
    private static final String TAG = "LineChart";

    private Context mContext;
    private int mWidth;
    private int mHeight;

    private Paint mXScaleTextPaint;
    private Paint mRoundRectPaint;
    private Paint mValuePointPaint;
    private Paint mScaleLinePaint;
    private Paint mYScaleTextPaint;
    private Paint mLinesPaint;

    private String[] mXScaleTexts = {"一月", "二月", "三月", "四月", "五月", "六月"};
    //选中的项
    private int mSelectedIndex = 1;

    private int[] mValues = {10, 210, 190, 198, 450, 78};
    private int mMaxValue = 450;
    private int mMinValue = 10;

    private Paint.FontMetricsInt mXScaleFontMetrics;
    private Paint.FontMetricsInt mYScaleFontMetrics;

    private int mTextBaseLine;
    //x轴刻度所占的高度
    private int mXScaleHeight = 80;
    //x轴每格刻度所占的宽度
    private int mXScaleWidth;
    //y轴刻度的最大宽度
    private int mMaxYScaleWidth;
    //y轴刻度的高度
    private int mYScaleHeight;

    //y轴刻度所占的宽度，根据y轴刻度文字的宽度动态调整
    private int mYScaleWidth;
    //图表中最低点与x轴之间距离
    private int mPointSuspendX = 50;

    //Y轴刻度个数
    private int mYScaleNum = 5;

    //y轴每格刻度高度
    private int mYScalePerHeight;

    //y轴刻度文字所在的矩形
    private List<Rect> mYScaleTargetRects;

    private int titleheight = 100;

    //各种颜色
    private int mXScaleTextColor = 0xFF1674E9;
    private int mRoundRectColor = 0xFF10A9C4;
    private int mValuePointColor = 0xFF17B56C;
    private int mYScaleLineColor = 0x33000000;

    //SparseArray(稀疏数组)的使用，对于索引是整数的情景，有时能带来一些效率的提升（相对于HashMap）。
    private SparseArray<Rect> mTextRects;

    public LineChart(Context context) {
        this(context, null);
    }

    public LineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs();
        initDatas();
        mMaxYScaleWidth = initYScaleTexts();
        mYScaleWidth = mMaxYScaleWidth + 20;
    }

    private List<String> mYScaleTexts;

    private int initYScaleTexts() {
        float perValue = (float) (mMaxValue - mMinValue) / (float) mYScaleNum;

        mYScaleTexts = new ArrayList<>();
        String text;
        Rect rect = new Rect();
        int maxWidth = 0;
        for(int i=0; i<mYScaleNum; i++) {
            text = String.valueOf((int)(mMinValue + perValue * i));
            mYScaleTexts.add(text);
            mYScaleTextPaint.getTextBounds(text, 0, text.length(), rect);
            if(maxWidth < rect.width()) {
                maxWidth = rect.width();
            }
        }
        mYScaleHeight = rect.height();
        return maxWidth;
    }

    private void initDatas() {
        mXScaleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mXScaleTextPaint.setColor(mXScaleTextColor);
        mXScaleTextPaint.setTextSize(Utils.sp2px(mContext, 12f));
        mXScaleTextPaint.setTextAlign(Paint.Align.CENTER);

        mXScaleFontMetrics = mXScaleTextPaint.getFontMetricsInt();

        mRoundRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRoundRectPaint.setStrokeWidth(2);
        mRoundRectPaint.setColor(mRoundRectColor);
        mRoundRectPaint.setStyle(Paint.Style.STROKE);

        mValuePointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mValuePointPaint.setColor(mValuePointColor);
        mValuePointPaint.setStyle(Paint.Style.FILL);

        mScaleLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleLinePaint.setColor(mYScaleLineColor);
        mScaleLinePaint.setStrokeWidth(3);

        mYScaleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mYScaleTextPaint.setColor(mXScaleTextColor);
        mYScaleTextPaint.setTextSize(Utils.sp2px(mContext, 10f));
        mYScaleTextPaint.setTextAlign(Paint.Align.RIGHT);

        mLinesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinesPaint.setColor(mValuePointColor);
        mLinesPaint.setStrokeWidth(3);

        mYScaleFontMetrics = mYScaleTextPaint.getFontMetricsInt();

        mTextRects = new SparseArray<>();
    }

    /**
     * init attrs from xml
     */
    private void initAttrs() {
        //perhaps tomorrow
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mTextBaseLine = (mHeight - mXScaleFontMetrics.bottom - mXScaleFontMetrics.top) / 2 + mHeight / 2 - mXScaleHeight / 2;
        mXScaleWidth = (mWidth - mYScaleWidth) / mXScaleTexts.length;

        mYScalePerHeight = (mHeight - mXScaleHeight - mPointSuspendX) / mYScaleNum;


        mYScaleTargetRects = new ArrayList<>();
        for(int i=0; i<mYScaleNum; i++) {
            mYScaleTargetRects.add(new Rect(0, mYScalePerHeight - mYScaleHeight / 2 + mYScalePerHeight *i, mMaxYScaleWidth, mYScalePerHeight + mYScaleHeight / 2 + mYScalePerHeight *i));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw y scale text
        drawYScaleText(canvas);

        //draw x scale text
        drawXScaleText(canvas);

        //draw value point and lines
        drawAllPointAndLines(canvas);

        //draw dialog
        drawDialog(canvas, mSelectedIndex);
    }

    private void drawYScaleText(Canvas canvas) {
        canvas.drawLine(mYScaleWidth, 0, mYScaleWidth, mHeight - mXScaleHeight, mScaleLinePaint);

        for(int i=1; i<mYScaleNum + 1; i++) {
            canvas.drawLine(mYScaleWidth, mYScalePerHeight * i, mYScaleWidth - 5, mYScalePerHeight * i, mScaleLinePaint);
            canvas.drawText
                    (mYScaleTexts.get(mYScaleNum - i), mMaxYScaleWidth,
                            (mYScaleTargetRects.get(i-1).bottom + mYScaleTargetRects.get(i-1).top - mYScaleFontMetrics.bottom - mYScaleFontMetrics.top) / 2, mYScaleTextPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int index;
                if(event.getY() > mHeight - mXScaleHeight) {
                    Log.i(TAG, "XScaleArea");
                    if ((index = judgeTouchArea(event.getX(), event.getY())) != -1) {
                        mSelectedIndex = index;
                        invalidate();
                    }
                } else {
                    Log.i(TAG, "ChartArea");
                    if((index = judgeChartPointTouchArea(event.getX(), event.getY())) != -1) {
                        mSelectedIndex = index;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    private int judgeChartPointTouchArea(float x, float y) {
        Rect rect = new Rect();
        for(int i=0; i<mValues.length; i++) {
            Point point = calculateLocation(i, mValues[i]);
            rect.left = point.x - 35;
            rect.right = point.x + 35;
            rect.top = point.y - 35;
            rect.bottom = point.y + 35;
            if(rect.contains((int)x, (int)y)) {
                return i;
            }
        }
        return -1;
    }

    private int judgeTouchArea(float x, float y) {
        Rect rect;
        for (int i = 0; i < mXScaleTexts.length; i++) {
            rect = new Rect(getXScaleTouchArea(mXScaleTexts[i], i));
            rect.left -= 10;
            rect.right += 10;
            rect.top -= 10;
            rect.bottom += 10;
            if (rect.contains((int) x, (int) y)) {
                return i;
            }
        }
        return -1;
    }

    private void drawXScaleText(Canvas canvas) {
        canvas.drawLine(mYScaleWidth, mHeight - mXScaleHeight, mWidth, mHeight - mXScaleHeight, mScaleLinePaint);

        for (int i = 0; i < mXScaleTexts.length; i++) {
            canvas.drawLine(mYScaleWidth + mXScaleWidth * i + mXScaleWidth / 2, mHeight - mXScaleHeight, mYScaleWidth + mXScaleWidth * i + mXScaleWidth / 2, mHeight - mXScaleHeight - 8, mScaleLinePaint);
            if (i == mSelectedIndex) {
                canvas.drawRoundRect(new RectF(getXScaleTouchArea(mXScaleTexts[i], i)), 7, 7, mRoundRectPaint);
                mXScaleTextPaint.setColor(mRoundRectColor);
                canvas.drawText(mXScaleTexts[i], mYScaleWidth + mXScaleWidth * i + mXScaleWidth / 2, mTextBaseLine, mXScaleTextPaint);
            } else {
                mXScaleTextPaint.setColor(mXScaleTextColor);
                canvas.drawText(mXScaleTexts[i], mYScaleWidth + mXScaleWidth * i + mXScaleWidth / 2, mTextBaseLine, mXScaleTextPaint);
            }
        }
        mXScaleTextPaint.setColor(mRoundRectColor);
    }

    private Rect getXScaleTouchArea(String text, int xIndex) {
        //如果存储的矩形有效，则不必重复计算
        if (mTextRects != null && mTextRects.size() == mXScaleTexts.length) {
            Log.i(TAG, "list is not null!");
            return mTextRects.get(xIndex);
        } else {
            Log.i(TAG, "list is null!");
            Rect rect = new Rect();
            mXScaleTextPaint.getTextBounds(text, 0, text.length(), rect);
            int height = rect.height();
            int width = rect.width();
            Rect wrapTextRect = new Rect();
            wrapTextRect.left = mYScaleWidth + mXScaleWidth * xIndex + (mXScaleWidth - width) / 2;
            wrapTextRect.right = wrapTextRect.left + width;
            wrapTextRect.bottom = mHeight - (mXScaleHeight - height) / 2;
            wrapTextRect.top = wrapTextRect.bottom - height;
            wrapTextRect.left -= 16;
            wrapTextRect.top -= 8;
            wrapTextRect.right += 15;
            wrapTextRect.bottom += 8;
            mTextRects.put(xIndex, wrapTextRect);

            return wrapTextRect;
        }
    }

    private void drawAllPointAndLines(Canvas canvas) {
        Point originPoint = calculateLocation(0, mValues[0]);

        for (int i = 0; i < mXScaleTexts.length; i++) {
            Point point = calculateLocation(i, mValues[i]);
            if (point.y == 0) {
                point.y += 8;
            }
            canvas.drawCircle(point.x, point.y, 8, mValuePointPaint);
            if(i != 0) {
                canvas.drawLine(originPoint.x, originPoint.y, point.x, point.y, mLinesPaint);
                originPoint = point;
            }
        }
    }

    private void drawDialog(Canvas canvas, int i) {
        int dialogWidth = mMaxYScaleWidth / 2;
        Point bottomPoint = calculateLocation(i, mValues[i]);
        Path path = new Path();
        if(mValues[i] != mMaxValue) {
            bottomPoint.y -= 8;
            path.moveTo(bottomPoint.x, bottomPoint.y);
            path.lineTo(bottomPoint.x + 15, bottomPoint.y - 20);
            path.lineTo(bottomPoint.x + 15 + dialogWidth, bottomPoint.y - 20);
            path.lineTo(bottomPoint.x + 15 + dialogWidth, bottomPoint.y - 20 - 35);
            path.lineTo(bottomPoint.x - 15 - dialogWidth, bottomPoint.y - 20 - 35);
            path.lineTo(bottomPoint.x - 15 - dialogWidth, bottomPoint.y - 20);
            path.lineTo(bottomPoint.x - 15, bottomPoint.y - 20);
            path.close();
        } else {
            bottomPoint.y += 8;
            path.moveTo(bottomPoint.x, bottomPoint.y);
            path.lineTo(bottomPoint.x + 15, bottomPoint.y + 20);
            path.lineTo(bottomPoint.x + 15 + dialogWidth, bottomPoint.y + 20);
            path.lineTo(bottomPoint.x + 15 + dialogWidth, bottomPoint.y + 20 + 35);
            path.lineTo(bottomPoint.x - 15 - dialogWidth, bottomPoint.y + 20 + 35);
            path.lineTo(bottomPoint.x - 15 - dialogWidth, bottomPoint.y + 20);
            path.lineTo(bottomPoint.x - 15, bottomPoint.y + 20);
            path.close();
        }
        canvas.drawPath(path, mValuePointPaint);

        Rect rect = new Rect();
        if(mValues[i] != mMaxValue) {
            rect.left = bottomPoint.x - 15 - dialogWidth;
            rect.right = bottomPoint.x + 15 + dialogWidth;
            rect.top = bottomPoint.y - 20 - 35;
            rect.bottom = bottomPoint.y - 20;
        } else {
            rect.left = bottomPoint.x - 15 - dialogWidth;
            rect.right = bottomPoint.x + 15 + dialogWidth;
            rect.top = bottomPoint.y + 20 + 35;
            rect.bottom = bottomPoint.y + 20;
        }
        mYScaleTextPaint.setTextAlign(Paint.Align.CENTER);
        mYScaleTextPaint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(mValues[i]), rect.left + rect.width() / 2, (rect.bottom + rect.top - mYScaleFontMetrics.bottom - mYScaleFontMetrics.top) / 2, mYScaleTextPaint);
        mYScaleTextPaint.setTextAlign(Paint.Align.RIGHT);
        mYScaleTextPaint.setColor(mXScaleTextColor);
    }

    /**
     * calculate the location of a value
     *
     * @param xIndex x轴刻度位置
     * @param value 值
     */
    private Point calculateLocation(int xIndex, int value) {
        float yScale = (float) (mHeight - mXScaleHeight - mPointSuspendX) / (float) (mMaxValue - mMinValue);
        int yLocation = (int) ((mMaxValue - value) * yScale);
        int xLocation = mYScaleWidth + mXScaleWidth * xIndex + mXScaleWidth / 2;

        return new Point(xLocation, yLocation);
    }
}
