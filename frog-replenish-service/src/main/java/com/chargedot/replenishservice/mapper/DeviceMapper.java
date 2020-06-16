package com.chargedot.replenishservice.mapper;

import com.chargedot.replenishservice.model.DeviceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author sjt
 */
@Mapper
public interface DeviceMapper {
    DeviceInfo queryDeviceDetail(@Param("deviceNumber") String deviceNumber);
}
