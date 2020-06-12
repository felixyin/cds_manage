<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>关键词与爬取对应关系管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="cdsrCrawlKeywordList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">关键词与爬取对应关系列表</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="cdsrCrawlKeyword" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="抓取URL外建：">抓取URL外建：</label>
				<sys:gridselect url="${ctx}/crawl/url/cdsCrawlUrl/data" id="crawlUrl" name="crawlUrl.id" value="${cdsrCrawlKeyword.crawlUrl.id}" labelName="crawlUrl.title" labelValue="${cdsrCrawlKeyword.crawlUrl.title}"
					title="选择抓取URL外建" cssClass="form-control " fieldLabels="标题|地址|描述" fieldKeys="title|url|description" searchLabels="标题|地址|描述" searchKeys="title|url|description" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="关键词外键：">关键词外键：</label>
				<sys:gridselect url="${ctx}/keyword/cdsKeyword/data" id="keyword" name="keyword.id" value="${cdsrCrawlKeyword.keyword.id}" labelName="keyword.keyword" labelValue="${cdsrCrawlKeyword.keyword.keyword}"
					title="选择关键词外键" cssClass="form-control " fieldLabels="关键词|有效域名数量|有效邮箱数量" fieldKeys="keyword|host_count|email_count" searchLabels="关键词|有效域名数量|有效邮箱数量" searchKeys="keyword|host_count|email_count" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="逻辑删除标记：">逻辑删除标记：</label>
				<form:select path="delFlag"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<shiro:hasPermission name="crawl:rkeyword:cdsrCrawlKeyword:add">
				<button id="add" class="btn btn-primary" onclick="add()">
					<i class="glyphicon glyphicon-plus"></i> 新建
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="crawl:rkeyword:cdsrCrawlKeyword:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="crawl:rkeyword:cdsrCrawlKeyword:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="crawl:rkeyword:cdsrCrawlKeyword:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="crawl:rkeyword:cdsrCrawlKeyword:export">
	        		<button id="export" class="btn btn-warning">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			 </shiro:hasPermission>
	                 <shiro:hasPermission name="crawl:rkeyword:cdsrCrawlKeyword:view">
				<button id="view" class="btn btn-default" disabled onclick="view()">
					<i class="fa fa-search-plus"></i> 查看
				</button>
			</shiro:hasPermission>
		    </div>
		
	<!-- 表格 -->
	<table id="cdsrCrawlKeywordTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="crawl:rkeyword:cdsrCrawlKeyword:view">
        <li data-item="view"><a>查看</a></li>
        </shiro:hasPermission>
    	<shiro:hasPermission name="crawl:rkeyword:cdsrCrawlKeyword:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="crawl:rkeyword:cdsrCrawlKeyword:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>