package com.ry.fu.esb.doctorbook.model.response;

import java.io.Serializable;

/**
 * @author ：Boven
 * @Description ：患者列表
 * @create ： 2018-03-12 17:01
 **/
public class PatientListResponeRecord  implements Serializable{
    private static final long serialVersionUID = -8910012200831645891L;

    /**
     *患者ID
     */
    private String patientID;
    /**
     *患者姓名
     */
    private String name;
    /**
     *医生姓名
     */
    private String doctorName;
    /**
     *性别
     */
    private String sex;
    /**
     *年龄
     */
    private String age;
    /**
     *住院的床号
     */
    private String bed;
    /**
     *住院多少天
     */
    private String hospitalDay;
    /**
     *手术后多少天
     */
    private String postoperativeDay;
    /**
     *是否已受理 0.	未受理1.	已受理
     */
    private String isAccept;
}
