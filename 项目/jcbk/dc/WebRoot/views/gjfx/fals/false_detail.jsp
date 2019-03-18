<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详细信息</title>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/cloud-zoom.1.0.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript" src="<%=basePath%>common/js/time/moment.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<link rel="stylesheet" href="<%=basePath%>common/css/sb/falsDetail.css" type="text/css">
</head>
<body>
	<div class="max_div">
	<div id="pre" class="pre"> </div>
	<div class="content">
		<div class="top_div">
			<table id="img_table" align="center" border="0" width="60%">
				<tr>
					<td>
						<div class="zoom-small-image" onclick="showTotalPic('${sb.tp1Url }')"> <a href="${sb.tp1Url }"  class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${sb.tp1Url }" height="250px" width="400px"/></a> </div>
					</td>
					<td>
						<div class="zoom-small-image" onclick="showTotalPic('${sb.tp2Url }')"> <a href="${sb.tp2Url }"  class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${sb.tp2Url }" height="250px" width="400px"/></a> </div>
					</td>
				</tr>
			</table>
			<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
				<legend style=" color:#333; font-weight:bold; font-size: medium; margin-left: 30px;" id="clxx_legend">过车记录</legend>
				<table  id="vehicle_table"  border="0" cellpadding="2" cellspacing="1"  width="100%">
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
		</div>
		<div class="bottom_div">
			<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style=" color:#333; font-weight:bold; font-size: medium; margin-left: 30px;">标记无效人员名单</legend>	
				<div class="left_div">
				<table>
					<thead>
						<tr>
							<td>警号</td>
							<td>姓名</td>
							<td>标记时间</td>
							<td>原因</td>
							<td>真实车牌号</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${delList }" var="s" varStatus="c">
							<tr>
								<td>${s.pno }</td>
								<td>${s.pname }</td>
								<td>${fn:substringBefore(s.time,".") }</td>
								<td>${s.reason }</td>
								<td>${s.realPlate }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
				<div class="right_div">
					<c:if test="${flag eq '0' }">
						<input class="button_blue" type="button" value="无效" id="delete_button">
					</c:if>
					<input type="hidden" value="${fla.id }" id="jcpid">
					<input type="hidden" value="${index }" id="index">
				</div>
			</fieldset>
		</div>
	</div>
	<div id="next" class="next"> </div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$("#pre").click(function(){
			var index = $.trim($("#index").val());
			if (index == '0') {
				layer.msg('已到第一条数据!');
				return;
			}
			layer.load();
			getData(parseInt(index)-1,'0');
		});
		$("#next").click(function(){
			var index = $.trim($("#index").val());
			layer.load();
			getData(parseInt(index)+1,'1');
		});
		$("#delete_button").click(function(){
			var id = $.trim($("#jcpid").val());
			isDelete(id);
		});
	});
	function getData(index,flag){
		var jcpid = $("#jcpid").val();//假牌id
		$.ajax({
			url:'<%=basePath%>fals/getFalseSb.do?' + new Date().getTime(),
			type:"POST",
			data:{index:index,jcpid:jcpid},
			success:function(data){
				layer.closeAll('loading');
				console.log(data);
				if(data != null && data.length > 1 && data[0] =='1'){
					appendSb(data[1]);
					var index = $.trim($("#index").val());
					if(flag == '1'){
						$("#index").val(parseInt($("#index").val())+1);
					}else if(flag == '0'){
						$("#index").val(parseInt($("#index").val())-1);
					}
				}else{
					layer.closeAll('loading');
					layer.msg('已到最后一条数据！');
				}
			},
			error: function () {//请求失败处理函数
				layer.closeAll('loading');
				layer.msg('查询失败！');
			}
		});
	}
	function appendSb(data){
		console.log(data);
		$("#img_table").empty();
		var img = "<tr><td><div onclick=\"showTotalPic('"+data.tp1Url+"')\"> <a href='javascript:void(0);'><img src='"+data.tp1Url+"' height=\"250px\" width=\"400px\"/></a> </div>"
					+"</td><td>"+
						"<div onclick=\"showTotalPic('"+data.tp1Ur2+"')\"> <a href='javascript:void(0);'><img src='"+data.tp1Ur2+"' height=\"250px\" width=\"400px\"/></a> </div>"
					"</td></tr>";
		$("#img_table").append(img);
		$("#data_table2 span:eq(0)").text(data.cphid);
		var arr = ['hphm','cplx','cdid','sbsj','scsj','sd','jcdid'];
		for(i=0;i<arr.length;i++){
			addSpan(arr[i],data[arr[i]]);
		}
	}	
	function addSpan(id,val){
		if(val == null || val == ''){
			$("#"+id).text('');
		}else{
			if(id == 'sbsj' || id == 'scsj'){
				$("#"+id).text(moment(val).format('YYYY-MM-DD HH:mm:ss'));
			}else if(id == 'cplx' || id == 'cplx2'){
				$("#"+id).text(getCplx(val));
			}else if(id == 'jcdid' || id == 'jcdid2'){
				$("#"+id).text(getJcdName(val));
			}else{
				$("#"+id).text(val);
			}
		}
	}
	function getCplx(cplx){
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];  
		var cplxName = "";
		$.ajax({
			async:false,
			type: 'POST',
			data:{cplx:cplx},
			dataType : "json",
			url: basePath + "/bdController/tranCplx.do",//请求的action路径
			error: function () {//请求失败处理函数
				layer.msg("翻译车牌类型失败！");
			},
			success:function(data){ //请求成功后处理函数。
				cplxName = data;
			}
		});
		return cplxName;
	}
	//是否删除
	function isDelete(id){
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		$.ajax({
			async:true,
			type:'post',
			data:{id:id},
			dataType : "json",
			url: basePath + "/fals/isDelete.do",//请求的action路径
			error: function () {//请求失败处理函数
				layer.msg('请求失败！');
			},
			success:function(data){
				console.log(data);
				if(data == null){
					layer.msg('请求失败!');
				}else if(data != null && data == '1'){
					markData(id);
				}else if(data != null && data == '2'){
					layer.confirm('已到指定人数，是否确认删除？',{icon: 7, title:'提示',btn: ['确定','取消']}
					  , function(index){
						layer.close(index);
						deleteData(id);
					}, function(index){
						layer.close(index);
					});
				}
			}
		});
	}
	function markData(id){
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];  
		var url = basePath + "/fals/turnToDel.do?id="+id;
		parent.layer.open({
		  type: 2,
		  title:'标记无效',
		  area:['600px','210px'],
		  shadeClose:false,
		  content: [url,'no']
		});
	}
	//删除
	function deleteData(id){
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		$.ajax({
			async:true,
			type:'post',
			data:{id:id},
			dataType : "json",
			url: basePath + "/fals/delete.do",//请求的action路径
			error: function () {//请求失败处理函数
				layer.msg('请求失败！');
			},
			success:function(data){
				console.log(data);
				if(data == null || data == '0'){
					layer.msg('请求失败!');
				}else if(data != null && data == '1'){
					layer.msg('删除成功!');
					parent.parent.doGoPage(1);
					parent.parent.layer.closeAll('iframe');
				}
			}
		});
	}
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