package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.Order;
import com.ry.fu.esb.medicaljournal.model.response.OrderResponse;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/19 11:54
 * @description 订单信息
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 挂号时候插入到订单表
     *
     * @param id         主键ID
     * @param regId      挂号ID
     * @param feeType    付费类型-诊查费
     * @param totalFee   总费用
     * @param operater   操作人
     * @param orderName  订单名称
     * @param createTime 创建时间
     */
    @Insert("INSERT INTO M_ORDER(id, reg_id, order_type, fee_type, total_fee, medical_fee, personal_fee, order_status, operater, " +
            "order_name, create_time)" +
            "values(#{id}, #{regId},'40', #{feeType}, #{totalFee}, '0', #{totalFee}, '0', #{operater},#{orderName},#{createTime})")
    void insertOrder(@Param("id") String id, @Param("regId") Long regId, @Param("feeType") String feeType, @Param("totalFee") String
            totalFee,
                     @Param("operater") String operater, @Param("orderName") String orderName, @Param("createTime") Date createTime);

    /**
     * 根据订单状态和类别查询订单
     *
     * @param orderStatus
     * @param feeType
     * @return
     */
    @Select("select a.id, a.reg_id as regId, a.order_status as orderStatus, a.total_fee as totalFee, a.update_time as updateTime " +
            "from m_order a where a.order_status = #{orderStatus} and a.fee_type = #{orderType}")
    List<Order> selectByStatus(@Param("orderStatus") String orderStatus, @Param("feeType") String feeType);

    /**
     * 查询需要取消的订单 挂号费
     *
     * @return
     */
    @Select("select a.id, a.reg_id as regId, a.order_status as orderStatus, a.total_fee as totalFee, a.update_time as updateTime " +
            " from m_order a where a.order_status = '0' and a.fee_type = '诊查费' and CEIL((to_date(#{currentDate},'yyyy-mm-dd hh24:mi:ss') " +
            "- a.create_time) * 24 * 60) > 30" +
            " and rownum <= 5 order by a.create_time asc")
    List<Order> selectNeedCancel(String currentDate);

    /**
     * 查询非医保用户未支付的订单，状态必须是0的前五条
     *
     * @return
     */
    @Select("select a.id, a.reg_id as regId, a.order_status as orderStatus, a.total_fee as totalFee, a.update_time as updateTime " +
            " from m_order a where a.order_status = '0'  and pay_type != 'sunshier_wallet'and a.create_time>sysdate-3 and rownum <= 5 " +
            "order by a.update_time asc")
    List<Order> selectNotPayOrder();

    @Update("update M_ORDER set update_time = #{updateTime} where id = #{orderId}")
    int updateDate(@Param("updateTime") Date updateTime, @Param("orderId") String orderId);

    /**
     * 根据挂号ID，查挂号订单
     *
     * @param id
     * @return
     */
    @Select("select ORDER_SOURCE,ORDER_TYPE,ORDER_STATUS,REMARK,OPERATER,CREATE_TIME,UPDATE_TIME,STATUS_REMARK,ORDER_NAME,OUT_ORDER_NO," +
            "REG_ID,HIS_REG_NO,TOTAL_FEE,AUTO_CANCEL_TIME,FEE_TYPE,REFUND_DATE,HIS_ORDER_ID,DOCTOR_ADVICE_ID,ID,MEDICAL_FEE,PERSONAL_FEE," +
            "TRADE_NO,AGT_TRADE_NO,PAY_TYPE,PAY_SUCCESS_TIME,ORDERDESC from M_ORDER where reg_id =#{regId} and (order_status<=1 or " +
            "order_status=8) and (fee_type='挂号费' or fee_type='诊查费')")
    Order selectAndRegid(@Param("regId") Long id);

    @Select("select ORDER_SOURCE,ORDER_TYPE,ORDER_STATUS,REMARK,OPERATER,CREATE_TIME,UPDATE_TIME,STATUS_REMARK,ORDER_NAME,OUT_ORDER_NO," +
            "REG_ID,HIS_REG_NO,TOTAL_FEE,AUTO_CANCEL_TIME,FEE_TYPE,REFUND_DATE,HIS_ORDER_ID,DOCTOR_ADVICE_ID,ID,MEDICAL_FEE,PERSONAL_FEE," +
            "TRADE_NO,AGT_TRADE_NO,PAY_TYPE,PAY_SUCCESS_TIME,ORDERDESC from M_ORDER where reg_id=#{regId} and " +
            "DOCTOR_ADVICE_ID=#{doctorAdviceId}")
    Order selectAndRegidRest(@Param("regId") Long regId, @Param("doctorAdviceId") String doctorAdviceId);


    @Select("select  ORDER_SOURCE,ORDER_TYPE,ORDER_STATUS,REMARK,OPERATER,CREATE_TIME,UPDATE_TIME,STATUS_REMARK,ORDER_NAME,OUT_ORDER_NO " +
            "REG_ID,HIS_REG_NO,TOTAL_FEE,AUTO_CANCEL_TIME,FEE_TYPE,REFUND_DATE,HIS_ORDER_ID,DOCTOR_ADVICE_ID,ID,MEDICAL_FEE,PERSONAL_FEE," +
            "TRADE_NO,AGT_TRADE_NO,PAY_TYPE,PAY_SUCCESS_TIME,ORDERDESC  from m_order where reg_id=#{regId} and order_status='0' and " +
            "order_name='总费用'  order by create_time desc")
    List<Order> selectAndRegidAndOrderName(@Param("regId") Long regId);


    @Delete("delete from m_order where id=#{id}")
    int deleteById(@Param("id") String id);

