package com.ry.fu.esb.medicaljournal.service;


import java.util.Date;

public interface JPushNoticeService {

    /**
     * 待支付通知
     * @param openId 用户别名
     * @param patientId 患者id
     */
    public void sendUnpaidPush(String openId, String patientId,String orderId, String time);

    /**
     *  挂号成功通知
     * @param openId 用户别名
     * @param userName 患者名
     */
    public void sendRegistrationSuccess(String openId,String patientId ,String userName,  String orderId);

    /**
     * 支付成功通知
     * @param openId 用户别名
     * @param userName 患者名
     * @param place 地点
     */
    public void sendPaymentSuccess(String openId, String userName, String place, String orderId);

    /**
     * 退费成功通知
     * @param openId 用户别名
     * @param userName 患者名
     */
    public void refundSuccessNotice(String openId, String userName,  String orderId);

    /**
     * 退费失败通知
     * @param openId 用户别名
     * @param userName 患者名
     */
    public void refundFailureNotice(String openId, String userName,  String orderId, String reason);

    /**
     * 排号通知
     * @param openId 用户别名
     * @param visitTime 预约时间
     * @param consultationRoom 科室
     */
    //public void sendAppointment(String openId, Date time, String consultationRoom, Integer pushType);
    public void sendAppointment(String openId, String name,String visitTime, String consultationRoom);

    /**
     * 危急值通知发布
     * @param lisLableNo 检验标本号
     * @param examineRequestID 检验申请单id
     * @param patientId 患者id
     * @param patientName 患者姓名
     * @param personId 员工id/科室id
     * @param personNo 员工工号/科室编码
     * @param noTelevel 1、开单医生；2、科室主任值班医生；3、总值班院领导
     * @param noteType 1、员工；2、科室
     * @param noteTime 超时时间
     * @param itemID 项目id
     * @param itemCode 项目编码
     * @param itemName 项目名称
     * @param receiveDate 接收危急值时间
     */
    public void sendCriticalValues(Long lisLableNo, Long examineRequestID, Long patientId, String patientName, Long personId, String personNo, Long noTelevel, Long noteType, Long noteTime, Long itemID, String itemCode, String itemName, Date receiveDate);

    /**
     * 危急值处理发布
     * @param lisLableNo 检验标本号
     * @param examineRequestID 检验申请单id
     * @param patientId 患者id
     * @param patientName 患者姓名
     * @param disposeWay 处理途径：1、电子病历；2、移动平台；3、LIS系统
     * @param disposeEmployeeNo 处理人工号
     * @param disposeEmployeeName 处理人姓名
     * @param disposeDatetime 处理日期时间
     * @param disposeTypeCode 处理类型编码
     * @param disposeTypeName 处理类型描述
     * @param disposeDescribe 处理备注
     * @param itemID 项目id
     * @param itemCode 项目编码
     * @param itemName 项目名称
     * @param disposeMinutes 处理时长
     */
    public void sendCriticalValuesHandler(Long lisLableNo, Long examineRequestID, Long patientId, String patientName, Long disposeWay, String disposeEmployeeNo, String disposeEmployeeName, Date disposeDatetime, String disposeTypeCode, String disposeTypeName, String disposeDescribe, Long itemID, String itemCode, String itemName, Long disposeMinutes);

    /**
     * 危急值解除审核
     * @param examineRequestId 检验申请单ID
     * @param statreMark 状态描述
     * @param optm 操作时间
     * @param results 检验编码
     */
    public void sendCriticalValuesRelieve(Long examineRequestId, String statreMark, Date optm, String results);

    /**
     * 催交款通知
     * @param patientId 患者id
     * @param patientName 患者姓名
     * @param orderId 订单id
     * @param time 订单创建时间
     */
    public void sendPushTheItem( String patientId,String patientName,String orderId, String time,String cause,Integer pushType);
}
