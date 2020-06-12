/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.email.category.entity.CdsEmailCategory;
import com.jeeplus.modules.email.category.mapper.CdsEmailCategoryMapper;

/**
 * 邮箱分类Service
 * @author 尹彬
 * @version 2019-04-03
 */
@Service
@Transactional(readOnly = true)
public class CdsEmailCategoryService extends CrudService<CdsEmailCategoryMapper, CdsEmailCategory> {

	public CdsEmailCategory get(String id) {
		return super.get(id);
	}
	
	public List<CdsEmailCategory> findList(CdsEmailCategory cdsEmailCategory) {
		return super.findList(cdsEmailCategory);
	}
	
	public Page<CdsEmailCategory> findPage(Page<CdsEmailCategory> page, CdsEmailCategory cdsEmailCategory) {
		return super.findPage(page, cdsEmailCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsEmailCategory cdsEmailCategory) {
		super.save(cdsEmailCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsEmailCategory cdsEmailCategory) {
		super.delete(cdsEmailCategory);
	}
	
}