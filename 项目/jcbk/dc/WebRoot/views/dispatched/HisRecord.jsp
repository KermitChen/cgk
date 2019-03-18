<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+path+"/";
%>
<html lang="en">
	<head>
		
	</head>
	<body>
	    <fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">审批信息</legend>
				<div class="pg_result">
					<table>
						<thead>
							<tr>
								<td width="20%" align="center">时间</td>
								<td width="20%" align="center">审批人</td>
								<td width="20%" align="center">操作结果</td>
								<td width="30%" align="center">审批意见</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="c" items="${commentList }" varStatus="status">
								<tr>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${c.czsj}" /></td>
									<td>${c.czr}(${c.czrjs})</td>
									<td>${c.czjgmc }</td>
									<td>${c.ms }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</fieldset>
	</body>
</html>
