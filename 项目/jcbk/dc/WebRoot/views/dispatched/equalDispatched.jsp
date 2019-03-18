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
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">协同作战信息</legend>
				<div class="pg_result">
					<table>
						<thead>
							<tr>
								<td width="10%" align="center">号牌号码</td>
								<td width="10%" align="center">号牌种类</td>
								<td width="10%" align="center">布控大类</td>
								<td width="10%" align="center">布控类别</td>
								<td width="10%" align="center">布控性质</td>
								<td width="10%" align="center">布控人</td>
								<td width="10%" align="center">布控人联系电话</td>
								<td width="20%" align="center">布控单位</td>
								<td width="20%" align="center">布控截止时间</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="d" items="${dispatchedList }" varStatus="status">
								<tr>
									<td>${d.hphm }</td>
									<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
						                    	<c:if test="${dic.typeSerialNo eq d.hpzl }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
									</td>
									<td>
									 	<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKDL' }">
						                    	<c:if test="${dic.typeSerialNo eq d.bkdl }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
								 	</td>
								 	<td>
									 	<c:forEach items= "${bklb}" var="b">
					            	  		<c:if test="${b.ID eq d.bklb }">
						                    		${b.NAME }
					            	  		</c:if> 
									 	</c:forEach>
								 	</td>
								 	<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKXZ' }">
						                    	<c:if test="${dic.typeSerialNo eq d.bkxz }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
									</td>
									<td>${d.bkr}</td>
									<td>${d.bkjglxdh }</td>
									<td>${d.bkjgmc }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${d.bkjzsj }" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</fieldset>
	</body>
</html>