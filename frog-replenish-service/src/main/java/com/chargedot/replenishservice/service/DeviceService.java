package com.chargedot.replenishservice.service;

import com.chargedot.replenishservice.model.DeviceInfo;

/**
 * @author sjt
 */
public interface DeviceService {

    DeviceInfo queryDeviceDetail(String deviceNumber);
}
