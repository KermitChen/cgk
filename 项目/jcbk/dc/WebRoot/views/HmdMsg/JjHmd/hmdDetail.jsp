<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <title>红名单详情页面</title> 
	<%-- <link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/hmdDetail.css'/>"> --%>
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/jcd/jcdChoose.css'/>">
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
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
		table{
			width: 99%;
		}
	</style>
</head>
<body>
	<div id="divMain">
		<div id="divbody">
		
			<div class="container">
				<ul class="tabs">
					<li class="active">
						<a href="#tab1" onclick="">订阅详情信息</a>
					</li>
					<li>
						<a href="#tab4" >审批流程记录</a>
					</li>
				</ul>
				<div class="tab_container">
				
					<div id="tab1" class="tab_content" style="display: block; ">
							<table id="tableForm">
								<tr>
									<td class="tdText">车牌号码:</td>
									<td class="tdInput">
										<input class="inputClass" type="text" id="areaNo" value="${hmdDetail.cphid }">
									</td>
									<td class="tdText">车牌颜色:</td>
									<td class="tdInput">
										<input class="inputClass" type="text" id="cplx" value="">
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
										<input class="inputClass" type="text" name="superAreaNo" id="superAreaNo" value="${hmdDetail.sqrdwmc }">
									</td>
									<td class="tdText">申请时间:</td>
									<td class="tdInput">
										<input class="inputClass" type="text" name="superAreaNo" id="superAreaNo" value="${hmdDetail.lrsj }">
									</td>
								</tr>
								
								<tr>
									<td class="tdText">列入原因:</td>
									<td class="tdInput" colspan="3">
										<input class="MaxinputClass" type="text" id="superAreaNo" value="${hmdDetail.lryy }">
									</td>
								</tr>
								
								<tr>
									<td class="tdText">备注:</td>
									<td class="tdInput" colspan="3">
										<input class="MaxinputClass" type="text" name="superAreaNo" id="superAreaNo" value="${hmdDetail.bzsm }">
									</td>
								</tr>
							</table>
							<div class="button_wrap clear_both" style="text-align: center;">
								<input type="button" class="button_blue" value="关闭" onclick="doClose()">
							</div>
					</div>
					
					<div id="tab4" class="tab_content" style="display: none; ">
						<c:if test="${!empty commentList }">
				        	<table border="1" cellpadding="0" cellspacing="0" width="100%">
								<thead>
									<tr>
										<td width="30%" align="center">时间</td>
										<td width="30%" align="center">审批人</td>
										<td width="40%" align="center">批注信息</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="c" items="${commentList }" varStatus="status">
										<tr>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${c.time}" /></td>
											<td>${c.userId}</td>
											<td>${c.message }</td>
										</tr>
									</c:forEach>
									<c:forEach var="c2" items="${commentList2 }" varStatus="status">
										<tr>
											<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${c2.time}" /></td>
											<td>${c2.userId}</td>
											<td>${c2.message }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
				        </c:if>
					</div>
					
				</div>
				</div>
			</div>
			
		</div>
	</div>

</body>
	<script type="text/javascript">
	
	//文档加载时
	$(function(){
		tabChange();
	});
	
	//关闭弹出层
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	function doClose(){
		parent.layer.close(index);
	}
	
	//选项卡切换
	function tabChange(){
		//load Tab
		//Default Action  
		$(".tab_content").hide(); //Hide all content  
		$("ul.tabs li:first").addClass("active").show(); //Activate first tab  
		$(".tab_content:first").show(); //Show first tab content  

		//On Click Event  
		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active"); //Remove any "active" class  
			$(this).addClass("active"); //Add "active" class to selected tab  
			$(".tab_content").hide(); //Hide all tab content  
			var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content  
			$(activeTab).fadeIn(); //Fade in the active content  
			return false;
			});
	}
	
	</script>
</html>