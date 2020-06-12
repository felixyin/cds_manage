<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>爬取的邮箱管理</title>
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
                jp.post("${ctx}/crawl/email/cdsCrawlEmail/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="cdsCrawlEmail" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>收件箱：</label></td>
					<td class="width-35">
						<form:input path="email" htmlEscape="false"    class="form-control required email"/>
					</td>
					<td class="width-15 active"><label class="pull-right">抓取域名外键：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/crawl/host/cdsCrawlHost/data" id="crawlHost" name="crawlHost.id" value="${cdsCrawlEmail.crawlHost.id}" labelName="crawlHost.host" labelValue="${cdsCrawlEmail.crawlHost.host}"
							 title="选择抓取域名外键" cssClass="form-control " fieldLabels="域名" fieldKeys="host" searchLabels="域名" searchKeys="host" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">发送计数：</label></td>
					<td class="width-35">
						<form:input path="sendTimes" htmlEscape="false"    class="form-control  isIntGteZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否合法邮箱：</label></td>
					<td class="width-35">
						<form:select path="isValidEmail" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('is_valid_email')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">发送状态：</label></td>
					<td class="width-35">
						<form:select path="sendStatus" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('email_send_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:input path="remarks" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>