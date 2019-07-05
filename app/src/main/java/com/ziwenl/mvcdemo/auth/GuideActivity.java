package com.ziwenl.mvcdemo.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ziwenl.library.base.BaseActivity;
import com.ziwenl.library.constant.HawkConst;
import com.orhanobut.hawk.Hawk;
import com.ziwenl.mvcdemo.MainActivity;
import com.ziwenl.mvcdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * PackageName : com.congya.store.auth
 * Author : Ziwen Lan
 * Date : 2019/6/25
 * Time : 17:53
 * Introduction : 引导页
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.vp_main)
    ViewPager mVpMain;
    GuidePageAdapter mAdapter;
    //引导页图片
    int[] mPictures = {R.drawable.ic_empty, R.drawable.ic_empty, R.drawable.ic_empty};

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mAdapter = new GuidePageAdapter(context, mPictures);
        mVpMain.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    /**
     * 引导页适配器
     */
    private class GuidePageAdapter extends PagerAdapter {
        private List<View> mData = new ArrayList<>();

        /**
         * @param imgRes 图片资源
         */
        public GuidePageAdapter(Context context, int... imgRes) {
            initView(context, imgRes);
        }


        /**
         * 初始化图片
         */
        private void initView(Context context, int[] imgRes) {
            ImageView imageView = null;
            ViewGroup.LayoutParams params = null;
            for (int i = 0; i < imgRes.length; i++) {
                imageView = new ImageView(context);
                imageView.setImageResource(imgRes[i]);
                params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                if (i == imgRes.length - 1) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Hawk.put(HawkConst.FIRST_ENTER, false);
                            startActivity(null, MainActivity.class);
                            finishSimple();
                        }
                    });
                }
                mData.add(imageView);
            }

        }


        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mData.get(position));//删除页卡
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mData.get(position));
            return mData.get(position);
        }
    }
}
