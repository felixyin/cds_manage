/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.category.entity;

import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 邮箱分类Entity
 * @author 尹彬
 * @version 2019-04-03
 */
public class CdsEmailCategory extends DataEntity<CdsEmailCategory> {
	
	private static final long serialVersionUID = 1L;
	private User owner;		// 所有者
	private String categoryName;		// 分类名称
	private String server;		// 服务器
	private String host;		// 服务器地址
	private Integer port;		// 端口
	private Integer isEnable;		// 是否启用

	private String emailAccounts; // 发件邮箱账号拼接字符串：账号，密码 换好新一组

	public CdsEmailCategory() {
		super();
	}

	public CdsEmailCategory(String id){
		super(id);
	}

	@ExcelField(title="所有者", fieldType=User.class, value="owner.name", align=2, sort=1)
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	@ExcelField(title="分类名称", dictType="email_category_name", align=2, sort=2)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@ExcelField(title="服务器", align=2, sort=3)
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	@ExcelField(title="服务器地址", align=2, sort=4)
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	@NotNull(message="端口不能为空")
	@ExcelField(title="端口", align=2, sort=5)
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	@ExcelField(title="是否启用", dictType="is_enable", align=2, sort=6)
	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	@NotNull(message = "发件账号不能为空")
	public String getEmailAccounts() {
		return emailAccounts;
	}

	public void setEmailAccounts(String emailAccounts) {
		this.emailAccounts = emailAccounts;
	}
}