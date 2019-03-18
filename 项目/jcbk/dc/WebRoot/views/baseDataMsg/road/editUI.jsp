<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <title>添加基础数据页面</title> 
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
			<span id="spanTitle">新增道路基础数据</span>
		</div>
		<form name="form1" action="" method="post">
		<div id="divBody">
			<table id="tableForm">
				<tr>
					<td class="tdText">道路名称:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="roadName" id="roadName" value="${road.roadName }">
					</td>
					<td class="tdError">
						<label class="errorClass" id="roadNameError">道路名称不能为空!</label>
					</td>
				</tr>
				<tr>
					<td class="tdText">道路代码:</td>
					<td>
						<input class="inputClass" type="text" name="roadNo" id="roadNo" value="${road.roadNo }">
					</td>
					<td class="tdError">
						<label class="errorClass" id="roadNoError">道路代码不能为空!</label>
					</td>
				</tr>
				<tr>
					<td class="tdText">区域名称:</td>
					<td>
						<input class="inputClass" type="text" name="areaName" id="cityName" value="${ areaName }">
					</td>
					<td class="tdError">
						<label class="errorClass" id="cityNameError" >区域名称错误!</label>
					</td>
				</tr>
				<tr>
					<td class="tdText">车道数量:</td>
					<td>
						<input class="inputClass" type="text" name="roadwayNum" id="roadwayNum" value="${road.roadwayNum }">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">管制行为:</td>
					<td>
						<input class="inputClass" type="text" name="controlBehavior" id="controlBehavior" value="${road.controlBehavior }">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">道路性质:</td>
					<td>
						<input class="inputClass" type="text" name="roadProperty" id="roadProperty" value="${road.roadProperty }">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">道路等级:</td>
					<td>
						<input class="inputClass" type="text" name="roadGrade" id="roadGrade" value=${road.roadGrade }>
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
		<input type="hidden" value=${road.pkId } name="pkId">
		<input type="hidden" value="${pageNo }" name="pageNo">
		<input type="hidden" value=${Check_roadName } name="Check_roadName">
		<input type="hidden" value=${Check_areaName } name="Check_areaName">
		</form>
	</div>
    <div class="foot">
        <p>苏ICP证编号 B2-20110162 信息网络传播视听节目许可证 1009365 号</p>
    </div>
</body>
    <script type="text/javascript">
		//文件加载完毕之后执行
		$(function(){
			
			/*
			 * 2.遍历输入框,隐藏错误信息
			 */
			$(".inputClass").each(function(){
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
		
		//道路名称校验方法
		function validateRoadName(){
			/*
			 * 1.非空校验
			 */
			var id="roadName";
			var value = $("#"+id).val();
			if(!value){//输入框中没有值,显示错误信息
				$("#" +id +"Error").text("道路名称不能为空!");
				showError($("#" +id +"Error"));
				return false;
			}else{//输入框中有值，清空错误信息
				$("#" +id +"Error").text("");
				showError($("#" +id +"Error"));
				return true;
			}
			/*
			 * 2.长度校验
			 */
			if(value.length>15){
				//获取对应的label,显示错误信息
				$("#" +id +"Error").text("请输入15位以内的道路名称");
				showError($("#" +id +"Error"));
				return false;
			}else{
				$("#" +id +"Error").text("");
				showError($("#" +id +"Error"));
				return true;				
			}
			return true;
		}
		
		//区域名称校验方法
		function validateCityName(){
			/*
			 * 1.非空校验
			 */
			var id="cityName";
			var value = $("#"+id).val();
			if(!value){
				//获取对应的label,显示错误信息
				$("#" +id +"Error").text("区域名称不能为空!");
				showError($("#" +id +"Error"));
				return false;
			}else{
				$("#" +id +"Error").text("");
				showError($("#" +id +"Error"));
				return true;				
			}
			/*
			 * 2.长度校验
			 */
			if(value.length>15){
				//获取对应的label,显示错误信息
				$("#" +id +"Error").text("请输入15位以内的区域名称!");
				showError($("#" +id +"Error"));
				return false;
			}else{
				$("#" +id +"Error").text("");
				showError($("#" +id +"Error"));
				return true;
			}
			return true;
			
		}
		
		function validateRoadNo(){
			/*
			 * 1.非空校验
			 */
			var id="roadNo";
			var value = $("#"+id).val();
			if(!value){
				//获取对应的label,显示错误信息
				$("#" +id +"Error").text("道路代码不能为空!");
				showError($("#" +id +"Error"));
				return false;
			}else{
				$("#" +id +"Error").text("");
				showError($("#" +id +"Error"));
				return true;				
			}
			/*
			 * 2.长度校验
			 */
			if(value.length>10){
				//获取对应的label,显示错误信息
				$("#" +id +"Error").text("请输入10位以内的道路代码");
				showError($("#" +id +"Error"));
				return false;
			}else{
				$("#" +id +"Error").text("");
				showError($("#" +id +"Error"));
				return true;			
			}
			return true;
		}
		//跳转到listUI页面
		function doSubmit(){
			//校验表单,出错不让点击保存
			 
			var verifyResult1 = validateRoadName();
			var verifyResult2= validateRoadNo();
			//账号校验
			if(verifyResult1 && verifyResult2){
				//提交表单
				document.forms[0].action="road/editRoad.do";
				document.forms[0].submit();
			}else{
				return confirm("请核对您录入的信息!");
			}
		}
    </script>
</html>