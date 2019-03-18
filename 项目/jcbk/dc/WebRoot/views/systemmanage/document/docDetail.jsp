<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取数据
	Wjjlb wjjlbObj = (Wjjlb)request.getAttribute("wjjlbObj");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>帮助文档详情页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-帮助文档详情页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	    <script type="text/javascript">
	    	window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
	    </script>
	</head>
	<body>
		<div id="content" style="width: 800px;margin:0 auto;">
			<div id="content_wrap" style="width: 750px;height: 300px;">
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>文档名称：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="fileName" name="fileName" type="text" class="slider_input" value="<c:if test="${not empty wjjlbObj}">${wjjlbObj.fileName}</c:if>" readonly="readonly">
		                    </div>
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
		                    	<input id="buildPno" name="buildPno" type="text" class="slider_input" value="<c:if test="${not empty wjjlbObj}">${wjjlbObj.buildPno}</c:if>" readonly="readonly">
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
		                    	<input id="buildName" name="buildName" type="text" class="slider_input" value="<c:if test="${not empty wjjlbObj}">${wjjlbObj.buildName}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>上传时间：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="buildTime" name="buildTime" type="text" class="slider_input" value="<c:if test="${not empty wjjlbObj}"><fmt:formatDate value='${wjjlbObj.buildTime }' pattern='yyyy-MM-dd HH:mm:ss'/></c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		       	<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>更新时间：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="updateTime" name="updateTime" type="text" class="slider_input" value="<c:if test="${not empty wjjlbObj}"><fmt:formatDate value='${wjjlbObj.updateTime }' pattern='yyyy-MM-dd HH:mm:ss'/></c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>备注：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="remark" name="remark" style="width: 580px;height: 80px;" maxlength="500" readonly="readonly"><c:if test="${not empty wjjlbObj}">${wjjlbObj.remark}</c:if></textarea>
		            </div>
		        </div>
			</div>
		</div>	
	</body>
<%
	if(wjjlbObj == null){
%>
		<SCRIPT type="text/javascript">
			alert("加载信息失败！");
		</SCRIPT>
<%
	}
%>
</html>