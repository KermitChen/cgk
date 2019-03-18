<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>布控撤控签收</title>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<style type="text/css">
		.button_blue{
			padding: 6px 18px;
		    border: none;
		    color: #fff;
		    background: url("${pageContext.request.contextPath}/common/images/submit_b.jpg") center no-repeat;
		    border-radius: 6px;
		    outline: none;
		    cursor: pointer;
		    margin: 4px 10px;
		    border: 1px solid #fff;
		}
		.button_blue:hover{
			color: #555;
			background: url("${pageContext.request.contextPath}/common/images/submit_g.jpg") center no-repeat;
		}
		input{
			border-style:none;
			readonly:readonly;
			color:blue;
		}
		span{
			color:blue;
			font-size:11pt;
			font-family:"Microsoft YaHei";
		}
		td{
			align:left;
			font-size:11pt;
			font-family:"Microsoft YaHei";
		}
		th{
			align:center;
		}
		.button_div{
			display: block;
		    float: left;
		    height: 40px;
		    margin-top: 8px;
		    position: relative;
		    width: 100%;
		    text-align: center;
		}
		.slider_body_textarea{
			margin: 15px 10px 0 10px;
		    position: relative;
		    display: block;
		    float: left;
		    width: 800px;
		    height: 100px; 
		}
		.slider_selected_left{ 
			float: left;
			padding: 0 5px;
		}
		.slider_selected_right{ 
			float: right;
			width: 204px;
			height: auto;
			position: relative;
			/* z-index: 1; */
			background: transparent;
			position: absolute;
			left: 115px;
		}
	</style>
</head>
<body>
	<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">
					<c:if test="${disReceive.bkckbz eq '1' }">布控通知</c:if>
					<c:if test="${disReceive.bkckbz eq '2' }">撤控通知</c:if>
				</legend>
			<table width="100%">
				<tr>
					<td>
						号牌号码:&nbsp;&nbsp;<span>${disReceive.hphm }</span>
					</td>
					<td>
						号牌种类:&nbsp;&nbsp;<span>
							<c:forEach items= "${dicList}" var="dic">
		            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
					            	<c:if test="${dic.typeSerialNo eq disReceive.hpzl }">
						                		${dic.typeDesc }
						            </c:if>
					      	  	</c:if> 
							</c:forEach>
						</span>
					</td>
					<td>
						布控类别:&nbsp;&nbsp;<span>
							<c:forEach items= "${bklb}" var="b">
					           	  		<c:if test="${b.ID eq disReceive.bklb }">
						                   		${b.NAME }
					          	  		</c:if> 
							</c:forEach>
						</span>
					</td>
				</tr>
				<tr>
					<td>
						下发通知人:&nbsp;&nbsp;<span>${disReceive.tzr }</span>
					</td>
					<td>
						下发通知单位:&nbsp;&nbsp;
						<span>
							${disReceive.xfdwmc }
						</span>
					</td>
					<td>
						下发通知时间:&nbsp;&nbsp;<span>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${disReceive.xfsj }" />
						</span>
					</td>
				</tr>
				<c:if test="${disReceive.bkckbz eq '1' }">
					<tr>
						<td colspan="3">
							简要案情:&nbsp;&nbsp;<span>${dispatched.jyaq }</span>
						</td>
					</tr>
				</c:if>
				<c:if test="${disReceive.bkckbz eq '2' }">
					<tr>
						<td colspan="3">
							撤控原因:&nbsp;&nbsp;<span>${withdraw.ckyyms }</span>
						</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="3">
						通知内容:&nbsp;&nbsp;<span>${disReceive.tznr }</span>
					</td>
				</tr>
			</table>
		</fieldset>
			<div class="slider_body_textarea" style="height:80px;">
				<div class="slider_selected_left">
					确认结果简述<span style="color: red">*</span>：
				</div>
				<div class="slider_selected_right">
					<textarea name="qrjg" id="qrjg" rows="2" style="width:700px"></textarea>
				</div>
			</div>
			<div class="button_div">
				<input type="button" class="button_blue" value="签收" onclick="sign()">
				<input type="button" class="button_blue" value="返回" onclick="closeLayer()">
			</div>
</body>

<script type="text/javascript">
	$(function(){

	});
	function sign(){
		if($("#qrjg").val() == ""){
			alert("请输入确认结果");
			return;
		}
		$.ajax({
		    url: '${pageContext.request.contextPath}/dispatched/dealDisReceive.do',
		    data: {id:'${disReceive.id }',qrjg:$("#qrjg").val()},
		    dataType: "json",
		    type: "POST",   //请求方式
		    success: function(data) {
		    	data == "success" ? parent.layer.msg("签收成功"):alert("该通知已被其他人员签收确认");
		    	parent.doSearch();
		    	closeLayer();
		    }
		});
	}
	function closeLayer() {
		//获取窗口索引关闭窗口
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
</script>
</html>