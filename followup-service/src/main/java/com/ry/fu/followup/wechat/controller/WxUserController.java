package com.ry.fu.followup.wechat.controller;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/5 21:34
 * @description 微信用户管理
 */
@RestController
@RequestMapping("/wechat/user")
public class WxUserController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 得到用户信息
     * @return
     * @throws WxErrorException
     */
    @GetMapping(value = "/getUserInfo", produces = "text/plain;charset=utf-8")
    public WxMpUser getUserInfo() throws WxErrorException {
        String lang = "zh_CN"; //语言
        WxMpUser user = wxMpService.getUserService().userInfo("oSDKjwQGp_ALAUGgzabrgouyzJI8", lang);
        return user;
    }

    /**
     * 得到用户List
     * @return
     * @throws WxErrorException
     */
    @GetMapping(value = "/getUserList", produces = "text/plain;charset=utf-8")
    public WxMpUserList getUserList() throws WxErrorException {
        return wxMpService.getUserService().userList("oSDKjwQGp_ALAUGgzabrgouyzJI8");
    }

    /**
     * 查询用户标签列表
     * @return
     * @throws WxErrorException
     */
    @GetMapping(value = "/getUserTagList", produces = "text/plain;charset=utf-8")
    public List<Long> getUserTagList() throws WxErrorException {
        return wxMpService.getUserTagService().userTagList("oSDKjwQGp_ALAUGgzabrgouyzJI8");
    }



}
