/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.history.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.email.history.entity.CdsEmailHistory;

/**
 * 发件历史MAPPER接口
 * @author 尹彬
 * @version 2019-03-21
 */
@MyBatisMapper
public interface CdsEmailHistoryMapper extends BaseMapper<CdsEmailHistory> {
	
}