<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
	<title>布控审批</title>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<link href="<%=basePath%>common/js/activiti/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
</head>

<body>
<jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">布控审批</span>
	</div>
	<div class="content">
		<div class="content_wrap">
	<table width="100%" class="need-border">
		<thead>
			<tr>
				<td width="80" align="center">号牌号码</td>
				<td width="100" align="center">号牌种类</td>
				<td width="100" align="center">布控类别</td>
				<td width="140" align="center">布控申请机关</td>
				<td width="80" align="center">布控申请人</td>
				<td width="100" align="center">当前节点</td>
				<td width="120" align="center">布控申请时间</td>
				<td width="100" align="center">布控性质</td>
				<td width="100" align="center">直接布控</td>
				<td width="80" align="center">操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageResults.items }" var="dis">
				<c:set var="disReport" value="${dis.disReport }" />
				<c:set var="task" value="${dis.task }" />
				<c:set var="pi" value="${dis.processInstance }" />
				<tr id="${dis.bkid }" tid="${task.id }">
					<td>${dis.hphm }</td>
					<td>
						<c:forEach items= "${dicList}" var="dic">
				           <c:if test="${dic.typeCode eq 'HPZL' }">
					          <c:if test="${dic.typeSerialNo eq dis.hpzl }">
					              ${dic.typeDesc }
					          </c:if>
				           </c:if> 
						</c:forEach>
					</td>
					<td>
						<c:forEach items= "${bklb}" var="b">
				            <c:if test="${b.ID eq dis.bklb }">
					            ${b.NAME }
				            </c:if> 
						</c:forEach>
					</td>
					<td>${dis.bkjgmc }</td>
					<td>${dis.bkr }</td>
					<td>
						<a class="trace" href='#' pid="${pi.id }" pdid="${pi.processDefinitionId}" title="点击查看流程图">${task.name }</a>
					</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dis.bksj }" /></td>
					<td>
						<c:forEach items= "${dicList}" var="dic">
				           <c:if test="${dic.typeCode eq 'BKXZ' }">
					          <c:if test="${dic.typeSerialNo eq dis.bkxz }">
					              ${dic.typeDesc }
					          </c:if>
				           </c:if> 
						</c:forEach>
					</td>
					<td>
						<c:if test="${dis.zjbk eq '1' }">
							是
						</c:if>
						<c:if test="${dis.zjbk eq '0' }">
							否
						</c:if>
					</td>
					<td>
						<a href="detailDispatched.do?rowId=${dis.bkid }&taskId=${task.id }&conPath=${conPath }" >详情</a>
						<c:if test="${empty task.assignee && dis.zjbk eq '0'}">
							| <a onclick="block()" href="taskClaim.do?taskId=${task.id}&conPath=${conPath }">签收</a>
						</c:if>
						<c:if test="${not empty task.assignee && dis.ywzt ne '6'}">
							| <a href="loadDispatchedHandle.do?rowId=${dis.bkid }&taskId=${task.id }&conPath=${conPath }" 
							style="color:red" onmouseover="this.style.cssText='color:black;'" onmouseout="this.style.cssText='color:red;'">审批</a>
						</c:if>
						<c:if test="${dis.zjbk eq '1'}">
							| <a href="loadDispatchedHandle.do?rowId=${dis.bkid }&taskId=${task.id }&conPath=${conPath }" 
							style="color:red" onmouseover="this.style.cssText='color:black;'" onmouseout="this.style.cssText='color:red;'">报备审批</a>
						</c:if>
						<c:if test="${not empty task.assignee && dis.ywzt eq '6'}">
							| <a href="editDispatched.do?rowId=${dis.bkid }&taskId=${task.id }&conPath=${conPath }" >调整申请</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="/common/pageNavigators.jsp"></jsp:include>
	</div>
	</div>
	<jsp:include page="/common/Foot.jsp" />
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/dispatched-todo.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script>
		$(function (){
			if('${success}'=="success"){
				layer.msg("签收成功");
			}else if('${success}'=="error"){
				alert("该任务已被签收，请选择其他任务");
			}
		});
		function block() {
			$.blockUI({
			   message: '<h2><img src="<%=basePath%>common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
			});
		}
	</script>
</html>
