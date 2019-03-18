<%@ page language="java"
	import="java.util.*,com.dyst.utils.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path;
	String filePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + "/" + Config.getInstance().getHelpDocPath() + "/";
	
	PageResult pageResult = (PageResult)request.getAttribute("pageResult");
	if(pageResult == null){
%>
		<SCRIPT type="text/javascript">
			window.location = '<%=basePath%>/common/errorPage/error500.jsp';
		</SCRIPT>
<%
	} 
	//else if(pageResult != null && pageResult.getItems().size() <= 0){
	//	<SCRIPT type="text/javascript">
	//		alert("没有找到符合条件的数据！");
	//	</SCRIPT>
	//}
%>
	<table>
		<thead>
			<tr>
				<td><input type="checkbox" id="selectAll" onclick="doSelectAll();"/></td>
				<td>序号</td>
				<td>文档名称</td>
				<td>上传人用户名</td>
				<td>上传人姓名</td>
				<td>上传时间</td>
				<td>备注</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${pageResult.items }" varStatus="status">
			<tr ondblclick="javascript:doDetail(${data.id})">
				<td width="30px" align="center">
					<input type="checkbox" id="ids" name="ids" value="${data.id }" />
				</td>
				<td>${status.index+1}</td>
				<td style="width: 150px;">${data.fileName }</td>
				<td>${data.buildPno }</td>
				<td>${data.buildName }</td>
				<td><fmt:formatDate value="${data.buildTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td style="width: 200px;">${data.remark }</td>
				<td width="200px">
					<a href="javascript:doDetail(${data.id})" >详情</a>
					|&nbsp;<a href="<%=filePath %>${data.fileUrl}">下载</a>
					|&nbsp;<a href="javascript:doEdit(${data.id})">编辑</a>
					|&nbsp;<a href="javascript:doDelete(${data.id})">删除</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>            
	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>