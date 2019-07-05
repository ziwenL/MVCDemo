package com.ziwenl.library.constant;

/**
 * PackageName : com.ziwenl.library.constant
 * Author : Ziwen Lan
 * Date : 2019/2/26
 * Time : 10:44
 * Introduction : Hawk键名
 */
public interface HawkConst {
    /**
     * 是否首次启动app
     * boolean
     */
    String FIRST_ENTER = "first_enter";

    /**
     * sessionId
     * String
     */
    String SESSION_ID = "sessionId";

    /**
     * 登录账号
     * String
     */
    String LOGIN_ACCOUNT = "login_account";

    /**
     * 搜索历史(商品)
     * List<String>
     */
    String SEARCH_HISTORY = "search_history";

    /**
     * 当前门店ID
     * long
     */
    String CURRENT_STORE_ID = "current_store_id";

    /**
     * 客服电话
     * String
     */
    String SERVER_PHONE = "server_phone";
}
