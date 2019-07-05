package com.ziwenl.library.adapter.recyclerview;

import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
 * PackageName : com.ziwenl.library.adapter.recyclerview
 * Author : Ziwen Lan
 * Date : 2019/2/28
 * Time : 9:20
 * Introduction : RecyclerView.ViewHolder的基类
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public abstract void build(T dto, int position, List<T> datas);
}
