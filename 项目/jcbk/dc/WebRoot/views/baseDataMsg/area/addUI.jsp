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
    <title>新增城区基础数据页面</title> 
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/edit.css'/>">
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
</head>
<body>
    <div class="head">
        <div class="head_wrap">
            <img src="<%=basePath%>common/images/police.png" alt="">
            <h1>深圳市公安局缉查布控系统</h1>
        </div>
    </div>
	<div id="divMain">
		<div id="divTitle">
			<span id="spanTitle">新增城区基础数据</span>
		</div>
		<form name="form1" action="" method="post">
		<div id="divBody">
			<table id="tableForm">
				<tr>
					<td class="tdText">区域代码:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="areano" id="areaNo" placeholder="区域代码不能为空!" />
					</td>
					<td class="tdError">
						<label class="errorClass" id="areaNoError"></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">区域名称:</td>
					<td>
						<input class="inputClass" type="text" name="areaname" id="areaName" placeholder="区域代码不能为空!" />
					</td>
					<td class="tdError">
						<label class="errorClass" id="areaNameError"></label>
					</td>
				</tr>
			
				<tr>
					<td class="tdText">隶属区域代码:</td>
					<td>
						<input class="inputClass" type="text" name="supareano" id="superAreaNo">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
				        <div class="button_wrap clear_both">
					    	<input type="button" class="button_blue" value="保存" onclick="doSubmit()">
					    	<input type="button" class="button_blue" value="返回" onclick="history.go(-1)">
					    </div>
					</td>
					<td>
						<label></label>
					</td>
				</tr>

			</table>
		</div>
		</form>
	</div>
    <jsp:include  page="/common/Foot.jsp"/>
</body>
    <script type="text/javascript">
		//文件加载完毕之后执行
		$(function(){
			/*
			 * 1.得到所有的错误信息，循环遍历他，调用方法确定是否显示错误信息
			 */
			$(".errorClass").each(function (){
				$(this).text("");//把label的内容清空
				showError($(this));//遍历每一个元素，调用showError()方法
			});
			/*
			 * 2.输入框得到焦点,隐藏错误信息
			 */
			$(".inputClass").focus(function(){
				var lableId = $(this).prop("id")+"Error";
				$("#"+lableId).text("");//把label的内容清空
				showError($("#"+lableId));
			});
			/*
			 * 3.输入框失去焦点时进行校验
			 */
			$(".inputClass").blur(function (){
				var id = $(this).prop("id");
				//alert($(this).prop("id"));
				//得到对应的校验函数
				
				//validateRoadName();
				var funName = "validate" + id.substring(0,1).toUpperCase()+id.substring(1)+"()";
				//把对应的字符串当成对应的函数方法来执行
				eval(funName); 
			});
		
		});
		
		//判断当前元素，是否显示错误信息的function
		function showError(ele){
			var text = ele.text();
			if(!text){
				ele.css("display","none");
			}else{
				ele.css("display","");
			}
		}
		
		//城区代码校验方法
		function validateAreaName(){
			/*
			 * 1.非空校验
			 */
			var id="areaName";
			var value = $("#"+id).val();
			if(!value){
				//获取对应的label,显示错误信息
				$("#" +id +"Error").text("城区名称不能为空!");
				showError($("#" +id +"Error"));
				return false;
			}
			/*
			 * 2.长度校验
			 */
			if(value.length>15){
				//获取对应的label,显示错误信息
				$("#" +id +"Error").text("请输入15位以内的城区名称");
				showError($("#" +id +"Error"));
				return false;
			}
			return true;
		}
		
		//区域名称校验方法
		function validateAreaNo(){
			/*
			 * 1.非空校验
			 */
			var id="areaNo";
			var value = $("#"+id).val();
			if(!value){
				//获取对应的label,显示错误信息
				$("#" +id +"Error").text("区域代码不能为空!");
				showError($("#" +id +"Error"));
				return false;
			}
			/*
			 * 2.长度校验
			 */
			if(value.length>10){
				//获取对应的label,显示错误信息
				$("#" +id +"Error").text("请输入10位以内的区域代码!");
				showError($("#" +id +"Error"));
				return false;
			}
			return true;
			
		}
		
		//提交时，校验表单
		function doSubmit(){
			//校验表单,出错不让点击保存
			 
			var verifyResult1 = validateAreaName();
			var verifyResult2= validateAreaNo();
			//账号校验
			if(verifyResult1 && verifyResult2){
				//提交表单
				document.forms[0].action="area/addArea.do";
				document.forms[0].submit();
			}else{
				return confirm("请您录入必填项的信息!");
			}
		}
    </script>
</html>