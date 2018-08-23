package com.ry.fu.esb.medicalpatron.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/12 14:39
 * @description 随医拍分页请求参数
 */
@Data
public class ReqInfo extends BaseModel {
    private static final long serialVersionUID = 8508631876628811045L;
    /**
     * 医生工号
     */
    private String doctorCode;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 页码数
     */
    private Integer pageNum;

    /**
     * 页码大小
     */
    private Integer pageSize;
}
