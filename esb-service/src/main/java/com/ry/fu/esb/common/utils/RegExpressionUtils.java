package com.ry.fu.esb.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Howard
 * @version V1.0.0
 * @date 2018/4/18 9:44
 * @description
 */
public class RegExpressionUtils {
    //判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }
}
