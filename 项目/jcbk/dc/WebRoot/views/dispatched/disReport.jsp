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
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">直接布控报备记录</legend>
				<div class="pg_result">
					<table>
						<thead>
							<tr>
								<td width="30%" align="center">报备人</td>
								<td width="40%" align="center">部门</td>
								<td width="30%" align="center">电话</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="d" items="${disReportList }" varStatus="status">
								<tr>
									<td>${d.bbrmc}(${d.bbrjsmc })</td>
									<td>${d.bbrbmmc }</td>
									<td>${d.bbrdh }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</fieldset>
	</body>
</html>
