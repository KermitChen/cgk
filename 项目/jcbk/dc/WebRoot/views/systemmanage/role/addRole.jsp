<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*,com.dyst.BaseDataMsg.entities.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	//获取用户信息
	User userObj = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	String userPosition = (userObj.getPosition() != null?userObj.getPosition().trim():"");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>新增角色权限</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-新增角色权限">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
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
				
				//设置父子关联关系
				var zTree = $.fn.zTree.getZTreeObj("roleTree");
				zTree.setting.check.chkboxType = {"Y":"ps", "N":"ps"};
				
				//保存按钮
				$("#saveBt").click(function() {
					var roleName = $.trim($("#roleNameAdd").val());//角色名称
					var roleType = $.trim($("#roleTyeSelectAdd").val());//角色类型
					if(roleName ==  null || roleName == ""){
						alert("角色名称不能为空！");
						return false;
					}
					if(roleType ==  null || roleType == ""){
						alert("请选择角色类型！");
						return false;
					}
					//获取选中的权限位
					var nodes = zTree.getCheckedNodes(true);
					if(nodes.length == 0){
						alert("请勾选权限功能！");
						return false;
					} else {
						var selectFun = "|";//特殊组装方式
	           	 		for(var i=0;i < nodes.length;i++){
	            			selectFun += nodes[i].permissionPosition + "|";//获取权限位
	            		}
					}
					
					//显示进度条
					var index = layer.load();
					
					//提交
					$.ajax({
						url:'<%=basePath%>/role/addRole.do?' + new Date().getTime(),
						method:"post",
						data:{roleName:roleName, roleType:roleType, selectFun:selectFun},
						success:function(data){
						    //关闭进度条
					    	layer.close(index);
					    	
							if(data.result == '1'){//保存成功
								//重新查询角色
								doGoPage(1);
								alert('保存成功！');
								//返回功能列表
								loadFunctionTree();
							} else if(data.result == '0'){//保存失败
								alert('保存失败！');
							}
						},
						error: function () {//请求失败处理函数
							//关闭进度条
					    	layer.close(index);
							alert('保存失败！');
						}
					});
				});
			});
		//-->
		</script>
	</head>
	<body>
		<%	
			//获取失败
			if(request.getAttribute("funList") == null){
		%>
				<SCRIPT type="text/javascript">
					alert("加载系统功能列表失败！");
				</SCRIPT>
		<%
			}
		%>
		<fieldset style="margin: 0px auto">
			<legend style="color:#FF3333">新增角色</legend>
			<div class="slider_body" style="height: 30px;margin: 10px 0px 0px 0px;">
		    	<div class="slider_selected_left" style="">
		        	<span>&nbsp;<span style="color: red;">*</span>角色名称：</span>
		        </div>
		        <div class="slider_selected_right" style="left: 90px;">
		        	<div class="img_wrap">
		            	<div class="select_wrap select_input_wrap">
		                	<input id="roleNameAdd" type="text" class="slider_input">
		                    <a id="roleNameAdd" class="empty"></a>
		                </div>
		            </div>  
		        </div>
		    </div>
		    <div class="slider_body" style="height: 30px;position:relative;clear:both;margin: 0px 0px 0px 0px;">
				<div class="slider_selected_left" style="">
					<span>&nbsp;<span style="color: red;">*</span>角色类型：</span>
				</div>
				<div class="slider_selected_right dropdown dropdowns" onclick="slider(this)" style="left: 90px;">
					<input class="input_select xiala" id="roleTyeAdd" readonly="readonly" type="text" value="==请选择=="/>
					<input type="hidden" id="roleTyeSelectAdd" name="property" value=""/>
					<div class="ul">
						<div id="roleTypeDiv" class="li" data-value="" onclick="sliders(this)">==请选择==</div>
						<%
							List<com.dyst.BaseDataMsg.entities.Dictionary> dicList = (List<com.dyst.BaseDataMsg.entities.Dictionary>)request.getAttribute("dicList");
							for(int i=dicList.size()-1;i >= 0;i--){
								com.dyst.BaseDataMsg.entities.Dictionary dic = dicList.get(i);
								String typeSerialNo = dic.getTypeSerialNo();
								if("1001".equals(dic.getTypeCode()) && (("".equals(userPosition)?0:userPosition.length()) == 5
									|| (typeSerialNo.length() == 2 && typeSerialNo.length() == ("".equals(userPosition)?0:userPosition.length()))
									|| (typeSerialNo.length() == 3 && typeSerialNo.substring(2, 3).equals((userPosition.length() == 3?userPosition.substring(2, 3):"99"))))
									&& Integer.parseInt(typeSerialNo.substring(0, 2)) < Integer.parseInt(("".equals(userPosition)?"0":userPosition.substring(0, 2)))){
						%>
					                <div class="li" data-value="<%=dic.getTypeSerialNo() %>" onclick="sliders(this)"><%=dic.getTypeDesc() %></div>
						<%
								}
							}
						%>
					</div>
				</div>
			</div>
			<ul id="roleTree" class="ztree" style="margin: 10px 20px;overflow-y: auto;height: 250px;border: 1px solid #000000;position:relative;clear:both;"></ul>
			<div class="button_wrap clear_both">
				<input id="saveBt" type="button" class="button_blue" value="保存">
				<input id="closeBt" type="button" class="button_blue" value="取消" onclick="loadFunctionTree();">
			</div>
		</fieldset>
	</body>
</html>