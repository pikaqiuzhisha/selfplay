package com.chargedot.replenishservice.model;

import lombok.Data;

@Data
public class ChargeOrder {
    private Integer tryOccupyUserId;
    private String durationTime;
    private String orderNumber;
    private String name;
    private String deviceNumber;
    private String startedAt;

    public void setTryOccupyUserId(Integer tryOccupyUserId) {
        this.tryOccupyUserId = tryOccupyUserId;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public Integer getTryOccupyUserId() {
        return tryOccupyUserId;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getName() {
        return name;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public String getStartedAt() {
        return startedAt;
    }
}
