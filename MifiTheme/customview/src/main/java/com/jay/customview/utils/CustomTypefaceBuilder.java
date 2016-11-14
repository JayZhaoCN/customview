package com.jay.customview.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;

/**
 * Created by Jay on 2016/11/14.
 * custom typeface builder
 */

public class CustomTypefaceBuilder {
    private static final String TAG = "CustomTypefaceBuilder";

    private Context mContext;
    private SpannableStringBuilder mBuilder;

    private int mIndex = 0;

    public CustomTypefaceBuilder(Context context) {
        mContext = context;
        mBuilder = new SpannableStringBuilder();
    }

    public CustomTypefaceBuilder append(String content, CustomTypeface font) {
        mBuilder.append(content);
        int length = mBuilder.length();
        Log.i(TAG, "index: " + mIndex + " length: " + length);

        //set text size and color
        int[][] states = new int[1][];
        states[0] = new int[] {};
        int[] colors = new int[]{font.mTextColor};
        ColorStateList mColorStateList = new ColorStateList(states, colors);
        TextAppearanceSpan textAppearanceSpan =
                new TextAppearanceSpan("default", Typeface.NORMAL, (int)Utils.sp2px(mContext, font.mTextSize), mColorStateList, null);
        mBuilder.setSpan(textAppearanceSpan,
                mIndex, length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        //set custom typeface
        if(font.mFont != Font.DEFAULT) {
            Typeface mTypeface = Typeface.createFromAsset(mContext.getAssets(), font.mFont.mFontPath);
            CustomTypefaceSpan typefaceSpan = new CustomTypefaceSpan(mTypeface);
            mBuilder.setSpan(typefaceSpan, mIndex, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mIndex = length;

        return this;
    }

    public SpannableStringBuilder build() {
        return mBuilder;
    }
}
