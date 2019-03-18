<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="Pragma" content="no-cache" />
		<style>
			body{
				font-family: "微软雅黑";
    			font-size: 14px;
    			color:#333;
			}
		</style>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script  src="<%=basePath%>common/js/layer/layer.js"></script>
		<script type="text/javascript">
			//获取父页面中待审批的数量
			$(function(){
				var hmdSum = parent.$("#hmdSum").val();
				var dySum = parent.$("#dySum").val();
				var gkbkSum = parent.$("#gkbkSum").val();
				var bkspSum = parent.$("#bkspSum").val();
				var gkckSum = parent.$("#gkckSum").val();
				var ckspSum = parent.$("#ckspSum").val();
				var yjqsSum = parent.$("#yjqsSum").val();
				var cktzSum = parent.$("#cktzSum").val();
				var bktzSum = parent.$("#bktzSum").val();
				var zlqsSum = parent.$("#zlqsSum").val();
				var zlfkSum = parent.$("#zlfkSum").val();
				
				var str = "";
				var sum = 0;
				if(parseInt(bktzSum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/dispatched/disReceive.do' target='_blank'>有&nbsp;" + bktzSum + "&nbsp;条布控通知待签收！</a><br/>";
				}
				if(parseInt(cktzSum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/dispatched/disReceive.do' target='_blank'>有&nbsp;" + cktzSum + "&nbsp;条撤控通知待签收！</a><br/>";
				}
				if(parseInt(yjqsSum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/earlyWarning/loadActualEWarning.do' target='_blank'>有&nbsp;" + yjqsSum + "&nbsp;条预警信息待签收！</a><br/>";
				}
				if(parseInt(gkckSum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/withdraw/openWithdrawTaskList.do' target='_blank'>有&nbsp;" + gkckSum + "&nbsp;条公开撤控待审批信息！</a><br/>";
				}
				if(parseInt(bkspSum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/dispatched/listTask.do' target='_blank'>有&nbsp;" + bkspSum + "&nbsp;条布控待审批信息！</a><br/>";
				}
				if(parseInt(gkbkSum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/dispatched/openTaskList.do' target='_blank'>有&nbsp;" + gkbkSum + "&nbsp;条公开布控待审批信息！</a><br/>";
				}
				if(parseInt(dySum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/dysp/findDySp.do' target='_blank'>有&nbsp;" + dySum + "&nbsp;条订阅待审批信息！</a><br/>";
				}
				if(parseInt(hmdSum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/JjHmdSp/findNewJjHmdSp.do' target='_blank'>有&nbsp;" + hmdSum + "&nbsp;条红名单待审批信息！</a><br/>";
				}
				if(parseInt(ckspSum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/withdraw/withdrawTaskList.do' target='_blank'>有&nbsp;" + ckspSum + "&nbsp;条撤控待审批信息！</a><br/>";
				}
				if(parseInt(zlqsSum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/earlyWarning/queryInsSign.do' target='_blank'>有&nbsp;" + zlqsSum + "&nbsp;条处警指令待签收！</a><br/>";
				}
				if(parseInt(zlfkSum) > 0){
					sum = sum + 1;
					str = str + "&nbsp;&nbsp;" + sum + ".<a href='${pageContext.request.contextPath}/earlyWarning/queryInsSignForFk.do' target='_blank'>有&nbsp;" + zlfkSum + "&nbsp;条处警指令待反馈！</a><br/>";
				}
				
				//显示
				if(str != ""){
					$('#message').html(str);
				}
			});
		</script>
	</head>
	<body>
		<div id="message"></div>
	</body>
</html>


