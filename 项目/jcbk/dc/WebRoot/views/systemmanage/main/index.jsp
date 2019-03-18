<%@ page language="java" import="java.util.*,com.dyst.base.utils.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%= basePath %>">
		<title>深圳市公安局缉查布控系统-主页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-主页">
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>
	<body>
		<jsp:include page="/common/Head.jsp">
			<jsp:param name="showBt" value="exitSystem"/>
		</jsp:include>
		<div style="height: 100%;width: 100%;margin-bottom: 50px;">
		<%
			//功能权限
			List<List<FunctionTree>> allFunList = (List<List<FunctionTree>>)request.getAttribute("allFunList");
			List<FunctionTree> funList = null;
			FunctionTree functionTree = null;
			//返回值为空，提示
			if(allFunList != null && allFunList.size() > 0) {
				for(int i=0;i < allFunList.size();i++){
		%>
					<div class="souche">
						<div class="souche_out">
							<%
								//一行最多放3个模块
								for(int j=1;j <= 3 && (i+(j-1)) < allFunList.size();j++){
									funList = allFunList.get(i+(j-1));//获取模块集合功能
									functionTree = funList.get(0);//获取该模块元素
							%>
									<div class="souche_wrap">
										<div class="souche_l">
											<p><%=functionTree.getFunName() %></p>
										</div>
										<img src="<%=basePath%>common/images/line.png" style="width: 280px;height: 1px;margin-left: 30px;"/>
								<%
										int pageNum = 0;
										for(int k=1;k < funList.size();k++){
											//页数加1
											pageNum++;
												
								%>
										<div id="<%=(i+(j-1)) + "_" + pageNum%>" class="souche_r" style="display: <%=!"1".equals(k+"")?"none":"block" %>;">
											<%
												//每页存放6个
												for(int h=1;h <= 6 && (k+(h-1)) < funList.size();h++){
												    
													functionTree = funList.get((k+(h-1)));//获取该模块的子功能
											%>
													<div class="icon_wrap">
														<a href="<%=basePath + functionTree.getLinkPath()%>" target="_blank" >
															<img src="<%=basePath%>common/images/main/<%=functionTree.getFunIcon() %>" 
															onmouseover="this.src='<%=basePath%>common/images/main/focus/focus<%=functionTree.getFunIcon() %>'" 
															onmouseout="this.src='<%=basePath%>common/images/main/<%=functionTree.getFunIcon() %>'"
															style="width: 50px;height: 50px;" alt="<%=functionTree.getFunName() %>"/>
														</a>
														<p><%=functionTree.getFunName() %></p>
													</div>
											<%
												}
												
												//移动游标
												k = k + 5;
											%>
										</div>
								<%
										}
								%>
										<div id="<%="pageDiv_" + (i+(j-1))%>" style="height: 40px;" align="center">
											<%	if(pageNum > 1){
													for(int t=1;t <= pageNum;t++){
											%>
														<img class="<%=(i+(j-1)) + "_" + t%>" onclick="javascript:changePage('<%="pageDiv_" + (i+(j-1))%>', '<%=(i+(j-1)) + "_" + t%>');" src="<%=basePath%>common/images/main/<%=t==1?t+"circle-blue.png":t+"circle-grey.png" %>" style="width:20px;height:20px;margin:6px;cursor:pointer;"/>
											<%
													}
												}
											%>
										</div>
									</div>
							<%
								}
								//移动游标
								i = i + 2;
							%>
						</div>
					</div>
		<%
				}
			}
		%>
		</div>
		<div id="jplayer"></div>
		<input type="hidden" id="sysAnnSum" value="" />
		<input type="hidden" id="hmdSum" value="" />
		<input type="hidden" id="dySum" value="" />
		<input type="hidden" id="gkbkSum" value="" />
		<input type="hidden" id="bkspSum" value="" />
		<input type="hidden" id="gkckSum" value="" />
		<input type="hidden" id="ckspSum" value="" />
		<input type="hidden" id="tzzxSum" value="" />
		<input type="hidden" id="yjqsSum" value="" />
		<input type="hidden" id="bktzSum" value="" />
		<input type="hidden" id="cktzSum" value="" />
		<input type="hidden" id="zlqsSum" value="" />
		<input type="hidden" id="zlfkSum" value="" />
		<jsp:include page="/common/Foot.jsp"/>
	</body>
	<%
		if(allFunList == null || allFunList.size() <= 0) {
	%>
			<SCRIPT type="text/javascript">
				$(document).ready(function(){
				 	alert("您暂无任何系统权限！");
				});
			</SCRIPT>
	<%
		}
	%>
	<%
		User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
	 %>
	<script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
	<script src="<%=basePath%>common/js/browser.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/time/JQueryTimer.js"></script>
	<script  src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/engine.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/interface/pushMessageCompont.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.jplayer.min.js"></script>
	<script type="text/javascript">
		//=====================@autour liuqiang 以下为新增部分====16.6.30========================
		var timesOfHmdAndDyRefresh = '30';//单位秒
		var firstFlag = "1";
		$(function(){
			//警报声
			$("#jplayer").jPlayer({
		      swfPath: "./common/js/",
		      ready: function () {
		        $(this).jPlayer("setMedia", {
		          mp3: "./common/warning.mp3",
		        });
		      },
		      supplied: "mp3"
		    });
		    
		    //设置更新频率
			$('body').everyTime(timesOfHmdAndDyRefresh + "s", HmdAndDyMessage);
			onPageLoad();//加载dwr组件
			dwr.engine.setActiveReverseAjax(true);
			dwr.engine.setNotifyServerOnPageUnload(true);
			//待办的系统公告
			showMessage("", null);
			//首次提示信息
			HmdAndDyMessage();
		});
		
		//翻页
		function changePage(divId, imgId) {
			//获取pageDiv下所有图标元素
			var pageImg = document.getElementById(divId).getElementsByTagName("img");
			for(var i=0;i < pageImg.length;i++){
				var j = i+1;
				if(imgId == pageImg[i].className){
					document.getElementById(pageImg[i].className).style.display = "block";
					pageImg[i].src = '<%=basePath%>common/images/main/'+j+'circle-blue.png';
				} else {
					document.getElementById(pageImg[i].className).style.display = "none";
					pageImg[i].src = '<%=basePath%>common/images/main/'+j+'circle-grey.png';
				}
			}
		}
		//13汇总   总的待办条目数
		function countTotalTasks(){
			var sysAnnSum = $("#sysAnnSum").val().trim()==""?0:$("#sysAnnSum").val().trim();
			var tzzxSum = $("#tzzxSum").val().trim()==""?0:$("#tzzxSum").val().trim();
			var totalSum = parseInt(sysAnnSum) + parseInt(tzzxSum);
			if(totalSum >= 0){
				layer.tips(''+totalSum+'', '#message_head_menu',{
					time:0,
				});
			}
			
			var hmdSum = $("#hmdSum").val().trim()==""?0:$("#hmdSum").val().trim();
			var dySum = $("#dySum").val().trim()==""?0:$("#dySum").val().trim();
			var gkbkSum = $("#gkbkSum").val().trim()==""?0:$("#gkbkSum").val().trim();
			var bkspSum = $("#bkspSum").val().trim()==""?0:$("#bkspSum").val().trim();
			var gkckSum = $("#gkckSum").val().trim()==""?0:$("#gkckSum").val().trim();
			var ckspSum = $("#ckspSum").val().trim()==""?0:$("#ckspSum").val().trim();
			var yjqsSum = $("#yjqsSum").val().trim()==""?0:$("#yjqsSum").val().trim();
			var bktzSum = $("#bktzSum").val().trim()==""?0:$("#bktzSum").val().trim();
			var cktzSum = $("#cktzSum").val().trim()==""?0:$("#cktzSum").val().trim();
			var zlqsSum = $("#zlqsSum").val().trim()==""?0:$("#zlqsSum").val().trim();
			var zlfkSum = $("#zlfkSum").val().trim()==""?0:$("#zlfkSum").val().trim();
			//提示声音
			var countSum = parseInt(hmdSum) + parseInt(dySum) + parseInt(gkbkSum) + parseInt(bkspSum) + parseInt(gkckSum) 
				+ parseInt(ckspSum) + parseInt(yjqsSum) + parseInt(bktzSum) + parseInt(cktzSum) + parseInt(zlqsSum) + parseInt(zlfkSum);
			if(countSum >= 1){
				//显示
				showMessages();
				//提示声音
				$("#jplayer").jPlayer('play');
			}
		}
		//异步查询该用户需要签收，审批的红名单的个数,和订阅的个数
		function HmdAndDyMessage(){
			$.ajax({
				url:"${pageContext.request.contextPath}/JjHmdSp/findNewJjHmdSpAjax.do",
				async:false,
				type:'POST',
				data:{firstFlag:firstFlag},
				dataType:"json",
				error:function(){
					layer.msg('获取待办信息失败！');
				},
				success:function(data){
					var sum_hmd = data['hmd'];//红名单
					if(typeof(sum_hmd)!="undefined"){
						if(sum_hmd >= 1){
							$("#hmdSum").val(sum_hmd);
						} else {
							$("#hmdSum").val("0");
						}
					}
					var sum_dy = data['dy'];//订阅
					if(typeof(sum_dy)!="undefined"){
						if(sum_dy >= 1){
							$("#dySum").val(sum_dy);
						} else {
							$("#dySum").val("0");
						}
					}
					var sum_gkbk = data['gkbk'];//公开布控
					if(typeof(sum_gkbk)!="undefined"){
						if(sum_gkbk>=1){
							$("#gkbkSum").val(sum_gkbk);
						} else {
							$("#gkbkSum").val("0");
						}
					}
					var sum_bksp = data['bksp'];//待办 布控审批数量
					if(typeof(sum_bksp)!="undefined"){
						if(sum_bksp>=1){
							$("#bkspSum").val(sum_bksp);
						} else {
							$("#bkspSum").val("0");
						}
					}
					var sum_gkck = data['gkck'];//待办公开撤控
					if(typeof(sum_gkck)!="undefined"){
						if(sum_gkck>=1){
							$("#gkckSum").val(sum_gkck);
						} else {
							$("#gkckSum").val("0");
						}
					}
					var sum_cksp = data['cksp'];//待办 撤控审批
					if(typeof(sum_cksp)!="undefined"){
						if(sum_cksp>=1){
							$("#ckspSum").val(sum_cksp);
						} else {
							$("#ckspSum").val("0");
						}
					}
					var sum_yjqs = data['yjqs'];//预警签收
					if(typeof(sum_yjqs) != "undefined"){
						if(sum_yjqs >= 1){
							$("#yjqsSum").val(sum_yjqs);
						} else {
							$("#yjqsSum").val("0");
						}
					}
					var sum_cktz = data['cktz'];//撤控通知
					if(typeof(sum_cktz) != "undefined"){
						if(sum_cktz >= 1){
							$("#cktzSum").val(sum_cktz);
						} else {
							$("#cktzSum").val("0");
						}
					}
					var sum_bktz = data['bktz'];//布控通知
					if(typeof(sum_bktz) != "undefined"){
						if(sum_bktz >= 1){
							$("#bktzSum").val(sum_bktz);
						} else {
							$("#bktzSum").val("0");
						}
					}
					var sum_zlqs = data['zlqs'];//指令签收
					if(typeof(sum_zlqs) != "undefined"){
						if(sum_zlqs >= 1){
							$("#zlqsSum").val(sum_zlqs);
						} else {
							$("#zlqsSum").val("0");
						}
					}
					var sum_zlfk = data['zlfk'];//指令反馈
					if(typeof(sum_zlfk) != "undefined"){
						if(sum_zlfk >= 1){
							$("#zlfkSum").val(sum_zlfk);
						} else {
							$("#zlfkSum").val("0");
						}
					}
				}
			});
			
			//非首次
			firstFlag = "0";
			//计数
			countTotalTasks();
		}

		//显示消息
		var showMessagesIndex;
		function showMessages(){
			//先关闭
			if(showMessagesIndex != null && showMessagesIndex != ""){
				layer.close(showMessagesIndex);
			}
			//再打开
			showMessagesIndex = layer.open({
				type: 2,
				titile: "提示",
				area: ['300', '200'],
				shade: 0,
				time: timesOfHmdAndDyRefresh*1000,
				offset: [$(window).height()-250, $(window).width()-325],
				content: "${pageContext.request.contextPath}/views/systemMessage/showMessages.jsp"
			});
		}
		
		//==========系统公告======@author liuqiang========================
		function getMessage(){
			window.open("${pageContext.request.contextPath}/sysAnn/toSysAnnListUI2.do?"+new Date().getTime());
		}
		
		//初始化
		var userId = "<%=(user!=null?user.getLoginName():"") %>";
		function onPageLoad() {
			pushMessageCompont.onPageLoad("sysAnn@"+userId);
		}
		function showMessage(sendMessages, clickEvent) {
			//1.查询所有待办的系统公告
			//2.查询 我的消息（我的待查看的消息）
			$.ajax({
				url:"${pageContext.request.contextPath}/sysAnn/getAllAnnIds2.do",
				type:'POST',
				dataType:'text',
				async:false,
				success:function(message){
					//系统公告部分
					var map = $.parseJSON(message);
					var data = map.sysAnn;
					if(data >= 1){
						$("#sysAnnSum").val(data);
						$("#xttz_head_menu").html(data);
					} else if(data < 1){
						$("#sysAnnSum").val(0);
					}
					
					//消息中心部分
					var messageCount = map.myMessage;
					if(messageCount >= 1){
						$("#tzzxSum").val(messageCount);
						$("#tzzx_head_menu").html(messageCount);
					} else if(data < 1){
						$("#tzzxSum").val(0);
					}
					
					//13计算总待办数
					countTotalTasks();
				}
			});
		}
		
		//重写错误方法
		dwr.engine._errorHandler = function(message, ex) {
			dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);
			return false;
		};
	</script>
</html>