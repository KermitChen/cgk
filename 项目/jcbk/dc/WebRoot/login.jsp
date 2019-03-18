<%@ page language="java" import="java.util.*,com.dyst.utils.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	//产生认证原文
	String Auth_Content = PkiUtil.generateRandomNum();
	//将认证原文存放在session中
	request.getSession().setAttribute("original_data", Auth_Content);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%= basePath %>">
		<title>深圳市公安局缉查布控系统-登录页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-登录页面">
		
		<link rel="stylesheet" href="<%= basePath %>views/systemmanage/login/css/style.css">
		<script type="text/javascript" src="<%=basePath %>common/js/jquery.min.js"></script>
		<!-- PKI -->
		<script type="text/javascript">
			if(!!window.ActiveXObject || "ActiveXObject" in window){
				document.write('<object id="JITDSignOcx" classid="clsid:B0EF56AD-D711-412D-BE74-A751595F3633" codebase="./resources/JITComVCTK_S.cab#version=2,0,24,40"></object>');
			} else{
				document.write('<embed id="JITDSignOcx" type="application/x-jit-sign-vctk-s-plugin-boc" width="0" height="0"/>');
			}
		</script>
	</head>
	<body onload="document.all.loginName.select()" style="background: url(<%= basePath %>views/systemmanage/login/images/bg.jpg)">
		<div class="contents_q">
			<div class="index_head">
				<img src="<%= basePath %>views/systemmanage/login/images/police.png" alt="">
				<h1>深圳市公安局缉查布控系统</h1>
			</div>
			<div class="main_q">
				<div class="part1">
					<div class="img1">
						<img src="<%= basePath %>views/systemmanage/login/images/left.png" width="395" height="470" alt="" />
					</div>
				</div>
				<div class="part2">
					<div class="denglu">
						<div class="denglu_top">
							账户登录
						</div>
						<!-- 错误信息提示 -->
						<div class="denglu_span">
							<span id="errSpan"></span>
						</div>
						<div class="names">
							<img style="margin: 5px 0px;" src="<%= basePath %>views/systemmanage/login/images/name.png" width="20" alt=""/>
							<input id="loginName" name="loginName" type="text" class="_input" placeholder="警号/用户名" maxlength="10" value=""/>
						</div>
						<div class="mima">
							<img style="margin: 5px 0px;" src="<%= basePath %>views/systemmanage/login/images/mima.png" width="20" alt=""/>
							<input id="password" name="password" type="password" class="_input" placeholder="密码" maxlength="18" value=""/>
						</div>
						<div class="dxyzm">
						<!-- <img style="margin: 5px 0px;" src="<%= basePath %>views/systemmanage/login/images/mima.png" width="20" alt=""/>-->
							<input id="code" name="code" type="text" class="_dxyzm_input" placeholder="短信验证码" maxlength="4"/>
							<input id="getCodeBt" type="button" class="_input_button" value="获取验证码"/>
						</div>
						<div class="jiazhu">
