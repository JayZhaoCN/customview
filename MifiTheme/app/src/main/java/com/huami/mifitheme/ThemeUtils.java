package com.huami.mifitheme;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

/**
 * <p>title:</p>
 * <p>desc:</p>
 * <p>author:liu-yuwu</p>
 * <p>data:16-10-27</p>
 */

public class ThemeUtils {


    public int getThemeColor(Context context, int mAttrResId) {
        Resources.Theme curTheme = context.getTheme();
        TypedValue typedValue = new TypedValue();
        curTheme.resolveAttribute(mAttrResId, typedValue, true);
        Log.i("ThemeUtils", Integer.toHexString(typedValue.data));
        return typedValue.data;
    }
}
