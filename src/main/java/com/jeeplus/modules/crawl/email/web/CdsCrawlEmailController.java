/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.crawl.email.web;

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
import com.jeeplus.modules.crawl.email.entity.CdsCrawlEmail;
import com.jeeplus.modules.crawl.email.service.CdsCrawlEmailService;

/**
 * 爬取的邮箱Controller
 * @author 尹彬
 * @version 2019-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/crawl/email/cdsCrawlEmail")
public class CdsCrawlEmailController extends BaseController {

	@Autowired
	private CdsCrawlEmailService cdsCrawlEmailService;
	
	@ModelAttribute
	public CdsCrawlEmail get(@RequestParam(required=false) String id) {
		CdsCrawlEmail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsCrawlEmailService.get(id);
		}
		if (entity == null){
			entity = new CdsCrawlEmail();
		}
		return entity;
	}
	
	/**
	 * 爬取的邮箱列表页面
	 */
	@RequiresPermissions("crawl:email:cdsCrawlEmail:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsCrawlEmail cdsCrawlEmail, Model model) {
		model.addAttribute("cdsCrawlEmail", cdsCrawlEmail);
		return "modules/crawl/email/cdsCrawlEmailList";
	}
	
		/**
	 * 爬取的邮箱列表数据
	 */
	@ResponseBody
	@RequiresPermissions("crawl:email:cdsCrawlEmail:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsCrawlEmail cdsCrawlEmail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsCrawlEmail> page = cdsCrawlEmailService.findPage(new Page<CdsCrawlEmail>(request, response), cdsCrawlEmail); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑爬取的邮箱表单页面
	 */
	@RequiresPermissions(value={"crawl:email:cdsCrawlEmail:view","crawl:email:cdsCrawlEmail:add","crawl:email:cdsCrawlEmail:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsCrawlEmail cdsCrawlEmail, Model model) {
		model.addAttribute("cdsCrawlEmail", cdsCrawlEmail);
		return "modules/crawl/email/cdsCrawlEmailForm";
	}

	/**
	 * 保存爬取的邮箱
	 */
	@ResponseBody
	@RequiresPermissions(value={"crawl:email:cdsCrawlEmail:add","crawl:email:cdsCrawlEmail:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsCrawlEmail cdsCrawlEmail, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsCrawlEmail);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsCrawlEmailService.save(cdsCrawlEmail);//保存
		j.setSuccess(true);
		j.setMsg("保存爬取的邮箱成功");
		return j;
	}
	
	/**
	 * 删除爬取的邮箱
	 */
	@ResponseBody
	@RequiresPermissions("crawl:email:cdsCrawlEmail:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsCrawlEmail cdsCrawlEmail) {
		AjaxJson j = new AjaxJson();
		cdsCrawlEmailService.delete(cdsCrawlEmail);
		j.setMsg("删除爬取的邮箱成功");
		return j;
	}
	
	/**
	 * 批量删除爬取的邮箱
	 */
	@ResponseBody
	@RequiresPermissions("crawl:email:cdsCrawlEmail:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsCrawlEmailService.delete(cdsCrawlEmailService.get(id));
		}
		j.setMsg("删除爬取的邮箱成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("crawl:email:cdsCrawlEmail:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsCrawlEmail cdsCrawlEmail, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "爬取的邮箱"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsCrawlEmail> page = cdsCrawlEmailService.findPage(new Page<CdsCrawlEmail>(request, response, -1), cdsCrawlEmail);
    		new ExportExcel("爬取的邮箱", CdsCrawlEmail.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出爬取的邮箱记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("crawl:email:cdsCrawlEmail:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsCrawlEmail> list = ei.getDataList(CdsCrawlEmail.class);
			for (CdsCrawlEmail cdsCrawlEmail : list){
				try{
					cdsCrawlEmailService.save(cdsCrawlEmail);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条爬取的邮箱记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条爬取的邮箱记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入爬取的邮箱失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入爬取的邮箱数据模板
	 */
	@ResponseBody
	@RequiresPermissions("crawl:email:cdsCrawlEmail:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "爬取的邮箱数据导入模板.xlsx";
    		List<CdsCrawlEmail> list = Lists.newArrayList(); 
    		new ExportExcel("爬取的邮箱数据", CdsCrawlEmail.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}