/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.history.entity;

import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.email.ruser.entity.CdsrEmailUser;
import org.hibernate.validator.constraints.Email;
import com.jeeplus.modules.crawl.email.entity.CdsCrawlEmail;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 发件历史Entity
 * @author 尹彬
 * @version 2019-03-21
 */
public class CdsEmailHistory extends DataEntity<CdsEmailHistory> {
	
	private static final long serialVersionUID = 1L;
	private User sendBy;		// 发件人
	private Date sendDate;		// 发送时间
	private CdsrEmailUser sendEmail;		// 发件箱外键
	private CdsCrawlEmail inboxEmail;		// 收件箱外键
	private Integer emailSendStatus;		// 发送状态（0：失败；1：成功）
	private Date beginSendDate;		// 开始 发送时间
	private Date endSendDate;		// 结束 发送时间
	
	public CdsEmailHistory() {
		super();
	}

	public CdsEmailHistory(String id){
		super(id);
	}

	@NotNull(message="发件人不能为空")
	@ExcelField(title="发件人", fieldType=User.class, value="sendBy.name", align=2, sort=1)
	public User getSendBy() {
		return sendBy;
	}

	public void setSendBy(User sendBy) {
		this.sendBy = sendBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="发送时间不能为空")
	@ExcelField(title="发送时间", align=2, sort=2)
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	@NotNull(message="发件箱外键不能为空")
	@Email(message="发件箱外键必须为合法邮箱")
	@ExcelField(title="发件箱外键", align=2, sort=3)
	public CdsrEmailUser getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(CdsrEmailUser sendEmail) {
		this.sendEmail = sendEmail;
	}
	
	@NotNull(message="收件箱外键不能为空")
	@Email(message="收件箱外键必须为合法邮箱")
	@ExcelField(title="收件箱外键", align=2, sort=4)
	public CdsCrawlEmail getInboxEmail() {
		return inboxEmail;
	}

	public void setInboxEmail(CdsCrawlEmail inboxEmail) {
		this.inboxEmail = inboxEmail;
	}
	
	@NotNull(message="发送状态（0：失败；1：成功）不能为空")
	@ExcelField(title="发送状态（0：失败；1：成功）", dictType="email_send_status", align=2, sort=5)
	public Integer getEmailSendStatus() {
		return emailSendStatus;
	}

	public void setEmailSendStatus(Integer emailSendStatus) {
		this.emailSendStatus = emailSendStatus;
	}
	
	public Date getBeginSendDate() {
		return beginSendDate;
	}

	public void setBeginSendDate(Date beginSendDate) {
		this.beginSendDate = beginSendDate;
	}
	
	public Date getEndSendDate() {
		return endSendDate;
	}

	public void setEndSendDate(Date endSendDate) {
		this.endSendDate = endSendDate;
	}
		
}