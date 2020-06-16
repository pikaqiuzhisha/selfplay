package com.chargedot.replenishservice.mapper;

import com.chargedot.replenishservice.model.OrderDeatil;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
@Mapper
public interface OrderDeatilMapper {
    OrderDeatil QueryChargeRecordByOrderNumber(@Param("orderNumber") String orderNumber);
}
