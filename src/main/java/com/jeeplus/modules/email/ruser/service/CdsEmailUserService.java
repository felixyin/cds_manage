/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.ruser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.email.ruser.entity.CdsEmailUser;
import com.jeeplus.modules.email.ruser.mapper.CdsEmailUserMapper;

/**
 * 我的邮箱Service
 * @author 尹彬
 * @version 2019-03-21
 */
@Service
@Transactional(readOnly = true)
public class CdsEmailUserService extends CrudService<CdsEmailUserMapper, CdsEmailUser> {

	public CdsEmailUser get(String id) {
		return super.get(id);
	}
	
	public List<CdsEmailUser> findList(CdsEmailUser cdsEmailUser) {
		return super.findList(cdsEmailUser);
	}
	
	public Page<CdsEmailUser> findPage(Page<CdsEmailUser> page, CdsEmailUser cdsEmailUser) {
		return super.findPage(page, cdsEmailUser);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsEmailUser cdsEmailUser) {
		super.save(cdsEmailUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsEmailUser cdsEmailUser) {
		super.delete(cdsEmailUser);
	}
	
}