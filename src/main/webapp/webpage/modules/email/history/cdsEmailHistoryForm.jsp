<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>发件历史管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {

	        $('#sendDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/email/history/cdsEmailHistory/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="cdsEmailHistory" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>发件人：</label></td>
					<td class="width-35">
						<sys:userselect id="sendBy" name="sendBy.id" value="${cdsEmailHistory.sendBy.id}" labelName="sendBy.name" labelValue="${cdsEmailHistory.sendBy.name}"
							    cssClass="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>发送时间：</label></td>
					<td class="width-35">
						<div class='input-group form_datetime' id='sendDate'>
							<input type='text'  name="sendDate" class="form-control required"  value="<fmt:formatDate value="${cdsEmailHistory.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>发件箱外键：</label></td>
					<td class="width-35">
						<form:input path="sendEmail" htmlEscape="false"    class="form-control required email"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>收件箱外键：</label></td>
					<td class="width-35">
						<form:input path="inboxEmail" htmlEscape="false"    class="form-control required email"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>发送状态（0：失败；1：成功）：</label></td>
					<td class="width-35">
						<form:select path="emailSendStatus" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('email_send_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:input path="remarks" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>