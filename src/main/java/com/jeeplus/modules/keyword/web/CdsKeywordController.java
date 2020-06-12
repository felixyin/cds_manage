/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.keyword.web;

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
import com.jeeplus.modules.keyword.entity.CdsKeyword;
import com.jeeplus.modules.keyword.service.CdsKeywordService;

/**
 * 关键词Controller
 * @author 尹彬
 * @version 2019-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/keyword/cdsKeyword")
public class CdsKeywordController extends BaseController {

	@Autowired
	private CdsKeywordService cdsKeywordService;
	
	@ModelAttribute
	public CdsKeyword get(@RequestParam(required=false) String id) {
		CdsKeyword entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsKeywordService.get(id);
		}
		if (entity == null){
			entity = new CdsKeyword();
		}
		return entity;
	}
	
	/**
	 * 关键词列表页面
	 */
	@RequiresPermissions("keyword:cdsKeyword:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsKeyword cdsKeyword, Model model) {
		model.addAttribute("cdsKeyword", cdsKeyword);
		return "modules/keyword/cdsKeywordList";
	}
	
		/**
	 * 关键词列表数据
	 */
	@ResponseBody
	@RequiresPermissions("keyword:cdsKeyword:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsKeyword cdsKeyword, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsKeyword> page = cdsKeywordService.findPage(new Page<CdsKeyword>(request, response), cdsKeyword); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑关键词表单页面
	 */
	@RequiresPermissions(value={"keyword:cdsKeyword:view","keyword:cdsKeyword:add","keyword:cdsKeyword:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsKeyword cdsKeyword, Model model) {
		model.addAttribute("cdsKeyword", cdsKeyword);
		return "modules/keyword/cdsKeywordForm";
	}

	/**
	 * 保存关键词
	 */
	@ResponseBody
	@RequiresPermissions(value={"keyword:cdsKeyword:add","keyword:cdsKeyword:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsKeyword cdsKeyword, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsKeyword);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsKeywordService.save(cdsKeyword);//保存
		j.setSuccess(true);
		j.setMsg("保存关键词成功");
		return j;
	}
	
	/**
	 * 删除关键词
	 */
	@ResponseBody
	@RequiresPermissions("keyword:cdsKeyword:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsKeyword cdsKeyword) {
		AjaxJson j = new AjaxJson();
		cdsKeywordService.delete(cdsKeyword);
		j.setMsg("删除关键词成功");
		return j;
	}
	
	/**
	 * 批量删除关键词
	 */
	@ResponseBody
	@RequiresPermissions("keyword:cdsKeyword:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsKeywordService.delete(cdsKeywordService.get(id));
		}
		j.setMsg("删除关键词成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("keyword:cdsKeyword:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsKeyword cdsKeyword, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "关键词"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsKeyword> page = cdsKeywordService.findPage(new Page<CdsKeyword>(request, response, -1), cdsKeyword);
    		new ExportExcel("关键词", CdsKeyword.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出关键词记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("keyword:cdsKeyword:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsKeyword> list = ei.getDataList(CdsKeyword.class);
			for (CdsKeyword cdsKeyword : list){
				try{
					cdsKeywordService.save(cdsKeyword);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条关键词记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条关键词记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入关键词失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入关键词数据模板
	 */
	@ResponseBody
	@RequiresPermissions("keyword:cdsKeyword:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "关键词数据导入模板.xlsx";
    		List<CdsKeyword> list = Lists.newArrayList(); 
    		new ExportExcel("关键词数据", CdsKeyword.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}