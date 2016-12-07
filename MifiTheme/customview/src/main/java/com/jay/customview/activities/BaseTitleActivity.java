package com.jay.customview.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jay.customview.R;
import com.jay.customview.utils.Utils;

/**
 * Created by Jay on 2016/4/7.
 * 项目中所有Activity的基类
 * 实现了沉浸式状态栏
 * 并有多种样式可供选择
 */
public class BaseTitleActivity extends FragmentActivity {
    private static final String TAG = "BaseTitleActivity";

    private FrameLayout mContentParent;
    private Button mRightButton;
    private View mContentView;
    private TextView mTitleText;
    private RelativeLayout mTitle;
    private View mStatusView;
    private ViewGroup mTitleParent;

    private ValueAnimator mColorAnimator;

    //默认的风格是BACK_AND_MORE
    private STYLE mStyle = STYLE.BACK_AND_MORE;
    /**
     * Style枚举
     */
    public enum STYLE {
        BACK_AND_MORE,
        SINGLE_BACK,
        FULL_SCREEN
    }

    /**
     * 得到右边的Button
     * @return 右Button
     */
    public Button getRightButton() {
        return mRightButton;
    }

    /**
     * 设置Activity的标题
     * @param title 标题
     */
    public void setTitle(CharSequence title) {
        mTitleText.setText(title);
    }

    /**
     * 得到标题所对应的TextView
     * @return TextView
     */
    public TextView getTitleText() {
        return mTitleText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_title_parent);
        initViews();

        //设置透明状态栏
        //实现沉浸式状态栏的关键
        //这里需要添加一个判断，KITKAT以下的版本，没有透明状态栏，无法实现沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            mStatusView.setVisibility(View.GONE);
        }
        //在这里加一个判断，如果不是MIUI，则该方法不必执行
        Log.i(TAG, "isMIUI: " + Utils.isMIUI());
        if(Utils.isMIUI()) {
            Utils.setMiuiStatusBarDarkMode(this, false);
        }
    }

    private void initViews() {
        mContentParent = (FrameLayout) findViewById(R.id.title_parent);
        mTitle = (RelativeLayout) findViewById(R.id.title);
        mTitleText = (TextView) findViewById(R.id.title_text);
        //按下返回键的默认响应事件
        findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRightButton = (Button) findViewById(R.id.right);
        setRightButtonBackground();

        mTitleParent = (ViewGroup) findViewById(R.id.title_container);
        mStatusView = findViewById(R.id.status_view);
        mStatusView.getLayoutParams().height = getStatusBarHeight();
    }

    /**
     * 设置右边Button的背景图片
     */
    private void setRightButtonBackground() {
        mRightButton.setBackgroundResource(R.drawable.btn_more_normal);

        /*
        //x方向上翻转一张图片
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.title_back_icon);
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Matrix matrix = canvas.getMatrix();
        matrix.setScale(-1, 1);
        matrix.postTranslate(bitmap1.getWidth(), 0);
        canvas.drawBitmap(bitmap1, matrix, null);
        //将Bitmap转换成Drawable
        Drawable drawable =new BitmapDrawable(getResources(), bitmap2);
        mRightButton.setBackground(drawable);
        */
    }

    /**
     * 设置显示风格
     * @param style 显示风格
     */
    public void setStyle(STYLE style) {
        mStyle = style;
        switch(style) {
            case BACK_AND_MORE:
                //默认的样式，不用做操作
                break;
            case SINGLE_BACK:
                mRightButton.setVisibility(View.GONE);
                break;
            case FULL_SCREEN:
                mTitle.setVisibility(View.GONE);
                updateView();
                break;
            default:
                break;
        }
    }

    /**
     * 需要注意的是，子类在继承时。该方法需要在super.onCreate()方法之后调用，否则颜色的设置可能不起作用
     * @param style 风格
     * @param color 颜色
     */
    public void setStyle(STYLE style, int color) {
        setStyle(style);
        setColor(color);
    }

    public void initAnimator() {
        if(mColorAnimator == null) {
            //invoke ValueAnimator.ofArgb(int ...values) to do some animation of colors
            mColorAnimator = ValueAnimator.ofInt(ContextCompat.getColor(this, R.color.titlebar_bg_color),
                    ContextCompat.getColor(this, R.color.heart_rate_dark),
                    ContextCompat.getColor(this, R.color.user_agreement_text),
                    ContextCompat.getColor(this, R.color.sleep_bg));

            mColorAnimator.setEvaluator(new ArgbEvaluator());
            mColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                int currentColor;
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentColor = (int) animation.getAnimatedValue();
                    setColorValue(currentColor);
                }
            });
            mColorAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mColorAnimator.setDuration(30000);
            mColorAnimator.setRepeatMode(ValueAnimator.REVERSE);
            mColorAnimator.setInterpolator(new LinearInterpolator());
            mColorAnimator.start();
        }
    }

    /**
     * set the color of titleBar and statusBar
     * @param color colorRes, not colorInt
     */
    public void setColor(@ColorRes int color) {
        if(mStatusView != null) {
            mStatusView.setBackgroundResource(color);
        }
        if(null != mTitleParent) {
            mTitleParent.setBackgroundResource(color);
        }
    }

    /**
     * set the color of titleBar and statusBar
     * @param color colorInt, not colorRes
     */
    public void setColorValue(@ColorInt int color) {
        if(mStatusView != null) {
            mStatusView.setBackgroundColor(color);
        }
        if(null != mTitleParent) {
            mTitleParent.setBackgroundColor(color);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(this, layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.TOP;
        setContentView(view, params);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentView = view;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) params;
        int titleHeight = this.getResources().getDimensionPixelSize(R.dimen.title_height);
        layoutParams.topMargin = titleHeight + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ? getStatusBarHeight() : 0);

        if(mContentParent.getChildCount() > 1) {
            mContentParent.removeViewAt(1);
        }
        mContentParent.addView(mContentView, layoutParams);
        updateView();
    }

    private void updateView() {
        if(mContentView != null && mStyle == STYLE.FULL_SCREEN) {
            if(mContentParent.getChildAt(1) != null) {
                FrameLayout.LayoutParams params =
                        (FrameLayout.LayoutParams) mContentParent.getChildAt(1).getLayoutParams();
                params.topMargin = 0;
                mContentView.setLayoutParams(params);
            }
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}
