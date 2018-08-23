package com.ry.fu.net.common.exception;

import lombok.Data;

/**
 * @Author: telly
 * @Description: 此处只存取ESB的异常
 * @Date: Create in 15:47 2018/1/15
 */
@Data
public class ESBException extends Exception {

    public ESBException() {}

    public ESBException(String status, String msg) {
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
