package com.ry.fu.followup.doctorbook.service.impl;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.mapper.FlowInstanceMapper;
import com.ry.fu.followup.doctorbook.mapper.ProgramMapper;
import com.ry.fu.followup.doctorbook.mapper.QuestionnaireMapper;
import com.ry.fu.followup.doctorbook.model.FlowInstance;
import com.ry.fu.followup.doctorbook.model.Program;
import com.ry.fu.followup.doctorbook.model.Questionnaire;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import com.ry.fu.followup.doctorbook.service.FlowInstanceService;
import com.ry.fu.followup.utils.JsonUtils;
import com.ry.fu.followup.utils.PdfUtils;
import com.ry.fu.followup.utils.ResponseMapUtil;
import com.tt.fastdfs.FastdfsFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jam on 2017/12/25.
 *
 */
@Service
public class FlowInstanceServiceImpl implements FlowInstanceService {
    @Value("${server.tomcat.basedir}")
    private String rootPath;

    public static final String SEPARATOR = File.separator;


    @Autowired
    private FlowInstanceMapper flowInstanceMapper;

    @Autowired
    private ProgramMapper programMapper;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    private org.slf4j.Logger logger = LoggerFactory.getLogger(FlowInstanceServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public ResponseData queryReportDate(ReqInfo reqInfo) {
        List<Questionnaire> questionnaires = questionnaireMapper.queryQuestionnaireByPatientAndPrjId(reqInfo.getPatientId(), reqInfo.getProjectId());
        List<Map<String,Object>> innerDataList = new ArrayList<>();
        for(Questionnaire questionnaire:questionnaires){
            List<FlowInstance> flowInstances = flowInstanceMapper.queryReportDateByQuesId(questionnaire.getId());
            List<Map<String, Object>> dateList = new ArrayList<>();
            for (int i = 0; i < flowInstances.size(); i++) {
                FlowInstance flowInstance = flowInstances.get(i);
                Map<String, Object> date = new HashMap<>();
                date.put("id",flowInstance.getId());

                String formatDate = "未设置开始随访时间";
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date startFollowTime = (Date) flowInstance.getStartFollowTime();
                if(startFollowTime!=null) {
                    formatDate = formatter.format(startFollowTime);
                }
                date.put("date",formatDate);
                date.put("description",flowInstance.getDescribe());
                dateList.add(date);
            }
            Map<String, Object> innerDataMap = new HashMap<>();
            innerDataMap.put("date",dateList);
            innerDataMap.put("ques_name",questionnaire.getName());
            innerDataList.add(innerDataMap);
        }

        return ResponseMapUtil.getSuccessResultList(innerDataList);
    }

    @Transactional
    @Override
    public ResponseData queryReportList(ReqInfo reqInfo) throws Exception {
        long time1 = new Date().getTime();
        List<Program> programs = programMapper.queryProgramByFlowId(reqInfo.getFlowInstanceId());
        if (programs==null || programs.size()==0){
            return  new ResponseData(200,"未查询到数据",null);
        }

        List programAndStatus = new ArrayList();
        FastdfsFacade fastdfsFacade = new FastdfsFacade();
        for (int i = 0; i < programs.size(); i++) {
            Map<String, Object> programMap = new HashMap<>();

            programMap.put("name",programs.get(i).getName());
            programMap.put("status",programs.get(i).getFlowStatus());

            String content =programs.get(i).getReport().getContent();

            String pdfpath="";
            if(StringUtils.isNotBlank(content)) {

            //删除文件服务器上的旧版本pdf
            String pdfPaths = programs.get(i).getPdfPath();
           if (StringUtils.isNotBlank(pdfPaths)){
            String s = pdfPaths.toString();
            Map<String,Object> oldPath = (Map<String,Object>)JsonUtils.readValue(s,Map.class);
            String groupName =(String) oldPath.get("groupName");
            String remoteFilename =(String) oldPath.get("remoteFilename");
            try{

            fastdfsFacade.deleteFile(groupName,remoteFilename);

            }catch (FileNotFoundException f){
            programMapper.savePdfPathById(null,programs.get(i).getId());
                }
            }
            String realPath = rootPath+SEPARATOR;
            pdfpath = PdfUtils.createPdf(content,realPath);//上传到服务器，返回保存路径
            if (StringUtils.isNotBlank(pdfpath)){

            String result = pdfpath.substring(1);
            String groupName = result.substring(0, result.indexOf("/"));
            String remoteFilename = result.substring(result.indexOf("/") + 1);
            //保存最新的
            Map<String,Object> pdfPath = new HashMap<>();
            pdfPath.put("groupName",groupName);
            pdfPath.put("remoteFilename",remoteFilename);
            Long programId = programs.get(i).getId();
            String s = JsonUtils.toJSon(pdfPath);
            long time2 = new Date().getTime();
            logger.info("时间是:{}", time2 - time1);
            programMapper.savePdfPathById(s,programId);

            }
        }
        String path = pdfpath;
        programMap.put("path",path);
        programAndStatus.add(programMap);
    }
        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("list",programAndStatus);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }

    /**
     * 根据住院号和状态获取随访实例,随访实例对应的是selectStatus=1
     *
     @Override@param hospNo
     * @return
     */
    public ResponseData queryFollowDetails(Long patientId) throws ParseException {
        String status = "2";
        Map<String, Object> innerDataMap = new HashMap<>();

        if (patientId==null){
            innerDataMap.put("flowInstanceList",null);
            return ResponseMapUtil.getSuccessResultMap(innerDataMap);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Map<String, Object>> flowInstanceList = flowInstanceMapper.queryFuInstanceByPatientId(patientId, status);
        if (flowInstanceList==null){
            innerDataMap.put("flowInstanceList",null);
            return ResponseMapUtil.getSuccessResultMap(innerDataMap);
        }
       // SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Map<String, Object> flowInstanceMap : flowInstanceList){

            Object time =  flowInstanceMap.get("time");

            if (time!=null){
                String  dateString = sdf.format(sdf.parse(flowInstanceMap.get("time").toString()));
                flowInstanceMap.put("time",dateString);
            }else {
                flowInstanceMap.put("time","");
            }
            flowInstanceMap.put("selectStatus","1");

        }

        innerDataMap.put("flowInstanceList",flowInstanceList);

        return ResponseMapUtil.getSuccessResultMap(innerDataMap);


    }


}

