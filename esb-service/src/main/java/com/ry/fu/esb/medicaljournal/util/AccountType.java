package com.ry.fu.esb.medicaljournal.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 检查类型
 *
 * @author ：Joker
 * @Description ：
 * @create ： 2018-06-09 17:23
 **/
public class AccountType {
    public static String getSpecialControlFlag(String type) {
        if (StringUtils.isNotBlank(type)) {
            if (type.contains("【试算失败GetRegisterInfoForMCRegister错误上传疾病编码不能为空】")) {
                return "1";
            } else if (type.contains("疾病编码错误")) {
                return "1";
            } else if (type.contains("疾病编码不能为空")) {
                return "1";
            } else if (type.contains("错误上传疾病编码不能为空")) {
                return "1";
            } else if (type.contains("ICD编码错误")) {
                return "1";
            } else if (type.contains("医保结果的自负金额小于0")) {
                return "2";
            } else {
                return "3";
            }
        }
        return "3";
    }
}
