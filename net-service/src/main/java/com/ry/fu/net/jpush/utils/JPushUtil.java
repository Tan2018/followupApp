package com.ry.fu.net.jpush.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleResult;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * @author Borehain
 * @version V1.0.0
 * @Date 2018/6/4
 * @description 极光推送工具类
 */
public class JPushUtil {
	/**
	 * 极光-调用推送消息时，设置保留的时长(默认一天)
	 */
	private static long TIME_TO_LIVE = 60 * 60 * 24;

	/**
	 * 极光-APNS开发环境（true-生产环境，false-开发环境）
	 */
	private static Boolean APNS_PRODUCTION = false;

	public static JPushClient jPushClient = null;

	/**
	 * 所有平台，所有设备
	 * @param msg_content 消息内容
	 * @return
	 */

	public static PushPayload buildPushObject_all_all(String msg_content){

		return PushPayload.alertAll(msg_content);
	}

	/**
	 * 推送平台为android和ios，推送目标是：别名推送。注意：一次推送最多1000个
	 * @param alias 目标别名
	 * @param notification_title 通知内容标题
	 * @param title 安卓端标题
	 * @return
	 */

	public static PushPayload buildPushObject_android_ios_alias(String alias,String title,String notification_title){
        return buildPushObject_android_ios_alias(alias,notification_title,title,null,0);
	}

	public static PushPayload buildPushObject_android_ios_alias(String alias,String title,String notification_title,Map<String,String> extrasparam){
        return buildPushObject_android_ios_alias(alias,notification_title,title,extrasparam,0);
	}

