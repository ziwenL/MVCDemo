package com.ziwenl.library.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * PackageName : com.ziwenl.library.base
 * Author : Ziwen Lan
 * Date : 2019/2/25
 * Time : 14:35
 * Introduction : 自定义弹窗
 */
public class BaseToast {
    private Toast toast;
    private final View rootView;
    private Context context;
    private WindowManager.LayoutParams lp;
    private long mLastTimeShow = 0;  //最后一次显示时间


    public BaseToast(Context context, @LayoutRes int layoutRes) {
        rootView = LayoutInflater.from(context).inflate(layoutRes, null);
        toast = new Toast(context);
        toast.setView(rootView);
        this.context = context;
    }

    public <T extends View> T findViewByRootView(int id) {
        return (T) rootView.findViewById(id);
    }


    public void setText(@IdRes int viewID, String content) {
        try {
            TextView view = findViewByRootView(viewID);
            view.setText(content);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    public void setImageResource(@IdRes int viewID, @DrawableRes int res) {
        try {
            ImageView imageView = findViewByRootView(viewID);
            imageView.setImageResource(res);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    public void setVisibility(@IdRes int viewID, int visibility) {
        try {
            View view = findViewByRootView(viewID);
            view.setVisibility(visibility);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    /**
     * 显示在底部
     */
    public void show() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastTimeShow > 2000) {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * 显示在屏幕中间
     */
    public void showCenter() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastTimeShow > 2000) {
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
            mLastTimeShow = nowTime;
        }
    }

    /**
     * 显示在屏幕中间且背景色变暗
     */
    public void showCenterBrightness() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastTimeShow > 2000) {
            adjustViewBrightness(rootView, (Activity) context);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
            mLastTimeShow = nowTime;
        }
    }

    /**
     * 显示时间
     *
     * @param duration @Toast.Duration
     */
    public void show(int duration) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastTimeShow > 2000) {
            toast.setDuration(duration);
            toast.show();
        }
    }

    /**
     * 窗口背景变暗
     *
     * @param view
     * @param context
     */
    private void adjustViewBrightness(View view, Activity context) {
        WindowManager wm = context.getWindowManager();
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        int w = point.x * 3 / 4;
        lp = context.getWindow().getAttributes();
        if (lp.alpha == 1.0f) {
            lp.alpha = 0.8f;
            context.getWindow().setAttributes(lp);
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            view.addOnAttachStateChangeListener(clearFlags);
        }
    }

    private View.OnAttachStateChangeListener clearFlags = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View view) {

        }

        @Override
        public void onViewDetachedFromWindow(View view) {
            if (lp.alpha == 0.8f) {
                lp.alpha = 1.0f;
                ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                ((Activity) context).getWindow().setAttributes(lp);
            }
        }
    };
}
