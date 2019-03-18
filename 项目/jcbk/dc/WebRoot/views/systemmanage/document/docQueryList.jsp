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
				<td>序号</td>
				<td>文档名称</td>
				<td>备注</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${pageResult.items }" varStatus="status">
			<tr ondblclick="javascript:doDetail(${data.id})">
				<td>${status.index+1}</td>
				<td style="width: 200px;">${data.fileName }</td>
				<td>${data.remark }</td>
				<td width="80px">
					<a href="<%=filePath %>${data.fileUrl}">下载</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>            
	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>