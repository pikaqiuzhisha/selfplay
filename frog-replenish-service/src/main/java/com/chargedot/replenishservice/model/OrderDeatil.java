package com.chargedot.replenishservice.model;
import lombok.Data;

@Data
public class OrderDeatil {
    private String orderNumber;
    private String deviceNumber;
    private Integer startType;
    private String startedAt;
    private String finishedAt;
    private String chargeFinishReason;
    private Integer refundAct;
    private Integer durationPlan;
    private Integer duration;
    private Integer samplingPower;
    private Integer payment;
    private Integer payStatus;
}
