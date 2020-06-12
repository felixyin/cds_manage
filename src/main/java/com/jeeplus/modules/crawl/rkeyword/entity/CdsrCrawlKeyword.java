/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.rkeyword.entity;

import com.jeeplus.modules.crawl.url.entity.CdsCrawlUrl;
import com.jeeplus.modules.keyword.entity.CdsKeyword;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 关键词与爬取对应关系Entity
 * @author 尹彬
 * @version 2019-03-21
 */
public class CdsrCrawlKeyword extends DataEntity<CdsrCrawlKeyword> {
	
	private static final long serialVersionUID = 1L;
	private CdsCrawlUrl crawlUrl;		// 抓取URL外建
	private CdsKeyword keyword;		// 关键词外键
	
	public CdsrCrawlKeyword() {
		super();
	}

	public CdsrCrawlKeyword(String id){
		super(id);
	}

	@ExcelField(title="抓取URL外建", fieldType=CdsCrawlUrl.class, value="crawlUrl.title", align=2, sort=1)
	public CdsCrawlUrl getCrawlUrl() {
		return crawlUrl;
	}

	public void setCrawlUrl(CdsCrawlUrl crawlUrl) {
		this.crawlUrl = crawlUrl;
	}
	
	@ExcelField(title="关键词外键", fieldType=CdsKeyword.class, value="keyword.keyword", align=2, sort=2)
	public CdsKeyword getKeyword() {
		return keyword;
	}

	public void setKeyword(CdsKeyword keyword) {
		this.keyword = keyword;
	}
	
}