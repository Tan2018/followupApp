package com.ry.fu.net.common.utils;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtils {



    /**
     *读取当前日期
     */
    public static DateTime getCurDate() {
        return new DateTime();
    }
    public static Date getCurrentDate() {
        return new DateTime().toDate();
    }

    public static DateTime getCurrentTime() {
        return new DateTime(new DateTime());
    }

    public static String getAge(DateTime startDateTime, DateTime endDateTime) {

        LocalDate start = new LocalDate(startDateTime);
        LocalDate end = new LocalDate(endDateTime);
        Days days = Days.daysBetween(start, end);
        int intervalDays = days.getDays();
        /**
        *出生当年的天数
        */
        int startYears=startDateTime .dayOfYear().getMaximumValue();
        /**
         *出生当月的天数
         */
        int startMonths=startDateTime .dayOfMonth().getMaximumValue();
        String strAge = "";
        if (intervalDays >= startYears) {
            Years age = Years.yearsBetween(startDateTime, endDateTime);
            Integer intAge = age.getYears();
            strAge = intAge.toString() + "岁";
        } else if (intervalDays < startYears && intervalDays >=startMonths) {
            Months age = Months.monthsBetween(startDateTime, endDateTime);
            Integer intAge = age.getMonths() + 1;
            strAge = intAge.toString() + "个月";
        } else if (intervalDays < startDateTime .dayOfMonth().getMaximumValue()) {
            Days age = Days.daysBetween(startDateTime, endDateTime);
            Integer intAge = age.getDays()+1;
            strAge = intAge.toString() + "天";
        }
        return strAge;
    }
    public static int getDays(DateTime startDateTime, DateTime endDateTime) {
        LocalDate start = new LocalDate(startDateTime);
        LocalDate end = new LocalDate(endDateTime);
        Days days = Days.daysBetween(start, end);
        int day = days.getDays();
        return day ;
    }
    public static int getHours(DateTime startDateTime, DateTime endDateTime) {
        LocalDate start = new LocalDate(startDateTime);
        LocalDate end = new LocalDate(endDateTime);
        Hours hours = Hours.hoursBetween(start, end);
        int hour = hours.getHours();
        return hour ;

    }
    public static DateTime getSdfDate(String time){
        DateTimeFormatter sdfDay = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime = DateTime.parse(time,sdfDay);
        return dateTime;
    }
    public static DateTime getSdfTime(String time){
        DateTimeFormatter sdfTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = DateTime.parse(time,sdfTime);
        return dateTime;
    }

}
