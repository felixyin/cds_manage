<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>关键词与爬取对应关系管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {

		});
		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/crawl/rkeyword/cdsrCrawlKeyword/save",$('#inputForm').serialize(),function(data){
                    if(data.success){
                        jp.getParent().refresh();
                        var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(dialogIndex);
                        jp.success(data.msg)

                    }else{
                        jp.error(data.msg);
                    }
                })
			}

        }
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="cdsrCrawlKeyword" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">抓取URL外建：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/crawl/url/cdsCrawlUrl/data" id="crawlUrl" name="crawlUrl.id" value="${cdsrCrawlKeyword.crawlUrl.id}" labelName="crawlUrl.title" labelValue="${cdsrCrawlKeyword.crawlUrl.title}"
							 title="选择抓取URL外建" cssClass="form-control " fieldLabels="标题|地址|描述" fieldKeys="title|url|description" searchLabels="标题|地址|描述" searchKeys="title|url|description" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">关键词外键：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/keyword/cdsKeyword/data" id="keyword" name="keyword.id" value="${cdsrCrawlKeyword.keyword.id}" labelName="keyword.keyword" labelValue="${cdsrCrawlKeyword.keyword.keyword}"
							 title="选择关键词外键" cssClass="form-control " fieldLabels="关键词|有效域名数量|有效邮箱数量" fieldKeys="keyword|host_count|email_count" searchLabels="关键词|有效域名数量|有效邮箱数量" searchKeys="keyword|host_count|email_count" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:input path="remarks" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>