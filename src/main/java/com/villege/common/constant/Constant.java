package com.villege.common.constant;

/**
 * 常量
 *
 * @author Lewis 31343080@qq.com
 */
public class Constant {
    /**
     * 超级管理员
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 成功
     */
    public static final int SUCCESS = 0;
    /**
     * 失败
     */
    public static final int FAIL = 1;
    /**
     * 开启
     */
    public static final int OPEN = 1;
    /**
     * 关闭
     */
    public static final int CLOSE = 0;
    /**
     * OK
     */
    public static final String OK = "OK";
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 现金支付
     */
    public static final String CASH = "cash";
    /**
     * 微信支付
     */
    public static final String WXPAY = "wxpay";
    /**
     * 支付宝支付
     */
    public static final String ALIPAY = "alipay";
    /**
     * token header
     */
    public static final String TOKEN_HEADER = "token";
    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(1),
        /**
         * 菜单
         */
        MENU(2),
        /**
         * 按钮
         */
        BUTTON(3);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
