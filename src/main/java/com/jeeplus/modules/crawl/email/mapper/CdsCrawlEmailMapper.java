/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.email.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.crawl.email.entity.CdsCrawlEmail;

/**
 * 爬取的邮箱MAPPER接口
 * @author 尹彬
 * @version 2019-04-03
 */
@MyBatisMapper
public interface CdsCrawlEmailMapper extends BaseMapper<CdsCrawlEmail> {
	
}