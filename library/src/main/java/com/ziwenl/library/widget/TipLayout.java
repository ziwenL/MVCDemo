package com.ziwenl.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ziwenl.library.R;

import androidx.annotation.DrawableRes;

/**
 * PackageName : com.ziwenl.library.widget
 * Author : Ziwen Lan
 * Date : 2019/2/20
 * Time : 15:25
 * Introduction : 提示布局(加载中,无网络,空值提示,出错)还有很大的优化空间
 */
public class TipLayout extends RelativeLayout {
    private final SparseArray<View> mViews;
    private LayoutInflater mInflater;
    private View mNetErrorView;//网络错误
    private View mLoadingView;//加载中
    private View mDiyEmptyView;//空值提示

    public boolean isRefreshing = false;//是否正在加载
    private TextView mTvEmptyTip;
    private ImageView mIvEmptyPicture;
    private ImageView mIvLoadingPicutre;
    private ProgressBar mProgressLoading;
    private Animation mAnimation;

    public TipLayout(Context context) {
        super(context);
        this.mViews = new SparseArray<View>();
        initView(context, null, 0);
    }

    public TipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mViews = new SparseArray<View>();
        initView(context, attrs, 0);
    }

    public TipLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mViews = new SparseArray<View>();
        initView(context, attrs, defStyleAttr);
    }

    private void initView(final Context context, AttributeSet attrs, int defStyleAttr) {
        mInflater = LayoutInflater.from(context);
        mNetErrorView = mInflater.inflate(R.layout.widget_network_error, null);
        mLoadingView = mInflater.inflate(R.layout.widget_loading, null);
        mDiyEmptyView = mInflater.inflate(R.layout.widget_empty, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mNetErrorView.setLayoutParams(params);
        mLoadingView.setLayoutParams(params);
        mDiyEmptyView.setLayoutParams(params);
        //加载中时相关控件
        mProgressLoading = mLoadingView.findViewById(R.id.progress_loading);
        mIvLoadingPicutre = mLoadingView.findViewById(R.id.iv_loading_picture);
        //无数据时相关控件
        mTvEmptyTip = mDiyEmptyView.findViewById(R.id.tv_tip_text);
        mIvEmptyPicture = mDiyEmptyView.findViewById(R.id.iv_tip_picture);

        addView(mNetErrorView);
        addView(mLoadingView);
        addView(mDiyEmptyView);

        //重新加载点击事件
        mNetErrorView.findViewById(R.id.btn_reload).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadClick != null) {
                    showLoading();
                    onReloadClick.onReload();
                }
            }
        });

        mLoadingView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //消费点击事件
            }
        });

        //显示内容时执行的渐变动画
        mAnimation = AnimationUtils.loadAnimation(context, R.anim.alpha_tiplayout);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearAnimation();
                setVisibility(GONE);
                isRefreshing = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //判断当前是否可见,显示loading
        if (getVisibility() == View.VISIBLE)
            showLoading();
    }

    private void resetStatus() {
        this.setVisibility(VISIBLE);
        mNetErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mDiyEmptyView.setVisibility(View.GONE);
    }

    /**
     * 显示文本内容（隐藏当前view）
     */
    public void showContent() {
        startAnimation(mAnimation);
//        if (getVisibility() == VISIBLE)
//            setVisibility(GONE);
    }

    /**
     * 显示加载中布局
     */
    public void showLoading() {
        resetStatus();
        mLoadingView.setBackgroundResource(R.color.white);
        mLoadingView.setVisibility(VISIBLE);
        isRefreshing = true;
    }

    /**
     * 显示透明的加载中布局
     */
    public void showLoadingTransparent() {
        resetStatus();
        mLoadingView.setBackgroundResource(android.R.color.transparent);
        mLoadingView.setVisibility(VISIBLE);
        isRefreshing = true;
    }

    /**
     * 显示没有网络
     */
    public void showNetError() {
        resetStatus();
        mNetErrorView.setBackgroundResource(R.color.white);
        mNetErrorView.setVisibility(VISIBLE);
        isRefreshing = false;
    }

    /**
     * 显示空数据
     */
    public void showEmpty() {
        resetStatus();
        //mDiyEmptyView.setBackgroundResource(R.color.white);
        //存在自定义空值layout
        this.setVisibility(VISIBLE);
        mNetErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mDiyEmptyView.setVisibility(VISIBLE);
        isRefreshing = false;
    }

    /**
     * 设置空数据时的提示文本和展示图片
     *
     * @param emptyText         提示文本
     * @param empytPictureResId 展示图片资源id
     */
    public void setEmptyTextAndPicture(CharSequence emptyText, @DrawableRes int empytPictureResId) {
        mTvEmptyTip.setText(emptyText);
        mIvEmptyPicture.setImageResource(empytPictureResId);
    }

    /**
     * 设置加载中时显示的图片
     *
     * @param loadingPictureResId
     */
    public void setLoadingPictureResId(@DrawableRes int loadingPictureResId) {
        mIvLoadingPicutre.setImageResource(loadingPictureResId);
        mProgressLoading.setVisibility(GONE);
        mIvLoadingPicutre.setVisibility(VISIBLE);
    }

    /**
     * 是否正在加载数据（转圈圈）
     *
     * @return
     */
    public boolean isRefreshing() {
        return isRefreshing;
    }


    /**
     * 重新加载点击事件
     */
    OnReloadClick onReloadClick;

    public void setOnReloadClick(OnReloadClick onReloadClick) {
        this.onReloadClick = onReloadClick;
    }

    public interface OnReloadClick {
        void onReload();
    }

}
