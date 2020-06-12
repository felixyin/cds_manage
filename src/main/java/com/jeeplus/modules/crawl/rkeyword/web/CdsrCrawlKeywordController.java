/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.rkeyword.web;

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
import com.jeeplus.modules.crawl.rkeyword.entity.CdsrCrawlKeyword;
import com.jeeplus.modules.crawl.rkeyword.service.CdsrCrawlKeywordService;

/**
 * 关键词与爬取对应关系Controller
 * @author 尹彬
 * @version 2019-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/crawl/rkeyword/cdsrCrawlKeyword")
public class CdsrCrawlKeywordController extends BaseController {

	@Autowired
	private CdsrCrawlKeywordService cdsrCrawlKeywordService;
	
	@ModelAttribute
	public CdsrCrawlKeyword get(@RequestParam(required=false) String id) {
		CdsrCrawlKeyword entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsrCrawlKeywordService.get(id);
		}
		if (entity == null){
			entity = new CdsrCrawlKeyword();
		}
		return entity;
	}
	
	/**
	 * 关键词与爬取对应关系列表页面
	 */
	@RequiresPermissions("crawl:rkeyword:cdsrCrawlKeyword:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsrCrawlKeyword cdsrCrawlKeyword, Model model) {
		model.addAttribute("cdsrCrawlKeyword", cdsrCrawlKeyword);
		return "modules/crawl/rkeyword/cdsrCrawlKeywordList";
	}
	
		/**
	 * 关键词与爬取对应关系列表数据
	 */
	@ResponseBody
	@RequiresPermissions("crawl:rkeyword:cdsrCrawlKeyword:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsrCrawlKeyword cdsrCrawlKeyword, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsrCrawlKeyword> page = cdsrCrawlKeywordService.findPage(new Page<CdsrCrawlKeyword>(request, response), cdsrCrawlKeyword); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑关键词与爬取对应关系表单页面
	 */
	@RequiresPermissions(value={"crawl:rkeyword:cdsrCrawlKeyword:view","crawl:rkeyword:cdsrCrawlKeyword:add","crawl:rkeyword:cdsrCrawlKeyword:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsrCrawlKeyword cdsrCrawlKeyword, Model model) {
		model.addAttribute("cdsrCrawlKeyword", cdsrCrawlKeyword);
		return "modules/crawl/rkeyword/cdsrCrawlKeywordForm";
	}

	/**
	 * 保存关键词与爬取对应关系
	 */
	@ResponseBody
	@RequiresPermissions(value={"crawl:rkeyword:cdsrCrawlKeyword:add","crawl:rkeyword:cdsrCrawlKeyword:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsrCrawlKeyword cdsrCrawlKeyword, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsrCrawlKeyword);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsrCrawlKeywordService.save(cdsrCrawlKeyword);//保存
		j.setSuccess(true);
		j.setMsg("保存关键词与爬取对应关系成功");
		return j;
	}
	
	/**
	 * 删除关键词与爬取对应关系
	 */
	@ResponseBody
	@RequiresPermissions("crawl:rkeyword:cdsrCrawlKeyword:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsrCrawlKeyword cdsrCrawlKeyword) {
		AjaxJson j = new AjaxJson();
		cdsrCrawlKeywordService.delete(cdsrCrawlKeyword);
		j.setMsg("删除关键词与爬取对应关系成功");
		return j;
	}
	
	/**
	 * 批量删除关键词与爬取对应关系
	 */
	@ResponseBody
	@RequiresPermissions("crawl:rkeyword:cdsrCrawlKeyword:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsrCrawlKeywordService.delete(cdsrCrawlKeywordService.get(id));
		}
		j.setMsg("删除关键词与爬取对应关系成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("crawl:rkeyword:cdsrCrawlKeyword:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsrCrawlKeyword cdsrCrawlKeyword, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "关键词与爬取对应关系"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsrCrawlKeyword> page = cdsrCrawlKeywordService.findPage(new Page<CdsrCrawlKeyword>(request, response, -1), cdsrCrawlKeyword);
    		new ExportExcel("关键词与爬取对应关系", CdsrCrawlKeyword.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出关键词与爬取对应关系记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("crawl:rkeyword:cdsrCrawlKeyword:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsrCrawlKeyword> list = ei.getDataList(CdsrCrawlKeyword.class);
			for (CdsrCrawlKeyword cdsrCrawlKeyword : list){
				try{
					cdsrCrawlKeywordService.save(cdsrCrawlKeyword);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条关键词与爬取对应关系记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条关键词与爬取对应关系记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入关键词与爬取对应关系失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入关键词与爬取对应关系数据模板
	 */
	@ResponseBody
	@RequiresPermissions("crawl:rkeyword:cdsrCrawlKeyword:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "关键词与爬取对应关系数据导入模板.xlsx";
    		List<CdsrCrawlKeyword> list = Lists.newArrayList(); 
    		new ExportExcel("关键词与爬取对应关系数据", CdsrCrawlKeyword.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}