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
		<title>系统公告列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-系统公告列表">
	</head>
	<body>
		<jsp:include page="/common/Head.jsp"/>
		<div id="divTitle">
			<span id="spanTitle">当前位置：系统管理&gt;&gt;系统公告列表</span>
		</div>
		
	    <div class="content">
	    	<div class="content_wrap">
	    	
			<form action="" id="form1">
	    		<fieldset style="margin: 0px 0px 0px 0px;">
					<legend  style="color:#FF3333">查询条件</legend>
		        	
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
			                <span>公告类型：</span>
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
		        	
			        <div class="slider_body" style="position:relative;z-index:0;clear:both;">
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;起始时间：</span>
			            </div>
			            <div class="slider_selected_right">
			                <div class="demolist">
			                    <input id="startTime" class="inline laydate-icon" name="startTime" value="" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
			                </div>
			            </div>
			        </div>
			        <div class="slider_body">
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;截止时间：</span>
			            </div>
			            <div class="slider_selected_right">
			                <input id="hello" class="laydate-icon" style="display:none">
			                <div class="demolist" style="">
			                    <input id="endTime" class="inline laydate-icon" name="endTime" value="" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
			                </div>
			            </div>
			        </div>
			        
			        <div class="slider_body"> 
			            <div class="slider_selected_left">
			                <span>公告时效：</span>
			            </div>
						<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
				            <input class="input_select xiala" id="xiala2" type="text" value="==全部公告==">
				            <input type="hidden" name="annType" id="xiala22" value="${d.typeSerialNo }" />
				            <div class="ul"> 
				            	  <div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部公告==</a></div>
				            	  <c:forEach items= "${annTime}" var="d">
				                    <div class="li" data-value="${d.typeSerialNo }" onclick="sliders(this)"><a rel="2">${d.typeDesc}</a></div> 
								 </c:forEach>
				            </div>
			        	</div>
			        </div>
			        
			        <div class="button_wrap clear_both">
				    	<input id="searchBt" type="button" class="button_blue" value="查询">
				    	<input id="resetBt" type="button" class="button_blue" value="重置">
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
			<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		  <script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
		<script type="text/javascript">
			window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    		
			//根据页号查询
			function doGoPage(pageNo) {
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
	  		
	  		//编辑指定记录
	  		function doEdit(id){
				var editUrl = "${pageContext.request.contextPath}/sysAnn/toEditUI.do?id="+id;
				layer.open({
					type:2,
					title:'系统公告编辑页面',
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
	  				title:'系统公告详情页面',
	  				shadeClose:false,
	  				shade:0,
	  				area:['800px','600px'],
	  				content:sysAnnDetailUrl
	  			});
	  		}
	  		
			$(document).ready(function(){
		  		//根据条件获取符合条件的数据
				$("#searchBt").click(function() {
					doGoPage(1);
				});
				//默认首次查询
				$("#searchBt").trigger("click");
				
				//重置..按钮
				$("#resetBt").click(function() {
					$("#loginName").val("");//用户名
		  			$("#userName").val("");//姓名
		  			$("#fileName").val("");//文档名称
		  			$("#startTime").val("");//起始时间
		  			$("#endTime").val("");//截止时间
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