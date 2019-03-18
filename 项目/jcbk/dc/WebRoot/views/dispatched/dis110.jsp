<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="zh-CN">
<head>
	<base href="<%=basePath%>">
	<title>布控查询</title>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
</head>

<body>
	<div style="text-align:center">
		<span style="color:red;">双击相应行选择</span>
	</div>
	<div class="pg_result">
		<table>
			<thead>
				<tr>
					<td width="60">号牌号码</td>
					<td width="60">号牌种类</td>
					<td width="60">警员警号</td>
					<td width="100">接警时间</td>
					<td width="80">警情编号</td>
					<td width="300">警情描述</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="d" items="${dis110 }" varStatus="status">
					<tr ondblclick="returnDis('${d.id }','${d.hphm }','${d.hpzl }')">
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
						<td>${d.jyjy }</td>
						<td>${d.bjsj }</td>
						<td>${d.jqbh }</td>
						<td>${d.jqms }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript">
	function returnDis(id, hphm, hpzl){
		parent.returnDis(id, hphm, hpzl);
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
</script>
</html>