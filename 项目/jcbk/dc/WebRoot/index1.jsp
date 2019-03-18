<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
<!-- 		<a id="loginForCodeId" href="http://localhost/dc/user/loginOa.do?loginName=super" target="_blank"><div class="dengluq">登录</div></a> -->
  		<frameset rows="82,*,400">
			<frame src="" scrolling="no">
			<frameset cols="200,*">
				
			</frameset>
			<frame src="http://10.235.58.104:8080/dc/user/oaSp.do?userName=s22222" scrolling="no">
		</frameset>
</html>
