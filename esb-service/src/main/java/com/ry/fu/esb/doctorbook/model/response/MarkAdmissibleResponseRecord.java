package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
* @Author:Boven
* @Description:标记已受理
* @Date: Created in 20:01 2018/3/6
*/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class MarkAdmissibleResponseRecord implements Serializable{


    private static final long serialVersionUID = -4178982963590579676L;
    /**
     *操作成功信息
     */

    private String message="未操作";


}
