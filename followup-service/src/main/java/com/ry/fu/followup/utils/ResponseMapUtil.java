package com.ry.fu.followup.utils;

import com.ry.fu.followup.base.model.ResponseData;

import java.util.List;
import java.util.Map;

/**
 * Created by Jam on 2017/12/8.
 *
 */
public class ResponseMapUtil {

    /**
     * 组装成功的Map
     *
     * @param innerData
     * @return
     */
    public static ResponseData getSuccessResultMap(Map<String, Object> innerData) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        responseData.setMsg("操作成功");
        responseData.setData(innerData);
        return responseData;
    }

    public static ResponseData getSuccessResultList(List<Map<String, Object>> innerDataList) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        responseData.setMsg("操作成功");
        responseData.setData(innerDataList);
        return responseData;
    }

    public static ResponseData getFailResultMapMap() {
        ResponseData responseData = new ResponseData();
        responseData.setStatus(500);
        responseData.setMsg("操作失败");
        responseData.setData(null);
        return responseData;
    }
}