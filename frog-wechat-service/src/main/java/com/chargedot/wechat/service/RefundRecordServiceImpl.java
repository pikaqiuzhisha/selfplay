package com.chargedot.wechat.service;

import com.chargedot.wechat.config.ConstantConfig;
import com.chargedot.wechat.mapper.ChargeOrderMapper;
import com.chargedot.wechat.mapper.RefundRecordMapper;
import com.chargedot.wechat.model.ChargeOrder;
import com.chargedot.wechat.model.RefundRecord;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional
@Slf4j
@Service
public class RefundRecordServiceImpl implements RefundRecordService {

    @Autowired
    private RefundRecordMapper refundRecordMapper;

    @Autowired
    private ChargeOrderMapper chargeOrderMapper;

    @Autowired
    private WxPayService payService;

    @Override
    public String updateRefundRecord(String xmlData) throws WxPayException {
        if (StringUtils.isBlank(xmlData)) {
            log.warn("参数非法");
            return WxPayNotifyResponse.fail("参数非法");
        }
        log.info("微信支付退款异步通知参数：{}", xmlData);
        WxPayRefundNotifyResult result = WxPayRefundNotifyResult.fromXML(xmlData, payService.getConfig().getMchKey());
        log.info("[result][{}]", result);

        // 查询退款记录表
        RefundRecord refundRecord = refundRecordMapper.getRefundRecordByRefundNumber(result.getReqInfo().getOutRefundNo());

        // 校验
        if (Objects.isNull(refundRecord)) {
            return WxPayNotifyResponse.fail("该退款单不存在");
        }
        // 是否是重复退款通知
        if (ConstantConfig.REFUND == refundRecord.getRefundStatus()) {
            return WxPayNotifyResponse.success("该订单已退款,无需再退款通知");
        }
        refundRecord.setRefundStatus(ConstantConfig.REFUND);
        refundRecord.setRefundAt(result.getReqInfo().getSuccessTime());
        refundRecordMapper.updateRefund(refundRecord);

        // 查找最近一条订单信息
        ChargeOrder chargeOrder = chargeOrderMapper.findByOrderNumber(result.getReqInfo().getOutTradeNo());
        // 校验
        if (Objects.isNull(chargeOrder)) {
            return WxPayNotifyResponse.success("该订单不存在");
        }

        // 进行判断
        List<RefundRecord> refundRecords = refundRecordMapper.getRefundRecordByOrderId(chargeOrder.getId());
        if (refundRecords.size() != 0) {
            return WxPayNotifyResponse.success("该订单下的退款单还有没有进行退款完成的。");
        }
        // 更改订单表订单状态
        chargeOrder.setPayStatus(ConstantConfig.REFUND);
        chargeOrderMapper.updateChargeOrder(chargeOrder);
        return WxPayNotifyResponse.success("退款成功");
    }

}
