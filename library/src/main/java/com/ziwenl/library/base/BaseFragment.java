package com.ziwenl.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ziwenl.library.R;
import com.ziwenl.library.dialog.DialogLoading;
import com.ziwenl.library.dto.MessageEvent;
import com.ziwenl.library.widget.TipLayout;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.trello.rxlifecycle3.components.support.RxFragment;

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
 * PackageName : com.ziwenl.library.base
 * Author : Ziwen Lan
 * Date : 2019/2/27
 * Time : 9:16
 * Introduction :
 */
public abstract class BaseFragment extends RxFragment {
    public String TAG;
    public Unbinder mUnbinder;
    protected BaseActivity context;
    public DialogLoading mLoading;
    private TipLayout mTipLayout;
    protected View mRootView;
    public int pPageNumber = 1;
    public int pPageSize = 20;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        context = (BaseActivity) getActivity();
        mLoading = new DialogLoading(context);
        if (isBindEventBusHere() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = setContentView(inflater, getViewId());
        mTipLayout = view.findViewById(R.id.tipLayout);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            onGetBundle(bundle);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState);
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 调用该办法可避免重复加载UI
     */
    private View setContentView(LayoutInflater inflater, int resId) {
        if (mRootView == null) {
            mRootView = inflater.inflate(resId, null);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    protected void onGetBundle(Bundle bundle) {
    }

    protected abstract int getViewId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initListener();

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

    protected void finish() {
        context.finish();
    }

    protected void finishSimple() {
        context.finishSimple();
    }

    public void finishResult(Intent intene) {
        context.finishResult(intene);
    }

    public void finishResult() {
        context.finishResult();
    }

    public void startActivity(Bundle bundle, Class<?> target) {
        context.startActivity(bundle, target);
    }

    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(context, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        context.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    public void showToast(final Object message) {
        context.showToast(message);
    }


    /**
     * 显示加载弹窗
     */
    public void showLoadingDialog() {
        if (!context.isFinishing()) {
            mLoading.show();
        }
    }

    /**
     * 关闭加载弹窗
     */
    public void dismissLoadingDialog() {
        if (!context.isFinishing() && mLoading.isShowing()) {
            mLoading.dismiss();
        }
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
                        .compose(RxLifecycle.bindUntilEvent(lifecycle(), FragmentEvent.DESTROY))//onDestory方法中取消请求
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
        if (EasyPermissions.hasPermissions(context, perms)) {
            //存在权限
            requestPermissionSuccess(requestCode);
        } else {
            //不存在权限，发起申请
            EasyPermissions.requestPermissions(context, "申请获取" + rationale, requestCode, perms);
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
