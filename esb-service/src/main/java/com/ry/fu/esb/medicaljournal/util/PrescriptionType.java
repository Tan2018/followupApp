package com.ry.fu.esb.medicaljournal.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 检查类型
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-15 19:15
 **/
public class PrescriptionType {
    public static String getSpecialControlFlag(String type) {
        String specialControlFlag = "";
        if (StringUtils.isNotBlank(type)){
            if(type.equals("0")){
                specialControlFlag = "普通药物";
            }
            else if (type.equals("1")) {
                specialControlFlag = "麻醉药";
            } else if (type.equals("2")) {
                specialControlFlag = "毒性药品";
            } else if (type.equals("3")) {
                specialControlFlag = "第一类精神类药物";
            } else if (type.equals("4")) {
                specialControlFlag = "第二类精神类药物";
            } else if (type.equals("5")) {
                specialControlFlag = "未知";
            } else if (type.equals("6")) {
                specialControlFlag = "剧药";
            } else if (type.equals("7")) {
                specialControlFlag = "非限制使用的抗菌药";
            } else if (type.equals("8")) {
                specialControlFlag = "限制使用的抗菌药";
            } else if (type.equals("9")) {
                specialControlFlag = "特殊使用的抗菌药物";
            } else if (type.equals("10")) {
                specialControlFlag = "含兴奋剂药品";
            } else if (type.equals("11")) {
                specialControlFlag = "高危药品";
            }else if (type.equals("12")) {
                specialControlFlag = "限制使用的抗菌药";
            }else if (type.equals("13")) {
                specialControlFlag = "特殊使用的抗菌药物";
            }else if (type.equals("14")) {
                specialControlFlag = "辅助用药";
            }else if (type.equals("15")) {
                specialControlFlag = "营养药";
            }else {
                specialControlFlag = "其他";
            }
        }

        return specialControlFlag;
    }
    public static String getPrescriptionType(String type) {
        String PescriptionType = "";
        if (StringUtils.isNotBlank(type)){
            if (type.equals("1")) {
                PescriptionType = "西药";
            } else if (type.equals("2")) {
                PescriptionType = "中成药";
            } else if (type.equals("3")) {
                PescriptionType = "中草药";
            } else if (type.equals("4")) {
                PescriptionType = "用药方式";
            } else if (type.equals("5")) {
                PescriptionType = "材料";
            } else if (type.equals("6")) {
                PescriptionType = "项目";
            } else if (type.equals("7")) {
                PescriptionType = "技诊费";
            } else if (type.equals("8")) {
                PescriptionType = "标本";
            } else if (type.equals("9")) {
                PescriptionType = "容器";
            } else if (type.equals("10")) {
                PescriptionType = "手术";
            } else if (type.equals("11")) {
                PescriptionType = "中草药煎法";
            }else {
                PescriptionType = "其他";
            }
        }
        return PescriptionType;
    }


}
