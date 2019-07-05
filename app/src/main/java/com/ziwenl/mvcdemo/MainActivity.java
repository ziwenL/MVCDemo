package com.ziwenl.mvcdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ziwenl.library.base.BaseActivity;
import com.ziwenl.mvcdemo.ui.category.CategoryFragment;
import com.ziwenl.mvcdemo.ui.home.HomeFragment;
import com.ziwenl.mvcdemo.ui.mine.MineFragment;
import com.ziwenl.mvcdemo.ui.news.NewsFragment;
import com.ziwenl.mvcdemo.ui.shopcart.ShopCartFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_main)
    FrameLayout mFlMain;
    @BindView(R.id.rb_home)
    RadioButton mRbHome;
    @BindView(R.id.rb_category)
    RadioButton mRbCategory;
    @BindView(R.id.rb_find)
    RadioButton mRbFind;
    @BindView(R.id.rb_cart)
    RadioButton mRbCart;
    @BindView(R.id.rb_mine)
    RadioButton mRbMine;
    private String mResumeFragmentName = "";//当前正在显示的fragment
    private long mLastTimePressed = 0;  //最后一次按返回键的时间

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRbHome.performClick();
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.rb_home, R.id.rb_category, R.id.rb_find, R.id.rb_cart, R.id.rb_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                replaceFragment(HomeFragment.class.getName());
                break;
            case R.id.rb_category:
                replaceFragment(CategoryFragment.class.getName());
                break;
            case R.id.rb_find:
                replaceFragment(NewsFragment.class.getName());
                break;
            case R.id.rb_cart:
                replaceFragment(ShopCartFragment.class.getName());
                break;
            case R.id.rb_mine:
                replaceFragment(MineFragment.class.getName());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (HomeFragment.class.getName().equals(mResumeFragmentName)) {
            //当前在首页
            if (System.currentTimeMillis() - mLastTimePressed < 1000) {
                finishSimple();
            } else {
                mLastTimePressed = System.currentTimeMillis();
                showToast("再按一次退出" + getString(R.string.app_name));
            }
        } else {
            mRbHome.performClick();
        }
    }

    /**
     * 用tempFragment替代当前Fragment, 并给tempFragment增加一个tag，以便下次调用，不用新建
     *
     * @param tag .class.getName
     */
    public void replaceFragment(String tag) {
        mResumeFragmentName = tag;
        Fragment tempFragment = getSupportFragmentManager().findFragmentByTag(tag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (tempFragment == null) {
            try {
                tempFragment = (Fragment) Class.forName(tag).newInstance();
                transaction.add(R.id.fl_main, tempFragment, tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (tag.equals(fragment.getTag())) {
                transaction.show(fragment);
            } else
                transaction.hide(fragment);
        }
        transaction.commitAllowingStateLoss();
    }


}
