<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>我的邮件模板管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
	<%@include file="/webpage/include/summernote.jsp" %>
	<script type="text/javascript">

		$(document).ready(function() {

					//富文本初始化
			$('#mainBody').summernote({
				height: 300,
                lang: 'zh-CN',
                callbacks: {
                    onChange: function(contents, $editable) {
                        $("input[name='mainBody']").val($('#mainBody').summernote('code'));//取富文本的值
                    }
                }
            });
		});
		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/email/template/cdsEmailTemplate/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="cdsEmailTemplate" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>所属用户：</label></td>
					<td class="width-35">
						<sys:userselect id="owner" name="owner.id" value="${cdsEmailTemplate.owner.id}" labelName="owner.name" labelValue="${cdsEmailTemplate.owner.name}"
							    cssClass="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>邮件标题：</label></td>
					<td class="width-35">
						<form:input path="subject" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>正文内容：</label></td>
					<td class="width-35">
                        <input type="hidden" name="mainBody" value=" ${cdsEmailTemplate.mainBody}"/>
						<div id="mainBody">
                          ${fns:unescapeHtml(cdsEmailTemplate.mainBody)}
                        </div>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否启用：</label></td>
					<td class="width-35">
						<form:select path="isEnable" class="form-control required">
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