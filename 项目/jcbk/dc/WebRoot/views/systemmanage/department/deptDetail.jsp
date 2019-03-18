<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取数据
	Department deptObj = (Department)request.getAttribute("deptObj");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>部门信息详情页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-部门信息详情页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>
	<body>
		<div id="content" style="width: 800px;height: 400px;margin:0 auto;">
			<div id="content_wrap" style="width: 750px;height: 400px;">
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>部门编号：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="DeptNo" name="deptNo" type="text" class="slider_input" value="<c:if test="${not empty deptObj}">${deptObj.deptNo }</c:if>" readonly="readonly">
<!--		                    	<a id="DeptNo" class="empty"></a>-->
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>部门名称：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="DeptName" name="deptName" type="text" class="slider_input" value="<c:if test="${not empty deptObj}">${deptObj.deptName}</c:if>" readonly="readonly">
<!--								<a id="DeptName" class="empty"></a>-->
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>联系电话：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="Telphone" name="telphone" type="text" class="slider_input" value="<c:if test="${not empty deptObj}">${deptObj.deptTelephone}</c:if>" readonly="readonly">
<!--		                    	<a id="Telphone" class="empty"></a>-->
		                    </div>
		                </div>  
		            </div>
		        </div>	
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>上级部门：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="ParentDept" name="parentDept" type="text" class="slider_input" value="<c:if test="${not empty deptObj}">${deptObj.parentName}</c:if>" readonly="readonly">
<!--		                    	<a id="ParentDept" class="empty"></a>-->
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span><span style="color: red;visibility: hidden;">*</span>创建人用户名：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="BuildPno" name="buildPno" type="text" class="slider_input" value="<c:if test="${not empty deptObj}">${deptObj.buildPno}</c:if>" readonly="readonly">
<!--		                    	<a id="BuildPno" class="empty"></a>-->
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>创建人姓名：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="BuildName" name="buildName" type="text" class="slider_input" value="<c:if test="${not empty deptObj}">${deptObj.buildName}</c:if>" readonly="readonly">
<!--		                    	<a id="BuildName" class="empty"></a>-->
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>创建时间：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="BuildTime" name="buildTime" type="text" class="slider_input" value="<c:if test="${not empty deptObj}"><fmt:formatDate value='${deptObj.buildTime }' pattern='yyyy-MM-dd HH:mm:ss'/></c:if>" readonly="readonly">
<!--		                    	<a id="BuildTime" class="empty"></a>-->
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
		        			<div class="select_wrap select_input_null">
		                    	<input id="UpdateTime" name="updateTime" type="text" class="slider_input" value="<c:if test="${not empty deptObj}"><fmt:formatDate value='${deptObj.updateTime }' pattern='yyyy-MM-dd HH:mm:ss'/></c:if>" readonly="readonly">
<!--		                    	<a id="UpdateTime" class="empty"></a>-->
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>信息来源：</span>
		        	</div>
					<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="InfoSource" name="infoSource" type="text" class="slider_input" value="<c:if test="${not empty deptObj}">${deptObj.infoSource}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
				</div>
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>绩效考核：</span>
		        	</div>
					<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="Jxkh" name="jxkh" type="text" class="slider_input" value="<%=(deptObj != null ? ("0".equals(deptObj.getJxkh())?"否":"是"):"") %>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
				</div>
		        <div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>备注：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="remark" name="remark" style="width: 580px;height: 80px;" maxlength="500" readonly="readonly"><c:if test="${not empty deptObj}">${deptObj.remark}</c:if></textarea>
		            </div>
		        </div>
			</div>
		</div>	
	</body>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript">
			//文件加载完毕之后执行
			$(function(){
				var dicJson = <%=request.getAttribute("dicJson") %>;
				//信息来源翻译
				for(var i=0;i < dicJson.length;i++){
					var obj = dicJson[i];
					if(obj.typeCode == '1002' && obj.typeSerialNo == $("#InfoSource").val()){
						$("#InfoSource").val(obj.typeDesc);//信息来源
					}
				}
			});
	    </script>
<%
	if(deptObj == null){
%>
		<SCRIPT type="text/javascript">
			alert("加载信息失败！");
		</SCRIPT>
<%
	}
%>
</html>