<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.lang.Exception"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>深圳市公安局缉查布控系统-错误页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-错误页面">
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
			 	background:url(<%=basePath %>common/images/wz.jpg) no-repeat;
				padding-top:50px;
				margin-left: auto; 
			 	margin-right: auto; 
			 	overflow: auto; 
			 	width: 325px; 
			 	height: 150px; 
			} 
		</style>
	</head>
 	<body style="text-align: center; background:#F0EEFF" >
 		<div class="FirstDIV"></div> 
 		<div class="SecondDIV">
		  	您访问的系统存在问题，
                  	请拨打维护电话:84469394协助解决！
 		</div>
	</body>
</html>