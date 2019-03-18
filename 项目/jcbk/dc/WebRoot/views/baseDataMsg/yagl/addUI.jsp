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
    <title>添加预案参数页面</title> 
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/edit.css'/>">
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script  src="<%=basePath%>common/js/layer/layer.js"></script>
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
			<span id="spanTitle">新增预案参数页面</span>
		</div>
		<form name="form1" action="" method="post">
		<div id="divBody">
			<table id="tableForm">
				<tr>
					<td class="tdText">预案名称:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="yamc" value="${yacs.yamc }" id="yaName" placeholder="预案名称不能为空！">
					</td>
					<td class="tdError">
						<label class="errorClass" id="yaNameError"></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">预案描述:</td>
					<td>
						<input class="inputClass" type="text" name="yams" value="${yacs.yams }" id="yaDesc" placeholder="预案描述不能为空！">
					</td>
					<td class="tdError">
						<label class="errorClass" id="yaDescError"></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">预案大类:</td>
					<td>
						<select class="inputClass" id= "Check_Yazl" name="yazl" style="width: 240px;height:32px; border:1px solid #7f9db9;">
	           				<option value="">=请选择=</option>
	           				<c:forEach items= "${Ya_kinds }" var="y">
	           					<option value="${y.typeSerialNo }" <c:if test="${y.typeSerialNo eq Check_Yazl }">selected="selected"</c:if> >${y.typeDesc }</option>
	           				</c:forEach>
	           			</select> 
					</td>
					<td class="tdError">
						<label class="errorClass" id="Check_YazlError">请选择预案大类!</label>
					</td>
				</tr>
				<tr>
					<td class="tdText">预案类别:</td>
					<td>
						<select class="inputClass" id="Check_Yadj" name="yadj" style="width: 240px;height:32px; border:1px solid #7f9db9;">
	           				<option value="">=请选择=</option>
	           			</select> 
					</td>
					<td class="tdError">
						<label class="errorClass" id="Check_YadjError">请选择预案等级!</label>
					</td>
				</tr>
				<tr>
					<td class="tdText">报警类型:</td>
					<td>
						<select class="inputClass" id="Check_Bjlx" name="bjlx" style="width: 240px;height:32px; border:1px solid #7f9db9;">
	           				<option value="">=请选择=</option>
	           			</select> 
					</td>
					<td class="tdError">
						<label class="errorClass" id="Check_BjlxError"></label>
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

	var message = '${message}';
	var yalb = $.parseJSON('${Ya_lbs}');//获取预案类别
	var bjlx = $.parseJSON('${list_dicList_json}');

	//文档加载完之后执行
	$(function(){
		//返回错误信息
		if(typeof(message)!=undefined&&message!=''){
			layer.confirm(
				message,{btn:'确定'}
			);
		}
		//根据返回的布控大类，加载相应的预案类别和报警类型
		var zl = $("#Check_Yazl").val();
		if(typeof(zl) != undefined){
			//清空 select下的option
			$("#Check_Yadj option[class='newOption']").remove();
			var $yalb = $("#Check_Yadj");
			for(var y in yalb){
				if(yalb[y].SUPERID==zl){
					$yalb.append("<option class='newOption' value='"+yalb[y].ID+"'>"+yalb[y].NAME+"</option>");
				}
			}
			//清空select下的option
			$("#Check_Bjlx option[class='newOption']").remove();
			//加载报警类型
			$bjlx = $("#Check_Bjlx");
			if(zl=='1'){
				for(var b in bjlx){
					if(bjlx[b].typeCode=='BKDL1'){
						$bjlx.append("<option class='newOption' value='"+bjlx[b].typeSerialNo+"'>"+bjlx[b].typeDesc+"</option>");
					}
				}
			}else if(zl=='2'){
				for(var b in bjlx){
					if(bjlx[b].typeCode=='BKDL2'){
						$bjlx.append("<option class='newOption' value='"+bjlx[b].typeSerialNo+"'>"+bjlx[b].typeDesc+"</option>");
					}
				}
			}else if(zl=='3'){
				for(var b in bjlx){
					if(bjlx[b].typeCode=='BKDL3'){
						$bjlx.append("<option class='newOption' value='"+bjlx[b].typeSerialNo+"'>"+bjlx[b].typeDesc+"</option>");
					}
				}		
			}
		}
	});

	//点击预案大类，加载相应的预案类别，以及报警类型
	$("#Check_Yazl").on("click",function(){
		var zl = $("#Check_Yazl").val();
		//清空 select下的option
		var $yalb = $("#Check_Yadj");
		$("#Check_Yadj option[class='newOption']").remove();
		for(var y in yalb){
			if(yalb[y].SUPERID==zl){
				$yalb.append("<option class='newOption' value='"+yalb[y].ID+"'>"+yalb[y].NAME+"</option>");
			}
		}
		//清空select下的option
		$("#Check_Bjlx option[class='newOption']").remove();
		//加载报警类型
		$bjlx = $("#Check_Bjlx");
		if(zl=='1'){
			for(var b in bjlx){
				if(bjlx[b].typeCode=='BKDL1'){
					$bjlx.append("<option class='newOption' value='"+bjlx[b].typeSerialNo+"'>"+bjlx[b].typeDesc+"</option>");
				}
			}
		}else if(zl=='2'){
			for(var b in bjlx){
				if(bjlx[b].typeCode=='BKDL2'){
					$bjlx.append("<option class='newOption' value='"+bjlx[b].typeSerialNo+"'>"+bjlx[b].typeDesc+"</option>");
				}
			}
		}else if(zl=='3'){
			for(var b in bjlx){
				if(bjlx[b].typeCode=='BKDL3'){
					$bjlx.append("<option class='newOption' value='"+bjlx[b].typeSerialNo+"'>"+bjlx[b].typeDesc+"</option>");
				}
			}		
		}
	});
	//文件加载完毕之后执行
	$(function(){
		//遍历所有的错误label，隐藏错误信息
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
	
	//预案名称校验方法
	function validateYaName(){
		/*
		 * 1.非空校验
		 */
		var id="yaName";
		var value = $("#"+id).val();
		if(!value){
			//获取对应的label,显示错误信息
			$("#" +id +"Error").text("预案名称不能为空!");
			showError($("#" +id +"Error"));
			return false;
		}
		return true;
	}
	
	//预案描述校验方法
	function validateYaDesc(){
		//非空校验
		var id="yaDesc";
		var value = $("#"+id).val();
		if(!value){
			//获取对应的label,显示错误信息
			$("#" +id +"Error").text("预案描述不能为空!");
			showError($("#" +id +"Error"));
			return false;
		}
		return true;
		
	}
	//校验预案种类不能为空
	function validateCheck_Yazl(){
		var id = "Check_Yazl";
		var value = $("#"+id).val();
		if(!value){
			//获取对应的label显示错误信息
			$("#" +id +"Error").text("预案种类不能为空!");
			showError($("#" +id +"Error"));
			return false;
		}
		return true;
	}
	//校验预案等级不能为空
	function validateCheck_Yadj(){
		var id = "Check_Yadj";
		var value = $("#"+id).val();
		if(!value){
			//获取对应的label显示错误信息
			$("#" +id +"Error").text("预案等级不能为空!");
			showError($("#" +id +"Error"));
			return false;
		}
		return true;
	}
	//校验报警类型
	function validateCheck_Bjlx(){
		var id = "Check_Bjlx";
		var value = $("#"+id).val();
		if(!value){
			//获取对应的label显示错误信息
			$("#" +id +"Error").text("报警类型不能为空!");
			showError($("#" +id +"Error"));
			return false;
		}
		return true;
	}
	//提交时，校验表单
	function doSubmit(){
		//校验表单,出错不让点击保存
		var verifyResult1 = validateYaName();
		var verifyResult2 = validateYaDesc();
		var verifyResult3 = validateCheck_Yazl();
		var verifyResult4 = validateCheck_Yadj();
		var verifyResult5 = validateCheck_Bjlx();
		//账号校验
		if(verifyResult1 && verifyResult2 && verifyResult3 && verifyResult4&&verifyResult5){
			//提交表单
			document.forms[0].action="yagl/addYa.do";
			document.forms[0].submit();
		}else{
			return confirm("请核对您录入的信息!");
		}
	}
</script>

</html>