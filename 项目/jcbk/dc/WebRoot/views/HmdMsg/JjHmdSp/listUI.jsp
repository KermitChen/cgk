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
    <title>红名单审批</title>
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
        <link href="<%=basePath%>common/js/activiti/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
    <script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
	<script  src="<%=basePath%>common/js/layer/layer.js"></script>
</head>
<body>
	<jsp:include page="/common/Head.jsp"/>
	<div id="divTitle">
		<span id="spanTitle">当前位置：红名单管理&gt;&gt;红名单审批</span>
	</div>
    <form name="form" action="" method="post">
    <div class="content">
    	<div class="content_wrap">
    	
<%-- 	        <div class="slider_body"> 
				<div class="slider_selected_left">
	                    <span>车牌号码：</span>
	            </div>
                <div class="slider_selected_right" >
                    <div class="img_wrap">
                        <div class="select_wrap input_wrap_select">
                            <input id="cphid" class="slider_input" type="text" value="${Check_cpNo }" name="Check_cpNo" />
                            <a class="empty" href="javascript:doCplrUI()"></a>
                        </div>
                    </div>  
               </div>
	        </div>
	        
	        <div class="slider_body">
		        <div class="slider_selected_left">
		            <span>车辆类型：</span>
		        </div>
			<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			     <input class="input_select xiala" id="xiala1" type="text" readonly="readonly" value="==全部=="/>
			     <input type="hidden" id="xiala11" name="Check_cllx" value="${c.typeSerialNo }" /> 
		            <div class="ul"> 
		            	  <div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
		            	  <c:forEach items= "${cpysList }" var="c">
		            	  <c:if test="${c.typeSerialNo eq Check_cllx }">
		            	   	<script>
		            		 	$("#xiala1").val("${c.typeDesc }");
		            		 	$("#xiala11").val("${c.typeSerialNo }");
		            	   	</script>
		            	  </c:if> 
		                    <div class="li" data-value="${c.typeSerialNo }" onclick="sliders(this)"><a rel="2">${c.typeDesc }</a></div> 
						 </c:forEach>
		            </div>
	        	</div>
	        </div>
	        
	        <div class="slider_body"> 
	            <div class="slider_selected_left">
	                <span>记录状态：</span>
	            </div>
				<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
				     <input class="input_select xiala" id="xiala2" type="text" readonly="readonly" value="==全部=="/>
				     <input type="hidden" id="xiala22" name="Check_jlzt" value="${s.typeSerialNo }" /> 
		            <div class="ul"> 
		            	<div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
		            	  <c:forEach items= "${HmdSpZtList }" var="s" >
		            	  <c:if test="${s.typeSerialNo eq Check_jlzt }">
		            	   	<script>
		            		 	$("#xiala2").val("${s.typeDesc}");
		            		 	$("#xiala22").val("${s.typeSerialNo}");
		            	   	</script>
		            	  </c:if> 
		                    <div class="li" data-value="${s.typeSerialNo }" onclick="sliders(this)"><a rel="2">${s.typeDesc }</a></div> 
						 </c:forEach>
		            </div>
		         </div>
	         </div>
	        
	        <div class="slider_body"> 
	            <div class="slider_selected_left">
	                <span>审批结果：</span>
	            </div>
			<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			     <input class="input_select xiala" id="xiala3" type="text" readonly="readonly" value="==全部=="/>
			     <input type="hidden" id="xiala33" name="Check_spjg" value="${s.typeSerialNo }" /> 
	            <div class="ul"> 
	            	<div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
	            	  <c:forEach items= "${HmdSpJgList }" var="s" >
	            	  <c:if test="${s.typeSerialNo eq Check_spjg }">
	            	   	<script>
	            		 	$("#xiala3").val("${s.typeDesc}");
	            		 	$("#xiala33").val("${s.typeSerialNo}");
	            	   	</script>
	            	  </c:if> 
	                    <div class="li" data-value="${s.typeSerialNo }" onclick="sliders(this)"><a rel="2">${s.typeDesc }</a></div> 
					 </c:forEach>
	            </div>
	         </div>
	         </div>
	
	        <div class="button_wrap clear_both">
		    	<input type="button" class="button_blue" value="查询" onclick="doSearch()">
		    	<input type="button" class="button_blue" value="重置" onclick="doReset()">
		    	<input type="button" class="button_blue" value="导出Excel" onclick="doExport()">
		    </div> --%>
		    
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
                            <td align="center">序号</td>
                            <td align="center">车牌号码</td>
                            <td align="center">车辆类型</td>
                            <td align="center">车辆使用人</td>
                            <td align="center">申请人</td>
                            <td align="center">申请时间</td>
                            <td align="center">记录状态</td>
                            <td align="center">审批结果</td>
                            <td align="center">当前节点</td>
                            <td align="center">流程类型</td>
                            <td align="center">流程状态</td>
                            <td align="center">操作</td>
	                    </tr>
	                </thead>
	                <tbody>
                        <c:forEach var="r" items="${pageResult.items }" varStatus="status">
                        <c:set var="task" value="${r.jjhomdsp.task }" />
                        <c:set var="task2" value="${r.jjhomdCx.task }" />
						<c:set var="pi" value="${r.jjhomdsp.processInstance }" />
						<c:set var="pi2" value="${r.jjhomdCx.processInstance }" />
                        	<tr>
                        		<td>${status.index+1} </td>
                        		<td>${r.cphid }</td>
                        		<td>
                        			<c:forEach items="${cpysList }" var="c">
                        				<c:if test="${c.typeSerialNo eq  r.cplx }">${c.typeDesc }</c:if>
                        			</c:forEach>
                        		</td>
                        		<td>${r.clsyz }</td>
                        		<td>${r.sqrxm }</td>
                        		<td>${r.lrsj }</td>
                        		<td>
                        			<c:forEach items="${HmdSpZtList }" var="c">
                        				<c:if test="${c.typeSerialNo eq  r.jlzt }">${c.typeDesc }</c:if>
                        			</c:forEach>                        			
                        		</td>
                        		<td>
                        			<c:forEach items="${HmdSpJgList }" var="c">
                        				<c:if test="${c.typeSerialNo eq  r.jjhomdsp.spjg }">${c.typeDesc }</c:if>
                        			</c:forEach>                          			
                        		</td>
                        		<td>
                        			<c:if test="${r.rwzt eq '01' }">
										<a class="trace" pid="${pi.id }" pdid="${pi.processDefinitionId}" title="点击查看流程图">${task.name }</a>
									</c:if>
                        			<c:if test="${r.rwzt eq '02' }">
										<a class="trace" pid="${pi2.id }" pdid="${pi2.processDefinitionId}" title="点击查看流程图">${task2.name }</a>
									</c:if>
								</td>
								<td>
									<c:if test="${r.rwzt eq '01' }">申请</c:if>
									<c:if test="${r.rwzt eq '02' }">撤销</c:if>
								</td>
                        		<td>
                        			<c:if test="${r.rwzt eq '01' }">
                        				${pi.suspended ? "已挂起" : "正常" }
                        			</c:if>
                        			<c:if test="${r.rwzt eq '02' }">
                        				${pi2.suspended ? "已挂起" : "正常" }
                        			</c:if>
								</td>
                        		<td>
                        			<c:if test="${r.rwzt eq '01' }">
										<c:if test="${empty task.assignee }">
											<a onclick="block()" href="JjHmdSp/taskClaim.do?taskId=${task.id}">签收</a>
										</c:if>
										<c:if test="${not empty task.assignee}">
										<a id="detail${id }" href="javascript:doSp(${r.id },${task.id })"
										style="color:red" onmouseover="this.style.cssText='color:black;'" onmouseout="this.style.cssText='color:red;'">审批</a>
									</c:if>
									</c:if>
									<c:if test="${r.rwzt eq '02' }">
										<c:if test="${empty task2.assignee }">
											<a onclick="block()" href="JjHmdSp/taskClaim.do?taskId=${task2.id}">签收</a>
										</c:if>
										<c:if test="${not empty task2.assignee}">
											<a id="detail${id }" href="javascript:doCxSp(${r.id },${task2.id })"
											style="color:red" onmouseover="this.style.cssText='color:black;'" onmouseout="this.style.cssText='color:red;'">审批</a>
										</c:if>
									</c:if>
