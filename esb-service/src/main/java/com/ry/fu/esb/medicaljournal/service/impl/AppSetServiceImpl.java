package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.enumstatic.ESBServiceCode;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.common.service.RequestService;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.medicaljournal.mapper.AboutAppMapper;
import com.ry.fu.esb.medicaljournal.mapper.AppVersionMapper;
import com.ry.fu.esb.medicaljournal.mapper.QuesFeedbackMapper;
import com.ry.fu.esb.medicaljournal.mapper.SymptomMapper;
import com.ry.fu.esb.medicaljournal.model.AboutApp;
import com.ry.fu.esb.medicaljournal.model.AppVersion;
import com.ry.fu.esb.medicaljournal.model.PartPic;
import com.ry.fu.esb.medicaljournal.model.QuesFeedback;
import com.ry.fu.esb.medicaljournal.model.response.InspectionCostRespone;
import com.ry.fu.esb.medicaljournal.service.AppSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/26 16:51
 **/
@Service
public class AppSetServiceImpl implements AppSetService{

    public static final Logger logger= LoggerFactory.getLogger(AppSetServiceImpl.class);

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Autowired
    private QuesFeedbackMapper quesFeedbackMapper;

    @Autowired
    private AboutAppMapper aboutAppMapper;

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private RequestService requestService;

    @Autowired
    private PublicService publicService;


    @Override
    public ResponseData findNewAppVersion(String useType,String appId) {
        AppVersion appVersion=null;
        try{
            appVersion =appVersionMapper.selectNewAppVersion(useType,appId);
        }catch(Exception e){
            return new ResponseData("500","查询出错",null);
        }
        if(appVersion==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        if("1".equals(appVersion.getRenewFlag())){
            return new ResponseData("404","",null);
        }
        return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),appVersion);
    }

    @Override
    public ResponseData insertQuesFeedbackInfo(Map<String,Object> map) {
        QuesFeedback quesFeedback=new QuesFeedback();
        String seq = BeanMapUtils.getTableSeqKey(quesFeedback);
        Long id = redisIncrementGenerator.increment(seq,1);
        quesFeedback.setId(id);
        quesFeedback.setAccountId(map.get("accountId").toString());
        quesFeedback.setVerNo(map.get("verNo").toString());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        quesFeedback.setCreateDate(new Date());
        quesFeedback.setUserType(map.get("userType").toString());
        quesFeedback.setQues(map.get("ques").toString());
        Integer result=quesFeedbackMapper.insert(quesFeedback);
        ResponseData responseData=new ResponseData();
        if(result>0){
            responseData.setData(result);
            responseData.setStatus("200");
            responseData.setMsg("操作成功");
        }
        return responseData;
    }



    @Override
    public ResponseData findAboutAppInfo(String useType) {
        AboutApp AboutApp1=aboutAppMapper.selectAboutAppInfo(useType);
        if(AboutApp1==null){
            return new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        AboutApp1.setImgUrl(requestService.picPreAdd+AboutApp1.getPwpUpload().getId());
        return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),AboutApp1);
    }
}
