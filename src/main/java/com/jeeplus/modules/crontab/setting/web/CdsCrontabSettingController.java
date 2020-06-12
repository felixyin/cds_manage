/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crontab.setting.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.crontab.setting.entity.CdsCrontabSetting;
import com.jeeplus.modules.crontab.setting.service.CdsCrontabSettingService;

/**
 * 邮箱定时任务Controller
 * @author 尹彬
 * @version 2019-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/crontab/setting/cdsCrontabSetting")
public class CdsCrontabSettingController extends BaseController {

	@Autowired
	private CdsCrontabSettingService cdsCrontabSettingService;
	
	@ModelAttribute
	public CdsCrontabSetting get(@RequestParam(required=false) String id) {
		CdsCrontabSetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsCrontabSettingService.get(id);
		}
		if (entity == null){
			entity = new CdsCrontabSetting();
		}
		return entity;
	}
	
	/**
	 * 邮箱定时任务列表页面
	 */
	@RequiresPermissions("crontab:setting:cdsCrontabSetting:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsCrontabSetting cdsCrontabSetting, Model model) {
		model.addAttribute("cdsCrontabSetting", cdsCrontabSetting);
		return "modules/crontab/setting/cdsCrontabSettingList";
	}
	
		/**
	 * 邮箱定时任务列表数据
	 */
	@ResponseBody
	@RequiresPermissions("crontab:setting:cdsCrontabSetting:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsCrontabSetting cdsCrontabSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsCrontabSetting> page = cdsCrontabSettingService.findPage(new Page<CdsCrontabSetting>(request, response), cdsCrontabSetting); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑邮箱定时任务表单页面
	 */
	@RequiresPermissions(value={"crontab:setting:cdsCrontabSetting:view","crontab:setting:cdsCrontabSetting:add","crontab:setting:cdsCrontabSetting:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsCrontabSetting cdsCrontabSetting, Model model) {
		model.addAttribute("cdsCrontabSetting", cdsCrontabSetting);
		return "modules/crontab/setting/cdsCrontabSettingForm";
	}

	/**
	 * 保存邮箱定时任务
	 */
	@ResponseBody
	@RequiresPermissions(value={"crontab:setting:cdsCrontabSetting:add","crontab:setting:cdsCrontabSetting:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsCrontabSetting cdsCrontabSetting, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsCrontabSetting);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsCrontabSettingService.save(cdsCrontabSetting);//保存
		j.setSuccess(true);
		j.setMsg("保存邮箱定时任务成功");
		return j;
	}
	
	/**
	 * 删除邮箱定时任务
	 */
	@ResponseBody
	@RequiresPermissions("crontab:setting:cdsCrontabSetting:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsCrontabSetting cdsCrontabSetting) {
		AjaxJson j = new AjaxJson();
		cdsCrontabSettingService.delete(cdsCrontabSetting);
		j.setMsg("删除邮箱定时任务成功");
		return j;
	}
	
	/**
	 * 批量删除邮箱定时任务
	 */
	@ResponseBody
	@RequiresPermissions("crontab:setting:cdsCrontabSetting:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsCrontabSettingService.delete(cdsCrontabSettingService.get(id));
		}
		j.setMsg("删除邮箱定时任务成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("crontab:setting:cdsCrontabSetting:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsCrontabSetting cdsCrontabSetting, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "邮箱定时任务"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsCrontabSetting> page = cdsCrontabSettingService.findPage(new Page<CdsCrontabSetting>(request, response, -1), cdsCrontabSetting);
    		new ExportExcel("邮箱定时任务", CdsCrontabSetting.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出邮箱定时任务记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("crontab:setting:cdsCrontabSetting:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsCrontabSetting> list = ei.getDataList(CdsCrontabSetting.class);
			for (CdsCrontabSetting cdsCrontabSetting : list){
				try{
					cdsCrontabSettingService.save(cdsCrontabSetting);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条邮箱定时任务记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条邮箱定时任务记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入邮箱定时任务失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入邮箱定时任务数据模板
	 */
	@ResponseBody
	@RequiresPermissions("crontab:setting:cdsCrontabSetting:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "邮箱定时任务数据导入模板.xlsx";
    		List<CdsCrontabSetting> list = Lists.newArrayList(); 
    		new ExportExcel("邮箱定时任务数据", CdsCrontabSetting.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}