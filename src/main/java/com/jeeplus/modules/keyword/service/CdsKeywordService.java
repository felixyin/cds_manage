/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.keyword.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.keyword.entity.CdsKeyword;
import com.jeeplus.modules.keyword.mapper.CdsKeywordMapper;

/**
 * 关键词Service
 * @author 尹彬
 * @version 2019-03-21
 */
@Service
@Transactional(readOnly = true)
public class CdsKeywordService extends CrudService<CdsKeywordMapper, CdsKeyword> {

	public CdsKeyword get(String id) {
		return super.get(id);
	}
	
	public List<CdsKeyword> findList(CdsKeyword cdsKeyword) {
		return super.findList(cdsKeyword);
	}
	
	public Page<CdsKeyword> findPage(Page<CdsKeyword> page, CdsKeyword cdsKeyword) {
		return super.findPage(page, cdsKeyword);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsKeyword cdsKeyword) {
		super.save(cdsKeyword);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsKeyword cdsKeyword) {
		super.delete(cdsKeyword);
	}
	
}