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
	<link rel="stylesheet" href="<%=basePath%>common/css/sb/fakeDetail.css" type="text/css">
</head>
<body>
	<div class="max_div">
	<div id="pre" class="pre"> </div>
	<div class="content">
		<div class="top_div">
			<table id="img_table" align="center" border="0" width="60%">
				<tr>
					<td>
						<div class="zoom-small-image" onclick="showTotalPic('${fake.jjtp1 }')"> <a href="${fake.jjtp1 }" class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${fake.jjtp1 }" height="250px" width="400px"/></a> </div>
					</td>
					<td>
						<div class="zoom-small-image" onclick="showTotalPic('${fake.jjtp2 }')"> <a href="${fake.jjtp2 }" class='cloud-zoom' rel="position:'inside',showTitle:false,adjustX:-4,adjustY:-4"><img src="${fake.jjtp2 }" height="250px" width="400px"/></a> </div>
					</td>
				</tr>
			</table>
			<fieldset class="half_filedset">
					<legend style=" color:#333; font-weight:bold; font-size: medium; margin-left: 30px">过车记录1</legend>
				<table id="data_table1"  border="0" cellpadding="2" cellspacing="1"  width="100%">
					<tr>
						<td>
							车牌号码:&nbsp;&nbsp;<span id="cphid">${fake.cphid }</span>
						</td>
						<td>
							车牌颜色:&nbsp;&nbsp;<span id="cplx1">${cplxMap[fake.cplx1] }</span>
						</td>
					</tr>
					<tr>
						<td>
							识别时间:&nbsp;&nbsp;<span id="sbsj1">${fn:substringBefore(fake.sbsj1, ".")}</span>
						</td>
						<td>
							行驶车道:&nbsp;&nbsp;<span id="cdid1">${fake.cdid1 }</span>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							监测点:&nbsp;&nbsp;<span id="jcdid1">${jcdMap[fake.jcdid1] }</span>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="half_filedset">
					<legend style=" color:#333; font-weight:bold; font-size: medium; margin-left: 30px">过车记录2</legend>
				<table id="data_table2"  border="0" cellpadding="2" cellspacing="1"  width="100%">
					<tr>
						<td>
							车牌号码:&nbsp;&nbsp;<span id="cphid">${fake.cphid }</span>
						</td>
						<td>
							车牌颜色:&nbsp;&nbsp;<span id="cplx2">${cplxMap[fake.cplx2] }</span>
						</td>
					</tr>
					<tr>
						<td>
							识别时间:&nbsp;&nbsp;<span id="sbsj2">${fn:substringBefore(fake.sbsj2, ".")}</span>
						</td>
						<td>
							行驶车道:&nbsp;&nbsp;<span id="cdid2">${fake.cdid2 }</span>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							监测点:&nbsp;&nbsp;<span id="jcdid2">${jcdMap[fake.jcdid2] }</span>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
				<legend style=" color:#333; font-weight:bold; font-size: medium; margin-left: 30px;" id="clxx_legend">${fake.cphid }车辆登记信息</legend>
				<table  id="vehicle_table"  border="0" cellpadding="2" cellspacing="1"  width="100%">
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
		</div>
		<div class="bottom_div">
			<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style=" color:#333; font-weight:bold; font-size: medium; margin-left: 30px;">标记无效人员名单</legend>	
				<div class="left_div">
				<table>
					<tbody>
						<tr>
							<td>警号</td>
							<td>姓名</td>
							<td>标记时间</td>
							<td>原因</td>
							<td>真实车牌号</td>
						</tr>
					</tbody>
					<tbody id="del_table">
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
						<input class="button_blue" type="button" value="无效" id="delete_button" onclick="isDelete()">
					</c:if>
					<input type="hidden" value="${fake.id }" id="tpid">
					<input type="hidden" value="${index }" id="index">
					<input type="hidden" value="${pageNo }" id="pageNo">
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
			var pageNo = $.trim($("#pageNo").val());
			if (pageNo == '1' && index == '1') {
				layer.msg('已到第一条数据!');
				return;
			}
			layer.load();
			getData(((parseInt(pageNo)-1)*10+parseInt(index)-1),'0');
		});
		$("#next").click(function(){
			var index = $.trim($("#index").val());
			var pageNo = $.trim($("#pageNo").val());
			layer.load();
			getData(((parseInt(pageNo)-1)*10+parseInt(index)+1),'1');
		});
	});
	function getData(pageNo,flag){
		var hphm = parent.$("#cphid").val();
		var jcdid = parent.$("#jcdid").val();//监测点id
		var kssj = parent.$("#kssj").val();//起始时间
		var jssj = parent.$("#jssj").val();//截止时间
		var roadNo = parent.$("#dlid").val();//道路id
		$.ajax({
			url:'<%=basePath%>fake/getFakeByIndex.do?' + new Date().getTime(),
			type:"POST",
			data:{hphm:hphm,jcdid:jcdid,kssj:kssj,jssj:jssj,pageNo:pageNo,roadNo:roadNo},
			success:function(data){
				layer.closeAll('loading');
				if(data != null && data.length > 3){
					console.log(data);
					appendFake(data[0]);
					appendVehicle(data[1]);
					appendDelList(data[2],data[3]);
					var index = $.trim($("#index").val());
					if(flag == '1'){
						if(index == '10'){
							$("#index").val('1');
							$("#pageNo").val(parseInt($("#pageNo").val())+1);
						}else{
							$("#index").val(parseInt($("#index").val())+1);
						}
					}else if(flag == '0'){
						if(index == '1'){
							$("#index").val('10');
							$("#pageNo").val(parseInt($("#pageNo").val())-1);
						}else{
							$("#index").val(parseInt($("#index").val())-1);
						}
					}
					//window.location.reload();
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
	function appendFake(data){
		$("#clxx_legend").text(data.cphid+'车辆登记信息');
		$("#tpid").val(data.id);
		$("#img_table").empty();
		var img = "<tr><td><div onclick=\"showTotalPic('"+data.jjtp1+"')\"> <a href='javascript:void(0);'><img src='"+data.jjtp1+"' height=\"250px\" width=\"400px\"/></a> </div>"
					+"</td><td>"+
						"<div onclick=\"showTotalPic('"+data.jjtp2+"')\"> <a href='javascript:void(0);'><img src='"+data.jjtp2+"' height=\"250px\" width=\"400px\"/></a> </div>"
					"</td></tr>";
		$("#img_table").append(img);
		$("#data_table2 span:eq(0)").text(data.cphid);
		var arr = ['cphid','cplx1','sbsj1','cdid1','jcdid1','cplx2','sbsj2','cdid2','jcdid2'];
		for(i=0;i<arr.length;i++){
			addSpan(arr[i],data[arr[i]]);
		}
	}
	function appendVehicle(data){
		var arr = ['clpp1','clxh','fdjh','clsbdh','csys','jdczt','jdcsyr','lxfs','sfzh','djzzxz'];
		for(i=0;i<arr.length;i++){
			addSpan(arr[i],data[arr[i]]);
		}
	}
	function appendDelList(list,flag){
		$("#del_table").empty();
		var html;
		for(i=0;i<list.length;i++){
			html += "<tr><td>"+list[i].pno+"</td><td>"+list[i].pname+"</td><td>"+moment(list[i].time).format('YYYY-MM-DD HH:mm:ss')+"</td><td>"+list[i].reason+"</td><td>"+list[i].realPlate+"</td></tr>";
		}
		$("#del_table").append(html);
		$("#delete_button").remove();
		if(flag == '0'){
			$(".right_div").append("<input class=\"button_blue\" type=\"button\" value=\"无效\" id=\"delete_button\" onclick=\"isDelete()\">");
		}
	}
	function addSpan(id,val){
		if(val == null || val == ''){
			$("#"+id).text('');
		}else{
			if(id == 'sbsj1' || id == 'sbsj2'){
				$("#"+id).text(moment(val).format('YYYY-MM-DD HH:mm:ss'));
			}else if(id == 'cplx1' || id == 'cplx2'){
				$("#"+id).text(getCplx(val));
			}else if(id == 'jcdid1' || id == 'jcdid2'){
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
				console.log(data);
				cplxName = data;
			}
		});
		return cplxName;
	}
	//是否删除
	function isDelete(){
		var id = $.trim($("#tpid").val());
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		$.ajax({
			async:true,
			type:'post',
			data:{id:id},
			dataType : "json",
			url: basePath + "/fake/isDelete.do",//请求的action路径
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
					layer.confirm('到达指定人数,是否确认删除？',{icon: 7, title:'提示',btn: ['确定','取消']}
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
		var url = basePath + "/fake/turnToDel.do?id="+id;
		layer.open({
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
			url: basePath + "/fake/delete.do",//请求的action路径
			error: function () {//请求失败处理函数
				layer.msg('请求失败！');
			},
			success:function(data){
				console.log(data);
				if(data == null || data == '0'){
					layer.msg('请求失败!');
				}else if(data != null && data == '1'){
					layer.msg('删除成功!');
					parent.doGoPage(1);
					parent.layer.closeAll('iframe');
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