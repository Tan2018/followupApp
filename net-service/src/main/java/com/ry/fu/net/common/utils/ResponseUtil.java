package com.ry.fu.net.common.utils;

import com.ry.fu.net.common.response.ResponseData;

import java.util.Map;

/**
 * Created by Jam on 2017/12/8.
 *
 */
public class ResponseUtil {

    private ResponseUtil(){}

    public static ResponseData getFaildResult(String status, String msg, Object obj) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus(status);
        responseData.setStatus(msg);
        responseData.setData(obj);
        return responseData;
    }

    /**
     * 组装成功的Map
     *
     * @param innerData
     * @return
     */
    public static ResponseData getSuccessResultMap(Map<String, Object> innerData) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus("200");
        responseData.setMsg("操作成功");
        responseData.setData(innerData);
        return responseData;
    }

    public static ResponseData getFailResultMapMap() {
        ResponseData responseData = new ResponseData();
        responseData.setStatus("500");
        responseData.setMsg("操作失败");
        responseData.setData(null);
        return responseData;
    }

    public static ResponseData getSuccessResultBean(Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus("200");
        responseData.setMsg("操作成功");
        responseData.setData(data);
        return responseData;
    }

    public static ResponseData getFailResultBean(Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setStatus("500");
        responseData.setMsg("操作失败");
        responseData.setData(null);
        return responseData;
    }
}