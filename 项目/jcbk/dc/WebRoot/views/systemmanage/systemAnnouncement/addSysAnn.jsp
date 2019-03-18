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
		<title>发布系统公告页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-发布系统公告页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/index_selectMenu.css" type="text/css">
	</head>
	<body>
    	<jsp:include page="/common/Head.jsp"/>
    	<div id="divTitle">
			<span id="spanTitle">当前位置：系统管理&gt;&gt;系统公告管理&gt;&gt;发布系统公告</span>
		</div>
		<div id="content" style="width: 800px;;margin:0 auto;">
			<div id="content_wrap" style="width: 750px;height: 450px;">
				<form action="" method="post" enctype="multipart/form-data">
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>选择文件：</span>
		        	</div>
		        	<div id="divFile" class="slider_selected_right" style="">
		        		<input id="File" name="file" type="file" maxlength="100" style="width: 212px;">
		            </div>
		        </div>
		        
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>公告名称：</span>
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
		        
		         <div class="slider_body" style="position: relative; clear: both;">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>有效天数：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="days" name="days" type="text" class="slider_input" value="7">
								<a id="days" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
		        
		        <div class="slider_body">
		            <div class="slider_selected_left">
		                <span id="dept1_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:red;visibility: hidden;">*</font>接收部门：</span>
		            </div>
		            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
		                <input id="dept1" class="input_select xiala" onclick="doChoseDept();" type="text" value="==全部=="/> 
		                <input type="hidden" id="dept" name="dept" value="">
		                <a class="empty" href="javascript:doChoseDept()"></a>
		            </div>
		        </div>
		        
		        <div class="slider_body" style="position: relative; clear: both;"> 
		            <div class="slider_selected_left">
		                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>公告类型：</span>
		            </div>
					<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			            <input class="input_select xiala" id="annType1" type="text" value="==全部==">
			            <input type="hidden" name="annType" id="annType2" value="${d.typeSerialNo }" />
			            <div class="ul"> 
			            	  <div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
			            	  <c:forEach items= "${annType}" var="d">
				            	  <c:if test="${d.typeSerialNo eq kind }">
				            	   	<script>
				            		 	$("#annType1").val("${d.typeDesc }");
				            		 	$("#annType2").val("${d.typeSerialNo }");
				            	   	</script>
				            	  </c:if> 
			                    <div class="li" data-value="${d.typeSerialNo }" onclick="sliders(this)"><a rel="2">${d.typeDesc}</a></div> 
							 </c:forEach>
			            </div>
		        	</div>
		        </div>
		        
<!--		        <div style="float: left;">-->
		        
		        <div class="slider_body_textarea" style="height: 85px;position: relative; clear: both;">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>公告概要：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="annGeneral" name="annGeneral" style="width: 580px;height: 80px;" ></textarea>
		            </div>
		        </div>
		        
		        <div class="slider_body_textarea" style="height: 85px;position: relative; clear: both;">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>备注：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="remark" name="remark" style="width: 580px;height: 80px;" ></textarea>
		            </div>
		        </div>
		        
		        <div class="button_wrap clear_both">
					<input id="saveBt" type="button" class="button_blue" value="保存">
					<input id="backBt" type="button" class="button_blue" value="返回">
				</div>
			</form>
			</div>
		</div>	
    	<jsp:include page="/common/Foot.jsp"/>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<%
    String result = (String)request.getSession().getAttribute("result");
	//移除
	request.getSession().removeAttribute("result");
	if(result != null && "1".equals(result)){
%>
		<SCRIPT type="text/javascript">
			//layer.confirm('发布成功！',{
			//	btn:['继续发布','返回列表']
			//}, function(index){
			//	layer.close(index);
			//}, function(){
			// 	var redirectUrl = "${pageContext.request.contextPath}/sysAnn/toSysAnnListUI.do";
			//	window.location.href = redirectUrl;
			//});
			layer.msg('发布成功！');
		</SCRIPT>
<%
	} else if(result != null && "0".equals(result)){
%>
		<SCRIPT type="text/javascript">
			//layer.confirm('发布失败！',{
			//	btn:['继续发布','返回列表']
			//}, function(){
			//
			//}, function(){
			//	var redirectUrl = "${pageContext.request.contextPath}/sysAnn/toSysAnnListUI.do";
			//	window.location.href = redirectUrl;
			//});
			layer.msg('发布失败！');
		</SCRIPT>
<%
	}
