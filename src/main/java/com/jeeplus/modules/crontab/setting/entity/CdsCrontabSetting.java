/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crontab.setting.entity;

import com.jeeplus.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 邮箱定时任务Entity
 * @author 尹彬
 * @version 2019-03-27
 */
public class CdsCrontabSetting extends DataEntity<CdsCrontabSetting> {
	
	private static final long serialVersionUID = 1L;
	private User owner;		// 所属用户
	private String name;		// 自定义名称
	private Date exeTime;		// 执行时间
	private Integer minute;		// 分(0-59)
	private Integer hour;		// 时(0-23)
	private Integer dayOfWeek;		// 周几(1-7)
	private Integer date;		// 日(1-31)
	private Integer month;		// 月(1-12)
	private Date beginExeTime;		// 开始 执行时间
	private Date endExeTime;		// 结束 执行时间
	
	public CdsCrontabSetting() {
		super();
	}

	public CdsCrontabSetting(String id){
		super(id);
	}

	@ExcelField(title="所属用户", fieldType=User.class, value="owner.name", align=2, sort=1)
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	@ExcelField(title="自定义名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="执行时间", align=2, sort=3)
	public Date getExeTime() {
		return exeTime;
	}

	public void setExeTime(Date exeTime) {
		this.exeTime = exeTime;
	}
	
	@ExcelField(title="分(0-59)", align=2, sort=4)
	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	
	@ExcelField(title="时(0-23)", align=2, sort=5)
	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}
	
	@ExcelField(title="周几(1-7)", align=2, sort=6)
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	@ExcelField(title="日(1-31)", align=2, sort=7)
	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}
	
	@ExcelField(title="月(1-12)", align=2, sort=8)
	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
	
	public Date getBeginExeTime() {
		return beginExeTime;
	}

	public void setBeginExeTime(Date beginExeTime) {
		this.beginExeTime = beginExeTime;
	}
	
	public Date getEndExeTime() {
		return endExeTime;
	}

	public void setEndExeTime(Date endExeTime) {
		this.endExeTime = endExeTime;
	}
		
}