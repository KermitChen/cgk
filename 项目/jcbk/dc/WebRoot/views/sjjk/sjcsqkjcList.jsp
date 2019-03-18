<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	
	String result = (String)request.getAttribute("result");
	if(result == null || "".equals(result.trim()) || "0".equals(result.trim())){
%>
		<SCRIPT type="text/javascript">
			alert("查询失败！");
		</SCRIPT>
<%
	}
%>
	<table id="table1">
		<thead>
			<tr>
				<td>序号</td>
				<td>监测点编号</td>
				<td>监测点名称</td>
				<td>及时上传量（条）</td>
				<td>超时上传量（条）</td>
				<td>总量（条）</td>
				<td>及时上传率</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${sjcsqkjcList }" varStatus="status">
				<tr id="tr_result">
					<td>${data.sn }</td>
					<td>${data.jcdid }</td>
					<td>${data.jcdmc }</td>
					<td>${data.jss }</td>
					<td>${data.css }</td>
					<td>${data.totalRst }</td>
					<td>${data.jsl }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>