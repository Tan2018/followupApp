package com.ry.fu.esb.medicalpatron.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/12 14:35
 * @description 随医拍文件相关信息
 */
@Data
@Table(name = "M_MEDICAL_FILE")
@EqualsAndHashCode(callSuper = false)
public class MedicalFile extends BaseModel{
    private static final long serialVersionUID = 5005720079696785823L;

    /**
     * ID
     */
    private Long id;

    /**
     * 业务类型表ID(外键)
     */
    private Long busTypeId;

    /**
     * 医生工号
     */
    private String doctorCode;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 文件路劲
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String busFileType;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 状态 0-正常 1-删除
     */
    private String status;
}
