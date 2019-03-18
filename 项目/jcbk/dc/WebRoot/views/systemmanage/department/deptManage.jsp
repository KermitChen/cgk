<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	//获取用户信息
	User userObj = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	request.setAttribute("userObj", userObj);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>部门信息管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-部门信息管理">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<style type="text/css">
			.table_content{
				padding-top: 10px;
				overflow: auto;
			    margin: 0 auto;
			    width: 1130px;
				margin-bottom: 50px;
			}
			.table_content_wrap{
				width: 810px;
				float:left;
			}
			.table_content_tree{
				width: 300px;
				height: 450px;
				float:left;
			}
		</style>
		<style type="text/css">
			.layer_span:hover {
				color:#FF0000 !important;
			}
		</style>
		<script type="text/javascript">
			window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    	</script>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp"/>
		<div id="divTitle">
			<span id="spanTitle">当前位置：系统管理&gt;&gt;部门信息管理</span>
		</div>
	    <div class="table_content">
	    	<div class="table_content_wrap" style="margin: 0px 10px 0px 0px;">
	    	<fieldset>
				<legend  style="color:#FF3333">查询条件</legend>
					<div class="slider_body" style="width: 350px;">
		                <div class="slider_selected_left" >
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;部门编号：</span>
		                </div>
		                <div class="slider_selected_right" style="left: 100px;">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="deptNo" type="text" class="slider_input">
		                            <a id="deptNo" class="empty"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
			        <div class="slider_body" style="width: 330px;">
		                <div class="slider_selected_left" >
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;部门名称：</span>
		                </div>
		                <div class="slider_selected_right" style="left: 100px;">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="deptName" type="text" class="slider_input">
		                            <a id="deptName" class="empty"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
		        	<div class="slider_body">
						<div class="slider_selected_left">
						    <span>&nbsp;&nbsp;&nbsp;&nbsp;信息来源：</span>
						</div>
						<div id="infoSource_allselect" class="slider_selected_right dropdown dropdown_all" style="left: 100px;">
							<div class="input_select xiala">
								<div id="infoSource_downlist" class='multi_select'>
									<input type="hidden" id="infoSource_select" value=""/>
									<a class="xiala_duoxuan_a"></a>
								</div>
							</div>
						</div>
					</div>
					<div class="slider_body">
						<div class="slider_selected_left">
						    <span id="jxkh_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;绩效考核：</span>
						</div>
						<div id="jxkh_allselect" class="slider_selected_right dropdown dropdown_all" style="left: 100px;">
							<div class="input_select xiala">
								<div id="jxkh_downlist" class='multi_select'>
									<input type="hidden" id="jxkh_select" value=""/>
									<a class="xiala_duoxuan_a"></a>
								</div>
							</div>
						</div>
					</div>
			        <div class="button_wrap clear_both">
				    	<input id="searchBt" type="button" class="button_blue" value="查询">
				    	<input id="resetBt" type="button" class="button_blue" value="重置">
				    	<input id="addBt" type="button" class="button_blue" value="新增">
				    	<input id="batcheDeleteBt" type="button" class="button_blue" value="批量删除">
				    </div>
			    </fieldset>
			    <fieldset style="margin: 20px 0px 0px 0px;">
			      	<legend style=" color:#FF3333">查询结果（注：双击记录可查看详情）</legend>
			        <div id="dataDiv" class="pg_result" style="padding: 0px;">
			        	
			        </div>
		       </fieldset>
			</div>
			<div class="table_content_tree">
				
			</div>
		</div>
    	<jsp:include page="/common/Foot.jsp"/>
	</body>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>	
		<script type="text/javascript">
		    var currentpageNo = 1;
			//根据页号查询
			function doGoPage(pageNo) {
				//获取条件
				var deptNo = $.trim($("#deptNo").val());//部门编号
				var deptName = $.trim($("#deptName").val());//部门名称
				var infoSource = $.trim($("#infoSource_select").val());//信息来源
				var jxkh = $.trim($("#jxkh_select").val());//绩效考核
				currentpageNo = pageNo;
				
				//显示进度条
				var index = layer.load();
						
				//提交
				$.ajax({
					url:'<%=basePath%>/dept/getDeptForPage.do?' + new Date().getTime(),
					method:"post",
					data:{deptNo:deptNo, deptName:deptName, infoSource:infoSource, jxkh:jxkh, pageNo:pageNo},
					success:function(data){
						//关闭进度条
					    layer.close(index);
						$('#dataDiv').html(data);
					},
					error: function () {//请求失败处理函数
						//关闭进度条
					    layer.close(index);
						alert('查询失败！');
					}
				});
			}
			
			//重新加载
			function reloadData() {
				//加载树
				loadFunctionTree();
				//重新加载数据
				doGoPage(currentpageNo);
			}
			
			//记录全选、反选总checkbox...
			function doSelectAll() {
				$("input[name=ids]").prop("checked", $("#selectAll").is(":checked"));
			}
	  		
	  		//删除指定记录
	  		function doDelete(deptNo){
				var msg = "提示：删除部门将同时删除其子孙部门！确定要删除所选的数据吗？\n请确认！";
				if (confirm(msg) == true){
				    //显示进度条
					var index = layer.load();
				    
		  			//提交
					$.ajax({
						url:'<%=basePath%>/dept/deleteDept.do?' + new Date().getTime(),
						method:"post",
						data:{deptNo:deptNo},
						success:function(data){
							//关闭进度条
					    	layer.close(index);
					    
							if(data.result == "1"){//删除成功
							    //加载树
					            loadFunctionTree();
								doGoPage(1);//重新查询
							} else{//删除失败
								alert("删除失败！");
							}
						},
						error: function () {//请求失败处理函数
						    //关闭进度条
					   	 	layer.close(index);
							alert('删除失败！');
						}
					});
				}
	  		}
	  		
	  		//编辑指定记录
	  		function doEdit(id){
				//window.location = '<%=basePath%>dept/initUpdateDept.do?id=' + id;
				var url ="${pageContext.request.contextPath}/dept/initUpdateDept.do?id=" + id;
				layer.open({
		           type: 2,
		           title: '修改部门信息',
		           shadeClose: false,
		           //shade: 0,
		           area:['826px', '450px'],
		           content: url //iframe的url
		       }); 
	  		}
	  		
	  		//显示信息详情
	  		function deptDetail(id){
				var url ="${pageContext.request.contextPath}/dept/deptDetail.do?id=" + id;
				layer.open({
				  type: 2,
				  title: '部门信息详情',
				  area:['826px', '450px'],
				  //shade: 0,
				  shadeClose:true,
				  content: url
				});
	  		}
	  		
	  		//加载并显示功能列表
	  		function loadFunctionTree(){
				$.ajax({
					url:'<%=basePath%>/dept/getMaxDeptToJson.do?' + new Date().getTime(),
					method: "post",
					success: function(data){
						$('.table_content_tree').html(data);
					},
					error: function() {//请求失败处理函数
						alert('加载部门结构树失败！');
					}
				});
	  		}
	  		
	  		//显示提示
			function showTip(message, id){
				layer.open({
			           type: 4,
			           shade: 0,
			           time:30000,
			           closeBtn: 0,
			           tips: [3, '#758a99'],
			           content: [message, id]
			       });
			}
			
			function doAdd(){
				var url ="${pageContext.request.contextPath}/dept/initAddDept.do?" + new Date().getTime();
				layer.open({
		           type: 2,
		           title: '新增部门信息',
		           shadeClose: false,
		           //shade: 0,
		           area:['826px', '450px'],
		           content: url //iframe的url
		       }); 
			}
     
			$(document).ready(function(){
				//信息来源
			    var value = []; 
				var data = [];
				var dicJson = jQuery.parseJSON('${dicJson}');
				for(var i=0;i < dicJson.length;i++){
					if(dicJson[i].typeCode == '1002'){
						value.push(dicJson[i].typeSerialNo);
						data.push(dicJson[i].typeDesc);
					}
				}
			    $('#infoSource_downlist').MSDL({
					'value': value,
			      	'data': data
			    });
			    
			    //绩效考核
			    $('#jxkh_downlist').MSDL({
					'value': ['0', '1'],
			      	'data': ['否', '是']
			    });
			    
		  		//根据条件获取符合条件的数据
				$("#searchBt").click(function() {
				    //加载树
					loadFunctionTree();
					//获取数据
					doGoPage(1);
				});
				
				//默认首次查询
				$("#infoSource_select").val("");//信息来源
				$("#jxkh_select").val("");//绩效考核
				$("#searchBt").trigger("click");
				
				//重置..按钮
				$("#resetBt").click(function() {
					$("#deptNo").val("");//部门编号
					$("#deptName").val("");//部门名称
					//刷新页面
					window.location.reload();
				});
				
				//批量删除...按钮
				$("#batcheDeleteBt").click(function() {
					//判断是否有checkbox选中，没有checkbox选中返回错误信息
					var checkboxs = $('input[name="ids"]');
					var chk_value = []; 
					if (checkboxs != null) {
						for (var i = 0;i < checkboxs.length;i++) {
							if (checkboxs[i].checked) {
								chk_value.push(checkboxs[i].value); 
							}
						}
					}
						
					if (chk_value.length > 0) {
						var msg = "提示：删除部门将同时删除其子孙部门！确定要删除所选的数据吗？\n请确认！";
						if (confirm(msg) == true) {
						    //显示进度条
							var index = layer.load();
						    
							//提交
							$.ajax({
								url:'<%=basePath%>/dept/batcheDeleteDept.do?' + new Date().getTime(),
								method:"post",
								traditional:true,
								data:{deptNos:chk_value},
								success:function(data){
								    //关闭进度条
					   	 			layer.close(index);
								    
									if(data.result == "1"){//删除成功
									    //加载树
					                    loadFunctionTree();
										doGoPage(1);//重新查询
									} else{//删除失败
										alert("删除失败！");
									}
								},
								error: function () {//请求失败处理函数
								    //关闭进度条
					   	 			layer.close(index);
									alert('删除失败！');
								}
							});
						} else {
							return;
						}
					} else {
						return alert("请先选择需要删除的数据！");
					}
				});
				
				//新增..按钮
				$("#addBt").click(function() {
					doAdd();
					//window.location = '<%=basePath%>dept/initAddDept.do?' + new Date().getTime();
				});
				
				//提示信息
				$("#jxkh_span").mouseover(function(){
					showTip("即参与系统信息签收及信息反馈考核的部门！", "#jxkh_span");
				});
				$("#jxkh_span").mouseleave(function(){
					layer.closeAll('tips');
				});
			});
		</script>
</html>