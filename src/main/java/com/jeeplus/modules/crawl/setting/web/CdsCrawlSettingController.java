/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.setting.web;

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
import com.jeeplus.modules.crawl.setting.entity.CdsCrawlSetting;
import com.jeeplus.modules.crawl.setting.service.CdsCrawlSettingService;

/**
 * 爬取参数设置Controller
 * @author 尹彬
 * @version 2019-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/crawl/setting/cdsCrawlSetting")
public class CdsCrawlSettingController extends BaseController {

	@Autowired
	private CdsCrawlSettingService cdsCrawlSettingService;
	
	@ModelAttribute
	public CdsCrawlSetting get(@RequestParam(required=false) String id) {
		CdsCrawlSetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsCrawlSettingService.get(id);
		}
		if (entity == null){
			entity = new CdsCrawlSetting();
		}
		return entity;
	}
	
	/**
	 * 爬取参数设置列表页面
	 */
	@RequiresPermissions("crawl:setting:cdsCrawlSetting:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsCrawlSetting cdsCrawlSetting, Model model) {
		model.addAttribute("cdsCrawlSetting", cdsCrawlSetting);
		return "modules/crawl/setting/cdsCrawlSettingList";
	}
	
		/**
	 * 爬取参数设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("crawl:setting:cdsCrawlSetting:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsCrawlSetting cdsCrawlSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsCrawlSetting> page = cdsCrawlSettingService.findPage(new Page<CdsCrawlSetting>(request, response), cdsCrawlSetting); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑爬取参数设置表单页面
	 */
	@RequiresPermissions(value={"crawl:setting:cdsCrawlSetting:view","crawl:setting:cdsCrawlSetting:add","crawl:setting:cdsCrawlSetting:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsCrawlSetting cdsCrawlSetting, Model model) {
		model.addAttribute("cdsCrawlSetting", cdsCrawlSetting);
		return "modules/crawl/setting/cdsCrawlSettingForm";
	}

	/**
	 * 保存爬取参数设置
	 */
	@ResponseBody
	@RequiresPermissions(value={"crawl:setting:cdsCrawlSetting:add","crawl:setting:cdsCrawlSetting:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsCrawlSetting cdsCrawlSetting, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsCrawlSetting);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsCrawlSettingService.save(cdsCrawlSetting);//保存
		j.setSuccess(true);
		j.setMsg("保存爬取参数设置成功");
		return j;
	}
	
	/**
	 * 删除爬取参数设置
	 */
	@ResponseBody
	@RequiresPermissions("crawl:setting:cdsCrawlSetting:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsCrawlSetting cdsCrawlSetting) {
		AjaxJson j = new AjaxJson();
		cdsCrawlSettingService.delete(cdsCrawlSetting);
		j.setMsg("删除爬取参数设置成功");
		return j;
	}
	
	/**
	 * 批量删除爬取参数设置
	 */
	@ResponseBody
	@RequiresPermissions("crawl:setting:cdsCrawlSetting:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsCrawlSettingService.delete(cdsCrawlSettingService.get(id));
		}
		j.setMsg("删除爬取参数设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("crawl:setting:cdsCrawlSetting:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsCrawlSetting cdsCrawlSetting, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "爬取参数设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsCrawlSetting> page = cdsCrawlSettingService.findPage(new Page<CdsCrawlSetting>(request, response, -1), cdsCrawlSetting);
    		new ExportExcel("爬取参数设置", CdsCrawlSetting.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出爬取参数设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("crawl:setting:cdsCrawlSetting:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsCrawlSetting> list = ei.getDataList(CdsCrawlSetting.class);
			for (CdsCrawlSetting cdsCrawlSetting : list){
				try{
					cdsCrawlSettingService.save(cdsCrawlSetting);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条爬取参数设置记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条爬取参数设置记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入爬取参数设置失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入爬取参数设置数据模板
	 */
	@ResponseBody
	@RequiresPermissions("crawl:setting:cdsCrawlSetting:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "爬取参数设置数据导入模板.xlsx";
    		List<CdsCrawlSetting> list = Lists.newArrayList(); 
    		new ExportExcel("爬取参数设置数据", CdsCrawlSetting.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}