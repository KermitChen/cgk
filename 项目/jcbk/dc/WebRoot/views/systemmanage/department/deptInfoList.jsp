<%@ page language="java"
	import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*"
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
	// else if(pageResult != null && pageResult.getItems().size() <= 0){
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
				<td>部门编号</td>
				<td>部门名称</td>
				<td>联系电话</td>
				<td>上级部门</td>
				<td>绩效考核</td>
<!--				<td>创建人</td>-->
<!--				<td>创建时间</td>-->
<!--				<td>更新时间</td>-->
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${pageResult.items }" varStatus="status">
				<tr ondblclick="javascript:deptDetail(${data.id});">
					<td width="30px" align="center">
						<c:if test="${data.infoSource ne '2'}">
							<input type="checkbox" id="ids" name="ids" value="${data.deptNo }"/>
						</c:if>
						<c:if test="${data.infoSource eq '2'}">
							<input type="checkbox" id="ids" name="ids" value="0"/>
						</c:if>
					</td>
					<td>${status.index+1}</td>
					<td>${data.deptNo }</td>
					<td>${data.deptName }</td>
					<td>${data.deptTelephone }</td>
					<td>${data.parentName }</td>
					<td>
						<c:if test="${data.jxkh eq '0' }">
							否
						</c:if>
						<c:if test="${data.jxkh eq '1' }">
							是
						</c:if>
					</td>
					<td width="120px">
						<a href="javascript:deptDetail(${data.id});">详情</a>
			            |&nbsp;<a href="javascript:doEdit(${data.id})">编辑</a>
						<c:if test="${data.infoSource ne '2'}">
			              	|&nbsp;<a href="javascript:doDelete(${data.deptNo})">删除</a>
			            </c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>            
	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>