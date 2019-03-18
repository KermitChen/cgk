<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="zh-CN">
<head>
	<base href="<%=basePath%>">
	<title>
		<c:if test="${conPath eq 'findWithdraw'}">撤控申请管理</c:if>
		<c:if test="${conPath ne 'findWithdraw'}">撤控综合查询</c:if>
	</title>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
<!-- 	<link rel="stylesheet" href="<%=basePath%>common/css/blueprint/screen.css" type="text/css" media="screen, projection"> -->
<!-- 	<link rel="stylesheet" href="<%=basePath%>common/css/blueprint/print.css" type="text/css" media="print"> -->
	<link href="<%=basePath%>common/js/activiti/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
</head>

<body>
	<jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">
			<c:if test="${conPath eq 'findWithdraw'}">撤控申请管理</c:if>
			<c:if test="${conPath ne 'findWithdraw'}">撤控综合查询</c:if>
		</span>
	</div>
	<div class="content">
		<div class="content_wrap">
			<form name="form" action="" method="post">
				<div class="slider_body">
					<div class="slider_selected_left">
						<span>撤控申请时间：</span>
					</div>
					<div class="slider_selected_right">
						<div class="demolist">
							<input class="inline laydate-icon" name="qssj" value="${qssj}" readonly="readonly"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" />
						</div>
					</div>
				</div>
				<div class="slider_body">
					<div class="slider_selected_left">
						<span>至</span>
					</div>
					<div class="slider_selected_right">
						<div class="demolist">
							<input class="inline laydate-icon" name="jzsj" value="${jzsj}" readonly="readonly"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" />
						</div>
					</div>
				</div>
				
				<div class="button_wrap1">
					<button class="submit_b" onclick="doSearch()">查询</button>
					<button class="submit_b" onclick="doReset()">重置</button>
					<button class="submit_b" onclick="doExport()">导出Excel</button>
				</div>

				<div class="pg_result">
					<table>
						<thead>
							<tr>
								<td width="80" align="center">号牌号码</td>
								<td width="100" align="center">号牌种类</td>
<!--								<td width="100" align="center">布控类别</td>-->
								<td width="80" align="center">撤销申请人</td>
								<td width="120" align="center">撤销申请单位</td>
								<td width="120" align="center">撤销申请时间</td>
								<td width="100" align="center">布控性质</td>
<!--								<td width="80" align="center">直接撤控</td>-->
								<td width="80" align="center">撤销状态</td>
								<td width="80" align="center">操作</td>
								<td width="80" align="center">当前节点</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="w" items="${pageResults.items }" varStatus="status">
								<c:set var="d" value="${w.dispatched }" />
								<c:set var="task" value="${w.task }" />
								<c:set var="pi" value="${w.processInstance }" />
								<tr id="${w.ckid }" tid="${task.id }">
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
<!-- 								 	<td> -->
<!-- 								 	<c:forEach items= "${bklb}" var="b"> -->
<!-- 				            	  		<c:if test="${b.ID eq d.bklb }"> -->
<!-- 					                    		${b.NAME } -->
<!-- 				            	  		</c:if>  -->
<!-- 								 	</c:forEach> -->
<!-- 								 	</td> -->
									<td>${w.cxsqr }</td>
									<td>${w.cxsqdwmc }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${w.cxsqsj }" /></td>
									<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKXZ' }">
						                    	<c:if test="${dic.typeSerialNo eq d.bkxz }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
									</td>
<!--									<td>-->
<!--										<c:if test="${w.zjck ne '1' }">否</c:if>-->
<!--										<c:if test="${w.zjck eq '1' }">是</c:if>-->
<!--									</td>-->
									<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'CKYWZT' }">
						                    	<c:if test="${dic.typeSerialNo eq w.ywzt }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
									</td>
									<td align="center">
										<a href="withdraw/detailWithdraw.do?rowId=${w.ckid }&taskId=${task.id }&conPath=${conPath }" >详情</a>
										<c:if test="${conPath eq 'findWithdraw' && userid eq w.cxsqr}">
