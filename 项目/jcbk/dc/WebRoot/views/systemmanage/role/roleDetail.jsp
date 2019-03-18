<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	//获取需要编辑的数据
	Role roleObj = (Role)request.getAttribute("roleObj");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>角色权限详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-角色权限详情">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
		<script type="text/javascript">
			<!--
			var zNodes = <%=(request.getAttribute("funList")!=null?request.getAttribute("funList"):"[]") %>;
			
			//树的基本设置
			var setting = {
				check: {
					enable: true
				},
				data: {
					key: {
						name: "funName",
						title: "funName"
					},
					simpleData: {
						enable: true,
						idKey: "permissionPosition",
						pIdKey: "parentPermission",
						rootPid: "0"
					}
				}
			};
			
			$(document).ready(function(){
				//显示树
				$.fn.zTree.init($("#roleTree"), setting, zNodes);
				
				//根据该角色所具有的权限，设置已选
				var zTree = $.fn.zTree.getZTreeObj("roleTree");
				<%
					if(roleObj != null){
						char[] permission = roleObj.getPermissionContent().toCharArray();
						for(int i=0;i < permission.length;i++){
				%>
							var node = zTree.getNodeByParam("permissionPosition", <%=i+1 %>);
							if(node != null){
								if(<%=permission[i] %> == '1') {
									node.checked = true;
									zTree.updateNode(node);
								}
								zTree.setChkDisabled(node, true);
							}
				<%
						}
					}
				%>
				var roleType = roleTypeFunction($("#roleType").val());
				$("#roleType").val(roleType);//角色类型
			});
		//-->
		</script>
	</head>
	<body>
		<%	
			//获取失败
			if(request.getAttribute("funList") == null || roleObj == null){
		%>
				<SCRIPT type="text/javascript">
					alert("加载信息失败！");
				</SCRIPT>
		<%
			}
		%>
		<fieldset style="margin: 0px auto">
			<legend style="color:#FF3333">角色权限详情</legend>
			<div class="slider_body" style="height: 30px;margin: 10px 0px 0px 0px;">
		    	<div class="slider_selected_left" style="">
		        	<span>&nbsp;&nbsp;角色名称：</span>
		        </div>
		        <div class="slider_selected_right" style="left: 90px;">
		        	<div class="img_wrap">
		            	<div class="select_wrap select_input_wrap">
		                	<input id="roleName" type="text" class="slider_input" value="<c:if test="${not empty roleObj}">${roleObj.roleName}</c:if>" readonly="readonly">
		                </div>
		            </div>  
		        </div>
		    </div>
		    <div class="slider_body" style="height: 30px;position:relative;clear:both;margin: 0px 0px 0px 0px;">
				<div class="slider_selected_left" style="">
					<span>&nbsp;&nbsp;角色类型：</span>
				</div>
				<div class="slider_selected_right" style="left: 90px;">
		        	<div class="img_wrap">
		            	<div class="select_wrap select_input_wrap">
		                	<input id="roleType" type="text" class="slider_input" value="<c:if test="${not empty roleObj}">${roleObj.roleType}</c:if>" readonly="readonly">
		                </div>
		            </div>  
		        </div>
			</div>
			<div class="slider_body" style="height: 30px;position:relative;clear:both;margin: 0px 0px 0px 0px;">
				<div class="slider_selected_left" style="">
					<span>&nbsp;&nbsp;创建时间：</span>
				</div>
				<div class="slider_selected_right" style="left: 90px;">
		        	<div class="img_wrap">
		            	<div class="select_wrap select_input_wrap">
		                	<input id="buildTime" type="text" class="slider_input" value="<c:if test="${not empty roleObj}"><fmt:formatDate value='${roleObj.buildTime }' pattern='yyyy-MM-dd HH:mm:ss'/></c:if>" readonly="readonly">
		                </div>
		            </div>  
		        </div>
			</div>
			<div class="slider_body" style="height: 30px;position:relative;clear:both;margin: 0px 0px 0px 0px;">
				<div class="slider_selected_left" style="">
					<span>&nbsp;&nbsp;更新时间：</span>
				</div>
				<div class="slider_selected_right" style="left: 90px;">
		        	<div class="img_wrap">
		            	<div class="select_wrap select_input_wrap">
		                	<input id="updateTime" type="text" class="slider_input" value="<c:if test="${not empty roleObj}"><fmt:formatDate value='${roleObj.updateTime }' pattern='yyyy-MM-dd HH:mm:ss'/></c:if>" readonly="readonly">
		                </div>
		            </div>  
		        </div>
			</div>
			<ul id="roleTree" class="ztree" style="margin: 10px 20px;overflow-y: auto;height: 250px;border: 1px solid #000000;position:relative;clear:both;"></ul>
			<div class="button_wrap clear_both">
				<input id="closeBt" type="button" class="button_blue" value="取消" onclick="loadFunctionTree();">
			</div>
		</fieldset>
	</body>
</html>