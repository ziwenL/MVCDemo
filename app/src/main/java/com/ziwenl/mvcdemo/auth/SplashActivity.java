package com.ziwenl.mvcdemo.auth;

import android.os.Bundle;

import com.ziwenl.library.base.BaseActivity;
import com.ziwenl.library.constant.HawkConst;
import com.orhanobut.hawk.Hawk;
import com.ziwenl.mvcdemo.MainActivity;
import com.ziwenl.mvcdemo.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * PackageName : com.congya.store.auth
 * Author : Ziwen Lan
 * Date : 2019/6/25
 * Time : 17:23
 * Introduction : 启动页
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    protected boolean fillInStatusBar() {
        return true;
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_splash;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (Hawk.get(HawkConst.FIRST_ENTER, true)) {
                            startActivityTransparnet(null, GuideActivity.class);
                            finishSimple();
                        } else {
                            startActivityTransparnet(null, MainActivity.class);
                            finishSimple();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void initListener() {

    }
}
