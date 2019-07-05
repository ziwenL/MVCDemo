package com.ziwenl.library.http;

import com.orhanobut.hawk.Hawk;
import com.ziwenl.library.constant.HawkConst;
import com.ziwenl.library.utils.JsonUtil;
import com.ziwenl.library.utils.LogUtil;

import java.io.IOException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * PackageName : com.ziwenl.library.http
 * Author : Ziwen Lan
 * Date : 2019/2/26
 * Time : 11:10
 * Introduction : 网络请求回调接口
 */
public abstract class RequestCallBack<T> extends DisposableObserver<JsonResult<T>> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e("RequestCallBack", "Throwable=" + e.toString());
        String errorMessage = "网络错误";
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ResponseBody body = httpException.response().errorBody();
            try {
                JsonResult result = JsonUtil.fromJson(body.string(), JsonResult.class);
                if (result != null) {
                    errorMessage = result.message;
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            e.printStackTrace();
        }
        fail(-1, errorMessage);
    }

    @Override
    public void onNext(JsonResult<T> response) {
        //已经移除订阅，return
        if (isDisposed()) {
            return;
        }

        try {
            if (response == null) {
                fail(-1, "网络请求失败");
            } else {
                if (response.isOk()) {
                    success(response.data);
                } else {
                    if (response.code == 40001) {
                        //用户已在其它设备上登录
                        Hawk.put(HawkConst.SESSION_ID, "");
                    }
                    fail(response.code, response.message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void success(T response);

    public abstract void fail(int errCode, String errStr);
}
