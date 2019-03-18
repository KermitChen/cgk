<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取用户信息
	User userObj = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>批量授权页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-批量授权页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
	</head>
	<body>
		<div id="content" style="width: 400px;height: 350px;margin:0 auto;">
			<div id="content_wrap" style="width: 350px;height: 350px;">
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>授权用户：</span>
		        	</div>
		        	<div id="deptDiv" class="slider_selected_right">
						<input class="input_select xiala" id="deptAndUser_downlist" readonly="readonly" type="text" value="==请选择==" onclick="javascript:showTree();"/> 
						<input type="hidden" id="deptAndUser_select" name="property" value=""/>
						<div id="treeUl" class="ul"> 
							<ul id="deptAndUserTree" class="ztree" style="height: 250px;"></ul>
						</div> 
					</div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>用户角色：</span>
		        	</div>
		        	<div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
						<input class="input_select xiala" id="role_downlist" readonly="readonly" type="text" value="==请选择=="/>
						<input type="hidden" id="RoleSelect" name="property" value=""/>
						<div class="ul">
							<div id="role_div" class="li" data-value="" onclick="sliders(this)">==请选择==</div>
							<c:forEach items="${roleList}" var="role">
								<c:set var="roleId" value="${role.id}${':'}${role.roleName}${':'}${role.roleType}" />
					        	<div class="li" data-value="${roleId}" onclick="sliders(this)">${role.roleName}</div>
							</c:forEach>
						</div>
					</div>
		        </div>
		        <div class="button_wrap clear_both">
					<input id="saveBt" type="button" class="button_blue" value="保存">
					<input id="backBt" type="button" class="button_blue" value="关闭">
				</div>
			</div>
		</div>
	</body>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	    <script type="text/javascript">
			//文件加载完毕之后执行
			$(function(){
				//返回按钮
				$("#backBt").click(function() {
					//window.location = '<%=basePath%>user/initUserManage.do?' + new Date().getTime();
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				});
				
				//保存按钮
				$("#saveBt").click(function() {
					//获取数据
					var userIds = $.trim($("#deptAndUser_select").val());//用户ID
					var roleId = $.trim($("#RoleSelect").val());//角色ID:角色名臣:角色类型
					
					//验证信息
					if(validateUserSelect() && validateRoleSelect()){
					    //显示进度条
						var index = layer.load();
					    
						//提交
						$.ajax({
							url:'<%=basePath %>/user/grantRole.do?' + new Date().getTime(),
							method:"post",
							data:{userIds:userIds, roleId:roleId},
							success:function(data){
								//关闭进度条
					    		layer.close(index);
								if(data.result == "1"){//授权成功！
									alert('授权成功！');
									parent.window.reloadData();
									$("#backBt").trigger("click");
									
									//重置
									//reset();
								} else if(data.result == "0"){//授权失败！    
									alert('授权失败！');
								}
							},
							error: function () {//请求失败处理函数
								//关闭进度条
					    		layer.close(index);
								alert('授权失败！');
							}
						});
					}
				});
			});
			
			//重置
			function reset(){
				$('#role_div').trigger("click");//权限角色
		  		
		  		//重新获取部门信息，并初始部门树
				$("#deptAndUser_downlist").val("==请选择==");//用户姓名
				$("#deptAndUser_select").val("");//用户编号，数据库主键
			}
			
			//用户选择校验方法
			function validateUserSelect(){
				//非空校验
				var value = $.trim($("#deptAndUser_select").val());
				if(!value){
					alert("请选择需要授权的用户！");
					return false;
				}
				return true;
			}
 
			//角色校验方法
			function validateRoleSelect(){
				//非空校验
				var value = $.trim($("#RoleSelect").val());
				if(!value){
					alert("请选择用户角色！");
					return false;
				}
				return true;
			}
	    </script>
	    <!-- 部门树-->
		<script type="text/javascript">
		<!--
			//树的基本设置
			var setting = {
				view: {
					dblClickExpand: true
				},
				async: {  
					enable: true,  
					url:"<%=basePath%>/user/getDeptAndUserToWriter.do",  
					autoParam:["nodeNo"],
					dataFilter: filter
				},
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				data: {
					key: {
						name:"nodeName",
						title:"nodeName"
					},
					simpleData: {
						enable: true,
						idKey: "nodeNo",
						pIdKey: "nodeParent"
					}
				},
				callback: {
					onCheck:onCheck
				}
			};
			
			function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				var myChildNodes = new Array();
				for (var i=0;i < childNodes.length;i++) {
					var fdStart = childNodes[i].systemNo.indexOf(<%=userObj.getSystemNo() %>);
					if(fdStart != 0 && childNodes[i].flag != "2"){
						childNodes[i].chkDisabled = true;
					}
					myChildNodes[i] = childNodes[i];
				}
				return myChildNodes;
			}
		
			//选中某一项
			function onCheck(event, treeId, treeNode){
				var deptAndUserTree = $.fn.zTree.getZTreeObj("deptAndUserTree");
				var nodes = deptAndUserTree.getCheckedNodes(true);
				
				var flag = "1";//第一个选项
				var mcs = "";
				var ids = "";
				for(var i=0;i < nodes.length;i++){
					if(flag == "0" && nodes[i].flag == "2") {
						mcs += "," + nodes[i].nodeName;
						ids += "," + nodes[i].id;
					} else if(flag == "1" && nodes[i].flag == "2"){
						flag = "0";
						mcs = nodes[i].nodeName;
						ids = nodes[i].id;
					} 
				}
				$("#deptAndUser_downlist").val(mcs);//用户姓名
				$("#deptAndUser_select").val(ids);//用户编号，数据库主键
			}
		
			//控制树的显示
			function showTree(){
		        if($("#treeUl").css("display") == "none"){ 
		            $("#treeUl").slideDown("fast"); 
		        } else{ 
		            $("#treeUl").slideUp("fast"); 
		        } 
			}
	 
	 		//初始化树
			$(document).ready(function(){
				//初始化树
				$.fn.zTree.init($("#deptAndUserTree"), setting, <%=request.getAttribute("deptAndUserToJson") %>);
					
				//检查顶级部门是否可选
				var treeObj = $.fn.zTree.getZTreeObj("deptAndUserTree");
				var nodes = treeObj.getNodesByParamFuzzy("systemNo", "68");
				for(var i=0;i < nodes.length;i++){
					var fdStart = nodes[i].systemNo.indexOf(<%=userObj.getSystemNo() %>);
					if(fdStart != 0 && nodes[i].flag != "2"){
						nodes[i].chkDisabled = true;
						treeObj.updateNode(nodes[i]);
					}
				}
						
				//单选点击空白关闭
				$("body").click(function(event){
					//不是点击dept_downlist，则隐藏树
					if(event.target.id != "deptAndUser_downlist" && event.target.nodeName != "SPAN"){
						$("#treeUl").slideUp("fast");
					}
				});
			});
		//-->
		</script>
</html>