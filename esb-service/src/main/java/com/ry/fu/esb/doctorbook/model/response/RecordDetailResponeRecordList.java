package com.ry.fu.esb.doctorbook.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * 信息详情列表
 *
 * @author ：Boven
 * @Description ：
 * @create ： 2018-03-12 17:31
 **/
public class RecordDetailResponeRecordList implements Serializable {
    /**
    *医生姓名
    */
   private String doctorName;
   /**
   *数据时间
   */
   private String dataTime;
   /**
   *图片信息列表
   */
   private List<String>  imageList;
   /**
   *语音信息列表
   */
   private List<String> voiceList;
    /**
    *信息描述
    */
   private String description;

}
