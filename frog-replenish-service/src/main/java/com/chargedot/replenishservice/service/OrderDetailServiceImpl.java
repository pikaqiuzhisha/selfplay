package com.chargedot.replenishservice.service;

import com.chargedot.replenishservice.mapper.OrderDeatilMapper;
import com.chargedot.replenishservice.model.OrderDeatil;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDeatilMapper orderDeatilMapper;
    @Override
    public OrderDeatil QueryChargeRecordByOrderNumber(String orderNumber) {
        return orderDeatilMapper.QueryChargeRecordByOrderNumber(orderNumber);
    }
}
