/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.email.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.crawl.email.entity.CdsCrawlEmail;
import com.jeeplus.modules.crawl.email.mapper.CdsCrawlEmailMapper;

/**
 * 爬取的邮箱Service
 * @author 尹彬
 * @version 2019-04-03
 */
@Service
@Transactional(readOnly = true)
public class CdsCrawlEmailService extends CrudService<CdsCrawlEmailMapper, CdsCrawlEmail> {

	public CdsCrawlEmail get(String id) {
		return super.get(id);
	}
	
	public List<CdsCrawlEmail> findList(CdsCrawlEmail cdsCrawlEmail) {
		return super.findList(cdsCrawlEmail);
	}
	
	public Page<CdsCrawlEmail> findPage(Page<CdsCrawlEmail> page, CdsCrawlEmail cdsCrawlEmail) {
		return super.findPage(page, cdsCrawlEmail);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsCrawlEmail cdsCrawlEmail) {
		super.save(cdsCrawlEmail);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsCrawlEmail cdsCrawlEmail) {
		super.delete(cdsCrawlEmail);
	}
	
}