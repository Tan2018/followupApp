package com.ry.fu.esb.medicaljournal.constants;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/4/17 19:56
 * @description 就医日志 Redis缓存的Key，本类主要是Redis的缓存的Key，方便管理，除了序列的Key，用来缓存的都放在这里
 */
public class RedisKeyConstants {

    //院区信息，南院，院本部等等信息Key
    public static final String HOSPITALINFO = "HospitalInfo";

    //智能导诊中部位和科室对应缓存数据Key
    public static final String SYMPTOMSLISTJSON = "cache_symptomsListJson";

    //科室信息
    public static final String DEPT_LIST = "DEPT_LIST";


    //His收费类别
    public static final String FEE_TYPE_LIST = "FEE_TYPE_LIST";

    //科室_开始日期_结束日期   号源
    public static final String DOCTOR_SOURCE = "DOCTOR_SOURCE_%s_%s_%s";

    //号源里面的排班  DOCTOR_科室ID_医生ID 可能医生排班在A科室，但是行政级别不属于B科室
    public static final String DOCTOR_DEPT_DOCTORID = "DOCTOR_%s_%s";

    //科室医生，行政归属的  ALL_DOCTOR_科室ID   S001057
    public static final String ALL_DOCTOR_DEPT = "ALL_DOCTOR_%s";

    //科室医生，行政归属的  ALL_DOCTOR_科室ID   S001056
    public static final String DOCTORQUERY_DEPT_DOCTORID = "DOCTORQUERY_%s_%s";

    //当天停诊医生 S001054
    public static final String STOPCONSULTATION_TYPE = "STOPCONSULTATION_%s";


    //门诊医生查询
    public static final String CLINICALQUERY_WEEK_TYPE = "CLINICALQUERY_%s_%s";
}
