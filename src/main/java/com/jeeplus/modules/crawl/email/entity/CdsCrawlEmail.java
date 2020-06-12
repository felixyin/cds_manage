/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.email.entity;

import org.hibernate.validator.constraints.Email;
import com.jeeplus.modules.crawl.host.entity.CdsCrawlHost;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 爬取的邮箱Entity
 * @author 尹彬
 * @version 2019-04-03
 */
public class CdsCrawlEmail extends DataEntity<CdsCrawlEmail> {
	
	private static final long serialVersionUID = 1L;
	private String email;		// 收件箱
	private CdsCrawlHost crawlHost;		// 抓取域名外键
	private Integer sendTimes;		// 发送计数
	private Integer isValidEmail;		// 是否合法邮箱
	private Integer sendStatus;		// 发送状态
	private Integer beginSendTimes;		// 开始 发送计数
	private Integer endSendTimes;		// 结束 发送计数
	
	public CdsCrawlEmail() {
		super();
	}

	public CdsCrawlEmail(String id){
		super(id);
	}

	@Email(message="收件箱必须为合法邮箱")
	@ExcelField(title="收件箱", align=2, sort=1)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="抓取域名外键", fieldType=CdsCrawlHost.class, value="crawlHost.host", align=2, sort=2)
	public CdsCrawlHost getCrawlHost() {
		return crawlHost;
	}

	public void setCrawlHost(CdsCrawlHost crawlHost) {
		this.crawlHost = crawlHost;
	}
	
	@ExcelField(title="发送计数", align=2, sort=3)
	public Integer getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}
	
	@ExcelField(title="是否合法邮箱", dictType="is_valid_email", align=2, sort=4)
	public Integer getIsValidEmail() {
		return isValidEmail;
	}

	public void setIsValidEmail(Integer isValidEmail) {
		this.isValidEmail = isValidEmail;
	}
	
	@ExcelField(title="发送状态", dictType="email_send_status", align=2, sort=5)
	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
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