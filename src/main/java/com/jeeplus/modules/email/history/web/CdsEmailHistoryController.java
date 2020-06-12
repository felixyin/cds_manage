/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.history.web;

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
import com.jeeplus.modules.email.history.entity.CdsEmailHistory;
import com.jeeplus.modules.email.history.service.CdsEmailHistoryService;

/**
 * 发件历史Controller
 * @author 尹彬
 * @version 2019-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/email/history/cdsEmailHistory")
public class CdsEmailHistoryController extends BaseController {

	@Autowired
	private CdsEmailHistoryService cdsEmailHistoryService;
	
	@ModelAttribute
	public CdsEmailHistory get(@RequestParam(required=false) String id) {
		CdsEmailHistory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsEmailHistoryService.get(id);
		}
		if (entity == null){
			entity = new CdsEmailHistory();
		}
		return entity;
	}
	
	/**
	 * 发件历史列表页面
	 */
	@RequiresPermissions("email:history:cdsEmailHistory:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsEmailHistory cdsEmailHistory, Model model) {
		model.addAttribute("cdsEmailHistory", cdsEmailHistory);
		return "modules/email/history/cdsEmailHistoryList";
	}
	
		/**
	 * 发件历史列表数据
	 */
	@ResponseBody
	@RequiresPermissions("email:history:cdsEmailHistory:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsEmailHistory cdsEmailHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsEmailHistory> page = cdsEmailHistoryService.findPage(new Page<CdsEmailHistory>(request, response), cdsEmailHistory); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑发件历史表单页面
	 */
	@RequiresPermissions(value={"email:history:cdsEmailHistory:view","email:history:cdsEmailHistory:add","email:history:cdsEmailHistory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsEmailHistory cdsEmailHistory, Model model) {
		model.addAttribute("cdsEmailHistory", cdsEmailHistory);
		return "modules/email/history/cdsEmailHistoryForm";
	}

	/**
	 * 保存发件历史
	 */
	@ResponseBody
	@RequiresPermissions(value={"email:history:cdsEmailHistory:add","email:history:cdsEmailHistory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsEmailHistory cdsEmailHistory, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsEmailHistory);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsEmailHistoryService.save(cdsEmailHistory);//保存
		j.setSuccess(true);
		j.setMsg("保存发件历史成功");
		return j;
	}
	
	/**
	 * 删除发件历史
	 */
	@ResponseBody
	@RequiresPermissions("email:history:cdsEmailHistory:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsEmailHistory cdsEmailHistory) {
		AjaxJson j = new AjaxJson();
		cdsEmailHistoryService.delete(cdsEmailHistory);
		j.setMsg("删除发件历史成功");
		return j;
	}
	
	/**
	 * 批量删除发件历史
	 */
	@ResponseBody
	@RequiresPermissions("email:history:cdsEmailHistory:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsEmailHistoryService.delete(cdsEmailHistoryService.get(id));
		}
		j.setMsg("删除发件历史成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("email:history:cdsEmailHistory:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsEmailHistory cdsEmailHistory, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "发件历史"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsEmailHistory> page = cdsEmailHistoryService.findPage(new Page<CdsEmailHistory>(request, response, -1), cdsEmailHistory);
    		new ExportExcel("发件历史", CdsEmailHistory.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出发件历史记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("email:history:cdsEmailHistory:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsEmailHistory> list = ei.getDataList(CdsEmailHistory.class);
			for (CdsEmailHistory cdsEmailHistory : list){
				try{
					cdsEmailHistoryService.save(cdsEmailHistory);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条发件历史记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条发件历史记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入发件历史失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入发件历史数据模板
	 */
	@ResponseBody
	@RequiresPermissions("email:history:cdsEmailHistory:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "发件历史数据导入模板.xlsx";
    		List<CdsEmailHistory> list = Lists.newArrayList(); 
    		new ExportExcel("发件历史数据", CdsEmailHistory.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}