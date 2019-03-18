<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<table id="table1">
		<thead>
			<tr>
				<td align="center">序号</td>
	            <td align="center">监测点编号</td>
	            <td align="center">监测点名称</td>
<!--	            <td align="center">监测点类型</td>-->
<!--	            <td align="center">方向</td>-->
<!--	            <td align="center">所属城区</td>-->
<!--	            <td align="center">所属道路</td>-->
<!--	            <td align="center">经度</td>-->
<!--	            <td align="center">纬度</td>-->
	            <td align="center">在线状态</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${pageResult.items }" varStatus="status">
				<tr id="tr_result">
					<td>${status.index+1}</td>
					<td>${data[0].id }</td>
	                <td>${data[0].jcdmc }</td>
<!--	                <td>-->
<!--		                <c:forEach items= "${dicList }" var="dic">-->
<!--							<c:if test="${dic.typeCode eq 'JCDLX' }">-->
<!--								<c:if test="${dic.typeSerialNo eq data[0].jcdxz }">-->
<!--							    	${dic.typeDesc }-->
<!--							    </c:if>-->
<!--						    </c:if> -->
<!--						</c:forEach>-->
<!--	                </td>-->
<!--	                <td>-->
<!--	                	<c:forEach items= "${dicList }" var="dic">-->
<!--							<c:if test="${dic.typeCode eq '0039' }">-->
<!--								<c:if test="${dic.typeSerialNo eq data[0].xsfx }">-->
<!--							    	${dic.typeDesc }-->
<!--							    </c:if>-->
<!--						    </c:if> -->
<!--						</c:forEach>-->
<!--	                </td>-->
<!--	                <td>${data[0].cqid }</td>-->
<!--	                <td>${data[0].dlid }</td>-->
<!--	                <td>${data[0].jd }</td>-->
<!--	                <td>${data[0].wd }</td>-->
	                <td>
	                	<c:if test="${empty data[1].id  }">连接正常</c:if>
	                	<c:if test="${not empty data[1].id }"><span style="color:red">连接异常</span></c:if>
	                </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>