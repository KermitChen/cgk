<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<style>
		#divMain{
			margin-left: 1px;
		}
	</style>
</head>
  <body>
    <div id="divMain">
		<div>
			<ul id="tree" class="ztree"></ul>
		</div>
    </div>
  </body>
<script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
  <script type="text/javascript">
  
	//2.function(获取用户并回显)
	function reShowUsers(){
		var hasIds = parent.$("#dxyhs").val();
		if(hasIds!=''&&hasIds!=null){
    		ids = parent.$("#dxyhs").val().split(',');
    	}
    	//树形选择...获取某个id节点，设置checked属性为true
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		for(var i in ids){
			//console.log(ids[i]);
			var node = treeObj.getNodeByParam("id", ids[i], null);
			node.checked = true;
			treeObj.updateNode(node);    	
		};
	}
  
	//页面关闭之前把ids中的监测点id传到父页面中
	$(window).bind("beforeunload",function(){
		parent.$("#dxyhs").val(ids);
		var len = ids.length;
		if(len>=1){
			parent.$("#dxyhs1").val("您选中了"+ids.length+"个用户");
		}else if(len<=0){
			parent.$("#dxyhs1").val("==请选择==");
		}
		//回显电话
		parent.$("#dxhm").val(telephones);
	});
	
  	//文档加载时
  	$(function(){
  		//1.初始化Ztree
  		initZTree();
  		//2.回显已选
  		reShowUsers();
  	});
  	
  	//加载Ztree...Prepared variables
	var setting = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback:{
                onCheck:onCheck
            }
	};
	var treeNodes;
	//选中节点的id数组
	var ids=[];
	//选中节点的电话号码
	var telephones=[];	
  	
  	//1.初始化ZTree
  	function initZTree(){
  		$.ajax({
			async : false,//同步方法
			cache:true,
			type: 'POST',
			dataType : "json",
			url: "${pageContext.request.contextPath}/dy/getTreeJson3.do",//请求的action路径
			error: function () {//请求失败处理函数
				alert('请求监测点树失败!');
			},
			success:function(data){ //请求成功后处理函数。
				treeNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
			}
		});
		$.fn.zTree.init($("#tree"), setting, treeNodes);
  	}
  	//2.树中节点...点击后回调函数
	function onCheck(event,treeId,treeNode){
		//获取选中的节点的id
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getCheckedNodes(true);	
		//alert(nodes);
		//清空
		ids.length=0;
		telephones.length=0;
		for(var node in nodes){
			if(nodes[node].isParent){
			}else{
				ids.push(nodes[node].id);
				telephones.push(nodes[node].phone);
			};
		}
	};
  </script>
</html>
