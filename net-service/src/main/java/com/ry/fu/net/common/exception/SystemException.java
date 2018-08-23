package com.ry.fu.net.common.exception;

import lombok.Data;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/13 11:39
 * @description 系统的异常
 */
@Data
public class SystemException extends Exception {

    public SystemException() {}

    public SystemException(String status, String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;
    }

    /**
     * Code代码
     */
    private String status;

    /**
     * 消息：正常，异常等等
     */
    private String msg;
}
