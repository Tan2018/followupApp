package com.ry.fu.esb.medicaljournal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @Author Seaton
 * @Date 2018/3/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "M_SYMPTOM")
public class Symptoms implements Serializable {

    private static final long serialVersionUID = 363286261777974668L;
    @Id
    @Column(name = "id")
    private Long id;


    /**
     * 症状名称
     */
    private String name;

    /**
     * 症状对应科室
     */
    @JsonIgnore
    private Department depts;

   @Transient
   private List<String> deptsList;

    public List<String> getDeptsList() {
        if(depts.getDeptId() !=null &&depts.getDeptId() !="") {
            return java.util.Arrays.asList(depts.getDeptId().split(","));
        }else {
            return null;
        }
    }

    public void setDeptsList(List<String> deptsList) {
        this.deptsList =deptsList;
    }

}