<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>爬取地址管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {

	        $('#siteLastDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/crawl/url/cdsCrawlUrl/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="cdsCrawlUrl" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">域名外键：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/crawl/host/cdsCrawlHost/data" id="crawlHost" name="crawlHost.id" value="${cdsCrawlUrl.crawlHost.id}" labelName="crawlHost.host" labelValue="${cdsCrawlUrl.crawlHost.host}"
							 title="选择域名外键" cssClass="form-control " fieldLabels="域名|爬取次数" fieldKeys="host|crawl_times" searchLabels="域名|爬取次数" searchKeys="host|crawl_times" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">访问地址：</label></td>
					<td class="width-35">
						<form:input path="url" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">标题：</label></td>
					<td class="width-35">
						<form:input path="title" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">描述：</label></td>
					<td class="width-35">
						<form:input path="description" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">统计次数：</label></td>
					<td class="width-35">
						<form:input path="crawlTimes" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">耗时（秒）：</label></td>
					<td class="width-35">
						<form:input path="elapsedTime" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">站点更新日期：</label></td>
					<td class="width-35">
						<div class='input-group form_datetime' id='siteLastDate'>
							<input type='text'  name="siteLastDate" class="form-control "  value="<fmt:formatDate value="${cdsCrawlUrl.siteLastDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</td>
					<td class="width-15 active"><label class="pull-right">是否有效：</label></td>
					<td class="width-35">
						<form:select path="isFindEmail" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('is_find_email')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
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