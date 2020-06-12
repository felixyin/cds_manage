/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.email.ruser.web;

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
import com.jeeplus.modules.email.ruser.entity.CdsEmailUser;
import com.jeeplus.modules.email.ruser.service.CdsEmailUserService;

/**
 * 我的邮箱Controller
 * @author 尹彬
 * @version 2019-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/email/ruser/cdsEmailUser")
public class CdsEmailUserController extends BaseController {

	@Autowired
	private CdsEmailUserService cdsEmailUserService;
	
	@ModelAttribute
	public CdsEmailUser get(@RequestParam(required=false) String id) {
		CdsEmailUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cdsEmailUserService.get(id);
		}
		if (entity == null){
			entity = new CdsEmailUser();
		}
		return entity;
	}
	
	/**
	 * 我的邮箱列表页面
	 */
	@RequiresPermissions("email:ruser:cdsEmailUser:list")
	@RequestMapping(value = {"list", ""})
	public String list(CdsEmailUser cdsEmailUser, Model model) {
		model.addAttribute("cdsEmailUser", cdsEmailUser);
		return "modules/email/ruser/cdsEmailUserList";
	}
	
		/**
	 * 我的邮箱列表数据
	 */
	@ResponseBody
	@RequiresPermissions("email:ruser:cdsEmailUser:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CdsEmailUser cdsEmailUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CdsEmailUser> page = cdsEmailUserService.findPage(new Page<CdsEmailUser>(request, response), cdsEmailUser); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑我的邮箱表单页面
	 */
	@RequiresPermissions(value={"email:ruser:cdsEmailUser:view","email:ruser:cdsEmailUser:add","email:ruser:cdsEmailUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CdsEmailUser cdsEmailUser, Model model) {
		model.addAttribute("cdsEmailUser", cdsEmailUser);
		return "modules/email/ruser/cdsEmailUserForm";
	}

	/**
	 * 保存我的邮箱
	 */
	@ResponseBody
	@RequiresPermissions(value={"email:ruser:cdsEmailUser:add","email:ruser:cdsEmailUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CdsEmailUser cdsEmailUser, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(cdsEmailUser);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cdsEmailUserService.save(cdsEmailUser);//保存
		j.setSuccess(true);
		j.setMsg("保存我的邮箱成功");
		return j;
	}
	
	/**
	 * 删除我的邮箱
	 */
	@ResponseBody
	@RequiresPermissions("email:ruser:cdsEmailUser:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CdsEmailUser cdsEmailUser) {
		AjaxJson j = new AjaxJson();
		cdsEmailUserService.delete(cdsEmailUser);
		j.setMsg("删除我的邮箱成功");
		return j;
	}
	
	/**
	 * 批量删除我的邮箱
	 */
	@ResponseBody
	@RequiresPermissions("email:ruser:cdsEmailUser:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cdsEmailUserService.delete(cdsEmailUserService.get(id));
		}
		j.setMsg("删除我的邮箱成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("email:ruser:cdsEmailUser:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(CdsEmailUser cdsEmailUser, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "我的邮箱"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CdsEmailUser> page = cdsEmailUserService.findPage(new Page<CdsEmailUser>(request, response, -1), cdsEmailUser);
    		new ExportExcel("我的邮箱", CdsEmailUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出我的邮箱记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("email:ruser:cdsEmailUser:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CdsEmailUser> list = ei.getDataList(CdsEmailUser.class);
			for (CdsEmailUser cdsEmailUser : list){
				try{
					cdsEmailUserService.save(cdsEmailUser);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条我的邮箱记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条我的邮箱记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入我的邮箱失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入我的邮箱数据模板
	 */
	@ResponseBody
	@RequiresPermissions("email:ruser:cdsEmailUser:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "我的邮箱数据导入模板.xlsx";
    		List<CdsEmailUser> list = Lists.newArrayList(); 
    		new ExportExcel("我的邮箱数据", CdsEmailUser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}