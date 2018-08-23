package com.ry.fu.esb.common.utils;

import com.ry.fu.esb.common.response.ResponseData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jam on 2017/12/8.
 *
 */
public class ResponseUtil {

    private ResponseUtil(){}


    /**
     * 组装成功的Map
     * @param innerData
     * @return
     */
    public static ResponseData getSuccessResultMap(Map<String, Object> innerData) {
        return getSuccessResultBean(innerData);
    }

    public static ResponseData getSuccessResultMap(List list) {
        return getSuccessResultBean(new HashMap<String,Object>(2){{put("list",list);}});
    }

    public static ResponseData getFailResultMapMap() {
        return getFailResultBean(null);
    }

    public static ResponseData getSuccessResultBean(Object data) {
        return new ResponseData("200","操作成功",data);
    }

    public static ResponseData getFailResultBean(Object data) {
        return new ResponseData("500","操作失败",data);
    }
}