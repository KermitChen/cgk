<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
	<base href="<%=basePath%>">
	<title>过车明细</title>
</head>
<body>
<jsp:include page="/common/Head.jsp" />
<link rel="stylesheet" href="<%=basePath%>common/css/sb/picGis.css" type="text/css"/>
	<div id="divTitle">
		<span id="spanTitle">过车明细</span>
	</div>
    <div class="content">
    	<div class="content_wrap" <c:if test="${fn:length(pageResult.items) le 0}">style="display:none;"</c:if>>
    		<div id="main_div">
    			<div id="detail_lunbo" class="left_div">
    				<div class="pre"><div class="left"></div></div>
    				<div class="detail_pic_div">
    					<img class="detail_pic" alt="" src="${pageResult.items[0].tp1Url }">
    				</div>
    				<div class="next"><div class="right"></div></div>
    			</div>
    			<div class="right_div" id="container">
    			</div>
    		</div>
    		<div id="bottom_div">
    			<div id="gene_lunbo" class="general_dics">
    				<div class="pre" <c:if test="${pageResult.pageNo eq 1}">style="display:none;"</c:if>><div class="left"></div></div>
    				<div class="pre_zhan" <c:if test="${pageResult.pageNo ne 1}">style="display:none;"</c:if>></div>
    				<c:forEach items="${pageResult.items }" var="sb" varStatus="c">
    					<div class="simple_gene_dic ${c.index }" data-value="${c.index }"><img class="gene_dic" <c:if test="${c.index eq 0 }">style="border:2px solid #E05A28;"</c:if> src="${sb.tp1Url }"><input type="hidden" name="dataId" value="${sb.tp1 }"></div>
    				</c:forEach>
    				<div class="next" <c:if test="${pageResult.pageNo eq pageResult.totalPageCount}">style="display:none;"</c:if>><div class="right"></div></div>
    			</div>
    			<div class="page_number_div">
    				<form action="<%=basePath%>clcx/picGis.do" method="post">
    					<input type="hidden" name="kssj" value="${args.kssj }">
    					<input type="hidden" name="jssj" value="${args.jssj }">
    					<input type="hidden" name="hphm" value="${args.hphm }">
    					<input type="hidden" id="pageNo" name="pageNo" value="${pageResult.pageNo }">
    				</form>
    				<input type="hidden" id="length" value="${fn:length(pageResult.items) }">
    				<span>${pageResult.pageNo }</span>/
    				<span id="totalPageCount">${pageResult.totalPageCount }</span>
    			</div>
    		</div>
    	</div>	
	</div>
 <jsp:include page="/common/Foot.jsp" />
</body>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
<script type="text/javascript" src="<%=basePath%>common/js/sb/showSbInGis.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
<script type="text/javascript">
	var map = new BMap.Map("container");      
	var point = new BMap.Point(114.063081, 22.547416);    // 创建点坐标 
	map.centerAndZoom(point,11);                     // 初始化地图,设置中心点坐标和地图级别。
	map.addControl(new BMap.NavigationControl());
	map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
	map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
	map.enableKeyboard();                         // 启用键盘操作。
	map.setMinZoom(11);
</script>
</html>