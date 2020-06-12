/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.url.entity;

import com.jeeplus.modules.crawl.host.entity.CdsCrawlHost;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 爬取地址Entity
 *
 * @author 尹彬
 * @version 2019-03-21
 */
public class CdsCrawlUrl extends DataEntity<CdsCrawlUrl> {

    private static final long serialVersionUID = 1L;
    private CdsCrawlHost crawlHost;        // 域名外键
    private String url;        // 访问地址
    private String title;        // 标题
    private String description;        // 描述
    private Long crawlTimes;        // 统计次数
    private Integer elapsedTime;        // 耗时（秒）
    private Date siteLastDate;        // 站点更新日期
    private Integer isFindEmail;        // 是否有效
    private Long beginCrawlTimes;        // 开始 统计次数
    private Long endCrawlTimes;        // 结束 统计次数
    private Integer beginElapsedTime;        // 开始 耗时（秒）
    private Integer endElapsedTime;        // 结束 耗时（秒）
    private Date beginSiteLastDate;        // 开始 站点更新日期
    private Date endSiteLastDate;        // 结束 站点更新日期

    public CdsCrawlUrl() {
        super();
    }

    public CdsCrawlUrl(String id) {
        super(id);
    }

    @ExcelField(title = "域名外键", fieldType = CdsCrawlHost.class, value = "crawlHost.host", align = 2, sort = 1)
    public CdsCrawlHost getCrawlHost() {
        return crawlHost;
    }

    public void setCrawlHost(CdsCrawlHost crawlHost) {
        this.crawlHost = crawlHost;
    }

    @ExcelField(title = "访问地址", align = 2, sort = 2)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ExcelField(title = "标题", align = 2, sort = 3)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (null != title && title.length() > 200) {
            title = title.substring(0, 200);
        }
        this.title = title;
    }

    @ExcelField(title = "描述", align = 2, sort = 4)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ExcelField(title = "统计次数", align = 2, sort = 5)
    public Long getCrawlTimes() {
        return crawlTimes;
    }

    public void setCrawlTimes(Long crawlTimes) {
        this.crawlTimes = crawlTimes;
    }

    @ExcelField(title = "耗时（秒）", align = 2, sort = 6)
    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "站点更新日期", align = 2, sort = 7)
    public Date getSiteLastDate() {
        return siteLastDate;
    }

    public void setSiteLastDate(Date siteLastDate) {
        this.siteLastDate = siteLastDate;
    }

    @ExcelField(title = "是否有效", dictType = "is_find_email", align = 2, sort = 8)
    public Integer getIsFindEmail() {
        return isFindEmail;
    }

    public void setIsFindEmail(Integer isFindEmail) {
        this.isFindEmail = isFindEmail;
    }

    public Long getBeginCrawlTimes() {
        return beginCrawlTimes;
    }

    public void setBeginCrawlTimes(Long beginCrawlTimes) {
        this.beginCrawlTimes = beginCrawlTimes;
    }

    public Long getEndCrawlTimes() {
        return endCrawlTimes;
    }

    public void setEndCrawlTimes(Long endCrawlTimes) {
        this.endCrawlTimes = endCrawlTimes;
    }

    public Integer getBeginElapsedTime() {
        return beginElapsedTime;
    }

    public void setBeginElapsedTime(Integer beginElapsedTime) {
        this.beginElapsedTime = beginElapsedTime;
    }

    public Integer getEndElapsedTime() {
        return endElapsedTime;
    }

    public void setEndElapsedTime(Integer endElapsedTime) {
        this.endElapsedTime = endElapsedTime;
    }

    public Date getBeginSiteLastDate() {
        return beginSiteLastDate;
    }

    public void setBeginSiteLastDate(Date beginSiteLastDate) {
        this.beginSiteLastDate = beginSiteLastDate;
    }

    public Date getEndSiteLastDate() {
        return endSiteLastDate;
    }

    public void setEndSiteLastDate(Date endSiteLastDate) {
        this.endSiteLastDate = endSiteLastDate;
    }

}