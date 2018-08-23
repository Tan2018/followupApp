package com.ry.fu.esb.medicaljournal.util;

/**
 * 住院患者状态显示
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-19 20:14
 **/
public class InpatientDayStatus {
    public static Integer getInpatientDayStatus(int days) {
          int  inpatientDayStatus=0;
       if (days==1){
           inpatientDayStatus=1;
       }else  if (1<days&&days<=24){
           inpatientDayStatus=2;
       }else {
           inpatientDayStatus=3;
       }
        return inpatientDayStatus;
    }

}
