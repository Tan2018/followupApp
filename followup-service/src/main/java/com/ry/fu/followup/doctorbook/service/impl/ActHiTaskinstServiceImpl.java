package com.ry.fu.followup.doctorbook.service.impl;

import com.ry.fu.followup.base.model.Pagenation;
import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.mapper.ActHiTaskinstMapper;
import com.ry.fu.followup.doctorbook.mapper.GroupMapper;
import com.ry.fu.followup.doctorbook.model.ActHiTaskinst;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import com.ry.fu.followup.doctorbook.service.ActHiTaskinstService;
import com.ry.fu.followup.utils.ResponseMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tasher on 2018/06/19.
 *
 */

@Service
public class ActHiTaskinstServiceImpl implements ActHiTaskinstService {
    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    @Autowired
    private ActHiTaskinstMapper actHiTaskinstMapper;
    @Autowired
    private GroupMapper groupMapper;

    /**
     * 根据传入的ProjectId查询出审批历史数据。
     * @param reqInfo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseData queryActHiTaskinstctByProsId(ReqInfo reqInfo){
        Long projectId = reqInfo.getProjectId();
        Long pageSize = reqInfo.getPageSize();
        Long pageNumber = reqInfo.getPageNumber();
        Long proc_inst_id_ = groupMapper.getInstanceIdByProjectId(projectId);
        Integer totalCount = actHiTaskinstMapper.getActHiTaskinstctCountByInstId(proc_inst_id_);
        Pagenation pagenation = new Pagenation(pageNumber.intValue(), totalCount, pageSize.intValue());
        List<ActHiTaskinst>  ahts = actHiTaskinstMapper.queryActHiTaskinstByInstid(proc_inst_id_,
                pagenation.getStartRow().longValue(),pagenation.getEndRow().longValue());
        Map<String, Object> innerDataMap = new HashMap<>();
        List<Map<String ,Object>> list = new ArrayList<>();
        for(int i = 0 ;i< ahts.size();i++){
            ActHiTaskinst aht = ahts.get(i);
            Map<String ,Object> map = new HashMap<>();
            String id_= aht.getId_()==null?"":aht.getId_();
            String name_ = aht.getName_()==null?"":aht.getName_();
            String assignee_ = aht.getAssignee_()==null?"":aht.getAssignee_();
            String description_ = aht.getDescription_()==null?"":aht.getDescription_();
            //显示每个任务的处理结果。
            String delete_reason_ = aht.getDelete_reason_();
            String start_time_ = aht.getStart_time_()==null?"":sdf.format(aht.getStart_time_());
            String end_time_ = aht.getEnd_time_()==null?"":sdf.format(aht.getEnd_time_());
            String status_ = "wait";

            if("".equals(end_time_)) {//如果结束时间为空，则是等待审核
                delete_reason_ = ActHiTaskinst.WAIT;
                status_ = "wait";
            }else{
                if("completed".equalsIgnoreCase(delete_reason_)){//如果是completed，则为通过
                    delete_reason_ = ActHiTaskinst.COMPLETED;
                    status_ = "completed";
                }else if("deleted".equalsIgnoreCase(delete_reason_)){//如果是删除
                    delete_reason_ = ActHiTaskinst.DELETED;
                    status_ = "deleted";
                }else {//否则为驳回
                    delete_reason_ = ActHiTaskinst.REJECT;
                    status_ = "reject";
                }
            }
            //封装数据。
            map.put("id_",id_);//任务id
            map.put("name_",name_);//任务名称
            map.put("assignee_",assignee_);//接收人
            map.put("proc_inst_id_",proc_inst_id_);//流程实例ID
            map.put("start_time_",start_time_);
            map.put("end_time_",end_time_);
            map.put("message_",description_);//利用description_字段储存act_hi_comment表的message_的值
            map.put("status_",status_);//状态
            map.put("delete_reason_",delete_reason_);//状态文字
            list.add(map);
        }
        innerDataMap.put("total", pagenation.getTotalPage());
        innerDataMap.put("list", list);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }
}


