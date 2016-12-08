package com.jay.customview.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;

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

    private static final float LINE_WIDTH_PERCENT = 0.55f;
    private static final float GAP_HEIGHT_PERCENT = 0.09f;
    private static final float LINE_HEIGHT_PERCENT = 0.05f;

    public MaterialMenuDrawable(Context context, @ColorRes int lineColorRes) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(context, lineColorRes));
    }

    @Override
    public void draw(Canvas canvas) {
        Log.i(TAG, "draw");
        canvas.drawLine(startP1.x, startP1.y, endP1.x, endP1.y, mPaint);
        canvas.drawLine(startP2.x, startP2.y, endP2.x, endP2.y, mPaint);
        canvas.drawLine(startP3.x, startP3.y, endP3.x, endP3.y, mPaint);

        super.draw(canvas);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        Log.i(TAG, "onBoundsChange");
        super.onBoundsChange(bounds);

        initValues(bounds);
    }

    private void initValues(Rect bounds) {
        mBounds = new Rect(bounds);

        mWidth = mBounds.width();
        mHeight = mBounds.height();

        mGapWidth = (int) (mHeight * GAP_HEIGHT_PERCENT);
        mLineWidth = (int) (mHeight * LINE_HEIGHT_PERCENT);
        mPaint.setStrokeWidth(mLineWidth);

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
