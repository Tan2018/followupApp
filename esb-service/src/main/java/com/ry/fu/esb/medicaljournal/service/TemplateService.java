package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.response.ResponseData;

/**
 * @Author jane
 * @Date 2018/7/19
 */
public interface TemplateService {
    ResponseData selectTemplateList(Long typeId);
    ResponseData selectTemplateTypeList();
}
