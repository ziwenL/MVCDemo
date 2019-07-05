package com.ziwenl.library.http;


import com.ziwenl.library.utils.JsonUtil;
import com.ziwenl.library.utils.LogUtil;

import java.io.IOException;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;


/**
 * PackageName : com.ziwenl.library.http
 * Author : Ziwen Lan
 * Date : 2019/2/26
 * Time : 10:45
 * Introduction : 多接口访问
 * <p>
 * RxJava2之后Subscriber改名Disposable
 */
public abstract class HttpMultiInterfaceAccess extends DisposableObserver<List<JsonResult>> {

    @Override
    public void onNext(List<JsonResult> jsonResults) {
        if (isDisposed()) {
            return;
        }
        try {
            if (jsonResults == null) {
                fail(-1, "网络错误");
            } else {
                for (JsonResult jsonResult : jsonResults) {
                    if (!jsonResult.isOk()) {
                        fail(jsonResult.code, jsonResult.message);
                        return;
                    }

                }
                success(jsonResults);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.simpleLog("Throwable=" + e.getMessage());
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
    public void onComplete() {

    }

    public abstract void success(List<JsonResult> response);

    public abstract void fail(int errCode, String errStr);
}
