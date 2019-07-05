package com.ziwenl.library.widget.photoview.gestures;

/**
 * Created by ziwenl on 2015/6/9.
 */
public interface OnGestureListener {

    public void onDrag(float dx, float dy);

    public void onFling(float startX, float startY, float velocityX,
                        float velocityY);

    public void onScale(float scaleFactor, float focusX, float focusY);

}