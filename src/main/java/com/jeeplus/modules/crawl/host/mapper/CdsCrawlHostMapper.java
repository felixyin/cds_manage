/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.host.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.crawl.host.entity.CdsCrawlHost;

/**
 * 爬取的域名MAPPER接口
 * @author 尹彬
 * @version 2019-03-24
 */
@MyBatisMapper
public interface CdsCrawlHostMapper extends BaseMapper<CdsCrawlHost> {
	
}