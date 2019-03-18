<%@ page language="java" import="com.dyst.utils.CommonUtils,java.util.*,com.dyst.earlyWarning.entities.InstructionSign,com.dyst.BaseDataMsg.entities.Dictionary" pageEncoding="utf-8"%>
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
		<title>指令反馈情况清单</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css" />
	</head>
	<body>
		<table>
			<thead>
				<tr>
					<td width="40" align="center">序号</td>
					<td width="80" align="center">号牌号码</td>
					<td width="80" align="center">号牌种类</td>
					<td width="120" align="center">指令时间</td>
					<td width="120" align="center">反馈单位</td>
					<td width="80" align="center">反馈人</td>
					<td width="80" align="center">反馈状态</td>
					<td width="120" align="center">反馈时间</td>
					<td width="80" align="center">时差</td>
				</tr>
			</thead>
			<tbody>
				<%
					List<InstructionSign> instructionSignFkList = (List<InstructionSign>)request.getAttribute("instructionSignFkList");
					List<Dictionary> dicList = (List<Dictionary>)request.getAttribute("dicList");
					for(int i=0;i < instructionSignFkList.size();i++){
						InstructionSign instructionSign = instructionSignFkList.get(i);
				%>
						<tr>
							<td><%=i+1 %></td>
							<td><%=instructionSign.getHphm() %></td>
							<td>
								<%
									for(int j=0;j < dicList.size();j++){
										Dictionary dic = dicList.get(j);
										if(instructionSign.getHpzl() != null && "HPZL".equals(dic.getTypeCode()) && instructionSign.getHpzl().equals(dic.getTypeSerialNo())){
								%>
											<%=dic.getTypeDesc() %>
								<%
											break;
										}
									}
								%>
							</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="<%=instructionSign.getZlsj() %>" /></td>
							<td><%=(instructionSign.getZlbmmc() != null ? instructionSign.getZlbmmc():"") %></td>
							<td><%=(instructionSign.getFkrmc() != null ? instructionSign.getFkrmc():"") %></td>
							<td><%=(instructionSign.getFkztmc() != null ? instructionSign.getFkztmc():"") %></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="<%=instructionSign.getFksj() %>" /></td>
							<td><%=CommonUtils.getDistanceHour(instructionSign.getFksj(), instructionSign.getZlsj()) %></td>
						</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</body>
</html>