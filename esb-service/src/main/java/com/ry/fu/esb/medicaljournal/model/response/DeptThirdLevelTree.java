package com.ry.fu.esb.medicaljournal.model.response;

import java.io.Serializable;

public class DeptThirdLevelTree implements Serializable {
    private static final long serialVersionUID = 2090045200866063489L;

    private Integer id;
    private String name;

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
}
