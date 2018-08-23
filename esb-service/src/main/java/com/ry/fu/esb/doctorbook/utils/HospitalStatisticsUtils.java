package com.ry.fu.esb.doctorbook.utils;

/**
 * 病案首页工具类
 * @Author:Tom
 * @Description:
 * @create: 2018/5/4 17:45
 **/
public class HospitalStatisticsUtils {

    public static String getDataDesc(String dataType){
        String content="";
        if("1".equals(dataType)){
            content="门诊人次";
        }else if("2".equals(dataType)){
            content="住院人次";
        }else if("3".equals(dataType)){
            content="手术台次";
        }else{
            content="未知";
        }
        return content;
    }
/*
    public static String getCampus(String departmentId){
        String content="";
        if("1106".equals(departmentId)){
            content="院本部";
        }else if("1699".equals(departmentId)){
            content="南院";
        }else{
            content="未知";
        }
        return content;
    }*/

}
