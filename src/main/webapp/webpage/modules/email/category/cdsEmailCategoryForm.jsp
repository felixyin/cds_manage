<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>邮箱分类管理</title>
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
                jp.post("${ctx}/email/category/cdsEmailCategory/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="cdsEmailCategory" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">所有者：</label></td>
					<td class="width-35">
						<sys:userselect id="owner" name="owner.id" value="${cdsEmailCategory.owner.id}" labelName="owner.name" labelValue="${cdsEmailCategory.owner.name}"
							    cssClass="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>分类名称：</label></td>
					<td class="width-35">
						<form:select path="categoryName" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('email_category_name')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">服务器：</label></td>
					<td class="width-35">
						<form:input path="server" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>服务器地址：</label></td>
					<td class="width-35">
						<form:input path="host" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>端口：</label></td>
					<td class="width-35">
						<form:input path="port" htmlEscape="false" maxlength="6"  minlength="2"   class="form-control required isIntGtZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否启用：</label></td>
					<td class="width-35">
						<form:select path="isEnable" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('is_enable')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
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