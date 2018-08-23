package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.medicaljournal.model.FeeType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Seaton
 * @Date 2018/4/22
 */
@Mapper
public interface FeeTypeMapper  {
    List<FeeType> findFeeType();
}
