<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	
	String result = (String)request.getAttribute("result");
	if(result == null || "".equals(result.trim()) || "0".equals(result.trim())){
%>
		<SCRIPT type="text/javascript">
			alert("查询失败！");
		</SCRIPT>
<%
	}
%>
	<table id="table1">
		<thead>
			<tr>
				<td>序号</td>
				<td>部门名称</td>
<!--				<td>布控审批及时率</td>-->
<!--				<td>撤控审批及时率</td>-->
				<td>布控签收及时率</td>
				<td>撤控签收及时率</td>
				<td>预警签收及时率</td>
				<td>指令下达及时率</td>
				<td>指令签收及时率</td>
				<td>指令反馈及时率</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${ywczqkjcList }" varStatus="status">
				<tr id="tr_result">
					<td>${data.sn }</td>
					<td>${data.bmmc }</td>
<!--					<td>${data.bkspjsl }</td>-->
<!--					<td>${data.ckspjsl }</td>-->
					<td><c:if test="${data.bmbh ne '440300010100' }"><a href="javascript:alertInfo('${data.bmmc }', '${data.bkqszs }', '${data.bkqsjss }', '${data.bkqscss }', '${data.bkwqss }', '${data.bkqsjsl }');" style="text-decoration: none;">${data.bkqsjsl }</a></c:if><c:if test="${data.bmbh eq '440300010100' }">-</c:if></td>
					<td><c:if test="${data.bmbh ne '440300010100' }"><a href="javascript:alertInfo('${data.bmmc }', '${data.ckqszs }', '${data.ckqsjss }', '${data.ckqscss }', '${data.ckwqss }', '${data.ckqsjsl }');" style="text-decoration: none;">${data.ckqsjsl }</a></c:if><c:if test="${data.bmbh eq '440300010100' }">-</c:if></td>
					<td><a href="javascript:alertInfo('${data.bmmc }', '${data.yjqszs }', '${data.yjqsjss }', '${data.yjqscss }', '${data.yjwqss }', '${data.yjqsjsl }');" style="text-decoration: none;">${data.yjqsjsl }</a></td>
					<td><c:if test="${data.bmbh eq '440300010100' }"><a href="javascript:alertInfo('${data.bmmc }', '${data.zlxdzs }', '${data.zlxdjss }', '${data.zlxdcss }', '0', '${data.zlxdjsl }');" style="text-decoration: none;">${data.zlxdjsl }</a></c:if><c:if test="${data.bmbh ne '440300010100' }">-</c:if></td>
					<td><c:if test="${data.bmbh ne '440300010100' }"><a href="javascript:alertInfo('${data.bmmc }', '${data.zlqszs }', '${data.zlqsjss }', '${data.zlqscss }', '${data.zlwqss }', '${data.zlqsjsl }');" style="text-decoration: none;">${data.zlqsjsl }</a></c:if><c:if test="${data.bmbh eq '440300010100' }">-</c:if></td>
					<td><c:if test="${data.bmbh ne '440300010100' }"><a href="javascript:alertInfo('${data.bmmc }', '${data.zlfkzs }', '${data.zlfkjss }', '${data.zlfkcss }', '${data.zlwfks }', '${data.zlfkjsl }');" style="text-decoration: none;">${data.zlfkjsl }</a></c:if><c:if test="${data.bmbh eq '440300010100' }">-</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>