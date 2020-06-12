/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.keyword_seeker.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.keyword_seeker.entity.CdsrKeywordSeeker;
import com.jeeplus.modules.keyword_seeker.mapper.CdsrKeywordSeekerMapper;

/**
 * 关键词搜索记录Service
 * @author 尹彬
 * @version 2019-03-24
 */
@Service
@Transactional(readOnly = true)
public class CdsrKeywordSeekerService extends CrudService<CdsrKeywordSeekerMapper, CdsrKeywordSeeker> {

	public CdsrKeywordSeeker get(String id) {
		return super.get(id);
	}
	
	public List<CdsrKeywordSeeker> findList(CdsrKeywordSeeker cdsrKeywordSeeker) {
		return super.findList(cdsrKeywordSeeker);
	}
	
	public Page<CdsrKeywordSeeker> findPage(Page<CdsrKeywordSeeker> page, CdsrKeywordSeeker cdsrKeywordSeeker) {
		return super.findPage(page, cdsrKeywordSeeker);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsrKeywordSeeker cdsrKeywordSeeker) {
		super.save(cdsrKeywordSeeker);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsrKeywordSeeker cdsrKeywordSeeker) {
		super.delete(cdsrKeywordSeeker);
	}
	
}