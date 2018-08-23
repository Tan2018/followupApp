package com.ry.fu.followup.doctorbook.controller;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import com.ry.fu.followup.doctorbook.service.FlowInstanceService;
import com.ry.fu.followup.doctorbook.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by Jam on 2017/11/30.
 *
 */
@RestController
@RequestMapping("${prefixPath}/v1/followup")
public class FollowUpController {
    private Logger logger = LoggerFactory.getLogger(FollowUpController.class);
    @Autowired
    private GroupService groupService;

    @Autowired
    private FlowInstanceService flowInstanceService;


    @RequestMapping(value = "/projectList", method = RequestMethod.POST)
    public ResponseData projectList(@RequestBody ReqInfo reqInfo) {
        return groupService.queryProjectList(reqInfo);
    }

    @RequestMapping(value = "/myApplicationList", method = RequestMethod.POST)
    public ResponseData myApplicationList(@RequestBody ReqInfo reqInfo) {
       return groupService.queryApplicationList(reqInfo);
    }

    @RequestMapping(value = "/needMeReview", method = RequestMethod.POST)
    public ResponseData needMeReview(@RequestBody ReqInfo reqInfo) {
        return groupService.queryNewReviewList(reqInfo);
    }

    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public ResponseData review(@RequestBody ReqInfo reqInfo) {
        return groupService.review(reqInfo);
    }

    @RequestMapping(value = "/remindList", method = RequestMethod.POST)
    public ResponseData remindList(@RequestBody ReqInfo reqInfo) {
        return groupService.queryRemindList(reqInfo);
    }

    @RequestMapping(value = "/haveContacted", method = RequestMethod.POST)
    public ResponseData haveContacted(@RequestBody ReqInfo reqInfo) {
        return groupService.haveContacted(reqInfo);
    }

    @RequestMapping(value = "/followReportDate", method = RequestMethod.POST)
    public ResponseData followReportDate(@RequestBody ReqInfo reqInfo) {
        return flowInstanceService.queryReportDate(reqInfo);
    }

    @RequestMapping(value = "/followReportList", method = RequestMethod.POST)
    public ResponseData followReportList(@RequestBody ReqInfo reqInfo) throws Exception {
        return flowInstanceService.queryReportList(reqInfo);
    }

    @RequestMapping(value = "/showFollowDetails", method = RequestMethod.POST)
    public ResponseData showFollowDetails(@RequestBody ReqInfo reqInfo) {

        try {
            return  flowInstanceService.queryFollowDetails(reqInfo.getPatientId());
        } catch (ParseException e) {
            e.printStackTrace();
            ResponseData responseData = new ResponseData();
            responseData.setMsg("后台异常");
            responseData.setStatus(500);
            responseData.setData(null);
            return  responseData;
        }

    }
    @RequestMapping(value = "/saveTransferDoctor", method = RequestMethod.POST)
    public ResponseData saveTransferDoctor(@RequestBody ReqInfo reqInfo){

        ResponseData responseData = groupService.saveTransferDoctor(reqInfo);

        return responseData;
    }



}