	public static PushPayload buildPushObject_android_ios_alias(List<String> doctorIds,String title,String notification_title,Map<String,String> extrasparam){
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())//设置接收平台
				.setAudience(Audience.alias(doctorIds))//设置接收目标
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder()
										.setTitle(title)//安卓端标题
                                        .setAlert(notification_title)
										.addExtras(extrasparam).build())
						.addPlatformNotification(
								IosNotification.newBuilder()
                                        .setAlert(IosAlert.newBuilder().setTitleAndBody(title,"",notification_title).build())
										.setBadge(1)
										.incrBadge(1)
										.addExtras(extrasparam).build()).build()
				)//设置通知消息标题
				.setOptions(Options.newBuilder().setTimeToLive(TIME_TO_LIVE)//设置消息离线保存时长
						.setApnsProduction(APNS_PRODUCTION).build())//设置是否是生产环境
				.build();
	}



	/**
	 * 推送平台为android和ios，推送目标是：别名推送。注意：一次推送最多1000个
	 * @param alias 目标别名
	 * @param notification_title 通知内容标题
	 * @param title 安卓端标题
	 * @param pushType 业务类型
	 * @param jsonObject 业务对象
	 * @return
	 */

	public static PushPayload buildPushObject_android_ios_alias(String alias,String notification_title,String title,Integer pushType,JsonObject jsonObject){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android_ios())//设置接收平台
				.setAudience(Audience.alias(alias))//设置接收对象
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder()
										.setTitle(title)//安卓端标题
										.setAlert(notification_title)
										.addExtra("pushType",pushType).addExtra("data",jsonObject)
										.build())
						.addPlatformNotification(
								IosNotification.newBuilder()
										.setAlert(IosAlert.newBuilder().setTitleAndBody(title,"",notification_title).build())
										.incrBadge(1)
										.setBadge(0)
										.addExtra("pushType",pushType).addExtra("data",jsonObject)
										.build()).build()
				)//设置通知消息标题
				.setOptions(Options.newBuilder().setTimeToLive(TIME_TO_LIVE)//设置消息离线保存时长
						.setApnsProduction(APNS_PRODUCTION).build())//设置是否是生产环境
				.build();
	}


	/**
	 * 推送平台为android和ios，推送目标是：别名推送。注意：一次推送最多1000个
	 * @param alias 目标别名
	 * @param notification_title 通知内容标题
	 * @param title 安卓端标题
	 * @param extras 扩展字段
	 * @return
	 */

	public static PushPayload buildPushObject_android_ios_alias(String alias,String notification_title,String title,Map<String,String> extras,Integer pushType){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android_ios())//设置接收平台
				.setAudience(Audience.alias(alias))//设置接收对象
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder()
										.setTitle(title)//安卓端标题
										.setAlert(notification_title)
										.addExtra("pushType",pushType).addExtras(extras)
										.build())
						.addPlatformNotification(
								IosNotification.newBuilder()
										.setAlert(IosAlert.newBuilder().setTitleAndBody(title,"",notification_title).build())
										.setBadge(1)
										.incrBadge(1)
										.addExtra("pushType",pushType).addExtras(extras)
										.build()).build()
				)//设置通知消息标题
				.setOptions(Options.newBuilder().setTimeToLive(TIME_TO_LIVE)//设置消息离线保存时长
						.setApnsProduction(APNS_PRODUCTION).build())//设置是否是生产环境
				.build();
	}


	/**
	 * 推送平台是android和ios，推送目标是：标签推送。注意：一次最多推送20个
	 * @param tags 目标标签
	 * @param notification_title 通知消息内容
	 * @param title 标题
	 * @return
	 */

	public static PushPayload buildPushObject_android_tag(List<String> tags,String notification_title,String title,Map<String,String> extrasparam){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())//设置接收平台
				.setAudience(Audience.tag(tags))//设置接收目标
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder()
										.setTitle(title)//安卓端标题
										.setAlert(notification_title)
										.addExtras(extrasparam).build())
						.addPlatformNotification(
								IosNotification.newBuilder()
										.setAlert(IosAlert.newBuilder().setTitleAndBody(title,"",notification_title).build())
										.incrBadge(1)
										.setBadge(0)
										.addExtras(extrasparam).build()).build()
				)//设置通知消息标题
				.setOptions(Options.newBuilder().setTimeToLive(TIME_TO_LIVE)//设置消息离线保存时长
						.setApnsProduction(APNS_PRODUCTION).build())//设置是否是生产环境
				.build();
	}

	/**
	 * 所有平台，推送目标是：设备id。注意：一次推送最多1000个
	 * @param registerids 目标设备终端id集合
	 * @param notification_title 消息内容
	 * @param extrasparam 附加字段
	 * @param title 安卓端标题
	 * @return
	 */

	public static PushPayload buildPushObject_all_registerids(List<String> registerids,String title,String notification_title,Map<String,String> extrasparam){

		return PushPayload.newBuilder()
				.setPlatform(Platform.all())//设置接收平台
				.setAudience(Audience.registrationId(registerids))//设置接收对象
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder()
										.setTitle(title)//安卓端标题
										.setAlert(notification_title)
										.addExtras(extrasparam).build())
						.addPlatformNotification(
								IosNotification.newBuilder()
										.setAlert(IosAlert.newBuilder().setTitleAndBody(title,"",notification_title).build())
										.incrBadge(1)
										.setBadge(0)
										.addExtras(extrasparam).build()).build()
				)//设置通知消息标题
				.setOptions(Options.newBuilder().setTimeToLive(TIME_TO_LIVE)//设置消息离线保存时长
						.setApnsProduction(APNS_PRODUCTION).build())//设置是否是生产环境
				.build();
	}

	/**
	 * 定时推送，推送平台是android和ios，推送目标为：别名推送。注意：最多有100个定时任务
	 * @param alias 目标别名
	 * @param name 计划名称
	 * @param time 推送时间
	 * @param notification_title 消息通知标题
	 * @param title 安卓端标题
	 * @return ScheduleResult
	 * @throws APIRequestException
	 * @throws APIConnectionException
	 */

	public static ScheduleResult buildPushObject_android_ios_alias_schedule(String alias,String name,String time,String notification_title,String title) throws APIConnectionException, APIRequestException{

		PushPayload push = PushPayload.newBuilder()
				.setPlatform(Platform.android_ios())//设置接收平台
				.setAudience(Audience.alias(alias))//设置接收对象
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(
								AndroidNotification.newBuilder()
										.setTitle(title)//安卓端标题
										.setAlert(notification_title)
										.build())
						.addPlatformNotification(
								IosNotification.newBuilder()
										.setAlert(IosAlert.newBuilder().setTitleAndBody(title,"",notification_title).build())
										.incrBadge(1)
										.setBadge(0)
										.build()).build()
				)//设置通知消息标题
				.setOptions(Options.newBuilder().setTimeToLive(TIME_TO_LIVE)//设置消息离线保存时长
						.setApnsProduction(APNS_PRODUCTION).build())//设置是否是生产环境
				.build();
		return jPushClient.createSingleSchedule(name, time, push);
	}
}
