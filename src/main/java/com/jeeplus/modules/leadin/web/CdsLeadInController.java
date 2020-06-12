/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.leadin.web;

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
import com.jeeplus.modules.leadin.entity.CdsLeadIn;
import com.jeeplus.modules.leadin.service.CdsLeadInService;

/**
 * 导入的客户信息Controller
 * @author 尹彬
 * @version 2019-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/leadin/cdsLeadIn")
public class CdsLeadInController extends BaseController {

	@Autowired
	private CdsLeadInService cdsLeadInService;
	
	@ModelAttribute
	public CdsLeadIn get(@RequestParam(required=false) String id) {
		CdsLeadIn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsLeadInService.get(id);
		}
		if (entity == null){
			entity = new CdsLeadIn();
		}
		return entity;
	}
	
	/**
	 * 导入的客户信息列表页面
	 */
	@RequiresPermissions("leadin:cdsLeadIn:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsLeadIn cdsLeadIn, Model model) {
		model.addAttribute("cdsLeadIn", cdsLeadIn);
		return "modules/leadin/cdsLeadInList";
	}
	
		/**
	 * 导入的客户信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("leadin:cdsLeadIn:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsLeadIn cdsLeadIn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsLeadIn> page = cdsLeadInService.findPage(new Page<CdsLeadIn>(request, response), cdsLeadIn); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑导入的客户信息表单页面
	 */
	@RequiresPermissions(value={"leadin:cdsLeadIn:view","leadin:cdsLeadIn:add","leadin:cdsLeadIn:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsLeadIn cdsLeadIn, Model model) {
		model.addAttribute("cdsLeadIn", cdsLeadIn);
		return "modules/leadin/cdsLeadInForm";
	}

	/**
	 * 保存导入的客户信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"leadin:cdsLeadIn:add","leadin:cdsLeadIn:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsLeadIn cdsLeadIn, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsLeadIn);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsLeadInService.save(cdsLeadIn);//保存
		j.setSuccess(true);
		j.setMsg("保存导入的客户信息成功");
		return j;
	}
	
	/**
	 * 删除导入的客户信息
	 */
	@ResponseBody
	@RequiresPermissions("leadin:cdsLeadIn:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsLeadIn cdsLeadIn) {
		AjaxJson j = new AjaxJson();
		cdsLeadInService.delete(cdsLeadIn);
		j.setMsg("删除导入的客户信息成功");
		return j;
	}
	
	/**
	 * 批量删除导入的客户信息
	 */
	@ResponseBody
	@RequiresPermissions("leadin:cdsLeadIn:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsLeadInService.delete(cdsLeadInService.get(id));
		}
		j.setMsg("删除导入的客户信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("leadin:cdsLeadIn:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsLeadIn cdsLeadIn, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "导入的客户信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsLeadIn> page = cdsLeadInService.findPage(new Page<CdsLeadIn>(request, response, -1), cdsLeadIn);
    		new ExportExcel("导入的客户信息", CdsLeadIn.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出导入的客户信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("leadin:cdsLeadIn:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsLeadIn> list = ei.getDataList(CdsLeadIn.class);
			for (CdsLeadIn cdsLeadIn : list){
				try{
					cdsLeadInService.save(cdsLeadIn);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条导入的客户信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条导入的客户信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入导入的客户信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入导入的客户信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("leadin:cdsLeadIn:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "导入的客户信息数据导入模板.xlsx";
    		List<CdsLeadIn> list = Lists.newArrayList(); 
    		new ExportExcel("导入的客户信息数据", CdsLeadIn.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}