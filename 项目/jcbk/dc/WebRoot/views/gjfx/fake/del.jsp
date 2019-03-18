<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标记无效页面</title>
<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
<style type="text/css">
	.slider_body{
		margin: 10px 0px 0 0px;
	    position: relative;
	    display: block;
	    float: left;
	    width: 300px;
	    height: 30px; 
	}
	.slider_selected_right{
		left :80px;
	}
	.button_div{
		margin: 10px 0px 0 0px;
	    position: relative;
	    display: block;
	    float: left;
	    width: 200px;
	    height: 30px; 
	}
	.button_blue{
		padding: 2px 18px;
	    border: none;
	    border-radius: 6px;
	    outline: none;
	    margin: 4px 10px;
	    border: 1px solid #fff;
	}
</style>
</head>
<body>
<div class="content">
	<div>
		<span>理由：</span>
		<textarea id="reason" maxlength="500" style="width: 580px;height: 80px;">识别错误!</textarea>
	</div>
	<div>
		<div class="slider_body">
	         <div class="slider_selected_left">
	             <span>车牌号码：</span>
	         </div>
	         <div class="slider_selected_right">
	             <div class="img_wrap">
					<div class="select_wrap select_input_wrap">
						<input id="realPlate" class="slider_input" type="text">
						<a id="realPlate" class="empty"></a>
					</div>
				</div>
	         </div>
	   	</div>
	   	<div class="button_div" >
			<input id="ensure_button" name="query_button" type="button" class="button_blue" value="确定">
	    	<input id="cancel_button" name="reset_button" type="button" class="button_blue" value="取消">
			<input type="hidden" value="${id }" id="tpid">
		</div>
	</div>
</div>
</body>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
<script type="text/javascript">
	var index; //获取窗口索引
	$(function(){
		index = parent.layer.getFrameIndex(window.name);
		$("#ensure_button").click(function(){
			markData();
		});
		$("#cancel_button").click(function(){
			parent.layer.close(index);
		});
	});
	function markData(){
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		var id = $.trim($("#tpid").val()); 
		var  reason = $.trim($("#reason").val()); 
		var realPlate = $.trim($("#realPlate").val()); 
		$.ajax({
			async:true,
			type:'post',
			data:{id:id,reason:reason,realPlate:realPlate},
			dataType : "json",
			url: basePath + "/fake/markDel.do",//请求的action路径
			error: function () {//请求失败处理函数
				layer.msg('请求失败！');
			},
			success:function(data){
				console.log(data);
				if(data == null || data == '0'){
					layer.msg('请求失败!');
				}else if(data != null && data == '1'){
					parent.$("#delete_button").remove();
					parent.layer.msg('标记成功!');
					parent.layer.close(index);
				}
			}
		});
	}
</script>
</html>