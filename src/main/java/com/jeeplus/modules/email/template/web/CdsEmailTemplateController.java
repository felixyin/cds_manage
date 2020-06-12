/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.template.web;

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
import com.jeeplus.modules.email.template.entity.CdsEmailTemplate;
import com.jeeplus.modules.email.template.service.CdsEmailTemplateService;

/**
 * 我的邮件模板Controller
 * @author 尹彬
 * @version 2019-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/email/template/cdsEmailTemplate")
public class CdsEmailTemplateController extends BaseController {

	@Autowired
	private CdsEmailTemplateService cdsEmailTemplateService;
	
	@ModelAttribute
	public CdsEmailTemplate get(@RequestParam(required=false) String id) {
		CdsEmailTemplate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsEmailTemplateService.get(id);
		}
		if (entity == null){
			entity = new CdsEmailTemplate();
		}
		return entity;
	}
	
	/**
	 * 我的邮件模板列表页面
	 */
	@RequiresPermissions("email:template:cdsEmailTemplate:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsEmailTemplate cdsEmailTemplate, Model model) {
		model.addAttribute("cdsEmailTemplate", cdsEmailTemplate);
		return "modules/email/template/cdsEmailTemplateList";
	}
	
		/**
	 * 我的邮件模板列表数据
	 */
	@ResponseBody
	@RequiresPermissions("email:template:cdsEmailTemplate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsEmailTemplate cdsEmailTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsEmailTemplate> page = cdsEmailTemplateService.findPage(new Page<CdsEmailTemplate>(request, response), cdsEmailTemplate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑我的邮件模板表单页面
	 */
	@RequiresPermissions(value={"email:template:cdsEmailTemplate:view","email:template:cdsEmailTemplate:add","email:template:cdsEmailTemplate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsEmailTemplate cdsEmailTemplate, Model model) {
		model.addAttribute("cdsEmailTemplate", cdsEmailTemplate);
		return "modules/email/template/cdsEmailTemplateForm";
	}

	/**
	 * 保存我的邮件模板
	 */
	@ResponseBody
	@RequiresPermissions(value={"email:template:cdsEmailTemplate:add","email:template:cdsEmailTemplate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsEmailTemplate cdsEmailTemplate, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsEmailTemplate);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsEmailTemplateService.save(cdsEmailTemplate);//保存
		j.setSuccess(true);
		j.setMsg("保存我的邮件模板成功");
		return j;
	}
	
	/**
	 * 删除我的邮件模板
	 */
	@ResponseBody
	@RequiresPermissions("email:template:cdsEmailTemplate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsEmailTemplate cdsEmailTemplate) {
		AjaxJson j = new AjaxJson();
		cdsEmailTemplateService.delete(cdsEmailTemplate);
		j.setMsg("删除我的邮件模板成功");
		return j;
	}
	
	/**
	 * 批量删除我的邮件模板
	 */
	@ResponseBody
	@RequiresPermissions("email:template:cdsEmailTemplate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsEmailTemplateService.delete(cdsEmailTemplateService.get(id));
		}
		j.setMsg("删除我的邮件模板成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("email:template:cdsEmailTemplate:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsEmailTemplate cdsEmailTemplate, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "我的邮件模板"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsEmailTemplate> page = cdsEmailTemplateService.findPage(new Page<CdsEmailTemplate>(request, response, -1), cdsEmailTemplate);
    		new ExportExcel("我的邮件模板", CdsEmailTemplate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出我的邮件模板记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("email:template:cdsEmailTemplate:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsEmailTemplate> list = ei.getDataList(CdsEmailTemplate.class);
			for (CdsEmailTemplate cdsEmailTemplate : list){
				try{
					cdsEmailTemplateService.save(cdsEmailTemplate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条我的邮件模板记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条我的邮件模板记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入我的邮件模板失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入我的邮件模板数据模板
	 */
	@ResponseBody
	@RequiresPermissions("email:template:cdsEmailTemplate:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "我的邮件模板数据导入模板.xlsx";
    		List<CdsEmailTemplate> list = Lists.newArrayList(); 
    		new ExportExcel("我的邮件模板数据", CdsEmailTemplate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}