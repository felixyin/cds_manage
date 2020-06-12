/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leadin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.leadin.entity.CdsLeadIn;
import com.jeeplus.modules.leadin.mapper.CdsLeadInMapper;

/**
 * 导入的客户信息Service
 * @author 尹彬
 * @version 2019-04-07
 */
@Service
@Transactional(readOnly = true)
public class CdsLeadInService extends CrudService<CdsLeadInMapper, CdsLeadIn> {

	public CdsLeadIn get(String id) {
		return super.get(id);
	}
	
	public List<CdsLeadIn> findList(CdsLeadIn cdsLeadIn) {
		return super.findList(cdsLeadIn);
	}
	
	public Page<CdsLeadIn> findPage(Page<CdsLeadIn> page, CdsLeadIn cdsLeadIn) {
		return super.findPage(page, cdsLeadIn);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsLeadIn cdsLeadIn) {
		super.save(cdsLeadIn);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsLeadIn cdsLeadIn) {
		super.delete(cdsLeadIn);
	}
	
}