package com.ry.fu.esb.medicaljournal.enums;

/**
 * @author xuxu
 * @version V1.0.0
 * @Date date 2018/04/16 17:34
 * @description 订单状态枚举
 */
public enum PayStatusEnum {
    CREADED("0","订单创建成功"),
    PAYING("1","订单支付中"),
    PAYED("2","订单已支付"),
    PAYFAIL("3","订单支付失败"),
    REFUNDING("4","订单退费中"),
    REFUNDED("5","订单已退费"),
    REFUNDFAIL("6","订单退费失败"),
    REVERSING("7","订单冲正中"),
    REVERSALED("8","订单已冲正"),
    REVERSEFAIL("9","订单冲正失败");

    private String code;
    private String msg;

    private PayStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //根据枚举的code获取msg的方法
    public static String getMsgByCode(String code){
        for(PayStatusEnum responseEnum : PayStatusEnum.values()) {
            if(responseEnum.getCode().equals(code)){
                return responseEnum.msg;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
