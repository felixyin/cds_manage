/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.api.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.api.entity.CdsCrawlHostAndEmail;
import com.jeeplus.modules.crawl.setting.entity.CdsCrawlSetting;
import com.jeeplus.modules.keyword.entity.CdsKeyword;
import com.jeeplus.modules.keyword_seeker.entity.CdsrKeywordSeeker;
import com.jeeplus.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 爬取参数设置MAPPER接口
 *
 * @author 尹彬
 * @version 2019-03-21
 */
@MyBatisMapper
public interface CdsApiMapper {

    List<CdsKeyword> findKeywordByUser(@Param("user") User user);

    List<CdsCrawlHostAndEmail> findHostAndUrl(String host);

    List<CdsCrawlHostAndEmail> findHostAndUrlEmail(String keywordId);

    List<CdsKeyword> findKeywordAndSelectedByUser(@Param("user") User user);

    List<String> findUserLeadInCategories(@Param("user") User user);

    List<String> findAllLeadInCategories();
}