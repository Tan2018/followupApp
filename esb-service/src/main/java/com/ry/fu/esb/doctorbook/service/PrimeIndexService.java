package com.ry.fu.esb.doctorbook.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.model.request.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;

/**
 * 主索引service
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-06-26 16:18
 **/
public interface PrimeIndexService {
    ResponseData primaryIndexRegistion(@RequestBody PrimaryIndexRegistRequest request) throws ESBException, SystemException;
    ResponseData primaryIndexQuery(@RequestBody PrimaryIndexQueryRequest request) throws ESBException, SystemException;
    ResponseData primaryIndexQueryPatient(@RequestBody PrimaryIndexQueryRequest request) throws ESBException, SystemException;
    ResponseData localIndexQuery(@RequestBody LocalIndexQueryRequest request) throws ESBException, SystemException;
    ResponseData primaryIndexChange(@RequestBody PrimaryIndexQueryRequest request) throws ESBException, SystemException;
    ResponseData localIndexQueryPatient(@RequestBody LocalIndexQueryPatientRequest request) throws ESBException, SystemException;
    ResponseData primaryIndexQueryPatientList(@RequestBody PrimaryIndexQueryPatientListRequest request) throws ESBException, SystemException, ParseException;


}
