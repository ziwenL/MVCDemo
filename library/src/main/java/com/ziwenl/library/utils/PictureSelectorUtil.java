package com.ziwenl.library.utils;

import android.content.Intent;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.ziwenl.library.R;
import com.ziwenl.library.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * PackageName : com.ziwenl.library.utils
 * Author : Ziwen Lan
 * Date : 2019/2/28
 * Time : 11:39
 * Introduction : 第三方图片选择库工具类
 */
public class PictureSelectorUtil {
    /**
     * 单独拍照圆形裁剪
     */
    public static void openCamera(BaseActivity activity, int requestCode) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .theme(R.style.picture_selector_style)
                .enableCrop(true)// 是否裁剪 true or false
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .withAspectRatio(1, 1)//裁剪比例 如16:9 3:2 3:4 1:1 可自定义  不设置为1:1时会出现拖动不了的情况
                .selectionMode(PictureConfig.SINGLE)
                .forResult(requestCode);
    }

    /**
     * 单独拍照并矩形裁剪
     */
    public static void openCameraRectangle(BaseActivity activity, int requestCode) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .theme(R.style.picture_selector_style)
                .enableCrop(true)// 是否裁剪 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .selectionMode(PictureConfig.SINGLE)
                .withAspectRatio(1, 1)//裁剪比例，不设为1:1会出现无法拖动的问题
                .forResult(requestCode);
    }

    /**
     * 单独拍照不裁剪
     */
    public static void openCameraNoCrop(BaseActivity activity, int requestCode) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .theme(R.style.picture_selector_style)
                .enableCrop(false)// 是否裁剪 true or false
                .selectionMode(PictureConfig.SINGLE)
                .compress(true)// 是否压缩 true or false
                .forResult(requestCode);
    }

    /**
     * 单独选择一张图片并圆形裁剪
     */
    public static void openAluamOne(BaseActivity activity, int requestCode) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_selector_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(4)// 每行显示个数 int
                .enableCrop(true)// 是否裁剪 true or false
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .withAspectRatio(1, 1)//裁剪比例，不设为1:1会出现无法拖动的问题
                .isCamera(false)// 是否显示拍照按钮 true or false
                .isDragFrame(true)// 是否可拖动裁剪框
                .forResult(requestCode);
    }

    /**
     * 单独选择一张图片并矩形裁剪
     */
    public static void openAluamOneRectangle(BaseActivity activity, int requestCode) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_selector_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(4)// 每行显示个数 int
                .enableCrop(true)// 是否裁剪 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .withAspectRatio(1, 1)//裁剪比例，不设为1:1会出现无法拖动的问题
                .isCamera(false)// 是否显示拍照按钮 true or false
                .isDragFrame(true)// 是否可拖动裁剪框
                .forResult(requestCode);
    }

    /**
     * 单独选择一张图片不裁剪
     */
    public static void openAluamOneNoCrop(BaseActivity activity, int requestCode) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_selector_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .imageSpanCount(4)// 每行显示个数 int
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(false)// 是否显示拍照按钮 true or false
                .forResult(requestCode);
    }

    /**
     * 选择多张图片
     */
    public static void openAluamMore(BaseActivity activity, int requestCode, int num) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_selector_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(num)// 最大图片选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(true)// 是否显示拍照按钮 true or false
                .forResult(requestCode);
    }

    /**
     * 缓存清除
     * 包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
     */
    public static void clearCache(BaseActivity activity) {
        PictureFileUtils.deleteCacheDirFile(activity);
    }


    /**
     * 获取原图paths
     */
    public static List<String> getPaths(Intent data) {
        List<String> paths = new ArrayList<>();
        List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
        for (LocalMedia media : localMedia) {
            paths.add(media.getPath());
        }
        return paths;
    }

    /**
     * 获取裁剪后的paths
     */
    public static List<String> getCutPaths(Intent data) {
        List<String> cutPaths = new ArrayList<>();
        List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
        for (LocalMedia media : localMedia) {
            cutPaths.add(media.getCutPath());
        }
        return cutPaths;
    }

}
