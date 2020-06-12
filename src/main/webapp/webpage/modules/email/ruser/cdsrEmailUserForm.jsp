<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>我的邮箱管理</title>
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
                jp.post("${ctx}/email/ruser/cdsrEmailUser/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="cdsrEmailUser" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>发件邮箱：</label></td>
					<td class="width-35">
						<form:input path="sendEmail" htmlEscape="false"    class="form-control required email"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>邮箱密码：</label></td>
					<td class="width-35">
						<form:input path="password" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>邮箱设置：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/email/category/cdsEmailCategory/data" id="emailCategory" name="emailCategory.id" value="${cdsrEmailUser.emailCategory.id}" labelName="emailCategory.categoryName" labelValue="${cdsrEmailUser.emailCategory.categoryName}"
							 title="选择邮箱设置" cssClass="form-control required" fieldLabels="分类名称|服务器地址|端口" fieldKeys="categoryName|host|port" searchLabels="分类名称|服务器地址|端口" searchKeys="categoryName|host|port" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">已发次数：</label></td>
					<td class="width-35">
						<form:input path="sendTimes" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
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