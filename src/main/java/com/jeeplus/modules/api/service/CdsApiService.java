/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.api.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.collection.ListUtil;
import com.jeeplus.modules.api.entity.CdsCrawlHostAndEmail;
import com.jeeplus.modules.api.mapper.CdsApiMapper;
import com.jeeplus.modules.crawl.email.entity.CdsCrawlEmail;
import com.jeeplus.modules.crawl.email.service.CdsCrawlEmailService;
import com.jeeplus.modules.crawl.host.entity.CdsCrawlHost;
import com.jeeplus.modules.crawl.host.service.CdsCrawlHostService;
import com.jeeplus.modules.crawl.rkeyword.entity.CdsrCrawlKeyword;
import com.jeeplus.modules.crawl.rkeyword.service.CdsrCrawlKeywordService;
import com.jeeplus.modules.crawl.setting.entity.CdsCrawlSetting;
import com.jeeplus.modules.crawl.setting.service.CdsCrawlSettingService;
import com.jeeplus.modules.crawl.url.entity.CdsCrawlUrl;
import com.jeeplus.modules.crawl.url.service.CdsCrawlUrlService;
import com.jeeplus.modules.crontab.rkeywordleadin.entity.CdsrCrontabKeywordLeadIn;
import com.jeeplus.modules.crontab.rkeywordleadin.service.CdsrCrontabKeywordLeadInService;
import com.jeeplus.modules.crontab.setting.entity.CdsCrontabSetting;
import com.jeeplus.modules.crontab.setting.service.CdsCrontabSettingService;
import com.jeeplus.modules.email.category.entity.CdsEmailCategory;
import com.jeeplus.modules.email.category.service.CdsEmailCategoryService;
import com.jeeplus.modules.email.ruser.entity.CdsrEmailUser;
import com.jeeplus.modules.email.ruser.service.CdsrEmailUserService;
import com.jeeplus.modules.email.template.entity.CdsEmailTemplate;
import com.jeeplus.modules.email.template.service.CdsEmailTemplateService;
import com.jeeplus.modules.keyword.entity.CdsKeyword;
import com.jeeplus.modules.keyword.service.CdsKeywordService;
import com.jeeplus.modules.keyword_seeker.entity.CdsrKeywordSeeker;
import com.jeeplus.modules.keyword_seeker.service.CdsrKeywordSeekerService;
import com.jeeplus.modules.leadin.entity.CdsLeadIn;
import com.jeeplus.modules.leadin.service.CdsLeadInService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 爬取参数设置Service
 *
 * @author 尹彬
 * @version 2019-03-21
 */
@Service
@Transactional(readOnly = true)
public class CdsApiService {

    @Resource
    private CdsApiMapper cdsApiMapper;

    @Resource
    private CdsCrawlSettingService cdsCrawlSettingService;

    @Resource
    private CdsCrawlHostService cdsCrawlHostService;

    @Resource
    private CdsCrawlUrlService cdsCrawlUrlService;

    @Resource
    private CdsCrawlEmailService cdsCrawlEmailService;

    @Resource
    private CdsrCrawlKeywordService cdsrCrawlKeywordService;

    @Resource
    private CdsrKeywordSeekerService cdsrKeywordSeekerService;

    @Resource
    private CdsKeywordService cdsKeywordService;

    @Resource
    private CdsEmailCategoryService cdsEmailCategoryService;

    @Resource
    private CdsrEmailUserService cdsrEmailUserService;

    @Resource
    private CdsCrontabSettingService cdsCrontabSettingService;

    @Resource
    private CdsEmailTemplateService cdsEmailTemplateService;

    @Resource
    private CdsrCrontabKeywordLeadInService cdsrCrontabKeywordLeadInService;

    @Resource
    private CdsLeadInService cdsLeadInService;

    /**
     * 查询用户的搜索设置
     */
    @Transactional(readOnly = false)
    public CdsCrawlSetting loadSearchSettingByUser() {
        CdsCrawlSetting crawlSetting = cdsCrawlSettingService.findUniqueByProperty("owner_id", UserUtils.getUser().getId());
        if (null == crawlSetting) { // 如果用户从来没有设置过爬虫参数，使用默认参数配置
            crawlSetting = new CdsCrawlSetting();
        }
        return crawlSetting;
    }


