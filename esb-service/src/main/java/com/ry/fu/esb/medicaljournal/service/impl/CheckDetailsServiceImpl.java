package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.model.request.InspectionReportRequest;
import com.ry.fu.esb.doctorbook.model.response.InspectionReportResponse;
import com.ry.fu.esb.doctorbook.model.response.InspectionReportResponseRecode;
import com.ry.fu.esb.doctorbook.service.IPService;
import com.ry.fu.esb.jpush.mapper.PatientInformationMapper;
import com.ry.fu.esb.jpush.model.CrisisProject;
import com.ry.fu.esb.medicaljournal.model.response.InspectionProjectResponse;
import com.ry.fu.esb.medicaljournal.service.CheckDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckDetailsServiceImpl implements CheckDetailsService {

    private static Logger logger = LoggerFactory.getLogger(CheckDetailsServiceImpl.class);

    @Autowired
    private PatientInformationMapper patientInformationMapper;

    @Autowired
    private IPService ipService;

    /**
     * 危急值检验详情
     *
     * @param lisLableNo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseData CheckDetails(Long lisLableNo) throws SystemException, ESBException {
        logger.info("危急值检验请求参数：{}" + lisLableNo);

        //根据标本号查出所检验的项目明细
        List<CrisisProject> projectList = patientInformationMapper.selectByCriticalId(lisLableNo);

        if (projectList != null && projectList.size() > 0) {
            Long examineRequestId = null;
            for (CrisisProject cpj : projectList) {
                examineRequestId = cpj.getExamineRequestId();
            }
            InspectionProjectResponse inspectionProjectResponse = new InspectionProjectResponse();
            //设置请求查询检验项目信息
            InspectionReportRequest inspectionReportRequest = new InspectionReportRequest();
            inspectionReportRequest.setExamineRequestId(examineRequestId.toString());
            //查询检验项目信息
            ResponseData responseDataTwo = ipService.inspectionReport(inspectionReportRequest);
            InspectionReportResponse inspectionReportResponse = (InspectionReportResponse) responseDataTwo.getData();
            List<InspectionReportResponseRecode> inspectionReportResponseRecodes = inspectionReportResponse.getInspectionReportResponseRecode();
            for (InspectionReportResponseRecode inspectionReportResponseRecode : inspectionReportResponseRecodes) {
                inspectionProjectResponse.setItemSet(inspectionReportResponseRecode.getItemSet());//项目名称
                inspectionProjectResponse.setRequestDoctor(inspectionReportResponseRecode.getRequestDoctor());//申请医生
                inspectionProjectResponse.setRequestDepartment(inspectionReportResponseRecode.getRequestDepartment());//申请科室
                inspectionProjectResponse.setRequestTime(inspectionReportResponseRecode.getRequestTime());//申请时间
                inspectionProjectResponse.setListNo(inspectionReportResponseRecode.getListNo());//标本号
            }
            inspectionProjectResponse.setCrisisProjectList(projectList);
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), inspectionProjectResponse);
        }

        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
    }
}
