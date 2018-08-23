/**
 * <html>
 * <body>
 *  <P> Copyright 2014 广东天泽阳光康众医疗投资管理有限公司. 粤ICP备09007530号-15</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2017年10月31日</p>
 *  <p> Created by x-lan</p>
 *  </body>
 * </html>
 */
package com.ry.fu.esb.medicaljournal.model;


import com.ry.fu.esb.common.response.BaseModel;

public class RechargeParamVo extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -499520963200295437L;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 用户账号
	 */
	private String account;

	/**
	 * 渠道ID
	 */
	private String appId;

	/**
	 * 渠道CODE
	 */
	private String appCode;

	/**
	 * 回传参数
	 */
	private String paramJson;

	/**
	 * 回传地址
	 */
	private String backUrl;

	/**
	 * 充值类型
	 */
	private String rechargeType;

	/**
	 * 充值订单号
	 */
	private String orderNo;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getParamJson() {
		return paramJson;
	}

	public void setParamJson(String paramJson) {
		this.paramJson = paramJson;
	}

	public String getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

}
