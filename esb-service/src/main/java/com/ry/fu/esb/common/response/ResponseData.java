package com.ry.fu.esb.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/2 15:25
 * @description 返回数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseData<T> extends BaseModel{
    private static final long serialVersionUID = 2021786799777726720L;

    public ResponseData(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResponseData() {

    }

    /**
     * Code代码
     */
    private String status;

    /**
     * 消息：正常，异常等等
     */
    private String msg;

    /**
     * 数据详情，可能是对象，也可能是数组
     */
    private T data;

}
