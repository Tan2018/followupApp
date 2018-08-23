package com.ry.fu.esb.jpush.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.transformer.Transformer;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.jpush.model.request.*;
import com.ry.fu.esb.jpush.service.DoctoresManualService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.text.ParseException;
import java.util.Map;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/5/12
 * @description 危急值controller
 */
@RestController
@RequestMapping("${prefixPath}/v1/jpush/dm")
public class DoctoresManualController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DoctoresManualService doctoresManualService;

    @Autowired
    private Transformer transformer;

    /**
     * 危急值列表(供前端调用)
     *
     * @param request {"personId":"3"}等数据格式
     * @return
     */
    @RequestMapping(value = "/doctoresManuaList", method = RequestMethod.POST)
    public ResponseData doctoresManuaList(@RequestBody PatientInformationsRequest request) {
        try {
            logger.info("危急值列表请求参数---json:{}", JsonUtils.toJSon(request));
            //校验参数
            if (request == null) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (request.getPersonId() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "PersonId不能为空");
            }
            if (request.getPageNum() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "PageNum不能为空");
            }
            if (request.getPageSize() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "PageSize不能为空");
            }
            return doctoresManualService.selectByPatientInformation(request);
        } catch (SystemException e) {
            logger.error("doctoresManuaList -> SystemException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        } catch (ESBException e) {
            logger.error("doctoresManuaList -> ESBException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        }
    }

    /**
     * 危急值通知(供平台调用)
     *
     * @param requestXml {<request></request>}等数据格式
     * @return
     */
    @RequestMapping(value = "/sendCriticalValues", method = RequestMethod.POST)
    public ResponseData sendCriticalValues(@RequestBody String requestXml) {
        try {
            logger.info("危急值通知请求参数XML格式{}" + requestXml);
            SendCriticalValuesRequest request = transformer.readFromXML(requestXml, SendCriticalValuesRequest.class);
            logger.info("危急值通知请求参数---json:{}" + JsonUtils.toJSon(request));
            if (request == null) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (request.getLisLableNo() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "LisLableNo不能为空");
            }
            if (request.getNoteList() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "NoteList不能为空");
            }
            return doctoresManualService.sendCriticalValues(request);
        } catch (JAXBException e) {
            logger.error("sendCriticalValues - > JAXBException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        } catch (ParseException e) {
            logger.error("sendCriticalValues - > ParseException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        } catch (SystemException e) {
            logger.error("sendCriticalValues - > SystemException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        } catch (ESBException e) {
            logger.error("sendCriticalValues - > ESBException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        }
    }

    /**
     * 危急值处理(供平台调用)
     *
     * @param requestXml {<request></request>}等数据格式
     * @return
     */
    @RequestMapping(value = "/sendCriticalValuesHandler", method = RequestMethod.POST)
    public ResponseData sendCriticalValuesHandler(@RequestBody String requestXml) {

        try {
            logger.info("危急值处理请求参数XML格式{}" + requestXml);
            SendCriticalValuesHandlerRequest request = transformer.readFromXML(requestXml, SendCriticalValuesHandlerRequest.class);
            logger.info("危急值处理请求参数---json:{}" + JsonUtils.toJSon(request));
            if (request == null) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (request.getLisLableNo() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "LisLableNo不能为空");
            }
            if (request.getDisposeWAY() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "DisposeWAY不能为空");
            }
            if (request.getDisposeEmployeeNo() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "DisposeEmployeeNo不能为空");
            }
            if (request.getDisposeEmployeeName() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "DisposeEmployeeName不能为空");
            }
            if (request.getDisposeDatetime() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "DisposeDatetime不能为空");
            }
            if (request.getDisposeTypeCode() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "DisposeTypeCode不能为空");
            }
            if (request.getDisposeresults() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "Disposeresults不能为空");
            }
            return doctoresManualService.sendCriticalValuesHandler(request);
        } catch (JAXBException e) {
            logger.error("sendCriticalValuesHandler - > JAXBException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        } catch (ParseException e) {
            logger.error("sendCriticalValuesHandler - > ParseException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        }
    }

    /**
     * 危急值解除(供平台调用)
     *
     * @param requestXml {<request></request>}等数据格式
     * @return
     */
    @RequestMapping(value = "/sendCriticalValuesRelieve", method = RequestMethod.POST)
    public ResponseData sendCriticalValuesRelieve(@RequestBody String requestXml) {

        try {
            logger.info("危急值解除请求参数XML格式{}" + requestXml);
            SendCriticalValuesRelieveRequest request = transformer.readFromXML(requestXml, SendCriticalValuesRelieveRequest.class);
            logger.info("危急值解除请求参数---json:{}" + JsonUtils.toJSon(request));
            if (request == null) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (request.getExamineRequestId() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "ExamineRequestId不能为空");
            }
            if (request.getOptm() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "Optm不能为空");
            }
            if (request.getStatremark() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "Statremark不能为空");
            }
            if (request.getResults() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "Results不能为空");
            }
            return doctoresManualService.sendCriticalValuesRelieve(request);
        } catch (JAXBException e) {
            logger.error("sendCriticalValuesRelieve - > JAXBException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        } catch (ParseException e) {
            logger.error("sendCriticalValuesRelieve - > ParseException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        }
    }

    /**
     * 危急值处理(供前端调用)
     *
     * @param request {"lisLableNo":"123"}等数据格式
     * @return
     */
    @RequestMapping(value = "/criticalValueProcessing", method = RequestMethod.POST)
    public ResponseData criticalValueProcessing(@RequestBody CriticalValueProcessingRequest request) {

        try {
            logger.info("前端危急值处理请求参数{}" + request);
            if (request == null) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (request.getLisLableNo() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "LisLableNo不能为空");
            }
            if (request.getDisposeEmployeeNo() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "DisposeEmployeeNo不能为空");
            }
            if (request.getDisposeEmployeeName() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "DisposeEmployeeName不能为空");
            }
            if (request.getDisposeDescribe() == null) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "DisposeDescribe不能为空");
            }
            return doctoresManualService.criticalValueProcessing(request);
        } catch (SystemException e) {
            logger.error("criticalValueProcessing - > SystemException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        } catch (ESBException e) {
            logger.error("criticalValueProcessing - > ESBException", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        }
    }

    /**
     * 查询医生手册推送记录条数
     *
     * @param request {"personId":"123"}等数据格式
     * @return
     */
    @RequestMapping(value = "/selectByPersonId", method = RequestMethod.POST)
    public ResponseData selectByPersonId(@RequestBody PushBarNumberRequest request) {
        if (request == null) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (request.getPersonId() == null) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "PersonId不能为空");
        }
        return doctoresManualService.selectByPersonId(request);
    }

    /**
     * 根据医生别名，清空某一类型的推送记录
     *
     * @param params {"personId":"123"}等数据格式
     * @return
     */
    @RequestMapping(value = "/deleteByPersonIdAndNoticeType", method = RequestMethod.POST)
    public ResponseData deleteByPersonIdAndNoticeType(@RequestBody Map<String, String> params) {
        //校验参数
        if (params == null || params.size() == 0) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (params.get("personId") == null || StringUtils.isBlank(params.get("personId").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "personId不能为空");
        }
        if (params.get("noticeType") == null || StringUtils.isBlank(params.get("noticeType").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "noticeType不能为空");
        }
        return doctoresManualService.deleteByPersonIdAndNoticeType(params.get("personId").toString(), params.get("noticeType").toString());
    }

    /**
     * 医生手册保存推送记录
     *
     * @param request {"personId":"123"}等数据格式
     * @return
     */
    @RequestMapping(value = "/savePushBarNumber", method = RequestMethod.POST)
    public ResponseData savePushBarNumber(@RequestBody PushBarNumberRequest request) {

        //参数校验
        if (request == null) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (request.getPersonId() == null) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "personId不能为空");
        }
        if (request.getNoticeType() == null) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "noticeType不能为空");
        }
        return doctoresManualService.savePushBarNumber(request);
    }
}
