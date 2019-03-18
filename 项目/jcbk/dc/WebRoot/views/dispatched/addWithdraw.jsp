<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>撤控审批</title>
<link rel="stylesheet" href="<%=basePath%>common/css/style.css"
	type="text/css">

</head>

<body>
	<form id="form" name="form" action="" method="post">
		<div class="slider_body">
			<div class="slider_selected_left">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>联系电话：</span>
			</div>
			<div class="slider_selected_right">
				<div class="img_wrap">
					<div class="select_wrap select_input_wrap">
						<input id="cxsqrdh" name="cxsqrdh" type="text"
							class="slider_input" maxlength="20" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"	onblur="this.v();"/> 
						<a id="cxsqrdh" class="empty"></a>
					</div>
				</div>
			</div>
		</div>
		<div class="slider_body_textarea" style="width: 500px;">
			<div class="slider_selected_left">
				<span><span style="color: red">*</span>撤控原因描述：</span>
			</div>
			<div class="slider_selected_right">
				<textarea name="ckyyms" id="ckyyms" rows="3" style="width:380px"></textarea>
			</div>
		</div>
		<div id="tzdwDiv" class="slider_body" style="width: 500px;display:none;">
				<div class="slider_selected_left">
				      <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>通知单位：</span>
				</div>
				<div class="slider_selected_right dropdown dropdown_all" id="dropdown_quanxuan">
				      <div class="input_select xiala">
				            <div class='multi_select'>
				            	<input type="hidden" id="tzdw" name="tzdw" value="" />
								<a class="xiala_duoxuan_a"></a>
				            </div>
				      </div>
				</div>
		</div>
		<div id="tznrDiv" class="slider_body_textarea" style="width: 500px;display:none;">
			<div class="slider_selected_left">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>通知内容：</span>
			</div>
			<div class="slider_selected_right">
				<textarea name="tznr" id="tznr" rows="3" style="width:380px"></textarea>
			</div>
		</div>
	</form>

	<div class="clear_both">
		<c:if test="${isZjck eq false }">
			<button class="submit_b" onclick="doSubmit()">申请撤销</button>
		</c:if>
		<c:if test="${isZjck eq true }">
			<button class="submit_b" onclick="doSubmit()">直接撤控</button>
		</c:if>
		<!-- 				<button class="submit_b" onclick="disagree()">不同意</button> -->
		<button class="submit_b" onclick="toClose()">返回</button>
	</div>
	<div class="mask"></div>
</body>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script>
<!-- 	<script type="text/javascript" src="<%=basePath%>common/js/1.9.0-jquery.js"></script> -->
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.dev.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<script>
	$(function(){
		var depts = jQuery.parseJSON('${depts}');
		//通知单位多选框初始化
		var value = []; 
		var data = []; 
		for(var i=0;i < depts.length;i++){
			if(depts[i].dept_no != '440300010100'){
				value.push(depts[i].dept_no);
				data.push(depts[i].dept_name);
			}
		}
	    $('.multi_select').MSDL({
		  'value': value,
	      'data': data
	    });
	    
	    //如果为涉案类，则显示通知单位
	    if('${dispatched.bkdl}' == "1"){
			$("#tzdwDiv").show();
			$("#tznrDiv").show();
		}
	});
	function toClose() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	//撤控
	function doSubmit() {
		if ($("#ckyyms").val() == "") {
			alert("请填写撤控原因！");
			return;
		} else if ($("#cxsqrdh").val() == "") {
			alert("请填写联系电话！");
			return;
		} else if ($("#tzdw").val() != "" && $("#tznr").val() == "") {
			alert("请填写通知内容！");
			return;
		}
		
		layer.load();
		$.post('${pageContext.request.contextPath}/withdraw/addWithdraw.do?bkid=${bkid}&isZjck=${isZjck}',
			$("#form").serialize(), function(resp) {
				if (resp == 'success') {
					alert("撤控申请提交成功！");
					parent.doSearch();
					toClose();
				} else if (resp == 'report') {
					alert("该布控已被申请撤销！");
				} else {
					alert("撤控申请失败！");
				}
				layer.closeAll('loading');
		});
	}
</script>
</html>