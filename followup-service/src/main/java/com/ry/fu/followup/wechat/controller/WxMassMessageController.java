package com.ry.fu.followup.wechat.controller;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/5 21:46
 * @description 微信群发消息
 */
@RestController
@RequestMapping("/wechat/mass")
public class WxMassMessageController {

    @Autowired
    private WxMpService wxService;

    @GetMapping(value = "/sendText", produces = "text/plain;charset=utf-8")
    public WxMpMassSendResult sendText() throws WxErrorException {
        WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
        massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        massMessage.setContent("测试群发消息\n欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");
        List<String> users = massMessage.getToUsers();
        users.add("oSDKjwTNgoV8bbyfDtFucwX9e4NA");
        users.add("oSDKjwQGp_ALAUGgzabrgouyzJI8");

        return this.wxService.getMassMessageService().massOpenIdsMessageSend(massMessage);
    }


}
