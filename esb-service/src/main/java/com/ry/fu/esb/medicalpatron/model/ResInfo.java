package com.ry.fu.esb.medicalpatron.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Howard
 * @version V1.0.0
 * @Date 2018/3/12 14:51
 * @description 随医拍分页响应信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResInfo extends BaseModel {
    private static final long serialVersionUID = 5328959915502655748L;
    /**
     * ID
     */
    private Long id;

    /**
     * 文件路劲
     */
    private String filePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 上传时间
     */
    private Date uploadTime;
}
