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
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<base href="<%=basePath%>">
	<title>撤控申请调整</title>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	
</head>

<body>
	<jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">撤控申请调整</span>
	</div>
	<div class="content">
    	<div class="content_wrap">
    		<form name="form" action="" method="post">
    			<input type="hidden" name="ckid" value="${withdraw.ckid }" /> 
    			<input type="hidden" name="bkid" value="${withdraw.bkid }" /> 
    			<input type="hidden" name="cxsqr" value="${withdraw.cxsqr }" /> 
    			<input type="hidden" name="cxsqrjh" value="${withdraw.cxsqrjh }" /> 
    			<input type="hidden" name="cxsqdw" value="${withdraw.cxsqdw }" /> 
    			<input type="hidden" name="cxsqdwmc" value="${withdraw.cxsqdwmc }" /> 
    			<input type="hidden" name="cxsqsj" value="" /> 
    			<input type="hidden" name="ckyydm" value="${withdraw.ckyydm }" /> 
    			<input type="hidden" name="ywzt" value="${withdraw.ywzt }" /> 
    			<div class="slider_body">
					<div class="slider_selected_left">
						<span>撤控人联系电话：</span>
					</div>
					<div class="slider_selected_right">
						<div class="img_wrap">
							<div class="select_wrap select_input_wrap">
								<input id="cxsqrdh" name="cxsqrdh" type="text"
									class="slider_input" maxlength="20" readonly="readonly" value="${withdraw.cxsqrdh }"
									onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
									onblur="this.v();" /> <a id="cxsqrdh" class="empty"></a>
							</div>
						</div>
					</div>
				</div>
				<div class="slider_body_textarea" style="height:160px;">
					<div class="slider_selected_left">
						<span>&nbsp;&nbsp;<span style="color: red">*</span>撤控原因描述：</span>
					</div>
					<div class="slider_selected_right">
						<textarea name="ckyyms" id="ckyyms" rows="6" style="width:951px">${withdraw.ckyyms }</textarea>
					</div>
				</div>
	        </form>
	        <c:if test="${!empty commentList }">
	        	<jsp:include page="/views/workflow/HisComment.jsp" />
	        </c:if>
	        <div class="clear_both">
				<button class="submit_b" onclick="agree(true)">重新提交</button>
				<button class="submit_b" onclick="agree(false)">取消申请</button>
	        	<button class="submit_b" onclick="toClose()">返回</button>
	        </div>
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
<script>
	$(function(){
		$("input[name='cxsqsj']").val('${withdraw.cxsqsj}'.substr(0,19));
// 		$("input[name='sqsb'][value='${dispatched.sqsb}']").attr("checked",true);
// 		var bklbList= jQuery.parseJSON('${bklbList}');
// 		for(var i=0;i< bklbList.length;i++){
// 			if(bklbList[i].ID == '${dispatched.bklb }'){
// 				$("#bklb").val(bklbList[i].NAME);
// 			}
// 		}
// 		$("input[name='bksj']").val('${dispatched.bksj}'.substr(0,19));
// 		$("input[name='bkqssj']").val('${dispatched.bkqssj}'.substr(0,10));
// 		$("input[name='bkjzsj']").val('${dispatched.bkjzsj}'.substr(0,10));
// 		var dic = jQuery.parseJSON('${dicListJSON}');
// 		for(var i=0;i<dic.length;i++){
// 			if(dic[i].typeCode == "HPZL" && '${dispatched.hpzl }' == dic[i].typeSerialNo){
// 				$("#hpzl").val(dic[i].typeDesc);
// 			}else if(dic[i].typeCode == "CSYS" && '${dispatched.csys }' == dic[i].typeSerialNo){
// 				$("#csys").val(dic[i].typeDesc);
// 			}else if(dic[i].typeCode == "BKDL" && '${dispatched.bkdl }' == dic[i].typeSerialNo){
// 				$("#bkdl").val(dic[i].typeDesc);
// 				selectBklb('${dispatched.bkdl }');
// 			}else if(dic[i].typeCode == "BKJB" && '${dispatched.bkjb }' == dic[i].typeSerialNo){
// 				$("#bkjb").val(dic[i].typeDesc);
// 			}else if(dic[i].typeCode == "BKFWLX" && '${dispatched.bkfwlx }' == dic[i].typeSerialNo){
// 				$("#bkfwlx").val(dic[i].typeDesc);
// 			}else if(dic[i].typeCode == "BKFW" && '${dispatched.bkfw }' == dic[i].typeSerialNo){
// 				$("#bkfw").val(dic[i].typeDesc);
// 			}else if(dic[i].typeCode == "BKXZ" && '${dispatched.bkxz }' == dic[i].typeSerialNo){
// 				$("#bkxz").val(dic[i].typeDesc);
// 			}else if(dic[i].typeCode == "BJYA" && '${dispatched.bjya }' == dic[i].typeSerialNo){
// 				$("#bjya").val(dic[i].typeDesc);
// 			}
// 		}
	});
	//取消..按钮
	function toClose() {
		location.replace("<%=basePath%>withdraw/"+'${conPath}'+".do");
	}
	//同意+不同意.按钮
	function agree(value) {
		if(validate()){
			$.blockUI({
		        message: '<h2><img src="<%=basePath%>common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
		    });
// 		    alert("任务完成");
			document.forms[0].action = "withdraw/editComplete.do?taskId="+'${task.id }'+"&taskProInstId="+'${task.processInstanceId }'+"&key="+'${passKey }'+"&value="+value+"&conPath="+'${conPath}';
// 			document.forms[0].action = "dispatched/editComplete.do?";
			document.forms[0].submit();
		}
// 		complete('${task.id }','${task.processInstanceId }','${passKey }',true,"",'${conPath }');
	}
	
	function validate() {
		if($("input[name='cxsqrdh']").val() == ""){
			alert("请输入联系电话");
			return false;
		}else if($("input[name='ckyyms']").val() == ""){
			alert("请输入撤控原因");
			return false;
		}else{
			return true;
		}
	}
	
</script>
</html>