/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.keyword_seeker.entity;

import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.keyword.entity.CdsKeyword;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 关键词搜索记录Entity
 * @author 尹彬
 * @version 2019-03-24
 */
public class CdsrKeywordSeeker extends DataEntity<CdsrKeywordSeeker> {
	
	private static final long serialVersionUID = 1L;
	private User owner;		// 搜索人
	private CdsKeyword keyword;		// 关键词外键
	private Date searchTime;		// 最后搜索时间
	private Date beginSearchTime;		// 开始 最后搜索时间
	private Date endSearchTime;		// 结束 最后搜索时间

	public CdsrKeywordSeeker() {
		super();
	}

	public CdsrKeywordSeeker(String id){
		super(id);
	}

	@NotNull(message="搜索人不能为空")
	@ExcelField(title="搜索人", fieldType=User.class, value="owner.name", align=2, sort=1)
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	@NotNull(message="关键词外键不能为空")
	@ExcelField(title="关键词外键", align=2, sort=2)
	public CdsKeyword getKeyword() {
		return keyword;
	}

	public void setKeyword(CdsKeyword keyword) {
		this.keyword = keyword;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后搜索时间", align=2, sort=3)
	public Date getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(Date searchTime) {
		this.searchTime = searchTime;
	}
	
	public Date getBeginSearchTime() {
		return beginSearchTime;
	}

	public void setBeginSearchTime(Date beginSearchTime) {
		this.beginSearchTime = beginSearchTime;
	}
	
	public Date getEndSearchTime() {
		return endSearchTime;
	}

	public void setEndSearchTime(Date endSearchTime) {
		this.endSearchTime = endSearchTime;
	}
		
}