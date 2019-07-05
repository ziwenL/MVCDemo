package com.ziwenl.library.constant;

/**
 * PackageName : com.ziwenl.library.constant
 * Author : Ziwen Lan
 * Date : 2019/4/16
 * Time : 15:10
 * Introduction :
 */
public interface EventBusConstant {
    /**
     * EventBus Message Code
     */
    int LOGIN_SUCCESS = 1001;//登录成功
    int LOGOUT_SUCCESS = 1002;//退出登录
    int ADDRESS_UPDATE = 1003;//地址更新
    int HEADLINE_LIKE_NOTICE = 1004;//头条点赞通知
    int SHOPCART_UPDATE = 1005;//购物车刷新
    int ORDER_UPDATE = 1006;//订单刷新

}
