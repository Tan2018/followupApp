package com.ry.fu.esb.medicaljournal.model.response;

import java.io.Serializable;
import java.util.List;

public class DeptTree implements Serializable {
    private static final long serialVersionUID = 2090045200866063489L;

    private List<DeptFirstLevelTree> deptFirstLevelTreeList;

    public List<DeptFirstLevelTree> getDeptFirstLevelTreeList() {
        return deptFirstLevelTreeList;
    }

    public void setDeptFirstLevelTreeList(List<DeptFirstLevelTree> deptFirstLevelTreeList) {
        this.deptFirstLevelTreeList = deptFirstLevelTreeList;
    }
}
