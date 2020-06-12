/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crontab.rkeywordleadin.entity;

import com.jeeplus.modules.crontab.setting.entity.CdsCrontabSetting;
import com.jeeplus.modules.keyword_seeker.entity.CdsrKeywordSeeker;
import com.jeeplus.modules.leadin.entity.CdsLeadIn;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 定时任务关联Entity
 * @author 尹彬
 * @version 2019-03-27
 */
public class CdsrCrontabKeywordLeadIn extends DataEntity<CdsrCrontabKeywordLeadIn> {
	
	private static final long serialVersionUID = 1L;
	private CdsCrontabSetting crontabSetting;		// 定时表外键
	private CdsrKeywordSeeker keywordSeeker;		// 用户关键词外键
	private CdsLeadIn leadIn;		// 导入客户表的分类
	
	public CdsrCrontabKeywordLeadIn() {
		super();
	}

	public CdsrCrontabKeywordLeadIn(String id){
		super(id);
	}

	@ExcelField(title="定时表外键", fieldType=CdsCrontabSetting.class, value="crontabSetting.name", align=2, sort=1)
	public CdsCrontabSetting getCrontabSetting() {
		return crontabSetting;
	}

	public void setCrontabSetting(CdsCrontabSetting crontabSetting) {
		this.crontabSetting = crontabSetting;
	}
	
	@ExcelField(title="用户关键词外键", align=2, sort=2)
	public CdsrKeywordSeeker getKeywordSeeker() {
		return keywordSeeker;
	}

	public void setKeywordSeeker(CdsrKeywordSeeker keywordSeeker) {
		this.keywordSeeker = keywordSeeker;
	}
	
	@ExcelField(title="导入客户表的分类", align=2, sort=3)
	public CdsLeadIn getLeadIn() {
		return leadIn;
	}

	public void setLeadIn(CdsLeadIn leadIn) {
		this.leadIn = leadIn;
	}
	
}