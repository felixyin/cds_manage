/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.url.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.crawl.url.entity.CdsCrawlUrl;
import com.jeeplus.modules.crawl.url.mapper.CdsCrawlUrlMapper;

/**
 * 爬取地址Service
 * @author 尹彬
 * @version 2019-03-21
 */
@Service
@Transactional(readOnly = true)
public class CdsCrawlUrlService extends CrudService<CdsCrawlUrlMapper, CdsCrawlUrl> {

	public CdsCrawlUrl get(String id) {
		return super.get(id);
	}
	
	public List<CdsCrawlUrl> findList(CdsCrawlUrl cdsCrawlUrl) {
		return super.findList(cdsCrawlUrl);
	}
	
	public Page<CdsCrawlUrl> findPage(Page<CdsCrawlUrl> page, CdsCrawlUrl cdsCrawlUrl) {
		return super.findPage(page, cdsCrawlUrl);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsCrawlUrl cdsCrawlUrl) {
		super.save(cdsCrawlUrl);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsCrawlUrl cdsCrawlUrl) {
		super.delete(cdsCrawlUrl);
	}
	
}