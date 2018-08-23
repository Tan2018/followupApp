package com.ry.fu.esb.doctorbook.controller;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.service.PublicService;
import com.ry.fu.esb.doctorbook.model.request.*;
import com.ry.fu.esb.doctorbook.service.PrimeIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 *
 *
 * @author ：Boven
 * @Description ：主索引
 * @create ： 2018-06-26 16:16
 **/
@RestController
@RequestMapping("${prefixPath}/v1/docBook/pr")
public class PrimeIndexController {
    @Autowired
    private PublicService publicService;

    @Autowired
    private PrimeIndexService prService;
   @PostMapping("/primaryIndexRegistion")
    public ResponseData primaryIndexRegistion(@RequestBody PrimaryIndexRegistRequest request) throws ESBException, SystemException {

        return prService.primaryIndexRegistion(request);
    }
    @PostMapping("/primaryIndexQuery")
    public ResponseData primaryIndexQuery(@RequestBody PrimaryIndexQueryRequest request) throws ESBException, SystemException {

        return prService.primaryIndexQuery(request);
    }
    @PostMapping("/primaryIndexQueryPatient")
    public ResponseData primaryIndexQueryPatient(@RequestBody PrimaryIndexQueryRequest request) throws ESBException, SystemException {

        return prService.primaryIndexQueryPatient(request);
    }
    @PostMapping("/localIndexQuery")
    public ResponseData localIndexQuery(@RequestBody LocalIndexQueryRequest request) throws ESBException, SystemException {

        return prService.localIndexQuery(request);
    }
    @PostMapping("/primaryIndexChange")
    public ResponseData primaryIndexChange(@RequestBody PrimaryIndexQueryRequest request) throws ESBException, SystemException {

        return prService.primaryIndexChange(request);
    }
    @PostMapping("/localIndexQueryPatient")
    public ResponseData localIndexQueryPatient(@RequestBody LocalIndexQueryPatientRequest request) throws ESBException, SystemException {

        return prService.localIndexQueryPatient(request);
    }
    @PostMapping("/primaryIndexQueryPatientList")
    public ResponseData primaryIndexQueryPatientList(@RequestBody PrimaryIndexQueryPatientListRequest request)  {
        try {
            return prService.primaryIndexQueryPatientList(request);
        }catch (ParseException e){
            return  new ResponseData("500","时间转换异常",null);
        }catch (ESBException e){
            return  new ResponseData("599","查询结果太多，请更换搜索条件",null);
        }catch (SystemException e){
            return  new ResponseData("500","后台内部异常",null);
        }
    }



}
