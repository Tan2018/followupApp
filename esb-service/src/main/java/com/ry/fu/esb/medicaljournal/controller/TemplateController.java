package com.ry.fu.esb.medicaljournal.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.medicaljournal.service.TemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author jane
 * @Date 2018/7/19
 */
@RestController
@RequestMapping("${prefixPath}/v1/medicalJournal/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = "/selectTemplateTypeList", method = RequestMethod.POST)
    public ResponseData selectTemplateTypeList() {

        return templateService.selectTemplateTypeList();
    }

    @RequestMapping(value = "/selectTemplateList", method = RequestMethod.POST)
    public ResponseData selectTemplateList(@RequestBody Map<String, Long> map) {
        if (map.get("typeId") == null || StringUtils.isBlank(map.get("typeId").toString())) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        Long typeId = map.get("typeId");
        return templateService.selectTemplateList(typeId);
    }
}
