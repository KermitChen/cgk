<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>上传帮助文档页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-上传帮助文档页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>
	<body>
		<div id="content" style="width: 400px;height: 350px;margin:0 auto;">
			<div id="content_wrap" style="width: 350px;height: 350px;">
				<form action="" method="post" enctype="multipart/form-data">
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>选择文件：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<input id="File" name="file" type="file" maxlength="100" style="width: 212px;">
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>文档名称：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="FileName" name="fileName" type="text" class="slider_input" maxlength="100">
								<a id="FileName" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>备注：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="remark" name="remark" style="width: 212px;height: 80px;" maxlength="500"></textarea>
		            </div>
		        </div>
		        <div class="button_wrap clear_both">
					<input id="saveBt" type="button" class="button_blue" value="保存">
					<input id="backBt" type="button" class="button_blue" value="关闭">
				</div>
			</form>
			</div>
		</div>	
	</body>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	    <script type="text/javascript">
			//文件加载完毕之后执行
			$(function(){
			    $("#File").bind('change',function(){
					fileNameFuZhi();
				});
			    
				//返回按钮
				$("#backBt").click(function() {
					//window.location = '<%=basePath%>helpDoc/iniDocManage.do?' + new Date().getTime();
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				});
				
				//保存按钮
				$("#saveBt").click(function() {
					//验证信息
					if(validateFile() && validateFileName()){
						//提交表单
						document.forms[0].action = '<%=basePath%>helpDoc/addWjjlb.do';
						document.forms[0].submit();
					}
				});
				
				var result = <%=(String)request.getSession().getAttribute("result") %>;
				if(result == "0"){
					alert("上传失败！");
				} else if(result == "1"){
					alert("上传成功！");
					parent.window.reloadData();
					$("#backBt").trigger("click");
				}
			});
			
			//文档名称校验方法
			function validateFileName(){
				//非空校验
				var value = $.trim($("#FileName").val());
				if(!value){
					alert('文档名称不能为空！');
					return false;
				}
				return true;
			}
			
			//上传名称校验方法
			function validateFile(){
				//非空校验
				var value = $.trim($("#File").val());
				if(!value){
					alert('请先选择需要上传的文档！');
					return false;
				}
				return true;
			}
			
			//文件名自动赋值
			function fileNameFuZhi() {
				var path = $("#File").val();
				var index = path.indexOf('.');
				var fileName;
				if(index > 0){
					fileName = path.substring(0, index);
				} else if(index == -1){
					fileName = path;
				}
				$("#FileName").val(fileName);
			}
	    </script>
<%
	//移除
	request.getSession().removeAttribute("result");
%>
</html>