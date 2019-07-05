package com.ziwenl.library.widget;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;

import com.ziwenl.library.R;

import androidx.core.content.ContextCompat;

/**
 * PackageName : com.ziwenl.library
 * Author : Ziwen Lan
 * Date : 2018/6/13
 * Time : 17:43
 * Introduction : 自带按压效果的Button
 * 可直接设置圆角、描边尺寸、描边色及按压时填充色
 * 减少shape/selector布局文件的创建
 */

public class BGButton extends androidx.appcompat.widget.AppCompatButton {
    //圆角值
    private float mRadius, mBottomLeftRadius, mBottomRightRadius, mTopLeftRadius, mTopRightRadius;
    //填充色、按压时填充色、不可点击时填充色、渐变色初始值、结束值
    private int mBackgroundColor, mPressedColor, mUnClickColor, mBackgroundColorStart, mBackgroundColorEnd;
    //正常状态下描边颜色、宽度
    private int mStrokeColor;
    private float mStrokeWidth;
    //按压时描边颜色、不可点击时描边颜色
    private int mPressedStrokeColor, mUnClickStrokeColor;
    //正常、按压时的drawable
    GradientDrawable mNormalDrawable, mPressedDrawable, mUnClickDrawable;
    //正常情况下的图片drawable
    Drawable mImgDrawable;
    //图片drawable的资源id
    private int mImgDrawableResourceId;
    //状态选择器
    private StateListDrawable mStateListDrawable;
    private Context mContext;

    public BGButton(Context context) {
        super(context);
    }

