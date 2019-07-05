package com.ziwenl.library.http;

import com.orhanobut.hawk.Hawk;
import com.ziwenl.library.constant.HawkConst;
import com.ziwenl.library.utils.LogUtil;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * PackageName : com.ziwenl.library.http
 * Author : Ziwen Lan
 * Date : 2019/2/26
 * Time : 10:40
 * Introduction : 请求头拦截器
 */
public class RequestHeaderInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        Request.Builder builder = request.newBuilder();
        //记录请求头信息
        StringBuffer headStr = new StringBuffer();
        //添加头信息
        if (Hawk.contains(HawkConst.SESSION_ID)) {
            String sessionId = Hawk.get(HawkConst.SESSION_ID);
            LogUtil.e("sessionId=" + sessionId);
            builder.addHeader("sessionId", sessionId);
            if (headStr.length() == 0) {
                headStr.append("sessionId=" + sessionId);
            } else {
                headStr.append("&");
                headStr.append("sessionId=" + sessionId);
            }
        }
        //打印请求信息
        String requestMessage;
        requestMessage = request.method() + ' ' + request.url();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            requestMessage += "?" + headStr + ' ' + buffer.readString(UTF8);
        }
        try {
            LogUtil.e("请求信息", URLDecoder.decode(requestMessage, "utf-8"));
        } catch (Exception e) {
            LogUtil.e("请求信息", "e.getMessage() = " + e.getMessage());
            LogUtil.e("请求信息", requestMessage);
        }
        return chain.proceed(builder.build());
    }
}
