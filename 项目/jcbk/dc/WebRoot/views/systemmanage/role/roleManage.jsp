<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	//获取用户信息
	User userObj = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	request.setAttribute("userObj", userObj);
	String userPosition = (userObj.getPosition() != null?userObj.getPosition().trim():"");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>角色权限管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-角色权限管理">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<style type="text/css">
			.table_content{
				padding: 10px;
				overflow: auto;
			    margin: 0 auto;
			    width: 1130px;
			    margin-bottom: 50px;
			    min-height: 500px;
			}
			.table_content_wrap{
				width: 760px;
				float:left;
			}
			.table_content_tree{
				width: 340px;
				height: 480px;
				float:left;
			}
		</style>
		<script type="text/javascript">
			window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    	</script>
    	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
    	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
		<script type="text/javascript">
			//根据页号查询
			function doGoPage(pageNo) {
				//获取条件
				var roleName = $.trim($("#roleName").val());//角色名称
				var roleType = $.trim($("#role_type_select").val());//角色类型
				
				//显示进度条
				var index = layer.load();
				
				//提交
				$.ajax({
					url:'<%=basePath%>/role/getAllMyRole.do?' + new Date().getTime(),
					method:"post",
					data:{roleName:roleName, roleType:roleType, pageNo:pageNo},
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
				
				//加载并显示功能列表
				loadFunctionTree();
			}
			
			//记录全选、反选总checkbox...
			function doSelectAll() {
				$("input[name=ids]").prop("checked", $("#selectAll").is(":checked"));
			}
	  		
	  		//删除指定记录
	  		function doDelete(id){
				var msg = "提示：删除角色将会级联删除由该角色派生出来的子孙角色。确定要删除所选的数据吗？\n请确认！";
				if (confirm(msg) == true){
					//显示进度条
					var index = layer.load();
				
		  			//提交
					$.ajax({
						url:'<%=basePath%>/role/deleteRole.do?' + new Date().getTime(),
						method:"post",
						data:{id:id},
						success:function(data){
							//关闭进度条
					    	layer.close(index);
					    	
							if(data.result == "1"){//删除成功
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
				//获取该用户所具有的可分配的功能，并显示于新增页面
				$.ajax({
					url:'<%=basePath%>/role/getFunctionsOfUserToJsonForEdit.do?' + new Date().getTime(),
					method: "post",
					data:{id:id},
					success: function(data){
						$('.table_content_tree').html(data);
					},
					error: function() {//请求失败处理函数
						alert('加载信息失败！');
					}
				});
	  		}
	  		
	  		//信息详情
	  		function doDetail(id){
				//获取该用户所具有的可分配的功能，并显示于新增页面
				$.ajax({
					url:'<%=basePath%>/role/getFunctionsOfUserToJsonForDetail.do?' + new Date().getTime(),
					method: "post",
					data:{id:id},
					success: function(data){
						$('.table_content_tree').html(data);
					},
					error: function() {//请求失败处理函数
						alert('加载信息失败！');
					}
				});
	  		}
	  		
	  		//加载并显示功能列表
	  		function loadFunctionTree(){
				$.ajax({
					url:'<%=basePath%>/role/getFunctionToJson.do?' + new Date().getTime(),
					method: "post",
					success: function(data){
						$('.table_content_tree').html(data);
					},
					error: function() {//请求失败处理函数
						alert('加载功能列表失败！');
					}
				});
	  		}
	  		
	  		//角色类型翻译
			var dicJson = jQuery.parseJSON('${dicJson}');
			function roleTypeFunction(roleType){
				for(var i=0;i < dicJson.length;i++){
					var obj = dicJson[i];
					if(obj.typeCode == '1001' && obj.typeSerialNo == roleType){
						return obj.typeDesc;
					}
				}
				return roleType;
			}
     
			$(document).ready(function(){
				//角色类型
			    var value = []; 
				var data = [];
			    var dicJson = jQuery.parseJSON('${dicJson}');
				for(var i=0;i < dicJson.length;i++){
					//if(dicJson[i].typeCode == '1001' && parseInt(dicJson[i].typeSerialNo.substring(0, 2)) < parseInt(<%=(userObj.getPosition() == null || "".equals(userObj.getPosition().trim())?0:userObj.getPosition().trim().substring(0, 2)) %>)){
					var typeSerialNo = dicJson[i].typeSerialNo;
					if(dicJson[i].typeCode == '1001' && (<%=("".equals(userPosition)?0:userPosition.length()) %> == 5 
						|| (typeSerialNo.length == 2 && typeSerialNo.length == <%=("".equals(userPosition)?0:userPosition.length()) %>)
						|| (typeSerialNo.length == 3 && typeSerialNo.substring(2, 3) == <%=(userPosition.length() == 3?userPosition.substring(2, 3):"99") %>)) 
						&& parseInt(typeSerialNo.substring(0, 2)) < parseInt(<%=("".equals(userPosition)?0:userPosition.substring(0, 2)) %>)){
						value.push(dicJson[i].typeSerialNo);
						data.push(dicJson[i].typeDesc);
					}
				}
			    $('#role_type_downlist').MSDL({
					'value': value,
			      	'data': data
			    });
			    
		  		//根据条件获取符合条件的数据
				$("#searchBt").click(function() {
					//获取数据
					doGoPage(1);
				});
				//默认首次查询
				$("#role_type_select").val("");
				$("#searchBt").trigger("click");
				
				//加载并显示功能列表
				loadFunctionTree();
				
				//重置..按钮
				$("#resetBt").click(function() {
					$("#roleName").val("");//用户名
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
						var msg = "提示：删除角色将会级联删除由该角色派生出来的子孙角色。确定要删除所选的数据吗？\n请确认！";
						if (confirm(msg) == true) {
						    //显示进度条
							var index = layer.load();
					
							//提交
							$.ajax({
								url:'<%=basePath%>/role/batcheDeleteRole.do?' + new Date().getTime(),
								method:"post",
								traditional:true,
								data:{ids:chk_value},
								success:function(data){
									//关闭进度条
					    			layer.close(index);
					    
									if(data.result == "1"){//删除成功
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
					//获取该用户所具有的可分配的功能，并显示于新增页面
					$.ajax({
						url:'<%=basePath%>/role/getFunctionsOfUserToJsonForAdd.do?' + new Date().getTime(),
						method: "post",
						success: function(data){
							$('.table_content_tree').html(data);
						},
						error: function() {//请求失败处理函数
							alert('加载功能列表失败！');
						}
					});
				});
			});
		</script>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp"/>
		<div id="divTitle">
			<span id="spanTitle">当前位置：系统管理&gt;&gt;角色权限管理</span>
		</div>
	    <div class="table_content">
	    	<div class="table_content_wrap" style="margin: 0px 10px 0px 0px;">
	    	<fieldset>
				<legend  style="color:#FF3333">查询条件</legend>
			        <div class="slider_body" style="width: 360px;">
		                <div class="slider_selected_left" >
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;角色名称：</span>
		                </div>
		                <div class="slider_selected_right" style="left: 120px;">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="roleName" type="text" class="slider_input">
		                            <a id="roleName" class="empty"></a>
		                        </div>
		                    </div>
		                </div>
		        	</div>
		        	<div class="slider_body" style="width: 320px;">
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;角色类型：</span>
						</div>
						<div id="role_type_allselect" class="slider_selected_right dropdown dropdown_all">
							<div class="input_select xiala">
								<div id="role_type_downlist" class='multi_select'>
									<input type="hidden" id="role_type_select" value=""/>
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
</html>