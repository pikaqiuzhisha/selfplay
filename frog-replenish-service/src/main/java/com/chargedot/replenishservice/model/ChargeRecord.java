package com.chargedot.replenishservice.model;
import lombok.Data;

@Data
public class ChargeRecord {
    private Integer orderStatus;
    private String certNumber;
    private String startedAt;
    private Integer payStatus;
    private String orderNumber;
    private String deviceNumber;
    private String durationTime;
    private Integer status;
    private String finishedAt;
    private Integer startType;
}
