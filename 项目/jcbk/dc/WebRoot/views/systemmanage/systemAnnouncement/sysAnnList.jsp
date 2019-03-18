<%@ page language="java"
	import="java.util.*,com.dyst.utils.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path;
	String filePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + "/" + Config.getInstance().getSysAnnPath() + "/";
	
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
<style>
	#table1 tbody td{
		width: auto;
	}
</style>
	<script type="text/javascript" src="<%=basePath%>/common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/common/js/layer/layer.js"></script>
	<table id="table1">
		<thead>
			<tr>
				<td><input type="checkbox" id="selectAll" onclick="doSelectAll();"/></td>
				<td>序号</td>
				<td>公告标题</td>
				<td>公告类型</td>
				<td>公告概要</td>
				<td>发布人用户名</td>
				<td>发布人姓名</td>
				<td>发布时间</td>
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
				<td>${data.fileName }</td>
				<td>
					<c:forEach items="${annTypeList }" var="a">
						<c:if test="${a.typeSerialNo eq data.annType }">
							${a.typeDesc }
						</c:if>
					</c:forEach>
				</td>
				<td>${data.outline }</td>
				<td>${data.buildPno }</td>
				<td>${data.buildName }</td>
				<td><fmt:formatDate value="${data.buildTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
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
