<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>详细信息</title>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/sb/cloud-zoom.1.0.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
		<style type="text/css">
			.slider_one{
				display: block;
			    float: left;
			    position: relative;
			    width: 255px;
			    font-size:11pt;
			}
			.title_one{
				display: block;
			    float: left;
			    height: 21px;
			    margin: 2px;
			    position: relative;
			    width: 80px;
			}
			.content_one{
				color:blue;
				display: block;
			    float: left;
			    height: 21px;
			    margin: 2px;
			    position: relative;
			    width: 160px;
			}
		</style>
	</head>
	<body>
		<table align="center" border="0" width="60%">
			<tr>
				<td>
					<div class="zoom-small-image" onclick="showTotalPic('${sb.tp1Url }')"> <a href='${sb.tp1Url }' class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${sb.tp1Url }" height="300px" width="400px"/></a> </div>
				</td>
				<td>
					<div class="zoom-small-image" onclick="showTotalPic('${sb.tp2Url }')"> <a href='${sb.tp2Url }' class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${sb.tp2Url }" height="300px" width="400px"/></a> </div>
				</td>
			</tr>
		</table>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">过车信息</legend>
				<div class="slider_one">
					<div class="title_one">车牌号码:</div>
					<div class="content_one">${sb.hphm }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">车牌颜色:</div>
					<div class="content_one">${cplxMap[sb.cplx] }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">行驶车道:</div>
					<div class="content_one">${sb.cdid }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">通过时间:</div>
					<div class="content_one">${sb.sbsj }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">上传时间:</div>
					<div class="content_one">${sb.scsj }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">通过速度:</div>
					<div class="content_one">${sb.sd }</div>
				</div>
				<div class="slider_one" style="width: 700px">
					<div class="title_one">监测点:</div>
					<div class="content_one" style="width:500px">${jcdMap[sb.jcdid] }</div>
				</div>
			</fieldset>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">车辆信息</legend>
				<div class="slider_one">
					<div class="title_one">中文品牌:</div>
					<div class="content_one">${vehicle.clpp1 }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">车辆型号:</div>
					<div class="content_one">${vehicle.clxh }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">发动机号:</div>
					<div class="content_one">${vehicle.fdjh }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">车驾号:</div>
					<div class="content_one">${vehicle.clsbdh }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">车身颜色:</div>
					<div class="content_one">${vehicle.csys }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">车辆状态:</div>
					<div class="content_one">${vehicle.jdczt }</div>
				</div>
				<div class="slider_one" style="width: 700px">
					<div class="title_one">车辆所有人:</div>
					<div class="content_one" style="width: 500px">${vehicle.jdcsyr }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">联系电话:</div>
					<div class="content_one">${vehicle.lxfs }</div>
				</div>
				<div class="slider_one">
					<div class="title_one">身份证号:</div>
					<div class="content_one">${vehicle.sfzh }</div>
				</div>
				<div class="slider_one" style="width: 700px">
					<div class="title_one">详细住址:</div>
					<div class="content_one" style="width: 500px">${vehicle.djzzxz }</div>
				</div>
		</fieldset>
	</body>
	<script type="text/javascript">
		function showTotalPic(url){
			parent.layer.open({
				  type: 2,
				  title:false,
				  area:['800px','600px'],
				  closeBtn:2,
				  shadeClose:true,
				  content: ["<%=basePath%>bdController/zoomImage.do?tpUrl="+url,'no']
				});
		}
	</script>
</html>