<!-- 									<c:if test="${not empty task.assignee && dis.ywzt eq '6'}"> -->
<!-- 										<a href="editDispatched.do?rowId=${dis.bkid }&taskId=${task.id }&conPath=${conPath }" >调整申请</a> -->
<!-- 									</c:if> -->
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
    <jsp:include  page="/common/Foot.jsp"/>
</body>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
<script type="text/javascript">

		$(function() {
		    // 跟踪
		    $('.trace').click(graphTrace);
		    
		});
		
		var list_url = "JjHmdSp/findNewJjHmdSp.do";
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
  			$("#cphid").val("");
  			$("#Check_startTime").val("");
  			$("#Check_endTime").val("");
  			$("#xiala1").val("==全部==");
  			$("#xiala11").val("");
  			$("#xiala2").val("==全部==");
  			$("#xiala22").val("");
  			$("#xiala3").val("==全部==");
  			$("#xiala33").val("");
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
			document.forms[0].action = "JjHmd/toAddUI.do";
	  		document.forms[0].submit();
		}
		
		//弹出审批页面
		function doSp(id,taskid){
			var url = "${pageContext.request.contextPath}/JjHmdSp/getSpUI.do?id="+id+"&taskId="+taskid;
		    layer.open({
		           type: 2,
		           title: '红名单审批',
		           shadeClose: false,
		           shade: 0,
		           area: ['800px', '600px'],
		           content: url //iframe的url
		       }); 
		}
		
		//弹出撤销审批页面
		function doCxSp(id,taskid){
			var url = "${pageContext.request.contextPath}/JjHmdSp/getCxSpUI.do?id="+id+"&taskId="+taskid;
		    layer.open({
		           type: 2,
		           title: '红名单撤销',
		           shadeClose: false,
		           shade: 0,
		           area: ['800px', '600px'],
		           content: url //iframe的url
		       }); 
		}
		
	</script>
</html>