package com.chargedot.replenishservice.model;

import lombok.Data;

@Data
public class ChargeDetail {
    private String chargeFinishReason;
    private String address;
    private String startedAt;
    private String finishedAt;
    private Integer power;
    private String deviceNumber;
    private Integer duration;
    private Integer durationPlan;
    private Integer status;
    private String dsname;
    private String orderNumber;
    private String feeDetailSnap;
    private Integer orderStatus;
    private Integer payStatus;
    private Integer payType;
    private Integer payment;
    private String portNumber;
    private String portName;
    private Integer refundAct;
    private Integer refundVirtual;
    private Integer startType;
    private Integer samplingPower;
}
