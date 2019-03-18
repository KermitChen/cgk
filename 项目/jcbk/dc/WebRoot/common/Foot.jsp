<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="en">
	<head>
		<title>Foot</title>
	<!-- 	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css"> -->
	</head>
	<body>
		<div class="foot">
			<p></p>
		</div>
		<div class="mask"></div>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script>
<!-- 	<script type="text/javascript" src="<%=basePath%>common/js/1.9.0-jquery.js"></script> -->
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.dev.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/time/moment.js"></script>
</html>
