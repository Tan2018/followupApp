package com.ry.fu.esb.doctorbook.service;

import com.ry.fu.esb.common.response.ResponseData;

import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/5/7 16:16
 **/
public interface HandInformService {

    /**
     * 查询指定医生的推送信息
     * @param map 医生id
     * @return 结果集
     */
   // ResponseData findHandInformInfo(Map<String,Object> map);
    ResponseData findHandInformInfo(Map<String,String> map);
}
