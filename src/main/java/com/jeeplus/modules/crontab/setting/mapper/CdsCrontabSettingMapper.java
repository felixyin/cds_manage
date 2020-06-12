/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crontab.setting.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.crontab.setting.entity.CdsCrontabSetting;

/**
 * 邮箱定时任务MAPPER接口
 * @author 尹彬
 * @version 2019-03-27
 */
@MyBatisMapper
public interface CdsCrontabSettingMapper extends BaseMapper<CdsCrontabSetting> {
	
}