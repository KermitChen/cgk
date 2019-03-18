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
			<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">撤控记录</legend>
			<div class="pg_result">
				<table>
					<thead>
						<tr>
							<td width="10%" align="center">撤控申请人</td>
							<td width="20%" align="center">撤控申请单位</td>
							<td width="15%" align="center">申请时间</td>
							<td width="10%" align="center">直接撤控</td>
							<td width="10%" align="center">撤控状态</td>
							<td width="30%" align="center">撤控原因</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="w" items="${withdrawList }" varStatus="status">
							<tr>
								<td>${w.cxsqr}</td>
								<td>${w.cxsqdwmc }</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${w.cxsqsj }" /></td>
								<td>
									<c:if test="${w.zjck eq '1' }">是</c:if>
									<c:if test="${w.zjck eq '0' }">否</c:if>
								</td>
								<td>
									<c:forEach items= "${dicList}" var="dic">
					            	  	<c:if test="${dic.typeCode eq 'CKYWZT' }">
						                    <c:if test="${dic.typeSerialNo eq w.ywzt }">
						                    	${dic.typeDesc }
						                    </c:if>
					            	  	</c:if> 
									 </c:forEach>
								</td>
								<td>${w.ckyyms }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</fieldset>
		<c:if test="${!empty ckCommentList }">
			<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">审批信息</legend>
				<div class="pg_result">
					<table>
						<thead>
							<tr>
								<td width="20%" align="center">时间</td>
								<td width="20%" align="center">审批人</td>
								<td width="20%" align="center">操作结果</td>
								<td width="30%" align="center">审批意见</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="c" items="${ckCommentList }" varStatus="status">
								<tr>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${c.czsj}" /></td>
									<td>${c.czr}(${c.czrjs})</td>
									<td>${c.czjgmc }</td>
									<td>${c.ms }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</fieldset>
		</c:if>
		<c:if test="${!empty ckReceiveList }">
			<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">撤控通知签收情况</legend>
				<div class="pg_result">
					<table>
						<thead>
							<tr>
								<td width="20%" align="center">布控签收单位</td>
								<td width="10%" align="center">签收状态</td>
								<td width="15%" align="center">签收时间</td>
								<td width="10%" align="center">签收人</td>
								<td width="15%" align="center">签收人联系电话</td>
								<td width="30%" align="center">签收内容</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="d" items="${ckReceiveList }" varStatus="status">
								<tr>
									<td>${d.qrdwmc}</td>
									<td>
										<c:if test="${d.qrzt eq '1'}">已签收</c:if>
										<c:if test="${d.qrzt eq '0'}">未签收</c:if>
									</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${d.qrsj }" /></td>
									<td>${d.qrr}</td>
									<td>${d.qrdwlxdh}</td>
									<td>${d.qrjg}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</fieldset>
		</c:if>
	</body>
</html>
