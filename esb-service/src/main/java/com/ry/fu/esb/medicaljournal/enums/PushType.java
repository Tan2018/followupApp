package com.ry.fu.esb.medicaljournal.enums;

public enum PushType {

    UNPAID("待支付",1),
    REGISTERSUCCESS("挂号成功",2),
    PAYSUCCESS("缴费成功",3),
    APPOINTMENT("就诊通知",4),
    PUSHTHEITEM("催交款",5),
    REFUNDSUCCESS("退费成功",6),
    REFUNDFAILURE("退费失败",7),
    FUSURVEY("随访调查",41),
    FUGROUPAPPLICATION("随访项目审核",51),
    FUEXAMINATION("随访复诊通知",52),
    CRITICALVALUENOTICE("危急值通知",61);

    private String name ;
    private int index ;

    private PushType(String name , int index ){
        this.name = name ;
        this.index = index ;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
