package com.chargedot.replenishservice.mapper;
import com.chargedot.replenishservice.model.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CertInfoMapper {
    Page<CardStream> QueryCertInfo(@Param("streamType") Integer streamType);

    Page<CardStream> QueryCertInfoByCertNumber(@Param("certNumber") String certNumber, @Param("streamType") Integer streamType);

    Page<ChargeRecord> QueryChargeRecordByCertNumber(@Param("certNumber")String certNumber);
    List<DWCert> QueryCert(@Param("certNumber")String certNumber);
    ChargeDetail QueryChargeRecordDetailByOrderNumber(@Param("orderNumber")String orderNumber);

}
