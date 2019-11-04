package com.chargedot.wxrefund.service;

import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import org.springframework.stereotype.Service;

@Service
public interface WXRefundService extends BestPayService {
    /**
     * 退款
     * @param request
     * @return
     */
    RefundResponse refund(RefundRequest request);
}
