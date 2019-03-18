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
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/edit.css'/>">
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
 	<style type="text/css">
 		.divMain  a{
 			padding: 0px 5px;
 			text-align: center;
 			font-size: 16px;
 			font-weight: bold;
 		}
 		table {
 			padding-bottom: 40px;
 			margin-bottom: 40px;
 			width: 99%;
 		}
 		td {
 			
 		}
 		.foot{
 			height: 36px;
 			background: #d0d0d0;
 		}
 		#btn_ok{
 			border:1;
 			padding: 0;
 			float: right;
 			margin-right: 20px;
 			font-size: 16px;
 		}
 		#btn_clear{
 			border:1;
 			padding: 0;
 			float: left; 
 			margin-left: 20px;
 			font-size: 16px;		
 		}
 		
 		#cphm{
 			width: 80%;
 			height: 30px;
 			border: 1 solid #fff;
 			margin-left: 2px;
 		}
 	</style>
</head>
<body>
	<div class="divCpInput">
		<span style="font-size: 16px;">车牌号码：</span><input id="cphm" onkeyup="this.value=this.value.toUpperCase()"/>
		<div>
			<font color="red">备注:想要查询多个车牌，每个车牌间用英文半角逗号隔开。</font>
		</div>
	</div>
	<div class="divMemo">
	</div>
	<hr>
	<div class="divMain" style="text-align: center;">
		<div class="div2" style="text-align: center;margin: auto;">
			<table>
				<tr>
					<td><a ><button style="width: 40px; height: 28px;">粤</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">粤B</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">港</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">澳</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">台</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">京</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">津</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">冀</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">沪</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">苏</button></a></td>
				</tr>
				<tr>
					<td><a ><button style="width: 40px; height: 28px;">浙</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">皖</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">蒙</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">闽</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">赣</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">鲁</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">豫</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">鄂</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">湘</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">桂</button></a></td>
				</tr>
				<tr>
					<td><a ><button style="width: 40px; height: 28px;">渝</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">琼</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">川</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">贵</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">云</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">新</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">青</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">藏</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">蒙</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">陕</button></a></td>
				</tr>
				<tr>
					<td><a ><button style="width: 40px; height: 28px;">甘</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">宁</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">军</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">海</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">警</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">学</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">使</button></a></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><a ><button style="width: 40px; height: 28px;">A</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">B</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">C</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">D</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">E</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">F</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">G</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">H</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">I</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">J</button></a></td>
				</tr>
				<tr>
					<td><a ><button style="width: 40px; height: 28px;">K</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">L</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">M</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">N</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">O</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">P</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">Q</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">R</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">S</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">T</button></a></td>
				</tr>
				<tr>
					<td><a ><button style="width: 40px; height: 28px;">U</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">V</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">W</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">X</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">Y</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">Z</button></a></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><a ><button style="width: 40px; height: 28px;">1</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">2</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">3</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">4</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">5</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">6</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">7</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">8</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">9</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">0</button></a></td>
				</tr>
				<tr>
					<td><a ><button style="width: 40px; height: 28px;">?</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">*</button></a></td>
					<td><a ><button style="width: 40px; height: 28px;">,</button></a></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
		<div class="foot">
			<input id="btn_clear" type="button" class="button_blue" value="清空" onclick="javascript:Clear()">
			<input type="button" id="btn_ok" class="button_blue" value="确定" />
		</div>
	</div>
	
</body>
<script type="text/javascript">
	//点击超链接，输入框中添加<a>中的值
	$(function(){
		$("a").click(function(){
				var Val = $(this).text();
				var temp = $("#cphm").val();
				temp+=Val;
				$("#cphm").val(temp);
			}
		);
	});
	//点击退格button，实现清空功能
	function Clear(){
		var cph = $("#cphm").val('');
	}
	var cphs = [];
	//页面关闭之前把input中的值传到父页面中
/* 	window.onbeforeunload=function(){
		var cph = $("#cphm").val();
		var temp = parent.$("#cphid").val();
		temp+=cph;
		parent.$("#cphid").val(temp);
	}; */
	//窗口关闭之前把值 传到父窗口中去
// 	$(window).bind("beforeunload",function(){
// 		doClose();
// 	});
	function doClose(){
		var cph = $("#cphm").val();
		var temp = parent.$("#cphid").val();
		temp+=cph;
		parent.$("#cphid").val(temp).change();//触发change事件，请不要去掉
	}
	//获取窗口索引
	var index = parent.layer.getFrameIndex(window.name); 
	//关闭layer弹出层
	$("#btn_ok").click(function(){
		doClose();
		parent.layer.close(index);
	});
</script>
</html>