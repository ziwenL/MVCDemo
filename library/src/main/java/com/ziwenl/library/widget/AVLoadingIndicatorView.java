package com.ziwenl.library.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.ziwenl.library.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AVLoadingIndicatorView extends View {

    private static final String TAG = "AVLoadingIndicatorView";

    private static final Indicator DEFAULT_INDICATOR = new Indicator();

    private static final int MIN_SHOW_TIME = 500; // ms
    private static final int MIN_DELAY = 500; // ms

    private long mStartTime = -1;

    private boolean mPostedHide = false;

    private boolean mPostedShow = false;

    private boolean mDismissed = false;

    private final Runnable mDelayedHide = new Runnable() {

        @Override
        public void run() {
            mPostedHide = false;
            mStartTime = -1;
            setVisibility(View.GONE);
        }
    };

    private final Runnable mDelayedShow = new Runnable() {

        @Override
        public void run() {
            mPostedShow = false;
            if (!mDismissed) {
                mStartTime = System.currentTimeMillis();
                setVisibility(View.VISIBLE);
            }
        }
    };

    int mMinWidth;
    int mMaxWidth;
    int mMinHeight;
    int mMaxHeight;

    private Indicator mIndicator;
    private int mIndicatorColor = 0xFFFF0000;

    private boolean mShouldStartAnimationDrawable;

    public AVLoadingIndicatorView(Context context) {
        this(context, null);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMinWidth = 24;
        mMaxWidth = 48;
        mMinHeight = 24;
        mMaxHeight = 48;

        mIndicatorColor = ContextCompat.getColor(context, R.color.colorAccent);

        if (mIndicator == null) {
            setIndicator(DEFAULT_INDICATOR);
        }
    }

    public Indicator getIndicator() {
        return mIndicator;
    }

    public void setIndicator(Indicator d) {
        if (mIndicator != d) {
            if (mIndicator != null) {
                mIndicator.setCallback(null);
                unscheduleDrawable(mIndicator);
            }

            mIndicator = d;
            //need to set indicator color again if you didn't specified when you update the indicator .
            setIndicatorColor(mIndicatorColor);
            if (d != null) {
                d.setCallback(this);
            }
            postInvalidate();
        }
    }


    /**
     * setIndicatorColor(0xFF00FF00)
     * or
     * setIndicatorColor(Color.BLUE)
     * or
     * setIndicatorColor(Color.parseColor("#FF4081"))
     * or
     * setIndicatorColor(0xFF00FF00)
     * or
     * setIndicatorColor(getResources().getColor(android.R.color.black))
     *
     * @param color
     */
    public void setIndicatorColor(int color) {
        this.mIndicatorColor = color;
        mIndicator.setColor(color);
    }



    public void smoothToShow() {
        startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        setVisibility(VISIBLE);
    }

    public void smoothToHide() {
        startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
        setVisibility(GONE);
    }

    public void hide() {
        mDismissed = true;
        removeCallbacks(mDelayedShow);
        long diff = System.currentTimeMillis() - mStartTime;
        if (diff >= MIN_SHOW_TIME || mStartTime == -1) {
            // The progress spinner has been shown long enough
            // OR was not shown yet. If it wasn't shown yet,
            // it will just never be shown.
            setVisibility(View.GONE);
        } else {
            // The progress spinner is shown, but not long enough,
            // so put a delayed message in to hide it when its been
            // shown long enough.
            if (!mPostedHide) {
                postDelayed(mDelayedHide, MIN_SHOW_TIME - diff);
                mPostedHide = true;
            }
        }
    }

    public void show() {
        // Reset the start time.
        mStartTime = -1;
        mDismissed = false;
        removeCallbacks(mDelayedHide);
        if (!mPostedShow) {
            postDelayed(mDelayedShow, MIN_DELAY);
            mPostedShow = true;
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mIndicator
                || super.verifyDrawable(who);
    }

    void startAnimation() {
        if (getVisibility() != VISIBLE) {
            return;
        }

        if (mIndicator instanceof Animatable) {
            mShouldStartAnimationDrawable = true;
        }
        postInvalidate();
    }

    void stopAnimation() {
        if (mIndicator instanceof Animatable) {
            mIndicator.stop();
            mShouldStartAnimationDrawable = false;
        }
        postInvalidate();
    }

    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
                stopAnimation();
            } else {
                startAnimation();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (verifyDrawable(dr)) {
            final Rect dirty = dr.getBounds();
            final int scrollX = getScrollX() + getPaddingLeft();
            final int scrollY = getScrollY() + getPaddingTop();

            invalidate(dirty.left + scrollX, dirty.top + scrollY,
                    dirty.right + scrollX, dirty.bottom + scrollY);
        } else {
            super.invalidateDrawable(dr);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateDrawableBounds(w, h);
    }

    private void updateDrawableBounds(int w, int h) {
        // onDraw will translate the canvas so we draw starting at 0,0.
        // Subtract out padding for the purposes of the calculations below.
        w -= getPaddingRight() + getPaddingLeft();
        h -= getPaddingTop() + getPaddingBottom();

        int right = w;
        int bottom = h;
        int top = 0;
        int left = 0;

        if (mIndicator != null) {
            // Maintain aspect ratio. Certain kinds of animated drawables
            // get very confused otherwise.
            final int intrinsicWidth = mIndicator.getIntrinsicWidth();
            final int intrinsicHeight = mIndicator.getIntrinsicHeight();
            final float intrinsicAspect = (float) intrinsicWidth / intrinsicHeight;
            final float boundAspect = (float) w / h;
            if (intrinsicAspect != boundAspect) {
                if (boundAspect > intrinsicAspect) {
                    // New width is larger. Make it smaller to match height.
                    final int width = (int) (h * intrinsicAspect);
                    left = (w - width) / 2;
                    right = left + width;
                } else {
                    // New height is larger. Make it smaller to match width.
                    final int height = (int) (w * (1 / intrinsicAspect));
                    top = (h - height) / 2;
                    bottom = top + height;
                }
            }
            mIndicator.setBounds(left, top, right, bottom);
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTrack(canvas);
    }

    void drawTrack(Canvas canvas) {
        final Drawable d = mIndicator;
        if (d != null) {
            // Translate canvas so a indeterminate circular progress bar with padding
            // rotates properly in its animation
            final int saveCount = canvas.save();

            canvas.translate(getPaddingLeft(), getPaddingTop());

            d.draw(canvas);
            canvas.restoreToCount(saveCount);

            if (mShouldStartAnimationDrawable && d instanceof Animatable) {
                ((Animatable) d).start();
                mShouldStartAnimationDrawable = false;
            }
        }
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw = 0;
        int dh = 0;

        final Drawable d = mIndicator;
        if (d != null) {
            dw = Math.max(mMinWidth, Math.min(mMaxWidth, d.getIntrinsicWidth()));
            dh = Math.max(mMinHeight, Math.min(mMaxHeight, d.getIntrinsicHeight()));
        }

        updateDrawableState();

        dw += getPaddingLeft() + getPaddingRight();
        dh += getPaddingTop() + getPaddingBottom();

        final int measuredWidth = resolveSizeAndState(dw, widthMeasureSpec, 0);
        final int measuredHeight = resolveSizeAndState(dh, heightMeasureSpec, 0);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    private void updateDrawableState() {
        final int[] state = getDrawableState();
        if (mIndicator != null && mIndicator.isStateful()) {
            mIndicator.setState(state);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);

        if (mIndicator != null) {
            mIndicator.setHotspot(x, y);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
        removeCallbacks();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnimation();
        // This should come after stopAnimation(), otherwise an invalidate message remains in the
        // queue, which can prevent the entire view hierarchy from being GC'ed during a rotation
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        removeCallbacks(mDelayedHide);
        removeCallbacks(mDelayedShow);
    }

    public static class Indicator extends Drawable implements Animatable {

        private HashMap<ValueAnimator, ValueAnimator.AnimatorUpdateListener> mUpdateListeners = new HashMap<>();

        private ArrayList<ValueAnimator> mAnimators;
        private int alpha = 255;
        private final Rect ZERO_BOUNDS_RECT = new Rect();
        protected Rect drawBounds = ZERO_BOUNDS_RECT;

        private boolean mHasAnimators;

        private Paint mPaint = new Paint();

        public int getColor() {
            return mPaint.getColor();
        }

        public void setColor(int color) {
            mPaint.setColor(color);
        }

        @Override
        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        @Override
        public int getAlpha() {
            return alpha;
        }

        @Override
        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public void draw(Canvas canvas) {
            draw(canvas, mPaint);
        }

        @Override
        public void start() {
            ensureAnimators();

            if (mAnimators == null) {
                return;
            }

            // If the animators has not ended, do nothing.
            if (isStarted()) {
                return;
            }
            startAnimators();
            invalidateSelf();
        }

        private void startAnimators() {
            for (int i = 0; i < mAnimators.size(); i++) {
                ValueAnimator animator = mAnimators.get(i);

                //when the animator restart , add the updateListener again because they
                // was removed by animator stop .
                ValueAnimator.AnimatorUpdateListener updateListener = mUpdateListeners.get(animator);
                if (updateListener != null) {
                    animator.addUpdateListener(updateListener);
                }

                animator.start();
            }
        }

        private void stopAnimators() {
            if (mAnimators != null) {
                for (ValueAnimator animator : mAnimators) {
                    if (animator != null && animator.isStarted()) {
                        animator.removeAllUpdateListeners();
                        animator.end();
                    }
                }
            }
        }

        private void ensureAnimators() {
            if (!mHasAnimators) {
                mAnimators = onCreateAnimators();
                mHasAnimators = true;
            }
        }

        @Override
        public void stop() {
            stopAnimators();
        }

        private boolean isStarted() {
            for (ValueAnimator animator : mAnimators) {
                return animator.isStarted();
            }
            return false;
        }

        @Override
        public boolean isRunning() {
            for (ValueAnimator animator : mAnimators) {
                return animator.isRunning();
            }
            return false;
        }

        /**
         * Your should use this to add AnimatorUpdateListener when
         * create animator , otherwise , animator doesn't work when
         * the animation restart .
         *
         * @param updateListener
         */
        public void addUpdateListener(ValueAnimator animator, ValueAnimator.AnimatorUpdateListener updateListener) {
            mUpdateListeners.put(animator, updateListener);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            setDrawBounds(bounds);
        }

        public void setDrawBounds(Rect drawBounds) {
            setDrawBounds(drawBounds.left, drawBounds.top, drawBounds.right, drawBounds.bottom);
        }

        public void setDrawBounds(int left, int top, int right, int bottom) {
            this.drawBounds = new Rect(left, top, right, bottom);
        }

        public void postInvalidate() {
            invalidateSelf();
        }

        public Rect getDrawBounds() {
            return drawBounds;
        }

        public int getWidth() {
            return drawBounds.width();
        }

        public int getHeight() {
            return drawBounds.height();
        }

        public int centerX() {
            return drawBounds.centerX();
        }

        public int centerY() {
            return drawBounds.centerY();
        }

        public float exactCenterX() {
            return drawBounds.exactCenterX();
        }

        public float exactCenterY() {
            return drawBounds.exactCenterY();
        }

        public Indicator() {
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
        }

        public static final float SCALE = 1.0f;

        //scale x ,y
        private float[] scaleFloats = new float[]{SCALE,
                SCALE,
                SCALE,
                SCALE,
                SCALE};


        public void draw(Canvas canvas, Paint paint) {
            float circleSpacing = 4;
            float radius = (Math.min(getWidth(), getHeight()) - circleSpacing * 2) / 12;
            float x = getWidth() / 2 - (radius * 2 + circleSpacing);
            float y = getHeight() / 2;
            for (int i = 0; i < 4; i++) {
                canvas.save();
                float translateX = x + (radius * 2) * i + circleSpacing * i;
                canvas.translate(translateX, y);
                canvas.scale(scaleFloats[i], scaleFloats[i]);
                canvas.drawCircle(0, 0, radius, paint);
                canvas.restore();
            }
        }

        public ArrayList<ValueAnimator> onCreateAnimators() {
            ArrayList<ValueAnimator> animators = new ArrayList<>();
            int[] delays = new int[]{120, 240, 360, 480};
            for (int i = 0; i < 4; i++) {
                final int index = i;

                ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);

                scaleAnim.setDuration(750);
                scaleAnim.setRepeatCount(-1);
                scaleAnim.setStartDelay(delays[i]);

                addUpdateListener(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        scaleFloats[index] = (float) animation.getAnimatedValue();
                        postInvalidate();

                    }
                });
                animators.add(scaleAnim);
            }
            return animators;
        }


    }

}
