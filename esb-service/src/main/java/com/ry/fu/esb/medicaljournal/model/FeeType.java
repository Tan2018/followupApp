package com.ry.fu.esb.medicaljournal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author Seaton
 * @Date 2018/3/14
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class FeeType implements Serializable {

    private static final long serialVersionUID = -7540670626449322078L;

    @Id
    private Long id;
    /**
     * Typekey
     */
    //@Column(name = "TYPE_NO")
    //private Long typeNo;

    /**
     * Typekey
     */
    //@Column(name = "TYPE_PID")
    private Long pId;

    /**
     * name
     */
    private String pTypeName;

    //private List<FeeType> feeTypeDetail;
    private String sTypeName;

    private  Long sTypeNo;




}
