package com.ry.fu.esb.medicaljournal.service.impl;

import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.mapper.TemplateMapper;
import com.ry.fu.esb.medicaljournal.model.response.TemplateResponse;
import com.ry.fu.esb.medicaljournal.model.response.TemplateTypeResponse;
import com.ry.fu.esb.medicaljournal.service.TemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author jane
 * @Date 2018/7/19
 */
@Service
public class TemplateServiceImpl implements TemplateService{

    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public ResponseData selectTemplateList(Long typeId) {
        List<TemplateResponse> template =templateMapper.selectTemplateList(typeId);
        if (template.size() == 0) {
            return new ResponseData("200", "没有模板列表", template);
        }
        //截取日期格式
        for (int i = 0; i < template.size(); i++) {
            //创建时间不为空且长度大于16
            if (StringUtils.isNotBlank(template.get(i).getCreateTime()) && template.get(i).getCreateTime().length() > 16) {
                String creatTime = template.get(i).getCreateTime().substring(0, 16);
                template.get(i).setCreateTime(creatTime);
            }
        }
        return new ResponseData("200", "请求成功", template);
    }

    @Override
    public ResponseData selectTemplateTypeList() {
        List<TemplateTypeResponse> typeList = templateMapper.selectTemplateTypeList();
        if (typeList.size() == 0) {
            return new ResponseData("200", "没有分类列表", typeList);
        }
        //截取日期格式
        for (int i = 0; i < typeList.size(); i++) {
            //创建时间不为空且长度大于16
            if (StringUtils.isNotBlank(typeList.get(i).getCreateTime()) && typeList.get(i).getCreateTime().length() > 16) {
                String creatTime = typeList.get(i).getCreateTime().substring(0, 16);
                typeList.get(i).setCreateTime(creatTime);
            }
        }
        return new ResponseData("200", "请求成功", typeList);
    }


}
