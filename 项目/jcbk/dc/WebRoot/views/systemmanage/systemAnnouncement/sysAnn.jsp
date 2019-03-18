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
		<title>系统公告管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-系统公告管理">
	</head>
	<body>
		<jsp:include page="/common/Head.jsp"/>
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<div id="divTitle">
			<span id="spanTitle">当前位置：系统管理&gt;&gt;系统公告管理</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
	    	
			<form action="" id="form1">
	    		<fieldset style="margin: 0px 0px 0px 0px;">
					<legend  style="color:#FF3333">查询条件</legend>
		        	
			        <div class="slider_body">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名：</span>
		                </div>
		                <div class="slider_selected_right" style="">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="loginName" name="loginName" type="text" class="slider_input">
		                            <a id="loginName" class="empty"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
		        	
		        	<div class="slider_body">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文档名称：</span>
		                </div>
		                <div class="slider_selected_right" style="">
		                    <div class="img_wrap">
		                        <div class="select_wrap select_input_wrap">
		                            <input id="fileName" name="fileName" type="text" class="slider_input">
		                            <a id="fileName" class="empty"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
		        	
		        	<div class="slider_body"> 
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公告类型：</span>
			            </div>
						<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
				            <input class="input_select xiala" id="xiala2" type="text" value="==全部==">
				            <input type="hidden" name="annType" id="xiala22" value="${d.typeSerialNo }" />
				            <div class="ul"> 
				            	  <div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
				            	  <c:forEach items= "${annType}" var="d">
					            	  <c:if test="${d.typeSerialNo eq kind }">
					            	   	<script>
					            		 	$("#xiala2").val("${d.typeDesc }");
					            		 	$("#xiala22").val("${d.typeSerialNo }");
					            	   	</script>
					            	  </c:if> 
				                    <div class="li" data-value="${d.typeSerialNo }" onclick="sliders(this)"><a rel="2">${d.typeDesc}</a></div> 
								 </c:forEach>
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
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布日期：</span>
						</div>
				 		<div class="slider_selected_right">
				  			<div class="demolist" style="">
				    			<input class="inline laydate-icon" id="cxrq" name="cxrq" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
				  			</div>
						</div>
					</div>
					<div class="slider_body cxlb_style2" <c:if test="${cxlb ne '2'}">style="display:none;"</c:if>>
				  		<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布月份：</span>
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
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布时间：</span>
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
				    	<input id="addBt" type="button" class="button_blue" value="发布">
				    	<input id="batcheDeleteBt" type="button" class="button_blue" value="批量删除">
				    </div>
				</fieldset>
				<input type="hidden" id="pageNo" name="pageNo" value="1">
				</form>
				
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
			window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    		
    		
			//根据页号查询
			function doGoPage(pageNo) {
				if(!changeTime()){
					return;
				}
				
				$("#pageNo").val(pageNo);
				
				//提交
				layer.load();
				$.ajax({
					url:'<%=basePath%>/sysAnn/getPageRsultByAjax.do?' + new Date().getTime(),
					method:"post",
					data:$("#form1").serialize(),
					dataType:'html',
					success:function(data){
						$('#dataDiv').html(data);
						layer.closeAll();
					},
					error: function () {//请求失败处理函数
						layer.msg('查询失败！');
						layer.closeAll();
					}
				});
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
						url:'<%=basePath%>/sysAnn/deleteOneById.do?' + new Date().getTime(),
						method:"post",
						data:{id:id},
						dataType:'json',
						success:function(result){
							if(result == "1"){//删除成功
								doGoPage(1);//重新查询
								alert('删除成功！');
							} else{//删除失败
								doGoPage(1);
								alert("删除失败！");
							}
						},
						error: function () {//请求失败处理函数
							alert('删除失败！');
						}
					});
				}
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
							alert("时间选择错误，不能超过当前日期！");
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
							alert("时间选择错误，不能超过当前月份！");
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
							alert("时间选择错误，起始时间不能大于截止时间！");
							return false;
						}
						if(!checkTime(kssj, now)){
							alert("时间选择错误，起始时间不能大于当前时间！");	
							return false;
						}
						if(checkTime(now, jssj)){
							$("#jssj").val(now);
						}
					}
					return true;
				}
			}
	  		
	  		//编辑指定记录
	  		function doEdit(id){
				var editUrl = "${pageContext.request.contextPath}/sysAnn/toEditUI.do?id="+id;
				layer.open({
					type:2,
					title:'编辑系统公告',
					shadeClose:false,
					shade:0,
					area:['800px','600px'],
					content:editUrl
				});
	  		}
	  		
	  		//显示信息详情
	  		function doDetail(id){
	  			var sysAnnDetailUrl = '${pageContext.request.contextPath}/sysAnn/getSysAnnDetail.do?id='+id;
	  			layer.open({
	  				type:2,
	  				title:'系统公告详情',
	  				shadeClose:false,
	  				shade:0,
	  				area:['800px','600px'],
	  				content:sysAnnDetailUrl
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
		  			
		  			//公告类型
		  			$("#xiala2").val("==全部==");
					$("#xiala22").val("");
		  			
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
							//提交
							$.ajax({
								url:'<%=basePath%>/sysAnn/batcheDeleteObj.do?' + new Date().getTime(),
								method:"post",
								traditional:true,
								data:{ids:chk_value},
								dataType:'json',
								success:function(data){
									if(data.result == "1"){//删除成功
										alert('批量删除成功！');
										doGoPage(1);//重新查询
									} else{//删除失败
										alert("批量删除失败！");
										doGoPage(1);//重新查询
									}
								},
								error: function () {//请求失败处理函数
									alert('批量删除失败！');
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
					window.location = '<%=basePath%>sysAnn/toAddSysAnnUI.do?' + new Date().getTime();
				});
			});
		</script>
</html>