<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>用户信息详情页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-用户信息详情页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>
	<body>
		<div id="content" style="width: 800px;margin:0 auto;">
			<div id="content_wrap" style="width: 750px;height: 450px;">
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>用户名：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="LoginName" name="loginName" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.loginName}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>姓名：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="UserName" name="userName" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.userName}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>手机号码：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="Telphone" name="telphone" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.telPhone}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身份证号码：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="IdentityCard" name="identityCard" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.identityCard}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>隶属部门：</span>
		        	</div>
					<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="DeptName" name="deptName" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.deptName}</c:if>" readonly="readonly">
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
		                    	<input id="InfoSource" name="infoSource" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.infoSource}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
				</div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>角色类型：</span>
		        	</div>
					<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="RoleType" name="roleType" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.position}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
				</div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>用户角色：</span>
		        	</div>
					<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="RoleName" name="roleName" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.roleName}</c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
				</div>
<!--				<div class="slider_body">-->
<!--		        	<div class="slider_selected_left">-->
<!--		            	<span><span style="color: red;visibility: hidden;">*</span>创建人用户名：</span>-->
<!--		        	</div>-->
<!--					<div class="slider_selected_right" style="">-->
<!--		        		<div class="img_wrap">-->
<!--		        			<div class="select_wrap select_input_wrap">-->
<!--		                    	<input id="BuildPno" name="buildPno" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.buildPno}</c:if>" readonly="readonly">-->
<!--		                    </div>-->
<!--		                </div>  -->
<!--		            </div>-->
<!--				</div>-->
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span><span style="color: red;visibility: hidden;">*</span>隶属考核部门：</span>
		        	</div>
					<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_null">
		                    	<input id="KhbmName" name="khbmName" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.lskhbmmc}</c:if>" readonly="readonly">
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
		                    	<input id="BuildName" name="buildName" type="text" class="slider_input" value="<c:if test="${not empty userObj}">${userObj.buildName}</c:if>" readonly="readonly">
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
		                    	<input id="BuildTime" name="buildTime" type="text" class="slider_input" value="<c:if test="${not empty userObj}"><fmt:formatDate value='${userObj.buildTime }' pattern='yyyy-MM-dd HH:mm:ss'/></c:if>" readonly="readonly">
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
		                    	<input id="UpdateTime" name="updateTime" type="text" class="slider_input" value="<c:if test="${not empty userObj}"><fmt:formatDate value='${userObj.updateTime}' pattern='yyyy-MM-dd HH:mm:ss'/></c:if>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
				</div>
		        <div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>备注：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="Remark" name="remark" style="width: 580px;height: 80px;" maxlength="500" readonly="readonly"><c:if test="${not empty userObj}">${userObj.remark}</c:if></textarea>
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
				//角色类型翻译
				for(var i=0;i < dicJson.length;i++){
					var obj = dicJson[i];
					if(obj.typeCode == '1001' && obj.typeSerialNo == $("#RoleType").val()){
						$("#RoleType").val(obj.typeDesc);//角色类型
					}
				}
				//信息来源翻译
				for(var i=0;i < dicJson.length;i++){
					var obj = dicJson[i];
					if(obj.typeCode == '1002' && obj.typeSerialNo == $("#InfoSource").val()){
						$("#InfoSource").val(obj.typeDesc);//信息来源
					}
				}
			});
	    </script>
</html>