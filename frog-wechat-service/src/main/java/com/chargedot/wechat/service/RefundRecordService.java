package com.chargedot.wechat.service;

import com.github.binarywang.wxpay.exception.WxPayException;

public interface RefundRecordService {

    String updateRefundRecord(String xmlData) throws WxPayException;

}
