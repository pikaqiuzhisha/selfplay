package com.chargedot.replenishservice.service;

import com.chargedot.replenishservice.mapper.SupplementMapper;
import com.chargedot.replenishservice.model.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplymentCardServiceImpl implements SupplymentCardService {

    @Autowired
    private SupplementMapper supplementMapper;


    @Override
    public User UserAuth(String userId) {
        return supplementMapper.UserAuth(userId);
    }

    @Override
    public DWCert SupplymentCardBangdadQuery(String certNumber) {
        return supplementMapper.SupplymentCardBangdadQuery(certNumber);
    }

    @Override
    public int SupplymentCardBangdaged(String certNumber, int userId) {
        return supplementMapper.SupplymentCardBangdaged(userId, certNumber);
    }

    @Override
    public int SupplymentCardDroped(String certNumber) {
        return supplementMapper.SupplymentCardDroped(certNumber);
    }

    @Override
    public int SelectSupplyCards(int userId, String certNumber) {
        return supplementMapper.SelectSupplyCards(userId,certNumber);
    }

    @Override
    public List<DisplayInfo> DisplayCertInfo(int userId) {
        return supplementMapper.DisplayCertInfo(userId);
    }

    @Override
    public Page<ChargeRecord> displayChargeRecord(int userId, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        return supplementMapper.displayChargeRecord(userId);
    }

    @Override
    public List<ChargeOrder> queryChargeOrderDetailBySESSIONID(int userId) {
        return supplementMapper.queryChargeOrderDetailBySESSIONID(userId);
    }

    @Override
    public void updateTypeToSup(String certNumber) {
        supplementMapper.updateTypeToSup(certNumber);
    }


}