%>
	<script type="text/javascript">
		//文档加载时执行
		$(function(){
			days();
			$("#File").bind('change',function(){
				fileNameFuZhi();
			});
			
			$("#dept1_span").mouseover(function(){
				showTip("#dept1_span");
			});
			$("#dept1_span").mouseleave(function(){
				layer.closeAll('tips');
			});
			
			//文件加载完毕之后执行
			$(function(){
				//返回按钮,关闭当前弹出层
				$("#backBt").click(function() {
					var redirectUrl = "${pageContext.request.contextPath}/sysAnn/toSysAnnListUI.do";
					window.location.href = redirectUrl;
				});
						
				//保存按钮,防止表单重复提交
				var isCommitted = false;//表单是否已经提交标识，默认为false
				$("#saveBt").click(function() {
					//验证信息
					if(validatePass()){
						//显示进度条
						var index = layer.load();
					    		
						//提交表单
						if(isCommitted==false){
							document.forms[0].action = '<%=basePath%>sysAnn/addSysAnn.do';
							document.forms[0].submit();
							isCommitted = true;
						} else{
							return false;//返回false那么表单将不提交
						}
					}
				});
			});
			
			//加载部门列表
			loadFunctionTree();
		});
	
		//表单校验通过
		function validatePass(){
			var flag = true;
			//校验公告概要
			var annGeneral = $.trim($("#annGeneral").val());
			if(!annGeneral){
				layer.msg('公告概要不能为空！');
				flag = false;
			}
			//校验公告类型
			var annType = $.trim($("#annType2").val());
			if(!annType){
				layer.msg('请先选择公告类型！');
				flag = false;
			}
			//有效天数
			var days = $.trim($("#days").val());
			if(!days){
				layer.msg('有效天数不能为空！');
				$("#days").val('7');
				flag = false;
			}
			var fileName = $.trim($("#FileName").val());
			if(!fileName){
				layer.msg('文档名称不能为空！');
				flag = false;
			}
			var file = $.trim($("#File").val());
			if(!file){
				layer.msg('请先选择需要发布的文件！');
				flag = false;
			}
			return flag;
		}
	
		//显示输入提示
		function showTip(id){
			layer.open({
				type: 4,
				shade: 0,
			 	time:8000,
				closeBtn: 0,
		 		tips: [3, '#758a99'],
				content: ['默认所有部门均可接收到此公告！', id]
			});
		}
		
		//给上传的文件加载有效时长
		function days(){
			$("#days").val('7');
		}
		
		//文件名自动赋值
		function fileNameFuZhi(){
			var path = $("#File").val();
			var index = path.indexOf('.');
			var fileName;
			if(index>0){
				fileName = path.substring(0,index);
			}else if(index==-1){
				fileName = path;
			}
			$("#FileName").val(fileName);
		}
		
		//加载部门列表
		function loadFunctionTree(){
			$.ajax({
				url:'<%=basePath%>/dept/getMaxDeptToJson2.do?' + new Date().getTime(),
				method: "post",
				success: function(data){
					$('.table_content_tree').html(data);
				},
				error: function() {//请求失败处理函数
					layer.msg('加载部门结构树失败！');
				}
			});
		}
		//选择部门
		function doChoseDept(){
			var url = "${pageContext.request.contextPath}/dept/getMaxDeptToJson2.do?"+ new Date().getTime();
		    layer.open({
		           type: 2,
		           title: '部门筛选窗口',
		           shadeClose: false,
		           shade: 0.5,
		           area: ['800px', '600px'],
		           content: url
		       }); 	
		};	
	</script>
</html>