<!--							<input type="checkbox" />-->
<!--							<div class="jiazhumima">下次自动登录</div>-->
							<div class="cc">
								<a id="loginForCodeId" href="javascript:;"><div class="dengluq">登录</div></a>
								<a id="loginForPKI" href="javascript:;"><div class="dengluq" style="background: #2f5eef;">PKI登录</div></a>
							</div>
						</div>
						<div class="tips">
							<p style="font-size: 12px; color: white; font-weight: 700; margin: 16px 28px;">注意事项</p>
							<p style="font-size: 12px; color: white; font-weight: 700; line-height: 22px; margin: 16px 28px;">
								1.帮助文档及相关插件<a href="<%=basePath %>/views/systemmanage/document/docQuery.jsp" target="_blank" style="text-decoration: underline;cursor: pointer;color: #333;">下载地址</a>；
								<br/>
								2.系统维护电话：0755-84469394.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		history.go(1);
		window.moveTo(0, 0);//最大化打开窗体
    	window.resizeTo(screen.width, screen.height);
		
		$(document).ready(function(){
			//事件监听
			$("#getCodeBt").click(function(){
				//获取用户名参数
				var loginName = $.trim($("#loginName").val());
				//用户名不能为空
				if(loginName=="" || loginName==null){
					//提示
					$("#errSpan").text("提示：请输入警号/用户名！");
					//清空内容
					$("#loginName").attr("value", '');
					return;//返回
				}
						
				//判断是否启用短信验证
				var dlyzmFlag = <%=Config.getInstance().getDlyzmFlag()%>;
				if(dlyzmFlag != "1"){
					//提示
					$("#errSpan").text("提示：未启用短信验证，无需短信验证码！");
					return;
				}
						
				//发送按钮不可用
				$("#getCodeBt").attr('disabled', true);
						
				//提交
				$.ajax({
					url:'<%=basePath%>/user/sendCode.do?' + new Date().getTime(),
					method:"post",
					data:{loginName:loginName},
					success:function(data){
						if(data.result == "0"){//获取短信验证码失败！
							$("#errSpan").text("错误提示：获取短信验证码失败！");
						} else if(data.result == "1"){//发送短信验证码成功！    
							$("#errSpan").text("提示：发送短信验证码成功！");
						} else if(data.result == "2"){//该用户信息不存在！
							$("#errSpan").text("错误提示：该用户信息不存在！");
						} else if(data.result == "3"){//该用户未绑定手机号码！
							$("#errSpan").text("错误提示：该用户未绑定手机号码！");
						}
								
						//若干秒后，可以重新发送    显示      重新发送(秒数)
						countDownTimer = window.setInterval("countDownFun()", 1000);
					},
					error: function () {//请求失败处理函数
						$("#errSpan").text("错误提示：获取短信验证码失败！");
					}
				});
			});
				
				
			//登录事件监听
			var loginFlag = false;
			$("#loginForCodeId").click(function(){
				//获取用户名参数
				var loginName = $.trim($("#loginName").val());
				var password = $.trim($("#password").val());
				var code = $.trim($("#code").val());
				//判断是否正在登陆
				if(loginFlag){
					$("#errSpan").text("提示：正在登录，请耐心等待！");
					return;
				}
				//判断空值
				if(loginName == "" || loginName == null){
					$("#errSpan").text("提示：请输入警号/用户名！");
					$("#loginName").focus();//获取焦点
					return;
				}
				if(password == "" || password == null){
					$("#errSpan").text("提示：请输入密码！");
					$("#password").focus();//获取焦点
					return;
				}
				//判断是否启用短信验证
				var dlyzmFlag = <%=Config.getInstance().getDlyzmFlag()%>;
				if(dlyzmFlag == "1"){//需要短信认证，则需判断是否为空
					if(code == "" || code == null){//需要短信认证，
						$("#errSpan").text("提示：请输入短信验证码！");
						$("#code").focus();//获取焦点
						return;
					}
				}
					
				//提交
				loginFlag = true;
				$.ajax({
					url:'<%=basePath %>/user/login.do?' + new Date().getTime(),
					method:"post",
					data:{loginName:loginName,password:password,code:code},
					success:function(data){
						loginFlag = false;
						if(data.result == "0"){//登录失败！
							$("#errSpan").text("错误提示：登录失败！");
							$("#loginName").focus();//获取焦点
						} else if(data.result == "1"){//登录成功！    
							window.location = '<%=basePath%>user/initIndex.do?' + new Date().getTime();
						} else if(data.result == "2"){//用户名与密码不匹配！
							$("#errSpan").text("错误提示：用户名与密码不匹配！");
							$("#loginName").focus();//获取焦点//获取焦点
						} else if(data.result == "3"){//验证码不正确！
							$("#errSpan").text("错误提示：验证码不正确！");
							$("#code").focus();//获取焦点
						}
					},
					error: function () {//请求失败处理函数
						loginFlag = false;
						$("#errSpan").text("错误提示：登录失败！");
						$("#loginName").focus();//获取焦点
					}
				});
			});
				
			//登录事件监听
			$("#loginForPKI").click(function(){
				var Auth_Content = '<%=Auth_Content %>';//获取原文
				//var DSign_Subject = 'CN=GDCA, O=JIT, C=CN';
				var temp_DSign_Result = "";//证书认证请求包
				//var idCard = "";
				if(Auth_Content == ""){
					//alert("认证原文不能为空，请刷新页面重新获取认证原文！");
					$("#errSpan").text("错误提示：PKI登录失败，请刷新页面再重试！");
					return;
				} else{
					var InitParam = "<?xml version=\"1.0\" encoding=\"gb2312\"?><authinfo><liblist><lib type=\"CSP\" version=\"1.0\" dllname=\"\" ><algid val=\"SHA1\" sm2_hashalg=\"sm3\"/></lib><lib type=\"SKF\" version=\"1.1\" dllname=\"SERfR01DQUlTLmRsbA==\" ><algid val=\"SHA1\" sm2_hashalg=\"sm3\"/></lib><lib type=\"SKF\" version=\"1.1\" dllname=\"U2h1dHRsZUNzcDExXzMwMDBHTS5kbGw=\" ><algid val=\"SHA1\" sm2_hashalg=\"sm3\"/></lib><lib type=\"SKF\" version=\"1.1\" dllname=\"U0tGQVBJLmRsbA==\" ><algid val=\"SHA1\" sm2_hashalg=\"sm3\"/></lib></liblist></authinfo>";
	    			JITDSignOcx.Initialize(InitParam);
					if (JITDSignOcx.GetErrorCode() != 0) {
					    //alert("初始化失败，错误码：" + JITDSignOcx.GetErrorCode() + " 错误信息："+JITDSignOcx.GetErrorMessage(JITDSignOcx.GetErrorCode()));
					    $("#errSpan").text("错误提示：请确认PKI插件运行是否正常！");
					    JITDSignOcx.Finalize();
					    return false;
					}
					    
					//控制证书为一个时，不弹出证书选择框
					JITDSignOcx.SetCertChooseType(1);
					JITDSignOcx.SetCert("SC", "", "", "", "","");
					if(JITDSignOcx.GetErrorCode() != 0){
						//alert("错误码：" + JITDSignOcx.GetErrorCode() + "　错误信息：" + JITDSignOcx.GetErrorMessage(JITDSignOcx.GetErrorCode()));
						$("#errSpan").text("错误提示：获取不到PKI认证证书！");
						JITDSignOcx.Finalize();
						return false;
					} else {
						//var sSubject = JITDSignOcx.getCertInfo("SC", 0, "");
						//idCard = sSubject.split(',')[0].substring(3);
						//temp_DSign_Result = JITDSignOcx.DetachSignStr(sSubject, Auth_Content);
						temp_DSign_Result = JITDSignOcx.DetachSignStr("", Auth_Content);
						if(JITDSignOcx.GetErrorCode() != 0){
							//alert("错误码：" + JITDSignOcx.GetErrorCode() + "　错误信息：" + JITDSignOcx.GetErrorMessage(JITDSignOcx.GetErrorCode()));
							$("#errSpan").text("错误提示：认证原文加密失败！");
							JITDSignOcx.Finalize();
							return false;
						}
						JITDSignOcx.Finalize();
					}
				}
					
				//提交
				$.ajax({
					url:'<%=basePath %>/user/loginPKI.do?' + new Date().getTime(),
					method:"post",
					data:{signed_data:temp_DSign_Result, original_jsp:Auth_Content},
					success:function(data){
						if(data.result == "0"){//登录失败！
							$("#errSpan").text("错误提示：登录失败！");
						} else if(data.result == "1"){//登录成功！    
							window.location = '<%=basePath%>user/initIndex.do?' + new Date().getTime();
						}
						
						//else if(data.result == "2"){//应用标识或网关认证地址不能为空！
						//	$("#errSpan").text("错误提示：应用标识或网关认证地址不能为空！");
						//} 
						//else if(data.result == "3"){//客户端提供的认证原文与服务端的不一致，请刷新页面！
						//	$("#errSpan").text("错误提示：客户端提供的认证原文与服务端的不一致，请刷新页面！");
						//} else if(data.result == "4"){//证书认证数据不完整！
						//	$("#errSpan").text("错误提示：证书认证数据不完整！");
						//} else if(data.result == "5"){//属性列表控制项为空，请确认配置！
						//	$("#errSpan").text("错误提示：属性列表控制项为空，请确认配置！");
						//} else if(data.result == "6"){//组装请求时出现异常！
						//	$("#errSpan").text("错误提示：组装请求时出现异常！");
						//} else if(data.result == "7"){//与网关连接出现异常！
						//	$("#errSpan").text("错误提示：与网关连接出现异常！");
						//} else if(data.result == "8"){//认证响应失败！
						//	$("#errSpan").text("错误提示：认证响应失败！");
						//} else if(data.result == "9"){//读取认证响应报文出现异常！
						//	$("#errSpan").text("错误提示：读取认证响应报文出现异常！");
						//} else if(data.result == "10"){//认证业务处理失败！
						//	$("#errSpan").text("错误提示：认证业务处理失败！");
						//} else if(data.result == "11"){//身份认证失败！
						//	$("#errSpan").text("错误提示：身份认证失败！");
						//} else if(data.result == "12"){//用户信息不存在或不完整！
						//	$("#errSpan").text("错误提示：用户信息不存在或不完整！");
						//} 
						
						else {
							$("#errSpan").text("错误提示：身份认证失败！");
						}
					},
					error: function () {//请求失败处理函数
						$("#errSpan").text("错误提示：登录失败！");
					}
				});
			});
				
			//按回车键提交
			$(document).keyup(function(event){
				if(event.keyCode == 13){
					$("#loginForCodeId").trigger("click");
				}
			});
		});
	</script>
	<script type="text/javascript">
		var maxTime = 90;//倒计时最大时间
		var countDownTimer;//计时器
		//倒计时，控制验证码发送频率，90秒后才可以再次请求
		function countDownFun(){
			//显示倒计时
			$("#getCodeBt").attr("value", '重新发送(' + maxTime + ')');
			//减少秒数
			maxTime = maxTime - 1;
			//为0时取消计时，按钮可用，恢复getCodeBt按钮标签为“获取验证码”，maxTime恢复90
			if(maxTime == 0){
				//取消计时
				window.clearInterval(countDownTimer);
				//按钮可用
				$("#getCodeBt").attr('disabled', false);
				//恢复getCodeBt按钮标签为“获取验证码”
				$("#getCodeBt").attr("value", '获取验证码');
				//maxTime恢复90
				maxTime = 90;
			}
		}
	</script>
	<!-- 检测浏览器是否IE -->
	<script language="JavaScript" type="text/javascript">
		if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){
			//var explorer = window.navigator.userAgent.toLowerCase();
			//var version = explorer.substr(explorer.indexOf("msie")+4, 4);
			//alert(version);
			//window.location = "http://10.42.127.80/uploadfiles/Firefox.exe";
			var msg = "由于IE8及以下版本浏览器不能完全支持本系统的各项功能，\n建议下载并安装火狐浏览器或IE9及以上版本浏览器！\n确定需要下载火狐浏览器吗？请确认！";
			if (confirm(msg) == true){
				window.location = "http://10.42.127.80/uploadfiles/Firefox.exe";
			}
		}
	</script>
		<%
			String result = request.getParameter("result");
			if(result != null && !"".equals(result.trim())){
				
		%>
				<SCRIPT type="text/javascript">
					$(document).ready(function(){
						if(<%=result %> == "0"){
							alert("登录失败！");
						} else if(<%=result %> == "2"){
							alert("用户信息不存在！");
						} else if(<%=result %> == "3"){
							alert("非法登录，请联系系统维护人员处理！");
						}
					});
				</SCRIPT>
		<%
			}
		%>
</html>