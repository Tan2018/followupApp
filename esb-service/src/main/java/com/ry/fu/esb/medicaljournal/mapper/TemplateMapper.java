package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.medicaljournal.model.response.TemplateResponse;
import com.ry.fu.esb.medicaljournal.model.response.TemplateTypeResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author jane
 * @Date 2018/7/19
 */
@Mapper
public interface TemplateMapper {

    /**
     * 根据模板分类id查询模板列表
     * @param typeId
     * @return
     */
    @Select("select t.id as id,t.type_id as type_id,t.title as title,t.content as content, \n" +
            " t.create_time as create_time,t.status as status,b.name as name from m_template t \n" +
            " inner join m_template_func_type b on  t.type_id=b.id where t.use_type='2' and type_id= #{typeId}")
    List<TemplateResponse>selectTemplateList(@Param("typeId") Long typeId);

    /**
     * 查询模板分类列表
     * @return
     */
    @Select(" select id,name,create_time from m_template_func_type where use_type= '2' ")
    List<TemplateTypeResponse>selectTemplateTypeList();
}
