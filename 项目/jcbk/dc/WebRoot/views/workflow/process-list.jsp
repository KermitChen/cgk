<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="en">
<head>
	<title>流程列表</title>
	
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<c:if test="${not empty message}">
	<div class="ui-widget">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
				<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
				<strong>提示：</strong>${message}</p>
			</div>
		</div>
	</c:if>
	<div style="text-align: right;padding: 2px 1em 2px;display:none">
		<div id="message" style="display:inline;background:#d5edf8;color:#205791;border-color:#92cae4;"><b>提示：</b>点击xml或者png链接可以查看具体内容！</div>
		<button onclick="$('#deployFieldset').toggle('normal');">部署流程</button>
		<a id='redeploy' href='redeploy/all.do' style="display:none">重新部署流程</a>
	</div>
	<fieldset id="deployFieldset">
		<legend>部署新流程</legend>
		<div><b>支持文件格式：</b>zip、bar、bpmn、bpmn20.xml</div>
		<form action="deploy.do" method="post" enctype="multipart/form-data">
			<input type="file" name="file" />
			<input type="button" onclick="doSubmit()" value="提交" />
		</form>
	</fieldset>
	<div class="pg_result">
	<table >
		<thead>
			<tr>
				<th>流程定义编号</th>
				<th>部署编号</th>
				<th>名称</th>
				<th>流程编号</th>
				<th>版本号</th>
				<th>源文件</th>
				<th>图片</th>
				<th>部署时间</th>
<!-- 				<th>是否挂起</th> -->
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageResult.items }" var="object">
				<c:set var="process" value="${object[0] }" />
				<c:set var="deployment" value="${object[1] }" />
				<tr>
					<td>${process.id }</td>
					<td>${process.deploymentId }</td>
					<td>${process.name }</td>
					<td>${process.key }</td>
					<td>${process.version }</td>
					<td><a target="_blank" href='resource/read.do?processDefinitionId=${process.id}&resourceType=xml'>${process.resourceName }</a></td>
					<td><a target="_blank" href='resource/read.do?processDefinitionId=${process.id}&resourceType=image'>${process.diagramResourceName }</a></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${deployment.deploymentTime }" /></td>
<!-- 					<td>${process.suspended} | -->
<!-- 						<c:if test="${process.suspended }"> -->
<!-- 							<a href="processdefinition/update/active/${process.id}">激活</a> -->
<!-- 						</c:if> -->
<!-- 						<c:if test="${!process.suspended }"> -->
<!-- 							<a href="processdefinition/update/suspend/${process.id}">挂起</a> -->
<!-- 						</c:if> -->
<!-- 					</td> -->
					<td>
                        <a href="javascript:if(confirm('确认要删除该流程吗？将会同时删除该流程下所有正在审批的任务！'))location='process/delete.do?deploymentId=${process.deploymentId}'">删除</a>
<!--                         <a href='process/convert-to-model/${process.id}'>转换为Model</a> -->
                    </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 	<jsp:include page="/common/pageNavigators.jsp"></jsp:include> -->
	</div>
<!-- 	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/> -->
</body>
<script type="text/javascript">
	$(function(){
		if('${exception}'=="error"){
			alert("上传流程文件错误，请检查后重新上传");
		}else if('${exception}'=="normal"){
			alert("部署成功");
		}
	});
    function doSubmit(){
    	if($("input[name='file']").val()==""){
    		alert("请选择文件");
    		return;
    	}
    	document.forms[0].submit();
    }
    //根据页号查询
	function doGoPage(pageNo) {
		document.getElementById("pageNo").value = pageNo;
		document.forms[0].action = "process-list.do";
		document.forms[0].submit();
	}
</script>
</html>