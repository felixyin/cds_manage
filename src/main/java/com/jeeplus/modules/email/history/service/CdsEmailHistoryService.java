/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.history.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.email.history.entity.CdsEmailHistory;
import com.jeeplus.modules.email.history.mapper.CdsEmailHistoryMapper;

/**
 * 发件历史Service
 * @author 尹彬
 * @version 2019-03-21
 */
@Service
@Transactional(readOnly = true)
public class CdsEmailHistoryService extends CrudService<CdsEmailHistoryMapper, CdsEmailHistory> {

	public CdsEmailHistory get(String id) {
		return super.get(id);
	}
	
	public List<CdsEmailHistory> findList(CdsEmailHistory cdsEmailHistory) {
		return super.findList(cdsEmailHistory);
	}
	
	public Page<CdsEmailHistory> findPage(Page<CdsEmailHistory> page, CdsEmailHistory cdsEmailHistory) {
		return super.findPage(page, cdsEmailHistory);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsEmailHistory cdsEmailHistory) {
		super.save(cdsEmailHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsEmailHistory cdsEmailHistory) {
		super.delete(cdsEmailHistory);
	}
	
}