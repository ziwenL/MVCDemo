package com.ziwenl.library.adapter.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * PackageName : com.ziwenl.library.adapter.recyclerview
 * Author : Ziwen Lan
 * Date : 2019/2/28
 * Time : 9:39
 * Introduction : RecyclerView Adatper
 * 简化使用流程
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    public List<T> mData;
    private int resource;
    private OnItemListener<T> mOnItemListener;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (mOnItemListener != null) {
                mOnItemListener.onItem(v, mData, position);
            }
        }
    };

    public RecyclerAdapter(List<T> data, int resource) {
        super();
        this.mData = data;
        this.resource = resource;
    }

    public RecyclerAdapter(int resource) {
        super();
        this.resource = resource;
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                resource, parent, false);
        return holder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mClickListener);
        if (mData.get(position) != null) {
            holder.build(mData.get(position), position, mData);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void clear() {
        mData.clear();
    }

    public void addAll(@NonNull List<T> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public abstract BaseViewHolder holder(View view, int viewType);

    public void setOnItemListener(OnItemListener<T> onItemListener) {
        mOnItemListener = onItemListener;
    }
}
