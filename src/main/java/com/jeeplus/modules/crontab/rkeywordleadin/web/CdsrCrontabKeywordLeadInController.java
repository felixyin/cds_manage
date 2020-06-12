/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crontab.rkeywordleadin.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.jeeplus.modules.crontab.rkeywordleadin.entity.CdsrCrontabKeywordLeadIn;
import com.jeeplus.modules.crontab.rkeywordleadin.service.CdsrCrontabKeywordLeadInService;

/**
 * 定时任务关联Controller
 * @author 尹彬
 * @version 2019-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/crontab/rkeywordleadin/cdsrCrontabKeywordLeadIn")
public class CdsrCrontabKeywordLeadInController extends BaseController {

	@Autowired
	private CdsrCrontabKeywordLeadInService cdsrCrontabKeywordLeadInService;
	
	@ModelAttribute
	public CdsrCrontabKeywordLeadIn get(@RequestParam(required=false) String id) {
		CdsrCrontabKeywordLeadIn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsrCrontabKeywordLeadInService.get(id);
		}
		if (entity == null){
			entity = new CdsrCrontabKeywordLeadIn();
		}
		return entity;
	}
	
	/**
	 * 定时任务关联列表页面
	 */
	@RequiresPermissions("crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn, Model model) {
		model.addAttribute("cdsrCrontabKeywordLeadIn", cdsrCrontabKeywordLeadIn);
		return "modules/crontab/rkeywordleadin/cdsrCrontabKeywordLeadInList";
	}
	
		/**
	 * 定时任务关联列表数据
	 */
	@ResponseBody
	@RequiresPermissions("crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsrCrontabKeywordLeadIn> page = cdsrCrontabKeywordLeadInService.findPage(new Page<CdsrCrontabKeywordLeadIn>(request, response), cdsrCrontabKeywordLeadIn); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑定时任务关联表单页面
	 */
	@RequiresPermissions(value={"crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:view","crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:add","crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn, Model model) {
		model.addAttribute("cdsrCrontabKeywordLeadIn", cdsrCrontabKeywordLeadIn);
		return "modules/crontab/rkeywordleadin/cdsrCrontabKeywordLeadInForm";
	}

	/**
	 * 保存定时任务关联
	 */
	@ResponseBody
	@RequiresPermissions(value={"crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:add","crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsrCrontabKeywordLeadIn);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsrCrontabKeywordLeadInService.save(cdsrCrontabKeywordLeadIn);//保存
		j.setSuccess(true);
		j.setMsg("保存定时任务关联成功");
		return j;
	}
	
	/**
	 * 删除定时任务关联
	 */
	@ResponseBody
	@RequiresPermissions("crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn) {
		AjaxJson j = new AjaxJson();
		cdsrCrontabKeywordLeadInService.delete(cdsrCrontabKeywordLeadIn);
		j.setMsg("删除定时任务关联成功");
		return j;
	}
	
	/**
	 * 批量删除定时任务关联
	 */
	@ResponseBody
	@RequiresPermissions("crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsrCrontabKeywordLeadInService.delete(cdsrCrontabKeywordLeadInService.get(id));
		}
		j.setMsg("删除定时任务关联成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "定时任务关联"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsrCrontabKeywordLeadIn> page = cdsrCrontabKeywordLeadInService.findPage(new Page<CdsrCrontabKeywordLeadIn>(request, response, -1), cdsrCrontabKeywordLeadIn);
    		new ExportExcel("定时任务关联", CdsrCrontabKeywordLeadIn.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出定时任务关联记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsrCrontabKeywordLeadIn> list = ei.getDataList(CdsrCrontabKeywordLeadIn.class);
			for (CdsrCrontabKeywordLeadIn cdsrCrontabKeywordLeadIn : list){
				try{
					cdsrCrontabKeywordLeadInService.save(cdsrCrontabKeywordLeadIn);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条定时任务关联记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条定时任务关联记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入定时任务关联失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入定时任务关联数据模板
	 */
	@ResponseBody
	@RequiresPermissions("crontab:rkeywordleadin:cdsrCrontabKeywordLeadIn:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "定时任务关联数据导入模板.xlsx";
    		List<CdsrCrontabKeywordLeadIn> list = Lists.newArrayList(); 
    		new ExportExcel("定时任务关联数据", CdsrCrontabKeywordLeadIn.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}