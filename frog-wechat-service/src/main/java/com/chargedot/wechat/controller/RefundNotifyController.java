package com.chargedot.wechat.controller;

import com.chargedot.wechat.service.RefundRecordService;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/refund_notify")
public class RefundNotifyController {

    @Autowired
    private RefundRecordService refundRecordService;

    @RequestMapping(value = "/wx_notify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String parseRefundNotifyResult(@RequestBody String xmlData) {

        try {
            String result = refundRecordService.updateRefundRecord(xmlData);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return WxPayNotifyResponse.fail("通知失败");
        }
    }
}
