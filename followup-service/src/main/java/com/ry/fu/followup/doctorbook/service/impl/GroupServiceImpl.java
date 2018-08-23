package com.ry.fu.followup.doctorbook.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ry.fu.followup.base.model.Pagenation;
import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.config.UrlConfig;
import com.ry.fu.followup.doctorbook.controller.NotificationController;
import com.ry.fu.followup.doctorbook.mapper.*;
import com.ry.fu.followup.doctorbook.model.*;
import com.ry.fu.followup.doctorbook.service.GroupService;
import com.ry.fu.followup.doctorbook.service.NotificationService;
import com.ry.fu.followup.pwp.mapper.AccountMapper;
import com.ry.fu.followup.pwp.model.Account;
import com.ry.fu.followup.utils.ResponseMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Jam on 2017/12/1.
 *
 */

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private PurposeMapper purposeMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @Autowired
    private GroupMemberMapper groupMemberMapper;

    @Autowired
    private GroupProgramFieldMapper groupProgramFieldMapper;

    @Autowired
    private GroupConditionMapper groupConditionMapper;

    @Autowired
    private GroupPurposeMapper groupPurposeMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PwpOrgMapper pwpOrgMapper;

    @Autowired
    private  GroupConditionexPressionMapper groupConditionexPressionMapper;

    @Autowired
    private FufileMapper fufileMapper;
    
    @Autowired
    private GroupRecordMapper groupRecordMapper;

    @Value("${followupPcServerHostAndPort}")
    private String followupPCServerHostAndPort;

    @Autowired
    private  UrlConfig urlConfig;

    @Transactional(readOnly = true)
    @Override
    public ResponseData queryProjectList(ReqInfo reqInfo) {
        Long doctortId = reqInfo.getDoctorId();
        long status = 2L;
        Long pageNumber = reqInfo.getPageNumber();
        Long pageSize = reqInfo.getPageSize();
        List<Map<String, Object>> groupList = new ArrayList<>();
        Integer totalCount = groupMapper.getGroupIdCountByDoctorIdAndStatus(reqInfo.getDoctorId(),status);
        if (totalCount == 0) {
            Map<String, Object> innerDataMap = new HashMap<>();
            innerDataMap.put("total",0);
            innerDataMap.put("list",groupList);
            return ResponseMapUtil.getSuccessResultMap(innerDataMap);
        }
        Pagenation pagenation = new Pagenation(pageNumber.intValue(),totalCount,pageSize.intValue());
        List<Map<String, Object>> lists = groupMapper.queryGroupIdByDoctorIdAndStatusByPage(doctortId,status, pagenation.getStartRow().longValue(), pagenation.getEndRow().longValue());

        List<Long> groupIdList = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            Map<String, Object> groupIdMap = lists.get(i);
            BigDecimal groupId = (BigDecimal) groupIdMap.get("GROUP_ID");
            groupIdList.add(groupId.longValue());
        }

        List<Group> groups = groupMapper.selectGroups(groupIdList,status,null);
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id",group.getId());
            map.put("project",group.getProject());
            map.put("projectType",group.getGroupType());
            Integer manageType;
            if (group.getManageType() == 2) {
                manageType = 1;
            } else {
                manageType = 0;
            }
            map.put("center",manageType);
            map.put("number",group.getRecordNo());
            groupList.add(map);
        }
        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("total",pagenation.getTotalPage());
        innerDataMap.put("list",groupList);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseData queryApplicationList(ReqInfo reqInfo) {
        Long status = reqInfo.getStatus();
        List<Map<String, Object>> groupList = new ArrayList<>();
        /*Integer totalCount = groupMapper.getGroupIdCountByApplDoctorIdAndStatus(reqInfo.getDoctorId(), status);
        if (totalCount == 0) {
            Map<String, Object> innerDataMap = new HashMap<>();
            innerDataMap.put("total",0);
            innerDataMap.put("list",groupList);
            return ResponseMapUtil.getSuccessResultMap(innerDataMap);
        }*/
        Integer totalCount = groupMapper.selectDoctorGroupsCount(reqInfo.getDoctorId(), status);
        Long pageNumber = reqInfo.getPageNumber();
        Long pageSize = reqInfo.getPageSize();
        Pagenation pagenation = new Pagenation(pageNumber.intValue(),totalCount,pageSize.intValue());
        /*List<Map<String, Object>> lists = queryApplGroupIdList(reqInfo, pagenation);
        List<Long> groupIdList = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            Map<String, Object> groupIdMap = lists.get(i);
            BigDecimal groupId = (BigDecimal) groupIdMap.get("GROUP_ID");
            groupIdList.add(groupId.longValue());
        }
        List<Group> groups = groupMapper.selectGroups(groupIdList,status,null);*/

        List<Group> groups = groupMapper.selectDoctorGroups(reqInfo.getDoctorId(), status,
                pagenation.getStartRow().longValue(),pagenation.getEndRow().longValue());
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id",group.getId());
            map.put("project",group.getProject());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatter.format(group.getCreateTime());
            map.put("date",date);
            groupList.add(map);
        }
        Map<String, Object> innerDataMap = new HashMap<String, Object>();
        innerDataMap.put("total",pagenation.getTotalPage());
        innerDataMap.put("status",status);
        innerDataMap.put("list",groupList);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseData queryNewReviewList(ReqInfo reqInfo) {

        Long status = reqInfo.getStatus();
        Integer totalCount =0;
        if(status==1L){
            totalCount = groupMapper.queryCountTask(reqInfo.getDoctorId(), status);
        }else if(status==2L){
            totalCount = groupMapper.queryCountPassTask(reqInfo.getDoctorId(), status);
        }else if((status==3L)){
            totalCount = groupMapper.queryCountNotPassTask(reqInfo.getDoctorId(), status);
        }
        List<Group> groupList = new ArrayList<>();
        if (totalCount == 0) {
            Map<String, Object> innerDataMap = new HashMap<>();
            innerDataMap.put("total",0);
            innerDataMap.put("list",groupList);
            return ResponseMapUtil.getSuccessResultMap(innerDataMap);
        }
        Long pageNumber = reqInfo.getPageNumber();
        Long pageSize = reqInfo.getPageSize();
        Pagenation pagenation = new Pagenation(pageNumber.intValue(),totalCount,pageSize.intValue());
        //查询任务列表
        List<Group> lists = new ArrayList<Group>();
        if(status==1L){
            lists = groupMapper.queryGroupTask(reqInfo.getDoctorId(), status, pagenation.getStartRow().longValue(), pagenation.getEndRow().longValue());
        }else if(status==2L){
            lists = groupMapper.queryPassGroupTask(reqInfo.getDoctorId(), status, pagenation.getStartRow().longValue(), pagenation.getEndRow().longValue());
        }else if((status==3L)){
            lists = groupMapper.queryNotPassGroupTask(reqInfo.getDoctorId(), status, pagenation.getStartRow().longValue(), pagenation.getEndRow().longValue());
        }

        for(int i = 0; i < lists.size(); i++){
            Group groupTask = lists.get(i);
            List<Purpose> purposes = purposeMapper.queryPurposeByGroupId(groupTask.getId());
            StringBuffer purposeTexts = new StringBuffer("");
            for (int j = 0; j < purposes.size(); j++) {
                Purpose purpose = purposes.get(j);
                String purposeText = purpose.getPurposeText();
                if (j != 0) {
                    purposeTexts.append("，");
                }
                purposeTexts.append(purposeText);
            }
            Map<String, Object> infoMap = new HashMap<>();
            // 查询申请人姓名
            Long createUserId = groupTask.getCreateUser();
            Account account = accountMapper.findById(createUserId);
            if("1".equals((groupTask.getGroupType()+""))){
                infoMap.put("type","回顾性");
            }else if ("0".equals((groupTask.getGroupType()+""))){
                infoMap.put("type","前瞻性");
            }else if("2".equals((groupTask.getGroupType()+""))){
                infoMap.put("type","前瞻性+回顾性");
            }else if(groupTask.getGroupType() == null){
                infoMap.put("type","");
            }
            infoMap.put("applicant",account.getAccountName());
            infoMap.put("purpose",purposeTexts.toString());
            groupTask.setInfo(infoMap);
            groupList.add(groupTask);
        }


        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("total",pagenation.getTotalPage());
        innerDataMap.put("status",status);
        innerDataMap.put("list",groupList);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }


    @Transactional(readOnly = true)
    @Override
    public ResponseData queryReviewList(ReqInfo reqInfo) {
        Long status = reqInfo.getStatus();
        Integer totalCount = groupMapper.getGroupIdCountByDoctorIdAndStatus(reqInfo.getDoctorId(), status);
        List<Group> groupList = new ArrayList<>();
        if (totalCount == 0) {
            Map<String, Object> innerDataMap = new HashMap<>();
            innerDataMap.put("total",0);
            innerDataMap.put("list",groupList);
            return ResponseMapUtil.getSuccessResultMap(innerDataMap);
        }
        Long pageNumber = reqInfo.getPageNumber();
        Long pageSize = reqInfo.getPageSize();
        Pagenation pagenation = new Pagenation(pageNumber.intValue(),totalCount,pageSize.intValue());
        List<Map<String, Object>> lists = queryGroupIdList(reqInfo, pagenation);
        List<Long> groupIdList = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            Map<String, Object> groupIdMap = lists.get(i);
            BigDecimal groupId = (BigDecimal) groupIdMap.get("GROUP_ID");
            groupIdList.add(groupId.longValue());
        }

        List<Group> groups = groupMapper.selectGroups(groupIdList,status,null);
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            List<Purpose> purposes = purposeMapper.queryPurposeByGroupId(group.getId());
            StringBuffer purposeTexts = new StringBuffer("");
            for (int j = 0; j < purposes.size(); j++) {
                Purpose purpose = purposes.get(j);
                String purposeText = purpose.getPurposeText();
                if (j != 0) {
                    purposeTexts.append("，");
                }
                purposeTexts.append(purposeText);
            }
            Map<String, Object> infoMap = new HashMap<>();
            // 查询申请人姓名
            Long createUserId = group.getCreateUser();
            Account account = accountMapper.findById(createUserId);

            if("1".equals((group.getGroupType()+""))){
                infoMap.put("type","回顾性");
            }else if ("0".equals((group.getGroupType()+""))){
                infoMap.put("type","前瞻性");
            }else if("2".equals((group.getGroupType()+""))){
                infoMap.put("type","前瞻性+回顾性");
            }else if (group.getGroupType() == null){
                infoMap.put("type","");
            }


            infoMap.put("applicant",account.getAccountName());
            infoMap.put("purpose",purposeTexts.toString());
            group.setInfo(infoMap);
            groupList.add(group);
        }

        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("total",pagenation.getTotalPage());
        innerDataMap.put("status",status);
        innerDataMap.put("list",groupList);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData review(ReqInfo reqInfo) {
        Long projectId = reqInfo.getProjectId();
        Long status  = reqInfo.getStatus();
        ResponseEntity<HashMap> postForEntity = restTemplate.postForEntity(followupPCServerHostAndPort+ urlConfig.getFOLLOWUP_PC_REVIEW(), reqInfo, HashMap.class);
        String msg= "操作失败";
        ResponseData responseData = new ResponseData(500,msg,null);
        HashMap ret= postForEntity.getBody();

        if("200".equals(ret.get("status"))){
            responseData =new ResponseData(200,ret.get("msg").toString(),null);
        }else if("500".equals(ret.get("status"))){
            responseData = new ResponseData(500,ret.get("msg").toString(),null);
        }
        return responseData;
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseData queryProjectDetail(ReqInfo reqInfo) {
        //随访项目（表）

        Group group = groupMapper.selectGroup(reqInfo.getProjectId(), null, null);

        if(group!=null) {
            String gctp = groupConditionexPressionMapper.queryConditionStub(group.getId());

            //项目负责人（表）
            Account account = accountMapper.findById(group.getCreateUser());
            //病种表
            List<Disease> diseases = diseaseMapper.queryNameById(group.getDiseaseId());
            System.out.println(account.getOrgId());
            PwpOrg pwpOrg = pwpOrgMapper.queryOrgNameById(diseases.get(0).getOrgId());

            GroupMember groupMember = groupMemberMapper.queryMemberByGroupIdAndUserId(group.getId(),account.getAccountId());

            String leaderName = account.getAccountName();
            String leaderMmail = groupMember.getAccountEmail();
            String leaderMobile = groupMember.getAccountMobile();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String plannedExecTime="";
            String date="";
            String projectApprovalTime="";
            if(group.getCreateTime()!=null) {

                date = formatter.format(group.getCreateTime());
            }
            if(group.getPlannedExecTime()!=null) {

                plannedExecTime = formatter.format(group.getPlannedExecTime());
            }
            if(group.getProjectApprovalTime()!=null){

                 projectApprovalTime = formatter.format(group.getProjectApprovalTime());
            }
            String managementType = new String();
            Integer manageType = group.getManageType();
            switch (manageType) {
                case 0:
                    managementType = "临床研究中心";
                    break;
                case 1:
                    managementType = "GCP";
                    break;
                case 2:
                    managementType = "随访中心";
                    break;
                default:
                    break;
            }

            List<Purpose> purposes = purposeMapper.queryPurposeByGroupId(reqInfo.getProjectId());
            StringBuffer purposeTexts = new StringBuffer("");
            int size = purposes.size();
            for (int j = 0; j < size; j++) {
                Purpose purpose = purposes.get(j);
                String purposeText = purpose.getPurposeText();
                if (j != 0) {
                    purposeTexts.append("，");
                }
                purposeTexts.append(purposeText);
            }

            //添加附件下载功能
            Long projectId = reqInfo.getProjectId();
            List<Map<String,Object>> showPaths = new ArrayList<>();
            if (projectId !=null){
                List<Fufile> filePaths = fufileMapper.getFilePaths(projectId);

                if (filePaths != null && filePaths.size()>0){
                    for (Fufile fufile : filePaths) {
                    Map<String, Object>  fileMap = new HashMap<>();
                    fileMap.put("fileName",fufile.getFileName());
                    fileMap.put("filePath",fufile.getFilePath());
                    String fieldName = fufile.getFieldName();
                    if ("files1".equals(fieldName)){
                        fileMap.put("fieldName","研究方案");
                    }else if ("files2".equals(fieldName)){
                        fileMap.put("fieldName","同意开展的伦理批件");
                    }else{
                        fileMap.put("fieldName","其它附件");
                    }

                    String filePath = fufile.getFilePath();
                    if(filePath != null){

                    String fileType = filePath.substring(filePath.indexOf(".")+1);
                    fileMap.put("fileType",fileType);
                    }else {
                    fileMap.put("fileType",null);
                    }
                    showPaths.add(fileMap);
                    }
                }
            }

            Map<String, Object> innerObj = new HashMap<>();
            innerObj.put("filePaths",showPaths);
            innerObj.put("applyTime", date);
            innerObj.put("managementType", managementType);
            innerObj.put("flupType", group.getGroupType());
            innerObj.put("projectName", group.getProject());
            innerObj.put("projectLeader", leaderName);
            innerObj.put("phoneNumber", leaderMobile);
            innerObj.put("email", leaderMmail);
            innerObj.put("diseaseType", diseases.get(0).getName());
            innerObj.put("caseNumber", group.getRecordNo());
            innerObj.put("status", group.getStatus());
            innerObj.put("flupPurpose", purposeTexts);
            innerObj.put("researchPurpose", group.getProjectPur());
            innerObj.put("remarks", group.getDescript());
            innerObj.put("auditOpinion", group.getCheckOpinion());

            innerObj.put("projectApprovalTime", projectApprovalTime);
            innerObj.put("plannedExecTime", plannedExecTime);
            innerObj.put("sponsor", group.getSponsor());
            innerObj.put("internationalProject", group.getInternationalProject());
            innerObj.put("multiCenter", group.getMultiCenter());
            innerObj.put("leadProject", group.getLeadProject());

            innerObj.put("leaderOrgName", account.getOrgName());
            innerObj.put("leaderJobNumber", account.getAccountCode());
            innerObj.put("leaderJobName", "");//职称 todo

            innerObj.put("diseaseOrgName", pwpOrg.getOrgName());

            innerObj.put("conditionStub",gctp);//条件


            Map<String, Object> innerDataMap = new HashMap<>();
            innerDataMap.put("list", innerObj);

            return ResponseMapUtil.getSuccessResultMap(innerDataMap);
        }
        return ResponseMapUtil.getSuccessResultMap(null);

    }

    @Transactional(readOnly = true)
    @Override
    public ResponseData queryProjectMember(ReqInfo reqInfo) {
        List<Map<String, Object>> lists = groupMemberMapper.queryMemberByGroupId(reqInfo.getProjectId());
        List<Map<String, Object>> members = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            Map<String, Object> map = lists.get(i);
            BigDecimal userid = (BigDecimal) map.get("USERID");
            Account account = accountMapper.findById(userid.longValue());
            if(account!=null) {
                String memtype = (String) map.get("MEMTYPE");
                Map<String, Object> member = new HashMap<>();
                member.put("doctorId",userid);
                member.put("name", account.getAccountName());
                member.put("phoneNumber", account.getAccountMobile());
                member.put("role", memtype);
                member.put("email", account.getAccountEmail());
                members.add(member);
            }
        }
        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("member",members);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }


    @Transactional(readOnly = true)
    @Override
    public ResponseData queryField(ReqInfo reqInfo) {
        List<Map<String, Object>> lists = groupProgramFieldMapper.queryFieldNameByGroupId(reqInfo.getProjectId());
        List<String> fields = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            Map<String, Object> map = lists.get(i);
            if (null == map) {
                continue;
            }
            String fieldName = (String) map.get("FIELDNAME");
            if (null != fieldName) {
                fields.add(fieldName);
            }
        }
        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("field",fields);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseData queryExpression(ReqInfo reqInfo) {
        List<Map<String, Object>> lists = groupConditionMapper.queryExpressionByGroupId(reqInfo.getProjectId());
        List<String> expressions = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            Map<String, Object> map = lists.get(i);
            String expression = (String) map.get("EXPRESSION");
            expressions.add(expression);
        }
        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("condition",expressions);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseData queryRemindList(ReqInfo reqInfo) {
        Long doctortId = reqInfo.getDoctorId();
        List<Remind> reminds = patientMapper.queryRemindsByDoctorId(doctortId);
        // 筛选数据
        List<Remind> clearReminds = new ArrayList<>();
        for (int i = 0; i < reminds.size(); i++) {
            Remind remind = reminds.get(i);
            if (null == remind.getConnected()) {
                remind.setConnected(0);
            }
            String phone = remind.getPhones();
            if (null != phone && phone.length() > 0) {
                String regEx = "^[\\u4e00-\\u9fa5]*$";
                Pattern pattern = Pattern.compile(regEx);
                String[] phoneArray = phone.split("#");
                Set phoneSet = new HashSet<>();
                for (int j = 0; j < phoneArray.length; j++) {
                    if (!pattern.matcher(phoneArray[j]).matches() && !"(null)".equals(phoneArray[j])) {
                        phoneSet.add(phoneArray[j]);
                    }
                }
                remind.setPhoneNumber(phoneSet);
            }
            if (remind.getFollowTime() != null) {
                if (clearReminds.size() > 0) {
                    Remind lastClearRemind = clearReminds.get(clearReminds.size()-1);
                    if (!lastClearRemind.getId().equals(remind.getId()) || !lastClearRemind.getProjectId().equals(remind.getProjectId())) {
                        clearReminds.add(remind);
                    }
                } else {
                    clearReminds.add(remind);
                }
            }
        }
        List<Remind> oneWeekReminds = new ArrayList<>();
        List<Remind> twoWeekReminds = new ArrayList<>();
        try {
        // 根据时间区分提醒
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentFormatDate = simpleDateFormat.format(new Date());
            Date currentDate = simpleDateFormat.parse(currentFormatDate);

            for (int i = 0; i < clearReminds.size(); i++) {
                Remind remind = clearReminds.get(i);
                String formatTime = simpleDateFormat.format(remind.getFollowTime());
                remind.setStartFollowTime(formatTime);
                Date remindFormatDate = simpleDateFormat.parse(formatTime);
                long deadline = (remindFormatDate.getTime()-currentDate.getTime())/(1000*3600*24);
                if (deadline > 0) {
                    if (deadline <= 7) {
                        oneWeekReminds.add(remind);
                    } else if (deadline > 7 && deadline <= 14) {
                        twoWeekReminds.add(remind);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List totalRemind = new ArrayList();
        totalRemind.add(oneWeekReminds);
        totalRemind.add(twoWeekReminds);
        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("list",totalRemind);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData haveContacted(ReqInfo reqInfo) {
        Long projectId = reqInfo.getProjectId();
        Long patientId = reqInfo.getPatientId();
        Integer result = groupMapper.updateConnected(projectId, patientId);
        if (result > 0) {
            return ResponseMapUtil.getSuccessResultMap(null);
        }
        return ResponseMapUtil.getFailResultMapMap();
    }

    /**
     * 查询组ID列表
     * @param reqInfo
     * @return
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> queryGroupIdList(ReqInfo reqInfo, Pagenation pagenation) {
        Long doctortId = reqInfo.getDoctorId();
        Long status = reqInfo.getStatus();
        List<Map<String, Object>> lists = groupMapper.queryGroupIdByDoctorIdAndStatusByPage(doctortId, status, pagenation.getStartRow().longValue(), pagenation.getEndRow().longValue());
        return lists;
    }

    /**
     * 查询该用户申请的组ID列表
     * @param reqInfo
     * @return
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> queryApplGroupIdList(ReqInfo reqInfo, Pagenation pagenation) {
        Long doctortId = reqInfo.getDoctorId();
        Long status = reqInfo.getStatus();
        List<Map<String, Object>> lists = groupMapper.queryApplGroupIdByDoctorIdAndStatusByPage(doctortId, status, pagenation.getStartRow().longValue(), pagenation.getEndRow().longValue());
        return lists;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseData queryDoctoryContactById(ReqInfo reqInfo){
        Long doctorId = reqInfo.getDoctorId();

        List<Map<String,Object>> list1 = new ArrayList<>();


        if (doctorId!=null){
            //获取每个项目下的病人个数
            List<Map<String,Object>> listTotal = groupMapper.countPatientsByProjectId(doctorId);
            List<Map<String,Object>> patientList= groupMapper.queryDoctoryContactById(doctorId);
            for(Map<String,Object> patientListMap : patientList) {
                Set<String> sets = patientListMap.keySet();
               if(! sets.contains("START_FOLLOW_TIME")){
                   patientListMap.put("nextFlowInstanceDsc","未设置开始时间");
               }
            }
            for(Map<String,Object> hashMap : listTotal){
                /**
                 * 每个hashMap {projectId=403,patientTotal=18}
                 * 如果这个projectId和patientList中的map的projectId值相等
                 * 就把它的值放入一个新的map中,map在 list1
                 * 然后再创建一个list用来装病人,病人再map2
                 *  list2
                 */
                Map<String, Object> map1 = new HashMap<>();

                List<Map<String,Object>> list2 = new ArrayList<>();
                for(Map<String,Object> patientListMap : patientList) {

                    if (hashMap.get("projectId").equals(patientListMap.get("projectId"))){
                        Map<String, Object> map2 = new HashMap<>();
                        map1.put("id",hashMap.get("projectId"));
                        map1.put("patientTotal",hashMap.get("patientTotal"));
                        map1.put("project",patientListMap.get("project"));
                        map2.putAll(patientListMap);

                        list2.add(map2);
                    }
                }
                map1.put("patientList",list2);
                list1.add(map1);
            }

            }
        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("list",list1);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData saveTransferDoctor(ReqInfo reqInfo) {
        long groupRecordId = reqInfo.getGroupRecordId();
        Long doctorId = reqInfo.getTransferDoctorId();

        if (groupRecordId == 0 || doctorId == 0){

        return ResponseMapUtil.getFailResultMapMap();
        }
        int i = groupRecordMapper.updateTransferDoctor(doctorId, groupRecordId);
        if (i>0) {


        return new ResponseData(200, "成功", null);
        }
        return new ResponseData(500, "后台异常", null);
    }
}


