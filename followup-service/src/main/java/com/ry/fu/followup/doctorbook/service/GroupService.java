package com.ry.fu.followup.doctorbook.service;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.model.ReqInfo;

/**
 * Created by Jam on 2017/12/7.
 *
 */
public interface GroupService {

    /**
     * 查询项目列表
     * @param reqInfo
     * @return
     */
    public ResponseData queryProjectList(ReqInfo reqInfo);

    /**
     * 查询申请列表
     * @param reqInfo
     * @return
     */
    public ResponseData queryApplicationList(ReqInfo reqInfo);

    /**
     * 审核列表
     * @param reqInfo
     * @return
     */
    public ResponseData queryReviewList(ReqInfo reqInfo);


    /**
     * 新的审核列表
     * @param reqInfo
     * @return
     */
    public ResponseData queryNewReviewList(ReqInfo reqInfo);

    /**
     * 审核
     * @param reqInfo
     * @return
     */
    public ResponseData review(ReqInfo reqInfo);

    /**
     * 项目详情
     * @param reqInfo
     * @return
     */
    public ResponseData queryProjectDetail(ReqInfo reqInfo);

    /**
     * 项目成员
     * @param reqInfo
     * @return
     */
    public ResponseData queryProjectMember(ReqInfo reqInfo);

    /**
     * 提取数字字段
     * @param reqInfo
     * @return
     */
    public ResponseData queryField(ReqInfo reqInfo);

    /**
     * 查询表达式
     * @param reqInfo
     * @return
     */
    public ResponseData queryExpression(ReqInfo reqInfo);

    /**
     * 查询提醒列表
     * @param reqInfo
     * @return
     */
    public ResponseData queryRemindList(ReqInfo reqInfo);

    /**
     * 已经联系
     * @param reqInfo
     * @return
     */
    public ResponseData haveContacted(ReqInfo reqInfo);

    public ResponseData queryDoctoryContactById(ReqInfo reqInfo);

    public ResponseData saveTransferDoctor(ReqInfo reqInfo);
}
