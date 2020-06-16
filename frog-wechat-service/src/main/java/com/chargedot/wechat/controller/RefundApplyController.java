package com.chargedot.wechat.controller;

import com.chargedot.wechat.config.ConstantConfig;
import com.chargedot.wechat.config.WechatAccountConfig;
import com.chargedot.wechat.controller.vo.CommonResult;
import com.chargedot.wechat.mapper.ChargeOrderMapper;
import com.chargedot.wechat.mapper.RefundRecordMapper;
import com.chargedot.wechat.model.ChargeOrder;
import com.chargedot.wechat.model.RefundRecord;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/refund_apply")
public class RefundApplyController {
    @Autowired
    private RefundRecordMapper refundRecordMapper;

    @Autowired
    private ChargeOrderMapper chargeOrderMapper;

    @Autowired
    private WxPayService payService;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;


    @RequestMapping(value = "/wx_apply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> refund(@RequestParam("refundNumber") String refundNumber) {
        // 校验参数
        if (StringUtils.isBlank(refundNumber)) {
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "参数错误.", null), HttpStatus.OK);
        }

        // 异常处理
        try {
            // 根据退款单号得到退款单信息
            RefundRecord refundRecord = refundRecordMapper.getRefundRecordByRefundNumber(refundNumber);
            // 校验是否存在该退款单号
            if (Objects.isNull(refundRecord)) {
                return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "不存在该退款单号.", null), HttpStatus.OK);
            }
            // 根据订单id得到最近一条要退款的订单信息
            ChargeOrder chargeOrder = chargeOrderMapper.findByOrderID(refundRecord.getOrderId());
            // 校验订单是否为空
            if (Objects.isNull(chargeOrder)) {
                return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "不存在该订单信息.", null), HttpStatus.OK);
            }
            // 退款验证
            if (refundRecord.getRefundStatus() != ConstantConfig.UNREFUND) {
                return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "该订单不进行退款.", null), HttpStatus.OK);
            }

            // 开始退款
            // 构造退款请求数据
            // 调微信申请退款接口
            WxPayRefundRequest request = WxPayRefundRequest.newBuilder()
                    .outRefundNo(refundRecord.getRefundNumber())
                    .outTradeNo(chargeOrder.getOrderNumber())
                    .totalFee(chargeOrder.getPayment())
                    .refundFee(refundRecord.getRefundMoney())
                    .notifyUrl(wechatAccountConfig.getNotifyUrl())
                    .build();
            WxPayRefundResult result = payService.refund(request);

            // 校验
            if (!"SUCCESS".equals(result.getReturnCode())) {
                String returnMsg = result.getReturnMsg();
                if (Strings.isEmpty(returnMsg)){
                    returnMsg = "微信退款申请业务提交失败";}
                return new ResponseEntity<CommonResult>(
                        CommonResult.buildResults(1, returnMsg, result), HttpStatus.OK);
            }
            if (!"SUCCESS".equals(result.getResultCode())) {
                String errorMsg = result.getErrCodeDes() + "[error_code-" + result.getErrCode()
                        + "]";
                if (Strings.isEmpty(errorMsg)){
                    errorMsg = "参数异常";}
                return new ResponseEntity<CommonResult>(
                        CommonResult.buildResults(1, errorMsg, result), HttpStatus.OK);
            }

            // 更新订单表数据库订单状态
            chargeOrder.setPayStatus(ConstantConfig.REFUNDING);
            chargeOrderMapper.updateChargeOrder(chargeOrder);

            // 更新退款记录表数据库订单状态
            refundRecord.setRefundStatus(ConstantConfig.REFUNDING);
            refundRecordMapper.updateRefund(refundRecord);
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(0, "进入退款中状态", result), HttpStatus.OK);
        } catch (Exception ex) {
            log.info("[Exception]：{}", ex.getMessage());
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "异常信息" + ex.getMessage(), null), HttpStatus.OK);
        }
    }
}
