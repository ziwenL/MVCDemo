package com.ziwenl.library.utils;

import android.util.Log;

/**
 * Log工具类
 * <p>
 * 建议任何打印日志的地方使用此工具类
 * 用意：软件发布后，方便屏蔽掉所有log日志打印，提高应用的安全性
 * </p>
 * <p>
 * 屏蔽方式：建议采用注释代码的方法。
 * 原因：使用布尔成员标记是否打印日志，容易遭到内存注入等手段的破解。
 * </p>
 */
public class LogUtil {


    private static final String TAG = "LogUtil";

    public static void v(Object msg) {
        v(TAG, msg+"");
    }

    public static void d(Object msg) {
        d(TAG, msg+"");
    }

    public static void i(Object msg) {
        i(TAG, msg+"");
    }

    public static void w(Object msg) {
        w(TAG, msg+"");
    }

    public static void e(Object msg) {
        e(TAG, msg+"");
    }


    public static void v(String tag, Object msg) {
        Log.v(tag, msg+"");
    }

    public static void d(String tag, Object msg) {
        Log.d(tag, msg+"");
    }

    public static void i(String tag, Object msg) {
        Log.i(tag, msg+"");
    }

    public static void w(String tag, Object msg) {
        Log.w(tag, msg+"");
    }

    public static void e(String tag, Object msg) {
        Log.e(tag, msg+"");
    }

    public static void log(String msg) {
        e(msg);
    }

    public static void simpleLog(Object msg) {
        Log.e(TAG, "" + msg);
    }

//    public static void v(Object msg) {
////        v(TAG, msg+"");
//    }
//
//    public static void d(Object msg) {
////        d(TAG, msg+"");
//    }
//
//    public static void i(Object msg) {
////        i(TAG, msg+"");
//    }
//
//    public static void w(Object msg) {
////        w(TAG, msg+"");
//    }
//
//    public static void e(Object msg) {
////        e(TAG, msg+"");
//    }
//
//
//    public static void v(String tag, Object msg) {
////        Log.v(tag, msg+"");
//    }
//
//    public static void d(String tag, Object msg) {
////        Log.d(tag, msg+"");
//    }
//
//    public static void i(String tag, Object msg) {
////        Log.i(tag, msg+"");
//    }
//
//    public static void w(String tag, Object msg) {
////        Log.w(tag, msg+"");
//    }
//
//    public static void e(String tag, Object msg) {
////        Log.e(tag, msg+"");
//    }
//
//    public static void log(String msg) {
////        e(msg);
//    }
//
//    public static void simpleLog(Object msg) {
////        Log.e(TAG, "" + msg);
//    }


}
