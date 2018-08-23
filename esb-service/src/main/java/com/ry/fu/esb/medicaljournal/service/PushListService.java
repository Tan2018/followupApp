package com.ry.fu.esb.medicaljournal.service;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/04/24 16:03
 * @description 通知列表service
 */
public interface PushListService {

    public String selectByPrimaryKey(String openId, Integer pageNum, Integer pageSize);
}
