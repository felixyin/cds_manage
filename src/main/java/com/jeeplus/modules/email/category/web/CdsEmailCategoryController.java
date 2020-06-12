/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.category.web;

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
import com.jeeplus.modules.email.category.entity.CdsEmailCategory;
import com.jeeplus.modules.email.category.service.CdsEmailCategoryService;

/**
 * 邮箱分类Controller
 * @author 尹彬
 * @version 2019-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/email/category/cdsEmailCategory")
public class CdsEmailCategoryController extends BaseController {

	@Autowired
	private CdsEmailCategoryService cdsEmailCategoryService;
	
	@ModelAttribute
	public CdsEmailCategory get(@RequestParam(required=false) String id) {
		CdsEmailCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsEmailCategoryService.get(id);
		}
		if (entity == null){
			entity = new CdsEmailCategory();
		}
		return entity;
	}
	
	/**
	 * 邮箱分类列表页面
	 */
	@RequiresPermissions("email:category:cdsEmailCategory:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsEmailCategory cdsEmailCategory, Model model) {
		model.addAttribute("cdsEmailCategory", cdsEmailCategory);
		return "modules/email/category/cdsEmailCategoryList";
	}
	
		/**
	 * 邮箱分类列表数据
	 */
	@ResponseBody
	@RequiresPermissions("email:category:cdsEmailCategory:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsEmailCategory cdsEmailCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsEmailCategory> page = cdsEmailCategoryService.findPage(new Page<CdsEmailCategory>(request, response), cdsEmailCategory); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑邮箱分类表单页面
	 */
	@RequiresPermissions(value={"email:category:cdsEmailCategory:view","email:category:cdsEmailCategory:add","email:category:cdsEmailCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsEmailCategory cdsEmailCategory, Model model) {
		model.addAttribute("cdsEmailCategory", cdsEmailCategory);
		return "modules/email/category/cdsEmailCategoryForm";
	}

	/**
	 * 保存邮箱分类
	 */
	@ResponseBody
	@RequiresPermissions(value={"email:category:cdsEmailCategory:add","email:category:cdsEmailCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsEmailCategory cdsEmailCategory, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsEmailCategory);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsEmailCategoryService.save(cdsEmailCategory);//保存
		j.setSuccess(true);
		j.setMsg("保存邮箱分类成功");
		return j;
	}
	
	/**
	 * 删除邮箱分类
	 */
	@ResponseBody
	@RequiresPermissions("email:category:cdsEmailCategory:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsEmailCategory cdsEmailCategory) {
		AjaxJson j = new AjaxJson();
		cdsEmailCategoryService.delete(cdsEmailCategory);
		j.setMsg("删除邮箱分类成功");
		return j;
	}
	
	/**
	 * 批量删除邮箱分类
	 */
	@ResponseBody
	@RequiresPermissions("email:category:cdsEmailCategory:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsEmailCategoryService.delete(cdsEmailCategoryService.get(id));
		}
		j.setMsg("删除邮箱分类成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("email:category:cdsEmailCategory:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsEmailCategory cdsEmailCategory, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "邮箱分类"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsEmailCategory> page = cdsEmailCategoryService.findPage(new Page<CdsEmailCategory>(request, response, -1), cdsEmailCategory);
    		new ExportExcel("邮箱分类", CdsEmailCategory.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出邮箱分类记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("email:category:cdsEmailCategory:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsEmailCategory> list = ei.getDataList(CdsEmailCategory.class);
			for (CdsEmailCategory cdsEmailCategory : list){
				try{
					cdsEmailCategoryService.save(cdsEmailCategory);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条邮箱分类记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条邮箱分类记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入邮箱分类失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入邮箱分类数据模板
	 */
	@ResponseBody
	@RequiresPermissions("email:category:cdsEmailCategory:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "邮箱分类数据导入模板.xlsx";
    		List<CdsEmailCategory> list = Lists.newArrayList(); 
    		new ExportExcel("邮箱分类数据", CdsEmailCategory.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}