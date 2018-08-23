package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.doctorbook.model.DepDoctor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper = false)
public class DepAllResponse implements Serializable{
    private static final long serialVersionUID = -4427204872922322941L;

    @XmlElement(name="RECORD")
    private List<DepDoctor> list;
}
