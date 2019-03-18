<%@ page language="java"
	import="java.util.*,com.sunshine.monitor.system.ws.server.VehPassrec,com.dyst.base.utils.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
<div class="show_style2 bannerbox">
	<div id="big_mess_div">
		<c:forEach items="${pageResult.items }" var="data" varStatus="status">
			<div class="mess_div ${status.index }" <c:if test="${status.index ne 0 }">style="display:none;"</c:if>>
				<fieldset style="-moz-border-radius: 8px; border: #D2691E 1px solid;">
					<legend style="color: #333; font-weight: bold; font-size: large; margin-left: 30px;">
						过车信息
					</legend>
					<table id="three_first_table">
						<tr>
							<td>
								车牌号码:&nbsp;&nbsp;<span class="hphm_str">${data.hphm }</span>
							</td>
							<td>
								号牌种类:&nbsp;&nbsp;<span>${data.hpzlmc }</span>
							</td>
						</tr>
						<tr>
							<td>
								通过时间:&nbsp;&nbsp;<span>${data.gcsj }</span>
							</td>
							<td>
								车牌颜色:&nbsp;&nbsp;<span>${data.hpysmc }</span>
							</td>
						</tr>
						<tr>
							<td>
								上传时间:&nbsp;&nbsp;<span>${data.rksj }</span>
							</td>
							<td>
								隶属城市:&nbsp;&nbsp;<span>${data.city }</span>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								卡点名称:&nbsp;&nbsp;<span>${data.kdmc }</span>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								行驶方向:&nbsp;&nbsp;<span>${data.fxmc }</span>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								行驶车道:&nbsp;&nbsp;<span>${data.cdbh }</span>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset style="-moz-border-radius: 8px; border: none;height: 150px;">
					
				</fieldset>
			</div>
		</c:forEach>
	</div>
	<div id="focus">
		<ul>
			<c:forEach items="${pageResult.items }" var="data" varStatus="status">
				<li>
					<a href="" target="_blank"> <img class="lunbo" src="${data.tp1}" alt=""/> </a>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<script type="text/javascript" src="<%=basePath%>views/clcx/roamorbit/js/simplefoucs.js"></script>
<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
<input type="hidden" id="failInput" value="<%=failfure %>" />
<input type="hidden" id="totalPage" value="${pageResult.totalPageCount }"/>