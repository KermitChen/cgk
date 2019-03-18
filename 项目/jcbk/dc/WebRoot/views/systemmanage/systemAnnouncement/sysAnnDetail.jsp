<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取数据
	Announcement Ann = (Announcement)request.getAttribute("Ann");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>系统公告详情页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-系统公告详情页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	    <script type="text/javascript">
	    	window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    		
			//文件加载完毕之后执行
			$(function(){
				//返回按钮。。关闭弹出层
				$("#backBt").click(function() {
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				});
			});
	    </script>
	</head>
	<body>
		<div id="content" style="width: 800px;margin:0 auto;">
			<div id="content_wrap" style="width: 750px;height: 500px;">
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>公告标题：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="fileName" name="fileName" type="text" class="slider_input" value="<c:if test="${not empty Ann}">${Ann.fileName}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span><span style="color: red;visibility: hidden;">*</span>发布人用户名：</span>
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
		            	<span>&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>发布人姓名：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="buildName" name="buildName" type="text" class="slider_input" value="<c:if test="${not empty Ann}">${Ann.buildName}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>公告类型：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="annType" name="annType" type="text" class="slider_input" 
		                    	value='<c:forEach items="${annTypeList }" var="a"><c:if test="${a.typeSerialNo eq Ann.annType }">${a.typeDesc }</c:if></c:forEach>' readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>发布时间：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="buildTime" name="buildTime" type="text" class="slider_input" value="<c:if test="${not empty Ann}"><fmt:formatDate value='${Ann.buildTime }' pattern='yyyy-MM-dd HH:mm:ss'/></c:if>" readonly="readonly">
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
		                    	<input id="updateTime" name="updateTime" type="text" class="slider_input" value="<c:if test="${not empty Ann}"><fmt:formatDate value='${Ann.updateTime }' pattern='yyyy-MM-dd HH:mm:ss'/></c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        
		        <div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>文档概要：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="annGeneral" name="annGeneral" style="width: 580px;height: 80px;" readonly="readonly"><c:if test="${not empty Ann}">${Ann.outline}</c:if></textarea>
		            </div>
		        </div>
		        
		        <div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>备注：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="remark" name="remark" style="width: 580px;height: 80px;" readonly="readonly"><c:if test="${not empty Ann}">${Ann.remark}</c:if></textarea>
		            </div>
		        </div>
		        
		        <div class="button_wrap clear_both">
					<input id="backBt" type="button" class="button_blue" value="关闭">
				</div>
			</div>
		</div>	
	</body>
<%
	if(true){
%>
		<SCRIPT type="text/javascript">
			layer.msg(message);
		</SCRIPT>
<%
	}
%>
</html>