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
		<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/baseDataMsg/jcd/jcdChoose.css'/>">
		<script src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
		<style>
			 ul,li{
				list-style:none;
				margin:0;
				padding:0;
	        	cursor: pointer;
			}
			#divleft ul li{
				border-bottom: 1px solid #d0d0d0;
			}
			#divright ul li{
				border-bottom: 1px solid #d0d0d0;
			}
			#divleft,#divright{
				overflow-y:auto;
			}
			.button_blue{
				padding: 4px 16px;
				border: none;
				color: #fff;
				background: #08f;
				/*behavior: url(ie-css3.htc);*/
				border-radius: 6px;
				outline: none;
				cursor: pointer;
				margin: 4px 10px;
				border: 1px solid #fff;
			}
			.button_blue:hover{
				background: #fff;
				border: 1px solid #ccc;
				color: #555;
			}
			.button_white{ 
				background: #fff;
				border: 1px solid #ccc;
				color: #555;
			}
			.button_white:hover{
				color: #fff;
				background: #08f;
				border: none;
				border: 1px solid transparent;
			}
			.btn{
				display:block;
				margin: 0 auto;
				width:40px;
			}
			.tab_content {
			    font-size: 12px;
			    padding: 20px;
			    width: 99%;
			}
		</style>
	</head>
	<body>
		<div id="divMain">
			<div id="divbody">
				<div class="container">
					<ul class="tabs">
						<li class="active"><a href="#tab1" onclick="">树形选择</a></li>
						<li><a href="#tab4" onclick="javascript:getByIds()">检索选择</a></li>
						<li><a href="#tab5" onclick="javascript:getByIds()">已选内容</a>	</li>
					</ul>
					<div class="tab_container" style="height: 400px;">
						<div id="tab1" class="tab_content" style="display: block;">
							<!-- 加载所有监测点树（JSON）Ztree -->
			                <div class="ul">
								<div>
									<ul id="tree" class="ztree" style="border: 1px solid #d0d0d0;overflow-y: auto;width:700px;height: 350px;position:relative;clear:both;"></ul>
								</div>
			                </div> 
						</div>
						<div id="tab4" class="tab_content" style="display: none;height: 360px;width:680px;">
							<form action="" id="form1">
								<div>
									<input type="text" name="jcdxz" value="监测点类型：" readonly="readonly" style="border: none;width: 80px;"/>
									<c:forEach items="${dicList}" var="dic">
										<input type="checkbox" name="Check_jcd" value="${dic.typeSerialNo}"/>${dic.typeDesc}
									</c:forEach>
								</div>
								<div>
									<input type="text" name="jcdmc">
									<input type="radio" checked="checked" name="Check_hanzi_pinyin" value="hanzi">汉字检索
									<input type="radio" name="Check_hanzi_pinyin" value="pinyin">拼音检索
									<input type="radio" name="Check_hanzi_pinyin" value="bianhao">编号检索
									<input type="button" id="btn_ss" class="button_blue" value="搜索" onclick="toSerch()">
								</div>
							</form>
							<div id="divbody1" style="width: 100%;height: 300px;">
								<div id="divleft" style="width: 310px;height: 300px;">
									<!--遍历jcdDatas显示出查询的监测点的名字  -->
									<ul id="ul1"></ul>
								</div>
								<div id="divcenter" style="width:50px;">
									<button class="btn" onclick="javascript:addOne()" id="addOne">&gt;</button>
									<button class="btn" onclick="javascript:delOne()">&lt;</button>
									<button class="btn" onclick="javascript:addMany()">&gt;&gt;</button>
									<button class="btn" onclick="javascript:delMany()">&lt;&lt;</button>
								</div>
								<div id="divright" style="width: 310px;height: 300px;">
									<ul id="ul2"></ul>
								</div>
							</div>
						</div>
						<div id="tab5" class="tab_content" style="height: 360px;width:680px;">
							<div id="divleft" style="height: 360px;">
								<!--遍历选中的items  -->
								<ul id="ul3">
									<c:forEach items="ids"></c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
	<script src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript">
		//1.function(获取监测点并回显)
		function reShowJcds(){
			var hasIds = parent.$("#jcdid").val();
			if(hasIds!=''&&hasIds!=null){
	    		ids = parent.$("#jcdid").val().split(',');
	    	}
	    	//树形选择...获取某个id节点，设置checked属性为true
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			for(var i in ids){
				//console.log(ids[i]);
				var node = treeObj.getNodeByParam("id", ids[i], null);
				node.checked = true;
				treeObj.updateNode(node);    	
			};
			//检索选择...右侧列表框中回显
			//已选...回显
			getByIds();
		}
	
		//页面关闭之前把ids中的监测点id传到父页面中
		$(window).bind("beforeunload",function(){
			parent.$("#jcdid").val(ids);
			var len = ids.length;
			if(len >= 1){
				parent.$("#jcdid1").val("您选中了"+ids.length+"个监测点");
			} else {
				parent.$("#jcdid1").val("==全部==");
			}
		});
	
		//加载Ztree...
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
		$(document).ready(function() {
			
			//load Tab
			//Default Action  
			$(".tab_content").hide(); //Hide all content  
			$("ul.tabs li:first").addClass("active").show(); //Activate first tab  
			$(".tab_content:first").show(); //Show first tab content  
	
			//On Click Event  
			$("ul.tabs li").click(function() {
				$("ul.tabs li").removeClass("active"); //Remove any "active" class  
				$(this).addClass("active"); //Add "active" class to selected tab  
				$(".tab_content").hide(); //Hide all tab content  
				var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content  
				$(activeTab).fadeIn(); //Fade in the active content  
				return false;
			});
			
			//页面加载时请求Ztree
			$.ajax({
				async : false,//同步方法
				cache:true,
				type: 'POST',
				dataType : "json",
				url: "${pageContext.request.contextPath}/jcd/getTreeJson3.do",//请求的action路径
				error: function () {//请求失败处理函数
					alert('请求监测点树失败!');
				},
				success:function(data){ //请求成功后处理函数。
					treeNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
				}
			});
			$.fn.zTree.init($("#tree"), setting, treeNodes);
			//1.获取父页面中的监测点并回显
			reShowJcds();
		});
		
		//树中节点...点击后回调函数
		function onCheck(event,treeId,treeNode){
			//获取选中的节点的id
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			var nodes = treeObj.getCheckedNodes(true);	
			//alert(nodes);
			//清空
			ids.length=0;
			for(var node in nodes){
				if(nodes[node].isParent){
				}else{
					ids.push(nodes[node].id);
				};
			}
		};
		
		//回车或点击确定button，检索符合条件的监测点
		function toSerch(){
			var JcdName = $.trim($("input[name='jcdmc']").val());
			if(JcdName == ""){
				alert('请先输入检索关键字!');
				return;
			}
			
			//ul1列表清空
			$("#ul1 li").remove();
			//获取输入框中的查询条件
			if($("input[name='Check_hanzi_pinyin']:checked").val() == "hanzi"){
				$.ajax({
					async : false,//同步方法
					cache:true,
					type: 'POST',
					data: $("#form1").serialize(),
					dataType : "json",
					url: "${pageContext.request.contextPath}/jcd/getJcdByName.do",//请求的action路径
					error: function () {//请求失败处理函数
						alert('检索失败!');
					},
					success:function(jcdDatas){ //请求成功后处理函数。
						for(var j in jcdDatas){
							$("#ul1").append('<li><input type="checkbox" name="ids" value='+jcdDatas[j].id+'>'+jcdDatas[j].jcdmc+'</li>');
						}					 
					}
				});
			} else if( $("input[name='Check_hanzi_pinyin']:checked").val() == "pinyin" ){
				$.ajax({
					async : false,//同步方法
					cache:true,
					type: 'POST',
					data: $("#form1").serialize(),
					dataType : "json",
					url: "${pageContext.request.contextPath}/jcd/getJcdByPinYin.do",//请求的action路径
					error: function () {//请求失败处理函数
						alert('检索失败!');
					},
					success:function(jcdDatas){ //请求成功后处理函数。
						for(var j in jcdDatas){
							$("#ul1").append('<li><input type="checkbox" name="ids" value='+jcdDatas[j].id+'>'+jcdDatas[j].jcdmc+'</li>');
						}
					}
				});
			} else if( $("input[name='Check_hanzi_pinyin']:checked").val() == "bianhao" ){
				$.ajax({
					async : false,//同步方法
					cache:true,
					type: 'POST',
					data: $("#form1").serialize(),
					dataType : "json",
					url: "${pageContext.request.contextPath}/jcd/getJcdByBianHao.do",//请求的action路径
					error: function () {//请求失败处理函数
						alert('检索失败!');
					},
					success:function(jcdDatas){ //请求成功后处理函数。
						for(var j in jcdDatas){
							$("#ul1").append('<li><input type="checkbox" name="ids" value='+jcdDatas[j].id+'>'+jcdDatas[j].jcdmc+'</li>');
						}
					}
				});
			}
		}
		
		//ul1框 双击事件
		$(function(){
			$('#ul1').on('dblclick','li',function(){
				//alert($(this).find('input').attr('value'));
				$(this).remove();
				var id = $(this).find('input').attr('value');
				var index = $.inArray(id,ids);
				if(index>=0){
					return ;
				}else{
					$('#ul2').append($(this).css('background','#FFF'));
					$(this).find('input').get(0).checked = false;
					ids.push($(this).find('input').attr('value'));
				}
			/* 	var xzjcd = parent.$("#Check_clsyr").val();
				xzjcd = ids; */
			});	
		
			//ul1 ul2 列表单击改变颜色
			$('#ul1').on('click','li',changeColor);	
			$('#ul2').on('click','li',changeColor);	
			//ul2列表 双击事件
			$('#ul2').on('dblclick','li',function(){
				$(this).remove();
				//$(this).find('input').get(0).attr('checked')==false;
				$('#ul1').append($(this).css('background','#FFF'));
				var index = $.inArray($(this).find('input').attr('value'),ids);
				if(index>=0){
					ids.splice(index,1);
				}
				$(this).find('input').get(0).checked = false;
				//向父页面中传值
				/* var xzjcd = parent.$("#Check_clsyr").val();
				xzjcd = ids; */
			});			
			
		});
		//单击一行改变颜色
		function changeColor(){
			if( $(this).find('input').get(0).checked==false || $(this).find('input').get(0).checked==undefined){
				$(this).css('background','#d0d0d0');
				$(this).find('input').get(0).checked = true;
			}else{
				$(this).css('background','#FFF');
				$(this).find('input').get(0).checked = false;
			}
		}
		//单击转移button > ，从ul1中转移项li到ul2中
		function addOne(){
			//获取ul1中 选中的li标签
			var lis = $("#ul1 li");
			var flag = 1;
			for(var i=0; i<lis.length; i++){ 
				if(flag==1){
					var inputElement = $("#ul1 li input:checked").eq(0).parent();
					inputElement.dblclick();
					flag=0;
				}else{
					return ;
				}
			}
		};
		//单击转移button < ,从ul2中国转移项li到ul1中
		function delOne(){
			var lis = $("#ul2 li");
			var flag = 1;
			for(var i=0; i<lis.length; i++){ 
				if(flag==1){
					var inputElement = $("#ul2 li input:checked").eq(0).parent();
					inputElement.dblclick();
					flag=0;
				}else{
					return ;
				}
			}
		}
		//单击全部选中转移button >> 事件
		function addMany(){
			/* var lis = $("#ul1 li");
			for(var i = 0 ;i<lis.length;i++){
				var inputElement = $("#ul1 li input:checked").eq(0).parent();
				inputElement.dblclick();	
			} */
			$("#ul1 li").dblclick();
		}
		//单击全部选中转移button << 事件
		function delMany(){
		/* 	var lis = $("#ul2 li");
			for(var i = 0 ;i<lis.length;i++){
				var inputElement = $("#ul2 li input:checked").eq(0).parent();
				inputElement.dblclick();	
			}	 */
			$("#ul2 li").dblclick();
		}
		//根据ids[]加载出已经选择的监测点
		function getByIds(){
			$.ajax({
					async : false,//同步方法
					cache:false,
					type: 'POST',
					data: {"ids[]":ids},
					dataType : "json",
					url: "${pageContext.request.contextPath}/jcd/getByIds.do",//请求的action路径
					/* error: function () {//请求失败处理函数
						alert('获取已选失败!');
					}, */
					success:function(jcdDatas){ //请求成功后处理函数。
						//ul2、ul3列表清空
						var $ul2 = $("#ul2");
						var $ul3 = $("#ul3");
						$("#ul3 li").remove();
						$("#ul2 li").remove();
						for(var j in jcdDatas){
							$ul2.append('<li>'+'<input type="checkbox" checked="checked" name="ids" value='+jcdDatas[j].id+'>'+jcdDatas[j].jcdmc+'</li>');
							$ul3.append('<li>'+'<input type="checkbox" checked="checked" name="ids" value='+jcdDatas[j].id+'>'+jcdDatas[j].jcdmc+'</li>');
						}					 
					}
				});
		}
	</script>
</html>