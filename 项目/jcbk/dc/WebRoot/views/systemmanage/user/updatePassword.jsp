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
		<title>修改密码页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-修改密码页面">
		
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
			<span id="spanTitle">当前位置：系统管理&gt;&gt;密码修改</span>
		</div>
		<div id="content" style="width: 400px;margin:0 auto;">
			<div id="content_wrap" style="width: 350px;height: 400px;">
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>用户名：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="LoginName" name="loginName" type="text" class="slider_input"  value="<%=user.getLoginName() %>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>姓名：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="UserName" name="userName" type="text" class="slider_input" value="<%=user.getUserName() %>" readonly="readonly">
		                    </div>
		                </div>  
		            </div>
		        </div>
				<div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span id="OldPassword_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>旧密码：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="OldPassword" name="oldPassword" type="password" class="slider_input" maxlength="18">
		                    	<a id="OldPassword" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span id="Password_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>新密码：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="Password" name="password" type="password" class="slider_input" maxlength="18">
		                    	<a id="Password" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span id="SecondPassword_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>确认密码：</span>
		        	</div>
		        	<div class="slider_selected_right" style="">
		        		<div class="img_wrap">
		        			<div class="select_wrap select_input_wrap">
		                    	<input id="SecondPassword" name="secondPassword" type="password" class="slider_input" maxlength="18">
		                    	<a id="SecondPassword" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div> 
	 			<div class="button_wrap clear_both">
					<input id="saveBt" type="button" class="button_blue" value="保存">
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
					var loginName = $.trim($("#LoginName").val());//用户名
					var oldPassword = $.trim($("#OldPassword").val());//旧密码
					var password = $.trim($("#Password").val());//新密码
					
					//验证信息
					if(validateOldPassword() && validatePassword() && validateSecondPassword()){
						//显示进度条
						var index = layer.load();
			
						//提交
						$.ajax({
							url:'<%=basePath %>/user/updatePassword.do?' + new Date().getTime(),
							method:"post",
							data:{id:<%=user.getId() %>,loginName:loginName,oldPassword:oldPassword,password:password},
							success:function(data){
								//关闭进度条
					    		layer.close(index);
					    			
								if(data.result == "1"){//修改成功！
									//重置
									reset();
									alert('修改成功！');
								} else if(data.result == "0"){//修改失败！
									alert('修改失败！');
								} else if(data.result == "2"){//旧密码不正确！
									alert('旧密码不正确！');
								}
							},
							error: function () {//请求失败处理函数
								//关闭进度条
					    		layer.close(index);
								alert('修改失败！');
							}
						});
					}
				});
				
				$("#OldPassword_span").mouseover(function(){
					showTip("由大小写字母及数字组成，长度6至18位！", "#OldPassword_span");
				});
				$("#OldPassword_span").mouseleave(function(){
					layer.closeAll('tips');
				});
				$("#Password_span").mouseover(function(){
					showTip("由大小写字母及数字组成，长度6至18位！", "#Password_span");
				});
				$("#Password_span").mouseleave(function(){
					layer.closeAll('tips');
				});
				$("#SecondPassword_span").mouseover(function(){
					showTip("由大小写字母及数字组成，长度6至18位！", "#SecondPassword_span");
				});
				$("#SecondPassword_span").mouseleave(function(){
					layer.closeAll('tips');
				});
			});
			
			//显示车牌号码输入提示
			function showTip(message, id){
				layer.open({
			           type: 4,
			           shade: 0,
			           time: 30000,
			           closeBtn: 0,
			           tips: [3, '#758a99'],
			           content: [message, id]
			       });
			}
			
			//重置
			function reset(){
		  		$("#OldPassword").val("");//旧密码
		  		$("#Password").val("");//新密码
		  		$("#SecondPassword").val("");//新密码确认
			}
			
			//旧密码校验方法
			function validateOldPassword(){
				//非空校验
				var value = $.trim($("#OldPassword").val());
				if(!value){
					alert('旧密码不能为空！');
					return false;
				}
				
				//验证是否有非法字符
				if(!value.match(/^([0-9a-zA-Z]{6,18})$/)){
					alert('旧密码由大小写字母及数字组成，长度6至18位！');
					return false;
				}
				return true;
			}
			
			//新密码校验方法
			function validatePassword(){
				//非空校验
				var value = $.trim($("#Password").val());
				if(!value){
					alert('新密码不能为空！');
					return false;
				}
				
				//验证是否有非法字符
				if(!value.match(/^([0-9a-zA-Z]{6,18})$/)){
					alert('新密码由大小写字母及数字组成，长度6至18位！');
					return false;
				}
				return true;
			}
			
			//新密码确认校验方法
			function validateSecondPassword(){
				//非空校验
				var value = $.trim($("#SecondPassword").val());
				var pwd = $.trim($("#Password").val());
				if(!value){
					alert('确认密码不能为空！');
					return false;
				}
				
				//验证是否有非法字符
				if(!value.match(/^([0-9a-zA-Z]{6,18})$/)){
					alert('确认密码由大小写字母及数字组成，长度6至18位！');
					return false;
				}
				
				//判断两次密码是否一致
				if(pwd && value != pwd){
					alert('确认密码与新密码不一致！');
					return false;
				}
				return true;
			}
	    </script>
</html>