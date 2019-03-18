<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+path+"/";
	User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	String showBt = request.getParameter("showBt");
%>
<html lang="en">
	<head>
		<title>Head</title>
 		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
 		<link rel="stylesheet" href="<%=basePath%>common/css/index_selectMenu.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript">
			//主页面退出，则退出系统
			function exitSystem(){
				//提示是否退出系统
				if (confirm("确认要退出系统吗？")) {
					//提交
					$.ajax({
						url:'<%=basePath%>user/exitSystem.do?' + new Date().getTime(),
						method:"post",
						data:{},
						success:function(data){
							parent.window.location='<%=basePath %>';
						}
					});
				}
			}
			
			//定时向服务器报告在线情况，并获取在线用户数
			function reportOnline(){
				//提交
				$.ajax({
					url:'<%=basePath %>user/reportOnline.do?' + new Date().getTime(),
					method:"post",
					data:{},
					success:function(data){
						if(data != null && data != undefined && data.result != undefined){
							//获取在线用户数，并更新
							$(".onlineH3").html("当前在线人数：<a href='javascript:showOnlineUser();' style='color: white;text-decoration: underline;' title='点击查看在线用户'>" + data.result + "</a>");
						} else {
							parent.window.location='<%=basePath %>';
						}
					}
				});
			}
			
			//显示在线用户
			function showOnlineUser(){
				var url = "${pageContext.request.contextPath}/views/systemmanage/user/showOnlineUser.jsp";
		    	layer.open({
		           type: 2,
		           title: '在线用户（已控制查看权限）',
		           shadeClose: false,
		           shade: 0.8,
		           area: ['1000px', '480px'],
		           content: url //iframe的url
		       });
			}
			
			//加载完毕
		    $(document).ready(function() {
		    	//启动定时器
				window.setInterval("reportOnline();", 1000*60*2);//2分钟报告在线情况并获取在线用户数
				
				//关闭按钮
				$("#closeBt").click(function() {
					window.close();
				});
				
		        $("ul.nav li").hover(
		        	function(){
		            	$(this).parent("ul").siblings("h5").addClass("choice");
		        	}, 
		        	function(){
		            	$(this).parent("ul").siblings("h5").removeClass("choice");
		        	}
		        );
		        
		        if($("ul.nav li").find("ul").html() != "") {
		            $("ul.nav li").parent("ul").siblings("h5").append("<span class='sub'></span>");                
		        }
		        
		        $("ul.nav li").hover(
		        	function(){
		            	$(this).addClass("on");
		        	}, 
		        	function(){
		            	$(this).removeClass("on");
		        	}
		        );
		    });
		 
    		//=====点击下拉菜单项时，跳转到红名单审批======
    		function getHmdSp(){
    			window.open(hmdspUrl);
    		}
    		//=====跳转到订阅审批页面=======
    		function getDySp(){
    			window.open(dyspUrl);
    		}
    		//=====跳转到公开布控页面=======
    		function getGkBk(){
    			window.open(gkbkUrl);
    		}
    		//=====跳转布控审批页面========
    		function getBksp(){
    			window.open(bkspUrl);
    		}
    		function getGkck(){
    			window.open(gkckUrl);
    		}
    		function getCksp(){
    			window.open(ckspUrl);
    		}
    		function toTzzx(){
    			var url ="${pageContext.request.contextPath}/sysAnn/toTzzxUI.do";
    			window.open(url);
    		}
    		function getMessage(){
				window.open("${pageContext.request.contextPath}/sysAnn/toSysAnnListUI2.do?"+new Date().getTime());
			}
    		function getYjqs(){
    			window.open(yjqsUrl);
    		}
    		function getBktz(){
    			window.open(bktzUrl);
    		}
    		function getCktz(){
    			window.open(cktzUrl);
    		}
		</script>
	</head>
	<body style="min-width: 1135px;">
		<div class="head">
			<div class="head_wrap">
				<img src="<%=basePath%>common/images/police.png" alt="" width="65" height="68" style="margin-top: 7px;"/>
				<h1><font face="微软雅黑" size="6">深圳市公安局缉查布控系统</font></h1>
				<div class="head_online">
					<h3>您好！<%=(user!=null?user.getUserName():"") %></h3>
					<a href="<%=basePath%>bugReport/preAdd.do" target="_blank">问题反馈</a><br>
					<h3 class="onlineH3">当前在线人数：<a href="javascript:showOnlineUser();" style="color: white;text-decoration: underline;" title="点击查看在线用户"><%=UserOnlineCountUtil.getOnlineUserMap().size() %></a></h3>
					<%
						if(showBt != null && "exitSystem".equals(showBt)){
					%>
							<a id="exitA" href="javascript:exitSystem();">退出</a>
					<%
						} else {
					%>
							<a id="closeBt">关闭</a>
					<%
						}
					%>
				</div>
				<div style="height:40px;line-height:40px;float: right;margin-bottom: 1px;margin-top: 50px;z-index:9999;position: relative;">
					<ul class="nav">
						<li id="target_li"><h5><a id="message_head_menu" style="color: white;">消息</a></h5>
							<ul>
	     					   <li><h5><a href="javascript:getMessage();">系统公告</a><span id="xttz_head_menu" style="color: red;margin-left: 10px;"></span></h5></li>
	     					</ul>
							<ul>
	     					   <li><h5><a href="javascript:toTzzx();">通知中心</a><span id="tzzx_head_menu" style="color: red;margin-left: 10px;"></span></h5></li>
	     					</ul>
							<!-- <ul>
	     					   <li><h5><a href="javascript:getHmdSp();">红名单审批</a><span id="hmd_head_menu" style="color: red;margin-left: 10px;"></span></h5></li>
	     					</ul>
							<ul>
	     					   <li><h5><a href="javascript:getDySp();">订阅审批</a><span id="dy_head_menu" style="color: red;margin-left: 10px;"></span></h5></li>
	     					</ul> -->
						</li>
					</ul>
				</div>
			</div>
		</div>
	</body>
</html>