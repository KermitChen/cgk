<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取数据
	//Announcement Ann = (Announcement)request.getAttribute("Ann");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>系统公告编辑页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-系统公告编辑页面">
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
	</head>
	<body>
		<div id="content" style="width: 800px;margin:0 auto;">
			<div id="content_wrap" style="width: 750px;height: 500px;">
				<form action="" id="formEdit">
			        <div class="slider_body">
			        	<div class="slider_selected_left">
			            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>公告标题：</span>
			        	</div>
			        	<div class="slider_selected_right" style="">
			        		<div class="img_wrap">
			        			<div class="select_wrap select_input_wrap">
			                    	<input id="fileName" name="fileName" type="text" class="slider_input" value="<c:if test="${not empty Ann}">${Ann.fileName}</c:if>" >
			                    	<a id="fileName" class="empty"></a>
			                    </div>
			                </div>  
			            </div>
			        </div>
			        
			        <div class="slider_body"> 
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>公告类型：</span>
			            </div>
						<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
				            <input class="input_select xiala" id="annType1" type="text" value="==全部==">
				            <input type="hidden" name="annType" id="annType2" value="${d.typeSerialNo }" />
				            <div class="ul"> 
				            	  <div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
				            	  <c:forEach items= "${annType}" var="d">
					            	  <c:if test="${d.typeSerialNo eq Ann.annType }">
					            	   	<script>
					            		 	$("#annType1").val("${d.typeDesc }");
					            		 	$("#annType2").val("${d.typeSerialNo }");
					            	   	</script>
					            	  </c:if> 
				                    <div class="li" data-value="${d.typeSerialNo }" onclick="sliders(this)"><a rel="2">${d.typeDesc}</a></div> 
								 </c:forEach>
				            </div>
			        	</div>
			        </div>
			        
			        <div class="slider_body">
			        	<div class="slider_selected_left">
			            	<span><span style="color: red;visibility: hidden;">*</span>上传人用户名：</span>
			        	</div>
			        	<div class="slider_selected_right" style="">
			        		<div class="img_wrap">
			        			<div class="select_wrap select_input_wrap">
			                    	<input id="buildPno" name="buildPno" type="text" class="slider_input" value="<c:if test="${not empty Ann}">${Ann.buildPno}</c:if>" readonly="readonly">
			                    </div>
			                </div>  
			            </div>
			        </div>
			        
			        <div class="slider_body">
			        	<div class="slider_selected_left">
			            	<span>&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>上传人姓名：</span>
			        	</div>
			        	<div class="slider_selected_right" style="">
			        		<div class="img_wrap">
			        			<div class="select_wrap select_input_wrap">
			                    	<input id="buildName" name="buildName" type="text" class="slider_input" value="<c:if test="${not empty Ann}">${Ann.buildName}</c:if>" readonly="readonly">
			                    </div>
			                </div>  
			            </div>
			        </div>
			        
			        <div class="slider_body_textarea" style="height: 85px;position: relative; clear: both;">
			        	<div class="slider_selected_left">
			            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>文档概要：</span>
			        	</div>
			        	<div class="slider_selected_right" style="">
		                	<textarea id="annGeneral" name="outline" style="width: 580px;height: 80px;" ><c:if test="${not empty Ann}">${Ann.outline}</c:if></textarea>
			            </div>
			        </div>
			        
			        <div class="slider_body_textarea" style="height: 85px;position: relative; clear: both;">
			        	<div class="slider_selected_left">
			            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>备注：</span>
			        	</div>
			        	<div class="slider_selected_right" style="">
		                	<textarea id="remark" name="remark" style="width: 580px;height: 80px;" ><c:if test="${not empty Ann}">${Ann.remark}</c:if></textarea>
			            </div>
			        </div>
			        <input type="hidden" value="${Ann.id }" name="id">
			        
			        <div class="button_wrap clear_both">
						<input id="updateBt" type="button" class="button_blue" value="保存">
						<input id="backBt" type="button" class="button_blue" value="关闭">
					</div>
		        </form>
			</div>
		</div>	
	</body>
	    <script type="text/javascript">
			//文件加载完毕之后执行
			$(function(){
				//返回按钮。。关闭弹出层
				$("#backBt").click(function() {
					parent.location.reload();
				});
				//function closeLayer(){
				//	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				//	parent.layer.close(index);
				//}
				
				//保存更新
				var url = "${pageContext.request.contextPath}/sysAnn/saveEdit.do";
				$("#updateBt").bind('click', function(){
					if(validatePass()){
						//显示进度条
						var index = layer.load();
							
						$.ajax({
							url:url,
							async:false,
							data:$("#formEdit").serialize(),
							type:'POST',
							dataType:'text',
							success:function(flag){
								//关闭进度条
						    	layer.close(index);
						    	
								if(flag == 1){
									layer.msg('修改成功！');
								} else{
									layer.msg('修改失败！');
								}
							}, error:function(){
								//关闭进度条
						    	layer.close(index);
						    		
								layer.msg('修改失败！');
							}
						});
					}
				});
			});
			
			//表单校验通过
			function validatePass(){
				var flag = true;
				//校验公告概要
				var annGeneral = $.trim($("#annGeneral").val());
				if(!annGeneral){
					layer.msg('公告概要不能为空！');
					flag = false;
				}
				//校验公告类型
				var annType = $.trim($("#annType2").val());
				if(!annType){
					layer.msg('请先选择公告类型！');
					flag = false;
				}
				var fileName = $.trim($("#fileName").val());
				if(!fileName){
					layer.msg('公告标题不能为空！');
					flag = false;
				}
				return flag;
			}
	    </script>
</html>