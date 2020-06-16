package com.chargedot.replenishservice.config;

/**
 * @Author：caoj
 * @Description：
 * @Data：Created in 2018/3/16
 */
public class ConstantConfig {

    /**
     * 卡类型: 1包月次卡 2包月包时卡 3充值卡 4手机号
     */
    public static final int CARD_TYPE_OF_MONTH_COUNT = 1;
    public static final int CARD_TYPE_OF_MONTH_TIME = 2;
    public static final int CARD_TYPE_OF_BALANCE = 3;
    public static final int CERT_OF_PHONE = 4;
    public static final int CARD_TYPE_OF_SUPPLEMENT=5;

    /**
     * return value
     */
    public static final Integer RET_SYSTEM_BUSY = -1;// 系统繁忙，稍后重试
    public static final Integer RET_SUCCESS = 0;// 成功
    public static final Integer RET_POST_PARAM_ERROR = 4003;// post参数错误
    public static final Integer RET_SYSTEM_ERROR = 500;// 系统错误
    /**
     * 1为已创建,2为进行中,3为正常充电完成,4为过流停止充电
     */
    public static final int CREATED = 1;
    public static final int ONGOING = 2;
    public static final int FINISH_SUCCESS = 3;
    public static final int FINISH_OUT_OF_AC = 4;

    /**
     * card status 1可使用2使用中3不可使用
     */
    public final static int CARD_AVAILABLE = 1;
    public final static int CARD_OCCUPYING = 2;
    public final static int CARD_UNAVAILABLE = 3;

    /**
     * bangdage_status 0未绑定1已绑定2已挂失
     */
    public final  static int CARD_UNBANGDAEDED=0;
    public final  static int CARD_BANGDAGED=1;
    public final  static int CARD_DROPED=2;
    /**
     * 启动方式 1为微信2为app3为卡
     */
    public static final int START_BY_WX = 1;
    public static final int START_BY_APP = 2;
    public static final int START_BY_CARD = 3;

    /**
     * 供应商
     */
    public static final int PROVID_BY_ELECFROG=1;

    /**
     * return message
     */
    public static final String MSG_SYSTEM_BUSY = "system busy, please try again later.";
    public static final String MSG_POST_PARAM_ERROR = "post param is illegal.";
    public static final String MSG_SYSTEM_ERROR = "system error, please try again later.";
    public static final String MSG_SUCCESS = "request success.";

}
