package com.chargedot.replenishservice.mapper;
import com.chargedot.replenishservice.model.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SupplementMapper {
    User UserAuth(@Param("userId")String userId);

    DWCert SupplymentCardBangdadQuery(@Param("certNumber") String certNumber);

    int SupplymentCardBangdaged(@Param("userId")int userId,@Param("certNumber")String certNumber);

    int SupplymentCardDroped(@Param("certNumber")String certNumber);
    int SelectSupplyCards(@Param("userId")int userId,@Param("certNumber")String certNumber);
    List<DisplayInfo> DisplayCertInfo(@Param("userId")int userId);
    Page<ChargeRecord> displayChargeRecord(@Param("userId")int userId);
    List<ChargeOrder> queryChargeOrderDetailBySESSIONID(@Param("userId")int userId);
    void updateTypeToSup(@Param("certNumber")String certNumber);
}
