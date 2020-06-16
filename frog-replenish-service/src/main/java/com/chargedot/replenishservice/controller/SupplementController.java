package com.chargedot.replenishservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.chargedot.replenishservice.controller.vo.CommonResult;
import com.chargedot.replenishservice.model.*;
import com.chargedot.replenishservice.service.SupplymentCardService;
import com.chargedot.replenishservice.util.PageInfo;
import com.chargedot.replenishservice.util.Unserialize;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/supplymentOperate")
public class SupplementController {
    @Autowired
    private SupplymentCardService supplymentCardService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 绑卡
     * 请求参数certNumber
     *
     * @param data
     * @return
     */

    @RequestMapping(value = "/supplymentCardBangdaged", method = RequestMethod.POST)
    public ResponseEntity<CommonResult> SupplymentCardBangdaged(
            @RequestHeader(value="SESSIONID", required = true) String sessionId,
            @RequestBody Map<String, String> data) {
        try {
//            从sessionid中获取userId

            int userId = getSessionUserId(sessionId);
            String certNumber = data.get("certNumber");
//            根据卡号查询出卡
            DWCert dwCert = supplymentCardService.SupplymentCardBangdadQuery(certNumber);
//            将余额<=0的充值卡变成附属卡
            if(3 == dwCert.getType() && dwCert.getCurValue()<=0 && dwCert.getRealValue()<=0 ){
                supplymentCardService.updateTypeToSup(certNumber);
            }
            if (!isCertAvaiable(certNumber, userId)) {
                return new ResponseEntity<>( CommonResult.buildResults(1, "卡已绑定.", null), HttpStatus.OK);
            }

            int result = supplymentCardService.SupplymentCardBangdaged(certNumber, userId);
            if (result==0) {
                return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "绑卡失败,请重新绑卡", null), HttpStatus.OK);
            }
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(0, "恭喜您绑卡成功", null), HttpStatus.OK);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (Strings.isBlank(msg)) {
                msg = "系统繁忙请稍后再试";
            }
            return new ResponseEntity<CommonResult>(
                    CommonResult.buildResults(1, msg, null), HttpStatus.OK);
        }
    }
    /**
     * 挂失卡 请求参数certNumber
     *
     * @param data
     * @return
     */
    @RequestMapping(value = "/supplymentCardDroped", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> SupplymentCardDroped( @RequestHeader(value="SESSIONID", required = true) String sessionId,
//            @CookieValue(value = "SESSIONID", required = true) String sessionId,
                                                              @RequestBody Map<String, String> data) {

        try {
            int userId = getSessionUserId(sessionId);
            String certNumber = data.get("certNumber");
            DWCert dwCert1 = supplymentCardService.SupplymentCardBangdadQuery(certNumber);
            if (3 == dwCert1.getCertStatus()) {
                return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "卡已挂失,请勿重复挂失", null), HttpStatus.OK);
            }
            supplymentCardService.SupplymentCardDroped(certNumber);
            DWCert dwCert = supplymentCardService.SupplymentCardBangdadQuery(certNumber);
            if (3 == dwCert.getCertStatus()) {
                return new ResponseEntity<CommonResult>(CommonResult.buildResults(0, "挂失成功", null), HttpStatus.OK);
            }
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "挂失失败,请重新挂失", null), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "系统繁忙,请稍后再试", null), HttpStatus.OK);

        }
    }
    /**
     * 根据用户登录的SESSIONID来回显用户信息
     *
     * @param
     * @return
     */
    @RequestMapping(value ="/displayCertInfo",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> DisplayCertInfo(@RequestHeader(value="SESSIONID", required = true) String sessionId
//            @CookieValue(value = "SESSIONID", required = true) String sessionId,
                                                       ){
//
        try {
            int userId = getSessionUserId(sessionId);
        List<DisplayInfo> displayInfos = supplymentCardService.DisplayCertInfo(userId);
            DisplayInfo di = null;
            if (displayInfos!=null&&displayInfos.size()>0) {
                di = displayInfos.get(0);
            }

            return new ResponseEntity<>(CommonResult.buildResults(0, "结果如下所示", di), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(CommonResult.buildResults(1, "系统繁忙,请稍后再试", null), HttpStatus.OK);
        }

    }

    /**
     * 根据用户登录的SESSIONID来查询用户充电记录
     *
     * @param
     * @return
     */
    @RequestMapping(value ="/displayChargeRecord",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> displayChargeRecord(@RequestHeader(value="SESSIONID", required = true) String sessionId,
                                                            @RequestBody JSONObject map
//            @CookieValue(value = "SESSIONID", required = true) String sessionId,
    ){
        try {
            int userId = getSessionUserId(sessionId);
            int pageNumber =  map.getIntValue("pageNumber");
            int pageSize =  map.getIntValue("pageSize");

            Page<ChargeRecord> chargeRecords = supplymentCardService.displayChargeRecord(userId,pageNumber,pageSize);
            PageInfo<ChargeRecord> pageInfo = new PageInfo<>(chargeRecords);
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(0, "结果如下所示", pageInfo), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CommonResult>(CommonResult.buildResults(1, "系统繁忙,请稍后再试", null), HttpStatus.OK);
        }

    }
    /**
     * 根据用户登录的SESSIONID查询是否有正在充电的订单
     * @param
     * @return
     */

    @RequestMapping(value ="/queryChargeOrderDetailBySESSIONID",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResult> queryChargeOrderDetailBySESSIONID(@RequestHeader(value="SESSIONID", required = true) String sessionId) {
        try {
            int userId = getSessionUserId(sessionId);
            List<ChargeOrder> chargeOrders = supplymentCardService.queryChargeOrderDetailBySESSIONID(userId);
            if(chargeOrders.size()!=0){
                for (int i = 0; i < chargeOrders.size(); i++) {
                    if(chargeOrders.get(i).getTryOccupyUserId()!=0){
                        return new ResponseEntity<>(CommonResult.buildResults(0, "您有订单正在进行中,详情如下", chargeOrders), HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>(CommonResult.buildResults(0, "请充电", null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(CommonResult.buildResults(1, "系统繁忙请您稍后再试", null), HttpStatus.OK);
        }
    }






    public boolean isCertAvaiable(String certNumber, int userId) {
        if (Strings.isBlank(certNumber)) {
            throw new RuntimeException("参数certNumber缺失");
        }
        //        查询用户是否已经绑定了其他附属卡
        int num = supplymentCardService.SelectSupplyCards(userId, certNumber);
        if (num >= 1) {
            throw new RuntimeException("您已绑定其他附属卡");
        }

        DWCert dwCert = supplymentCardService.SupplymentCardBangdadQuery(certNumber);
        if (Objects.isNull(dwCert)) {
            throw new RuntimeException("此卡[" + certNumber + "]不存在");
        }

        if (5 != dwCert.getType()) {
            throw new RuntimeException("此卡[" + certNumber + "]非附属卡,详情请联系发卡方");
        }

        if (3 == dwCert.getCertStatus()) {
            throw new RuntimeException("此卡[" + certNumber + "]已冻结不可绑定,详情请联系发卡方");
        }
        //已绑定,直接返回
        if (userId == dwCert.getUserId()) {
            return false;
        }
        if (0 != dwCert.getUserId()) {
            throw new RuntimeException("此卡[" + certNumber + "]已绑定其他用户,详情请联系发卡方");
        }
        return true;
    }

    private int getSessionUserId(String sessionId) {
        //            根据SESSIONID获取到redis中的php的对象并强转成string类型
        String u = (String) stringRedisTemplate.opsForValue().get("laravel:FROG_WEIXIN_LOGIN_INFO" + sessionId);
        if (StringUtils.isBlank(u)) {
            throw new RuntimeException("用户会话[" + sessionId + "]不存在");
        }

        // 把PHP对象转换为Java的map
        Map om = Unserialize.unserializePHP(u);
//            将map转化为string
        String userId = om.get("userId").toString();
//            切割字符串并获取userId
        if (Objects.isNull(userId)) {
            throw new RuntimeException("用户未登录,请重新登录");
        }
        User user = supplymentCardService.UserAuth(userId);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户[" + userId + "]不存在,请重新登录");
        }
        if (1 == user.getStatus()) {
            throw new RuntimeException("用户[" + userId + "]状态异常");
        }
        if (1 != user.getType() && 2 != user.getType()) {
            throw new RuntimeException("用户[" + userId + "]类型错误");
        }
        return user.getId();
    }


}
