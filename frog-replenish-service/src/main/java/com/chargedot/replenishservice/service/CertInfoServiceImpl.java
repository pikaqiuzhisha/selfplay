package com.chargedot.replenishservice.service;
import com.chargedot.replenishservice.mapper.CertInfoMapper;
import com.chargedot.replenishservice.model.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author sjt
 */
@Service
public class CertInfoServiceImpl implements CertInfoService {
    @Autowired
    private CertInfoMapper certInfoMapper;

    @Override
    public Page<CardStream> QueryCertInfo(Integer streamType, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return certInfoMapper.QueryCertInfo(streamType);
    }

    @Override
    public Page<CardStream> QueryCertInfoByCertNumber(String certNumber, Integer streamType,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return certInfoMapper.QueryCertInfoByCertNumber(certNumber, streamType);
    }

    @Override
    public Page<ChargeRecord> QueryChargeRecordByCertNumber(String certNumber, Integer pageNumber, int i) {
        PageHelper.startPage(pageNumber,i);
        return certInfoMapper.QueryChargeRecordByCertNumber(certNumber);
    }

    @Override
    public List<DWCert> QueryCert(String certNumber) {
        return certInfoMapper.QueryCert(certNumber);
    }

    @Override
    public ChargeDetail QueryChargeRecordDetailByOrderNumber(String orderNumber) {
        return certInfoMapper.QueryChargeRecordDetailByOrderNumber(orderNumber);
    }


}
