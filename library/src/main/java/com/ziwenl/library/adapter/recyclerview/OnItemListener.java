package com.ziwenl.library.adapter.recyclerview;

import android.view.View;

import java.util.List;

/**
 * PackageName : com.ziwenl.library.adapter.recyclerview
 * Author : Ziwen Lan
 * Date : 2019/2/28
 * Time : 9:44
 * Introduction : RecyclerView Adapter item点击监听
 */
public interface OnItemListener<T> {
    void onItem(View view, List<T> datas, int position);
}
