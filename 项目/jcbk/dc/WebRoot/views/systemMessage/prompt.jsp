<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html lang="en">
  <head>
    <base href="<%=basePath%>">
    <base href="<%=request.getContextPath()%>">
    
    <title>My JSP 'pushletTest.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    This is my PushletTest JSP page. <br>
    <input id="test1" value="确定" type="button" >
  </body>
  <script type="text/javascript" src="<%=basePath%>common/js/systemMessage/ajax-pushlet-client.js"></script>
  <script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
  <script type="text/javascript">
      $('#test1').on('click', function(){
		   //layer.msg('Hello layer');
		   layer.msg('有表情地提示', {icon: 6});
	  });
	  
	  //PL._init(); 
	  PL.joinListen('/test/liu');
	  function onData(event) { 
		  console.log(event.get("hw"));
		  //layer.msg('有表情地提示', {icon: 6});
		  layer.tips('2','#test1',{
		  	time: 0,
		  });
		  systemErrorMessage();
	  } 
	  
	  //只向系统推送一次...系统异常联系方式
	  var xtyc=0;
	  function systemErrorMessage(){
	  	if(xtyc==0){
	  		layer.confirm('系统异常联系方式1523654489', {
  			btn: ['知道了','稍后提示'] //按钮
		},function(){
			xtyc=1;
			layer.msg('系统异常，请拔打此号码！');
		},function(){
			layer.msg('稍后提示...',{time:1000});
		});
		     /* layer.msg(
		     	'系统异常联系方式:15233674456!',
		     	{icon:6,time:4000}
		     ); */
	  	}else if(xtyc==1){
	  		
	  	}
	  }
	  
  </script>	
</html>
