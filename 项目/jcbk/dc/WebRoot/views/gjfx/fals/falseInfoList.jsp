<%@ page language="java"
	import="java.util.*,com.dyst.utils.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	
	PageResult pageResult = (PageResult)request.getAttribute("pageResult");
	if(pageResult == null){
%>
		<SCRIPT type="text/javascript">
			window.location = '<%=basePath%>/common/errorPage/error500.jsp';
		</SCRIPT>
<%
	} 
%>
	<table>
		<thead>
			<tr>
				<td>序号</td>
				<td>车牌号</td>
				<td>车牌颜色</td>
				<td>录入时间</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${pageResult.items }" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${data.jcphm }</td>
				<td>${cplxMap[data.jcplx] }</td>
				<td>${fn:substringBefore(data.lrsj,".") }</td>
				<td width="180px">
	              	<a href="javascript:showDetail('${data.id}')" >详情</a>	              	
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>            
	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>