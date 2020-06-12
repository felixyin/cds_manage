/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.ruser.entity;

import org.hibernate.validator.constraints.Email;
import com.jeeplus.modules.email.category.entity.CdsEmailCategory;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 我的邮箱Entity
 * @author 尹彬
 * @version 2019-04-03
 */
public class CdsrEmailUser extends DataEntity<CdsrEmailUser> {
	
	private static final long serialVersionUID = 1L;
	private String sendEmail;		// 发件邮箱
	private String password;		// 邮箱密码
	private CdsEmailCategory emailCategory;		// 邮箱设置
	private Integer sendTimes;		// 已发次数
	private Integer beginSendTimes;		// 开始 已发次数
	private Integer endSendTimes;		// 结束 已发次数
	
	public CdsrEmailUser() {
		super();
	}

	public CdsrEmailUser(String id){
		super(id);
	}

	@Email(message="发件邮箱必须为合法邮箱")
	@ExcelField(title="发件邮箱", align=2, sort=1)
	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}
	
	@ExcelField(title="邮箱密码", align=2, sort=2)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@NotNull(message="邮箱设置不能为空")
	@ExcelField(title="邮箱设置", fieldType=CdsEmailCategory.class, value="emailCategory.categoryName", align=2, sort=3)
	public CdsEmailCategory getEmailCategory() {
		return emailCategory;
	}

	public void setEmailCategory(CdsEmailCategory emailCategory) {
		this.emailCategory = emailCategory;
	}
	
	@ExcelField(title="已发次数", align=2, sort=4)
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