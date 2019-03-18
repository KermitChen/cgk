<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>帮助文档下载</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-帮助文档下载">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript">
			window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    		
			//根据页号查询
			function doGoPage(pageNo) {
				//获取条件
				var fileName = $.trim($("#fileName").val());//文件名
				//提交
				$.ajax({
					url:'<%=basePath%>/helpDoc/getWjjlbForPageToDownLoad.do?' + new Date().getTime(),
					method:"post",
					data:{loginName:"",userName:"",fileName:fileName,startTime:"",endTime:"",pageNo:pageNo},
					success:function(data){
						$('#dataDiv').html(data);
					},
					error: function () {//请求失败处理函数
						alert('查询失败！');
					}
				});
			}
			
			$(document).ready(function(){
		  		//根据条件获取符合条件的数据
				$("#searchBt").click(function() {
					doGoPage(1);
				});
				//默认首次查询
				$("#searchBt").trigger("click");
				
				//重置..按钮
				$("#resetBt").click(function() {
		  			$("#fileName").val("");//文档名称
				});
			});
		</script>
	</head>
	<body>
		<div class="head">
	        <div class="head_wrap">
	            <img src="<%=basePath%>common/images/police.png" alt="">
	            <h1>深圳市公安局缉查布控系统</h1>
	        </div>
	    </div>
		<div id="divTitle">
			<span id="spanTitle">当前位置：帮助文档下载</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
	    		<fieldset style="margin: 0px 0px 0px 0px;">
					<legend  style="color:#FF3333">查询条件</legend>
					<div class="slider_body">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文档名称：</span>
		                </div>
		                <div class="slider_selected_right" style="">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="fileName" type="text" class="slider_input">
		                            <a id="fileName" class="empty"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
			        <div class="button_wrap clear_both">
				    	<input id="searchBt" type="button" class="button_blue" value="查询">
				    	<input id="resetBt" type="button" class="button_blue" value="重置">
				    </div>
				</fieldset>
			    <fieldset style="margin: 20px 0px 0px 0px;">
			      	<legend style=" color:#FF3333">查询结果</legend>
			        <div id="dataDiv" class="pg_result">
			        
			        </div>
			   </fieldset>
			</div>
   		</div>
    	<jsp:include page="/common/Foot.jsp"/>
	</body>
</html>