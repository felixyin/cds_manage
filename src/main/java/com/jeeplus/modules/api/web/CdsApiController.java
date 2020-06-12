/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.api.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.api.entity.CdsCrawlHostAndEmail;
import com.jeeplus.modules.api.service.CdsApiService;
import com.jeeplus.modules.crawl.setting.entity.CdsCrawlSetting;
import com.jeeplus.modules.crontab.setting.entity.CdsCrontabSetting;
import com.jeeplus.modules.email.category.entity.CdsEmailCategory;
import com.jeeplus.modules.email.template.entity.CdsEmailTemplate;
import com.jeeplus.modules.keyword.entity.CdsKeyword;
import com.jeeplus.modules.leadin.entity.CdsLeadIn;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 爬取的邮箱Controller
 *
 * @author 尹彬
 * @version 2019-03-21
 */
@Api(value = "CdsApiController", description = "接口控制器(调用前请先登录)")
@Controller
@RequestMapping(value = "${adminPath}/api")
public class CdsApiController extends BaseController {

    @Resource
    private CdsApiService cdsApiService;


    /**
     * 获取用户的搜索设置参数
     */
    @ApiOperation(notes = "loadSearchSetting", httpMethod = "POST", value = "获取用户的搜索设置")
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "loadSearchSetting")
    public AjaxJson loadSearchSetting(Model model) throws Exception {
        CdsCrawlSetting cdsCrawlSetting = cdsApiService.loadSearchSettingByUser();
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("cdsCrawlSetting", cdsCrawlSetting);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setBody(data);
        j.setMsg("获取用户的搜索设置成功");
        return j;
    }


    /**
     * 保存用户的搜索设置
     */
    @ApiOperation(notes = "saveSearchSetting", httpMethod = "POST", value = "保存用户的搜索设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siteQueue", value = "站点队列长度，默认100，最小10，最大100", required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "siteSleep", value = "站点入队间隔时间，默认500毫秒，最小100，最大5000", required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "diggingDepth", value = "站点挖掘深度，默认2层，最小1，最大5", required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "threadCount", value = "链接线程并发数，默认一个站点内20并发，最小1，最大50", required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "threadSleep", value = "链接线程间隔时间，默认间隔200毫秒，最小100，最大5000", required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "fetchTimeout", value = "链接抓取超时时间，默认1000毫秒，最小100，最大5000", required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "isNeedDistinct", value = "是否去除重复(0不去除/1去除)", required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "isVerifyEmail", value = "是否验证邮箱(0不验证/1验证)", required = false, paramType = "query", dataType = "integer")
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "saveSearchSetting")
    public AjaxJson saveSearchSetting(CdsCrawlSetting cdsCrawlSetting, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        String errMsg = beanValidator(cdsCrawlSetting);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        cdsApiService.saveSearchSettingForUser(cdsCrawlSetting);

        j.setSuccess(true);
        j.setMsg("保存用户的搜索设置成功");
        return j;
    }


    /**
     * 保存用户的搜索关键词
     */
    @ApiOperation(notes = "saveKeyword", httpMethod = "POST", value = "保存用户的搜索关键词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键词", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "saveKeyword")
    public AjaxJson saveKeyword(String keyword, Model model) throws Exception {
        cdsApiService.saveCdsrKeywordSeeker(keyword);

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("保存用户的搜索关键词成功");
        return j;
    }


    /**
     * 获取此Host之前爬取的邮箱数据
     */
    @ApiOperation(notes = "loadHostAndEmail", httpMethod = "POST", value = "获取域名下的邮箱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "whost", value = "域名", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "loadHostAndEmail")
    public AjaxJson loadHostAndEmail(String whost, Model model) throws Exception {
        List<CdsCrawlHostAndEmail> crawlHostAndEmails = cdsApiService.loadHostAndEmail(whost);

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("hostAndEmails", crawlHostAndEmails);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setBody(data);
        j.setMsg("根据域名获取邮箱成功");
        return j;
    }


    /**
     * 批量保存用户的爬取结果
     */
    @ApiOperation(notes = "saveCrawlResult", httpMethod = "POST", value = "批量保存用户的爬取结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "crawlResultJson", value = "爬取结果的json", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "saveCrawlResult")
    public AjaxJson saveCrawlResult(String crawlResultJson, Model model) throws Exception {
        cdsApiService.saveCrawlResult(crawlResultJson);

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("批量保存用户的爬取结果成功");
        return j;
    }


    /**
     * 查询用户163邮箱设置
     */
    @ApiOperation(notes = "loadEmailSetting", httpMethod = "POST", value = "查询用户163发件邮箱设置")
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "loadEmailSetting")
    public AjaxJson loadEmailSetting(Model model) throws Exception {
        CdsEmailCategory emailCategory = cdsApiService.loadEmailSetting();

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("emailCategory", emailCategory);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setBody(data);
        j.setMsg("查询用户163发件邮箱设置成功");
        return j;
    }


    /**
     * 保存用户163邮箱设置
     */
    @ApiOperation(notes = "saveEmailSetting", httpMethod = "POST", value = "保存用户163发件邮箱设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "host", value = "SMTP地址", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "post", value = "端口号", required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "service", value = "服务", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "emailAccounts", value = "账号，密码之间逗号分隔，每组采用换行分隔", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "saveEmailSetting")
    public AjaxJson saveEmailSetting(CdsEmailCategory emailCategory, Model model) throws Exception {
        emailCategory.setCategoryName("163企业邮箱"); // 1 163邮箱

        AjaxJson j = new AjaxJson();
        String errMsg = beanValidator(emailCategory);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }

        cdsApiService.saveEmailSetting(emailCategory);
        j.setSuccess(true);
        j.setMsg("保存用户163发件邮箱设置成功");
        return j;
    }


    /**
     * 查询用户定时器设置
     */
    @ApiOperation(notes = "loadCrontabSetting", httpMethod = "POST", value = "查询用户定时器设置")
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "loadCrontabSetting")
    public AjaxJson loadCrontabSetting(Model model) throws Exception {
        CdsCrontabSetting crontabSetting = cdsApiService.loadCrontabSetting();

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("crontabSetting", crontabSetting);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setBody(data);
        j.setMsg("查询用户定时器设置成功");
        return j;
    }


    /**
     * 保存用户定时器设置
     */
    @ApiOperation(notes = "saveCrontabSetting", httpMethod = "POST", value = "保存用户定时器设置")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "minute", value = "分(0-59)", required = false, paramType = "query", dataType = "integer"),
