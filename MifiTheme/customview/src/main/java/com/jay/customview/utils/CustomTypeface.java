package com.jay.customview.utils;

import android.support.annotation.ColorInt;

public class CustomTypeface {
    Font mFont;
    int mTextColor;
    int mTextSize;

    public CustomTypeface(Font font, @ColorInt int textColor, int textSize) {
        mFont = font;
        mTextColor = textColor;
        mTextSize = textSize;
    }
}
