package com.ry.fu.esb.doctorbook.model.response;

import com.ry.fu.esb.doctorbook.model.OtherDepInfoLog;
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
public class OtherDepInfoLogReponse implements Serializable {
    private static final long serialVersionUID = 2953794105039635567L;

    @XmlElement(name="RECORD")
    private List<OtherDepInfoLog> otherDepInfoLogList;

    @XmlElement(name="TOTAL")
    private Integer total;
}
