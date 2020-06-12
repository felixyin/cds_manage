/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.url.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.crawl.url.entity.CdsCrawlUrl;

/**
 * 爬取地址MAPPER接口
 * @author 尹彬
 * @version 2019-03-21
 */
@MyBatisMapper
public interface CdsCrawlUrlMapper extends BaseMapper<CdsCrawlUrl> {
	
}