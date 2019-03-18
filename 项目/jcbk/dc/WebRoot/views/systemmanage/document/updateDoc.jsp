<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//获取需要编辑的数据
	Wjjlb wjjlbObj = (Wjjlb)request.getAttribute("wjjlbObj");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>修改帮助文档页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-修改帮助文档页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>
	<body>
    	<input id="docId" type="hidden" value="<%=(wjjlbObj != null ? wjjlbObj.getId():"0") %>"/>
		<div id="content" style="width: 400px;height: 350px;margin:0 auto;">
			<div id="content_wrap" style="width: 350px;height: 350px;">
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>文档名称：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="FileName" name="fileName" type="text" class="slider_input" maxlength="100" value="<c:if test="${not empty wjjlbObj}">${wjjlbObj.fileName}</c:if>">
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
	                	<textarea id="remark" name="remark" style="width: 212px;height: 80px;" maxlength="500"><c:if test="${not empty wjjlbObj}">${wjjlbObj.remark}</c:if></textarea>
		            </div>
		        </div>
		        <div class="button_wrap clear_both">
					<input id="saveBt" type="button" class="button_blue" value="保存">
					<input id="backBt" type="button" class="button_blue" value="关闭">
				</div>
			</div>
		</div>	
	</body>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
		<script type="text/javascript">
			//文件加载完毕之后执行
			$(function(){
				//返回按钮
				$("#backBt").click(function() {
					//window.location = '<%=basePath%>helpDoc/iniDocManage.do?' + new Date().getTime();
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				});
				
				//保存按钮
				$("#saveBt").click(function() {
					//获取数据
					var docId = $.trim($("#docId").val());//文档Id
					var fileName = $.trim($("#FileName").val());//文档名称
					var remark = $.trim($("#remark").val());//备注
					
					//验证信息
					if(validateFileName()){
						//提交
						$.ajax({
							url:'<%=basePath %>/helpDoc/updateWjjlb.do?' + new Date().getTime(),
							method:"post",
							data:{id:docId, fileName:fileName, remark:remark},
							success:function(data){
								if(data.result == "1"){//修改成功！
									alert('修改成功！');
									parent.window.reloadData();
									$("#backBt").trigger("click");
								} else if(data.result == "0"){//修改失败！    
									alert('修改失败！');
								}
							},
							error: function () {//请求失败处理函数
								alert('修改失败！');
							}
						});
					}
				});
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
	    </script>
<%
	if(wjjlbObj == null){
%>
		<SCRIPT type="text/javascript">
			alert("加载信息失败！");
		</SCRIPT>
<%
	}
%>
</html>