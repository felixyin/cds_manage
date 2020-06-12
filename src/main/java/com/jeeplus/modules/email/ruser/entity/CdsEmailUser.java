/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.ruser.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 我的邮箱Entity
 * @author 尹彬
 * @version 2019-03-21
 */
public class CdsEmailUser extends DataEntity<CdsEmailUser> {
	
	private static final long serialVersionUID = 1L;
	private String email;		// 发件箱外建
	private String emailSetting;		// 邮箱设置外键
	private Integer sendTimes;		// 已发次数
	private Integer beginSendTimes;		// 开始 已发次数
	private Integer endSendTimes;		// 结束 已发次数
	
	public CdsEmailUser() {
		super();
	}

	public CdsEmailUser(String id){
		super(id);
	}

	@ExcelField(title="发件箱外建", align=2, sort=1)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="邮箱设置外键", align=2, sort=2)
	public String getEmailSetting() {
		return emailSetting;
	}

	public void setEmailSetting(String emailSetting) {
		this.emailSetting = emailSetting;
	}
	
	@ExcelField(title="已发次数", align=2, sort=3)
	public Integer getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}
	
	public Integer getBeginSendTimes() {
		return beginSendTimes;
	}

	public void setBeginSendTimes(Integer beginSendTimes) {
		this.beginSendTimes = beginSendTimes;
	}
	
	public Integer getEndSendTimes() {
		return endSendTimes;
	}

	public void setEndSendTimes(Integer endSendTimes) {
		this.endSendTimes = endSendTimes;
	}
		
}