package com.jay.customview.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by Jay on 2016/11/29.
 * tint util
 */

public class TintUtils {
    private static final String TAG = "TintUtils";

    /**
     * set colors of enable and disable
     * @param context context
     * @param drawableResId drawable resource id
     * @param disableColorResId disable color resource id
     * @param enableColorResId enable color resource id
     * @return StateListDrawable
     */
    public static Drawable getEnableDrawableResId(Context context, @DrawableRes int drawableResId, @ColorRes int disableColorResId, @ColorRes int enableColorResId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        int disableColor = ContextCompat.getColor(context, disableColorResId);
        int enableColor = ContextCompat.getColor(context, enableColorResId);

        return getEnableDrawable(drawable, disableColor, enableColor);
    }

    /**
     * set colors of enable and disable
     * @param drawable drawable
     * @param disableColor disable color
     * @param enableColor enable color
     * @return StateListDrawable
     */
    public static Drawable getEnableDrawable(Drawable drawable, @ColorInt int disableColor, @ColorInt int enableColor) {
        int[] colors = new int[]{disableColor, enableColor};
        int[][] states = new int[2][];

        states[0] = new int[]{-android.R.attr.state_enabled};
        states[1] = new int[]{};

        ColorStateList colorList = new ColorStateList(states, colors);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(states[0], drawable);
        stateListDrawable.addState(states[1], drawable);

        Drawable.ConstantState state = stateListDrawable.getConstantState();
        drawable = DrawableCompat.wrap(state == null ? stateListDrawable : state.newDrawable()).mutate();

        DrawableCompat.setTintList(drawable, colorList);

        return drawable;
    }

    /**
     * set colors of checked and normal
     * @param drawable drawable
     * @param checkedColor checked color
     * @param normalColor normal color
     * @return StateListDrawable
     */
    public static Drawable getCheckedDrawable(Drawable drawable, @ColorInt int checkedColor, @ColorInt int normalColor) {
        int[] colors = new int[]{checkedColor, normalColor};
        int[][] states = new int[2][];

        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{-android.R.attr.state_checked};

        ColorStateList colorList = new ColorStateList(states, colors);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(states[0], drawable);
        stateListDrawable.addState(states[1], drawable);

        Drawable.ConstantState state = stateListDrawable.getConstantState();
        drawable = DrawableCompat.wrap(state == null ? stateListDrawable : state.newDrawable()).mutate();

        DrawableCompat.setTintList(drawable, colorList);

        return drawable;
    }

    /**
     * set colors of checked and normal
     * @param context context
     * @param drawableResId drawanle resource id
     * @param checkedColorResId checked color resource id
     * @param normalColorResId normal color resource id
     * @return StateListDrawable
     */
    public static Drawable getCheckedDrawableResId(Context context, @DrawableRes int drawableResId, @ColorRes int checkedColorResId, @ColorRes int normalColorResId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        int checkedColor = ContextCompat.getColor(context, checkedColorResId);
        int normalColor = ContextCompat.getColor(context, normalColorResId);

        return getCheckedDrawable(drawable, checkedColor, normalColor);
    }


    /**
     *set colors of pressed and normal
     * @param drawable drawable to be set
     * @param pressedColor pressed color
     * @param normalColor normal color
     * @return StateListDrawable
     */
    public static Drawable getPressedDrawable(Drawable drawable, int pressedColor, int normalColor) {
        int[] colors = new int[]{pressedColor, normalColor};
        int[][] states = new int[2][];

        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};

        ColorStateList colorList = new ColorStateList(states, colors);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(states[0], drawable);
        stateListDrawable.addState(states[1], drawable);

        Drawable.ConstantState state = stateListDrawable.getConstantState();
        drawable = DrawableCompat.wrap(state == null ? stateListDrawable : state.newDrawable()).mutate();

        DrawableCompat.setTintList(drawable, colorList);

        return drawable;
    }

    /**
     * set colors of pressed and normal
     * @param context context
     * @param drawableResId drawable resource id
     * @param pressedColorResId pressed color resource id
     * @param normalColorResId normal color resource id
     * @return StateListDrawable
     */
    public static Drawable getPressedDrawableResId(Context context, int drawableResId, int pressedColorResId, int normalColorResId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        int pressedColor = ContextCompat.getColor(context, pressedColorResId);
        int normalColor = ContextCompat.getColor(context, normalColorResId);

        return getPressedDrawable(drawable, pressedColor, normalColor);
    }

    public static Drawable setDrawableTint(Context context, int drawableResId, @ColorRes int colorResId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, colorResId));
        return drawable;
    }


}
