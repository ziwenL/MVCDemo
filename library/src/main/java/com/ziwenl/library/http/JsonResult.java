package com.ziwenl.library.http;

import com.google.gson.reflect.TypeToken;
import com.ziwenl.library.utils.JsonUtil;

/**
 * PackageName : com.ziwenl.library.http
 * Author : Ziwen Lan
 * Date : 2019/2/26
 * Time : 10:46
 * Introduction : 数据基类
 */
public class JsonResult<T> {
    public int code;
    public String message;
    public T data;

    public JsonResult() {
    }

    public JsonResult(int code, String message, T object) {
        this.code = code;
        this.message = message;
        this.data = object;
    }


    public <T> T get(Class<T> clazz) {
        return JsonUtil.fromObject(data, clazz);
    }

    public <T> T get(TypeToken<T> token) {
        return JsonUtil.fromObject(data, token);
    }

    public <T> T getDecrypt(Class<T> clazz) {
        if (data == null) {
            return null;
        }
        return get(clazz);
    }

    public <T> T getDecrypt(TypeToken<T> token) {
        if (data == null) {
            return null;
        }
        return get(token);
    }

    public boolean isOk() {
        if (code == 1) return true;
        return false;
    }

    public String getData() {
        return String.valueOf(data);
    }


    public String toString() {
        return JsonUtil.toJson(this);
    }

    /**
     * 由字符串构造
     *
     * @param value
     * @return
     */
    public static JsonResult fromString(String value) {
        if (value == null) {
            return null;
        }
        JsonResult result = JsonUtil.fromJson(value, JsonResult.class);
        return result;
    }

    public static JsonResult fail() {
        JsonResult result = new JsonResult(1, "网络连接不可用，请稍后重试", null);
        return result;
    }
}