//            @ApiImplicitParam(name = "hour", value = "时(0-23)", required = false, paramType = "query", dataType = "integer"),
//            @ApiImplicitParam(name = "dayOfWeek", value = "周几(1-7)", required = false, paramType = "query", dataType = "integer"),
//            @ApiImplicitParam(name = "date", value = "日(1-31)", required = false, paramType = "query", dataType = "integer"),
//            @ApiImplicitParam(name = "month", value = "月(1-12)", required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "exeTime", value = "定时时间", required = false, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "saveCrontabSetting")
    public AjaxJson saveCrontabSetting(CdsCrontabSetting crontabSetting, Model model) throws Exception {
        cdsApiService.saveCrontabSetting(crontabSetting);

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("保存用户定时器设置成功");
        return j;
    }


    /**
     * 查询用户邮件模版列表
     */
    @ApiOperation(notes = "findAllEmailTemplates", httpMethod = "POST", value = "查询用户邮件模版列表")
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "findAllEmailTemplates")
    public AjaxJson findAllEmailTemplates(Model model) throws Exception {
        List<CdsEmailTemplate> emailTemplates = cdsApiService.findAllEmailTemplates();

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        AjaxJson j = new AjaxJson();
        data.put("emailTemplates", emailTemplates);
        j.setSuccess(true);
        j.setBody(data);
        j.setMsg("查询用户邮件模版列表成功");
        return j;
    }


    /**
     * 根据ID查询用户邮件模版
     */
    @ApiOperation(notes = "loadEmailTemplate", httpMethod = "POST", value = "根据ID查询用户邮件模版")
    @ApiParam(name = "id", value = "id主键", required = true)
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "loadEmailTemplate")
    public AjaxJson loadEmailTemplate(String id, Model model) throws Exception {
        CdsEmailTemplate emailTemplate = cdsApiService.loadEmailTemplate(id);

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        AjaxJson j = new AjaxJson();
        data.put("emailTemplate", emailTemplate);
        j.setSuccess(true);
        j.setBody(data);
        j.setMsg("根据ID查询用户邮件模版成功");
        return j;
    }


    /**
     * 保存用户邮件模版
     */
    @ApiOperation(notes = "saveEmailTemplate", httpMethod = "POST", value = "保存用户邮件模版")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id主键", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "subject", value = "邮件标题", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mainBody", value = "正文内容", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isEnable", value = "是否启用", required = false, paramType = "query", dataType = "integer"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:saveSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "saveEmailTemplate")
    public AjaxJson saveEmailTemplate(CdsEmailTemplate emailTemplate, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
//        String errMsg = beanValidator(emailTemplate);
//        if (StringUtils.isNotBlank(errMsg)) {
//            j.setSuccess(false);
//            j.setMsg(errMsg);
//            return j;
//        }

        cdsApiService.saveEmailTemplate(emailTemplate);
        j.setSuccess(true);
        j.setMsg("保存用户邮件模版成功");
        return j;
    }


    /**
     * 选中用户邮件模版
     */
    @ApiOperation(notes = "selectEmailTemplate", httpMethod = "POST", value = "选择用户邮件模版")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id主键", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:saveSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "selectEmailTemplate")
    public AjaxJson selectEmailTemplate(String id, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
//        String errMsg = beanValidator(emailTemplate);
//        if (StringUtils.isNotBlank(errMsg)) {
//            j.setSuccess(false);
//            j.setMsg(errMsg);
//            return j;
//        }

        cdsApiService.selectEmailTemplate(id);
        j.setSuccess(true);
        j.setMsg("保存用户邮件模版成功");
        return j;
    }



    /**
     * 删除用户邮件模版
     */
    @ApiOperation(notes = "deleteEmailTemplate", httpMethod = "POST", value = "删除用户邮件模版")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id主键", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:saveSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "deleteEmailTemplate")
    public AjaxJson deleteEmailTemplate(String id, Model model) throws Exception {
        cdsApiService.deleteEmailTemplate(id);

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("删除用户邮件模版成功");
        return j;
    }


    /**
     * 获取用户的所有搜索关键词、选择关键词和email数据
     */
    @ApiOperation(notes = "findKeywordSeekers", httpMethod = "POST", value = "获取用户的所有搜索关键词、选择关键词和email数据")
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "findKeywordSeekers")
    public AjaxJson findKeywordSeekers(Model model) throws Exception {
        List<CdsKeyword> keywords = cdsApiService.findUserKeywords();

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("keywords", keywords);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setBody(data);
        j.setMsg("获取用户的所有搜索关键词、选择关键词和email数据成功");
        return j;
    }


    /**
     * 根据关键词ID获取用户的搜索关键词和email数据
     */
    @ApiOperation(notes = "findHostAndUrlEmail", httpMethod = "POST", value = "根据关键词ID获取用户的搜索关键词和email数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywordId", value = "关键词主键", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "findHostAndUrlEmail")
    public AjaxJson findHostAndUrlEmail(String keywordId, Model model) throws Exception {
        List<CdsCrawlHostAndEmail> crawlHostAndEmails = cdsApiService.findHostAndUrlEmail(keywordId);

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("crawlHostAndEmails", crawlHostAndEmails);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setBody(data);
        j.setMsg("根据关键词ID获取用户的搜索关键词和email数据成功");
        return j;
    }


    /**
     * 用户选择某个关键词
     */
    @ApiOperation(notes = "selectedKeyword", httpMethod = "POST", value = "用户选择某个关键词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywordId", value = "关键词主键", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "selectedKeyword")
    public AjaxJson selectedKeyword(String keywordId, Model model) throws Exception {
        cdsApiService.selectedKeyword(keywordId);

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("用户选择某个关键词成功");
        return j;
    }


    /**
     * 用户取消搜索关键词的选中
     */
    @ApiOperation(notes = "unSelectedKeyword", httpMethod = "POST", value = "用户取消搜索关键词的选中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywordId", value = "选择选项主键", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:saveSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "unSelectedKeyword")
    public AjaxJson unSelectedKeyword(String keywordId, Model model) throws Exception {
        cdsApiService.unSelectedKeyword(keywordId);

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("用户取消搜索关键词的选中成功");
        return j;
    }


    /**
     * 用户删除搜索关键词和数据
     */
    @ApiOperation(notes = "deleteKeyword", httpMethod = "POST", value = "用户删除搜索关键词和数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywordId", value = "搜索关键词主键", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:saveSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "deleteKeyword")
    public AjaxJson deleteKeyword(String keywordId, Model model) throws Exception {
        cdsApiService.deleteKeyword(keywordId);

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("用户删除搜索关键词和数据成功");
        return j;
    }


    /**
     * 获取导入的的所有分类、选择的分类
     */
    @ApiOperation(notes = "findUserLeadInCategories", httpMethod = "POST", value = "获取导入的的所有分类、选择的分类")
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "findUserLeadInCategories")
    public AjaxJson findUserLeadInCategorys(Model model) throws Exception {
        List<Map<String, Object>> categorise = cdsApiService.findUserLeadInCategorys();

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("keywords", categorise);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setBody(data);
        j.setMsg("获取导入的的所有分类、选择的分类成功");
        return j;
    }


    /**
     * 根据分类返回邮箱列表
     */
    @ApiOperation(notes = "findLeadInEmails", httpMethod = "POST", value = "根据分类返回邮箱列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryName", value = "分类名称", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "findLeadInEmails")
    public AjaxJson findLeadInEmails(String categoryName, Model model) throws Exception {
        List<CdsLeadIn> leadIns = cdsApiService.findLeadInEmails(categoryName);

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("leadIns", leadIns);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setBody(data);
        j.setMsg("根据分类返回邮箱列表成功");
        return j;
    }


    /**
     * 用户选择分类
     */
    @ApiOperation(notes = "selectedLeadInCategory", httpMethod = "POST", value = "用户选择分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryName", value = "分类名称", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:loadSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "selectedLeadInCategory")
    public AjaxJson selectedLeadInCategory(String categoryName, Model model) throws Exception {
        cdsApiService.selectedLeadInCategory(categoryName);

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("用户选择分类成功");
        return j;
    }


    /**
     * 用户取消搜索关键词的选中
     */
    @ApiOperation(notes = "unSelectedLeadInCategory", httpMethod = "POST", value = "用户取消分类的选中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryName", value = "分类名称", required = true, paramType = "query", dataType = "string"),
    })
    @ResponseBody
//    @RequiresPermissions(value = {"api:saveSearchSettingByUser"}, logical = Logical.OR)
    @RequestMapping(value = "unSelectedLeadInCategory")
    public AjaxJson unSelectedLeadInCategory(String categoryName, Model model) throws Exception {
        cdsApiService.unSelectedLeadInCategory(categoryName);

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("用户取消分类的选中成功");
        return j;
    }


}