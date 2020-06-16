package com.chargedot.replenishservice.model;
import lombok.Data;

/**
 * @author sjt
 */
@Data
public class DeviceInfo {
    private String deviceNumber;
    private Integer provider;
    private Integer applyType;
    private Integer chargeType;
    private Integer deviceStatus;
    private Integer portCnt;
    private String openForBusinessDate;
    private Integer serveYear;
    private String longitude;
    private String latitude;
    private String addressLocation;
    private String address;
    private String simNumber;
    private String simExpiredAt;
    private Integer dDeviceStatus;
    private String netInfo;

}
