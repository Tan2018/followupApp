package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.AppVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/4/26 9:24
 **/
@Mapper
public interface AppVersionMapper extends BaseMapper<AppVersion>{

    @Select("select id,ver_No,ios_Url,android_Url,descr,verCode,html_ver,app_id,ipad_url,use_type,renew_flag,strong_Flag  from (select id,ver_No,ios_Url,android_Url,descr,verCode,html_ver,app_id,ipad_url,use_type,renew_flag,strong_Flag from M_APP_VERSION " +
            "where use_type=#{useType} and app_id=#{appId} order by create_date desc) a where rownum=1")
    public AppVersion selectNewAppVersion(@Param("useType") String useType,@Param("appId") String appId);
}
