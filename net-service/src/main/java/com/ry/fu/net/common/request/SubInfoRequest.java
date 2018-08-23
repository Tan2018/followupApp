package com.ry.fu.net.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@Data
@EqualsAndHashCode(callSuper =false)
public class SubInfoRequest implements Serializable {


    private static final long serialVersionUID = 4725642575963743300L;

}
