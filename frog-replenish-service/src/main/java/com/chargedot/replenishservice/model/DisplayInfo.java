package com.chargedot.replenishservice.model;

import lombok.Data;

@Data
public class DisplayInfo {
    private Integer userid;
    private String city;
    private String phone;
    private Integer realValue;
    private Integer curValue;
    private String certNumber;
    private Integer certStatus;
    private String bindCertNumber;
    private Integer bindCertStatus;
}
