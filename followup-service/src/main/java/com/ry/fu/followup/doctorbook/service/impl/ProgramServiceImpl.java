package com.ry.fu.followup.doctorbook.service.impl;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.mapper.GroupMapper;
import com.ry.fu.followup.doctorbook.mapper.PlanMapper;
import com.ry.fu.followup.doctorbook.mapper.ProgramMapper;
import com.ry.fu.followup.doctorbook.model.Group;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import com.ry.fu.followup.doctorbook.service.ProgramService;
import com.ry.fu.followup.utils.JsonUtils;
import com.ry.fu.followup.utils.PdfUtils;
import com.ry.fu.followup.utils.ResponseMapUtil;
import com.tt.fastdfs.FastdfsFacade;
import com.tt.fastdfs.client.FileMeta;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Jam on 2017/12/11.
 *
 */
@Service
public class ProgramServiceImpl implements ProgramService {

    @Value("${server.tomcat.basedir}")
    private String rootPath;

    public static final String SEPARATOR = File.separator;

    @Autowired
    ProgramMapper programMapper;

    @Autowired
    PlanMapper planMapper;

    @Autowired
    private GroupMapper groupMapper;

    private static Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);

    /*@Transactional(readOnly = true)
    @Override
    public ResponseData queryProgramDetailByGroupId(ReqInfo reqInfo) {
        List<Map<String, Object>> lists = programMapper.queryProgramDetailByGroupId(reqInfo.getProjectId());
        Set<String> totalTitle = new HashSet<>();
        Set<String> totalDate = new HashSet<>();
        Map<String, List<String>> totalMap = new HashMap();
        for (int i = 0; i < lists.size(); i++) {
            Map<String, Object> map = lists.get(i);

            String flowName = (String) map.get("flowName");
            totalTitle.add(flowName);
            if (i == 0 || totalMap.get(flowName) == null) {
                List<String> dateList = new ArrayList<>();
                totalMap.put(flowName,dateList);
            }
            Date startTime = (Date) map.get("startTime");
            if (startTime != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String formatDate = formatter.format(startTime);
                if (formatDate != null && formatDate.length() > 0) {
                    totalDate.add(formatDate);
                    List<String> dateList = totalMap.get(flowName);
                    dateList.add(formatDate);
                    totalMap.put(flowName,dateList);
                }
            }

        }

        List programsList = new ArrayList();

        for(Map.Entry<String, List<String>> entry:totalMap.entrySet()) {
            Map program = new HashMap();

            program.put("title",entry.getKey());
            program.put("date",entry.getValue());
            programsList.add(program);
        }
        List<String> totalDateList = new ArrayList<>();
        for(String value : totalDate){
            totalDateList.add(value);
        }
        Collections.sort(totalDateList);
        Map<String, Object> innerDataMap = new HashMap<>();
        innerDataMap.put("info",programsList);
        innerDataMap.put("totalTitle",totalTitle);
        innerDataMap.put("totalDate",totalDateList);
        return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }*/

    @Transactional(readOnly = true)
    @Override
    public ResponseData queryProgramDetailByGroupId(ReqInfo reqInfo) {
        List<Map<String, Object>> planLists = planMapper.queryPlanListByGroupId(reqInfo.getProjectId());
        List<Map<String, Object>> innerDataList = new ArrayList<>();
        for(Map<String, Object> planMap :planLists){
            reqInfo.setPlanId(Long.parseLong(planMap.get("planId").toString()));
            List<Map<String, Object>> lists = programMapper.queryProgramDetailByPlanId(reqInfo.getPlanId());
            Set<String> totalTitle = new LinkedHashSet<>();
            Set<String> totalFlowDes = new LinkedHashSet<>();
            Map<String, List<String>> totalMap = new HashMap();
            for (int i = 0; i < lists.size(); i++) {
                Map<String, Object> map = lists.get(i);
                String programName = (String) map.get("programName");
                totalTitle.add(programName);
                if ( i == 0 || totalMap.get(programName) == null) {
                    List<String> dateList = new ArrayList<>();
                    totalMap.put(programName,dateList);
                }
                String describe = (String) map.get("describe");
                if (describe != null && describe.length() > 0) {
                    totalFlowDes.add(describe);
                    if(map.get("FLOW_ID")!=null) {
                        List<String> dateList = totalMap.get(programName);
                        dateList.add(describe);
                        totalMap.put(programName, dateList);
                    }
                }
            }
            List programsList = new ArrayList();



            for (String title : totalTitle) {

                Map program = new HashMap();

                program.put("title",title);

                for(Map.Entry<String, List<String>> entry:totalMap.entrySet()) {
                    String val = entry.getKey();
                    if(title.equals(val)){
                        program.put("flowDes",entry.getValue());
                        programsList.add(program);
                    }

                }
            }

            Map<String, Object> innerDataMap = new HashMap<>();
            innerDataMap.put("info",programsList);
            innerDataMap.put("totalTitle",totalTitle);
            innerDataMap.put("totalFlowDes",totalFlowDes);
            innerDataMap.put("planName",planMap.get("planName"));
            innerDataList.add(innerDataMap);
        }



        return ResponseMapUtil.getSuccessResultList(innerDataList);
    }

    /**
     * 根据项目ID查询表单预览
     * @param reqInfo
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public ResponseData queryProgramByGroupId(ReqInfo reqInfo) throws Exception {
            List<Map<String,Object>> programList  = programMapper.queryProgramDatasByGroupId(reqInfo.getProjectId());
            Group group = groupMapper.selectGroup(reqInfo.getProjectId(), null, null);
            FastdfsFacade fastdfsFacade = new FastdfsFacade();
            Map<String,Object> innerDataMap = new HashMap<>();
            String content ="";

            //删除文件服务器上的旧版本pdf
            if(group == null){
              return   ResponseMapUtil.getFailResultMapMap();
            }
            String pdfPaths = group.getPdfPath();
            if (StringUtils.isNotBlank(pdfPaths)){
            String s = pdfPaths.toString();
            Map<String,Object> oldPath = (Map<String,Object>) JsonUtils.readValue(s,Map.class);
            String groupName =(String) oldPath.get("groupName");
            String remoteFilename =(String) oldPath.get("remoteFilename");
            try{

                fastdfsFacade.deleteFile(groupName,remoteFilename);
            }
            catch (FileNotFoundException e){

            groupMapper.updatePdfpathById(null,group.getId());
            logger.info("catch到文件不存在异常");

                }
            }
            getInnerDataMap(programList, group, innerDataMap, content);
            return ResponseMapUtil.getSuccessResultMap(innerDataMap);
    }

    private void getInnerDataMap(List<Map<String, Object>> programList, Group group, Map<String, Object> innerDataMap, String content) {
            for(Map program : programList){
            Long programId = Long.parseLong(program.get("program_id").toString());
            System.out.println("==>content="+ program.get("content"));
            if (program.get("content")!=null) {
            Clob contentClob = (Clob) program.get("content");
            String content_ = ClobToString(contentClob);
            content += "<div id='"+programId+"' >"+content_+"</div>";
                }
            }
            String pdfpath=null;
            String realPath = rootPath+SEPARATOR;
            if(StringUtils.isNotBlank(content)) {
            pdfpath = PdfUtils.createPdf(content,realPath);//上传到服务器，返回保存路径
            //pdfpath = PdfUtils.formPdf(realPath,content);
            }
            if (StringUtils.isNotBlank(pdfpath)){
            String result = pdfpath.substring(1);
            String groupName = result.substring(0, result.indexOf("/"));
            String remoteFilename = result.substring(result.indexOf("/") + 1);
            //保存最新的
            Map<String,Object> pdfPath = new HashMap<>();
            pdfPath.put("groupName",groupName);
            pdfPath.put("remoteFilename",remoteFilename);
            String s = JsonUtils.toJSon(pdfPath);
            groupMapper.updatePdfpathById(s,group.getId());
            }
            String path = pdfpath;
            innerDataMap.put("path",path==null ? "" : path);
            logger.info("===>path"+path);
    }

    public String ClobToString(Clob clob)  {

            String reString = "";
            Reader is = null;// 得到流
            try {
                is = clob.getCharacterStream();
            } catch (SQLException e) {
                e.printStackTrace();
                return "";
            }
            BufferedReader br = new BufferedReader(is);
            String s = null;
            try {
                s = br.readLine();
                StringBuffer sb = new StringBuffer();
                while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
                    sb.append(s);
                    s = br.readLine();
                }
                reString = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
            return reString;
        }


}
