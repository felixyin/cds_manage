/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.setting.entity;

import com.jeeplus.modules.sys.entity.User;

import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 爬取参数设置Entity
 *
 * @author 尹彬
 * @version 2019-03-21
 */
public class CdsCrawlSetting extends DataEntity<CdsCrawlSetting> {

    private static final long serialVersionUID = 1L;
    private User owner;        // 搜索人
    private Integer siteQueue = 100;        // 站点队列长度
    private Integer siteSleep = 500;        // 站点入队间隔时间
    private Integer diggingDepth = 3;        // 站点挖掘深度
    private Integer threadCount = 20;        // 链接线程并发数
    private Integer threadSleep = 200;        // 链接线程间隔时间
    private Integer fetchTimeout = 1000;        // 链接抓取超时时间
    private Integer isNeedDistinct = 1;        // 是否去除重复
    private Integer isVerifyEmail = 1;        // 是否验证邮箱
    private Integer beginSiteQueue;        // 开始 站点队列长度
    private Integer endSiteQueue;        // 结束 站点队列长度
    private Integer beginSiteSleep;        // 开始 站点入队间隔时间
    private Integer endSiteSleep;        // 结束 站点入队间隔时间
    private Integer beginDiggingDepth;        // 开始 站点挖掘深度
    private Integer endDiggingDepth;        // 结束 站点挖掘深度
    private Integer beginThreadCount;        // 开始 链接线程并发数
    private Integer endThreadCount;        // 结束 链接线程并发数
    private Integer beginThreadSleep;        // 开始 链接线程间隔时间
    private Integer endThreadSleep;        // 结束 链接线程间隔时间
    private Integer beginFetchTimeout;        // 开始 链接抓取超时时间
    private Integer endFetchTimeout;        // 结束 链接抓取超时时间

    public CdsCrawlSetting() {
        super();
    }

    public CdsCrawlSetting(String id) {
        super(id);
    }

    //	@NotNull(message="搜索人不能为空")
    @ExcelField(title = "搜索人", fieldType = User.class, value = "owner.name", align = 2, sort = 1)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ExcelField(title = "站点队列长度", align = 2, sort = 2)
    public Integer getSiteQueue() {
        return siteQueue;
    }

    public void setSiteQueue(Integer siteQueue) {
        this.siteQueue = siteQueue;
    }

    @ExcelField(title = "站点入队间隔时间", align = 2, sort = 3)
    public Integer getSiteSleep() {
        return siteSleep;
    }

    public void setSiteSleep(Integer siteSleep) {
        this.siteSleep = siteSleep;
    }

    @ExcelField(title = "站点挖掘深度", align = 2, sort = 4)
    public Integer getDiggingDepth() {
        return diggingDepth;
    }

    public void setDiggingDepth(Integer diggingDepth) {
        this.diggingDepth = diggingDepth;
    }

    @ExcelField(title = "链接线程并发数", align = 2, sort = 5)
    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    @ExcelField(title = "链接线程间隔时间", align = 2, sort = 6)
    public Integer getThreadSleep() {
        return threadSleep;
    }

    public void setThreadSleep(Integer threadSleep) {
        this.threadSleep = threadSleep;
    }

    @ExcelField(title = "链接抓取超时时间", align = 2, sort = 7)
    public Integer getFetchTimeout() {
        return fetchTimeout;
    }

    public void setFetchTimeout(Integer fetchTimeout) {
        this.fetchTimeout = fetchTimeout;
    }

    @ExcelField(title = "是否去除重复", dictType = "is_need_distinct", align = 2, sort = 8)
    public Integer getIsNeedDistinct() {
        return isNeedDistinct;
    }

    public void setIsNeedDistinct(Integer isNeedDistinct) {
        this.isNeedDistinct = isNeedDistinct;
    }

    @ExcelField(title = "是否验证邮箱", dictType = "is_verify_email", align = 2, sort = 9)
    public Integer getIsVerifyEmail() {
        return isVerifyEmail;
    }

    public void setIsVerifyEmail(Integer isVerifyEmail) {
        this.isVerifyEmail = isVerifyEmail;
    }

    public Integer getBeginSiteQueue() {
        return beginSiteQueue;
    }

    public void setBeginSiteQueue(Integer beginSiteQueue) {
        this.beginSiteQueue = beginSiteQueue;
    }

    public Integer getEndSiteQueue() {
        return endSiteQueue;
    }

    public void setEndSiteQueue(Integer endSiteQueue) {
        this.endSiteQueue = endSiteQueue;
    }

    public Integer getBeginSiteSleep() {
        return beginSiteSleep;
    }

    public void setBeginSiteSleep(Integer beginSiteSleep) {
        this.beginSiteSleep = beginSiteSleep;
    }

    public Integer getEndSiteSleep() {
        return endSiteSleep;
    }

    public void setEndSiteSleep(Integer endSiteSleep) {
        this.endSiteSleep = endSiteSleep;
    }

    public Integer getBeginDiggingDepth() {
        return beginDiggingDepth;
    }

    public void setBeginDiggingDepth(Integer beginDiggingDepth) {
        this.beginDiggingDepth = beginDiggingDepth;
    }

    public Integer getEndDiggingDepth() {
        return endDiggingDepth;
    }

    public void setEndDiggingDepth(Integer endDiggingDepth) {
        this.endDiggingDepth = endDiggingDepth;
    }

    public Integer getBeginThreadCount() {
        return beginThreadCount;
    }

    public void setBeginThreadCount(Integer beginThreadCount) {
        this.beginThreadCount = beginThreadCount;
    }

    public Integer getEndThreadCount() {
        return endThreadCount;
    }

    public void setEndThreadCount(Integer endThreadCount) {
        this.endThreadCount = endThreadCount;
    }

    public Integer getBeginThreadSleep() {
        return beginThreadSleep;
    }

    public void setBeginThreadSleep(Integer beginThreadSleep) {
        this.beginThreadSleep = beginThreadSleep;
    }

    public Integer getEndThreadSleep() {
        return endThreadSleep;
    }

    public void setEndThreadSleep(Integer endThreadSleep) {
        this.endThreadSleep = endThreadSleep;
    }

    public Integer getBeginFetchTimeout() {
        return beginFetchTimeout;
    }

    public void setBeginFetchTimeout(Integer beginFetchTimeout) {
        this.beginFetchTimeout = beginFetchTimeout;
    }

    public Integer getEndFetchTimeout() {
        return endFetchTimeout;
    }

    public void setEndFetchTimeout(Integer endFetchTimeout) {
        this.endFetchTimeout = endFetchTimeout;
    }

}