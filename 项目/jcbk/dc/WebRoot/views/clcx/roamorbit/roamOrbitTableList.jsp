<%@ page language="java" import="java.util.*,com.sunshine.monitor.system.ws.server.VehPassrec,com.dyst.base.utils.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String result = (String)request.getAttribute("result");
	String failfure = (String)request.getAttribute("failfure");
%>
		
<%
	//查询失败的城市
	if("1".equals(result) && !"".equals(failfure)){
%>
		<SCRIPT type="text/javascript">
			$(document).ready(function(){
			    var fail = document.getElementById("failInput");
				alert("查询失败的城市：" + fail.value);
			});
		</SCRIPT>
<%
	} else if("0".equals(result)){
%>
		<SCRIPT type="text/javascript">
			$(document).ready(function(){
				alert("查询失败！");
			});
		</SCRIPT>
<%
	}
%>
	<table>
		<thead>
			<tr>
				<td>序号</td>
				<td>城市</td>
				<td>车牌号码</td>
				<td>号牌种类</td>
				<td>车牌颜色</td>
				<td>识别时间</td>
				<td>入库时间</td>
				<td>车道</td>
				<td>监测点</td>
				<td>行驶方向</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${pageResult.items }" varStatus="status">
				<tr ondblclick="javascript:showSbDetail('${data.id}');">
					<td width="40px">${data.id}</td>
					<td width="60px">${data.city }</td>
					<td width="80px">${data.hphm }</td>
					<td width="60px">${data.hpzlmc }</td>
					<td width="60px">${data.hpysmc }</td>
					<td width="80px">${data.gcsj }</td>
					<td width="80px">${data.rksj }</td>
					<td width="40px">${data.cdbh }</td>
					<td>${data.kdmc }</td>
					<td width="120px">${data.fxmc }</td>
					<td width="60px">
						<a href="javascript:showSbDetail('${data.id}');">详细信息</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
	<input type="hidden" id="failInput" value="<%=failfure %>" />