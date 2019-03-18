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
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<%-- <script type="text/javascript" src="<%=basePath%>views/clcx/js/cloud-zoom.1.0.2.min.js"></script> --%>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<style type="text/css">
		.button_blue{
			padding: 6px 18px;
		    border: none;
		    color: #fff;
		    background: url("${pageContext.request.contextPath}/common/images/submit_b.jpg") center no-repeat;
		    border-radius: 6px;
		    outline: none;
		    cursor: pointer;
		    margin: 4px 10px;
		    border: 1px solid #fff;
		}
		.button_blue:hover{
			color: #555;
			background: url("${pageContext.request.contextPath}/common/images/submit_g.jpg") center no-repeat;
		}
		input{
			border-style:none;
			readonly:readonly;
			color:blue;
		}
		span{
			color:blue;
			font-size:11pt;
			font-family:"Microsoft YaHei";
		}
		td{
			align:left;
			font-size:11pt;
			font-family:"Microsoft YaHei";
		}
		th{
			align:center;
		}
		.button_div{
			display: block;
		    float: left;
		    height: 40px;
		    margin-top: 8px;
		    position: relative;
		    width: 100%;
		    text-align: center;
		}
	</style>
</head>
<body>
	
		<table align="center" border="0" width="60%">
			<tr>
				<td>
					<div class="zoom-small-image" id="tp1"> <a href='${sb.tp1Url }' class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${sb.tp1Url }" height="270px" width="380px"/></a> </div>
				</td>
				<td>
					<div class="zoom-small-image" id="tp2"> <a href='${sb.tp2Url }' class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${sb.tp2Url }" height="270px" width="380px"/></a> </div>
				</td>
			</tr>
		</table align="center" border="0" width="60%">
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">过车信息</legend>
			<table>
				<tr>
					<td>
						车牌号码:&nbsp;&nbsp;<span id="hphm">${sb.hphm }</span>
					</td>
					<td>
						车牌类型:&nbsp;&nbsp;<span id="cplx">${cplxMap[sb.cplx] }</span>
					</td>
					<td>
						行驶车道:&nbsp;&nbsp;<span id="cdid">${sb.cdid }</span>
					</td>
				</tr>
				<tr>
					<td>
						通过时间:&nbsp;&nbsp;<span id="sbsj">${sb.sbsj }</span>
					</td>
					<td>
						上传时间:&nbsp;&nbsp;<span id="scsj">${sb.scsj }</span>
					</td>
					<td>
						通过速度:&nbsp;&nbsp;<span id="sd">${sb.sd }</span>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						监测点:&nbsp;&nbsp;<span id="jcdid">${jcdMap[sb.jcdid] }</span>
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">车辆信息</legend>
			<table>
				<tr>
					<td>
						中文品牌:&nbsp;&nbsp;<span id="clpp1">${vehicle.clpp1 }</span>
					</td>
					<td>
						车辆型号:&nbsp;&nbsp;<span id="clxh">${vehicle.clxh }</span>
					</td>
					<td>
						发动机号:&nbsp;&nbsp;<span id="fdjh">${vehicle.fdjh }</span>
					</td>
				</tr>
				<tr>
					<td>
						车驾号:&nbsp;&nbsp;<span id="clsbdh">${vehicle.clsbdh }</span>
					</td>
					<td>
						车身颜色:&nbsp;&nbsp;<span id="csys">${vehicle.csys }</span>
					</td>
					<td>
						车辆状态:&nbsp;&nbsp;<span id="jdczt">${vehicle.jdczt }</span>
					</td>
				</tr>
				<tr>
					<td>
						车辆所有人:&nbsp;&nbsp;<span id="jdcsyr">${vehicle.jdcsyr }</span>
					</td>
					<td>
						联系电话:&nbsp;&nbsp;<span id="lxfs">${vehicle.lxfs }</span>
					</td>
					<td>
						身份证号:&nbsp;&nbsp;<span id="sfzh">${vehicle.sfzh }</span>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						详细住址:&nbsp;&nbsp;<span id="djzzxz">${vehicle.djzzxz }</span>
					</td>
				</tr>
			</table>
		</fieldset>
		<div class="button_div">
			<input type="hidden" name="tpids" id="tpids" value="${tpids }">
			<input id="query_button" name="query_button" type="button" class="button_blue" value="上一条" onclick="findSb(0);">
			<input id="reset_button" name="reset_button" type="button" class="button_blue" value="下一条" onclick="findSb(1);">
		</div>
</body>
<script type="text/javascript">
	var index = 0;
	var tpids = $.trim($("#tpids").val());
	var length;
	if(!isNullOrEmpty(tpids)){
		var arr = new Array();
		arr = tpids.split(",");
		length = arr.length;
		console.info(arr);
	}
	function findSb(flag){
		if(flag == 0){
			index -= 1;
			if (index == -1) { index = length - 1; }		
		}else{
			index += 1;
			if (index == length) { index = 0; }
		}
		$.ajax({
			url:'<%=basePath%>/clcx/getByTpid4Details.do?' + new Date().getTime(),
			method:"post",
			data:{tpids:tpids,index:index},
			success:function(data){
				$("#hphm").text(data[0].hphm);
				$("#cplx").text(data[0].cplx);
				$("#cdid").text(data[0].cdid);
				$("#sbsj").text(data[0].sbsj);
				$("#scsj").text(data[0].scsj);
				$("#sd").text(data[0].sd);
				$("#jcdid").text(data[0].jcdid);
				//$("#tp1 a").attr("href",data[0].tp1Url);
				$("#tp1 img").attr("src",data[0].tp1Url);
				//$("#tp2 a").attr("href",data[0].tp2Url);
				if(data[0].tp2Url == null || data[0].tp2Url == ""){
					$("#tp2 img").attr("src","");
				}else{
					$("#tp2 img").attr("src",data[0].tp2Url);
				}

				$("#clpp1").text(data[1].clpp1);
				$("#clxh").text(data[1].clxh);
				$("#fdjh").text(data[1].fdjh);
				$("#clsbdh").text(data[1].clsbdh);
				$("#csys").text(data[1].csys);
				$("#jdczt").text(data[1].jdczt);
				$("#jdcsyr").text(data[1].jdcsyr);
				$("#lxfs").text(data[1].lxfs);
				$("#sfzh").text(data[1].sfzh);
				$("#djzzxz").text(data[1].djzzxz);
			},
			error: function () {//请求失败处理函数
				alert('查询失败！');
			}
		});
	}
</script>
</html>