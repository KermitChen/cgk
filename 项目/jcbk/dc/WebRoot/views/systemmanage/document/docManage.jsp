<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>帮助文档管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-帮助文档管理">
		<script type="text/javascript">
			window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    	</script>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp"/>
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<div id="divTitle">
			<span id="spanTitle">当前位置：系统管理&gt;&gt;帮助文档管理</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
	    		<fieldset style="margin: 0px 0px 0px 0px;">
					<legend  style="color:#FF3333">查询条件</legend>
					<div class="slider_body">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文档名称：</span>
		                </div>
		                <div class="slider_selected_right" style="">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="fileName" type="text" class="slider_input">
		                            <a id="fileName" class="empty"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
			        <div class="slider_body">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名：</span>
		                </div>
		                <div class="slider_selected_right" style="">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="loginName" type="text" class="slider_input">
		                            <a id="loginName" class="empty"></a>
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
		                            <input id="userName" type="text" class="slider_input">
		                            <a id="userName" class="empty"></a>
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
				    	<input id="addBt" type="button" class="button_blue" value="上传">
				    	<input id="batcheDeleteBt" type="button" class="button_blue" value="批量删除">
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
				var loginName = $.trim($("#loginName").val());//录入人用户名
				var userName = $.trim($("#userName").val());//录入人姓名
				var fileName = $.trim($("#fileName").val());//文件名
				var startTime = $.trim($("#kssj").val());//起始时间
				var endTime = $.trim($("#jssj").val());//截止时间
				currentpageNo = pageNo;
				
				//显示进度条
				var index = layer.load();
				
				//提交
				$.ajax({
					url:'<%=basePath%>/helpDoc/getWjjlbForPage.do?' + new Date().getTime(),
					method:"post",
					data:{loginName:loginName,userName:userName,fileName:fileName,startTime:startTime,endTime:endTime,pageNo:pageNo},
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
					//显示进度条
					var index = layer.load();
				
		  			//提交
					$.ajax({
						url:'<%=basePath%>/helpDoc/deleteWjjlb.do?' + new Date().getTime(),
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
				//window.location = '<%=basePath%>helpDoc/initUpdateWjjlb.do?id=' + id;
				var url ="${pageContext.request.contextPath}/helpDoc/initUpdateWjjlb.do?id=" + id;
					layer.open({
					  type: 2,
					  title: '修改帮助文档',
					  area:['426px', '400px'],
					  //shade: 0,
					  shadeClose:true,
					  content: url
				});
	  		}
	  		
	  		//显示信息详情
	  		function doDetail(id){
				var url ="${pageContext.request.contextPath}/helpDoc/wjjlbDetail.do?id=" + id;
				layer.open({
				  type: 2,
				  title: '帮助文档详情',
				  area:['800px', '350px'],
				  //shade: 0,
				  shadeClose:true,
				  content: url
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
			
		  		//根据条件获取符合条件的数据
				$("#searchBt").click(function() {
					doGoPage(1);
				});
				//默认首次查询
				$("#searchBt").trigger("click");
				$('#queryByDay').trigger("click");//时间类型
				
				//重置..按钮
				$("#resetBt").click(function() {
					$("#loginName").val("");//用户名
		  			$("#userName").val("");//姓名
		  			$("#fileName").val("");//文档名称
		  			
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
						var msg = "确定要删除所选的数据吗？\n请确认！";
						if (confirm(msg) == true) {
							//显示进度条
							var index = layer.load();
						
							//提交
							$.ajax({
								url:'<%=basePath%>/helpDoc/batcheDeleteWjjlb.do?' + new Date().getTime(),
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
					//window.location = '<%=basePath%>helpDoc/initAddWjjlb.do?' + new Date().getTime();
					var url ="${pageContext.request.contextPath}/helpDoc/initAddWjjlb.do?" + new Date().getTime();
					layer.open({
					  type: 2,
					  title: '上传帮助文档',
					  area:['426px', '400px'],
					  //shade: 0,
					  shadeClose:true,
					  content: url
					});
				});
			});
		</script>
</html>