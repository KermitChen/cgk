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
    <title>编辑字典数据页面</title> 
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/edit.css'/>">
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
</head>
<body>
    <jsp:include page="/common/Head.jsp" />
	<div id="divMain">
		<div id="divTitle">
			<span id="spanTitle">当前位置：基础数据管理&gt;&gt;编辑字典数据</span>
		</div>
		<form name="form" action="" method="post" id="form">
		<div id="divBody">
			<table id="tableForm">
				<tr>
					<td class="tdText">大类编号:</td>
					<td class="tdInput">
						<input class="inputClass" readonly="readonly" type="text" name="typeCode" id="typeCode" value="${dic.typeCode }">
					</td>
					<td class="tdError">
						<label class="errorClass" id="typeCodeError"></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">大类描述:</td>
					<td>
						<input class="inputClass" type="text" name="memo" id="memo" value="${dic.memo }">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">小类编号:</td>
					<td>
						<input class="inputClass" readonly="readonly" type="text" name="typeSerialNo" id="typeSerialNo" value="${dic.typeSerialNo }">
					</td>
					<td class="tdError">
						<label class="errorClass" id="typeSerialNoError" ></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">小类描述:</td>
					<td>
						<input class="inputClass" type="text" name="typeDesc" id="typeDesc" value="${dic.typeDesc }">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">备注:</td>
					<td>
						<input class="inputClass" type="text" name="remark" id="remark" value="${dic.remark }">
					</td>
					<td>
						<label></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">是否可编辑:</td>
					<td colspan="2">
						<c:if test="${dic.editFlag eq '1' }">
							<div style="text-align: left;">
								&nbsp;<input style="width: 8px" class="inputClass" type="radio" name="editFlag" id="editFlag" value="1" checked="checked">&nbsp;可编辑
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input style="width: 8px" class="inputClass" type="radio" name="editFlag" id="editFlag" value="0">&nbsp;不可编辑
							</div>
						</c:if>
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
		<input type="hidden" name="dictionaryid" value="${ dic.dictionaryid}">
		<input type="hidden" name="Check_kind" value="${ Check_kind}">
		<input type="hidden" name="Check_code" value="${ Check_code}">
		<input type="hidden" name="pageNo" value="${ pageNo}">
		</form>
	</div>
    <jsp:include  page="/common/Foot.jsp"/>
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
    <script type="text/javascript">
    	/*添加页面表单校验JS文件*/
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
				//得到对应的校验函数
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

		
		//类别代码校验方法
		function validateTypeCode(){
			/*
			 * 1.非空校验
			 */
			var id="typeCode";
			var value = $("#"+id).val();
			if(!value){
				//输入框中没有值,给错误label中添加错误信息，调用是否显示错误信息的方法
				$("#" +id +"Error").text("类别代码不能为空!");
				showError($("#" +id +"Error"));
				return false;
			}else{
				//输入框中有值，清空错误label中的错误信息，调用是否显示错误信息的方法
				$("#" +id +"Error").text("");
				showError($("#" +id +"Error"));
				return true;
			}
			/*
			 * 2.长度校验
			 */
			if(value.length>10){
				//输入框中的长度不合规范，label中添加错误信息，调用是否显示错误信息的方法
				$("#" +id +"Error").text("请输入10位以内的类别代码");
				showError($("#" +id +"Error"));
				return false;
			}else{
				//符合要求,清空错误信息,调用是否显示错误信息的方法
				$("#" +id +"Error").text("");
				showError($("#" +id +"Error"));	
				return true;			
			}
			return true;
		}
		
		//类别序号校验方法
		function validateTypeSerialNo(){
			/*
			 * 1.非空校验
			 */
			var id="typeSerialNo";
			var value = $("#"+id).val();
			if(!value){
				//获取对应的label,显示错误信息
				$("#" +id +"Error").text("类别序号不能为空!");
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
				//长度不规范，错误label中添加错误信息，调用是否显示错误信息的方法
				$("#" +id +"Error").text("请输入10位以内的区域名称!");
				showError($("#" +id +"Error"));
				return false;
			}else{
				//反之
				$("#" +id +"Error").text("");
				showError($("#" +id +"Error"));
				return true;				
			}
			return true;
			
		}
		
		//提交时，校验表单
		function doSubmit(){
			//校验表单,出错不让点击保存
			/*
			 * 1.得到所有的错误信息，循环遍历他，调用方法确定是否显示错误信息
			 */
			$(".errorClass").each(function (){
				showError($(this));//遍历每一个元素，调用showError()方法
			});
			 
			var verifyResult1 = validateTypeCode();
			var verifyResult2= validateTypeSerialNo();
			//账号校验
			if(verifyResult1 && verifyResult2){
				//提交表单
				document.forms[0].action="dic/updateDic.do";
				document.forms[0].submit();
			}else{
				return confirm("请核对您录入的信息!");
			}
		}	
    	
    </script>
</html>