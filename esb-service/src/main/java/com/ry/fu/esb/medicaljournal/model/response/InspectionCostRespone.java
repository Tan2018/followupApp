package com.ry.fu.esb.medicaljournal.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 检查检验费用
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-04-03 9:57
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@XmlRootElement(name = "RESPONSE")
@EqualsAndHashCode(callSuper = false)
public class InspectionCostRespone implements Serializable{
    private static final long serialVersionUID = -4793951576384252062L;
    private ArrayList itemIdList;
    private ArrayList itemList;
    private ArrayList itemNameList;
    private Map<String,String> itemMap;
    @XmlElement(name="RECORD")
    private List<InspectionCostRecord> inspectionCostRecord;

}
