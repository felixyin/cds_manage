/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.url.web;

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
import com.jeeplus.modules.crawl.url.entity.CdsCrawlUrl;
import com.jeeplus.modules.crawl.url.service.CdsCrawlUrlService;

/**
 * 爬取地址Controller
 * @author 尹彬
 * @version 2019-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/crawl/url/cdsCrawlUrl")
public class CdsCrawlUrlController extends BaseController {

	@Autowired
	private CdsCrawlUrlService cdsCrawlUrlService;
	
	@ModelAttribute
	public CdsCrawlUrl get(@RequestParam(required=false) String id) {
		CdsCrawlUrl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsCrawlUrlService.get(id);
		}
		if (entity == null){
			entity = new CdsCrawlUrl();
		}
		return entity;
	}
	
	/**
	 * 爬取地址列表页面
	 */
	@RequiresPermissions("crawl:url:cdsCrawlUrl:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsCrawlUrl cdsCrawlUrl, Model model) {
		model.addAttribute("cdsCrawlUrl", cdsCrawlUrl);
		return "modules/crawl/url/cdsCrawlUrlList";
	}
	
		/**
	 * 爬取地址列表数据
	 */
	@ResponseBody
	@RequiresPermissions("crawl:url:cdsCrawlUrl:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsCrawlUrl cdsCrawlUrl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsCrawlUrl> page = cdsCrawlUrlService.findPage(new Page<CdsCrawlUrl>(request, response), cdsCrawlUrl); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑爬取地址表单页面
	 */
	@RequiresPermissions(value={"crawl:url:cdsCrawlUrl:view","crawl:url:cdsCrawlUrl:add","crawl:url:cdsCrawlUrl:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsCrawlUrl cdsCrawlUrl, Model model) {
		model.addAttribute("cdsCrawlUrl", cdsCrawlUrl);
		return "modules/crawl/url/cdsCrawlUrlForm";
	}

	/**
	 * 保存爬取地址
	 */
	@ResponseBody
	@RequiresPermissions(value={"crawl:url:cdsCrawlUrl:add","crawl:url:cdsCrawlUrl:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsCrawlUrl cdsCrawlUrl, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsCrawlUrl);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsCrawlUrlService.save(cdsCrawlUrl);//保存
		j.setSuccess(true);
		j.setMsg("保存爬取地址成功");
		return j;
	}
	
	/**
	 * 删除爬取地址
	 */
	@ResponseBody
	@RequiresPermissions("crawl:url:cdsCrawlUrl:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsCrawlUrl cdsCrawlUrl) {
		AjaxJson j = new AjaxJson();
		cdsCrawlUrlService.delete(cdsCrawlUrl);
		j.setMsg("删除爬取地址成功");
		return j;
	}
	
	/**
	 * 批量删除爬取地址
	 */
	@ResponseBody
	@RequiresPermissions("crawl:url:cdsCrawlUrl:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsCrawlUrlService.delete(cdsCrawlUrlService.get(id));
		}
		j.setMsg("删除爬取地址成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("crawl:url:cdsCrawlUrl:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsCrawlUrl cdsCrawlUrl, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "爬取地址"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsCrawlUrl> page = cdsCrawlUrlService.findPage(new Page<CdsCrawlUrl>(request, response, -1), cdsCrawlUrl);
    		new ExportExcel("爬取地址", CdsCrawlUrl.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出爬取地址记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("crawl:url:cdsCrawlUrl:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsCrawlUrl> list = ei.getDataList(CdsCrawlUrl.class);
			for (CdsCrawlUrl cdsCrawlUrl : list){
				try{
					cdsCrawlUrlService.save(cdsCrawlUrl);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条爬取地址记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条爬取地址记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入爬取地址失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入爬取地址数据模板
	 */
	@ResponseBody
	@RequiresPermissions("crawl:url:cdsCrawlUrl:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "爬取地址数据导入模板.xlsx";
    		List<CdsCrawlUrl> list = Lists.newArrayList(); 
    		new ExportExcel("爬取地址数据", CdsCrawlUrl.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}