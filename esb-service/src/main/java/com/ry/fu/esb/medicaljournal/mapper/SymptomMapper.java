package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.medicaljournal.model.Part;
import com.ry.fu.esb.medicaljournal.model.PartPic;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @Author Seaton
 * @Date 2018/3/14
 */
@Mapper
public interface SymptomMapper  {

   ArrayList<Part> findSymptom(String sex);

   PartPic getPartPic(String id);

}
