<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	//获取session中的用户
	User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);

	String telphone = request.getParameter("telphone");
	String content = request.getParameter("content");
	IntefaceUtils.sendMessage(telphone, content);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>操作结果返回页面</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
		html,body{ 
			height: 100%; 
			padding:0; 
			margin:0; 
		} 
		body{ 
			min-height: 136px; 
			text-align: center; 
			min-width: 325px 
		} 
		.FirstDIV{ 
			margin-top: -68px; /* SecondDIV高度的一半 */ 
			float: left; 
			width: 100%; 
			height: 50%; 

		} 
		.SecondDIV{ 
			text-align:center;
			clear: both; 
			background:url(/dc/images/wz.jpg);
			padding-top:50px;
			margin-left: auto; 
			margin-right: auto; 
			overflow: auto; 
			width: 325px; 
			height: 136px; 
		} 
	</style>
	<script type="text/javascript">
		function btnClick(){
		   window.location = '<%=basePath %>/views/shortmessage/send.jsp?' + new Date().getTime()
		}
	</script>
	</head>
	<body style="text-align: center; background:#F0EEFF" onunload="btnClick();">
		<form action="" name="Formresult" id="Formresult"></form>
		<div class="FirstDIV"></div> 
		<div class="SecondDIV">
		    <span style="color: green;">发送成功！</span>
			<a href="###" onclick="btnClick(); return false; ">返回</a>
		</div>
	</body>
</html>