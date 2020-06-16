package com.chargedot.replenishservice.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chargedot.replenishservice.controller.vo.CommonResult;
import com.chargedot.replenishservice.model.*;
import com.chargedot.replenishservice.model.vo.DataParam;
import com.chargedot.replenishservice.service.CertInfoService;
import com.chargedot.replenishservice.util.JacksonUtil;
import com.chargedot.replenishservice.util.PageInfo;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

/**
 * @author sjt
 */
@Slf4j
@RestController
@RequestMapping("/certInfo")
public class CertInfoController {

    @Autowired
    private CertInfoService certInfoService;
    /**
     * 查询所有用户信息
     *
     * @return
     */
    @RequestMapping(value = "/queryCertInfo",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> QueryCertInfo(@RequestBody JSONObject map) {
        try {
            if (Objects.nonNull(map.getIntValue("streamType"))) {
                Integer stream = map.getIntValue("streamType");
                Page<CardStream> page = null;

                if (stream == 1 || stream == 3) {
                    // 消费
                    page = certInfoService.QueryCertInfo(1,map.getIntValue("pageNumber"),10);
                    PageInfo<CardStream> pageInfo = new PageInfo<>(page);
                    return new ResponseEntity<CommonResult>(CommonResult.buildResults(0, "消费记录如下所示", pageInfo), HttpStatus.OK);
                } else if (stream == 2) {
                    // 充值
                    page = certInfoService.QueryCertInfo(2,map.getIntValue("pageNumber"),10);
                    PageInfo<CardStream> pageInfo = new PageInfo<>(page);
                    return new ResponseEntity<CommonResult>(CommonResult.buildResults(0, "充值记录如下所示", pageInfo), HttpStatus.OK);
                } else {
                    return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "消费类型错误,请输入正确的类型", null), HttpStatus.OK);
                }
            }
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "请输入您想查询的消费记录", null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "系统繁忙请稍后再试",null), HttpStatus.OK);
        }
    }

    /**
     * 根据certNumber查询用户信息
     *
     * @param
     * @return
     */
    @RequestMapping(value ="/queryCertInfoByCertNumber",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> QueryCertInfoByCertNumber(@RequestBody JSONObject map) {

        try {
            Page<CardStream> page = null;
            if (Objects.nonNull(map.getIntValue("streamType"))) {
                Integer stream = map.getIntValue("streamType");
                if (stream == 1 || stream == 3) {
                    // 消费
                    page = certInfoService.QueryCertInfoByCertNumber(map.getString("certNumber"), 1,map.getIntValue("pageNumber"),10);
                    PageInfo<CardStream> pageInfo = new PageInfo<>(page);
                    return new ResponseEntity<CommonResult>(CommonResult.buildResults(0, "消费记录如下所示", pageInfo), HttpStatus.OK);
                } else if (stream == 2) {
                    // 充值
                    page = certInfoService.QueryCertInfoByCertNumber(map.getString("certNumber"), 2,map.getIntValue("pageNumber"),10);
                    PageInfo<CardStream> pageInfo = new PageInfo<>(page);
                    return new ResponseEntity<CommonResult>(CommonResult.buildResults(0, "充值记录如下所示", pageInfo), HttpStatus.OK);
                } else {
                    return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "消费类型错误,请输入正确的类型", null), HttpStatus.OK);
                }
            }
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "请输入您想查询的消费类型", null), HttpStatus.OK);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "系统繁忙请稍后再试", null), HttpStatus.OK);
        }
    }

    /**
     * 根据certNumber查询用户充电记录
     *
     * @param
     * @return
     */
    @RequestMapping(value ="/queryChargeRecordByCertNumber",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> QueryChargeRecordByCertNumber(@RequestBody JSONObject map) {
//       获取参数
        String certNumber =  map.getString("certNumber");
        int pageNumber =  map.getIntValue("pageNumber");
        int pageSize =  map.getIntValue("pageSize");
     List<DWCert> certs = certInfoService.QueryCert(certNumber);
        if(0==certs.size()){
            return new ResponseEntity<>(CommonResult.buildResults(1, "卡号不正确,请输入正确的卡号", null), HttpStatus.OK);
        }
        try {
            Page<ChargeRecord> page;
            page = certInfoService.QueryChargeRecordByCertNumber(certNumber, pageNumber,pageSize);
            PageInfo<ChargeRecord> pageInfo = new PageInfo<>(page);
            if(pageInfo.getList()== null || 0 ==pageInfo.getList().size()){
                return new ResponseEntity<>(CommonResult.buildResults(0, "您还没有充电记录", null), HttpStatus.OK);
            }
            return new ResponseEntity<>(CommonResult.buildResults(0, "充电记录如下所示", pageInfo), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(CommonResult.buildResults(1, "系统繁忙请您稍后再试", null), HttpStatus.OK);

        }
    }

    /**
     * 查询充电记录详情
     * @param map
     * @return
     */
    @RequestMapping(value ="/queryChargeRecordDetailByOrderNumber",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> QueryChargeRecordDetailByOrderNumber(@RequestBody JSONObject map) {

        String orderNumber =  map.getString("orderNumber");
        try {
//            根据订单号查询充电记录详情
            ChargeDetail chargeDetail = certInfoService.QueryChargeRecordDetailByOrderNumber(orderNumber);

            return new ResponseEntity<CommonResult>(CommonResult.buildResults(0, "充电详情如下所示", chargeDetail), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "系统繁忙请您稍后再试", null), HttpStatus.OK);
        }

    }


}
