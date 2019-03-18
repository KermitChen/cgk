<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
			<ul id="deptTree" class="ztree" style="margin: 10px 20px;overflow-y: auto;height: 350px;border: 1px solid #000000;position:relative;clear:both;"></ul>
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
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript">
			
			var ids = new Array();//选中节点的ids数组
			//回显选中的部门ids
			function reShowDept(){
				var hasIds = parent.$("#dept").val();
				if(hasIds!=''&& hasIds!=null){
		    		ids = hasIds.split(',');
		    	}
		    	//树形选择...获取某个id节点，设置checked属性为true
				var treeObj = $.fn.zTree.getZTreeObj("deptTree");
				for(var i in ids){
					var node = treeObj.getNodeByParam("id",ids[i],null);
					node.checked = true;
					treeObj.updateNode(node);    	
				};
			}
			//树中节点...点击后回调函数
			function onCheck(event,treeId,treeNode){
				//获取选中的节点的id
				var treeObj = $.fn.zTree.getZTreeObj("deptTree");
				var nodes = treeObj.getCheckedNodes(true);	
				//alert(nodes);
				//清空
				ids.length=0;
				for(var node in nodes){
					ids.push(nodes[node].nodeNo);
				}
			};
			//页面关闭之前把ids中的监测点id传到父页面中
			$(window).bind("beforeunload",function(){
				parent.$("#dept").val(ids);
				console.log(ids);
				var len = ids.length;
				if(len>=1){
					parent.$("#dept1").val("您选中了"+ids.length+"个部门");
				}
			});
		
			var zNodes = <%=(request.getAttribute("MaxDeptToJson")!=null?request.getAttribute("MaxDeptToJson"):"[]") %>;
			//树的基本设置
			var setting = {
				view: {
					dblClickExpand: true
				},
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "s", "N": "s" }
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
				callback:{
                	onCheck:onCheck
            	}
			};
			
			function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				//for (var i=0, l=childNodes.length; i<l; i++) {
					//childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
				//}
				return childNodes;
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
				reShowDept();
			});
		//-->
		</script>
</html>