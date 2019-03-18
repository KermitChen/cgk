<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>详细信息</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>
	<body>
		<div class="content" style="width: 800px;height: 425px;margin:0 auto;">
	    	<div class="content_wrap" style="width:800px;height: 425px;">
				<table align="center" border="0">
					<tr>
						<td>
							<div style="margin: 0px auto;width: 350px;" class="zoom-small-image" onclick="showTotalPic('${sbDto.tp1Url }');"><a href="${sbDto.tp1Url }" class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${sbDto.tp1Url }" height="200px" width="350px"/></a></div>
						</td>
						<td>
							<div style="margin: 0px auto;width: 350px;" class="zoom-small-image" onclick="showTotalPic('${sbDto.tp1Url }');"><a href="${sbDto.tp2Url }" class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${sbDto.tp2Url }" height="200px" width="350px"/></a></div>
						</td>
					</tr>
				</table>
				<div class="slider_body">
				    <div class="slider_selected_left">
				       	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号牌号码：
				    </div>
				    <div class="slider_selected_right" style="">
				        <div class="img_wrap">
				            <div class="select_wrap select_input_null">
				            	<input id="hphm" name="hphm" type="text" class="slider_input" readonly="readonly" value="${ewarning.hphm }" style="background-color:#FFFFFF;"/>
				       		</div>
				   		</div>  
					</div>
				</div>
		        <div class="slider_body">
				   	<div class="slider_selected_left">
				    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号牌种类：
				   	</div>
				    <div class="slider_selected_right" style="">
				    	<div class="img_wrap">
							<div class="select_wrap select_input_null">
				    			<input id="cplx" name="cplx" type="text" class="slider_input" readonly="readonly" value="<c:forEach items="${dicList}" var="dic"><c:if test="${dic.typeCode eq 'HPZL' and dic.typeSerialNo eq ewarning.hpzl}">${dic.typeDesc }</c:if></c:forEach>"/>
				             </div>
				   		</div>  
				    </div>
		        </div>
		       	<div class="slider_body">
				    <div class="slider_selected_left">
				        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行驶车道：
				   	</div>
				   	<div class="slider_selected_right" style="">
				    	<div class="img_wrap">
				  			<div class="select_wrap select_input_null">
				  				<input id="cdid" name="cdid" type="text" class="slider_input" readonly="readonly" value="${ewarning.cdid }"/>
							</div>
						</div>  
				  	</div>
		  		</div>
				<div class="slider_body">
					<div class="slider_selected_left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;通过时间：
					</div>
					<div class="slider_selected_right" style="">
						<div class="img_wrap">
							<div class="select_wrap select_input_null">
								<input id="sbsj" name="sbsj" type="text" class="slider_input" readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewarning.tgsj }"/>"/>
							</div>
						</div>  
					</div>
				</div>
				<div class="slider_body">
					<div class="slider_selected_left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上传时间：
					</div>
					<div class="slider_selected_right" style="">
						<div class="img_wrap">
							<div class="select_wrap select_input_null">
								<input id="scsj" name="scsj" type="text" class="slider_input" readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewarning.scsj }"/>"/>
							</div>
						</div>  
					</div>
				</div>
		        <div class="slider_body">
				    <div class="slider_selected_left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报警时间：
					</div>
					<div class="slider_selected_right" style="">
						<div class="img_wrap">
							<div class="select_wrap select_input_null">
								<input id="bjsj" name="bjsj" type="text" class="slider_input" readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewarning.bjsj }"/>"/>
							</div>
						</div>  
					</div>
				</div>
				<div class="slider_body">
					<div class="slider_selected_left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行驶速度：
					</div>
					<div class="slider_selected_right" style="">
						<div class="img_wrap">
							<div class="select_wrap select_input_null">
								<input id="sd" name="sd" type="text" class="slider_input" readonly="readonly" value="<c:if test="${ewarning.sd ne '0.0'}">${ewarning.sd }</c:if>"/>
							</div>
						</div>  
					</div>
				</div>
				<div class="slider_body">
					<div class="slider_selected_left">
				       	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点：
					</div>
					<div class="slider_selected_right">
						<textarea id="jcdid" style="width: 212px;height: 20px;" readonly="readonly">${ewarning.jcdmc }</textarea>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript">
		//放大图片
		function showTotalPic(url){
		    //调用
			parent.layer.open({
				type: 2,
				title:false,
				area:['800px', '600px'],
				closeBtn:2,
				shadeClose:true,
				content: ["<%=basePath%>bdController/zoomImage.do?tpUrl=" + url, 'no']
			});
		}
	</script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/cloud-zoom.1.0.2.min.js"></script>
</html>