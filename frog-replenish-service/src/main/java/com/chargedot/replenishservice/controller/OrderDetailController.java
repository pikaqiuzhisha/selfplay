package com.chargedot.replenishservice.controller;
import com.chargedot.replenishservice.controller.vo.CommonResult;
import com.chargedot.replenishservice.model.OrderDeatil;
import com.chargedot.replenishservice.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;
    @RequestMapping(value ="/queryChargeRecordByOrderNumber",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> QueryChargeRecordByOrderNumber(@RequestBody Map<String, String> data) {
     String orderNumber=  data.get("orderNumber");
        try {
         OrderDeatil orderDeatil = orderDetailService.QueryChargeRecordByOrderNumber(orderNumber);
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(0, "订单详情如下", orderDeatil), HttpStatus.OK);
        } catch (Exception e) {
            log.info("系统繁忙,请稍后再试"+e);
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "系统繁忙,请稍后再试", null), HttpStatus.OK);
        }
    }

}
