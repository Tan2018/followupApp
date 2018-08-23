package com.ry.fu.esb.medicaljournal.model;

import com.ry.fu.esb.common.constants.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Seaton
 * @version V1.0.0
 * @description 查询未支付订单通知用户支付
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterSucesessRecordVO implements Serializable {

    private static final long serialVersionUID = 4136719068688998201L;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 医生名字
     */
    private String doctorName;

    /**
     * 科室名字
     */
    private String deptName;

    /**
     * 院区
     */
    private String hospitalName;


    /**
     * 候诊室
     */
    private  String diagnoseRoom;

    /**
     * 就诊日期
     */
    private Date visitDate;

    /**
     * 就诊时段
     */
    private String visitTime;

    /**
     *挂号订单创建时间
     */
    private Date createTime;

    /**
     * 诊查费
     */
    private Long totalFee;

    /**
     * 就诊人
     */
    private String patientName;

    /**
     * 挂号His流水号
     */
    private String hisOrderId;

    /**
     * 订单状态
     */
    private String orderStatus;

    public String getOrderStatus() {
        //对前端显示超时取消,后台保持可支付状态
        if(Constants.ORDER_NO_PAY_STATUS.equals(orderStatus) && DateUtils.addMinutes(createTime,Constants.ORDER_OVER_TIME) .before (new Date())){
            return Constants.ORDER_EXPIRED_STATUS;
        }
        return orderStatus;
    }

}
