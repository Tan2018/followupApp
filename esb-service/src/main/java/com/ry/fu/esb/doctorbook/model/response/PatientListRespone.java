package com.ry.fu.esb.doctorbook.model.response;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ：Boven
 * @Description ：患者列表
 * @create ： 2018-03-12 16:35
 **/
public class PatientListRespone implements Serializable {
    @XmlElement(name="RECORD")
    private List<PatientListResponeRecord> patientListResponeRecord;
    private ArrayList<PatientListResponeRecord> patientArray;



}
