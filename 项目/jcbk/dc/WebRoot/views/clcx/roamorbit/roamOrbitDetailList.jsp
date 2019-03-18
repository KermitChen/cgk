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
<table style="border:none;">
	<tr style="border:none;">
		<td style="border:none;">
			<div class="show_style0">
				<c:forEach items="${pageResult.items }" var="data" varStatus="status">
					<div class="detail_div" style="height:200px;">
						<div class="img_div">
							<img alt="" src="${data.tp1 }" onclick="showSbDetail('${data.id }');">
						</div>
						<div class="content_div">
							<div class="title_div">车牌号码：</div>
							<div class="text_div">${data.hphm }</div>
						</div>
						<div class="content_div">
							<div class="title_div">通过时间：</div>
							<div class="text_div">${data.gcsj }</div>
						</div>
						<div class="content_div">
							<div class="title_div">卡点名称：</div>
							<div class="text_div">${data.kdmc }</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</td>
	</tr>
</table>
<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
<input type="hidden" id="failInput" value="<%=failfure %>" />