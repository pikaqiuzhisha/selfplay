package com.chargedot.wxrefund.controller;


import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
@Slf4j
@RestController
@RequestMapping("/refund")
public class WXRefundController {

    private BestPayServiceImpl bestPayService;
    @GetMapping("/refund")
    @ResponseBody
    public RefundResponse refund(@RequestParam(required = true) String orderId) {
        RefundRequest request = new RefundRequest();
        request.setOrderId(orderId);
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_MWEB);
        request.setOrderAmount(0.1);
        RefundResponse response = bestPayService.refund(request);
        return response;
    }

    /**
     * 异步回调
     */
    @PostMapping(value = "/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        log.info("【异步通知】支付平台的数据request={}", notifyData);
        PayResponse response = bestPayService.asyncNotify(notifyData);
        log.info("【异步通知】处理后的数据data={}", JsonUtil.toJson(response));
        //返回成功信息给支付平台，否则会不停的异步通知
        if (response.getPayPlatformEnum() == BestPayPlatformEnum.WX) {
            return new ModelAndView("pay/responeSuccessForWx");
        }else if (response.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY) {
            return new ModelAndView("pay/responeSuccessForAlipay");
        }
        throw new RuntimeException("错误的支付平台");
    }
}
