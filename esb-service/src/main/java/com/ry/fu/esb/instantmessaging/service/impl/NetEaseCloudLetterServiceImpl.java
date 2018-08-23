package com.ry.fu.esb.instantmessaging.service.impl;

import com.github.pagehelper.PageHelper;
import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.enumstatic.StatusCode;
import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.http.HttpRequest;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.common.seq.RedisIncrementGenerator;
import com.ry.fu.esb.common.utils.BeanMapUtils;
import com.ry.fu.esb.common.utils.JsonUtils;
import com.ry.fu.esb.common.utils.RestTemplateMethodParamUtil;
import com.ry.fu.esb.instantmessaging.mapper.*;
import com.ry.fu.esb.instantmessaging.model.*;
import com.ry.fu.esb.instantmessaging.service.NetEaseCloudLetterService;
import com.ry.fu.esb.jpush.model.BaseResponseList;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 网易云信serviceImpl
 */
@Service
public class NetEaseCloudLetterServiceImpl implements NetEaseCloudLetterService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisIncrementGenerator redisIncrementGenerator;

    @Autowired
    private MessageContentMapper messageContentMapper;

    @Autowired
    private MessageContentListMapper messageContentListMapper;

    @Autowired
    private RecentSessionListMapper recentSessionListMapper;

    @Value("${netServerHostAndPort}")
    private String netServerHostAndPort;

    @Autowired
    private UinfosMapper uinfosMapper;

    @Autowired
    private RestTemplateMethodParamUtil restTemplateMethodParamUtil;

    @Autowired
    private UserListMapper userListMapper;

    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public ResponseData registerOrUpdateToken(String accid, String token, String name) throws Exception {

        String responseResult = registerNetEaseCloudCommunicationId(accid, token, name);
        logger.info("net-service注册网易云账号响应参数：json{}" + responseResult);
        if (responseResult == null) {
            logger.info("net-service注册网易云账号返回结果为空!");
            return new ResponseData(StatusCode.ACCOUNT_ERROR_THREE.getCode(), "net-service注册网易云账号返回结果为空!", 0);
        }
        Map<String, Object> map = JsonUtils.readValue(responseResult, Map.class);
        String status = (String) map.get("status");
        if (status != null) {
            if (status.equals("500")) {
                return new ResponseData(status, "请求net-service接口出错", 0);
            } else {
                return new ResponseData(status, map.get("msg").toString(), 0);
            }
        }
        Integer code = (Integer) map.get("code");
        logger.info(name + "的网易云信账号" + accid + map.get("desc"));
        if (code == 200) {
            //保存注册成功的用户信息
            UserList userList = new UserList();
            String key = BeanMapUtils.getTableSeqKey(userList);
            Long id = redisIncrementGenerator.increment(key, 1);
            userList.setId(id);
            userList.setUserAccid(accid);
            userList.setUserToken(token);
            userList.setUserName(name);
            userList.setSymbol(active);
            userList.setCreateTime(new Date());
            userList.setUpdateTime(new Date());
            userListMapper.insertSelective(userList);
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), 1);
        }
        if (code == 414) {//可能是已注册，也可能是参数错误，所以应该是网易云信的状态码有问题。
            return updateNetEaseCloudCommunicationId(accid, token);
        }
        return new ResponseData(map.get("code").toString(), map.get("msg").toString(), 0);
    }

    /**
     * 创建网易云通信ID
     *
     * @param accid 要注册的网易云通信id
     * @param token 要注册的token值
     * @param name  网易云通信ID昵称
     * @return
     * @throws Exception
     */
    public String registerNetEaseCloudCommunicationId(String accid, String token, String name) throws Exception {

        //设置请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("accid", accid);
        params.put("token", token);
        params.put("name", name);
        params.put("requestType", "CREATE_COMMUNICATION_ID");
        if (!active.equals("pro")) {
            params.put("test", "1");
        }
        logger.info("创建网易云信id请求net-service参数：json{}" + JsonUtils.toJSon(params));
        return HttpRequest.post(netServerHostAndPort + Constants.INSTANT_MESSAGING, JsonUtils.toJSon(params), ContentType.APPLICATION_JSON);
    }

    /**
     * 修改网易云信密码
     *
     * @param accid 网易云信id
     * @param token 网易云信token
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateNetEaseCloudCommunicationId(String accid, String token) throws Exception {

        Map<String, Object> params = new HashMap<>();
        //设置更新网易云信密码参数
        params.put("accid", accid);
        params.put("token", token);
        if (!active.equals("pro")) {
            params.put("test", "1");
        }
        params.put("requestType", "UPDATE_ACCID");
        logger.info("修改网易云信密码请求net-service参数：json{}" + JsonUtils.toJSon(params));
        long t1 = System.currentTimeMillis();
        Header header = restTemplateMethodParamUtil.getPostHeader(params);
        String result = HttpRequest.post(netServerHostAndPort + Constants.INSTANT_MESSAGING, JsonUtils.toJSon(params), ContentType.APPLICATION_JSON, header);
        long t2 = System.currentTimeMillis();
        logger.info("net-service修改网易云信密码---响应时长---" + (t2 - t1));
        logger.info("net-service修改网易云信密码返回参数：json{}", JsonUtils.toJSon(result));
        if (result == null) {
            logger.info("net-service修改网易云信密码返回参数为空!");
            return new ResponseData(StatusCode.ACCOUNT_ERROR_THREE.getCode(), "网易云信返回修改密码参数为空", 0);
        }
        Map<String, Object> resultRep = JsonUtils.readValue(result, Map.class);
        Integer resultCode = (Integer) resultRep.get("code");
        if (resultCode == 200) {
            int count = userListMapper.updateUserToken(accid, token, new Date());
            if (count <= 0) {
                logger.info(active + "环境根据账号：" + accid + "更新数据库用户的网易云信密码失败!");
            } else {
                logger.info(active + "环境根据账号：" + accid + "更新数据库用户的网易云信密码成功!");
            }
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), 1);
        }
        return new ResponseData(resultRep.get("code").toString(), resultRep.get("desc").toString(), 0);
    }

    /**
     * 客户端聊天消息保存
     *
     * @param request 保存的参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMessageContent(String request) {

        if (request.equals("{}")) {
            return;
        }
        logger.info("消息抄送请求参数：{}", request);
        Map<String, Object> map = JsonUtils.readValue(request, Map.class);
        String fromAccount = (String) map.get("fromAccount");
        String to = (String) map.get("to");
        String msgType = (String) map.get("msgType");
        String fromClientType = (String) map.get("fromClientType");
        String msgidServer = (String) map.get("msgidServer");
        String msgTimestamp = (String) map.get("msgTimestamp");
        String eventType = (String) map.get("eventType");
        String body = (String) map.get("body");
        String convType = (String) map.get("convType");
        String msgidClient = (String) map.get("msgidClient");
        String resendFlag = (String) map.get("resendFlag");
        String fromNick = (String) map.get("fromNick");
        Map<String, Object> attach = null;
        if (map.get("attach") != null && !map.get("attach").equals("")) {
            attach = JsonUtils.readValue(map.get("attach").toString(), Map.class);
        }
        Integer size = null;
        String ext = null;
        Integer dur = null;
        String url = null;
        String md5 = null;
        if (attach != null) {
            size = (Integer) attach.get("size");
            ext = (String) attach.get("ext");
            dur = (Integer) attach.get("dur");
            url = (String) attach.get("url");
            md5 = (String) attach.get("md5");
        }
        logger.info("fromAccount：" + fromAccount + ",to：" + to + ",msgType：" + msgType + ",fromClientType：" + fromClientType + ",msgidServer：" + msgidServer + ",msgTimestamp：" + msgTimestamp + ",eventType：" + eventType + ",body：" + body + ",convType：" + convType + ",msgidClient：" + msgidClient + ",resendFlag：" + resendFlag);
        //判断是否消息以保存
        int count = messageContentMapper.selectByMsgIdServer(msgidServer);
        if (count <= 0) {
            MessageContent mc = new MessageContent();
            String key = BeanMapUtils.getTableSeqKey(mc);
            Long id = redisIncrementGenerator.increment(key, 1);
            //设置要保存的消息
            mc.setId(id);
            mc.setFromAccount(fromAccount);
            mc.setTid(to);
            if (body != null) {
                mc.setBody(body);
            }
            mc.setConvType(convType);
            mc.setEventType(eventType);
            mc.setFromClientType(fromClientType);
            mc.setMsgidClient(msgidClient);
            mc.setMsgidServer(msgidServer);
            if (fromNick != null) {
                mc.setFromNick(fromNick);
            }
            if (msgTimestamp != null && msgTimestamp.length() > 0) {
                mc.setMsgTimeStamp(new Date(Long.valueOf(msgTimestamp)));
            }
            if (size != null) {
                mc.setAttachSize(size);
            }
            if (ext != null) {
                mc.setAttachExt(ext);
            }
            if (dur != null) {
                mc.setAttachDur(dur);
            }
            if (url != null) {
                mc.setAttachUrl(url);
            }
            if (md5 != null) {
                mc.setAttachMd5(md5);
            }
            mc.setMsgType(msgType);
            mc.setResendFlag(resendFlag);
            messageContentMapper.insertSelective(mc);
            //表示发送人
            int FromAccountCount = recentSessionListMapper.selectByFromAccountToCount(fromAccount, to);
            //表示接收人
            int toCount = recentSessionListMapper.selectByFromAccountToCount(to, fromAccount);
            logger.info("FromAccountCount：{}" + FromAccountCount + ",toCount：{}" + toCount);
            RecentSessionList rsl = new RecentSessionList();
            //判断临时表中是否有此数据
            if ((toCount <= 0 && FromAccountCount <= 0) || (toCount <= 0 && FromAccountCount > 0) || (toCount > 0 && FromAccountCount <= 0)) {//临时表没数据，进行数据添加
                logger.info("临时表中没有数据，进行添加!");
                rsl.setMcId(id);
                rsl.setFromAccount(fromAccount);
                rsl.setTid(to);
                rsl.setBody(body);
                rsl.setConvType(convType);
                rsl.setEventType(eventType);
                if (fromNick != null) {
                    rsl.setFromNick(fromNick);
                }
                if (msgTimestamp != null && msgTimestamp.length() > 0) {
                    rsl.setMsgTimeStamp(new Date(Long.valueOf(msgTimestamp)));
                }
                recentSessionListMapper.insertSelective(rsl);
            } else if (FromAccountCount > 0 && toCount > 0) {//临时表有数据，进行修改
                logger.info("临时表中有数据，进行修改!");
                int updateCount = recentSessionListMapper.updateByFromAccount(convType, body, msgType, new Date(Long.valueOf(msgTimestamp)), eventType, fromAccount, to);
                if (updateCount < 1) {
                    logger.info("临时表修改失败，进行添加!");
                    rsl.setMcId(id);
                    rsl.setFromAccount(fromAccount);
                    rsl.setTid(to);
                    rsl.setBody(body);
                    rsl.setConvType(convType);
                    rsl.setEventType(eventType);
                    if (fromNick != null) {
                        rsl.setFromNick(fromNick);
                    }
                    if (msgTimestamp != null && msgTimestamp.length() > 0) {
                        rsl.setMsgTimeStamp(new Date(Long.valueOf(msgTimestamp)));
                    }
                    recentSessionListMapper.insertSelective(rsl);
                }
            }
        }
    }

    /**
     * 查询单聊历史记录
     *
     * @param fromAccount 该用户注册的网易云通信id
     * @param to          接收人
     * @param pageSize    每页显示条数
     * @return
     */
    @Override
    public ResponseData selectMessageContent(String fromAccount, String to, Integer pageSize, Long startTime) throws
            SystemException, ParseException {

        BaseResponseList<MessageContentList> listBaseResponseList = new BaseResponseList<MessageContentList>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        logger.info("当前时间：" + System.currentTimeMillis() + "毫秒：" + new Date().getTime());
        //查询消息记录
        logger.info("查询单聊历史记录请求参数：{}，转换后的时间：" + sdf.format(startTime) + ",pageSize：" + pageSize + ",fromaccount：" + fromAccount + ",to：" + to);
        List<MessageContentList> mcList = messageContentListMapper.selectByFromAccountAndTo(fromAccount, to, sdf.format(startTime), pageSize);
        //判断是否为空
        if (null == mcList || mcList.size() == 0) {
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
        }
        //保存查询出来的消息记录
        listBaseResponseList.setList(mcList);
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), listBaseResponseList);
    }

    /**
     * 查询最近会话列表
     *
     * @param accid 该用户注册的网易云通信id
     * @return
     */
    /*@Override
    public ResponseData selectMessageLis(String accid, Integer pageNum, Integer pageSize) {

        BaseResponseList<RecentSessionList> listBaseResponseList = new BaseResponseList<RecentSessionList>();
        List<RecentSessionList> recentSessionLists = new ArrayList<>();
        PageHelper.startPage((pageNum - 1), pageSize);
        int fromAccountCount = recentSessionListMapper.selectByFromAccountCount(accid);
        if (fromAccountCount > 0) {//表示返回医生信息
            List<RecentSessionList> rsList = recentSessionListMapper.selectByFromAccount(accid);
            if (rsList != null && rsList.size() > 0) {
                //计算总分页数
                int total = (fromAccountCount % pageSize == 0) ? fromAccountCount / pageSize : fromAccountCount / pageSize + 1;
                //判断请求页数是否大于总页数
                if (pageNum > total) {
                    return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
                }
                for (RecentSessionList rsl : rsList) {
                    RecentSessionList recentSession = new RecentSessionList();
                    recentSession.setFromAccount(rsl.getFromAccount());
                    recentSession.setTid(rsl.getTid());
                    recentSession.setBody(rsl.getBody());
                    recentSession.setMsgTimeStamp(rsl.getMsgTimeStamp());
                    Map<String, Object> params = new HashMap<>();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(rsl.getFromAccount());
                    params.put("accids", list);
                    params.put("requestType", "GET_USERINFO");//表示请求用户名片
                    Header header = restTemplateMethodParamUtil.getPostHeader(params);
                    String result = HttpRequest.post(netServerHostAndPort + Constants.INSTANT_MESSAGING, JsonUtils.toJSon(params), ContentType.APPLICATION_JSON, header);
                    if (result != null) {
                        Map<String, Object> map = JsonUtils.readValue(result, Map.class);
                        Integer code = (Integer) map.get("code");
                        logger.info("获取用户名片返回的code状态码：" + code);
                        if (code == 200) {
                            if (map.get("uinfos") != null) {
                                ArrayList<Map<String, String>> uinfos = (ArrayList<Map<String, String>>) map.get("uinfos");
                                logger.info("uinfos：" + uinfos);
                                for (Map<String, String> str : uinfos) {
                                    if (str.get("name") != null) {
                                        recentSession.setName(str.get("name"));
                                    }
                                }
                            }
                        }
                    } else {
                        logger.info("获取用户昵称失败...");
                    }
                    recentSessionLists.add(recentSession);
                }
                //保存查询出来的消息记录
                listBaseResponseList.setList(recentSessionLists);
                //保存总分页数
                listBaseResponseList.setTotal(total);
                return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), listBaseResponseList);
            } else {
                return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
            }
        } else {//返回患者信息
            List<RecentSessionList> recentSessionListList = recentSessionListMapper.selectByTid(accid);
            if (recentSessionListList != null && recentSessionListList.size() >= 0) {
                int count = recentSessionListMapper.selectByTidCount(accid);
                //计算总分页数
                int total = (count % pageSize == 0) ? count / pageSize : count / pageSize + 1;
                //判断请求页数是否大于总页数
                if (pageNum > total) {
                    return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
                }
                for (RecentSessionList rsl : recentSessionListList) {
                    RecentSessionList recentSession = new RecentSessionList();
                    recentSession.setFromAccount(rsl.getFromAccount());
                    recentSession.setTid(rsl.getTid());
                    recentSession.setBody(rsl.getBody());
                    recentSession.setMsgTimeStamp(rsl.getMsgTimeStamp());
                    Map<String, Object> params = new HashMap<>();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(rsl.getTid());
                    params.put("accids", list);
                    params.put("requestType", "GET_USERINFO");//表示请求用户名片
                    Header header = restTemplateMethodParamUtil.getPostHeader(params);
                    String result = HttpRequest.post(netServerHostAndPort + Constants.INSTANT_MESSAGING, JsonUtils.toJSon(params), ContentType.APPLICATION_JSON, header);
                    if (result != null) {
                        Map<String, Object> map = JsonUtils.readValue(result, Map.class);
                        Integer code = (Integer) map.get("code");
                        logger.info("获取用户名片code状态码：" + code);
                        if (code == 200) {
                            if (map.get("uinfos") != null) {
                                ArrayList<Map<String, String>> uinfos = (ArrayList<Map<String, String>>) map.get("uinfos");
                                logger.info("uinfos：" + uinfos);
                                for (Map<String, String> str : uinfos) {
                                    if (str.get("name") != null) {
                                        recentSession.setName(str.get("name"));
                                    }
                                }
                            }
                        }
                    }
                    recentSessionLists.add(recentSession);
                }
                //保存查询出来的消息记录
                listBaseResponseList.setList(recentSessionLists);
                //保存总分页数
                listBaseResponseList.setTotal(total);
                return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), listBaseResponseList);
            }
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
        }
    }*/
    @Override
    public ResponseData selectMessageLis(String accid, Integer pageNum, Integer pageSize) {

        BaseResponseList<RecentSessionList> listBaseResponseList = new BaseResponseList<RecentSessionList>();
        PageHelper.startPage((pageNum - 1), pageSize);
        List<RecentSessionList> rsList = recentSessionListMapper.selectByFromAccount(accid);
        if (rsList != null && rsList.size() > 0) {
            int rsCount = recentSessionListMapper.selectByFromAccountCount(accid);
            //计算总分页数
            int total = (rsCount % pageSize == 0) ? rsCount / pageSize : rsCount / pageSize + 1;
            //判断请求页数是否大于总页数
            if (pageNum > total) {
                return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
            }
            listBaseResponseList.setTotal(total);
            listBaseResponseList.setList(rsList);
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), listBaseResponseList);
        }
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
    }

    /**
     * 点击聊天界面查询近3天的聊条记录(分页，排序)
     *
     * @param fromaccount 发送人
     * @param to          接收人
     * @return
     */
    @Override
    public ResponseData selectRecentRecords(String fromaccount, String to, Integer pageNum, Integer pageSize) {
        //计算前3天时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        date = calendar.getTime();
        logger.info("前三天的时间：" + sdf.format(date) + ",当天的时间：" + sdf.format(new Date()));
        BaseResponseList<MessageContentList> responseList = new BaseResponseList<MessageContentList>();
        PageHelper.startPage((pageNum - 1), pageSize);
        List<MessageContentList> messageContents = messageContentListMapper.selectByMsgTimeStamp(fromaccount, to, date, new Date());
        //统计总条数
        int count = messageContentListMapper.selectByMsgTimeStampCount(fromaccount, to, date, new Date());
        if (messageContents != null && messageContents.size() > 0) {
            //计算总分页数
            int total = (count % pageSize == 0) ? count / pageSize : count / pageSize + 1;
            //判断请求页数是否大于总页数
            if (pageNum > total) {
                return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
            }
            responseList.setTotal(total);
            responseList.setList(messageContents);
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), responseList);
        }
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
    }

    /**
     * 获取医生信息
     *
     * @param accids
     * @return
     */
    @Override
    public ResponseData inquireOfDoctorInformation(ArrayList<String> accids) throws Exception {

        List<Uinfos> responseList = new ArrayList<>();
        long t1 = System.currentTimeMillis();
        for (String accid : accids) {
            logger.info("accid：" + accid);
            List<Uinfos> uinfosRep = uinfosMapper.inquireOfDoctorInformation(accid);
            if (uinfosRep != null && uinfosRep.size() > 0) {
                for (int i = 0; i <= uinfosRep.size(); i++) {
                    if (i == 0) {
                        Uinfos uinfos = uinfosRep.get(i);
                        responseList.add(uinfos);
                    }
                }
            } else {
                Uinfos uinfos = new Uinfos();
                responseList.add(uinfos);
            }
        }
        long t2 = System.currentTimeMillis();
        logger.info("===查询医生信息===响应时间t2=" + (t2) + ",---响应时长---" + (t2 - t1));
        if (responseList != null && responseList.size() > 0) {
            return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), responseList);
        }
        return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), null);
    }

    /**
     * 消息撤回
     *
     * @param params
     * @return
     */

    public ResponseData recallMessage(Map<String, Object> params) throws ESBException, SystemException {

        //设置请求参数
        params.put("requestType", "RECALL");
        String result = HttpRequest.post(netServerHostAndPort + Constants.INSTANT_MESSAGING, JsonUtils.toJSon(params), ContentType.APPLICATION_JSON);
        if (result != null) {
            Map<String, Object> resultMap = JsonUtils.readValue(result, Map.class);
            if (200 == (Integer) resultMap.get("code")) {
                return new ResponseData(StatusCode.OK.getCode(), StatusCode.OK.getMsg(), 1);
            } else {
                return new ResponseData("" + (Integer) resultMap.get("code"), (String) resultMap.get("desc"), 0);
            }
        }
        return new ResponseData(StatusCode.INSIDE_ERROR.getCode(), StatusCode.INSIDE_ERROR.getMsg(), 0);
    }
}
