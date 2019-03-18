<%@ page language="java"
	import="java.util.*,com.dyst.utils.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	
	User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	request.setAttribute("user", user);
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
				<td>用户名</td>
				<td>姓名</td>
				<td>手机号码</td>
				<td>身份证号码</td>
				<td>隶属部门</td>
				<td>角色类型</td>
				<td>用户角色</td>
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
				<td>${data.loginName }</td>
				<td>${data.userName }</td>
				<td>${data.telPhone }</td>
				<td>${data.identityCard }</td>
				<td>${data.deptName }</td>
				<td>
					<c:forEach items= "${dicList}" var="dic">
						<c:if test="${dic.typeCode eq '1001' }">
							<c:if test="${dic.typeSerialNo eq data.position }">
						    	${dic.typeDesc }
						    </c:if>
					    </c:if> 
					</c:forEach>
				</td>
				<td>${data.roleName }</td>
				<td width="180px">
					<a href="javascript:doDetail(${data.id})">详情</a>
	              	|&nbsp;<a href="javascript:doEdit(${data.id})">编辑</a>
                  	<c:if test="${data.position ne '99999'}">
                  		|&nbsp;<a href="javascript:doResetPassword(${data.id})">重置密码</a>
                  	</c:if>
					<c:if test="${data.infoSource ne '2' and data.position ne '99999' and data.loginName ne user.loginName}">
	              		|&nbsp;<a href="javascript:doDelete(${data.id})">删除</a>
                  	</c:if>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>            
	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>