package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.service.CheckDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/checkDetails")
public class CheckDetailsController {

    private Logger logger = LoggerFactory.getLogger(CheckDetailsController.class);

    @Autowired
    private CheckDetailsService checkDetailsService;

    /**
     * 危急值检验详情
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/CheckProjectDetails", method = RequestMethod.POST)
    public ResponseData CheckProjectDetails(@RequestBody Map<String, String> params) {

        //校验参数
        if (params == null || params.size() == 0) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (params.get("lisLableNo") == null || StringUtils.isBlank(params.get("lisLableNo").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "lisLableNo不能为空");
        }
        try {
            return checkDetailsService.CheckDetails(Long.valueOf(params.get("lisLableNo")));
        } catch (SystemException e) {
            e.printStackTrace();
            logger.error("CheckProjectDetails()方法SystemException异常" + e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(),StatusCode.INSIDE_ERROR.getMsg(),null);
        } catch (ESBException e) {
            e.printStackTrace();
            logger.error("CheckProjectDetails()方法ESB异常" + e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(),StatusCode.INSIDE_ERROR.getMsg(),null);
        }
    }
}
