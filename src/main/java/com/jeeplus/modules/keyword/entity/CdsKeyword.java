/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.keyword.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 关键词Entity
 *
 * @author 尹彬
 * @version 2019-03-21
 */
public class CdsKeyword extends DataEntity<CdsKeyword> {

    private static final long serialVersionUID = 1L;
    private String keyword;        // 关键词
    private Integer times = 0;        // 使用次数
    private String hostCount;        // 有效域名数量
    private String emailCount;        // 有效邮箱数量
    private Integer beginTimes;        // 开始 使用次数
    private Integer endTimes;        // 结束 使用次数
    private String beginHostCount;        // 开始 有效域名数量
    private String endHostCount;        // 结束 有效域名数量
    private String beginEmailCount;        // 开始 有效邮箱数量
    private String endEmailCount;        // 结束 有效邮箱数量

    // 新增，邮件模块选择
    private boolean isSelected;
    private String keywordSeekerId;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getKeywordSeekerId() {
        return keywordSeekerId;
    }

    public void setKeywordSeekerId(String keywordSeekerId) {
        this.keywordSeekerId = keywordSeekerId;
    }

    public CdsKeyword() {
        super();
    }

    public CdsKeyword(String id) {
        super(id);
    }

    @ExcelField(title = "关键词", align = 2, sort = 1)
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @ExcelField(title = "使用次数", align = 2, sort = 2)
    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    @ExcelField(title = "有效域名数量", align = 2, sort = 3)
    public String getHostCount() {
        return hostCount;
    }

    public void setHostCount(String hostCount) {
        this.hostCount = hostCount;
    }

    @ExcelField(title = "有效邮箱数量", align = 2, sort = 4)
    public String getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(String emailCount) {
        this.emailCount = emailCount;
    }

    public Integer getBeginTimes() {
        return beginTimes;
    }

    public void setBeginTimes(Integer beginTimes) {
        this.beginTimes = beginTimes;
    }

    public Integer getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(Integer endTimes) {
        this.endTimes = endTimes;
    }

    public String getBeginHostCount() {
        return beginHostCount;
    }

    public void setBeginHostCount(String beginHostCount) {
        this.beginHostCount = beginHostCount;
    }

    public String getEndHostCount() {
        return endHostCount;
    }

    public void setEndHostCount(String endHostCount) {
        this.endHostCount = endHostCount;
    }

    public String getBeginEmailCount() {
        return beginEmailCount;
    }

    public void setBeginEmailCount(String beginEmailCount) {
        this.beginEmailCount = beginEmailCount;
    }

    public String getEndEmailCount() {
        return endEmailCount;
    }

    public void setEndEmailCount(String endEmailCount) {
        this.endEmailCount = endEmailCount;
    }

}