<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>爬取参数设置管理</title>
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
                jp.post("${ctx}/crawl/setting/cdsCrawlSetting/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="cdsCrawlSetting" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>搜索人：</label></td>
					<td class="width-35">
						<sys:userselect id="owner" name="owner.id" value="${cdsCrawlSetting.owner.id}" labelName="owner.name" labelValue="${cdsCrawlSetting.owner.name}"
							    cssClass="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">站点队列长度：</label></td>
					<td class="width-35">
						<form:input path="siteQueue" htmlEscape="false"   max="100"  min="1" class="form-control  isIntGteZero"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">站点入队间隔时间：</label></td>
					<td class="width-35">
						<form:input path="siteSleep" htmlEscape="false"   max="10"  min="1" class="form-control  isIntGteZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right">站点挖掘深度：</label></td>
					<td class="width-35">
						<form:input path="diggingDepth" htmlEscape="false"   max="5"  min="1" class="form-control  isIntGteZero"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">链接线程并发数：</label></td>
					<td class="width-35">
						<form:input path="threadCount" htmlEscape="false"   max="30"  min="1" class="form-control  isIntGteZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right">链接线程间隔时间：</label></td>
					<td class="width-35">
						<form:input path="threadSleep" htmlEscape="false"   max="1000"  min="1" class="form-control  isIntGteZero"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">链接抓取超时时间：</label></td>
					<td class="width-35">
						<form:input path="fetchTimeout" htmlEscape="false"   max="10"  min="1" class="form-control  isIntGteZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否去除重复：</label></td>
					<td class="width-35">
						<form:select path="isNeedDistinct" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('is_need_distinct')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">是否验证邮箱：</label></td>
					<td class="width-35">
						<form:select path="isVerifyEmail" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('is_verify_email')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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