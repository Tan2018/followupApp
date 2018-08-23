package com.ry.fu.esb.doctorbook.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/3/30 11:40
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_PT_FILE")
public class PatientRecordFile extends BaseModel{
    private static final long serialVersionUID = -2979103657829936972L;
    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 交接班ID
     */
    @Column(name = "PC_ID")
    private Long patientRecordId;

    /**
     * 附件路径
     */
    @Column(name = "FILE_PATH")
    private String filePath;

    /**
     * 附件类型 0-图片、1-语音、2-短视频
     */
    @Column(name = "FLAG")
    private String flag;

    /**
     * 上传时间
     */
    @Column(name = "UPLOAD_TIME")
    private Date uploadTime;
}
