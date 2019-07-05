package com.ziwenl.library.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * PackageName : com.ziwenl.distribution.constant
 * Author : Ziwen Lan
 * Date : 2018/11/21
 * Time : 10:27
 * Introduction : 打开类型常量汇总
 */

public interface OpenConstant {

    /**
     * 权限请求码
     */
    int PERMISSION_PICTURE = 10001;//相机、图片及读写权限
    int PERMISSION_LOCATION = 10002;//定位权限
    int PERMISSION_CALL_PHONE = 10003;//拨打电话权限
    int PERMISSION_CAMERA = 10004;//相机权限
    int PERMISSION_READ_EXTERNAL_STORAGE = 10005;//相册权限


    /**
     * 页面请求码
     */
    int REQUEST_CODE_SERVICE_PROTOCOL = 20001;//用户协议
    int REQUEST_CODE_SELECT_CITY = 20002;//选择城市
    int REQUEST_CODE_SELECT_STORE = 20003;//选择门店
    int REQUEST_CODE_MAP_LOCATION = 20004;//地图定位
    int REQUEST_CODE_MAP_LOCATION_SEARCH = 20005;//地图定位
    int REQUEST_CODE_SELECT_PICTURE = 20006;//选择图片
    int REQUESE_CODE_SELECT_AFTER_SALE_GOODS = 20007;//选择售后商品
    int REQUESE_CODE_SELECT_SHIPPING_ADDRESS = 20008;//选择收货地址
    int REQUESE_CODE_ADD_OR_EDIT_SHIPPING_ADDRESS = 20009;//添加/修改收货地址

    /**
     * 售后类型
     */
    int AFTER_SALE_ONLY_RETURN = 1001;//仅退款
    int AFTER_SALE_RETURNS = 1002;//退货退款

    @IntDef({AFTER_SALE_ONLY_RETURN, AFTER_SALE_RETURNS})
    @Retention(RetentionPolicy.SOURCE)
    @interface AfterSaleType {
    }

    /**
     * 优惠券类型
     */
    int COUPON_UNUSED = 1;//未使用
    int COUPON_USED = 2;//已使用
    int COUPON_EXPIRED = 3;//已过期

    @IntDef({COUPON_UNUSED, COUPON_USED, COUPON_EXPIRED})
    @Retention(RetentionPolicy.SOURCE)
    @interface CouponType {
    }

    /**
     * 修改支付密码的类型
     */
    int FROM_FIRST_SET_UP = 1009;//首次设置
    int FROM_FORGOT = 1010;//忘记重置

    @IntDef({FROM_FIRST_SET_UP, FROM_FORGOT})
    @Retention(RetentionPolicy.SOURCE)
    @interface SetUpPayPwdType {
    }


    /**
     * 订单类型
     */
    int ORDER_ALL = -1;//全部
    int ORDER_PAY = 10;//待付款
    int ORDER_SHIP = 20;//待发货
    int ORDER_RECEIVE = 30;//待收货
    int ORDER_COMPLETED = 40;//待评价

    @IntDef({ORDER_ALL, ORDER_PAY, ORDER_SHIP, ORDER_RECEIVE, ORDER_COMPLETED})
    @Retention(RetentionPolicy.SOURCE)
    @interface OrderType {
    }

}
