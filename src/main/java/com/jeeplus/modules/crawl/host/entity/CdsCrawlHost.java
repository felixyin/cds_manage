/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.host.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 爬取的域名Entity
 * @author 尹彬
 * @version 2019-03-24
 */
public class CdsCrawlHost extends DataEntity<CdsCrawlHost> {
	
	private static final long serialVersionUID = 1L;
	private String host;		// 域名
	private Integer isFindEmail;		// 是否有效
	private Long crawlTimes;		// 爬取次数
	private Long beginCrawlTimes;		// 开始 爬取次数
	private Long endCrawlTimes;		// 结束 爬取次数
	
	public CdsCrawlHost() {
		super();
	}

	public CdsCrawlHost(String id){
		super(id);
	}

	@ExcelField(title="域名", align=2, sort=1)
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	@ExcelField(title="是否有效", dictType="is_find_email", align=2, sort=2)
	public Integer getIsFindEmail() {
		return isFindEmail;
	}

	public void setIsFindEmail(Integer isFindEmail) {
		this.isFindEmail = isFindEmail;
	}
	
	@ExcelField(title="爬取次数", align=2, sort=3)
	public Long getCrawlTimes() {
		return crawlTimes;
	}

	public void setCrawlTimes(Long crawlTimes) {
		this.crawlTimes = crawlTimes;
	}
	
	public Long getBeginCrawlTimes() {
		return beginCrawlTimes;
	}

	public void setBeginCrawlTimes(Long beginCrawlTimes) {
		this.beginCrawlTimes = beginCrawlTimes;
	}
	
	public Long getEndCrawlTimes() {
		return endCrawlTimes;
	}

	public void setEndCrawlTimes(Long endCrawlTimes) {
		this.endCrawlTimes = endCrawlTimes;
	}
		
}