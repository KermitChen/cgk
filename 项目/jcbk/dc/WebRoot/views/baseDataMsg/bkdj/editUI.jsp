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
    <title>修改布控等级</title> 
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/edit.css'/>">
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
</head>
<body>
    <jsp:include page="/common/Head.jsp" />
	<div id="divMain">
		<div id="divTitle">
			<span id="spanTitle">修改布控等级</span>
		</div>
		<form name="form1" action="" method="post">
		<input type="hidden" id="id" value="${type.id }">
		<div id="divBody">
			<table id="tableForm">
				<tr>
					<td class="tdText">类别名称:</td>
					<td class="tdInput">
						<input class="inputClass" type="text" name="name" id="name" value="${type.name }"/>
					</td>
					<td class="tdError">
						<label class="errorClass" id="nameError"></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">布控大类:</td>
					<td>
						<select class="inputClass" id="superid" name="superid" style="width: 240px;height:32px; border:1px solid #7f9db9;">
							<c:forEach items="${bkdlList }" var="s">
								<c:choose>
									<c:when test="${type.superid eq s.typeSerialNo }">
										<option value="${s.typeSerialNo }" selected="selected">${s.typeDesc }</option>
									</c:when>
									<c:otherwise>
										<option value="${s.typeSerialNo }">${s.typeDesc }</option>
									</c:otherwise>
								</c:choose>
		                	</c:forEach>
	           			</select>
					</td>
					<td class="tdError">
						<label class="errorClass" id="superidError"></label>
					</td>
				</tr>
			
				<tr>
					<td class="tdText">显示顺序:</td>
					<td>
						<input class="inputClass" type="text" name="showOrder" id="showOrder" value="${type.showOrder }"/>
					</td>
					<td class="tdError">
						<label class="errorClass" id="showOrderError"></label>
					</td>
				</tr>
				<tr>
					<td class="tdText">级别:</td>
					<td>
						<input class="inputClass" type="text" name="level" id="level" value="${type.level }">
					</td>
					<td class="tdError">
						<label class="errorClass" id="levelError"></label>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
				        <div class="button_wrap clear_both">
					    	<input type="button" class="button_blue" value="修改" onclick="doSubmit()">
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
			$(".errorClass").each(function() {
				$(this).text("");//把label的内容清空
				showError($(this));//遍历每一个元素，调用showError()方法
			});
			$("#id").change(function(){
				validateId();
				showError($("#idError"));
			});
			$("#name").change(function(){
				validateName();
				showError($("#nameError"));
			});
			$("#showOrder").change(function(){
				validateShowOrder();
				showError($("#showOrderError"));
			});
			$("#level").change(function(){
				validateLevel();
				showError($("#levelError"));
			});
		});
		
		
		function validateName(){
			var str = $.trim($("#name").val());
			if(str == null || str == ""){
				$("#nameError").text("名称不可为空!");
				return false;
			}
			if(str.length > 50){
				$("#nameError").text("长度不可大于50!");
				return false;
			}
			$("#nameError").text("");
			return true;
		}
		function validateShowOrder(){
			var str = $.trim($("#showOrder").val());
			var id = $.trim($("#id").val());
			if(str == null || str == ""){
				$("#showOrderError").text("显示顺序不可为空!");
				return false;
			}
			var reg = /^\d{1,2}$/;
			console.log(reg.test(str));
			if(!reg.test(str)){
				$("#showOrderError").text("请输入1-2位数字!");
				return false;
			}
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];  
			var flag;
			$.ajax({
				async:false,
				type: 'POST',
				data:{showOrder:str,notId:id},
				dataType : "json",
				url: basePath + "/bkdj/isHave.do",//请求的action路径
				error: function () {//请求失败处理函数
					$("#showOrderError").text("查询是否存在失败!");
					flag = false;
				},
				success:function(data){ //请求成功后处理函数。
					console.log(data);
					if(data.res == null || data.res == "" || data.res == '1'){
						$("#showOrder").val("");
						$("#showOrderError").text("显示顺序已存在,请重新输入!");
						flag = false;
					}else if(data.res == '0'){
						$("#showOrderError").text("");
						flag = true;
					}
				}
			});
			return flag;
		}
		function validateLevel(){
			var id = $.trim($("#id").val());
			var str = $.trim($("#level").val());
			if(str == null || str == ""){
				$("#levelError").text("等级不可为空!");
				return false;
			}
			var reg = /^\d{2}$/;
			if(!reg.test(str)){
				$("#levelError").text("请输入两位数字!");
				return false;
			}
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			var flag;
			$.ajax({
				async:false,
				type: 'POST',
				data:{level:str,notId:id},
				dataType : "json",
				url: basePath + "/bkdj/isHave.do",//请求的action路径
				error: function () {//请求失败处理函数
					$("#levelError").text("查询是否存在失败!");
					flag = false;
				},
				success:function(data){ //请求成功后处理函数。
					console.log(data);
					if(data.res == null || data.res == "" || data.res == '1'){
						$("#levelError").val("");
						$("#levelError").text("等级已存在,请重新输入!");
						$("#level").val('');
						flag = false;
					}else if(data.res == '0'){
						$("#levelError").text("");
						flag = true;
					}
				}
			});
			return flag;
		}
		//判断当前元素，是否显示错误信息的function
		function showError(ele){
			var text = ele.text();
			if(!text){
				ele.css("display","none");
			}else{
				ele.css("display","");
			}
		}
		//提交时，校验表单
		function doSubmit(){
			var id = $.trim($("#id").val());
			var name = $.trim($("#name").val());
			var showOrder = $.trim($("#showOrder").val());
			var superid = $.trim($("#superid").val());
			var level = $.trim($("#level").val());
			if(!validateName() || !validateShowOrder() || !validateLevel()){
				return;
			}
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			layer.load();
			$.ajax({
				type: 'POST',
				data:{id:id,name:name,showOrder:showOrder,superid:superid,level:level},
				dataType : "json",
				url: basePath + "/bkdj/update.do",//请求的action路径
				error: function () {//请求失败处理函数
					layer.closeAll('loading');
					layer.msg('修改失败!');					
				},
				success:function(data){ //请求成功后处理函数。
					layer.closeAll('loading');
					console.log(data);
					if(data.res == null || data.res == "" || data.res == '0'){
						layer.msg('修改失败!');
					}else if(data.res == '1'){
						layer.msg('修改成功!');
						$("#name").val("");
						$("#showOrder").val("");
						$("#superid").val("");
						$("#level").val("");
					}
				}
			});
		}
    </script>
</html>