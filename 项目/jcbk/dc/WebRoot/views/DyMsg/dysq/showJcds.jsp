<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="zh-CN">
<head>
	<base href="<%=basePath%>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/jcd/jcdChoose.css'/>">
	<script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
</head>
<style>
	li{
		list-style: none;
	}
</style>
<body>
	<ul id="ul1">
		
	</ul>
</body>
<script type="text/javascript">
    
    var ids = [];
    
	$(function(){
		getByIds();
	});
	//查询监测点方法
	function getByIds(){
		var hasIds = parent.$("#jcdid").val();
		if(hasIds!=''&&hasIds!=null){
    		ids = hasIds.split(',');
			$.ajax({
					async : false,//同步方法
					cache:false,
					type: 'POST',
					data: {"ids[]":ids},
					dataType : "json",
					url: "${pageContext.request.contextPath}/jcd/getByIds.do",//请求的action路径
					success:function(jcdDatas){ //请求成功后处理函数。
						for(var j in jcdDatas){
							$("#ul1").append('<li>'+'<input type="checkbox" checked="checked" name="ids" value='+jcdDatas[j].id+'>'+jcdDatas[j].jcdmc+'</li>');
						}	
					}
			});
    	}else{
    		$("#ul1").append('<li>'+'<span style="color:red;"> 默认选中了全部监测点！'+'</span>'+'</li>');
    	}
	}
</script>
</html>