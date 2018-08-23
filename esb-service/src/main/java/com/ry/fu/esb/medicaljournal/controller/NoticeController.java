package com.ry.fu.esb.medicaljournal.controller;


import com.github.pagehelper.PageHelper;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.utils.ResponseUtil;
import com.ry.fu.esb.medicaljournal.mapper.PushListMapper;
import com.ry.fu.esb.medicaljournal.model.PushList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @Author:Seaton
 * @Description:
 * @create: 2018/5/2 16:02
 **/
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/notice")
public class NoticeController {
    private static Logger logger = LoggerFactory.getLogger(NoticeController.class);


    @Autowired
    private PushListMapper pushListMapper;

    @RequestMapping(value = "/notice",method =  RequestMethod.POST)
    public ResponseData notice(@RequestBody Map<String,String> map) {
        Example example = new Example(PushList.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("alias",map.get("accountId"));
        /*if(map.get("pushType")!=null && "41".equals(map.get("pushType").toString())){
            criteria.andEqualTo("pushType",map.get("pushType").toString());
            criteria.andEqualTo("patientId",map.get("patientId").toString());
        }*/
        criteria.andEqualTo("patientId",map.get("patientId"));
        example.setOrderByClause("PUSH_TIME DESC");
        PageHelper.startPage(Integer.parseInt(map.get("pageNum")),Integer.parseInt(map.get("pageSize")));
        List<PushList> pushLists = pushListMapper.selectByExample(example);
        logger.info(pushLists.toString());
        return ResponseUtil.getSuccessResultBean(pushLists);
    }

}
