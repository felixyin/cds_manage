/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.template.entity;

import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 我的邮件模板Entity
 * @author 尹彬
 * @version 2019-03-25
 */
public class CdsEmailTemplate extends DataEntity<CdsEmailTemplate> {
	
	private static final long serialVersionUID = 1L;
	private User owner;		// 所属用户
	private String subject;		// 邮件标题
	private String mainBody;		// 正文内容
	private Integer isEnable;		// 是否启用
	
	public CdsEmailTemplate() {
		super();
	}

	public CdsEmailTemplate(String id){
		super(id);
	}

	@NotNull(message="所属用户不能为空")
	@ExcelField(title="所属用户", fieldType=User.class, value="owner.name", align=2, sort=1)
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	@ExcelField(title="邮件标题", align=2, sort=2)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@ExcelField(title="正文内容", align=2, sort=3)
	public String getMainBody() {
		return mainBody;
	}

	public void setMainBody(String mainBody) {
		this.mainBody = mainBody;
	}
	
	@NotNull(message="是否启用不能为空")
	@ExcelField(title="是否启用", dictType="is_enable", align=2, sort=4)
	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
	
}