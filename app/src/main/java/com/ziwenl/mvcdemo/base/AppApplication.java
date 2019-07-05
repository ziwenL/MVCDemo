package com.ziwenl.mvcdemo.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.ziwenl.library.constant.EventBusConstant;
import com.ziwenl.library.constant.HawkConst;
import com.ziwenl.library.dto.MessageEvent;
import com.ziwenl.library.glide.GlideUtil;
import com.ziwenl.library.http.Http;
import com.ziwenl.library.utils.StringUtil;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.EventBus;

/**
 * PackageName : com.congya.store.base
 * Author : Ziwen Lan
 * Date : 2019/6/25
 * Time : 17:54
 * Introduction :
 */
public class AppApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决方法数超过65536问题
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Hawk
        Hawk.init(this).build();
        //初始化网络
        Http.initHttp(this);
        //初始化Glide
        GlideUtil.init(this);
        //初始化Glide并设置全局占位头像和占位图
        //GlideUtil.init(this, R.drawable.ic_empty,R.drawable.ic_empty);
    }


    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        if (Hawk.contains(HawkConst.SESSION_ID)) {
            return !StringUtil.isEmpty(Hawk.get(HawkConst.SESSION_ID, ""));
        }
        return false;
    }


    /**
     * 退出登录
     * 退出登录时进行的操作
     */
    public static void logout() {
        Hawk.delete(HawkConst.SESSION_ID);
        EventBus.getDefault().post(new MessageEvent(EventBusConstant.LOGOUT_SUCCESS));
    }

    /**
     * 登录
     * 登录后进行的操作
     */
    public static void login(String account) {
        Hawk.put(HawkConst.LOGIN_ACCOUNT, account);
        EventBus.getDefault().post(new MessageEvent(EventBusConstant.LOGIN_SUCCESS));
//        HXKFChatUtil.registered(String.valueOf(response.getUserId()), "123456");
//        JPushUtil.get().setAlias(String.valueOf(response.getUserId()));
    }
}
