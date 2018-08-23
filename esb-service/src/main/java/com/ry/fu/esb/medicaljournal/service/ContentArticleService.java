package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.model.ContentArticle;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/12 17:43
 **/
public interface ContentArticleService {

    /**
     * 查询制定标题的文章接口
     * @return
     */
    ResponseData findContentArticleInfo();


    /**
     * 查询专科信息文章接口
     */
    ResponseData findJuniorInfo();
}
