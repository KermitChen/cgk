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
		<title>用户信息管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-用户信息管理">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<style type="text/css">
			.layer_span:hover {
				color:#FF0000 !important;
			}
		</style>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript">
			window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    	</script>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp"/>
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<div id="divTitle">
			<span id="spanTitle">当前位置：系统管理&gt;&gt;用户信息管理</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
	    		<fieldset style="margin: 0px 0px 0px 0px;">
					<legend  style="color:#FF3333">查询条件（注：仅可查询本部门及其子孙部门下的用户）</legend>
			        <div class="slider_body">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名：</span>
		                </div>
		                <div class="slider_selected_right" style="">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="loginname" type="text" class="slider_input">
		                            <a id="loginname" class="empty"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
		        	<div class="slider_body">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名：</span>
		                </div>
		                <div class="slider_selected_right" style="">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="username" type="text" class="slider_input">
		                            <a id="username" class="empty"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
			        <div class="slider_body">
						<div class="slider_selected_left">
						    <span id="deptTree_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;隶属部门：</span>
						</div>
						<div id="deptDiv" class="slider_selected_right">
						    <input class="input_select xiala" id="dept_downlist" readonly="readonly" type="text" value="==全部==" onclick="javascript:showTree();"/> 
						    <input type="hidden" id="dept_select" value=""/>
						    <div id="treeUl" class="ul"> 
						       <ul id="deptTree" class="ztree" style="height: 200px;"></ul>
						    </div> 
						</div>
					</div>
					<div class="slider_body">
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;角色类型：</span>
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
					<div class="slider_body">
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户角色：</span>
						</div>
						<div id="role_allselect" class="slider_selected_right dropdown dropdown_all">
							<div class="input_select xiala">
								<div id="role_downlist" class='multi_select'>
									<input type="hidden" id="role_select" value=""/>
									<a class="xiala_duoxuan_a"></a>
								</div>
							</div>
						</div>
					</div>
					<div class="slider_body">
						<div class="slider_selected_left">
						    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;信息来源：</span>
						</div>
						<div id="infoSource_allselect" class="slider_selected_right dropdown dropdown_all">
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
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询类型：</span>
			            </div>
			            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			            	<input class="input_select xiala cxlb" type="text" id="xiala" readonly="readonly" value="按天查询"/>
		               		<input type="hidden" id="cxlb" name="cxlb" value="${cxlb }"/>
		                	<div class="ul"> 
			                    <div id="queryByDay" class="li xiala_div" data-value="1" onclick="sliders(this)">按天查询</div> 
			                    <div class="li xiala_div" data-value="2" onclick="sliders(this)">按月查询</div> 
			                    <div class="li xiala_div" data-value="3" onclick="sliders(this)">自定义查询</div> 
		                	</div>
		            	</div>
		        	</div>
					<div class="slider_body cxlb_style1" <c:if test="${cxlb ne '1'}">style="display:none;"</c:if>>
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建日期：</span>
						</div>
				 		<div class="slider_selected_right">
				  			<div class="demolist" style="">
				    			<input class="inline laydate-icon" id="cxrq" name="cxrq" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
				  			</div>
						</div>
					</div>
					<div class="slider_body cxlb_style2" <c:if test="${cxlb ne '2'}">style="display:none;"</c:if>>
				  		<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建月份：</span>
				  		</div>
				  		<div class="slider_selected_right dropdown dropdowns little_slider_selected_left" id="cp_2" onclick="slider(this)">
		                	<input class="input_select little_xiala" id="yearShow" readonly="readonly" style="text-align: center;" type="text"/> 
		               	 	<input type="hidden" id="year" name="year"/>
		                	<div class="ul"> 
		                		<%
		                			for(int i=Integer.parseInt((String)request.getAttribute("year"));i >= 2014;i--){
		                		%>
			                    		<div class="li" data-value="<%=i %>" onclick="sliders(this)"><%=i %></div>
			                    <%
			                    	}
			                    %>
		              	  	</div> 
	           	 		</div>
	           	 		<div class="font_year">年</div>
	           	 		<div class="slider_selected_right dropdown dropdowns little_slider_selected_right" id="cp_2" onclick="slider(this)">
		                	<input class="input_select little_xiala" id="monthShow" readonly="readonly" style="text-align: center;" type="text"/> 
		                	<input type="hidden" id="month" name="month"/>
		                	<div class="ul">
		                		<%
		                			for(int i=1;i <= 12;i++){
		                		%>
			                    		<div class="li" data-value="<%=i >= 10? i : "0" + i%>" onclick="sliders(this)"><%=i >= 10? i : "0" + i%></div>
			                    <%
			                    	}
			                    %>
		                	</div> 
	           	 		</div>
	           	 		<div class="font_month">月</div>
					</div>
					<div class="slider_body cxlb_style3" <c:if test="${cxlb ne '3'}">style="display:none;"</c:if>>
				  		<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建时间：</span>
				  		</div>
						<div class="slider_selected_right">
					  		<div class="demolist" style="">
					    		<input class="inline laydate-icon" id="kssj" name="kssj" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
					  		</div>
						</div>
					</div>
					<div class="slider_body cxlb_style3" <c:if test="${cxlb ne '3'}">style="display:none;"</c:if>>
				  		<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至</span>
				  		</div>
						<div class="slider_selected_right">
					  		<div class="demolist" style="">
					    		<input class="inline laydate-icon" id="jssj" name="jssj" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
					  		</div>
						</div>
					</div>
			        <div class="button_wrap clear_both" style="height: 50px;">
				    	<input id="searchBt" type="button" class="button_blue" value="查询">
				    	<input id="resetBt" type="button" class="button_blue" value="重置">
				    	<input id="addUserBt" type="button" class="button_blue" value="新增">
				    	<input id="batcheGrantRoleBt" type="button" class="button_blue" value="批量授权">
				    	<input id="batcheDeleteUserBt" type="button" class="button_blue" value="批量删除">
				    </div>
				</fieldset>
			    <fieldset style="margin: 20px 0px 0px 0px;">
			      	<legend style=" color:#FF3333">查询结果（注：双击记录可查看详情）</legend>
			        <div id="dataDiv" class="pg_result">
			        
			        </div>
			   </fieldset>
			</div>
   		</div>
    	<jsp:include page="/common/Foot.jsp"/>
	</body>
		<script type="text/javascript" src="<%=basePath%>common/js/sb/simplefoucs.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
		<script type="text/javascript">
			var currentpageNo = 1;
			//根据页号查询
			function doGoPage(pageNo) {
				//检查并组装时间
				if(!changeTime()){
					return;
				}
			
				//获取条件
				var loginName = $.trim($("#loginname").val());//用户名
				var username = $.trim($("#username").val());//姓名
				var deptno = $.trim($("#dept_select").val());;//所属部门
				var roleType = $.trim($("#role_type_select").val());//角色类型
				var roleId = $.trim($("#role_select").val());//角色Id
				var infoSource = $.trim($("#infoSource_select").val());//信息来源
				var startTime = $.trim($("#kssj").val());//起始时间
				var endTime = $.trim($("#jssj").val());//截止时间
				currentpageNo = pageNo;
				
				//显示进度条
				var index = layer.load();
				
				//提交
				$.ajax({
					url:'<%=basePath%>/user/getUserForPage.do?' + new Date().getTime(),
					method:"post",
					data:{loginName:loginName,username:username,deptno:deptno,roleType:roleType,roleId:roleId,infoSource:infoSource,startTime:startTime,endTime:endTime,pageNo:pageNo},
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
				//重新加载数据
				doGoPage(currentpageNo);
			}
			
			//根据不同选择类型填充时间
			function changeTime(){
				var cxlb = $("#cxlb").val();
				if(cxlb == "1"){//按天
					var day = $("#cxrq").val();
					if(day != null && day != ""){
						var now = new Date().Format('yyyy-MM-dd');
						var flag = compareTime(now,day);
						if(flag == '1'){
							$("#kssj").val(day + " 00:00:00");
							$("#jssj").val(day + " 23:59:59");
							return true;
						} else if(flag == '2'){
							$("#kssj").val(day + " 00:00:00");
							$("#jssj").val(new Date().Format('yyyy-MM-dd HH:mm:ss'));
							return true;
						} else{
							alert("时间选择错误，不可超过当前日期！");
							return false;
						}
					}
					return true;
				} else if(cxlb == "2"){//按月
					var year = $("#year").val();
					var month = $("#month").val();
					if(year != null && year != "" && month != null && month != ""){
						var now = new Date().Format('yyyy-MM-dd');
						var flag = compareTime(now,year+"-"+month);
						if(flag == '1'){
							var firstDate = new Date(year + "/" + month + "/01 00:00:00");
							var endDate = new Date(year + "/" + month + "/31 23:59:59");
							$("#kssj").val(new Date(firstDate).Format('yyyy-MM-dd HH:mm:ss'));
							$("#jssj").val(new Date(endDate).Format('yyyy-MM-dd HH:mm:ss'));
							return true;
						} else if(flag == '2'){
							var firstDate = new Date(year+"/"+month+"/01 00:00:00");
							$("#kssj").val(new Date(firstDate).Format('yyyy-MM-dd HH:mm:ss'));
							$("#jssj").val(new Date().Format('yyyy-MM-dd HH:mm:ss'));
							return true;
						} else{
							alert("时间选择错误，不可超过当前月份！");
							return false;
						}
					}
					return true;
				} else{
					var kssj = $("#kssj").val();
					var jssj = $("#jssj").val();
					if(kssj != null && kssj != "" && jssj != null && jssj != ""){
						var now = new Date().Format('yyyy-MM-dd HH:mm:ss');
						if(!checkTime(kssj, jssj)){
							alert("时间选择错误，起始时间不可大于截止时间！");
							return false;
						}
						if(!checkTime(kssj, now)){
							alert("时间选择错误，起始时间不可大于当前时间！");	
							return false;
						}
						if(checkTime(now, jssj)){
							$("#jssj").val(now);
						}
					}
					return true;
				}
			}
			
			//记录全选、反选总checkbox...
			function doSelectAll() {
				$("input[name=ids]").prop("checked", $("#selectAll").is(":checked"));
			}
	  		
	  		//删除指定记录
	  		function doDelete(id){
				var msg = "确定要删除所选的数据吗？\n请确认！";
				if (confirm(msg) == true){
		  			//提交
					$.ajax({
						url:'<%=basePath%>/user/deleteUser.do?' + new Date().getTime(),
						method:"post",
						data:{id:id},
						success:function(data){
							if(data.result == "1"){//删除成功
								doGoPage(1);//重新查询
							} else{//删除失败
								alert("删除失败！");
							}
						},
						error: function () {//请求失败处理函数
							alert('删除失败！');
						}
					});
				}
	  		}
	  		
	  		//编辑指定记录
	  		function doEdit(id){
				//window.location = '<%=basePath%>user/initUpdateUser.do?id=' + id;
				var url ="${pageContext.request.contextPath}/user/initUpdateUser.do?id=" + id;
				layer.open({
				  type: 2,
				  title: '修改用户信息',
				  area:['826px', '500px'],
				  //shade: 0,
				  shadeClose:true,
				  content: url
				});
	  		}
	  		
	  		//显示信息详情
	  		function doDetail(id){
	  			var url ="${pageContext.request.contextPath}/user/userDetail.do?id=" + id;
				layer.open({
				  type: 2,
				  title: '用户信息详情',
				  area:['826px', '500px'],
				  //shade: 0,
				  shadeClose:true,
				  content: url
				});
	  		}
	  		
	  		//重置密码
	  		function doResetPassword(id){
				var msg = "确定要重置该用户的密码吗？\n请确认！";
				if (confirm(msg) == true){
		  			//提交
					$.ajax({
						url:'<%=basePath%>/user/resetPassword.do?' + new Date().getTime(),
						method:"post",
						data:{id:id},
						success:function(data){
							if(data.result == "1"){//重置成功
								alert("重置成功！");
							} else{//重置失败
								alert("重置失败！");
							}
						},
						error: function () {//请求失败处理函数
							alert('重置失败！');
						}
					});
				}
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
     
			$(document).ready(function(){
				//时间查询方式
				$(".xiala_div").click(function(){
					var value = $(this).attr("data-value");
					if(value == "1"){
						$(".cxlb_style1").show();
						$(".cxlb_style2").hide();
						$(".cxlb_style3").hide();
					} else if(value == "2"){
						$(".cxlb_style1").hide();
						$(".cxlb_style2").show();
						$(".cxlb_style3").hide();
					} else{
						$(".cxlb_style1").hide();
						$(".cxlb_style2").hide();
						$(".cxlb_style3").show();
					}
				});
				
				//角色
				var value = []; 
				var data = [];
				var roleJson = jQuery.parseJSON('${roleJson}');
				for(var i=roleJson.length-1;i >= 0;i--){
					value.push(roleJson[i].id);
					data.push(roleJson[i].roleName);
				}
			    $('#role_downlist').MSDL({
					'value': value,
			      	'data': data
			    });
			    
			    //角色类型
			    var value = []; 
				var data = [];
			    var dicJson = jQuery.parseJSON('${dicJson}');
				for(var i=0;i < dicJson.length;i++){
					//if(dicJson[i].typeCode == '1001' && parseInt(dicJson[i].typeSerialNo.substring(0, 2)) <= parseInt(<%=(userObj.getPosition() == null || "".equals(userObj.getPosition().trim())?0:userObj.getPosition().trim().substring(0, 2)) %>)){
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
			    
			    //信息来源
			    var value = []; 
				var data = [];
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
			
		  		//根据条件获取符合条件的数据
				$("#searchBt").click(function() {
					doGoPage(1);
				});
				//默认首次查询
				$("#role_type_select").val("");//角色类型
				$("#role_select").val("");//角色Id
				$("#infoSource_select").val("");//信息来源
				$("#queryByDay").trigger("click");//时间类型
				$("#searchBt").trigger("click");
				
				//重置..按钮
				$("#resetBt").click(function() {
					$("#loginname").val("");//用户名
		  			$("#username").val("");//姓名
		  			
		  			$('#queryByDay').trigger("click");//时间类型
		  			$("#cxrq").val("");//日期
		  			
		  			//月份
		  			$("#yearShow").val("");
		  			$("#year").val("");
		  			$("#monthShow").val("");
		  			$("#month").val("");
		  			
		  			//时间范围
		  			$("#kssj").val("");
		  			$("#jssj").val("");
		  			
		  			//重新获取部门信息，并初始部门树
		  			$("#dept_downlist").val("==全部==");//部门名称
					$("#dept_select").val("");//部门ID
					//loadDeptData();
					
					//刷新页面
					window.location.reload();
				});
				
				//批量删除...按钮
				$("#batcheDeleteUserBt").click(function() {
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
						var msg = "确定要删除所选的数据吗？\n请确认！";
						if (confirm(msg) == true) {
							//提交
							$.ajax({
								url:'<%=basePath%>/user/batcheDeleteUser.do?' + new Date().getTime(),
								method:"post",
								traditional:true,
								data:{ids:chk_value},
								success:function(data){
									if(data.result == "1"){//删除成功
										doGoPage(1);//重新查询
									} else{//删除失败
										alert("删除失败！");
									}
								},
								error: function () {//请求失败处理函数
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
				$("#addUserBt").click(function() {
					//window.location = '<%=basePath%>user/initAddUser.do?' + new Date().getTime();
					var url ="${pageContext.request.contextPath}/user/initAddUser.do?" + new Date().getTime();
					layer.open({
					  type: 2,
					  title: '新增用户信息',
					  area:['826px', '500px'],
					  //shade: 0,
					  shadeClose:true,
					  content: url
					});
				});
				
				//批量授权...按钮
				$("#batcheGrantRoleBt").click(function() {
					//window.location = '<%=basePath%>user/initGrantRole.do?' + new Date().getTime();
					var url ="${pageContext.request.contextPath}/user/initGrantRole.do?" + new Date().getTime();
					layer.open({
					  type: 2,
					  title: '批量授权',
					  area:['426px', '400px'],
					  //shade: 0,
					  shadeClose:true,
					  content: url
					});
				});
				
				//提示信息
				$("#deptTree_span").mouseover(function(){
					showTip("仅可选本部门及其子孙部门！", "#deptTree_span");
				});
				$("#deptTree_span").mouseleave(function(){
					layer.closeAll('tips');
				});
			});
		</script>
		
		<!-- 部门树-->
		<script type="text/javascript">
		<!--
			//加载顶级部门信息
			function loadDeptData() {
				$.ajax({
					url:'<%=basePath%>/dept/getMaxDeptToJsonWriter.do?' + new Date().getTime(),
					method:"post",
					dataType:"json",
					success:function(data){
						//初始化树
						$.fn.zTree.init($("#deptTree"), setting, data);
						
						//检查顶级部门是否可选
						var treeObj = $.fn.zTree.getZTreeObj("deptTree");
						var nodes = treeObj.getNodesByParamFuzzy("systemNo", "68");
						for(var i=0;i < nodes.length;i++){
						    var fdStart = nodes[i].systemNo.indexOf(<%=userObj.getSystemNo() %>);
							if(fdStart != 0){
								nodes[i].chkDisabled = true;
								treeObj.updateNode(nodes[i]);
							}
						}
					},
					error: function () {//请求失败处理函数
						alert('加载部门信息失败！');
					}
				});
			}
			 
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
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "", "N": "" }
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
		
			//点击树中某一项时响应
			//function onClickTree(event, treeId, treeNode, clickFlag) {
				//显示部门名称，并获取部门ID
			//	$("#dept_downlist").val(treeNode.deptName);//部门名称
			//	$("#dept_select").val(treeNode.deptNo);//部门ID
				
				//隐藏树
			//	showTree();
			//}
			
			function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				var myChildNodes = new Array();
				for (var i=0;i < childNodes.length;i++) {
					var fdStart = childNodes[i].systemNo.indexOf(<%=userObj.getSystemNo() %>);
					if(fdStart != 0){
						childNodes[i].chkDisabled = true;
					}
					myChildNodes[i] = childNodes[i];
				}
				return myChildNodes;
			}
			
			//选中某一项
			function onCheck(event, treeId, treeNode){
				var deptTree = $.fn.zTree.getZTreeObj("deptTree");
				var nodes = deptTree.getCheckedNodes(true);
				
				var flag = "1";//第一个选项
				var mcs = "";
				var ids = "";
				for(var i=0;i < nodes.length;i++){
					if(flag == "1"){
						flag = "0";
						mcs = nodes[i].nodeName;
						ids = nodes[i].nodeNo;
					} else {
						mcs += "," + nodes[i].nodeName;
						ids += "," + nodes[i].nodeNo;
					}
				}
				$("#dept_downlist").val(mcs);//部门名称
				$("#dept_select").val(ids);//部门ID
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
				//加载部门json数据
				loadDeptData();
				
				//单选点击空白关闭
				$("body").click(function(event){
					//不是点击dept_downlist，则隐藏树
					if(event.target.id != "dept_downlist" && event.target.nodeName != "SPAN"){
						$("#treeUl").slideUp("fast");
					}
				});
			});
		//-->
		</script>
</html>