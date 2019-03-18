<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	//获取session中的用户
	User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>短信发送页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-短信发送页面">
		
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<style type="text/css">
			.layer_span:hover {
				color:#FF0000 !important;
			}
		</style>
		<script type="text/javascript">
	    	window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    	</script>
	</head>
	<body>
    	<jsp:include page="/common/Head.jsp"/>
    	<div id="divTitle">
			<span id="spanTitle">当前位置：系统管理&gt;&gt;短信发送</span>
		</div>
		<div id="content" style="width: 400px;margin:0 auto;">
			<div id="content_wrap" style="width: 350px;height: 400px;">
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span id="Telphone_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>手机号码：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="Telphone" name="telphone" type="text" class="slider_input" maxlength="11" placeholder="有效的11位数字手机号码！">
		                    	<a id="Telphone" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span id="Tetra_span" class="layer_span" style="color:#900b09"><span style="color: red;">*</span>TETRA终端号：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="Tetra" name="tetra" type="text" class="slider_input" maxlength="11">
		                    	<a id="Tetra" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
				<div class="slider_body_textarea">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>短信内容：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
	                	<textarea id="Content" name="content" style="width: 280px;height: 80px;" maxlength="280"></textarea>
		            </div>
		        </div>
	 			<div class="button_wrap clear_both">
					<input id="saveBt" type="button" class="button_blue" value="发送">
					<input id="closeWinBt" type="button" class="button_blue" value="关闭">
				</div>
			</div>
		</div>
    	<jsp:include page="/common/Foot.jsp"/>
	</body>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
		<script type="text/javascript">
			//文件加载完毕之后执行
			$(function(){
				//返回按钮
				$("#closeWinBt").click(function() {
					window.close();
				});
				
				//保存按钮
				$("#saveBt").click(function() {
					//获取数据
					var telphone = $.trim($("#Telphone").val());//手机号码
					var content = $.trim($("#Content").val());//短信内容
					
					//验证信息
					if(validateTelphone() && validateTetra() && validateContent()){
						 window.location = '<%=basePath %>/views/shortmessage/sendOk.jsp?telphone=' + telphone + '&content=' + content;
					}
				});
				
				$("#Telphone_span").mouseover(function(){
					showTip("#Telphone_span", "有效的11位数字手机号码！");
				});
				$("#Telphone_span").mouseleave(function(){
					layer.closeAll('tips');
				});
			});
			
			//显示车牌号码输入提示
			function showTip(id, message){
				layer.open({
			           type: 4,
			           shade: 0,
			           time:16000,
			           closeBtn: 0,
			           tips: [3, '#758a99'],
			           content: [message, id]
			    });
			}
			
			//手机号码校验方法
			function validateTelphone(){
				//非空校验
				var value = $.trim($("#Telphone").val());
				if(!value){
					alert("手机号码不能为空！");
					return false;
				}
				 
				//校验是否为正确的手机号码
				if(value != "" && !(/^1[3|4|5|8][0-9]\d{8}$/.test(value))){
					alert("请输入正确的11位手机号码！");
					return false;
				}
				return true;
			}

			//终端号校验方法
			function validateTetra(){
				//非空校验
				var value = $.trim($("#Tetra").val());
				if(!value){
					alert('TETRA终端号不能为空！');
					return false;
				}
				return true;
			}
			
			//短信内容校验方法
			function validateContent(){
				//非空校验
				var value = $.trim($("#Content").val());
				if(!value){
					alert('短信内容不能为空！');
					return false;
				}
				return true;
			}
	    </script>
</html>