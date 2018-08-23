package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author Seaton
 * @Date 2018/3/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PartPic implements Serializable {


    private static final long serialVersionUID = 3964790918478754453L;

    private String uploadid;
    private byte[] bytearray;

}
