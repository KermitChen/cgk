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
    <title>红名单申请详情页面</title> 
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/hmdDetail.css'/>">
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<style type="text/css">
		/* 确定button样式style */
		.button_blue{
			padding: 4px 16px;
			border: none;
			color: #fff;
			background: #08f;
			/*behavior: url(ie-css3.htc);*/
			border-radius: 6px;
			outline: none;
			cursor: pointer;
			margin: 4px 10px;
			border: 1px solid #fff;
		}
		.button_blue:hover{
			background: #fff;
			border: 1px solid #ccc;
			color: #555;
		}
		.button_white{ 
			background: #fff;
			border: 1px solid #ccc;
			color: #555;
		}
		.button_white:hover{
			color: #fff;
			background: #08f;
			border: none;
			border: 1px solid transparent;
		}
	</style>
</head>
<body>

	<div id="divMain">
		<div id="divTitle">
			<span id="spanTitle">红名单审批</span>
		</div>
		<div id="divBody">
			<table id="tableForm">
				<tr>
					<td class="tdText">车牌号码:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="areaNo" id="areaNo" value="${hmdDetail.cphid }">
					</td>
					<td class="tdText">车辆类型:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="areaNo" id="cplx" value="">
						<c:forEach items= "${cpysList }" var="c">
							<c:if test="${c.typeSerialNo eq hmdDetail.cplx }">
			            	   	<script>
			            		 	$("#cplx").val("${c.typeDesc }");
			            	   	</script>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td class="tdText">车辆使用人:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="areaName" id="areaName" value="${hmdDetail.clsyz }">
					</td>
					<td class="tdText">车辆所有人:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="areaName" id="areaName" value="${hmdDetail.cz }">
					</td>
				</tr>
			
				<tr>
					<td class="tdText">车标:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="superAreaNo" id="superAreaNo" value="${hmdDetail.cb }">
					</td>
					<td class="tdText">车身颜色:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="superAreaNo" id="superAreaNo" value="${hmdDetail.csys }">
					</td>
				</tr>
				
				<tr>
					<td class="tdText">申请人警号:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="superAreaNo" id="superAreaNo" value="${hmdDetail.sqrjh }">
					</td>
					<td class="tdText">申请人姓名:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="superAreaNo" id="superAreaNo" value="${hmdDetail.sqrxm }">
					</td>
				</tr>
				
				<tr>
					<td class="tdText">所属单位:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="superAreaNo" id="superAreaNo" value="${dwmc}">
					</td>
					<td class="tdText">申请时间:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="superAreaNo" id="superAreaNo" value="${hmdDetail.lrsj }">
					</td>
				</tr>
				
				<tr>
					<td class="tdText">列入原因:</td>
					<td class="tdInput" colspan="3">
						<input class="MaxinputClass" type="text" name="superAreaNo" id="superAreaNo" value="${hmdDetail.lryy }">
					</td>
				</tr>
				
				<tr>
					<td class="tdText">备注:</td>
					<td class="tdInput" colspan="3">
						<input class="MaxinputClass" type="text" name="superAreaNo" id="superAreaNo" value="${hmdDetail.bzsm }">
					</td>
				</tr>
			</table>
			
				<div style="text-align: center;">
					<form id="form1">
					<table id="table3" width="95%">
						<tr>
							<td class="tdText">审批意见：</td>
							<td>
								<input type="radio" name="spyj" value="1" id="agree" checked="checked" onclick="textareaFuZhi()" />同意
								<input type="radio" name="spyj" value="0" id="disagree" onclick="textareaFuZhi()"/>不同意
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td class="tdText">审批结果描述：</td>
							<td colspan="3">
								<textarea name="spjgDesc" id="textarea" rows="1" style="width: 99%;"></textarea>
							</td>
						</tr>
					</table>
					<input type="hidden" name="dyxxId" id="dyxxId" value=""/>
					<input type="button" value="确定" class="button_blue" onclick="doSubmit()"/>
					<input type="button" value="关闭" class="button_blue" onclick="doClose()"/>
					</form>
				</div>
			</div>
		</div>
		
</body>
<script type="text/javascript">

	//文档加载时
	$(function(){
		textareaFuZhi();
	});
	
	//审批结果描述 textarea自动赋值
	function textareaFuZhi(){
		if($("#agree").is(':checked')){
			$("#textarea").text("同意列入！");
		}else  if($("#disagree").is(':checked')){
			$("#textarea").text("不同意列入！");
		}
	}
	
	//点击提交按钮，保存审批表
	function doSubmit(){
		if($("#agree").is(':checked')){
			complete('${task.id }','${task.processInstanceId }','${passKey }',true,$("#textarea").text());
		}else  if($("#disagree").is(':checked')){
			complete('${task.id }','${task.processInstanceId }','${passKey }',false,$("#textarea").text());
		}
	}
	
	/**
	 * 完成任务
	 * @param {Object} taskId
	 */
	function complete(taskId,taskProInstId, key,value,advice) {
		$.blockUI({
	        message: '<h2><img src="' + 'common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
	    });
		// 发送任务完成请求
	    $.post('/dc/workflow/complete.do?taskId=' + taskId+'&key='+key+'&value='+value+'&taskProInstId='+taskProInstId+'&advice='+encodeURI(encodeURI(advice))  
	    , function(resp) {
			$.unblockUI();
	        if (resp == 'success') {
	            alert('审批完成');
	            parent.location.reload();
	            doClose();
	        } else {
	            alert('操作失败!请联系管理员');
	            parent.location.reload();
	            doClose();
	        }
	    });
	}
</script>
</html>