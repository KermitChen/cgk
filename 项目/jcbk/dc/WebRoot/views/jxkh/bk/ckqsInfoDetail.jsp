<%@ page language="java" import="com.dyst.utils.CommonUtils,java.util.*,com.dyst.dispatched.entities.DisReceive,com.dyst.BaseDataMsg.entities.Dictionary" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="zh-CN">
	<head>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
		<base href="<%=basePath%>">
		<title>撤控签收情况清单</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css" />
	</head>
	<body>
		<table>
			<thead>
				<tr>
					<td width="40" align="center">序号</td>
					<td width="80" align="center">号牌号码</td>
					<td width="80" align="center">号牌种类</td>
					<td width="120" align="center">通知单位</td>
					<td width="120" align="center">通知时间</td>
					<td width="120" align="center">签收单位</td>
					<td width="80" align="center">签收人</td>
					<td width="80" align="center">签收状态</td>
					<td width="120" align="center">签收时间</td>
					<td width="80" align="center">时差</td>
				</tr>
			</thead>
			<tbody>
				<%
					List<DisReceive> ckDisReceiveList = (List<DisReceive>)request.getAttribute("ckDisReceiveList");
					List<Dictionary> dicList = (List<Dictionary>)request.getAttribute("dicList");
					for(int i=0;i < ckDisReceiveList.size();i++){
						DisReceive disReceive = ckDisReceiveList.get(i);
				%>
						<tr>
							<td><%=i+1 %></td>
							<td><%=(disReceive.getHphm() != null ? disReceive.getHphm():"") %></td>
							<td>
								<%
									for(int j=0;j < dicList.size();j++){
										Dictionary dic = dicList.get(j);
										if(disReceive.getHpzl() != null && "HPZL".equals(dic.getTypeCode()) && disReceive.getHpzl().equals(dic.getTypeSerialNo())){
								%>
											<%=dic.getTypeDesc() %>
								<%
											break;
										}
									}
								%>
							</td>
							<td><%=(disReceive.getXfdwmc() != null ? disReceive.getXfdwmc():"") %></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="<%=disReceive.getXfsj() %>" /></td>
							<td><%=(disReceive.getQrdwmc() != null ? disReceive.getQrdwmc():"") %></td>
							<td><%=(disReceive.getQrr() != null ? disReceive.getQrr():"") %></td>
							<td><%=("1".equals(disReceive.getQrzt())?"已签收":"未签收") %></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="<%=disReceive.getQrsj() %>" /></td>
							<td><%=CommonUtils.getDistanceTime(disReceive.getQrsj(), disReceive.getXfsj()) %></td>
						</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</body>
</html>