package com.huami.mifitheme;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

/**
 * <p>title:</p>
 * <p>desc:</p>
 * <p>author:liu-yuwu</p>
 * <p>data:16-11-3</p>
 */

public class TintUtils {
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public static void tintDrawable(ImageView imageView, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(imageView.getDrawable().mutate());
        DrawableCompat.setTintList(wrappedDrawable, colors);
        DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SRC_IN);
        imageView.setImageDrawable(wrappedDrawable);
    }
}
