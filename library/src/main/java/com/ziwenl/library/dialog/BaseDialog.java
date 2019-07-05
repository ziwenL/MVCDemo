package com.ziwenl.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ziwenl.library.R;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import io.reactivex.annotations.NonNull;

/**
 * PackageName : com.library.dialog
 * Author : Ziwen Lan
 * Date : 2017/11/12
 * Time : 11:09
 * Introduction : 简化dialog的创建过程
 */

public class BaseDialog extends Dialog {
    private Object tag;
    private Map<Integer, Object> tagMap = new HashMap<>();

    /**
     * 默认在中间显示
     *
     * @param context
     * @param layoutId
     */
    public BaseDialog(@NonNull Context context, @LayoutRes int layoutId) {
        super(context, R.style.Dialog);
        this.setCanceledOnTouchOutside(true);
        setContentView(layoutId);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        //window.setWindowAnimations(R.style.bottom_int_out_dialog_style); // 添加动画
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(dm.widthPixels, window.getAttributes().height);
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    /**
     * 自定义显示位置
     *
     * @param context
     * @param layoutId
     * @param gravity eg: Gravity.BOTTOM
     */
    public BaseDialog(@NonNull Context context, @LayoutRes int layoutId, int gravity) {
        super(context, R.style.Dialog);
        this.setCanceledOnTouchOutside(true);
        setContentView(layoutId);
        Window window = getWindow();
        window.setGravity(gravity); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.bottom_int_out_dialog_style); // 添加动画
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(dm.widthPixels, window.getAttributes().height);
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    public <T extends View> T findViewByRootView(int id) {
        return (T) this.findViewById(id);
    }


    public void setText(@IdRes int viewID, String content) {
        try {
            TextView view = findViewByRootView(viewID);
            view.setText(content);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    public void setOnClickListener(@IdRes int viewID, View.OnClickListener clickListener) {
        View view = findViewByRootView(viewID);
        if (view == null) {
            throw new NullPointerException();
        } else {
            view.setOnClickListener(clickListener);
        }
    }

    public void setText(@IdRes int viewID, CharSequence content) {
        TextView view = findViewByRootView(viewID);
        if (view == null) {
            throw new NullPointerException();
        } else {
            view.setText(content);
        }
    }

    public void setCancelClickListener(@IdRes int viewID) {
        View view = findViewByRootView(viewID);
        if (view == null) {
            throw new NullPointerException();
        } else {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
    }

    public void setVisible(int viewId, int visibility) {
        View view = findViewByRootView(viewId);
        view.setVisibility(visibility);
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(int key, Object tag) {
        tagMap.put(key, tag);
    }

    public Object getTag(int key) {
        if (tagMap.containsKey(key)) {
            return tagMap.get(key);
        }
        return null;
    }
}
