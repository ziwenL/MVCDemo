package com.ziwenl.library.widget.photoview.gestures;

/**
 * Created by ziwenl on 2015/6/9.
 */
import android.view.MotionEvent;

public interface GestureDetector {

    public boolean onTouchEvent(MotionEvent ev);

    public boolean isScaling();

    public boolean isDragging();

    public void setOnGestureListener(OnGestureListener listener);

}