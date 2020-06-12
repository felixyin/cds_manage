/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.rkeyword.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.crawl.rkeyword.entity.CdsrCrawlKeyword;
import com.jeeplus.modules.crawl.rkeyword.mapper.CdsrCrawlKeywordMapper;

/**
 * 关键词与爬取对应关系Service
 * @author 尹彬
 * @version 2019-03-21
 */
@Service
@Transactional(readOnly = true)
public class CdsrCrawlKeywordService extends CrudService<CdsrCrawlKeywordMapper, CdsrCrawlKeyword> {

	public CdsrCrawlKeyword get(String id) {
		return super.get(id);
	}
	
	public List<CdsrCrawlKeyword> findList(CdsrCrawlKeyword cdsrCrawlKeyword) {
		return super.findList(cdsrCrawlKeyword);
	}
	
	public Page<CdsrCrawlKeyword> findPage(Page<CdsrCrawlKeyword> page, CdsrCrawlKeyword cdsrCrawlKeyword) {
		return super.findPage(page, cdsrCrawlKeyword);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsrCrawlKeyword cdsrCrawlKeyword) {
		super.save(cdsrCrawlKeyword);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsrCrawlKeyword cdsrCrawlKeyword) {
		super.delete(cdsrCrawlKeyword);
	}
	
}