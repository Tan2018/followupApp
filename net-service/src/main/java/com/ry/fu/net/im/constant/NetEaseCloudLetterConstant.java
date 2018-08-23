package com.ry.fu.net.im.constant;
/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 网易云信常量类
 */
public class NetEaseCloudLetterConstant {
    //测试环境网易云信appkey
    public final static String TEST_APP_KEY = "f69e52b91dbd82b140f59f18dea20564";
    //测试环境网易云信appSecret
    public final static String TEST_APP_SECRET= "5eeb96db5728";
    //正式环境网易云信appkey
    public final static String APP_KEY = "2924fa25a314a972e63b77348e8b7c3b";
    //正式环境网易云信appSecret
    public final static String APP_SECRET= "3c3bcfaae0ce";
    //网易云注册请求路径
    public final static String CREATE_COMMUNICATION_ID = "https://api.netease.im/nimserver/user/create.action";
    //修改网易云通信密码请求路径
    public final static String UPDATE_ACCID = "https://api.netease.im/nimserver/user/update.action";
    //获取用户名片请求路径
    public final static String GET_USERINFO = "https://api.netease.im/nimserver/user/getUinfos.action";
    //消息撤回
    public final static String RECALL = "https://api.netease.im/nimserver/msg/recall.action";
}
