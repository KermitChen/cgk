<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取数据
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>通知中心详情页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-系统公告详情页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	    <script type="text/javascript">
	    	window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    		
			//文件加载完毕之后执行
			$(function(){
				//返回按钮。。关闭弹出层
				$("#backBt").click(function() {
					parent.location.reload();
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				});
			});
	    </script>
	</head>
	<body>
		<div id="content" style="width: 800px;margin:0 auto;">
			<div id="content_wrap" style="width: 750px;height: 500px;">
		        
		        <div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;通知内容：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="annGeneral" name="annGeneral" style="width: 580px;height: 80px;" readonly="readonly">${message.content}</textarea>
		            </div>
		        </div>
		        
		        <div class="button_wrap clear_both">
					<input id="backBt" type="button" class="button_blue" value="关闭">
				</div>
			</div>
		</div>	
	</body>
<%
	if(true){
%>
		<SCRIPT type="text/javascript">
			layer.msg(message);
		</SCRIPT>
<%
	}
%>
</html>