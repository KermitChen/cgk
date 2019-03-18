<%@ page language="java"
	import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>新增问题反馈</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">新增问题反馈</span>
	</div>
	<div id="content" style="width: 400px;margin:0 auto;">
		<div id="content_wrap" style="width: 350px;height: 450px;">
			<form action="" method="post" enctype="multipart/form-data">
				<div class="slider_body">
					<div class="slider_selected_left">
						<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择文件：</span>
					</div>
					<div class="slider_selected_right" style="">
						<input id="File" name="file" type="file" maxlength="100"
							style="width: 212px;">
					</div>
				</div>
				<div class="slider_body_textarea">
					<div class="slider_selected_left">
						<span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>问题描述：</span>
					</div>
					<div class="slider_selected_right" style="">
						<textarea id="problemDesc" name="problemDesc"
							style="width: 212px;height: 80px;" maxlength="500"></textarea>
					</div>
				</div>
				<div class="button_wrap clear_both">
					<input id="saveBt" type="button" class="button_blue" value="保存">
					<input id="backBt" type="button" class="button_blue" value="返回">
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="/common/Foot.jsp" />
</body>
<%
	String result = (String) request.getSession()
			.getAttribute("result");
	//移除
	request.getSession().removeAttribute("result");
	if (result != null && "1".equals(result)) {
%>
<SCRIPT type="text/javascript">
			alert("添加成功！");
		</SCRIPT>
<%
	} else if (result != null && "0".equals(result)) {
%>
<SCRIPT type="text/javascript">
			alert("添加失败！");
		</SCRIPT>
<%
	}
%>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
<script type="text/javascript">
  	window.moveTo(0, 0);//最大化打开窗体
	window.resizeTo(screen.width, screen.height);
	
	//文件加载完毕之后执行
	$(function(){
		//返回按钮
		$("#backBt").click(function() {
			window.location = '<%=basePath%>bugReport/preFind.do?' + new Date().getTime();
		});
		
		//保存按钮
		$("#saveBt").click(function() {
			if(validateProblemDesc()){
				layer.load();
				//提交表单
				document.forms[0].action = '<%=basePath%>bugReport/add.do';
				document.forms[0].submit();
			}
		});
	});
	//文档名称校验方法
	function validateProblemDesc() {
		//非空校验
		var value = $.trim($("#problemDesc").val());
		if (!value) {
			alert('请输入问题描述信息!');
			return false;
		}
		if(value.length > 250){
			alert('描述信息太长了,请简要概括!');
			return false;
		}
		return true;
	}

	//上传名称校验方法
	function validateFile() {
		//非空校验
		var value = $.trim($("#File").val());
		//var flag = isPicture(value);
		if (!value) {
			alert('请选择附件！');
			return false;
		}
		return true;
	}
	//判断上传文件格式是否满足条件
	function isPicture(fileName) {
		if (fileName != null && fileName != "") {
			//lastIndexOf如果没有搜索到则返回为-1
			if (fileName.lastIndexOf(".") != -1) {
				var fileType = (fileName.substring(
						fileName.lastIndexOf(".") + 1, fileName.length))
						.toLowerCase();
				var suppotFile = new Array();
				suppotFile[0] = "jpg";
				suppotFile[1] = "gif";
				suppotFile[2] = "bmp";
				suppotFile[3] = "png";
				suppotFile[4] = "jpeg";
				for ( var i = 0; i < suppotFile.length; i++) {
					if (suppotFile[i] == fileType) {
						return true;
					} else {
						continue;
					}
				}
				alert("文件类型不合法,只能是jpg、gif、bmp、png、jpeg类型！");
				return false;
			} else {
				alert("文件类型不合法,只能是 jpg、gif、bmp、png、jpeg 类型！");
				return false;
			}
		}
	}
</script>
</html>