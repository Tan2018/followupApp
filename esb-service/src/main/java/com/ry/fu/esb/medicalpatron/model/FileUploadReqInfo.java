package com.ry.fu.esb.medicalpatron.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/4/7 20:05
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FileUploadReqInfo implements Serializable {
    private static final long serialVersionUID = 3344943677902978604L;
    /**
     * 医生工号
     */
    private String doctorCode;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 上传图片
     */
    private MultipartFile[] file;
}
