/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crontab.setting.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.crontab.setting.entity.CdsCrontabSetting;
import com.jeeplus.modules.crontab.setting.mapper.CdsCrontabSettingMapper;

/**
 * 邮箱定时任务Service
 * @author 尹彬
 * @version 2019-03-27
 */
@Service
@Transactional(readOnly = true)
public class CdsCrontabSettingService extends CrudService<CdsCrontabSettingMapper, CdsCrontabSetting> {

	public CdsCrontabSetting get(String id) {
		return super.get(id);
	}
	
	public List<CdsCrontabSetting> findList(CdsCrontabSetting cdsCrontabSetting) {
		return super.findList(cdsCrontabSetting);
	}
	
	public Page<CdsCrontabSetting> findPage(Page<CdsCrontabSetting> page, CdsCrontabSetting cdsCrontabSetting) {
		return super.findPage(page, cdsCrontabSetting);
	}
	
	@Transactional(readOnly = false)
	public void save(CdsCrontabSetting cdsCrontabSetting) {
		super.save(cdsCrontabSetting);
	}
	
	@Transactional(readOnly = false)
	public void delete(CdsCrontabSetting cdsCrontabSetting) {
		super.delete(cdsCrontabSetting);
	}
	
}