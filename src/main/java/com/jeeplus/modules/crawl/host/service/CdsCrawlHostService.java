/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.host.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.crawl.host.entity.CdsCrawlHost;
import com.jeeplus.modules.crawl.host.mapper.CdsCrawlHostMapper;

/**
 * 爬取的域名Service
 * @author 尹彬
 * @version 2019-03-24
 */
@Service
@Transactional(readOnly = true)
public class CdsCrawlHostService extends CrudService<CdsCrawlHostMapper, CdsCrawlHost> {

	public CdsCrawlHost get(String id) {
		return super.get(id);
	}
	
	public List<CdsCrawlHost> findList(CdsCrawlHost cdsCrawlHost) {
		return super.findList(cdsCrawlHost);
	}
	
	public Page<CdsCrawlHost> findPage(Page<CdsCrawlHost> page, CdsCrawlHost cdsCrawlHost) {
		return super.findPage(page, cdsCrawlHost);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsCrawlHost cdsCrawlHost) {
		super.save(cdsCrawlHost);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsCrawlHost cdsCrawlHost) {
		super.delete(cdsCrawlHost);
	}
	
}