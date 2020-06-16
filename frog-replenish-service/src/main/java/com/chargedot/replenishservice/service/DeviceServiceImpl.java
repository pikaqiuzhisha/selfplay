package com.chargedot.replenishservice.service;

import com.chargedot.replenishservice.mapper.DeviceMapper;
import com.chargedot.replenishservice.model.DeviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sjt
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;
    @Override
    public DeviceInfo queryDeviceDetail(String deviceNumber) {
        return deviceMapper.queryDeviceDetail(deviceNumber);
    }
}
