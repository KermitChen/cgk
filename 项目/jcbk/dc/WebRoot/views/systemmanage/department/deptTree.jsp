<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	//获取用户信息
	User userObj = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>部门结构树</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-部门结构树">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<style type="text/css">
			div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
			div#rMenu ul li{
				margin: 1px 0;
				padding: 0 5px;
				cursor: pointer;
				list-style: none outside none;
				background-color: #DFDFDF;
			}
		</style>
	</head>
	<body>
		<%	
			//获取失败
			if(request.getAttribute("MaxDeptToJson") == null){
		%>
				<SCRIPT type="text/javascript">
					alert("加载部门结构树失败！");
				</SCRIPT>
		<%
			}
		%>
		<fieldset style="margin: 0px auto">
			<legend style="color:#FF3333">部门结构树</legend>
			<ul id="deptTree" class="ztree" style="margin: 10px 20px;overflow-y: auto;width:240px;height: 350px;border: 1px solid #000000;position:relative;clear:both;"></ul>
		</fieldset>
		<div id="rMenu">
			<ul>
				<li id="m_add" onclick="addTreeNode();">新增部门</li>
				<li id="m_edit" onclick="editTreeNode();">修改部门</li>
				<li id="m_del" onclick="removeTreeNode();">删除部门</li>
				<li id="m_detail" onclick="detailTreeNode();">信息详情</li>
			</ul>
	   	</div>
	</body>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript">
			<!--
			var zNodes = <%=(request.getAttribute("MaxDeptToJson")!=null?request.getAttribute("MaxDeptToJson"):"[]") %>;
			
			//树的基本设置
			var setting = {
				view: {
					dblClickExpand: true
				},
				async: {  
					enable: true,  
					url:"<%=basePath%>/dept/getDeptByParentNoToJson.do",  
					autoParam:["nodeNo"],
					dataFilter: filter
				},				
				check: {
					enable: false
				},
				data: {
					keep: {
						parent:true
					}, 
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
					onRightClick: OnRightClick
				}
			};
			
			function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				//for (var i=0, l=childNodes.length; i<l; i++) {
					//childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
				//}
				return childNodes;
			}

			function OnRightClick(event, treeId, treeNode) {
				if (treeNode) {
					zTree.selectNode(treeNode);
					
					//var fdStart = treeNode.systemNo.indexOf(<%=userObj.getSystemNo() %>);
					//if(fdStart == 0){
						if(treeNode.infoSource != '2'){
							showRMenu("1", event.clientX, event.clientY);
						} else{
							showRMenu("2", event.clientX, event.clientY);
						}
					//} else {
					//	alert("仅可操作本部门及其子孙部门！");
					//}
				}
			}
 
 			//显示菜单
			function showRMenu(type, x, y) {
				//显示菜单
				$("#rMenu ul").show();
				
				//隐藏部分菜单
				if (type == "1") {
					$("#m_add").show();
					$("#m_edit").show();
					$("#m_del").show();
					$("#m_detail").show();
				} else {
					$("#m_add").show();
					$("#m_edit").show();
					$("#m_del").hide();
					$("#m_detail").show();
				}
				
				rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	 			//增加事件监听
				$("body").bind("mousedown", onBodyMouseDown);
			}
			
			//隐藏菜单
			function hideRMenu() {
				if (rMenu) rMenu.css({"visibility": "hidden"});
				$("body").unbind("mousedown", onBodyMouseDown);
			}
			
			//点击body，隐藏菜单
			function onBodyMouseDown(event){
				if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
					rMenu.css({"visibility" : "hidden"});
				}
			}
			
			//删除部门
			function removeTreeNode() {
				//隐藏菜单
				hideRMenu();
				//获取选中的节点
				var nodes = zTree.getSelectedNodes();
				if (nodes && nodes.length > 0) {
					doDelete(nodes[0].nodeNo);
				}
			}
			
			//修改部门
			function editTreeNode() {
				//隐藏菜单
				hideRMenu();
				//获取选中的节点
				var nodes = zTree.getSelectedNodes();
				if (nodes && nodes.length > 0) {
					//window.location = '<%=basePath%>dept/initUpdateDept.do?id=' + nodes[0].id;
					var url ="${pageContext.request.contextPath}/dept/initUpdateDept.do?id=" + nodes[0].id;
					layer.open({
			           type: 2,
			           title: '修改部门信息',
			           shadeClose: false,
			           //shade: 0,
			           area:['826px', '450px'],
			           content: url //iframe的url
			       });
				}
			}
			
			//新增部门
			function addTreeNode() {
				//隐藏菜单
				hideRMenu();
				//获取选中的节点
				var nodes = zTree.getSelectedNodes();
				if (nodes && nodes.length > 0) {
					//window.location = '<%=basePath%>dept/initAddDept.do?pNo=' + nodes[0].nodeNo;
					var url ="${pageContext.request.contextPath}/dept/initAddDept.do?pNo=" + nodes[0].nodeNo;
					layer.open({
			           type: 2,
			           title: '新增部门信息',
			           shadeClose: false,
			           //shade: 0,
			           area:['826px', '450px'],
			           content: url //iframe的url
			       }); 
				}
			}
			
			//部门信息详情
			function detailTreeNode() {
				//隐藏菜单
				hideRMenu();
				//获取选中的节点
				var nodes = zTree.getSelectedNodes();
				if (nodes && nodes.length > 0) {
					deptDetail(nodes[0].id);
				}
			}
			
			var zTree, rMenu;
			$(document).ready(function(){
				//显示树
				$.fn.zTree.init($("#deptTree"), setting, zNodes);
				zTree = $.fn.zTree.getZTreeObj("deptTree");
				rMenu = $("#rMenu");
			});
		//-->
		</script>
</html>