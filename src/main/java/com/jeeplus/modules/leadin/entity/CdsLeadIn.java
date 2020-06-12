/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leadin.entity;

import org.hibernate.validator.constraints.Email;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 导入的客户信息Entity
 * @author 尹彬
 * @version 2019-04-07
 */
public class CdsLeadIn extends DataEntity<CdsLeadIn> {
	
	private static final long serialVersionUID = 1L;
	private String category;		// 类别
	private String company;		// 公司名称
	private String area;		// 地区
	private String website;		// 公司网址
	private String email;		// 邮箱
	private Integer isValidEmail;		// 邮箱有效性
	private String contacts;		// 联系人
	private String phone;		// 联系电话
	private String fax;		// 传真
	private String address;		// 地址
	private String msn;		// MSN
	private String skype;		// SKYPE
	
	public CdsLeadIn() {
		super();
	}

	public CdsLeadIn(String id){
		super(id);
	}

	@ExcelField(title="类别", align=2, sort=1)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@ExcelField(title="公司名称", align=2, sort=2)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@ExcelField(title="地区", align=2, sort=3)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@ExcelField(title="公司网址", align=2, sort=4)
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Email(message="邮箱必须为合法邮箱")
	@ExcelField(title="邮箱", align=2, sort=5)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="邮箱有效性", dictType="is_valid_email", align=2, sort=6)
	public Integer getIsValidEmail() {
		return isValidEmail;
	}

	public void setIsValidEmail(Integer isValidEmail) {
		this.isValidEmail = isValidEmail;
	}
	
	@ExcelField(title="联系人", align=2, sort=7)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@ExcelField(title="联系电话", align=2, sort=8)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="传真", align=2, sort=9)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@ExcelField(title="地址", align=2, sort=10)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="MSN", align=2, sort=11)
	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}
	
	@ExcelField(title="SKYPE", align=2, sort=12)
	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}
	
}