    /**
     * 保存用户的搜索设置
     *
     * @return
     */
    @Transactional(readOnly = false)
    public CdsCrawlSetting saveSearchSettingForUser(CdsCrawlSetting cdsCrawlSetting) {
        User user = UserUtils.getUser();
        CdsCrawlSetting oldCdsCrawlSetting = cdsCrawlSettingService.findUniqueByProperty("owner_id", user.getId());
        if (null != oldCdsCrawlSetting) { // 已经设置过了，这次是修改
//            将oldCdsCrawlSetting的属性复制到cdsCrawlSetting
            BeanUtil.copyProperties(cdsCrawlSetting, oldCdsCrawlSetting, false, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            oldCdsCrawlSetting.setIsNewRecord(false);
            oldCdsCrawlSetting.setOwner(user);
            cdsCrawlSettingService.save(oldCdsCrawlSetting);
        } else { // 第一次设置
            cdsCrawlSetting.setOwner(user);
            cdsCrawlSettingService.save(cdsCrawlSetting);

        }
        return oldCdsCrawlSetting;
    }

    /**
     * 保存用户搜索的关键词
     */
    @Transactional(readOnly = false)
    public CdsrKeywordSeeker saveCdsrKeywordSeeker(String keyword) {
        CdsrKeywordSeeker cdsrKeywordSeeker = new CdsrKeywordSeeker();

        CdsKeyword cdsKeyword = cdsKeywordService.findUniqueByProperty("keyword", keyword);
        if (null != cdsKeyword) { // 已经存在这个关键词则关联给当前搜索者
            cdsKeyword.setTimes(cdsKeyword.getTimes() + 1);
        } else { // 不存在则新建关键词，并关联
            cdsKeyword = new CdsKeyword();
            cdsKeyword.setKeyword(keyword);
            cdsKeyword.setTimes(1);
        }
        cdsKeywordService.save(cdsKeyword);

//        设置搜索条件
        cdsrKeywordSeeker.setKeyword(cdsKeyword);
        cdsrKeywordSeeker.setOwner(UserUtils.getUser());

        List<CdsrKeywordSeeker> list = cdsrKeywordSeekerService.findList(cdsrKeywordSeeker);
        if (null == list || list.size() < 1) { // 没有则需要保存

            cdsrKeywordSeeker.setSearchTime(new Date()); // 最后搜索时间
            cdsrKeywordSeekerService.save(cdsrKeywordSeeker);
            return cdsrKeywordSeeker;
        } else {
            CdsrKeywordSeeker cdsrKeywordSeeker1 = list.get(0);
            cdsrKeywordSeeker1.setSearchTime(new Date()); // 最后搜索时间
            cdsrKeywordSeekerService.save(cdsrKeywordSeeker1); // 修改更新日期字段
            return cdsrKeywordSeeker1;
        }
    }


    /**
     * 获取用户的搜索关键词
     *
     * @return
     */
    public List<CdsKeyword> loadCdsrKeywordSeeker() {
        return cdsApiMapper.findKeywordByUser(UserUtils.getUser());
    }

    /**
     * 批量保存用户的爬取结果
     *
     * @param crawlResultJson
     */
    @Transactional(readOnly = false)
    public void saveCrawlResult(String crawlResultJson) throws UnsupportedEncodingException {
        if (null == crawlResultJson) return;
        crawlResultJson = crawlResultJson.replaceAll("&quot;", "\"");
        System.out.println(crawlResultJson);
        /* output:
          {
              keyword:"furniture",
              crawlResult:[
                  {
                      "emails": [
                          "internetsalessupport@roomstogo.com",
                          "customerservice@roomstogo.com"
                      ],
                      "host": "www.roomstogo.com",
                      "title": "Rooms To Go",
                      "url": "https://www.roomstogo.com/content/Customer-Service/Customer-Support"
                  }
              ]
          }
        */
        JSONObject jsonObject = JSON.parseObject(crawlResultJson);
        String keyword = jsonObject.getString("keyword");
        CdsrKeywordSeeker cdsrKeywordSeeker = saveCdsrKeywordSeeker(keyword);

        JSONArray crawlResult = jsonObject.getJSONArray("crawlResult");
        for (Object obj : crawlResult) {
            JSONObject jo = (JSONObject) obj;
            String host = jo.getString("host");
            String url = jo.getString("url");
            String title = jo.getString("title");
            JSONArray emails = jo.getJSONArray("emails");

//            保存host表
            CdsCrawlHost crawlHost = cdsCrawlHostService.findUniqueByProperty("host", host);
            if (null == crawlHost) {
                crawlHost = new CdsCrawlHost();
                crawlHost.setCrawlTimes(1L);
                crawlHost.setHost(host);
            } else {
                crawlHost.setCrawlTimes(crawlHost.getCrawlTimes() + 1L);
            }
            crawlHost.setIsFindEmail(emails.size() > 0 ? 1 : 0);
            cdsCrawlHostService.save(crawlHost);

//            保存url表
            CdsCrawlUrl crawlUrl = cdsCrawlUrlService.findUniqueByProperty("url", url);
            if (null == crawlUrl) {
                crawlUrl = new CdsCrawlUrl();
                crawlUrl.setCrawlHost(crawlHost);
                crawlUrl.setCrawlTimes(1L);
                crawlUrl.setTitle(title);
                crawlUrl.setUrl(url);
            } else {
                crawlUrl.setCrawlHost(crawlHost);
                crawlUrl.setCrawlTimes(crawlUrl.getCrawlTimes() + 1L);
            }
            cdsCrawlUrlService.save(crawlUrl);

//            保存crawl_keyword表
            CdsrCrawlKeyword crawlKeyword = new CdsrCrawlKeyword();
            crawlKeyword.setCrawlUrl(crawlUrl);
            crawlKeyword.setKeyword(cdsrKeywordSeeker.getKeyword());
            List<CdsrCrawlKeyword> crawlKeywords = cdsrCrawlKeywordService.findList(crawlKeyword);
            if (null == crawlKeywords || crawlKeywords.size() < 1) {
                cdsrCrawlKeywordService.save(crawlKeyword);
            }

            for (Object eo : emails) {
                String email = (String) eo;
                System.out.println(email);

//                保存email表
                CdsCrawlEmail crawlEmail = new CdsCrawlEmail();
                crawlEmail.setEmail(email);
                crawlEmail.setCrawlHost(crawlHost);
                List<CdsCrawlEmail> crawlEmails = cdsCrawlEmailService.findList(crawlEmail);
                if (null == crawlEmails || crawlEmails.size() < 1) {
                    cdsCrawlEmailService.save(crawlEmail);
                }
            }
        }

    }

    /**
     * 根据host，查询下面的url、title、email array
     *
     * @param host
     * @return
     */
    public List<CdsCrawlHostAndEmail> loadHostAndEmail(String host) {
        List<CdsCrawlHostAndEmail> crawlHostAndEmails = cdsApiMapper.findHostAndUrl(host);
        for (CdsCrawlHostAndEmail crawlHostAndEmail : crawlHostAndEmails) {
            CdsCrawlHost crawlHost = new CdsCrawlHost();
            crawlHost.setHost(crawlHostAndEmail.getHost());
            CdsCrawlEmail crawlEmail = new CdsCrawlEmail();
            crawlEmail.setCrawlHost(crawlHost);
            List<CdsCrawlEmail> crawlEmails = cdsCrawlEmailService.findList(crawlEmail);
            for (CdsCrawlEmail email : crawlEmails) {
                email.setCreateBy(null);
                email.setUpdateBy(null);
                email.setCreateDate(null);
                email.setUpdateDate(null);
                email.setCrawlHost(null);
            }
            crawlHostAndEmail.setEmails(crawlEmails);
        }
        return crawlHostAndEmails;
    }

    public static List removeDuplicate(String[] list){
        List listTemp = new ArrayList();
        for (int i = 0; i < list.length; i++) {
            Object o = list[i];
            if (!listTemp.contains(o)) {
                listTemp.add(o);
            }
        }
        return listTemp;
    }

    /**
     * 保存用户的163邮箱设置
     *
     * @param emailCategory
     */
    @Transactional(readOnly = false)
    public void saveEmailSetting(CdsEmailCategory emailCategory) {
        System.out.println(emailCategory.getEmailAccounts());

//        查询当前用户的email_category，如果找到了，修改；否则新增
        CdsEmailCategory paramEmailCategory = new CdsEmailCategory();
        User user = UserUtils.getUser();
        paramEmailCategory.setOwner(user);
        List<CdsEmailCategory> emailCategories = cdsEmailCategoryService.findList(paramEmailCategory);
        if (null != emailCategories && emailCategories.size() == 1) {
            CdsEmailCategory emailCategory1 = emailCategories.get(0);
            emailCategory.setId(emailCategory1.getId());
        }

//        保存email_category
        emailCategory.setOwner(user);
        cdsEmailCategoryService.save(emailCategory);

//        获取邮箱账号字符串，解析为每个邮箱
        String emailAccounts = emailCategory.getEmailAccounts();
        String[] acounts = {};
        if (SystemUtils.IS_OS_WINDOWS) {
            acounts = emailAccounts.split("\r\n");
        } else { // unix/linux
            acounts = emailAccounts.split("\n");
        }

//        清除已经保存的发件箱
        CdsrEmailUser emailUser2 = new CdsrEmailUser();
        emailUser2.setEmailCategory(emailCategory);
        List<CdsrEmailUser> cdsrEmailUserServiceList = cdsrEmailUserService.findList(emailUser2);
        for (CdsrEmailUser cdsrEmailUser : cdsrEmailUserServiceList) {
            cdsrEmailUserService.delete(cdsrEmailUser);
        }

//        循环，email_user建立关联关系后，保存
       List<String> acounts1 = removeDuplicate(acounts);
        for (int i = 0, acounts1Size = acounts1.size(); i < acounts1Size; i++) {
            String acount = acounts1.get(i);
            String[] split = acount.split(",");
            String sendEmail = split[0];
            String password = split[1];

            CdsrEmailUser emailUser = new CdsrEmailUser();
            emailUser.setEmailCategory(emailCategory);
            emailUser.setSendEmail(sendEmail);
            emailUser.setPassword(password);
            emailUser.setSendTimes(0);
            emailUser.setRemarks(""+i);
            cdsrEmailUserService.save(emailUser);
        }

    }

    /**
     * 查询用户的163邮箱设置
     *
     * @return
     */
    public CdsEmailCategory loadEmailSetting() {
        User user = UserUtils.getUser();

//        根据当前用户查询email_category
        CdsEmailCategory emailCategory = new CdsEmailCategory();
        emailCategory.setOwner(user);
        List<CdsEmailCategory> emailCategories = cdsEmailCategoryService.findList(emailCategory);

//        根据email_category_id查询email_user表
        if (null != emailCategories && emailCategories.size() == 1) {
            CdsEmailCategory cdsEmailCategory = emailCategories.get(0);
            CdsrEmailUser emailUser = new CdsrEmailUser();
            emailUser.setEmailCategory(cdsEmailCategory);
            List<CdsrEmailUser> emailUsers = cdsrEmailUserService.findList(emailUser);

//        拼接邮箱账号字符串，设置到email_category
            StringBuilder emailAccounts = new StringBuilder();
            for (CdsrEmailUser cdsrEmailUser : emailUsers) {
                emailAccounts.append(cdsrEmailUser.getSendEmail()).append(",").append(cdsrEmailUser.getPassword());
                if (SystemUtils.IS_OS_WINDOWS) {
                    emailAccounts.append("\r\n");
                } else { // unix/linux
                    emailAccounts.append("\n");
                }
            }
            cdsEmailCategory.setEmailAccounts(emailAccounts.toString());

//            去掉不需要的信息
            cdsEmailCategory.setOwner(null);
            cdsEmailCategory.setCreateBy(null);
            cdsEmailCategory.setCreateDate(null);
            cdsEmailCategory.setUpdateBy(null);
            cdsEmailCategory.setUpdateDate(null);

            return cdsEmailCategory;
        }

        return null; // 没有找到发件邮箱设置
    }

    /**
     * 查询用户定时器设置
     *
     * @return
     */
    @Transactional(readOnly = false)
    public CdsCrontabSetting loadCrontabSetting() {
        CdsCrontabSetting crontabSetting = new CdsCrontabSetting();
        User user = UserUtils.getUser();
        crontabSetting.setOwner(user);

        List<CdsCrontabSetting> list = cdsCrontabSettingService.findList(crontabSetting);
        if (null != list && list.size() > 0) {
            CdsCrontabSetting crontabSetting1 = list.get(0);
            crontabSetting1.setOwner(null);
            crontabSetting1.setCreateBy(null);
            crontabSetting1.setCreateDate(null);
            crontabSetting1.setUpdateBy(null);
            crontabSetting1.setUpdateDate(null);
            return crontabSetting1;
        } else { // 默认初始化一个定时器配置
            crontabSetting.setExeTime(new Date());
            cdsCrontabSettingService.save(crontabSetting);
            return crontabSetting;
        }
    }

    /**
     * 保存用户定时器设置
     *
     * @param crontabSetting
     */
    @Transactional(readOnly = false)
    public void saveCrontabSetting(CdsCrontabSetting crontabSetting) {
        User user = UserUtils.getUser();
        crontabSetting.setOwner(user);

        CdsCrontabSetting paramCrontabSetting = new CdsCrontabSetting();
        paramCrontabSetting.setOwner(user);
        List<CdsCrontabSetting> list = cdsCrontabSettingService.findList(paramCrontabSetting);

        if (null != list && list.size() > 0) { // 更新
            CdsCrontabSetting crontabSetting1 = list.get(0);
            crontabSetting.setId(crontabSetting1.getId());
            cdsCrontabSettingService.save(crontabSetting);
        } else { // 添加
            cdsCrontabSettingService.save(crontabSetting);
        }
    }

    /**
     * 查询用户的所有邮件模版
     *
     * @return
     */
    public List<CdsEmailTemplate> findAllEmailTemplates() {
        CdsEmailTemplate emailTemplate = new CdsEmailTemplate();
        User user = UserUtils.getUser();
        emailTemplate.setOwner(user);
        List<CdsEmailTemplate> list = cdsEmailTemplateService.findList(emailTemplate);
        for (CdsEmailTemplate cdsEmailTemplate : list) {
            cdsEmailTemplate.setOwner(null);
            cdsEmailTemplate.setCreateBy(null);
            cdsEmailTemplate.setCreateDate(null);
            cdsEmailTemplate.setUpdateBy(null);
            cdsEmailTemplate.setUpdateDate(null);
        }
        return list;
    }

    /**
     * 根据id查询用户的邮件模版
     *
     * @param id
     * @return
     */
    public CdsEmailTemplate loadEmailTemplate(String id) {
        CdsEmailTemplate emailTemplate = cdsEmailTemplateService.get(id);
        if (null == emailTemplate) return new CdsEmailTemplate(); // 新建邮件模板
        emailTemplate.setOwner(null);
        emailTemplate.setCreateBy(null);
        emailTemplate.setCreateDate(null);
        emailTemplate.setUpdateBy(null);
        emailTemplate.setUpdateDate(null);
        return emailTemplate;
    }

    /**
     * 保存或修改用户的邮件模版
     *
     * @param emailTemplate
     */
    @Transactional(readOnly = false)
    public void saveEmailTemplate(CdsEmailTemplate emailTemplate) {
        emailTemplate.setOwner(UserUtils.getUser());
        cdsEmailTemplateService.save(emailTemplate);
    }

    /**
     * 保存或修改用户的邮件模版
     *
     * @param id
     */
    @Transactional(readOnly = false)
    public void selectEmailTemplate(String id) {
        CdsEmailTemplate cdsEmailTemplate = new CdsEmailTemplate();
        cdsEmailTemplate.setOwner(UserUtils.getUser());
        List<CdsEmailTemplate> emailTemplates = cdsEmailTemplateService.findList(cdsEmailTemplate);
        for (CdsEmailTemplate emailTemplate : emailTemplates) {
            if (emailTemplate.getId().equals(id)) {
                emailTemplate.setIsEnable(1);
                cdsEmailTemplateService.save(emailTemplate);
            } else if (emailTemplate.getIsEnable() == 1) {
                emailTemplate.setIsEnable(0);
                cdsEmailTemplateService.save(emailTemplate);
            }
        }
    }

    /**
     * 删除用户的邮件模版
     *
     * @param id
     */
    @Transactional(readOnly = false)
    public void deleteEmailTemplate(String id) {
        CdsEmailTemplate emailTemplate = cdsEmailTemplateService.get(id);
        cdsEmailTemplateService.delete(emailTemplate);
    }

    /**
     * 获取用户的所有搜索关键词、选择关键词
     *
     * @return
     */
    @Transactional(readOnly = false)
    public List<CdsKeyword> findUserKeywords() {
        List<CdsKeyword> keywordSeekers = cdsApiMapper.findKeywordByUser(UserUtils.getUser());

        // 清理null数据
        List<CdsKeyword> keywordSeekers2 = Lists.newArrayList();
        for (CdsKeyword keywordSeeker : keywordSeekers) {
            if (null != keywordSeeker) keywordSeekers2.add(keywordSeeker);
        }

        CdsCrontabSetting crontabSetting = loadCrontabSetting();
        for (CdsKeyword keyword : keywordSeekers2) {
            CdsrCrontabKeywordLeadIn crontabKeywordLeadIn = new CdsrCrontabKeywordLeadIn();
            crontabKeywordLeadIn.setCrontabSetting(crontabSetting);
//            CdsrKeywordSeeker cdsrKeywordSeeker = cdsrKeywordSeekerService.get(keyword.getKeywordSeekerId());
            CdsrKeywordSeeker cdsrKeywordSeeker = new CdsrKeywordSeeker();
            cdsrKeywordSeeker.setId(keyword.getKeywordSeekerId());
            crontabKeywordLeadIn.setKeywordSeeker(cdsrKeywordSeeker);

            //            组合用户的关键词和定时设置参数,查询后，取消关联
            List<CdsrCrontabKeywordLeadIn> crontabKeywordLeadIns = cdsrCrontabKeywordLeadInService.findList(crontabKeywordLeadIn);
            boolean b = null != crontabKeywordLeadIns && crontabKeywordLeadIns.size() > 0;
            keyword.setSelected(b);

//            清理不需要的数据
            keyword.setCreateBy(null);
            keyword.setCreateDate(null);
            keyword.setUpdateBy(null);
            keyword.setUpdateDate(null);
        }

        return keywordSeekers;
    }

    /**
     * 根据关键词ID获取用户的搜索关键词和email数据
     *
     * @param keywordId
     * @return
     */
    public List<CdsCrawlHostAndEmail> findHostAndUrlEmail(String keywordId) {
//        Object o = CacheUtils.get(keywordId);
//        if (null != o) return (List<CdsCrawlHostAndEmail>) o;

//        查询 cdsr_crawl_keyword cds_crawl_host cds_crawl_url cds_crawl_email 表
        List<CdsCrawlHostAndEmail> crawlHostAndEmails = cdsApiMapper.findHostAndUrlEmail(keywordId);
        for (CdsCrawlHostAndEmail crawlHostAndEmail : crawlHostAndEmails) {
            CdsCrawlHost crawlHost = new CdsCrawlHost();
            crawlHost.setHost(crawlHostAndEmail.getHost());
            List<CdsCrawlHost> crawlHosts = cdsCrawlHostService.findList(crawlHost);
            if (null != crawlHosts && crawlHosts.size() > 0) {
                CdsCrawlEmail crawlEmail = new CdsCrawlEmail();
                crawlEmail.setCrawlHost(crawlHosts.get(0));
                List<CdsCrawlEmail> crawlEmails = cdsCrawlEmailService.findList(crawlEmail);
                for (CdsCrawlEmail email : crawlEmails) {
                    email.setCreateBy(null);
                    email.setUpdateBy(null);
                    email.setCreateDate(null);
                    email.setUpdateDate(null);
                    email.setCrawlHost(null);
                }
                crawlHostAndEmail.setEmails(crawlEmails);
            }
        }

//        CacheUtils.put(keywordId, crawlHostAndEmails);
        return crawlHostAndEmails;
    }


    /**
     * 用户选择搜索关键词
     *
     * @param keywordId
     */
    @Transactional(readOnly = false)
    public void selectedKeyword(String keywordId) {
        User user = UserUtils.getUser();

        CdsrKeywordSeeker keywordSeeker = new CdsrKeywordSeeker();
        CdsKeyword keyword = new CdsKeyword();
        keyword.setId(keywordId);
        keywordSeeker.setKeyword(keyword);
        keywordSeeker.setOwner(user);

        CdsCrontabSetting crontabSetting = loadCrontabSetting();
        List<CdsrKeywordSeeker> keywordSeekers = cdsrKeywordSeekerService.findList(keywordSeeker);
        if (null != keywordSeekers && keywordSeekers.size() < 1) { // 找到关键词与用户关联记录
            cdsrKeywordSeekerService.save(keywordSeeker);
        } else {
            keywordSeeker = keywordSeekers.get(0);
        }
        CdsrCrontabKeywordLeadIn crontabKeywordLeadIn = new CdsrCrontabKeywordLeadIn();
        crontabKeywordLeadIn.setCrontabSetting(crontabSetting);
        crontabKeywordLeadIn.setKeywordSeeker(keywordSeeker);
//            组合用户的关键词和定时设置参数,查询后，取消关联
        List<CdsrCrontabKeywordLeadIn> crontabKeywordLeadIns = cdsrCrontabKeywordLeadInService.findList(crontabKeywordLeadIn);
        if (null == crontabKeywordLeadIns || crontabKeywordLeadIns.size() < 1) {
            cdsrCrontabKeywordLeadInService.save(crontabKeywordLeadIn);
        }
    }

    /**
     * 用户取消搜索关键词的选中
     *
     * @param keywordId
     */
    @Transactional(readOnly = false)
    public void unSelectedKeyword(String keywordId) {
        User user = UserUtils.getUser();

        CdsrKeywordSeeker keywordSeeker = new CdsrKeywordSeeker();
        CdsKeyword keyword = new CdsKeyword();
        keyword.setId(keywordId);
        keywordSeeker.setKeyword(keyword);
        keywordSeeker.setOwner(user);

        CdsCrontabSetting crontabSetting = loadCrontabSetting();
        List<CdsrKeywordSeeker> keywordSeekers = cdsrKeywordSeekerService.findList(keywordSeeker);
        if (null != keywordSeekers && keywordSeekers.size() > 0) { // 找到关键词与用户关联记录
            CdsrKeywordSeeker cdsrKeywordSeeker = keywordSeekers.get(0);

            CdsrCrontabKeywordLeadIn crontabKeywordLeadIn = new CdsrCrontabKeywordLeadIn();
            crontabKeywordLeadIn.setCrontabSetting(crontabSetting);
            crontabKeywordLeadIn.setKeywordSeeker(cdsrKeywordSeeker);
//            组合用户的关键词和定时设置参数,查询后，取消关联
            List<CdsrCrontabKeywordLeadIn> crontabKeywordLeadIns = cdsrCrontabKeywordLeadInService.findList(crontabKeywordLeadIn);
            for (CdsrCrontabKeywordLeadIn keywordLeadIn : crontabKeywordLeadIns) {
                cdsrCrontabKeywordLeadInService.delete(keywordLeadIn);
            }
        }
    }

    /**
     * 用户删除搜索关键词和数据
     *
     * @param keywordId
     */
    @Transactional(readOnly = false)
    public void deleteKeyword(String keywordId) {
        String userId = UserUtils.getUser().getId();

        CdsrKeywordSeeker keywordSeeker = new CdsrKeywordSeeker();
        CdsKeyword keyword = new CdsKeyword();
        keyword.setId(keywordId);
        keywordSeeker.setKeyword(keyword);

        List<CdsrKeywordSeeker> keywordSeekers = cdsrKeywordSeekerService.findList(keywordSeeker);
        if (null != keywordSeekers) {

            int size = keywordSeekers.size();
            if (size > 1) { // 有其他人也使用了这个关键词，仅仅删除自己的关联表数据

                for (CdsrKeywordSeeker seeker : keywordSeekers)
                    if (userId.equalsIgnoreCase(seeker.getOwner().getId()))
                        cdsrKeywordSeekerService.delete(seeker);

            } else if (size == 1) { // 否则，直接清除所有相关表的相关数据
                // 删除用户关联表数据
                cdsrKeywordSeekerService.delete(keywordSeekers.get(0));

                // 删除关键词表数据
                cdsKeywordService.delete(keyword);

                // 删除url和关键词表的数据
                CdsrCrawlKeyword crawlKeyword = new CdsrCrawlKeyword();
                crawlKeyword.setKeyword(keyword);
                cdsrCrawlKeywordService.delete(crawlKeyword);
            }
        }
    }


    /**
     * 获取导入的的所有分类、选择的分类
     *
     * @return
     */
    public List<Map<String, Object>> findUserLeadInCategorys() {
//        查询所有分类
        List<String> allLeadInCategories = cdsApiMapper.findAllLeadInCategories();
//        查询某个用户关联的分类
        List<String> userLeadInCategories = cdsApiMapper.findUserLeadInCategories(UserUtils.getUser());
        List<Map<String, Object>> list = Lists.newArrayList();
        for (String leadInCategory : allLeadInCategories) {
            Map<String, Object> map = new HashMap<>();
            map.put("keyword", leadInCategory);
            map.put("id", leadInCategory);
            if (userLeadInCategories.contains(leadInCategory)) {// 选中了
                map.put("selected", true);
            } else {// 没选中
                map.put("selected", false);
            }
            list.add(map);
        }
        return list;
    }


    /**
     * 根据分类返回邮箱列表
     *
     * @param categoryName
     * @return
     */
    public List<CdsLeadIn> findLeadInEmails(String categoryName) {
        CdsLeadIn leadIn = new CdsLeadIn();
        leadIn.setCategory(categoryName);
        return cdsLeadInService.findList(leadIn);
    }


    /**
     * 用户选择分类
     *
     * @param categoryName
     */
    @Transactional(readOnly = false)
    public void selectedLeadInCategory(String categoryName) {
        User user = UserUtils.getUser();
        CdsCrontabSetting crontabSetting = new CdsCrontabSetting();
        crontabSetting.setOwner(user);
        List<CdsCrontabSetting> crontabSettings = cdsCrontabSettingService.findList(crontabSetting);
        if (null != crontabSettings && crontabSettings.size() > 0) {
            CdsCrontabSetting crontabSetting1 = crontabSettings.get(0);

            CdsLeadIn leadIn = new CdsLeadIn();
            leadIn.setCategory(categoryName);

            CdsrCrontabKeywordLeadIn crontabKeywordLeadIn = new CdsrCrontabKeywordLeadIn();
            crontabKeywordLeadIn.setCrontabSetting(crontabSetting1);
            crontabKeywordLeadIn.setLeadIn(leadIn);

//            组合用户的关键词和定时设置参数,查询后，取消关联
            List<CdsrCrontabKeywordLeadIn> crontabKeywordLeadIns = cdsrCrontabKeywordLeadInService.findList(crontabKeywordLeadIn);
            if (null == crontabKeywordLeadIns || crontabKeywordLeadIns.size() < 1) {
                cdsrCrontabKeywordLeadInService.save(crontabKeywordLeadIn);
            }
        }
    }


    /**
     * 用户取消分类的选中
     *
     * @param categoryName
     */
    @Transactional(readOnly = false)
    public void unSelectedLeadInCategory(String categoryName) {
        User user = UserUtils.getUser();
        CdsCrontabSetting crontabSetting = new CdsCrontabSetting();
        crontabSetting.setOwner(user);
        List<CdsCrontabSetting> crontabSettings = cdsCrontabSettingService.findList(crontabSetting);
        if (null != crontabSettings && crontabSettings.size() > 0) {
            CdsCrontabSetting crontabSetting1 = crontabSettings.get(0);

            CdsLeadIn leadIn = new CdsLeadIn();
            leadIn.setCategory(categoryName);

            CdsrCrontabKeywordLeadIn crontabKeywordLeadIn = new CdsrCrontabKeywordLeadIn();
            crontabKeywordLeadIn.setCrontabSetting(crontabSetting1);
            crontabKeywordLeadIn.setLeadIn(leadIn);

//            组合用户的关键词和定时设置参数,查询后，取消关联
            List<CdsrCrontabKeywordLeadIn> crontabKeywordLeadIns = cdsrCrontabKeywordLeadInService.findList(crontabKeywordLeadIn);
            for (CdsrCrontabKeywordLeadIn crontabKeywordLeadIn1 : crontabKeywordLeadIns) {
                cdsrCrontabKeywordLeadInService.delete(crontabKeywordLeadIn1);
            }
        }
    }


}