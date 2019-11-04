package com.chargedot.wxrefund.controller;


import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refund")
public class WXRefundController {

    private BestPayServiceImpl bestPayService;
    @GetMapping("/refund")
    @ResponseBody
    public RefundResponse refund(@RequestParam String orderId) {
        RefundRequest request = new RefundRequest();
        request.setOrderId(orderId);
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_MWEB);
        request.setOrderAmount(0.1);
        RefundResponse response = bestPayService.refund(request);
        return response;
    }


}
