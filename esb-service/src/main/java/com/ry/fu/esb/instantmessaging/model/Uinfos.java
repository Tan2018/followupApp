package com.ry.fu.esb.instantmessaging.model;

import com.ry.fu.esb.common.response.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 即时通讯
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Uinfos extends BaseModel {

    /**
     * 医生名字
     */
    private String chName;
    /**
     * 医生职称
     */
    private String professName;

    /**
     * 医生职称
     */
    private String headImg;

    /**
     * 医生职称
     */
    private String doctorId;
    /**
     * user_name就是doctorCode
     */
    private String userName;
}
