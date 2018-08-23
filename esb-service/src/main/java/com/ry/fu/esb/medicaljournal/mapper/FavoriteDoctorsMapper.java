package com.ry.fu.esb.medicaljournal.mapper;

import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.medicaljournal.model.DoctorInfo;
import com.ry.fu.esb.medicaljournal.model.FavoriteDoctor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Seaton
 * @Date 2018/3/14
 */
@Mapper
public interface FavoriteDoctorsMapper extends BaseMapper<FavoriteDoctor> {
    List<DoctorInfo> findFavoriteDoctor(Long accountId);
}
