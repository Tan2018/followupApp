package com.ry.fu.esb.medicaljournal.util;

/**
 * 检查类型
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-15 19:15
 **/
public class CheckType {
    public static String getCheckType(String type) {
        String checkType = "";
        if (type.equals("1")) {
            checkType = "MR";
        } else if (type.equals("2")) {
            checkType = "X光";
        } else if (type.equals("3")) {
            checkType = "CT";
        } else if (type.equals("4")) {
            checkType = "超声";
        } else if (type.equals("5")) {
            checkType = "内镜";
        } else if (type.equals("6")) {
            checkType = "核医学";
        } else if (type.equals("7")) {
            checkType = "心电图";
        } else if (type.equals("8")) {
            checkType = "病理资料";
        } else if (type.equals("9")) {
            checkType = "ALO";
        } else if (type.equals("10")) {
            checkType = "ALO-随医拍";
        } else if (type.equals("99")) {
            checkType = "其他";
        }
        return checkType;

    }
}
