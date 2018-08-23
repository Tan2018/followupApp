package com.ry.fu.net.common.enumstatic;

/**
 * ESB 服务码
 */
public class ESBServiceCode {

    private ESBServiceCode(){

    }

    /**
     * 医生手册-住院患者医嘱查询
     */
    public static final String DOCTORADVICE = "B003004";

    /**
     * 医生手册-检验报告查询
     */
    public static final String INSPECTIONREPORT = "B002008";

    /**
     * 医生手册-检查结果查询
     */
    public static final String PATIENTDETAILS = "B003010";

    /**
     * 医生手册-住院患者病程记录信息查询
     */
    public static final String COURSEOFDISASERECORD="B007013";

    /**
     * 医生手册-住院患者手术记录
     */
    public static final String OPERATIONRECORD="B008001";

    /**
     * 医生手册-医生住院患者信息查询
     */
    public static final String INPATIENTINFO = "B007011";

    /**
     * 医生手册-住院病人病案首页基本信息查询
     */
    public static final String INPATIENTCASE = "B014003";
    /**
     * 医生手册-住院病人基本信息查询
     */
    public static final String INPATIENTBASIC = "B007012";
    /**
     * 医生手册-全部住院病人基本信息查询
     */
    public static final String HOSPITALPATIENTS = "B007014";

    /**
     * 医生手册-住院患者入院记录信息
     */
    public static final String HOSPITALIZATIONRECORD="B007012";
    /**
     * 	医生手册-护理三测
     */
    public static final String NURSEMEASURE="B012006";

    /**
     * 	医生手册-随访系统登录认证
     */
    public static final String SYSTEMLOGIN="A005001";
    /**
     * 	医生手册-其他科室查询
     */
    public static final String OTHERQUERY="A001002";
    /**
     * 	医生手册-影像接口
     */
    public static final String MEDIACALIMAGE="B001006";




    /**
     * 	门诊处方服务
     */
    public static final String PRESCRIPTION="B004007";
    /**
     * 	就医日志检查检验费用详情
     */
    public static final String INSPECTIONCOST="B004009";


    /**
     *科室列表
     */
    public static final String DEPTLIST="S001002";

    public static final String DOCTORINFOLIST="S001003";

    public static final String ALLDOCTORINFOLIST="S001057";

    /**
     * 	医生号源信息
     */
    public static final String DOCTORREGISTERSOURCE ="S001004";

    /**
     * 	医生号源分时
     */
    public static final String DOCTORREGISTERSOURCEBYTIME ="S001005";

    /**
     * 患者信息查询
     */
    public static final String PATIENT_INFO ="S001007";

    /**
     *用户待缴费记录查询
     */
    public static final String  USERPAYRECORDINQUIRE="S001021";

    /**
     * 	科室号源信息
     */
    public static final String DEPTREGISTERSOURCE ="S001006";

    public static final String REGISTERLOCKED ="S001010";

    public static final String REGISTER ="S001011";

    public static final String CANCELREGISTER ="S001012";
    /**
     * 曾挂号医生
     */
    public static final String REGISTEREDDOCTOR ="S001047";

    /**
     *挂号记录查询
     */
    public static final String REGISTEREDLIST ="S001025";

    /**
     * 申请挂号退费，只是查询是否允许退费，不做退费
     */
    public static final String REGISTERCANREFUND ="S001026";

    /**
     * 挂号退费
     */
    public static final String REGISTERREFUND ="S001015";

    /**
     * 号源锁定
     */
    public static final String REGISTERSOURCELOCKED ="S001008";
    /**
     * 号源解除锁定
     */
    public static final String REGISTERSOURCEUNLOCKED ="S001009";
    /**
     * 指定获取医生列表（交班）
     */
    public static final String DESIGNATEDDOCTORS ="S001003";
    /**
     * 根据科室 医生简介模糊查询医生信息
     */
    public static final String DOCTORINFOBYNAME = "S001046";
    /**
     * 挂号支付，用于支付成功后调取HIS接口
     */
    public static final String REGISTEREDPAY = "S001013";
    /**
     * 复诊预约
     */
    public static final String RECHECKRESERVATION =  "S001048";
    /**
     * 科室切换
     */
    public static final String DEPTSWITCH ="S001051";
    /**
     * 挂号详情
     */
    public static final String REGISTERERECORD ="S001049";
    /**
     * 今日停诊
     */
    public static final String STOPCONSULTATION ="S001054";
    /**
     * 门诊查询
     */
    public static final String CLINICALQUERY ="S001055";
    /**
     * 订单支付
     */
    public static final String PAYMENT ="S001014";

    /**
     * 医生信息查询
     */
    public static final String DOCTORINFOQUERY ="S001056";

    /**
     * 	用户待缴费记录支付
     */
    public static final String WAITPAYMENT ="S001022";
    /**
     * 	用户待缴费记录支付
     */
    public static final String REGISTREXAMIN ="S001022";

    /**
     *检验危急值处理发布
     */
    public static final String CRITICALVALUESHANDLER = "B002012";
}
