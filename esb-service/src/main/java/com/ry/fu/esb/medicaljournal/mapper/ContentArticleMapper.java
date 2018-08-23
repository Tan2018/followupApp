package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.ContentArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/12 17:38
 **/
@Mapper
public interface ContentArticleMapper extends BaseMapper<ContentArticle>{

    /**
     * 查询制定标题的文章接口
     * @return
     */
    @Select("SELECT TITLE,CONTENT FROM M_ARTICLE WHERE TITLE LIKE '%操作指引%'")
    ContentArticle selectContentArticleInfo();

    @Select("SELECT TITLE,CONTENT FROM M_ARTICLE WHERE ARTICLE_TYPE_ID=1523525746060")
    List<ContentArticle> selectJuniorInfo();
}
