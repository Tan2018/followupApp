package com.ry.fu.esb.instantmessaging.controller;

import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.instantmessaging.service.NetEaseCloudLetterService;
import com.ry.fu.esb.instantmessaging.service.impl.CheckSumBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 即时通讯controller
 */
@RequestMapping("${prefixPath}/v1/instantmessaging/netEaseCloudLetter")
@RestController
public class NetEaseCloudLetterController {

    private static Logger logger = LoggerFactory.getLogger(NetEaseCloudLetterController.class);

    @Autowired
    private NetEaseCloudLetterService netEaseCloudLetterService;

    @Value("${im.appSecret}")
    private String appSecret;

    @Value("${netServerHostAndPort}")
    private String netServerHostAndPort;

    /**
     * 注册网易云信ID
     *
     * @param params 请求参数：{accid：要注册的网易云通信id，token：要注册的token}
     * @return
     */
    @RequestMapping(value = "/registerNetEaseCloudCommunicationId", method = RequestMethod.POST)
    public ResponseData registerNetEaseCloudCommunicationId(@RequestBody Map<String, String> params) {

        logger.info("注册网易云信请求参数：{}", params);
        //校验参数
        if (params == null || params.size() == 0) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (params.get("accid") == null || StringUtils.isBlank(params.get("accid").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "accid不能为空");
        }
        if (params.get("token") == null || StringUtils.isBlank(params.get("token").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "token不能为空");
        }
        if (params.get("name") == null || StringUtils.isBlank(params.get("name").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "name不能为空");
        }
        try {
            return netEaseCloudLetterService.registerOrUpdateToken(params.get("accid").toString(), params.get("token").toString(), params.get("name").toString());
        } catch (Exception e) {
            logger.error("registerNetEaseCloudCommunicationId -> Exception", e);
            e.printStackTrace();
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        }
    }

    /**
     * 服务端抄送接口
     *
     * @param request 抄送的参数
     * @return
     */
    @RequestMapping(value = {"/mockClient"}, method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject mockClient(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            // 获取请求体
            byte[] body = readBody(request);
            if (body == null) {
                logger.warn("请求错误，body为空！");
                result.put("code", 414);
                return result;
            }
            // 获取部分request header，并打印
            String ContentType = request.getContentType();
            String AppKey = request.getHeader("AppKey");
            String CurTime = request.getHeader("CurTime");
            String MD5 = request.getHeader("MD5");
            String CheckSum = request.getHeader("CheckSum");
            logger.info("request headers: ContentType = {}, AppKey = {}, CurTime = {}, " +
                    "MD5 = {}, CheckSum = {}", ContentType, AppKey, CurTime, MD5, CheckSum);
            // 将请求体转成String格式，并打印
            String requestBody = new String(body, "utf-8");
            logger.info("request body = {}", requestBody);
            // 获取计算过的md5及checkSum
            String verifyMD5 = CheckSumBuilder.getMD5(requestBody);
            String verifyChecksum = CheckSumBuilder.getCheckSum(appSecret, verifyMD5, CurTime);
            logger.debug("verifyMD5 = {}, verifyChecksum = {}", verifyMD5, verifyChecksum);
            // TODO: 比较md5、checkSum是否一致，以及后续业务处理
            result.put("code", 200);
            //保存历史消息记录
            netEaseCloudLetterService.saveMessageContent(requestBody);
            return result;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            result.put("code", 414);
            return result;
        }
    }

    private byte[] readBody(HttpServletRequest request) throws IOException {
        if (request.getContentLength() > 0) {
            byte[] body = new byte[request.getContentLength()];
            org.apache.commons.io.IOUtils.readFully(request.getInputStream(), body);
            return body;
        } else
            return null;
    }