//
//    @Select("select * from M_ORDER where reg_id=#{regId} and DOCTOR_ADVICE_ID=#{doctorAdviceId}")
//    Order selectLastRegidRest(@Param("regId") Long regId,@Param("doctorAdviceId") String doctorAdviceId);

    /**
     * 已支付历史记录
     *
     * @param accountId
     * @return
     */
    @Select("select t.order_status,t.total_fee ,t.create_time,t.fee_type,t.id as order_id ," +
            "(select ch_name from m_doctor  where doctor_id=t.operater)as doctor_name," +
            "(select org_name from pwp_org where org_id= a.dept_id)as org_name,a.patient_id as patient_id, " +
            "(select name from m_patient where esb_patient_id =a.patient_id )as patient_name " +
            " from m_order t,m_registration a where a.id=t.reg_id and t.fee_type ='总费用' and a.account_id= #{accountId} " +
            " and t.order_status in （1，5）  order by t.create_time desc")
    List<OrderResponse> selectOrderPayHistory(@Param("accountId") Long accountId);

    /**
     * 未支付已过期历史记录
     *
     * @param accountId
     * @return
     */
    @Select("select t.order_status,t.total_fee ,t.create_time,t.fee_type,t.id as order_id ," +
            "(select ch_name from m_doctor  where doctor_id=t.operater)as doctor_name," +
            "(select org_name from pwp_org where org_id= a.dept_id)as org_name,a.patient_id as patient_id," +
            " (select name from m_patient where esb_patient_id =a.patient_id )as patient_name " +
            " from m_order t,m_registration a where a.id=t.reg_id and t.fee_type ='总费用' and a.account_id= #{accountId} " +
            " and to_date(#{currentDate},'yyyy-mm-dd hh24:mi:ss') >= trunc(t.create_time)+1-1/86400  and t.order_status=0 order by t.create_time desc")
    List<OrderResponse> selectOrderHistory(@Param("accountId") Long accountId ,@Param("currentDate")String currentDate);

    /**
     * 未支付订单记录
     *
     * @param accountId
     * @return
     */
    @Select("select t.order_status,t.total_fee ,t.create_time,t.fee_type,t.id as order_id , " +
            " (select ch_name from m_doctor  where doctor_id = t.operater)as doctor_name, " +
            " (select org_name from pwp_org where org_id = a.dept_id)as org_name ," +
            "b.phone as phone, t.medical_fee as medical_fee,t.personal_fee as personal_fee, " +
            " b.name as patient_name,b.id_card as id_card,b.health_card_no as health_card_no," +
            " a.patient_id as patient_id  from m_order t,m_registration a ,m_patient b where a.id=t.reg_id " +
            " and a.patient_id = b.esb_patient_id and t.fee_type ='总费用' and a.ACCOUNT_ID= #{accountId}" +
            " and  to_date(#{currentDate},'yyyy-mm-dd hh24:mi:ss') <= trunc(t.create_time)+1-1/86400  and t.order_status=0 order by t.create_time desc ")
    List<OrderResponse> selectOrderNoPayHistory(@Param("accountId") Long accountId,@Param("currentDate")String currentDate);


    /**
     * 取药查询
     *
     * @param regid
     * @return
     */
    @Select("select ORDER_SOURCE,ORDER_TYPE,ORDER_STATUS,REMARK,OPERATER,CREATE_TIME,UPDATE_TIME,STATUS_REMARK,ORDER_NAME,OUT_ORDER_NO " +
            "REG_ID,HIS_REG_NO,TOTAL_FEE,AUTO_CANCEL_TIME,FEE_TYPE,REFUND_DATE,HIS_ORDER_ID,DOCTOR_ADVICE_ID,ID,MEDICAL_FEE,PERSONAL_FEE," +
            " TRADE_NO,AGT_TRADE_NO,PAY_TYPE,PAY_SUCCESS_TIME,ORDERDESC from M_ORDER where REG_ID=#{regid} and order_name='总费用' and " +
            "  ORDER_STATUS=1")
    List<Order> selectRegid(@Param("regid") Long regid);
}
