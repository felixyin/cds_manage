/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crontab.rkeywordleadin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.crontab.rkeywordleadin.entity.CdsrCrontabKeywordLeadIn;
import com.jeeplus.modules.crontab.rkeywordleadin.mapper.CdsrCrontabKeywordLeadInMapper;

/**
 * 定时任务关联Service
 * @author 尹彬
 * @version 2019-03-27
 */
@Service
@Transactional(readOnly = true)
public class CdsrCrontabKeywordLeadInService extends CrudService<CdsrCrontabKeywordLeadInMapper, CdsrCrontabKeywordLeadIn> {

	public CdsrCrontabKeywordLeadIn get(String id) {
		return super.get(id);
	}
	
	public List<CdsrCrontabKeywordLeadIn> findList(CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn) {
		return super.findList(cdsrCrontabKeywordLeadIn);
	}
	
	public Page<CdsrCrontabKeywordLeadIn> findPage(Page<CdsrCrontabKeywordLeadIn> page, CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn) {
		return super.findPage(page, cdsrCrontabKeywordLeadIn);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn) {
		super.save(cdsrCrontabKeywordLeadIn);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn) {
		super.delete(cdsrCrontabKeywordLeadIn);
	}
	
}