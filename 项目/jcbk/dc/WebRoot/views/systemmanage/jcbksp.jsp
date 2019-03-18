<%@ page language="java" import="java.util.*,com.dyst.dispatched.entities.*,org.activiti.engine.task.Task" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0068)http://10.42.11.104:8080/generaloa/DBSY_iframe.jsp?userID=0000005296 -->
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=GBK">
		<STYLE>
			*{
			   font-size: 14px;
			}
			.bag-rb {
				BACKGROUND-POSITION: right bottom; BACKGROUND-REPEAT: no-repeat
			}
			.red-text {
				BACKGROUND-POSITION: 50% bottom; BORDER-TOP: 1px solid; FONT-WEIGHT: bold; FONT-SIZE: 13px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#C3D9FF, EndColorStr=#Ffffff); BACKGROUND-IMAGE: url(../common/images/red_line.gif); PADDING-BOTTOM: 4px; COLOR: #2d4874; BORDER-BOTTOM: #cccccc 1px solid; BACKGROUND-REPEAT: repeat-x; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif"
			}
			.i-line {
				BACKGROUND-POSITION: 4px 4px; PADDING-LEFT: 20px; BACKGROUND-IMAGE: url(../common/images/icon-repo.gif); VERTICAL-ALIGN: middle; BACKGROUND-REPEAT: no-repeat; HEIGHT: 25px
			}
		</STYLE>
		<META content="MSHTML 6.00.2900.5726" name=GENERATOR>
	</HEAD>
	<BODY leftMargin=0 topMargin=0 marginheight="0" marginwidth="0">
 		<form method="post" name="formShcx" id="formShcx">
  			<INPUT id="userName" type="hidden" name="userName" value="<%=request.getParameter("userName")%>">
  			<INPUT id="password" type="hidden" name="password" value="1">
		</form>
       	<%  String loginName = request.getParameter("userName");
	   		List<Dispatched> bkspList = (List<Dispatched>)request.getAttribute("bkspList");
	   		String bkConPath = (String)request.getAttribute("bkConPath");
	   		List<Withdraw> ckspList = (List<Withdraw>)request.getAttribute("ckspList");
	   		String ckConPath = (String)request.getAttribute("ckConPath");
       		Integer sum = 0;
       		if(bkspList != null && bkspList.size() > 0) {
       			for(int i=0;i < bkspList.size();i++){//因为浏览器不同，不能进行信息调整，过滤需要调整的信息
       				Dispatched dis = bkspList.get(i);
       				if(!"6".equals(dis.getYwzt())){
						sum++;
					}
       			}
       		}
       		if(ckspList != null && ckspList.size() > 0) {
          		for(int i=0;i < ckspList.size();i++){//因为浏览器不同，不能进行信息调整，过滤需要调整的信息
					Withdraw withdraw = ckspList.get(i);
					if(!"6".equals(withdraw.getYwzt())){
						sum++;
					}
				}
       		}
	 	%>
		<div style="position: absolute;">
	  		<table width='100%' border='0' cellpadding='4' cellspacing='0'>
	 	 		<tr valign="top">
	 	 			<td width='60%' align='left' class='red-text' valign="top">
	 	 				<img src='<%=basePath %>common/images/18_link.gif' width='18' height='16' hspace='10' align="middle" style="white-space: nowrap;">缉查布控待办项
	 	 			</td>
	 	 			<td width='40%' align='left' class='red-text' valign="top" style="white-space: nowrap;">
	 	 				<a href="javascript:void(0);" style="color: blue;font-size: 13px;font-weight: normal;text-decoration: none;">
	 	 				<font style="color: red;font-size: 14px;font-weight: bold"><%=sum%>&nbsp;</font>条未审批信息</a>
	 	 			</td>
	 	 			<td width='3%' class='red-text'> &nbsp; </td>
	 	 		</tr>
			</table>
	 	</div>
		<DIV style="margin-top: 0px;padding-top:30px;SHEIGHT: 100%;overflow:auto;">
			<table width='98%' border='0' cellspacing='0' cellpadding='4'>
				<%
					int index = 1;
					if(bkspList != null && bkspList.size() > 0){
						for(int i=0;i < bkspList.size();i++){
							Dispatched dis = bkspList.get(i);
							Task task = dis.getTask();
							String href = "";
							String operate = "";
							if(task != null && task.getAssignee() == null && dis != null && "0".equals(dis.getZjbk())){
								href = basePath + "dispatched/taskClaimForOa.do?taskId=" + task.getId() + "&conPath=" + bkConPath + "&loginName=" + loginName + "&login=1";
								operate = "1";
							} else if(task != null && task.getAssignee() != null && dis != null && !"6".equals(dis.getYwzt())){
								href = basePath + "dispatched/loadDispatchedHandleForOa.do?rowId=" + dis.getBkid() + "&taskId=" + task.getId() + "&conPath=" + bkConPath + "&loginName=" + loginName + "&login=1";
								operate = "2";
							} else if(dis != null && "1".equals(dis.getZjbk())){
								href = basePath + "dispatched/loadDispatchedHandleForOa.do?rowId=" + dis.getBkid() + "&taskId=" + (task != null ? task.getId():"") + "&conPath=" + bkConPath + "&loginName=" + loginName + "&login=1";
								operate = "3";
							} else if(task != null && task.getAssignee() != null && dis != null && "6".equals(dis.getYwzt())){
								//href = basePath + "dispatched/editDispatched.do?rowId=" + dis.getBkid() + "&taskId=" + task.getId() + "&conPath=" + bkConPath + "&loginName=" + loginName + "&login=1";
								//operate = "4";
							}
							
							if(!"".equals(href) && "1".equals(operate)){
				%>
								<tr>
						   			<td class='i-line'><a href="javascript:openWin('<%=href %>', '<%=operate %>');" style="text-decoration: none;white-space: nowrap;">[<%=index++ %>]&nbsp;&nbsp;关于车牌号“<% out.print(dis.getHphm()); %>”的布控审批信息待签收！</a></td>
								</tr>
				<%			
							} else if(!"".equals(href) && "2".equals(operate)){
				%>
								<tr>
						   			<td class='i-line'><a href="javascript:openWin('<%=href %>', '<%=operate %>');" style="text-decoration: none;white-space: nowrap;">[<%=index++ %>]&nbsp;&nbsp;关于车牌号“<% out.print(dis.getHphm()); %>”的布控审批信息待审批！</a></td>
								</tr>
				<%
							} else if(!"".equals(href) && "3".equals(operate)){
				%>
								<tr>
						   			<td class='i-line'><a href="javascript:openWin('<%=href %>', '<%=operate %>');" style="text-decoration: none;white-space: nowrap;">[<%=index++ %>]&nbsp;&nbsp;关于车牌号“<% out.print(dis.getHphm()); %>”的布控待报备！</a></td>
								</tr>
				<%			
							} else if(!"".equals(href) && "4".equals(operate)){
				%>
								<tr>
						   			<td class='i-line'><a href="javascript:openWin('<%=href %>', '<%=operate %>');" style="text-decoration: none;white-space: nowrap;">[<%=index++ %>]&nbsp;&nbsp;关于车牌号“<% out.print(dis.getHphm()); %>”的布控申请信息待调整！</a></td>
								</tr>
				<%			
							}
						}
					}
				%>
				
				<%
					if(ckspList != null && ckspList.size() > 0){
						for(int i=0;i < ckspList.size();i++){
							Withdraw withdraw = ckspList.get(i);
							Task task = withdraw.getTask();
							Dispatched dis = withdraw.getDispatched();
							String href = "";
							String operate = "";
							if(task != null && task.getAssignee() == null){
								href = basePath + "withdraw/taskClaimForOa.do?taskId=" + task.getId() + "&conPath=" + ckConPath + "&loginName=" + loginName + "&login=1";
								operate = "1";
							} else if(task != null && task.getAssignee() != null && !"6".equals(withdraw.getYwzt())){
								href = basePath + "withdraw/loadWithdrawHandleForOa.do?rowId=" + withdraw.getCkid() + "&taskId=" + task.getId() + "&conPath=" + ckConPath + "&loginName=" + loginName + "&login=1";
								operate = "2";
							} else if(task != null && task.getAssignee() != null && "6".equals(withdraw.getYwzt())){
								//href = basePath + "withdraw/editWithdraw.do?rowId=" + withdraw.getCkid() + "&taskId=" + task.getId() + "&conPath=" + ckConPath + "&loginName=" + loginName + "&login=1";
								//operate = "3";
							}
							
							if(!"".equals(href) && "1".equals(operate)){
				%>
								<tr>
						   			<td class='i-line'><a href="javascript:openWin('<%=href %>', '<%=operate %>');" style="text-decoration: none;white-space: nowrap;">[<%=index++ %>]&nbsp;&nbsp;关于车牌号“<% out.print(dis.getHphm()); %>”的撤控审批信息待签收！</a></td>
								</tr>
				<%			
							} else if(!"".equals(href) && "2".equals(operate)){
				%>
								<tr>
						   			<td class='i-line'><a href="javascript:openWin('<%=href %>', '<%=operate %>');" style="text-decoration: none;white-space: nowrap;">[<%=index++ %>]&nbsp;&nbsp;关于车牌号“<% out.print(dis.getHphm()); %>”的撤控审批信息待审批！</a></td>
								</tr>
				<%
							} else if(!"".equals(href) && "3".equals(operate)){
				%>
								<tr>
						   			<td class='i-line'><a href="javascript:openWin('<%=href %>', '<%=operate %>');" style="text-decoration: none;white-space: nowrap;">[<%=index++ %>]&nbsp;&nbsp;关于车牌号“<% out.print(dis.getHphm()); %>”的撤控申请信息待调整！</a></td>
								</tr>
				<%
							}
						}
					}
				%>
			</table>
		</DIV>
	</body>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.js"></script>
		<script type="text/javascript">
  			function reRefresh(){
  			    document.formShcx.action = '<%=basePath %>user/oaSp.do';
	 			document.formShcx.submit();
  			}

			function openWin(href, operate) {
				if(operate == "1"){
					xxQs(href);
				} else {
		  			window.open(href, "newwindow", "height=650, width=900, top=50,left=50, menubar=no, scrollbars=yes, resizable=no");
				}
			}
			
			function xxQs(url){
				//提交
				$.post(url, {}, function(data) {
					if(data.result == "1"){//添加成功！
						alert('签收成功！');
					} else {
						alert('该任务已被签收！');
					}
					//刷新
					reRefresh();
				});
			}
		</script>
		<%
			String havaRole = (String)request.getAttribute("havaRole");
			if(havaRole != null && "1".equals(havaRole.trim())){
				
		%>
				<SCRIPT type="text/javascript">
					var timer = window.setInterval("reRefresh();", 1000*60*1);
					//页面退出，关闭定时器
					window.onbeforeunload = function(){
						window.clearInterval(timer);
					}
				</SCRIPT>
		<%
			}
		%>
</html>