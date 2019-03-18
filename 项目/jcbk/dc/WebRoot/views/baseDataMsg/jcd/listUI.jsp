<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
	<head>
	    <base href="<%=basePath%>">
	    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	    <link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
	    <title>监测点管理</title>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp"/>
	    <div id="divTitle">
			<span id="spanTitle">当前位置：基础数据管理&gt;&gt;监测点管理</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
			    <form name="form" action="" method="post">
		    		<fieldset style="margin: 0px 0px 0px 0px;">
						<legend  style="color:#FF3333">查询条件</legend>
				        <div class="slider_body"> 
							<div class="slider_selected_left">
				            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点ID：</span>
				            </div>
			                <div class="slider_selected_right" style="z-index:0;">
			                    <div class="img_wrap">
			                        <div class="select_wrap select_input_wrap">
			                            <input id="Check_JcdID" class="slider_input" type="text" name="Check_JcdID" />
			                            <a id="Check_JcdID" class="empty"></a>
			                        </div>
			                    </div>  
			               </div>
				        </div>
				        <div class="slider_body">
				           <div class="slider_selected_left">
				           		<span>&nbsp;&nbsp;&nbsp;监测点名称：</span>
				           </div>
				           <div class="slider_selected_right" style="z-index:0;">
				                <div class="img_wrap">
				                	<div class="select_wrap select_input_wrap">
				                    	<input id="Check_JcdName" class="slider_input" type="text" name="Check_JcdName" />
				                    	<a id="Check_JcdName" class="empty"></a>
				                	</div>
				                </div>  
				            </div>
				        </div>
				        <div class="slider_body"> 
				            <div class="slider_selected_left">
				                <span>&nbsp;&nbsp;&nbsp;监测点类型：</span>
				            </div>
				            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
				            	<input class="input_select xiala" id="jcdxzLabel" type="text" readonly="readonly" value="==全部=="/>
				            	<input type="hidden" name="Check_Jcdxz" id="jcdxzName"/>
					            <div class="ul"> 
					            	<div id="jcdxz_div" class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
									<c:forEach items="${dicList}" var="dic">
							       		<c:if test="${dic['typeCode'] eq 'JCDLX'}">
					                    	<div class="li" data-value="${dic['typeSerialNo']}" onclick="sliders(this)">${dic['typeDesc']}</div>
				                    	</c:if>
							 		</c:forEach>
					            </div>
					         </div>
				        </div>
				        <div class="slider_body"> 
				            <div class="slider_selected_left">
				                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;隶属城区：</span>
				            </div>
				            <div class="slider_selected_right dropdown dropdowns" id="dropdown">
				                <input class="input_select xiala" id="cqLabel" type="text" onclick="javascript:showTree()" value="==全部=="/>
				                <input type="hidden" id="cqName"/>
				                <div class="ul">
									<div>
										<ul id="tree" class="zTree"></ul>
									</div>
				                </div> 
				            </div>
				        </div>
				        <div class="slider_body"> 
				            <div class="slider_selected_left">
				                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;隶属道路：</span>
				            </div>
				            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
				            	<input class="input_select xiala" id="dlLabel" type="text" readonly="readonly" value="==全部=="/>
				            	<input type="hidden" id="dlName"/>
					            <div class="ul"> 
					            	<div id="road_div" class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
					         		<c:forEach items= "${roads }" var="r" >
					                	<div class="li" data-value="${r.roadNo }" onclick="sliders(this)"><a rel="2">${r.roadName }</a></div> 
									</c:forEach>
					            </div>
					         </div>
				        </div>
				        <div class="slider_body"> 
				            <div class="slider_selected_left">
				                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;启用标志：</span>
				            </div>
				            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
				            	<input class="input_select xiala" id="qybzLabel" type="text" readonly="readonly" value="==全部=="/>
				            	<input type="hidden" id="qybzName"/>
					            <div class="ul"> 
					            	<div id="qybz_div" class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
									<c:forEach items="${dicList}" var="dic">
							       		<c:if test="${dic['typeCode'] eq 'JCDQYBZ'}">
					                    	<div class="li" data-value="${dic['typeSerialNo']}" onclick="sliders(this)">${dic['typeDesc']}</div>
				                    	</c:if>
							 		</c:forEach>
					            </div>
					         </div>
				        </div>
				        <div class="button_wrap clear_both">
					    	<input id="searchBt" type="button" class="button_blue" value="查询" onclick="doSearch()">
					    	<input type="button" class="button_blue" value="重置" onclick="doReset()">
					    </div>
				    </fieldset>
				    <fieldset style="margin: 20px 0px 0px 0px;">
			      		<legend style="color:#FF3333">查询结果</legend>
				        <div id="dataDiv" class="pg_result">
				            
				        </div>
				     </fieldset> 
		    	</form>
			</div>
	    </div>
	    <jsp:include  page="/common/Foot.jsp"/>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
			//搜索..按钮
		  	function doSearch(){
		  		doGoPage(1);	
		  	}
	
	  		//重置..按钮
	  		function doReset(){
	  			$("#Check_JcdID").val("");//监测点ID
	  			$("#Check_JcdName").val("");//监测点名称
	  			$('#jcdxz_div').trigger("click");//监测点类型
	  			$('#road_div').trigger("click");//道路
	  			$("#cqLabel").val("==全部==");//
	  			$("#cqName").val("");//
	  			$('#qybz_div').trigger("click");//启用标志
	  		}
	 
			//根据页号查询...
			var currentpageNo = 1;
			function doGoPage(pageNo) {
				//获取条件
				var jcdId = $.trim($("#Check_JcdID").val());//监测点ID
				var jcdName = $.trim($("#Check_JcdName").val());//监测点名称
				var jcdxzName = $.trim($("#jcdxzName").val());//监测点类型
				var dlName = $.trim($("#dlName").val());//道路
				var cqName = $.trim($("#cqName").val());//城区
				var qybzName = $.trim($("#qybzName").val());//启用标志
				currentpageNo = pageNo;
				
				//显示进度条
				var index = layer.load();
				
				//提交
				$.ajax({
					url:'<%=basePath%>/jcd/getJcdForPage.do?' + new Date().getTime(),
					method:"post",
					data:{jcdId:jcdId,jcdName:jcdName,jcdxzName:jcdxzName,dlName:dlName,cqName:cqName,qybzName:qybzName,pageNo:pageNo},
					success:function(data){
						//关闭进度条
					    layer.close(index);
						$('#dataDiv').html(data);
					},
					error: function () {//请求失败处理函数
						//关闭进度条
					    layer.close(index);
						layer.msg("查询失败！");
					}
				});
			}
			
			//启用停用
			function qyty(jcdid, qybz) {
				var msg = "确定要" + ((qybz == '1' ? '停用' : '启用')) + "该点位吗？\n请确认！";
				if (confirm(msg) == true) {
					//显示进度条
					var index = layer.load();
					
					//提交
					$.ajax({
						url:'<%=basePath%>/jcd/qyty.do?' + new Date().getTime(),
						method:"post",
						data:{jcdId:jcdid,qybz:qybz},
						success:function(data){
							//关闭进度条
						    layer.close(index);
							if(data.result == "1"){//添加成功！
								layer.msg("操作成功！");
								doGoPage(currentpageNo);//重新加载
							} else if(data.result == "0"){//添加失败！    
								layer.msg("操作失败！");
							}
						},
						error: function () {//请求失败处理函数
							//关闭进度条
						    layer.close(index);
							layer.msg("操作失败！");
						}
					});
				}
			}
			 
			//加载Ztree...
			var setting = {
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				callback: {
					onCheck:onCheck
				},
				data: {
					key :{
						//name:"areaname",
					},
					simpleData: {
						enable: true,
						//idKey: "areano",
						//pIdKey: "suparea",
						rootPid:"00"
					},
				}
			};
			var zTree;
			var treeNodes;
			
			$(function(){
				//异步请求城区信息树
				$.ajax({
					async : false,//同步方法
					cache:false,
					type: 'POST',
					dataType : "json",
					url: "${pageContext.request.contextPath}/jcd/getTreeJson2.do",//请求的action路径
					error: function () {//请求失败处理函数
						layer.msg("请求城区信息树失败！");
					},
					success:function(data){ //请求成功后处理函数。
						treeNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
					}
				});
				$.fn.zTree.init($("#tree"), setting, treeNodes);
				//展开第一个模块
				var zTree = $.fn.zTree.getZTreeObj("tree");
				var nodes = zTree.getNodes();//获取节点
				if (nodes.length > 0) {
					zTree.expandNode(nodes[0], true, false);
				}
				
				//首次加载
				$('#searchBt').trigger("click");//
			});
			
			//checkbox oncheck回调函数
			function onCheck(event,treeId,treeNode){
				//遍历树种所有的选中节点，把名字显示在input中去
				//获取选中的节点的id，保存ids
				var treeObj = $.fn.zTree.getZTreeObj("tree");
				var nodes = treeObj.getCheckedNodes(true);	
				
				var flag = "1";//第一个选项
				var mcs = "";
				var ids = "";
				for(var i=0;i < nodes.length;i++){
				    //去掉父节点
				    if(nodes[i].isParent) continue;
				    
					if(flag == "1"){
						flag = "0";
						mcs = nodes[i].name;
						ids = nodes[i].id;
					} else {
						mcs += "," + nodes[i].name;
						ids += "," + nodes[i].id;
					}
				}
				$("#cqLabel").val(mcs);//名称
				$("#cqName").val(ids);//ID
			};
			//控制树的显示
			function showTree(){
				var ul = $("#dropdown .ul"); 
		        if(ul.css("display")=="none"){ 
		            ul.slideDown("fast"); 
		            $(".mask").show();
		        }else{ 
		            ul.slideUp("fast"); 
		            $(".mask").hide();
		        } 
			}
	</script>
</html>