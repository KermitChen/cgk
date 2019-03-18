<%@ page import="org.springframework.ui.Model"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
					<div class="zoom-small-image" onclick="showTotalPic('${vehPassrec.tp1 }')"><a href='${vehPassrec.tp1 }' class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${vehPassrec.tp1 }" height="300px" width="600px"/></a></div>
				</td>
			</tr>
		</table>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;margin-top: 20px;">
			<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">过车信息</legend>
			<div class="slider_one">
				<div class="title_one">车牌号码:</div>
				<div class="content_one">${vehPassrec.hphm }</div>
			</div>
			<div class="slider_one">
				<div class="title_one">号牌种类:</div>
				<div class="content_one">${vehPassrec.hpzlmc }</div>
			</div>
			<div class="slider_one">
				<div class="title_one">车牌颜色:</div>
				<div class="content_one">${vehPassrec.hpysmc }</div>
			</div>
			<div class="slider_one">
				<div class="title_one">通过时间:</div>
				<div class="content_one">${vehPassrec.gcsj }</div>
			</div>
			<div class="slider_one">
				<div class="title_one">入库时间:</div>
				<div class="content_one">${vehPassrec.rksj }</div>
			</div>
			<div class="slider_one">
				<div class="title_one">隶属城市:</div>
				<div class="content_one">${vehPassrec.city }</div>
			</div>
			<div class="slider_one" style="width: 508px;">
				<div class="title_one">卡点名称:</div>
				<div class="content_one" style="width:400px">${vehPassrec.kdmc }</div>
			</div>
			<div class="slider_one">
				<div class="title_one">行驶车道:</div>
				<div class="content_one">${vehPassrec.cdbh }</div>
			</div>
			<div class="slider_one" style="width: 508px;">
				<div class="title_one">行驶方向:</div>
				<div class="content_one" style="width:400px">${vehPassrec.fxmc }</div>
			</div>
		</fieldset>
<!--		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">-->
<!--			<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">车辆信息</legend>-->
<!--			<div class="slider_one">-->
<!--				<div class="title_one">中文品牌:</div>-->
<!--				<div class="content_one">${vehicle.clpp1 }</div>-->
<!--			</div>-->
<!--			<div class="slider_one">-->
<!--				<div class="title_one">车辆型号:</div>-->
<!--				<div class="content_one">${vehicle.clxh }</div>-->
<!--			</div>-->
<!--			<div class="slider_one">-->
<!--				<div class="title_one">发动机号:</div>-->
<!--				<div class="content_one">${vehicle.fdjh }</div>-->
<!--			</div>-->
<!--			<div class="slider_one">-->
<!--				<div class="title_one">车驾号:</div>-->
<!--				<div class="content_one">${vehicle.clsbdh }</div>-->
<!--			</div>-->
<!--			<div class="slider_one">-->
<!--				<div class="title_one">车身颜色:</div>-->
<!--				<div class="content_one">${vehicle.csys }</div>-->
<!--			</div>-->
<!--			<div class="slider_one">-->
<!--				<div class="title_one">车辆状态:</div>-->
<!--				<div class="content_one">${vehicle.jdczt }</div>-->
<!--			</div>-->
<!--			<div class="slider_one">-->
<!--				<div class="title_one">车辆所有人:</div>-->
<!--				<div class="content_one">${vehicle.jdcsyr }</div>-->
<!--			</div>-->
<!--			<div class="slider_one">-->
<!--				<div class="title_one">联系电话:</div>-->
<!--				<div class="content_one">${vehicle.lxfs }</div>-->
<!--			</div>-->
<!--			<div class="slider_one">-->
<!--				<div class="title_one">身份证号:</div>-->
<!--				<div class="content_one">${vehicle.sfzh }</div>-->
<!--			</div>-->
<!--			<div class="slider_one" style="width: 700px">-->
<!--				<div class="title_one">详细住址:</div>-->
<!--				<div class="content_one" style="width: 500px">${vehicle.djzzxz }</div>-->
<!--			</div>-->
<!--		</fieldset>-->
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