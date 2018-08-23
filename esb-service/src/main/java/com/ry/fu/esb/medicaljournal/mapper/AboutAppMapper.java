package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.AboutApp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/27 10:55
 **/
@Mapper
public interface AboutAppMapper extends BaseMapper<AboutApp>{
        AboutApp selectAboutAppInfo(@Param("useType") String useType);
}
