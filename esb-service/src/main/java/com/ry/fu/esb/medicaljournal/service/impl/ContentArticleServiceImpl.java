package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.mapper.ContentArticleMapper;
import com.ry.fu.esb.medicaljournal.model.ContentArticle;
import com.ry.fu.esb.medicaljournal.service.ContentArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/12 17:49
 **/
@Service
public class ContentArticleServiceImpl implements ContentArticleService {

    private static Logger logger= LoggerFactory.getLogger(ContentArticleServiceImpl.class);


    @Autowired
    private ContentArticleMapper contentArticleMapper;

    @Override
    public ResponseData findContentArticleInfo() {
        return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),contentArticleMapper.selectContentArticleInfo());
    }

    @Override
    public ResponseData findJuniorInfo() {
        List<ContentArticle> list= contentArticleMapper.selectJuniorInfo();
        if(list.size()<=0){
            new ResponseData(StatusCode.ESB_NOT_QUERY_DATA.getCode(),StatusCode.ESB_NOT_QUERY_DATA.getMsg(),null);
        }
        return new ResponseData(StatusCode.OK.getCode(),StatusCode.OK.getMsg(),list);
    }
}
