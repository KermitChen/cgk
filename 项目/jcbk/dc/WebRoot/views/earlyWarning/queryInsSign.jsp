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
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
	<title>指令签收</title>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
</head>

<body>
	<jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">指令签收</span>
	</div>
	<div class="content">
		<div class="content_wrap">
			<div align="right">
				<input type="button" class="button_blue" value="刷新" onclick="refresh()">
			</div>
			<form name="form" action="" method="post">
				<div class="pg_result">
					<table>
						<thead>
							<tr>
								<td width="80" align="center">号牌号码</td>
								<td width="80" align="center">号牌种类</td>
								<td width="120" align="center">报警地点</td>
								<td width="150" align="center">通过时间</td>
								<td width="150" align="center">报警时间</td>
								<td width="80" align="center">下发指令人</td>
								<td width="120" align="center">下发指令部门</td>
								<td width="150" align="center">下发指令时间</td>
								<td width="80" align="center">签收状态</td>
								<td width="100" align="center">操作</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="instructionSign" items="${pageResults.items }" varStatus="status">
								<c:set var="instruction" value="${instructionSign.instruction }" />
								<c:set var="ewrecieve" value="${instructionSign.instruction.ewrecieve }" />
								<tr>
									<td>${ewrecieve.hphm}</td>
									<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
						                    	<c:if test="${dic.typeSerialNo eq ewrecieve.hpzl }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
									</td>
									<td>${ewrecieve.jcdmc}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewrecieve.tgsj}"/></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewrecieve.bjsj}"/></td>
									<td>${instruction.zlxfrmc}</td>
									<td>${instruction.zlxfbmmc}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${instruction.xfsj}"/></td>
									<td>${instructionSign.qsztmc}</td>
									<td>
										<a onclick="insDetail('${instructionSign.id}')" style="font-size: 15px;">详情</a>
										<c:if test="${instructionSign.qszt eq '0'}">
											|&nbsp;<a onclick="sigh('${instructionSign.id}')" style="font-size: 15px;">签收</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<jsp:include page="/common/pageNavigators.jsp"></jsp:include>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="/common/Foot.jsp" />
	
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/MarkerClusterer_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/TextIconOverlay_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
		var list_url = "queryInsSign.do";
		//刷新..按钮
		function refresh() {
			layer.msg("刷新完成！");
			doSearch();
		}
		function doSearch(){
			document.getElementById("pageNo").value = 1;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		//根据页号查询
		function doGoPage(pageNo) {
			document.getElementById("pageNo").value = pageNo;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		function sigh(id){
			var url = "${pageContext.request.contextPath}/earlyWarning/insSign.do?id="+id;
		    layer.open({
		           type: 2,
		           title: '指令签收窗口',
		           shadeClose: false,
		           shade: 0.8,
		           area: ['1180px', '600px'],
		           content: url //iframe的url
		       }); 	
		}
		
		function insDetail(id){
			var url = "${pageContext.request.contextPath}/earlyWarning/loadInsDetail.do?id="+id;
		    layer.open({
		           type: 2,
		           title: '指令详情窗口',
		           shadeClose: true,
		           shade: 0.8,
		           area: ['1180px', '600px'],
		           content: url //iframe的url
		       }); 	
		}
	</script>
</html>