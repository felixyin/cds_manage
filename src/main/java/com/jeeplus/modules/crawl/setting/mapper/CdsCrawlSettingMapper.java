/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.setting.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.crawl.setting.entity.CdsCrawlSetting;

/**
 * 爬取参数设置MAPPER接口
 * @author 尹彬
 * @version 2019-03-21
 */
@MyBatisMapper
public interface CdsCrawlSettingMapper extends BaseMapper<CdsCrawlSetting> {
	
}