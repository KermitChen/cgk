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
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">报警记录</legend>
				<div class="pg_result">
					<table>
						<thead>
							<tr>
								<td width="10%" align="center">号牌号码</td>
								<td width="10%" align="center">号牌种类</td>
								<td width="30%" align="center">报警监测点</td>
								<td width="10%" align="center">行驶车道</td>
								<td width="15%" align="center">通过时间</td>
								<td width="15%" align="center">报警时间</td>
								<td width="10%" align="center">详情</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="e" items="${ewarningList }" varStatus="status">
								<tr>
									<td>${e.hphm}</td>
									<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
						                    	<c:if test="${dic.typeSerialNo eq e.hpzl }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
									</td>
									<td>${e.jcdmc }</td>
									<td>${e.cdid }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${e.tgsj }"/></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${e.bjsj }"/></td>
									<td><a onclick="eWarningDetail('${e.bjxh}')">详情</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</fieldset>
	</body>
	<script type="text/javascript">
		function eWarningDetail(bjxh){
			var url = "${pageContext.request.contextPath}/earlyWarning/loadEWarningDetail.do?bjxh="+bjxh;
		    layer.open({
		           type: 2,
		           title: '预警信息详情',
		           shadeClose: true,
		           area: ['860px', '500px'],
		           content: url //iframe的url
		       }); 	
		}
	</script>
</html>