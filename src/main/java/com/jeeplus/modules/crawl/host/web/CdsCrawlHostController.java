/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.host.web;

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
import com.jeeplus.modules.crawl.host.entity.CdsCrawlHost;
import com.jeeplus.modules.crawl.host.service.CdsCrawlHostService;

/**
 * 爬取的域名Controller
 * @author 尹彬
 * @version 2019-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/crawl/host/cdsCrawlHost")
public class CdsCrawlHostController extends BaseController {

	@Autowired
	private CdsCrawlHostService cdsCrawlHostService;
	
	@ModelAttribute
	public CdsCrawlHost get(@RequestParam(required=false) String id) {
		CdsCrawlHost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsCrawlHostService.get(id);
		}
		if (entity == null){
			entity = new CdsCrawlHost();
		}
		return entity;
	}
	
	/**
	 * 爬取的域名列表页面
	 */
	@RequiresPermissions("crawl:host:cdsCrawlHost:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsCrawlHost cdsCrawlHost, Model model) {
		model.addAttribute("cdsCrawlHost", cdsCrawlHost);
		return "modules/crawl/host/cdsCrawlHostList";
	}
	
		/**
	 * 爬取的域名列表数据
	 */
	@ResponseBody
	@RequiresPermissions("crawl:host:cdsCrawlHost:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsCrawlHost cdsCrawlHost, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsCrawlHost> page = cdsCrawlHostService.findPage(new Page<CdsCrawlHost>(request, response), cdsCrawlHost); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑爬取的域名表单页面
	 */
	@RequiresPermissions(value={"crawl:host:cdsCrawlHost:view","crawl:host:cdsCrawlHost:add","crawl:host:cdsCrawlHost:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsCrawlHost cdsCrawlHost, Model model) {
		model.addAttribute("cdsCrawlHost", cdsCrawlHost);
		return "modules/crawl/host/cdsCrawlHostForm";
	}

	/**
	 * 保存爬取的域名
	 */
	@ResponseBody
	@RequiresPermissions(value={"crawl:host:cdsCrawlHost:add","crawl:host:cdsCrawlHost:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsCrawlHost cdsCrawlHost, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsCrawlHost);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsCrawlHostService.save(cdsCrawlHost);//保存
		j.setSuccess(true);
		j.setMsg("保存爬取的域名成功");
		return j;
	}
	
	/**
	 * 删除爬取的域名
	 */
	@ResponseBody
	@RequiresPermissions("crawl:host:cdsCrawlHost:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsCrawlHost cdsCrawlHost) {
		AjaxJson j = new AjaxJson();
		cdsCrawlHostService.delete(cdsCrawlHost);
		j.setMsg("删除爬取的域名成功");
		return j;
	}
	
	/**
	 * 批量删除爬取的域名
	 */
	@ResponseBody
	@RequiresPermissions("crawl:host:cdsCrawlHost:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsCrawlHostService.delete(cdsCrawlHostService.get(id));
		}
		j.setMsg("删除爬取的域名成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("crawl:host:cdsCrawlHost:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsCrawlHost cdsCrawlHost, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "爬取的域名"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsCrawlHost> page = cdsCrawlHostService.findPage(new Page<CdsCrawlHost>(request, response, -1), cdsCrawlHost);
    		new ExportExcel("爬取的域名", CdsCrawlHost.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出爬取的域名记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("crawl:host:cdsCrawlHost:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsCrawlHost> list = ei.getDataList(CdsCrawlHost.class);
			for (CdsCrawlHost cdsCrawlHost : list){
				try{
					cdsCrawlHostService.save(cdsCrawlHost);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条爬取的域名记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条爬取的域名记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入爬取的域名失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入爬取的域名数据模板
	 */
	@ResponseBody
	@RequiresPermissions("crawl:host:cdsCrawlHost:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "爬取的域名数据导入模板.xlsx";
    		List<CdsCrawlHost> list = Lists.newArrayList(); 
    		new ExportExcel("爬取的域名数据", CdsCrawlHost.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}