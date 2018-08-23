package com.ry.fu.esb.medicaljournal.model.request;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 *
 *
 * @author ：Boven
 * @Description ：检验标本字典查询
 * @create ： 2018-06-25 11:45
 **/
public class ExamineSpecimenRequest implements Serializable{


    private static final long serialVersionUID = 4849213985496774475L;
    /**
     *如果为-1时，则查询所有有效的标本信息
     */
    @XmlElement(name = "EXEMPLARID")
    private String exemplarId;

}