    /**
     * 查询单聊历史记录
     *
     * @param params 前端请求参数：{fromAccount：消息发送人，to：消息接收人，pageNum：页码，pageSize：每页显示条数}
     * @return
     */
    @RequestMapping(value = "/selectMessageContent", method = RequestMethod.POST)
    public ResponseData selectMessageContent(@RequestBody Map<String, Object> params) {

        logger.info("查询单聊历史记录请求参数---json：{}" + JsonUtils.toJSon(params));
        //校验参数
        if (params == null || params.size() == 0) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (params.get("fromaccount") == null || StringUtils.isBlank(params.get("fromaccount").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "fromaccount不能为空");
        }
        if (params.get("to") == null || StringUtils.isBlank(params.get("to").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "to不能为空");
        }
        if (params.get("startTime") == null || StringUtils.isBlank(params.get("startTime").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "startTime不能为空");
        }
        if (params.get("pageSize") == null || StringUtils.isBlank(params.get("pageSize").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "pageSize不能为空");
        }
        try {
            return netEaseCloudLetterService.selectMessageContent(params.get("fromaccount").toString(), params.get("to").toString(), Integer.parseInt(params.get("pageSize").toString()), (Long) params.get("startTime"));
        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("selectMessageContent()方法异常", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), "时间转换错误!", null);
        } catch (SystemException e) {
            e.printStackTrace();
            logger.info("selectMessageContent()方法异常", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), null);
        }
    }

    /**
     * 查询最近会话列表信息
     *
     * @param params 请求参数：{accid：网易云通信id，pageNum：页码，pageSize：每页显示条数}
     * @return
     */
    @RequestMapping(value = "/selectMessageLis", method = RequestMethod.POST)
    public ResponseData selectMessageLis(@RequestBody Map<String, String> params) {

        logger.info("查询最近会话列表请求参数---json：{}" + JsonUtils.toJSon(params));
        //校验参数
        if (params == null || params.size() == 0) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (params.get("accid") == null || StringUtils.isBlank(params.get("accid").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "accid不能为空");
        }
        if (params.get("pageNum") == null || StringUtils.isBlank(params.get("pageNum").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "pageNum不能为空");
        }
        if (params.get("pageSize") == null || StringUtils.isBlank(params.get("pageSize").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "pageSize不能为空");
        }

        return netEaseCloudLetterService.selectMessageLis(params.get("accid").toString(), Integer.valueOf(params.get("pageNum").toString()), Integer.valueOf(params.get("pageSize").toString().toString()));
    }

    /**
     * 点击聊天界面查询近3天的聊条记录(分页，排序)
     *
     * @param params 请求参数：{fromaccount：发送人，to：接收人，pageNum：页码，pageSize：每页显示条数}
     * @return
     */
    @RequestMapping(value = "selectRecentRecords", method = RequestMethod.POST)
    public ResponseData selectRecentRecords(@RequestBody Map<String, String> params) {

        logger.info("点击查询近3天的聊天记录请求参数--json：{}", JsonUtils.toJSon(params));
        //校验参数
        if (params == null || params.size() == 0) {
            return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
        }
        if (params.get("fromaccount") == null || StringUtils.isBlank(params.get("fromaccount").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "fromaccount不能为空");
        }
        if (params.get("to") == null || StringUtils.isBlank(params.get("to").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "to不能为空");
        }
        if (params.get("pageNum") == null || StringUtils.isBlank(params.get("pageNum").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "pageNum不能为空");
        }
        if (params.get("pageSize") == null || StringUtils.isBlank(params.get("pageSize").toString())) {
            return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "pageSize不能为空");
        }
        return netEaseCloudLetterService.selectRecentRecords(params.get("fromaccount").toString(), params.get("to").toString(), Integer.valueOf(params.get("pageNum")), Integer.valueOf(params.get("pageSize").toString()));
    }

    /**
     * 即时通讯获取医生信息(就医日志前端)
     *
     * @param params {"accids":"00876"}等数据格式
     * @return
     */
    @RequestMapping(value = "/inquireOfDoctorInformation", method = RequestMethod.POST)
    public ResponseData inquireOfDoctorInformation(@RequestBody Map<String, Object> params) {

        try {
            logger.info("获取医生信息请求参数---json：{}", JsonUtils.toJSon(params));
            //校验参数
            if (params == null || params.size() == 0) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (params.get("accids") == null || StringUtils.isBlank(params.get("accids").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "accids不能为空");
            }
            ArrayList<String> accids = new ArrayList<>();
            JSONArray jsonArray = JSONArray.fromObject(params.get("accids"));
            jsonArray.forEach(e -> accids.add(e.toString()));
            if (accids == null) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            return netEaseCloudLetterService.inquireOfDoctorInformation(accids);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("inquireOfDoctorInformation -> ParseException", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), "类型转换错误!", null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("inquireOfDoctorInformation -> Exception", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), null);
        }
    }

    /**
     * 消息撤回
     *
     * @param params:{"deleteMsgid":"2002","timetag":132546575}等数据格式
     * @return
     */
    @RequestMapping(value = "/recallMessage", method = RequestMethod.POST)
    public ResponseData recallMessage(@RequestBody Map<String, Object> params) {

        try {
            logger.info("消息撤回请求参数---json：{}", JsonUtils.toJSon(params));
            //校验参数
            if (params == null || params.size() == 0) {
                return new ResponseData(StatusCode.ARGU_EMPTY.getCode(), StatusCode.ARGU_EMPTY.getMsg(), null);
            }
            if (params.get("deleteMsgid") == null || StringUtils.isBlank(params.get("deleteMsgid").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "deleteMsgid不能为空");
            }
            if (params.get("timetag") == null || StringUtils.isBlank(params.get("timetag").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "timetag不能为空");
            }
            if (params.get("type") == null || StringUtils.isBlank(params.get("type").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "type不能为空");
            }
            if (params.get("from") == null || StringUtils.isBlank(params.get("from").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "from不能为空");
            }
            if (params.get("to") == null || StringUtils.isBlank(params.get("to").toString())) {
                return new ResponseData(StatusCode.ARGU_DEFECT.getCode(), StatusCode.ARGU_DEFECT.getMsg(), "to不能为空");
            }
            return netEaseCloudLetterService.recallMessage(params);
        } catch (SystemException e) {
            e.printStackTrace();
            logger.error("recallMessage - > SystemException", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        } catch (ESBException e) {
            e.printStackTrace();
            logger.error("recallMessage - > ESBException", e);
            return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
        }
    }
}
