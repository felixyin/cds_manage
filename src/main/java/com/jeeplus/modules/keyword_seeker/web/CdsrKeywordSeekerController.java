/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.keyword_seeker.web;

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
import com.jeeplus.modules.keyword_seeker.entity.CdsrKeywordSeeker;
import com.jeeplus.modules.keyword_seeker.service.CdsrKeywordSeekerService;

/**
 * 关键词搜索记录Controller
 * @author 尹彬
 * @version 2019-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/keyword_seeker/cdsrKeywordSeeker")
public class CdsrKeywordSeekerController extends BaseController {

	@Autowired
	private CdsrKeywordSeekerService cdsrKeywordSeekerService;
	
	@ModelAttribute
	public CdsrKeywordSeeker get(@RequestParam(required=false) String id) {
		CdsrKeywordSeeker entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsrKeywordSeekerService.get(id);
		}
		if (entity == null){
			entity = new CdsrKeywordSeeker();
		}
		return entity;
	}
	
	/**
	 * 关键词搜索记录列表页面
	 */
	@RequiresPermissions("keyword_seeker:cdsrKeywordSeeker:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsrKeywordSeeker cdsrKeywordSeeker, Model model) {
		model.addAttribute("cdsrKeywordSeeker", cdsrKeywordSeeker);
		return "modules/keyword_seeker/cdsrKeywordSeekerList";
	}
	
		/**
	 * 关键词搜索记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("keyword_seeker:cdsrKeywordSeeker:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsrKeywordSeeker cdsrKeywordSeeker, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsrKeywordSeeker> page = cdsrKeywordSeekerService.findPage(new Page<CdsrKeywordSeeker>(request, response), cdsrKeywordSeeker); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑关键词搜索记录表单页面
	 */
	@RequiresPermissions(value={"keyword_seeker:cdsrKeywordSeeker:view","keyword_seeker:cdsrKeywordSeeker:add","keyword_seeker:cdsrKeywordSeeker:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsrKeywordSeeker cdsrKeywordSeeker, Model model) {
		model.addAttribute("cdsrKeywordSeeker", cdsrKeywordSeeker);
		return "modules/keyword_seeker/cdsrKeywordSeekerForm";
	}

	/**
	 * 保存关键词搜索记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"keyword_seeker:cdsrKeywordSeeker:add","keyword_seeker:cdsrKeywordSeeker:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsrKeywordSeeker cdsrKeywordSeeker, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsrKeywordSeeker);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsrKeywordSeekerService.save(cdsrKeywordSeeker);//保存
		j.setSuccess(true);
		j.setMsg("保存关键词搜索记录成功");
		return j;
	}
	
	/**
	 * 删除关键词搜索记录
	 */
	@ResponseBody
	@RequiresPermissions("keyword_seeker:cdsrKeywordSeeker:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsrKeywordSeeker cdsrKeywordSeeker) {
		AjaxJson j = new AjaxJson();
		cdsrKeywordSeekerService.delete(cdsrKeywordSeeker);
		j.setMsg("删除关键词搜索记录成功");
		return j;
	}
	
	/**
	 * 批量删除关键词搜索记录
	 */
	@ResponseBody
	@RequiresPermissions("keyword_seeker:cdsrKeywordSeeker:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsrKeywordSeekerService.delete(cdsrKeywordSeekerService.get(id));
		}
		j.setMsg("删除关键词搜索记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("keyword_seeker:cdsrKeywordSeeker:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsrKeywordSeeker cdsrKeywordSeeker, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "关键词搜索记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsrKeywordSeeker> page = cdsrKeywordSeekerService.findPage(new Page<CdsrKeywordSeeker>(request, response, -1), cdsrKeywordSeeker);
    		new ExportExcel("关键词搜索记录", CdsrKeywordSeeker.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出关键词搜索记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("keyword_seeker:cdsrKeywordSeeker:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsrKeywordSeeker> list = ei.getDataList(CdsrKeywordSeeker.class);
			for (CdsrKeywordSeeker cdsrKeywordSeeker : list){
				try{
					cdsrKeywordSeekerService.save(cdsrKeywordSeeker);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条关键词搜索记录记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条关键词搜索记录记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入关键词搜索记录失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入关键词搜索记录数据模板
	 */
	@ResponseBody
	@RequiresPermissions("keyword_seeker:cdsrKeywordSeeker:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "关键词搜索记录数据导入模板.xlsx";
    		List<CdsrKeywordSeeker> list = Lists.newArrayList(); 
    		new ExportExcel("关键词搜索记录数据", CdsrKeywordSeeker.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}