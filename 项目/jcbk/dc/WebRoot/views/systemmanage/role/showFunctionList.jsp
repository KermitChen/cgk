<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>系统功能列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-系统功能列表">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript">
			<!--
			var zNodes = <%=(request.getAttribute("funList")!=null?request.getAttribute("funList"):"[]") %>;
			
			//树的基本设置
			var setting = {
				data: {
					key: {
						name: "funName",
						title: "funName"
					},
					simpleData: {
						enable: true,
						idKey: "permissionPosition",
						pIdKey: "parentPermission",
						rootPid: "0"
					}
				}
			};
			
			$(document).ready(function(){
				//显示树
				$.fn.zTree.init($("#roleTree"), setting, zNodes);
			});
		//-->
		</script>
	</head>
	<body>
		<%	
			//获取失败
			if(request.getAttribute("funList") == null){
		%>
				<SCRIPT type="text/javascript">
					alert("加载系统功能列表失败！");
				</SCRIPT>
		<%
			}
		%>
		<fieldset style="margin: 0px auto">
			<legend style="color:#FF3333">系统功能列表</legend>
			<ul id="roleTree" class="ztree" style="margin: 10px 20px;overflow-y: auto;height: 350px;border: 1px solid #000000;position:relative;clear:both;"></ul>
		</fieldset>
	</body>
</html>