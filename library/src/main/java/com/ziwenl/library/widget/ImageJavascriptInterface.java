package com.ziwenl.library.widget;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.ziwenl.library.activity.ImageActivity;

import java.util.ArrayList;

/**
 * PackageName : com.ziwenl.distribution.widgets
 * Author : Ziwen Lan
 * Date : 2019/1/22
 * Time : 17:01
 * Introduction : js通信接口
 */
public class ImageJavascriptInterface {
    private Context context;

    public ImageJavascriptInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void openImage(String img) {
        ArrayList<String> imgStrs = new ArrayList<>();
        imgStrs.add(img);
        ImageActivity.open(context, 0, imgStrs);
    }
}
