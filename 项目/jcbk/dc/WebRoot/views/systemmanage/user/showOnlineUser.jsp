<%@page import="java.util.*,org.springframework.ui.Model,java.util.Map,com.dyst.utils.*,com.dyst.systemmanage.entities.User"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User currentUser = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>详细信息</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
		<style type="text/css">
			.slider_body_textarea{
				width:95%;
			}
			.slider_body{
				height:30px;
			}
		</style>
	</head>
	<body>
		<div class="pg_result" style="height: 350px;">
			<table>
				<thead>
					<tr>
						<td width="10%" align="center">序号</td>
						<td width="10%" align="center">警号</td>
						<td width="10%" align="center">姓名</td>
						<td width="20%" align="center">隶属部门</td>
						<td width="10%" align="center">角色</td>
						<td width="20%" align="center">登录时间</td>
						<td width="20%" align="center">在线时长</td>
					</tr>
				</thead>
				<tbody>
					<%
						int i = 0;
						for (Map.Entry<String, User> entry : UserOnlineCountUtil.getOnlineUserMap().entrySet()) {
							User user = entry.getValue();
							if(user != null && user.getPosition() != null
								&& currentUser != null && currentUser.getPosition() != null 
								&& ("99999".equals(currentUser.getPosition().trim()) || 
									(currentUser.getPosition().trim().length() == 2) ||
									(currentUser.getPosition().trim().length() == 3 && user.getPosition().trim().length() == 3 && currentUser.getPosition().trim().substring(2, 3).equals(user.getPosition().trim().substring(2, 3)))
								)
							) {
					%>
								<tr>
									<td><%=++i %></td>
									<td><%=user.getLoginName() %></td>
									<td><%=user.getUserName() %></td>
									<td><%=user.getDeptName() %></td>
									<td><%=user.getRoleName() %></td>
									<td>
										<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="<%=user.getUpdateTime() %>" />
									</td>
									<td><%=CommonUtils.getDistanceHour(user.getUpdateTime(), new Date()) %></td>
								</tr>
					<%
							}
						}
					%>
				</tbody>
			</table>
			<div class="clear_both">
				<input type="button" class="button_blue" value="刷新" onclick="reload();">
				<input type="button" class="button_blue" value="关闭" onclick="closeLayer();">
			</div>
		</div>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
		//关闭窗口
		function closeLayer() {
			//获取窗口索引关闭窗口
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
		//刷新页面
		function reload() {
			window.location.reload();
		}
	</script>
</html>