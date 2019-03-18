<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<title>已布控车拦截反馈</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<link href="<%=basePath%>common/js/activiti/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
		<jsp:include page="/common/Head.jsp" />
		<div id="divTitle">
			<span id="spanTitle">当前位置：预警管理&gt;&gt;已布控车拦截反馈</span>
		</div>
		<div class="content">
			<div class="content_wrap">
				<form name="form" action="" method="post">
					<div id="hphm_div" class="slider_body">
		                <div class="slider_selected_left">
		                	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>号牌号码：</span>
		                </div>
		                <div class="slider_selected_right" style="">
		                    <div class="img_wrap">
		                        <div class="select_wrap input_wrap_select">
		                            <input id="cphid" name="hphm" type="text" class="slider_input" value="${hphm }"/>
		                            <a class="empty" href="javascript:doCplrUI()"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
					<div id="hpzl_div" class="slider_body">
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>号牌种类：</span>
			            </div>
			            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
			                <input class="input_select xiala" id="hpzl" type="text" readonly="readonly" value="==全部=="/>
			                <input type="hidden" name="hpzl" value="${hpzl }" /> 
			                <div class="ul">
							    <div class="li" data-value="" onclick="sliders(this)">==全部==</div>
							    <c:forEach items="${dicList}" var="dic">
			            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
				                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc }</div>
			            	  			<c:if test="${dic.typeSerialNo eq hpzl }">
			            	  				<script>$("#hpzl").val('${dic.typeDesc}');</script>
			            	  			</c:if>
			            	  		</c:if> 
							 	</c:forEach>
							</div>
			            </div>
			        </div>
			        
					<div class="button_wrap clear_both" style="height: 60px;">
						<button class="submit_b" onclick="doSearch()">查询</button>
						<button class="submit_b" onclick="doReset()">重置</button>
					</div>
					
					<div class="pg_result">
						<table>
							<thead>
								<tr>
									<td width="40" align="center">序号</td>
									<td width="80" align="center">号牌号码</td>
									<td width="80" align="center">号牌种类</td>
									<td width="80" align="center">布控大类</td>
									<td width="80" align="center">布控类别</td>
									<td width="100" align="center">布控申请人</td>
									<td width="120" align="center">布控申请单位</td>
									<td width="120" align="center">布控申请时间</td>
									<td width="80" align="center">布控状态</td>
									<td width="100" align="center">操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="d" items="${pageResult.items }" varStatus="status">
									<tr id="${d.bkid }">
										<td>${status.index + 1 }</td>
										<td>${d.hphm}</td>
										<td>
											<c:forEach items= "${dicList}" var="dic">
						            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
							                    	<c:if test="${dic.typeSerialNo eq d.hpzl }">
							                    		${dic.typeDesc }
							                    	</c:if>
						            	  		</c:if> 
										 	</c:forEach>
									 	</td>
									 	<td>
										 	<c:forEach items= "${dicList}" var="dic">
						            	  		<c:if test="${dic.typeCode eq 'BKDL' }">
							                    	<c:if test="${dic.typeSerialNo eq d.bkdl }">
							                    		${dic.typeDesc }
							                    	</c:if>
						            	  		</c:if> 
										 	</c:forEach>
									 	</td>
									 	<td>
										 	<c:forEach items= "${bklb}" var="b">
						            	  		<c:if test="${b.ID eq d.bklb }">
							                    		${b.NAME }
						            	  		</c:if> 
										 	</c:forEach>
									 	</td>
										<td>${d.bkr }</td>
										<td>${d.bkjgmc }</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${d.bksj }" /></td>
										<td>
											<c:forEach items= "${dicList}" var="dic">
						            	  		<c:if test="${dic.typeCode eq 'BKYWZT' }">
							                    	<c:if test="${dic.typeSerialNo eq d.ywzt }">
							                    		${dic.typeDesc }
							                    	</c:if>
						            	  		</c:if> 
										 	</c:forEach>
										</td>
										<td align="center">
											<a onclick="feedback('${d.bkid }');" style="font-size: 15px;">反馈</a>
											|&nbsp;<a onclick="showDetail('${d.bkid }');" style="font-size: 15px;">详情</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</form>
			</div>
		</div>
		<jsp:include page="/common/Foot.jsp"/>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/dispatched-todo.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
		$(function (){
			
		});
		 
		var list_url = "<%=basePath%>dispatched/queryBksqForFk.do";
		//搜索..按钮
		function doSearch() {
			if($.trim($("input[name='hphm']").val()) == ""){
				alert("请先输入号牌号码！");
				return;
			}
			
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		 
		//重置..按钮
		function doReset() {
			$("#hpzl").val("==全部==");
			$("input[name='hpzl']").val("");
			$("input[name='hphm']").val("");
		}
		
		//详情
		function showDetail(bkid){
			var url = "${pageContext.request.contextPath}/dispatched/detailDispatchedForWin.do?bkid=" + bkid;
		    var index = layer.open({
		           type: 2,
		           title: '布控信息详情',
	// 	           shadeClose: false,
	// 	           shade: 0.8,
		           area: ['1000px', '500px'],
		           content: url,//iframe的url
		           maxmin: true
		       }); 	
		    layer.full(index);
		}
		
		//反馈
		function feedback(bkid){
			var url = "${pageContext.request.contextPath}/dispatched/loadBksqForFk.do?bkid=" + bkid;
		    layer.open({
		    	type: 2,
		        title: '已布控车拦截反馈窗口',
		        shadeClose: false,
		        shade: 0.8,
		        area: ['1180px', '500px'],
		        content: url //iframe的url
		    }); 	
		}
	</script>
</html>