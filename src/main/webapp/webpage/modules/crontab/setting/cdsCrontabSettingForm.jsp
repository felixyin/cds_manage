<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>邮箱定时任务管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {

	        $('#exeTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/crontab/setting/cdsCrontabSetting/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="cdsCrontabSetting" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">所属用户：</label></td>
					<td class="width-35">
						<sys:userselect id="owner" name="owner.id" value="${cdsCrontabSetting.owner.id}" labelName="owner.name" labelValue="${cdsCrontabSetting.owner.name}"
							    cssClass="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">自定义名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">执行时间：</label></td>
					<td class="width-35">
						<div class='input-group form_datetime' id='exeTime'>
							<input type='text'  name="exeTime" class="form-control "  value="<fmt:formatDate value="${cdsCrontabSetting.exeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</td>
					<td class="width-15 active"><label class="pull-right">分(0-59)：</label></td>
					<td class="width-35">
						<form:input path="minute" htmlEscape="false"   max="59"  min="0" class="form-control  isIntGteZero"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">时(0-23)：</label></td>
					<td class="width-35">
						<form:input path="hour" htmlEscape="false"   max="23"  min="0" class="form-control  isIntGteZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right">周几(1-7)：</label></td>
					<td class="width-35">
						<form:input path="dayOfWeek" htmlEscape="false"   max="7"  min="1" class="form-control  isIntGtZero"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">日(1-31)：</label></td>
					<td class="width-35">
						<form:input path="date" htmlEscape="false"   max="31"  min="1" class="form-control  isIntGtZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right">月(1-12)：</label></td>
					<td class="width-35">
						<form:input path="month" htmlEscape="false"   max="12"  min="1" class="form-control  isIntGtZero"/>
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