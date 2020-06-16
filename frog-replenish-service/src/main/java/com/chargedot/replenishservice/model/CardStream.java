package com.chargedot.replenishservice.model;

import lombok.Data;

import java.util.List;

/**
 * @author Eric Gui
 * @date 2019/4/26
 */
@Data
public class CardStream {
    private String orderNumber;
    private String createdAt;
    private String certNumber;
    private Integer changeValue;
    private Integer curRealValue;
    private Integer curValue;
    private Integer realChangeValue;
    private String remarks;
    private int streamType;
    private String name;
    private String finishedAt;
    private String chargeFinishReason;
    private String deviceNumber;
    private Integer duration;
    private Integer durationPlan;
    private Integer status;
    private String dsname;
    private Integer orderStatus;
    private String feeDetailSnap;
    private Integer payStatus;
    private Integer payType;
    private Integer payment;
    private String portName;
    private Integer refundAct;
    private Integer refundVirtual;
}
