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
    <title>添加字典数据页面</title> 
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/edit.css'/>">
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>

</head>
<body>
    <jsp:include page="/common/Head.jsp" />
	<div id="divMain">
		<div id="divTitle">
			<span id="spanTitle">当前位置：基础数据管理&gt;&gt;添加字典数据</span>
		</div>
		<form name="form1" action="" method="post" id="form">
		<div id="divBody">
			<table id="tableForm">
				<tr>
					<td class="tdText">字典大类:</td>
					<td class="tdInput">
						<select class="inputClass" id="typeCode" name="typeCode" onchange="bindMemo()" style="width: 240px;height:32px; border:1px solid #7f9db9;">
	           				<option value="">=请选择=</option>
	           				<c:forEach items= "${kindSet }" var="y">
	           					<option value="${y.typeCode }">${y.memo }</option>
	           				</c:forEach>
	           			</select> 
					</td>
					<td class="tdError">
						<label class="errorClass" id="typeCodeError"></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">小类序号:</td>
					<td>
						<input class="inputClass" type="text" name="typeSerialNo" id="typeSerialNo" placeholder="类别代码不能为空!" />
					</td>
					<td class="tdError">
						<label class="errorClass" id="typeSerialNoError"></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">小类描述:</td>
					<td>
						<input class="inputClass" type="text" name="typeDesc" id="typeDesc">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">备注:</td>
					<td>
						<input class="inputClass" type="text" name="remark" id="remark">
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
		<input type="hidden" id="memo" name="memo" value="">
		</form>
	</div>
    <jsp:include  page="/common/Foot.jsp"/>
</body>
<script type="text/javascript">
	/*添加页面表单校验JS文件*/
	//文件加载完毕之后执行
	$(function() {
		/*
		 * 1.得到所有的错误信息，循环遍历他，调用方法确定是否显示错误信息
		 */
		$(".errorClass").each(function() {
			$(this).text("");//把label的内容清空
			showError($(this));//遍历每一个元素，调用showError()方法
		});
		/*
		 * 2.输入框得到焦点,隐藏错误信息
		 */
		$(".inputClass").focus(function() {
			var lableId = $(this).prop("id") + "Error";
			$("#" + lableId).text("");//把label的内容清空
			showError($("#" + lableId));
		});
		/*
		 * 3.输入框失去焦点时进行校验
		 */
		$(".inputClass").blur(
				function() {
					var id = $(this).prop("id");
					//alert($(this).prop("id"));
					//得到对应的校验函数
					var funName = "validate" + id.substring(0, 1).toUpperCase()
							+ id.substring(1) + "()";
					//把对应的字符串当成对应的函数方法来执行
					eval(funName);
				});

	});

	//判断当前元素，是否显示错误信息的function
	function showError(ele) {
		var text = ele.text();
		if (!text) {
			ele.css("display", "none");
		} else {
			ele.css("display", "");
		}
	}

	//类别代码校验方法
	function validateTypeCode() {
		/*
		 * 1.非空校验
		 */
		var id = "typeCode";
		var value = $("#" + id).val();
		if (!value) {
			//获取对应的label,显示错误信息
			$("#" + id + "Error").text("类别代码不能为空!");
			showError($("#" + id + "Error"));
			return false;
		}
		/*
		 * 2.长度校验
		 */
		if (value.length > 20) {
			//获取对应的label,显示错误信息
			$("#" + id + "Error").text("请输入20位以内的类别代码");
			showError($("#" + id + "Error"));
			return false;
		}
		return true;
	}

	//类别序号校验方法
	function validateTypeSerialNo() {
		/*
		 * 1.非空校验
		 */
		var id = "typeSerialNo";
		var value = $("#" + id).val();
		if (!value) {
			//获取对应的label,显示错误信息
			$("#" + id + "Error").text("类别序号不能为空!");
			showError($("#" + id + "Error"));
			return false;
		}
		/*
		 * 2.长度校验
		 */
		if (value.length > 20) {
			//获取对应的label,显示错误信息
			$("#" + id + "Error").text("请输入20位以内的区域名称!");
			showError($("#" + id + "Error"));
			return false;
		}
		return true;

	}

	//提交时，校验表单
	function doSubmit() {
		//校验表单,出错不让点击保存

		var verifyResult1 = validateTypeCode();
		var verifyResult2 = validateTypeSerialNo();
		//账号校验
		if (verifyResult1 && verifyResult2) {
			//提交表单
			document.forms[0].action = "/dc/dic/addDic.do";
			document.forms[0].submit();
		} else {
			return confirm("请核对您录入的信息!");
		}
	};
	
	//绑定备注信息
	function bindMemo(){
		var temp = $("#typeCode  option:selected").text();
		$("#memo").val(temp);
	}
</script>
</html>