    public BGButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BGButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BGButton, defStyleAttr, 0);
        //圆角值
        //不用getDimension()获取是因为getDimension获取的值与屏幕分辨率有关
        mBottomLeftRadius = a.getDimensionPixelSize(R.styleable.BGButton_bottomLeftRadius, 0);
        mBottomRightRadius = a.getDimensionPixelSize(R.styleable.BGButton_bottomRightRadius, 0);
        mTopLeftRadius = a.getDimensionPixelSize(R.styleable.BGButton_topLeftRadius, 0);
        mTopRightRadius = a.getDimensionPixelSize(R.styleable.BGButton_topRightRadius, 0);
        mRadius = a.getDimensionPixelSize(R.styleable.BGButton_radius, 0);
        //填充色--正常状态下
        mBackgroundColor = a.getColor(R.styleable.BGButton_backgroundColor, Color.parseColor("#00000000"));
        //按压时填充色
        mPressedColor = a.getColor(R.styleable.BGButton_pressedColor, mBackgroundColor == 0 || mBackgroundColor == -1 ? Color.parseColor("#00000000") : getDarkerColor(mBackgroundColor));
        //不可点击时填充色
        mUnClickColor = a.getColor(R.styleable.BGButton_unClickColor, mPressedColor);
        //渐变色初始值与结束值
        mBackgroundColorStart = a.getColor(R.styleable.BGButton_backgroundColorStart, 0);
        mBackgroundColorEnd = a.getColor(R.styleable.BGButton_backgroundColorEnd, 0);
        if (mBackgroundColorStart != 0 && mBackgroundColorEnd != 0) {
            //设置了渐变色--将按压颜色已渐变色结束色为准加深
            mPressedColor = a.getColor(R.styleable.BGButton_pressedColor, mBackgroundColorEnd == -1 ? Color.parseColor("#dfe0e1") : getDarkerColor(mBackgroundColorEnd));
        }
        mImgDrawableResourceId = a.getResourceId(R.styleable.BGButton_backgroundDrawable, -1);
        //正常状态下线条颜色、宽度
        mStrokeColor = a.getColor(R.styleable.BGButton_strokeColor, -3);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.BGButton_strokeWidth, -1);
        //按下时线条颜色
        mPressedStrokeColor = a.getColor(R.styleable.BGButton_pressedStrokeColor, mStrokeColor);
        //不可点击时描边颜色
        mUnClickStrokeColor = a.getColor(R.styleable.BGButton_unClickStrokeColor, mPressedStrokeColor);
        init();
    }

    private void init() {
        //正常状态下的drawable
        if (mImgDrawableResourceId != -1) {
            mImgDrawable = ContextCompat.getDrawable(mContext, mImgDrawableResourceId);
        } else if (mBackgroundColorStart != 0 && mBackgroundColorEnd != 0) {
            //设置成渐变色
            int colors[] = {mBackgroundColorStart, mBackgroundColorEnd};
            mNormalDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        } else {
            //非渐变色设置
            mNormalDrawable = new GradientDrawable();
            mNormalDrawable.setColor(mBackgroundColor);
        }
        //按下时的drawable
        mPressedDrawable = new GradientDrawable();
        //不可点击时的drawable
        mUnClickDrawable = new GradientDrawable();
        //填充色设置
        mPressedDrawable.setColor(mPressedColor);

        mUnClickDrawable.setColor(mUnClickColor);
        //圆角值设置
        if (mRadius == 0) {
            if (mBottomLeftRadius != 0
                    || mBottomRightRadius != 0
                    || mTopLeftRadius != 0
                    || mTopRightRadius != 0) {
                //遵循左上，右上，右下，左下
                float[] radii = new float[]{mTopLeftRadius, mTopLeftRadius,
                        mTopRightRadius, mTopRightRadius,
                        mBottomRightRadius, mBottomRightRadius,
                        mBottomLeftRadius, mBottomLeftRadius};
                mNormalDrawable.setCornerRadii(radii);
                mPressedDrawable.setCornerRadii(radii);
                mUnClickDrawable.setCornerRadii(radii);
            }
        } else {
            mNormalDrawable.setCornerRadius(mRadius);
            mPressedDrawable.setCornerRadius(mRadius);
            mUnClickDrawable.setCornerRadius(mRadius);
        }
        //描边设置
        if (mStrokeWidth != -1 && mStrokeColor != -3) {
            mNormalDrawable.setStroke((int) mStrokeWidth, mStrokeColor);
            mPressedDrawable.setStroke((int) mStrokeWidth, mPressedStrokeColor);
            mUnClickDrawable.setStroke((int) mStrokeWidth, mUnClickStrokeColor);
        }
        //状态选择器
        mStateListDrawable = new StateListDrawable();
        if (mImgDrawable != null) {
            Drawable imgPressedDrawable = getResources().getDrawable(mImgDrawableResourceId);
            imgPressedDrawable.setAlpha(155);
            mStateListDrawable.addState(new int[]{android.R.attr.state_pressed}, imgPressedDrawable);
            mImgDrawable.setAlpha(255);
            mStateListDrawable.addState(new int[]{}, mImgDrawable);
        } else {
            mStateListDrawable.addState(new int[]{android.R.attr.state_pressed}, mPressedDrawable);
            mStateListDrawable.addState(new int[]{}, mNormalDrawable);
        }
        //未设置权重时，默认内容居中
        int gravity = Gravity.TOP | Gravity.START;
        if (getGravity() == gravity) {
            setGravity(Gravity.CENTER);
        }

        //设置背景及动画
        setUnClickStyle(true);
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setUnClickStyle(enabled);
    }

    /**
     * 设置为不可点击样式
     *
     * @param canClick true普通样式 false不可点击样式
     */
    public void setUnClickStyle(boolean canClick) {
        if (mStateListDrawable == null || mUnClickDrawable == null) return;
        if (canClick) {
            setBackground(mStateListDrawable);
            /*
             * 设置点击动画效果
             * 因为使用代码进行setBackground的时候，Button的默认点击效果会消失，所以再次为button添加动画效果
             * 5.0上有效
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setStateListAnimator(AnimatorInflater.loadStateListAnimator(mContext, R.animator.selector_bg_button_animator));
            }
        } else {
            //不可点击时样式
            setBackground(mUnClickDrawable);
        }
    }

    /**
     * 得到深色
     *
     * @param color
     * @return
     */
    private int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv
        // make darker
        hsv[1] = hsv[1] + 0.1f; // more saturation
        hsv[2] = hsv[2] - 0.2f; // less brightness
        return Color.HSVToColor(hsv);
    }

    /**
     * 得到浅色
     *
     * @param color
     * @return
     */
    private int getBrighterColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv
        hsv[1] = hsv[1] - 0.1f; // less saturation
        hsv[2] = hsv[2] + 0.1f; // more brightness
        return Color.HSVToColor(hsv);
    }
}
