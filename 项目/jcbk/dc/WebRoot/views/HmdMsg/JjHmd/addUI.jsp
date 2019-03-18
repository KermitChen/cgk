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
    <title>添加红名单页面</title> 
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/edit.css'/>">
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
</head>
<body>
    <jsp:include page="/common/Head.jsp"/>
	<div id="divMain">
		<div id="divTitle" style="width: 880px;">
			<span id="spanTitle">新增红名单申请</span>
		</div>
		<form name="form1" action="" method="post">
		<div id="divBody">
			<table id="tableForm">
				<tr>
					<td class="tdText"><span style="color: red;">*</span>车牌号码:</td>
					<td class="tdInput" style="text-align: center;">
						<input class="inputClass need" type="text" name="cphid" id="cphid" style="width:220px ;vertical-align:middle;" >
						<a><img alt="车牌" src="${pageContext.request.contextPath}/common/images/cplr_1.png" width="25px" height="25px" style="vertical-align:middle;"
							onclick="doCplrUI()"></a>
					</td>
					<td class="tdError">
						<label class="errorClass" id="cphidError"></label>
					</td>
				</tr>
				<tr>
					<td class="tdText"><span style="color: red;">*</span>车牌类型:</td>
					<td>
						<select class="inputClass need" id="cplx" name="cplx">
							<option value="">=请选择=</option>
							<c:forEach items="${cpysList }" var="c">
								<option value="${c.typeSerialNo }">${c.typeDesc }</option>
							</c:forEach>
						</select>
					</td>
					<td class="tdError">
						<label class="errorClass" id="cplxError"></label>
					</td>
				</tr>
			
				<tr>
					<td class="tdText">车辆使用人:</td>
					<td>
						<input class="inputClass" type="text" name="clsyz" id="clsyz">
					</td>
					<td class="tdError">
						<label class="errorClass" id="clsyzError"></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">车辆所有人:</td>
					<td>
						<input class="inputClass" type="text" name="cz" id="cz">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">车标:</td>
					<td>
						<input class="inputClass" type="text" name="cb" id="cb">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">车身颜色:</td>
					<td>
						<input class="inputClass" type="text" name="csys" id="csys">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">列入原因:</td>
					<td>
						<input class="inputClass" type="text" name="lryy" id="lryy">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">备注:</td>
					<td>
						<input class="inputClass" type="text" name="bzsm" id="bzsm">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
			</table>
			<div style="text-align: center">
				<input type="button" class="button_blue" value="确定" onclick="doSubmit()">
				<input type="button" class="button_blue" value="返回" onclick="history.go(-1)">
			</div>
		</div>
		</form>
	</div>
    <jsp:include  page="/common/Foot.jsp"/>
</body>
<script  src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript">
//文档加载完毕之后执行
$(function(){
	//遍历所有的错误label，隐藏错误信息
	$(".errorClass").each(function (){
		$(this).text("");//把label的内容清空
		showError($(this));//遍历每一个元素，调用showError()方法
	});
	//输入框得到焦点 隐藏错误信息
	$(".inputClass").focus(function(){
		var lableId = $(this).prop("id")+"Error";
		$("#"+lableId).text("");//把label的内容清空
		showError($("#"+lableId));
	});
	//输入框失去焦点时进行校验
	$(".need").blur(function (){
		var id = $(this).prop("id");
		//得到对应的校验函数 例如：validateRoadName();
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
	//车辆使用者不能为空的校验方法
/* 	function validateClsyz(){
		var id="clsyz";
		var value = $("#"+id).val();
		if(!value){
			//获取对应的label,显示错误信息
			$("#" +id +"Error").text("请您填写车辆使用者!");
			showError($("#" +id +"Error"));
			return false;
		}
	} */
	//车牌号不能为空的而检验方法
	function validateCphid(){
		var id="cphid";
		var value = $("#"+id).val();
		if(!value){
			//获取对应的label,显示错误信息
			$("#" +id +"Error").text("请您填写车牌号码!");
			showError($("#" +id +"Error"));
			return false;
		}
		return true;
	}
	//车牌类型不能为空的而检验方法
	function validateCplx(){
		var id="cplx";
		var value = $("#"+id).val();
		if(!value){
			//获取对应的label,显示错误信息
			$("#" +id +"Error").text("请您填写车牌类型!");
			showError($("#" +id +"Error"));
			return false;
		}
		return true;
	}
	//提交时，校验表单
	var isCommitted = false;//表单是否已经提交标识，默认为false
	function doSubmit(){
		//校验表单,出错不让点击保存
		var verifyResult1 = validateCphid();
		var verifyResult2= validateCplx();
		//账号校验
		if(verifyResult1 && verifyResult2){
			//提交表单
			if(isCommitted==false){
				document.forms[0].action="${pageContext.request.contextPath}/JjHmd/saveJJhomd.do";
				document.forms[0].submit();
				isCommitted = true;//提交表单后，将表单是否已经提交标识设置为true
                return true;//返回true让表单正常提交
			}else{
				return false;//返回false那么表单将不提交
			}
		}else{
			layer.msg("请您录入必填项的信息!");
		}
	}

</script>
</html>