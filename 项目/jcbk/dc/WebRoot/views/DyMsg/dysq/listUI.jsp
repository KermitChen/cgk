<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
	<head>
	    <base href="<%=basePath%>">
	    <title>订阅申请管理</title>
	    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	    <link href="<%=basePath%>common/js/activiti/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp"/>
	    <div id="divTitle">
			<span id="spanTitle">当前位置：订阅管理&gt;&gt;订阅申请管理</span>
		</div>
	    <form name="form" id="form0" action="" method="post">
	    <div class="content">
	    	<div class="content_wrap">
	        	<div class="slider_body">
					<div class="slider_selected_left">
						<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订阅类型：</span>
					</div>
					<div id="dropdown_quanxuan" class="slider_selected_right dropdown dropdown_all">
						<div class="input_select xiala">
							<div id="role_type_downlist" class='multi_select'>
								<input type="hidden" name="Check_dylx" id="dylx_selects" value=""/>
								<a class="xiala_duoxuan_a"></a>
							</div>
						</div>
					</div>
				</div>
		        
		        <div class="slider_body">
			        <div class="slider_selected_left">
			            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;记录状态：</span>
			        </div>
					<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
				     	<input class="input_select xiala" id="xiala2" type="text" readonly="readonly" value="==全部=="/>
				     	<input type="hidden" id="xiala22" name="Check_jlzt" value="${c.typeSerialNo }" /> 
			            <div class="ul"> 
			            	  <div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
			            	  <c:forEach items= "${jlztList }" var="c">
			            	  <c:if test="${c.typeSerialNo eq Check_jlzt }">
			            	   	<script>
			            		 	$("#xiala2").val("${c.typeDesc }");
			            		 	$("#xiala22").val("${c.typeSerialNo }");
			            	   	</script>
			            	  </c:if> 
			                    <div class="li" data-value="${c.typeSerialNo }" onclick="sliders(this)"><a rel="2">${c.typeDesc }</a></div> 
							 </c:forEach>
			            </div>
		        	</div>
		        </div>
		        
		        <div class="slider_body"> 
		            <div class="slider_selected_left">
		                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批结果：</span>
		            </div>
					<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
					     <input class="input_select xiala" id="xiala1" type="text" readonly="readonly" value="==全部=="/>
					     <input type="hidden" id="xiala11" name="Check_spjg" value="${s.value }" /> 
			            <div class="ul"> 
			            	<div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
			            	  <c:forEach items= "${spjgList }" var="s" >
			            	  <c:if test="${s.typeSerialNo eq Check_jlzt }">
			            	   	<script>
			            		 	$("#xiala1").val("${s.typeDesc}");
			            		 	$("#xiala11").val("${s.typeSerialNo}");
			            	   	</script>
			            	  </c:if> 
			                    <div class="li" data-value="${s.typeSerialNo }" onclick="sliders(this)"><a rel="2">${s.typeDesc }</a></div> 
							 </c:forEach>
			            </div>
			         </div>
		         </div>
		        
				<div class="slider_body" >
				  <div class="slider_selected_left">
					<span>申请起始日期：</span>
				  </div>
					<div class="slider_selected_right">
					  <div class="demolist">
					    <input id="Check_startTime" class="inline laydate-icon" name="Check_startTime" value="${Check_startTime }" readonly="readonly" 
							onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
					  </div>
					</div>
				</div>
	
				<div class="slider_body" >
				  <div class="slider_selected_left">
					<span>至：</span>
				  </div>
					<div class="slider_selected_right">
					  <div class="demolist" style="">
					    <input id="Check_endTime" class="inline laydate-icon" name="Check_endTime" value="${Check_endTime }" readonly="readonly" 
							onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
					  </div>
					</div>
				</div>        
		         
		        <div class="button_wrap clear_both">
			    	<input type="button" class="button_blue" value="查询" onclick="doSearch()">
			    	<input type="button" class="button_blue" value="重置" onclick="doReset()">
			    	<input type="button" class="button_blue" value="新增" onclick="doAddUI()">
			    	<input type="button" class="button_blue" value="导出Excel" onclick="doExport()">
			    </div>
			    
		        <div class="pg_result">
		            <table>
		                <thead>
		                    <tr>
	                            <td align="center">序号</td>
	                            <td align="center">订阅人姓名</td>
	                            <td align="center">有效起始时间</td>
	                            <td align="center">有效截止时间</td>
	                            <td align="center">订阅类型</td>
	                            <td align="center">记录状态</td>
	                            <td align="center">审批结果</td>
	                            <td align="center">业务状态</td>
	                            <td align="center">操作</td>
	                            <td align="center">当前节点</td>
		                    </tr>
		                </thead>
		                <tbody>
	                        <c:forEach var="dyxxsp" items="${pageResult.items }" varStatus="status">
	                        	<c:set var="dyxx" value="${dyxxsp.dyxx }"></c:set>
	                        	<c:set var="task" value="${dyxxsp.task }" />
								<c:set var="pi" value="${dyxxsp.processInstance }" />
	                        	<tr>
	                        		<td>${status.index+1} </td>
	                        		<td>${dyxx.jyxm }</td>
	                        		<td>${dyxx.qssj }</td>
	                        		<td>${dyxx.jzsj }</td>
	                        		<td>
	                        			<c:forEach items="${dylxList }" var="c">
	                        				<c:if test="${c.typeSerialNo eq dyxx.dylx }">${c.typeDesc }</c:if>
	                        			</c:forEach>                        			
	                        		</td>
	                        		<td>
	                        			<c:forEach items="${jlztList }" var="c">
	                        				<c:if test="${c.typeSerialNo eq  dyxx.jlzt }">${c.typeDesc }</c:if>
	                        			</c:forEach>                        			
	                        		</td>
	                        		<td>
	                        			<c:forEach items="${spjgList }" var="c">
	                        				<c:if test="${c.typeSerialNo eq  dyxxsp.spjg }">${c.typeDesc }</c:if>
	                        			</c:forEach>                        			
	                        		</td>
	                        		<td>
	                        			<c:forEach items="${ywztList }" var="c">
	                        				<c:if test="${c.typeSerialNo eq  dyxx.ywzt }">${c.typeDesc }</c:if>
	                        			</c:forEach>                        			
	                        		</td>
	                        		<td>
	                        			<c:if test="${!empty task.id }">
	                        				<a id="detail${id }" href="javascript:doDetail(${dyxx.id },${task.id })">详情</a>
	                        			</c:if>
	                        			<c:if test="${empty task.id }">
	                        				<a id="detail${id }" href="javascript:doDetail(${dyxx.id },'')">详情</a>
	                        			</c:if>
	                        			|
	                        			<a id="detail${id }" href="javascript:doDetailEditUI(${dyxx.id })">修改</a>
	                        			<c:if test="${pi.suspended eq false}">
											| <a onclick="updateState('suspend','${pi.id}','${dyxx.id}')">取消申请</a>
										</c:if>
	                        		</td>
	                        		<td align="center">
										<a class="trace"  pid="${pi.id }" pdid="${pi.processDefinitionId}" title="点击查看流程图">${task.name }</a>
									</td>
	                        	</tr>
	                        </c:forEach>
		                </tbody>
		            </table>
				<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
		        </div>
				</div>
	        </div>
	    </form>
	    <input type="hidden" id="jcdid" value=""/>
	    <jsp:include page="/common/Foot.jsp"/>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
	<script src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
		$(function() {
		    // 跟踪
		    $('.trace').click(graphTrace);
		});
		
		//跳转到订阅详情编辑页面
		function doDetailEditUI(id){
			var url="${pageContext.request.contextPath}/dy/getDetailEditUI.do?id="+id;
			layer.open({
	           type: 2,
	           title: '订阅信息详情',
	           shadeClose: false,
	           shade: 0,
	           area: ['800px', '620px'],
	           content: url //iframe的url
	       }); 
		}
		
		//查看详情弹出层
		function doDetail(id,taskId){
			var url = "${pageContext.request.contextPath}/dy/getDetail.do?id="+id+"&taskId="+taskId;
		    layer.open({
		    	type: 2,
		    	title: '订阅信息详情',
		    	shadeClose: false,
		   		shade: 0,
		    	area: ['800px', '620px'],
		    	content: url //iframe的url
		   	}); 
		}
	
		var list_url = "dy/findDy.do";
		//搜索..按钮
	  	function doSearch(){
	  		//重置页号(两种写法DOM,JQuery)
	  		//document.getElementById("pageNo").value=1;
	  		$("#pageNo").val(1);
	  		document.forms[0].action = list_url;
	  		document.forms[0].submit();	  		
	  	}
	
		//重置..按钮
		function doReset(){
			//订阅类型清空
			$('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
				$(this).children("input").attr("checked",false);
				$(this).children("input").trigger("change");
			});
			$("#xiala1").val("==全部==");
			$("#xiala11").val("");
			$("#xiala2").val("==全部==");
			$("#xiala22").val("");
			$("#Check_startTime").val("");
			$("#Check_endTime").val("");
		}
	
		//根据页号查询...
		function doGoPage(pageNo) {
			document.getElementById("pageNo").value = pageNo;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		
		//新增..按钮
		function doAddUI(){
			//跳转到新增页面
			var url_addUI = "${pageContext.request.contextPath}/dy/toAddUI.do";
	/* 		layer.open({
		           type: 2,
		           title: '<font style="border:16px">新增订阅信息</font>',
		           shadeClose: false,
	  			   shade: [0],
		           area: ['1000px', '600px'],
		           content: url_addUI //iframe的url
		    }); */ 
			$("#form0").attr("action",url_addUI);
		    $("#form0").submit();
		}
		
		//文档加载的时候，给多选下拉框赋值
		$(function(){
		    var value = []; 
			var data = [];
		    var dicJson = $.parseJSON('${spjgList1 }');
			for(var i=0;i < dicJson.length;i++){
				value.push(dicJson[i].typeSerialNo);
				data.push(dicJson[i].typeDesc);
			}
		    $('.multi_select').MSDL({
				'value': value,
		      	  'data': data,
		    });
		    //条件回显，勾选上次选定的项
		    var bkfwValue = '${Check_dylx}'.split(";"); 
		    $('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
				for(var i in bkfwValue){
					if(bkfwValue[i] == $(this).children("input").val()){
						$(this).children("input").attr("checked","checked");
						$(this).children("input").trigger("change");
					};
				};
			});
			/* //文档加载时，调用此方法，测试页面实时接收
			$.ajax({
				url:"${pageContext.request.contextPath}/dyss/addDyssItem.do",
				type:'post',
				success:function(data){
					console.log(data);
				}
			}); */
		});
		
		//取消申请
		function updateState(state,processInstanceId,id){
			if(!confirm("确认要取消订阅申请吗？")){
				return;
			}
			// 发送请求更新流程状态
		    $.post('${pageContext.request.contextPath}/dy/update.do?state=' + state+'&processInstanceId='+processInstanceId+'&id='+id
		    , function(resp) {
		        if (resp == 'success') {
					alert("订阅申请取消成功");
		            location.reload();
		        } else {
		            alert('操作失败,请联系管理员');
		        }
		    });
		}
		
		//导出excel
		function doExport(){
		    var	url = "${pageContext.request.contextPath}/dy/exportDyExcel.do";
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	</script>
</html>