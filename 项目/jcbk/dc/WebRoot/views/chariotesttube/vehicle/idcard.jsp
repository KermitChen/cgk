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
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	 	<style type="text/css">
	 		#btn_ok{
	 			border:1;
	 			padding: 0;
	 			margin-right: 20px;
	 			font-size: 16px;
	 		}
	 	</style>
	</head>
	<body>
		<div style="height: 100px;width: 340px;vertical-align: middle;line-height: 100px;">
			<span style="font-size: 16px;">&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>身份证号：</span><input id="idcard" maxlength="18"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="btn_ok" class="button_blue" value="确定" />
		</div>
	</body>
	<script type="text/javascript">
		//获取窗口索引
		var winIndex = parent.layer.getFrameIndex(window.name); 
		//关闭layer弹出层
		$("#btn_ok").click(function(){
			var idcard = $.trim($("#idcard").val());
			if(idcard == ""){
				alert('请先填写身份证号！');
				return;
			}
			
			//保存身份证号
			//显示进度条
			var index = layer.load();
			$.ajax({
				url:'<%=basePath %>/user/addIdCardToUser.do?' + new Date().getTime(),
				method:"post",
				data:{idcard:idcard},
				success:function(data){
					//关闭进度条
					layer.close(index);
					if(data.result == "1"){//添加成功！
						alert('补全身份证号信息成功，请再次执行查询！');
						//将身份证传到父页面
						parent.$("#idcard").val(idcard).change();
						//关闭窗口
						parent.layer.close(winIndex);			
					} else if(data.result == "2"){
						alert('身份证号不正确！');
					} else{
						alert('补全身份证号信息失败！');
					}
				},
				error: function () {//请求失败处理函数
					//关闭进度条
					layer.close(index);
					alert('补全身份证号信息失败！');
				}
			});
		});
	</script>
</html>