/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.ruser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.email.ruser.entity.CdsrEmailUser;
import com.jeeplus.modules.email.ruser.mapper.CdsrEmailUserMapper;

/**
 * 我的邮箱Service
 * @author 尹彬
 * @version 2019-04-03
 */
@Service
@Transactional(readOnly = true)
public class CdsrEmailUserService extends CrudService<CdsrEmailUserMapper, CdsrEmailUser> {

	public CdsrEmailUser get(String id) {
		return super.get(id);
	}
	
	public List<CdsrEmailUser> findList(CdsrEmailUser cdsrEmailUser) {
		return super.findList(cdsrEmailUser);
	}
	
	public Page<CdsrEmailUser> findPage(Page<CdsrEmailUser> page, CdsrEmailUser cdsrEmailUser) {
		return super.findPage(page, cdsrEmailUser);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsrEmailUser cdsrEmailUser) {
		super.save(cdsrEmailUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsrEmailUser cdsrEmailUser) {
		super.delete(cdsrEmailUser);
	}
	
}