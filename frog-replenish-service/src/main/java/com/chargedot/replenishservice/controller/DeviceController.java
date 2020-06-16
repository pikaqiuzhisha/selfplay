package com.chargedot.replenishservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.chargedot.replenishservice.controller.vo.CommonResult;
import com.chargedot.replenishservice.model.DeviceInfo;
import com.chargedot.replenishservice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * @author sjt
 * @Description：
 * @Data：Created in 2019/12/4
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @RequestMapping(value = "/queryDeviceDetail",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> queryDeviceDetail(@RequestBody JSONObject map){
        String deviceNumber = map.getString("deviceNumber");
        try {
         DeviceInfo deviceInfo = deviceService.queryDeviceDetail(deviceNumber);
        if(Objects.isNull(deviceInfo)){
            return new ResponseEntity<>(CommonResult.buildResults(1, "设备号不存在请输入正确的设备号", null), HttpStatus.OK);

        }
            return new ResponseEntity<>(CommonResult.buildResults(0, "设备信息如下", deviceInfo), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(CommonResult.buildResults(1, "系统繁忙请稍后再试", null), HttpStatus.OK);

        }


    }
}
