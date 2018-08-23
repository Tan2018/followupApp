package com.ry.fu.followup.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtils {


    /**
     * 读取当前日期
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
        int startYears = startDateTime.dayOfYear().getMaximumValue();
        /**
         *出生当月的天数
         */
        int startMonths = startDateTime.dayOfMonth().getMaximumValue();
        String strAge = "";
        if (intervalDays >= startYears) {
            Years age = Years.yearsBetween(startDateTime, endDateTime);
            Integer intAge = age.getYears();
            strAge = intAge.toString() + "岁";
        } else if (intervalDays < startYears && intervalDays >= startMonths) {
            Months age = Months.monthsBetween(startDateTime, endDateTime);
            Integer intAge = age.getMonths() + 1;
            strAge = intAge.toString() + "个月";
        } else if (intervalDays < startDateTime.dayOfMonth().getMaximumValue()) {
            Days age = Days.daysBetween(startDateTime, endDateTime);
            Integer intAge = age.getDays() + 1;
            strAge = intAge.toString() + "天";
        }
        return strAge;
    }

    public static int getDays(DateTime startDateTime, DateTime endDateTime) {
        if (startDateTime != null && endDateTime != null) {
            LocalDate start = new LocalDate(startDateTime);
            LocalDate end = new LocalDate(endDateTime);
            Days days = Days.daysBetween(start, end);
            int day = days.getDays();
            return day;
        }

        return -1;
    }

    public static int getHours(DateTime startDateTime, DateTime endDateTime) {

        if (startDateTime != null && endDateTime != null) {
            LocalDate start = new LocalDate(startDateTime);
            LocalDate end = new LocalDate(endDateTime);
            Hours hours = Hours.hoursBetween(start, end);
            int hour = hours.getHours();
            return hour;
        }

        return -1;

    }

    public static DateTime getSdfDate(String time) {
        if (StringUtils.isBlank(time)) {
            return new DateTime();
        }
        //时区
        DateTimeFormatter sdfDay = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.forID("+08:00"));
        DateTime dateTime = DateTime.parse(time, sdfDay);
        return dateTime;
    }

    public static DateTime getSdfTime(String time) {
        if (StringUtils.isBlank(time)) {
            return new DateTime();
        }
        //时区
        DateTimeFormatter sdfTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.forID("+08:00"));
        DateTime dateTime = DateTime.parse(time, sdfTime);
        return dateTime;
    }

    public static Date Dateparse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }

    //由出生日期获得年龄
    public static int getDateToAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;

    }
    public static String countAge(Date from){
        String age = "";
        int year = 0;
        int month = 0;
        int day = 0;
        if(from == null){
            age = age + 0;
        } else {
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(from);
            c2.setTime(new Date());
            if (c1.after(c2)) {
                throw new IllegalArgumentException("生日不能超过当前日期");
            }
            int from_year = c1.get(Calendar.YEAR);
            int from_month = c1.get(Calendar.MONTH)+1;
            int from_day = c1.get(Calendar.DAY_OF_MONTH);
//           System.out.println("以前："+from_year + "-" + from_month + "-" + from_day);
            int MaxDayOfMonth = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
            //System.out.println(MaxDayOfMonth);
            int to_year = c2.get(Calendar.YEAR);
            int to_month = c2.get(Calendar.MONTH)+1;
            int to_day = c2.get(Calendar.DAY_OF_MONTH);
//           /**/ System.out.println("现在："+to_year+"-"+to_month+"-"+to_day);

            year = to_year - from_year;
            if(c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) < 0){
                year = year -1;
            }
            if(year < 1){// 小于一岁要精确到月份和天数
                System.out.println("--------");
                if(to_month - from_month>0){
                    month = to_month -from_month;
                    if(to_day - from_day < 0){
                        month = month - 1;
                        day = to_day -from_day + MaxDayOfMonth;
                    } else {
                        day= to_day -from_day;
                    }
                }else if(to_month - from_month==0){
                    if(to_day - from_day > 0){
                        day = to_day -from_day;
                    }
                }
            }
            if(year > 1){
                age = age + year + "岁";
            }else if(month > 0) {
                age = age + month + "个月" + day + "天";
            } else {
                age=age + day + "天";
            }
        }
        //System.out.println(year + "--" + month + "--" + day);
        return age;
    }
    public static Date parseDate(String strDate) throws ParseException {
        if (StringUtils.isNotBlank(strDate)){
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd " );
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
            return  date;
        }

        return  new Date();

    }

}