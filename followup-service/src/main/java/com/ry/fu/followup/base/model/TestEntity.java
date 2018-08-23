package com.ry.fu.followup.base.model;

import java.util.List;
import java.util.Map;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 18:22 2018/1/29
 */
public class TestEntity {
    private String name;
    private Integer age;
    private List<String> list;
    private Boolean bool;
    private Map<String,List<String>> map;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List <String> getList() {
        return list;
    }

    public void setList(List <String> list) {
        this.list = list;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public Map<String,List<String>> getMap() {
        return map;
    }

    public void setMap(Map<String,List<String>> map) {
        this.map = map;
    }
}
