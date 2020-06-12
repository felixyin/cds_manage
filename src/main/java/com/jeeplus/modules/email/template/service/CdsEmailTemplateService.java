/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.template.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.email.template.entity.CdsEmailTemplate;
import com.jeeplus.modules.email.template.mapper.CdsEmailTemplateMapper;

/**
 * 我的邮件模板Service
 * @author 尹彬
 * @version 2019-03-25
 */
@Service
@Transactional(readOnly = true)
public class CdsEmailTemplateService extends CrudService<CdsEmailTemplateMapper, CdsEmailTemplate> {

	public CdsEmailTemplate get(String id) {
		return super.get(id);
	}
	
	public List<CdsEmailTemplate> findList(CdsEmailTemplate cdsEmailTemplate) {
		return super.findList(cdsEmailTemplate);
	}
	
	public Page<CdsEmailTemplate> findPage(Page<CdsEmailTemplate> page, CdsEmailTemplate cdsEmailTemplate) {
		return super.findPage(page, cdsEmailTemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsEmailTemplate cdsEmailTemplate) {
		super.save(cdsEmailTemplate);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsEmailTemplate cdsEmailTemplate) {
		super.delete(cdsEmailTemplate);
	}
	
}