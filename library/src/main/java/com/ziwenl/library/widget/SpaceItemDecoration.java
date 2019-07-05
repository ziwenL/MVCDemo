package com.ziwenl.library.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView的空白间隔
 * Created by z on 2017/1/11 0011.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    int mSpaceLeft, mSpaceTop, mSpaceRight, mSpaceBottom;
    private boolean isGrid, isHorizontal;
    private int spanCount;

    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     * <p>
     * <p>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     * <p>
     * <p>
     * If you need to access Adapter for additional data, you can call
     * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (isGrid) {
            outRect.left = mSpaceLeft;
            if (parent.getChildAdapterPosition(view) % spanCount != 0 || parent.getChildAdapterPosition(view) / spanCount == 0) {
                outRect.top = mSpaceTop;
            }
            if (parent.getChildAdapterPosition(view) % spanCount != 0) {
                outRect.right = mSpaceRight;
            }
        } else {
            outRect.right = mSpaceRight;
            if (isHorizontal) {
                outRect.top = mSpaceTop;
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.left = mSpaceRight;
                }
            } else {
                outRect.left = mSpaceRight;
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = mSpaceTop;
                }
            }

        }
        outRect.bottom = mSpaceBottom;

    }

    public SpaceItemDecoration(int mSpaceLeft, int mSpaceTop, int mSpaceRight, int mSpaceBottom, boolean isGrid, int spanCount) {
        this.mSpaceLeft = mSpaceLeft;
        this.mSpaceTop = mSpaceTop;
        this.mSpaceRight = mSpaceRight;
        this.mSpaceBottom = mSpaceBottom;
        this.isGrid = isGrid;
        this.spanCount = spanCount;
    }

    public SpaceItemDecoration(int mSpaceLeft, int mSpaceTop, int mSpaceRight, int mSpaceBottom, boolean isHorizontal) {
        this.mSpaceLeft = mSpaceLeft;
        this.mSpaceTop = mSpaceTop;
        this.mSpaceRight = mSpaceRight;
        this.mSpaceBottom = mSpaceBottom;
        this.isHorizontal = isHorizontal;
    }
}