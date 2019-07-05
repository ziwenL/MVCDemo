package com.ziwenl.library.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ziwenl.library.R;
import com.ziwenl.library.dialog.DialogLoading;
import com.ziwenl.library.dto.MessageEvent;
import com.ziwenl.library.utils.AppManager;
import com.ziwenl.library.utils.KeyBoardUtils;
import com.ziwenl.library.utils.SystemUtils;
import com.ziwenl.library.widget.TipLayout;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * PackageName : com.ziwenl.library.activity
 * Author : Ziwen Lan
 * Date : 2019/2/20
 * Time : 14:08
 * Introduction :
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    public String TAG;
    protected BaseActivity context;
    protected ImageButton mIvBack;
    private TipLayout mTipLayout;
    protected TextView mTvTitle;
    private Unbinder mUnBinder;
    public DialogLoading mLoading;
    private BaseToast mToast;
    private boolean mIsFillSuccess;
    public int pPageNumber = 1;
    public int pPageSize = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        //设置别的主题
        setOtherTheme();
        //判断是否设置透明状态栏
        if (fillInStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mIsFillSuccess = true;
        }
        super.onCreate(savedInstanceState);
        context = this;
        //绑定布局
        setContentView(getViewId());
        initView();
        //判断是否设置状态栏字体颜色
        if (setStatusBarDarkMode()) {
            //将状态栏字体设为暗色
            SystemUtils.setStatusBarDarkMode(context);
        }
        //保存当前Activity实体
        AppManager.getInstance().addActivity(context);
        //接收数据
        if (getIntent() != null && getIntent().getExtras() != null) {
            onGetBundle(getIntent().getExtras());
        }
        //初始化
        init(savedInstanceState);
        initListener();
        if (isBindEventBusHere() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 绑定布局
     *
     * @param layoutResID 布局ID
     */
    public void setContentView(int layoutResID) {
        if (setStatusBarDarkMode() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置状态栏字体颜色为深色时需设置默认延伸状态栏，否则布局会被上移
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            view.setFitsSystemWindows(true);
            super.setContentView(view);
        } else {
            super.setContentView(layoutResID);
        }
        //绑定黄油刀
        mUnBinder = ButterKnife.bind(this);
    }

    /**
     * 初始化
     */
    private void initView() {
        mLoading = new DialogLoading(context);

        mIvBack = findViewById(R.id.iv_back);
        mTvTitle = findViewById(R.id.tv_title);
        mTipLayout = findViewById(R.id.tipLayout);
        if (mIvBack != null) {
            mIvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyBoardUtils.hideSoftKeyboard(context);
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().removeActivity(this);
        if (isBindEventBusHere() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
        //黄油刀解绑
        mUnBinder.unbind();
    }

    protected abstract void onGetBundle(Bundle bundle);

    protected abstract int getViewId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initListener();

    /**
     * 设置别的主题
     */
    public void setOtherTheme() {

    }

    /**
     * 是否填充状态栏
     * 重写返回true表示设置透明状态栏（全屏模式）
     */
    protected boolean fillInStatusBar() {
        return false;
    }


    /**
     * 将状态栏字体设置为暗色
     * 重写返回true表示将状态栏字体设置为暗色
     */
    protected boolean setStatusBarDarkMode() {
        return false;
    }

    /**
     * EventBus 需要注册EventBus的Activity重写该方法,返回true
     */
    protected boolean isBindEventBusHere() {
        return false;
    }

    /**
     * EventBus 主线程运行
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event != null) {
            onEventComing(event);
        }
    }

    /**
     * EventBus 子类重写该方法
     */
    protected void onEventComing(MessageEvent event) {

    }

    /**
     * 是否全屏
     */
    public boolean isFillSuccess() {
        return mIsFillSuccess;
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
    }

    public void showToast(final Object message) {
        if (mToast == null) {
            mToast = new BaseToast(context, R.layout.widget_base_toast);
        }
        mToast.setText(R.id.tv_content, message + "");
        mToast.setVisibility(R.id.iv_icon, View.GONE);
        mToast.showCenter();
    }

    public void showToast(final Object message, final boolean isSuccess) {
        if (mToast == null) {
            mToast = new BaseToast(context, R.layout.widget_base_toast);
        }
        mToast.setText(R.id.tv_content, message + "");
        if (isSuccess) {
            mToast.setImageResource(R.id.iv_icon, R.drawable.popup_bg_succeed);
        } else {
            mToast.setImageResource(R.id.iv_icon, R.drawable.popup_bg_succeed);
        }
        mToast.setVisibility(R.id.iv_icon, View.VISIBLE);
        mToast.showCenter();
    }

    /**
     * 覆写startactivity方法，加入切换动画
     */
    public void startActivity(Bundle bundle, Class<?> target) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        if (lp.alpha == 0.8f) {
            lp.alpha = 1.0f;
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    /**
     * 覆写startactivity方法，加入淡入淡出切换动画
     */
    public void startActivityTransparnet(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_transparent_in, R.anim.slide_transparent_out);
    }

    /**
     * 带回调的跳转，带入场动画
     */
    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    /**
     * 覆写finish方法，覆盖默认方法，加入切换动画
     */
    public void finish() {
        KeyBoardUtils.hideSoftKeyboard(context);
        super.finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    /**
     * 从底部弹出
     */
    public void startActivityBottomToTop(Bundle bundle, Class<?> target) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        if (lp.alpha == 0.8f) {
            lp.alpha = 1.0f;
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }

    /**
     * 从底部收入
     */
    public void finishBottomToTop() {
        finishSimple();
        overridePendingTransition(0, R.anim.push_bottom_out);
    }

    /**
     * 不带切换动画
     */
    public void finishSimple() {
        KeyBoardUtils.hideSoftKeyboard(context);
        super.finish();
    }

    /**
     * 显示加载弹窗
     */
    public void showLoadingDialog() {
        if (!isFinishing() && !mLoading.isShowing()) {
            mLoading.show();
        }
    }


    /**
     * 关闭加载弹窗
     */
    public void dismissLoadingDialog() {
        if (!isFinishing() && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    /**
     * 显示网络错误页
     */
    public void showNetError() {
        if (mTipLayout == null) return;
        mTipLayout.showNetError();
    }

    /**
     * 显示空白页
     */
    public void showEmptyLayout() {
        if (mTipLayout == null) return;
        mTipLayout.showEmpty();

    }

    /**
     * 隐藏空白页
     */
    public void dismissEmptyLayout() {
        if (mTipLayout == null) return;
        mTipLayout.showContent();
    }

    public TipLayout getTipLayout() {
        return mTipLayout;
    }

    public void finishResult(Intent intent) {
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void finishResult() {
        setResult(RESULT_OK);
        this.finish();
    }

    /**
     * RxJava线程调度
     * 与Activity的生命周期绑定（在Destroy方法中取消订阅）
     * subscribeOn指定观察者代码运行的线程
     * observerOn()指定订阅者运行的线程
     * 提供给它一个Observable它会返回给你另一个Observable
     */
    public <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return (Observable<T>) upstream
                        .compose(RxLifecycle.bindUntilEvent(lifecycle(), ActivityEvent.DESTROY))//onDestory方法中取消请求
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    //------------权限请求相关------------------

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, mPermissionCallbacks);
    }

    private EasyPermissions.PermissionCallbacks mPermissionCallbacks = new EasyPermissions.PermissionCallbacks() {
        @Override
        public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
            //获得授予的权限
            requestPermissionSuccess(requestCode);
        }

        @Override
        public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
            //被拒绝的权限
            requestPermissionFailed(requestCode);
//            if (EasyPermissions.somePermissionPermanentlyDenied(BaseActivity.this, perms)) {
//                //存在权限被永久拒绝--此时要跳转系统软件设置页面，让用户手动给予权限
//                new AppSettingsDialog.Builder(BaseActivity.this).setTitle("权限申请")
//                        .setRationale("获取" + mRequestPermissionsRationale + "失败，请手动授权")
//                        .setPositiveButton("确定")
//                        .setNegativeButton("取消")
//                        .build()
//                        .show();
//            } else {
//                //权限获取失败
//                requestPermissionFailed();
//            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        }
    };

    /**
     * 请求权限
     *
     * @param requestCode 权限请求码
     * @param rationale   权限请求描述（eg: 定位权限、图片相关权限）
     * @param perms
     */
    public void requestPermissions(int requestCode, @NonNull String rationale, @NonNull String... perms) {
        if (EasyPermissions.hasPermissions(this, perms)) {
            //存在权限
            requestPermissionSuccess(requestCode);
        } else {
            //不存在权限，发起申请
            EasyPermissions.requestPermissions(this, "申请获取" + rationale, requestCode, perms);
        }
    }

    /**
     * 请求权限失败回调
     * 子类通过重写该方法获取权限请求结果并执行下一步相关操作
     */
    public void requestPermissionFailed(int requestCode) {

    }

    /**
     * 请求成功回调
     * 子类通过重写该方法获取权限请求结果并执行下一步相关操作
     */
    public void requestPermissionSuccess(int requestCode) {

    }
}
