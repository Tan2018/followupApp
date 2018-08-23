package com.ry.fu.followup.wechat.controller;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/5 20:19
 * @description 微信客服-主动推送
 */
@RestController
@RequestMapping("/wechat/kefu")
public class WxKefuController {

    @Autowired
    private WxMpService wxMpService;

    @GetMapping(value = "/sendText", produces = "text/plain;charset=utf-8")
    public String sendText() throws WxErrorException {
        //测试，后面需要动态获取OpenId
        WxMpKefuMessage wxMpKefuMessage = WxMpKefuMessage.TEXT().toUser("oSDKjwQGp_ALAUGgzabrgouyzJI8").content("微信客服文本消息").build();
        wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
        return "Success";
    }

    @GetMapping(value = "/sendImage", produces = "text/plain;charset=utf-8")
    public String sendImage() throws WxErrorException {
        WxMpKefuMessage wxMpKefuMessage = WxMpKefuMessage.IMAGE().toUser("oSDKjwQGp_ALAUGgzabrgouyzJI8").mediaId("MEDIA_ID").build();

        wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
        return "Success";
    }

}
