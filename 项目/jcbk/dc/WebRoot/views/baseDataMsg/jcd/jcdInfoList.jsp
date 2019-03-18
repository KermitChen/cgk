<%@ page language="java"
	import="java.util.*,com.dyst.base.utils.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	
	//查询结果
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
				<td align="center">序号</td>
			    <td align="center">监测点ID</td>
			    <td align="left">监测点名称</td>
			    <td align="center">监测点类型</td>
			    <td align="center">方向</td>
			    <td align="center">隶属城区</td>
			    <td align="center">隶属道路</td>
			    <td align="center">经度</td>
			    <td align="center">纬度</td>
			    <td align="center">启用标志</td>
			    <td align="center">操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${pageResult.items }" varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${data.id }</td>
			    <td>${data.jcdmc }</td>
			    <td>
			    	<c:forEach items= "${dicList}" var="dic">
						<c:if test="${dic.typeCode eq 'JCDLX' }">
							<c:if test="${dic.typeSerialNo eq data.jcdxz }">
						    	${dic.typeDesc }
						    </c:if>
					    </c:if> 
					</c:forEach>
			    </td>
			    <td>
			    	<c:forEach items= "${dicList}" var="dic">
						<c:if test="${dic.typeCode eq '0039' }">
							<c:if test="${dic.typeSerialNo eq data.xsfx }">
						    	${dic.typeDesc }
						    </c:if>
					    </c:if> 
					</c:forEach>
			    </td>
			    <td>
			    	<c:forEach items= "${areas}" var="area">
						<c:if test="${area.areano eq data.cqid }">
						    ${area.areaname }
						</c:if>
					</c:forEach>
			    </td>
			    <td>
			    	<c:forEach items= "${roads}" var="road">
						<c:if test="${road.roadNo eq data.dlid }">
						    ${road.roadName }
						</c:if>
					</c:forEach>
			    </td>
			    <td>${data.jd }</td>
			    <td>${data.wd }</td>
			    <td>
			    	<c:forEach items= "${dicList}" var="dic">
						<c:if test="${dic.typeCode eq 'JCDQYBZ' }">
							<c:if test="${dic.typeSerialNo eq data.qybz and data.qybz eq '1'}">
						    	<font color="green">${dic.typeDesc }</font>
						    </c:if>
						    <c:if test="${dic.typeSerialNo eq data.qybz and data.qybz eq '0'}">
						    	${dic.typeDesc }
						    </c:if>
					    </c:if> 
					</c:forEach>
			    </td>
			    <td>
			    	<a onclick="qyty('${data.id }', '${data.qybz }')" style="font-size: 15px;">
						<c:if test="${data.qybz eq '0' }">启用</c:if>
						<c:if test="${data.qybz eq '1' }">停用</c:if>
					</a>
			    </td>
			</tr>
			</c:forEach>
		</tbody>
	</table>            
	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>