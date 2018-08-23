package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.http.HttpRequest;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.model.ContentArticle;
import com.ry.fu.esb.medicaljournal.service.ContentArticleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/12 18:00
 **/
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/contentArticle")
public class ContentArticleController {

    private static Logger logger= LoggerFactory.getLogger(ContentArticleController.class);

    @Autowired
    private ContentArticleService contentArticleService;

    @RequestMapping(value = "/queryContentArticleInfo",method =  RequestMethod.POST)
    public ResponseData queryContentArticleInfo()throws SystemException, ESBException {
        return contentArticleService.findContentArticleInfo();
    }

    @RequestMapping(value = "/queryJuniorInfo",method =  RequestMethod.POST)
    public ResponseData queryJuniorInfo()throws SystemException, ESBException {
        return contentArticleService.findJuniorInfo();
    }
}
