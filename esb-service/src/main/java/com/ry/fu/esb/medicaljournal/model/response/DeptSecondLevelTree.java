package com.ry.fu.esb.medicaljournal.model.response;

import java.io.Serializable;
import java.util.List;

public class DeptSecondLevelTree implements Serializable {
    private static final long serialVersionUID = 2090045200866063489L;

    private Integer id;
    private String name;
    private List<DeptThirdLevelTree> thirdLevelTreeList;

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

    public List<DeptThirdLevelTree> getThirdLevelTreeList() {
        return thirdLevelTreeList;
    }

    public void setThirdLevelTreeList(List<DeptThirdLevelTree> thirdLevelTreeList) {
        this.thirdLevelTreeList = thirdLevelTreeList;
    }
}
