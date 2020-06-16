package com.chargedot.replenishservice.service;

import com.chargedot.replenishservice.model.*;

import com.github.pagehelper.Page;

import java.util.List;


public interface CertInfoService {

    Page<CardStream> QueryCertInfo(Integer streamType, Integer pageNum, Integer pageSize);

    Page<CardStream> QueryCertInfoByCertNumber(String certNumber, Integer streamType,Integer pageNum, Integer pageSize);


    Page<ChargeRecord> QueryChargeRecordByCertNumber(String certNumber, Integer pageNumber, int i);

    List<DWCert> QueryCert(String certNumber);

    ChargeDetail QueryChargeRecordDetailByOrderNumber(String orderNumber);


}
