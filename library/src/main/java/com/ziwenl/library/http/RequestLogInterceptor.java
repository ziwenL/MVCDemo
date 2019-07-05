package com.ziwenl.library.http;

import com.ziwenl.library.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * PackageName : com.ziwenl.library.http
 * Author : Ziwen Lan
 * Date : 2019/2/26
 * Time : 10:39
 * Introduction : 请求信息打印拦截器
 */
public class RequestLogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        RequestBody requestBody = request.body();
        ResponseBody responseBody = response.body();
        String requestMessage = "";
        requestMessage = request.method() + ' ' + request.url();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            requestMessage += "?\n" + buffer.readString(UTF8);
        }
        LogUtil.i("RequestLogInterceptor", requestMessage);
        String responseBodyString;
        try {
            responseBodyString = (responseBody == null ? "null" : responseBody.string());
        } catch (Exception e) {
            responseBodyString = "";
            LogUtil.e("RequestLogInterceptor", "e.getMessage() = " + e.getMessage());
        }
        LogUtil.e("RequestLogInterceptor", request.method() + ' ' + request.url() + ' ' + responseBodyString);

        return response.newBuilder().body(ResponseBody.create(responseBody==null? MediaType.parse("application/json"):responseBody.contentType(),
                responseBodyString.getBytes())).build();
    }
}
