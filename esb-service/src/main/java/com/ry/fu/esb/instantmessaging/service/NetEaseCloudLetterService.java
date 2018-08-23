package com.ry.fu.esb.instantmessaging.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 网易云信service
 */
public interface NetEaseCloudLetterService {


    /**
     * 注册或者更新网易云信密码
     *
     * @param accid
     * @param token
     * @param name
     * @return
     * @throws Exception
     */
    ResponseData registerOrUpdateToken(String accid, String token, String name) throws Exception;

    /**
     * 创建网易云通信ID
     *
     * @param accid 要注册的网易云通信id
     * @param token 要注册的token值
     * @param name  网易云通信ID昵称
     * @return
     * @throws Exception
     */
    public String registerNetEaseCloudCommunicationId(String accid, String token, String name) throws Exception;

    /**
     * 修改网易云信密码
     *
     * @param accid 网易云信id
     * @param token 网易云信token
     * @return
     */
    public ResponseData updateNetEaseCloudCommunicationId(String accid, String token) throws Exception;

    /**
     * 保存消息内容
     *
     * @param request 保存的参数
     */
    public void saveMessageContent(String request);

    /**
     * 查询单聊历史记录
     *
     * @param fromAccount 该用户注册的网易云通信id
     * @param to          接收人
     * @param pageSize    每页显示条数
     * @return
     */
    public ResponseData selectMessageContent(String fromAccount, String to, Integer pageSize, Long startTime) throws SystemException, ParseException;

    /**
     * 查询最近会话列表(分页，排序)
     *
     * @param accid 该用户注册的网易云通信id
     * @return
     */
    public ResponseData selectMessageLis(String accid, Integer pageNum, Integer pageSize);

    /**
     * 点击聊天界面查询近3天的聊条记录(分页,排序)
     *
     * @param fromaccount 发送人
     * @param to          接收人
     * @return
     */
    public ResponseData selectRecentRecords(String fromaccount, String to, Integer pageNum, Integer pageSize);

    /**
     * 获取医生信息
     *
     * @param accids
     * @return
     */
    public ResponseData inquireOfDoctorInformation(ArrayList<String> accids) throws Exception;

    /**
     * 消息撤回
     *
     * @param params
     * @return
     */
    public ResponseData recallMessage(Map<String, Object> params) throws ESBException, SystemException;
}
