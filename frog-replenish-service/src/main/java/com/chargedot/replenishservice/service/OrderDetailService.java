package com.chargedot.replenishservice.service;

import com.chargedot.replenishservice.model.OrderDeatil;
import com.github.pagehelper.Page;

public interface OrderDetailService {

    OrderDeatil QueryChargeRecordByOrderNumber(String orderNumber);
}
