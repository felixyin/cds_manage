/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.setting.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.crawl.setting.entity.CdsCrawlSetting;
import com.jeeplus.modules.crawl.setting.mapper.CdsCrawlSettingMapper;

/**
 * 爬取参数设置Service
 * @author 尹彬
 * @version 2019-03-21
 */
@Service
@Transactional(readOnly = true)
public class CdsCrawlSettingService extends CrudService<CdsCrawlSettingMapper, CdsCrawlSetting> {

	public CdsCrawlSetting get(String id) {
		return super.get(id);
	}
	
	public List<CdsCrawlSetting> findList(CdsCrawlSetting cdsCrawlSetting) {
		return super.findList(cdsCrawlSetting);
	}
	
	public Page<CdsCrawlSetting> findPage(Page<CdsCrawlSetting> page, CdsCrawlSetting cdsCrawlSetting) {
		return super.findPage(page, cdsCrawlSetting);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsCrawlSetting cdsCrawlSetting) {
		super.save(cdsCrawlSetting);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsCrawlSetting cdsCrawlSetting) {
		super.delete(cdsCrawlSetting);
	}
	
}