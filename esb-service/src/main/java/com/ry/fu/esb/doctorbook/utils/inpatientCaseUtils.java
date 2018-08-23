package com.ry.fu.esb.doctorbook.utils;

/**
 * 病案首页工具类
 * @Author:Tom
 * @Description:
 * @create: 2018/5/4 17:45
 **/
public class inpatientCaseUtils {

    public static String getMaritalType(String typeNumber){
        String content="";
        if("1".equals(typeNumber)){
            content="未婚";
        }else if("2".equals(typeNumber)){
            content="已婚";
        }else if("3".equals(typeNumber)){
            content="丧偶";
        }else if("4".equals(typeNumber)){
            content="离婚";
        }else if("9".equals(typeNumber)){
            content="其他";
        }else{
            content="未知";
        }
        return content;
    }

    public static String getInpatientWayType(String typeNumber){
        String content="";
        if("1".equals(typeNumber)){
            content="急诊";
        }else if("2".equals(typeNumber)){
            content="门诊";
        }else if("3".equals(typeNumber)){
            content="其他医疗机构转入";
        }else if("9".equals(typeNumber)){
            content="其他";
        }else{
            content="未知";
        }
        return content;
    }
    public static String getipFlagType(String typeNumber){
        String content="";
        if("0".equals(typeNumber)){
            content="入院登记";
        }else if("1".equals(typeNumber)){
            content="在院";
        }else if("2".equals(typeNumber)){
            content="转科";
        }else if("3".equals(typeNumber)){
            content="批准出院";
        }else if("4".equals(typeNumber)){
            content="出院返回";
        }else if("5".equals(typeNumber)){
            content="确认出院";
        }else{
            content="未知";
        }
        return content;
    } public static String marriagToBasic(String typeNumber){
        String content="";
        if("0".equals(typeNumber)){
            content="入院登记";
        }else if("1".equals(typeNumber)){
            content="在院";
        }else if("2".equals(typeNumber)){
            content="转科";
        }else if("3".equals(typeNumber)){
            content="批准出院";
        }else if("4".equals(typeNumber)){
            content="出院返回";
        }else if("5".equals(typeNumber)){
            content="确认出院";
        }else{
            content="未知";
        }
        return content;
    }
}
