package com.chargedot.replenishservice.model.vo;

import com.chargedot.replenishservice.model.DWCert;
import lombok.Data;

import java.util.List;

@Data
public class DataParam {
    /**
     * 卡号集合
     */
    private List<DWCert> dwCertList;

    /**
     * 卡类型
     */
    private Integer type;

    /**
     * 归属公司
     */
    private Integer enterpriseId;

    /**
     * 有效日期
     */
    private  String finishedAt;

    /**
     * 交易类型
     */
    private Integer streamType;
    /**
     *
     */
    private String sessionId;

    /**
     * 卡号
     */
    private String certNumber;

    /**
     * 页码
     *
     */
    private Integer pageNumber;

    /**
     * 每页展示条数
     *
     */
    private Integer pageSize;
    /**
     * 订单号
     */
    private String orderNumber;
}
