package com.chargedot.replenishservice.service;
import com.chargedot.replenishservice.model.*;
import com.github.pagehelper.Page;

import java.util.List;

public interface SupplymentCardService {

    User UserAuth(String userId);

    DWCert SupplymentCardBangdadQuery(String certNumber);


    int SupplymentCardBangdaged(String certNumber,int userId);

    int SupplymentCardDroped(String certNumber);


    int SelectSupplyCards(int userId, String certNumber);


    List<DisplayInfo> DisplayCertInfo(int userId);

    Page<ChargeRecord> displayChargeRecord(int userId, int pageNumber, int pageSize);
    List<ChargeOrder> queryChargeOrderDetailBySESSIONID(int userId);

    void updateTypeToSup(String certNumber);
}
