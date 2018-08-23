package com.ry.fu.followup.doctorbook.service;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.model.ReqInfo;

import java.text.ParseException;

/**
 * Created by Jam on 2017/12/25.
 *
 */
public interface FlowInstanceService {

    /**
     * 查询随访报告（日期）
     * @param reqInfo
     * @return
     */
     ResponseData queryReportDate(ReqInfo reqInfo);

    /**
     * 查询随访报告（列表）
     * @param reqInfo
     * @return
     */
     ResponseData queryReportList(ReqInfo reqInfo) throws Exception;

     ResponseData queryFollowDetails(Long patientId) throws ParseException;
}
