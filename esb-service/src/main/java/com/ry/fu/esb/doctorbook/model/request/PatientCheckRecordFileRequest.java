package com.ry.fu.esb.doctorbook.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

/**
 * 患者检查记录文件
 * @author Howard
 * @version V1.0.0
 * @date 2018/4/9 15:01
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PatientCheckRecordFileRequest {
    /**
     * 患者信息记录ID
     */
    private String patientRecordId;

    /**
     * 图片
     */
    private MultipartFile[] shiftPicture;

    /**
     * 声音
     */
    private MultipartFile[] shiftVoice;
}
