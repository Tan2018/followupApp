package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.medicaljournal.mapper.PushListMapper;
import com.ry.fu.esb.medicaljournal.model.PushList;
import com.ry.fu.esb.medicaljournal.service.PushListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushListServiceImpl implements PushListService {

    @Autowired
    private PushListMapper pushListMapper;

    @Override
    public String selectByPrimaryKey(String openId,Integer pageNum,Integer pageSize) {

        List<PushList> list = pushListMapper.selectByPrimaryAlias(openId,(pageNum+1),pageSize);

        return JsonUtils.toJSon(list);
    }
}
