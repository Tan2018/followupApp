package com.ry.fu.esb.doctorbook.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 *
 *
 * @author ：Boven
 * @Description ：影像响应实体
 * @create ： 2018-03-12 11:09
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = false)
public class GetImageRecordImage implements Serializable{


    private static final long serialVersionUID = 3716070441472595570L;

   /**
    图片来源1、pacs，可扩展
     */
    @XmlElement(name = "IMAGESOURCE")
    private String imageSource;

     /**
     * * 图片角度1、侧面、2正面等等
     */
    @XmlElement(name = "IMAGESIDE")
    private String imageSide;
    /**
            *图片路径
    */
    @XmlElement(name = "IMAGEFILEPATH")
    private String imagepath;






}
