package com.ry.fu.esb.medicaljournal.model.response;

import java.io.Serializable;
import java.util.List;

public class DeptFirstLevelTree implements Serializable {
    private static final long serialVersionUID = 2090045200866063489L;

    private Integer id;
    private String name;
    private List<DeptSecondLevelTree> secondLevelTreeList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DeptSecondLevelTree> getSecondLevelTreeList() {
        return secondLevelTreeList;
    }

    public void setSecondLevelTreeList(List<DeptSecondLevelTree> secondLevelTreeList) {
        this.secondLevelTreeList = secondLevelTreeList;
    }
}
