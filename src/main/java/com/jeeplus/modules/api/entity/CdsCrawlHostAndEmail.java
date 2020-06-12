/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.api.entity;


import com.google.common.collect.Lists;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.crawl.email.entity.CdsCrawlEmail;

import java.util.List;

/**
 * 爬取的域名Entity
 *
 * @author 尹彬
 * @version 2019-03-21
 */
public class CdsCrawlHostAndEmail extends DataEntity<CdsCrawlHostAndEmail> {

    private static final long serialVersionUID = 1L;

    /*
       {
          "emails": [
              "internetsalessupport@roomstogo.com",
              "customerservice@roomstogo.com"
          ],
          "host": "www.roomstogo.com",
          "title": "Rooms To Go",
          "url": "https://www.roomstogo.com/content/Customer-Service/Customer-Support"
      }
     */
    private String host;        // 域名
    private Integer isFindEmail;        // 是否有效
    private Long crawlTimes = 0L;        // 爬取次数
    private String url;        // 访问地址
    private String title;        // 标题
    private String description;        // 描述
    private List<CdsCrawlEmail> emails = Lists.newArrayList(); // 抓取的邮箱

    public CdsCrawlHostAndEmail() {
        super();
    }

    public CdsCrawlHostAndEmail(String id) {
        super(id);
    }

    @ExcelField(title = "域名", align = 2, sort = 1)
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @ExcelField(title = "是否有效", dictType = "is_find_email", align = 2, sort = 2)
    public Integer getIsFindEmail() {
        return isFindEmail;
    }

    public void setIsFindEmail(Integer isFindEmail) {
        this.isFindEmail = isFindEmail;
    }

    @ExcelField(title = "爬取次数", align = 2, sort = 3)
    public Long getCrawlTimes() {
        return crawlTimes;
    }

    public void setCrawlTimes(Long crawlTimes) {
        this.crawlTimes = crawlTimes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CdsCrawlEmail> getEmails() {
        return emails;
    }

    public void setEmails(List<CdsCrawlEmail> emails) {
        this.emails = emails;
    }
}