<!-- 											<c:if test="${pi.suspended eq true}"> -->
<!-- 												|<a onclick="updateState('active','${pi.id}','${w.ckid}')">激活</a> -->
<!-- 											</c:if> -->
<!-- 											<c:if test="${pi.suspended eq false}"> -->
<!-- 												|<a onclick="updateState('suspend','${pi.id}','${w.ckid}')">挂起</a> -->
<!-- 											</c:if> -->
											<c:if test="${pi.suspended eq false}">
												|<a onclick="updateState('suspend','${pi.id}','${w.ckid}')">取消申请</a>
											</c:if>
											<c:if test="${w.ywzt eq '6' && not empty task.id}">
												|<a href="withdraw/editWithdraw.do?rowId=${w.ckid }&taskId=${task.id }&conPath=${conPath }" >调整申请</a>
											</c:if>
										</c:if>
									</td>
									<td align="center">
										<a class="trace"  pid="${pi.id }" pdid="${pi.processDefinitionId}" title="点击查看流程图">${task.name }</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<jsp:include page="/common/pageNavigators.jsp"></jsp:include>
				</div>
			</form>
		</div>
	</div>

	<jsp:include page="/common/Foot.jsp" />
	
</body>
<!-- 	<script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script> -->
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/dispatched-todo.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript">
	$(function (){
		//布控类别初始化
// 		var bklbList= jQuery.parseJSON('${bklbList}');
// 		$("#bklbUl").empty();
// 		$("#bklbUl").append('<div class="li" data-value="" onclick="sliders(this)">==全部==</div>');
// 		for(var i=0;i< bklbList.length;i++){
// 			$("#bklbUl").append('<div class="li" data-value='+bklbList[i].ID+' onclick="sliders(this)">'+bklbList[i].NAME+'</div>');
// 			if(bklbList[i].ID == '${dispatched.bklb }'){
// 				$("#bklb").val(bklbList[i].NAME);
// 			}
// 		}
	});
	var list_url = "<%=basePath%>withdraw/${conPath }.do";
	//搜索..按钮
	function doSearch() {
		if(!compareDate($("input[name='qssj']").val(),$("input[name='jzsj']").val())){
			alert("起始时间不能大于结束时间");
			return ;
		}
		document.getElementById("pageNo").value = 1;
		document.forms[0].action = list_url;
		document.forms[0].submit();
	}

	//重置..按钮
	function doReset() {
// 		$("input[name='hphm']").val("");
		$("input[name='qssj']").val("");
		$("input[name='jzsj']").val("");
	}
	//根据页号查询
	function doGoPage(pageNo) {
		document.getElementById("pageNo").value = pageNo;
		document.forms[0].action = list_url;
		document.forms[0].submit();
	}
	//取消申请
	function updateState(state,processInstanceId,ckid){
		confirm("确认要取消撤控申请吗？");
// 		$.blockUI({
// 	        message: '<h2><img src="' + 'common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
// 	    });
		// 发送请求更新流程状态
	    $.post('${pageContext.request.contextPath}/withdraw/update.do?state=' + state+'&processInstanceId='+processInstanceId+'&ckid='+ckid
	    , function(resp) {
// 			$.unblockUI();
	        if (resp == 'success') {
// 	        	if(state=="active"){
// 	            	alert('激活成功');
// 	            }else{
// 	            	alert('挂起成功');
// 	            }
	            alert("撤控申请取消成功");
	            location.reload();
	        } else {
	            alert('操作失败!');
	        }
	    });
	}
	//导出excel
	function doExport(){
		var url = "${pageContext.request.contextPath}/withdraw/findWithdraw.do?conPath="+'${conPath}';
		document.forms[0].action = url;
		document.forms[0].submit();
	}
</script>
</html>