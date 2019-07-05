package com.ziwenl.library.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ziwenl.library.R;
import com.ziwenl.library.base.BaseActivity;
import com.ziwenl.library.glide.GlideUtil;
import com.ziwenl.library.widget.photoview.PhotoView;
import com.ziwenl.library.widget.photoview.PhotoViewAttacher;
import com.ziwenl.library.widget.photoview.ViewPagerFixed;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * PackageName : com.ziwenl.library.activitys
 * Author : Ziwen Lan
 * Date : 2019/2/27
 * Time : 10:17
 * Introduction :
 */
public class ImageActivity extends BaseActivity {
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    private static final String STATE_POSITION = "STATE_POSITION";
    ArrayList<String> urls;
    private ViewPagerFixed mPager;
    private int pagerPosition;
    private TextView indicator;

    /**
     * 查看图片
     *
     * @param context
     * @param position
     * @param urls
     */
    public static void open(Context context, int position, ArrayList<String> urls) {
        Intent i = new Intent(context, ImageActivity.class);
        i.putExtra(EXTRA_IMAGE_INDEX, position);
        i.putStringArrayListExtra(EXTRA_IMAGE_URLS, urls);
        context.startActivity(i);
    }

    /**
     * 查看单张图片
     *
     * @param context
     * @param url
     */
    public static void open(Context context, String url) {
        Intent i = new Intent(context, ImageActivity.class);
        i.putExtra(EXTRA_IMAGE_INDEX, 0);
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url);
        i.putStringArrayListExtra(EXTRA_IMAGE_URLS, urls);
        context.startActivity(i);
    }


    @Override
    public int getViewId() {
        return R.layout.activity_image_show;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = context.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = context.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        mPager = (ViewPagerFixed) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);
        CharSequence text = 1 + "/" + mPager.getAdapter().getCount();
        indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = arg0 + 1 + "/" + mPager.getAdapter().getCount();
                indicator.setText(text);
            }
        });
        mPager.setCurrentItem(pagerPosition);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onGetBundle(Bundle bundle) {
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
    }

    @Override
    protected boolean setStatusBarDarkMode() {
        return false;
    }

    public static class ImageDetailFragment extends Fragment {
        private String mImageUrl;
        private PhotoView mImageView;
        private ProgressBar progressBar;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View v = inflater.inflate(R.layout.fragment_image_detail, container, false);
            mImageView = (PhotoView) v.findViewById(R.id.image);
            mImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    ((BaseActivity) getActivity()).finish();
                }
            });
            progressBar = (ProgressBar) v.findViewById(R.id.loading);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
//            GlideUtil.loadPicture(mImageUrl, new DrawableImageViewTarget(mImageView) {
//                @Override
//                public void onLoadStarted(Drawable placeholder) {
//                    super.onLoadStarted(placeholder);
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onLoadFailed(Drawable errorDrawable) {
//                    view.setImageDrawable(errorDrawable);
//                    progressBar.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                    super.onResourceReady(resource, transition);
//                    mImageView.setImageDrawable(resource);
//                    progressBar.setVisibility(View.GONE);
//                }
//            });
            GlideUtil.loadPicture(mImageUrl, mImageView);
        }
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            final ImageDetailFragment f = new ImageDetailFragment();
            final Bundle args = new Bundle();
            args.putString("url", url);
            f.setArguments(args);
            return f;
        }
    }
}
