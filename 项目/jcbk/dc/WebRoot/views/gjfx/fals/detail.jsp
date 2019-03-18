<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
	<base href="<%=basePath%>">
<title>轨迹</title>
<link rel="stylesheet" href="<%=basePath%>common/css/sb/simplefoucs.css" type="text/css">
<style type="text/css">
	html,body{
		margin: 0;
		padding: 0;
		height:100%; 
		font-family: "Microsoft YaHei";
   		font-size: 14px;
	}
	table {
	    border-collapse: collapse;
	    width: 90%;
	    margin-left:32px;
	}
	tr {
	    border-bottom: 1px solid #d5d5d5;
	}
	td {
	    text-align: center;
	    padding: 2px 10px;
	}
	tbody {
	    font-size: 12px;
	}
	.container{
		height: 100%;
		width: 100%;
	}
	.title_div{
		height:4%;
		width: 100%;
	}
	.content_div{
		height: 30%;
		width: 100%;
		overflow: hidden; 
		margin: 0px auto;
	}
	.show_del_div{
		height: 98%;
		width: 100%;
		overflow:auto;
	}
	.sb_div{
		height: 66%;
		width: 100%;
		overflow: auto; 
		margin: 0px auto;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="title_div" style="cursor: move;" move="ok">
			车牌号码：<input type="text" value="${fla.jcphm }" style="border-style:none" readonly="readonly">录入时间：<input readonly="readonly" style="border-style:none" type="text" value="${fn:substringBefore(fla.lrsj,'.') }">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${flag eq '0'}">
				<input class="button_blue" type="button" value="无效" id="delete_button">
			</c:if>
			<input type="hidden" value="${fla.id }" id="jcpid">
		</div>
		<div class="content_div pic_div bannerbox">
			<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;height: 90%;width: 97%;">
			<legend style=" color:#333; font-weight:bold; font-size: medium; margin-left: 30px;">标记无效人员名单</legend>	
				<div class="show_del_div">
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
			</fieldset>
		</div>
		<div class="sb_div">
			<table>
	            <thead>
	                <tr>
	                    <td>序号</td>
	                    <td>车牌号码</td>
	                    <td>车牌颜色</td>
	                    <td>识别时间</td>
	                    <td>监测点名称</td>
	                    <td>操作</td>
	                </tr>
	            </thead>
	            <tbody>
	            	<c:if test="${fn:length(list) le 0}">
	            		<tr>
	            			<td colspan="6" style="color:red;font-size:16px;">未查询到任何数据！</td>
	            		</tr>
	            	</c:if>
	                <c:forEach items="${list }" var="s" varStatus="c">
	                	<tr>
		                    <td>${c.index + 1 }</td>
		                    <td>${s.hphm }</td>
		                   	<td>${cplxMap[s.cplx] }</td>
		                    <td>${s.sbsj }</td>
		                    <td>${jcdMap[s.jcdid] }</td>
		                    <td><a href="javascript:showSbDetailInParent('${s.tp1 }');">详细信息</a></td>
	                	</tr>
	                </c:forEach>
	            </tbody>
	        </table>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>views/clcx/module/lunbo/simplefoucs.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
<script type="text/javascript">
	var hasFirst = false;//起始点
	var markerNum = 1;//标记物序号
	$(function(){
		$("#delete_button").click(function(){
			var id = $.trim($("#jcpid").val());
			isDelete(id);
		});
		markerNum = 1;
	});
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
	//map
	var map = new BMap.Map("container");      
	var point = new BMap.Point(114.063081, 22.547416);    // 创建点坐标 
	map.centerAndZoom(point,11);                     // 初始化地图,设置中心点坐标和地图级别。
	
	map.addControl(new BMap.NavigationControl());
	map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
	map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
	map.enableKeyboard();                         // 启用键盘操作。
	map.setMinZoom(11);
	var points = new Array();//点集合，用来调整最佳视野
	var passIcon = new BMap.Icon("<%=basePath%>images/pass1.png", new BMap.Size(30,30));
	var beginIcon = new BMap.Icon("<%=basePath%>images/begin.png", new BMap.Size(30,30));
	var endIcon = new BMap.Icon("<%=basePath%>images/end.png", new BMap.Size(30,30));
	passIcon.setAnchor(new BMap.Size(15,30));
	beginIcon.setAnchor(new BMap.Size(15,30));
	endIcon.setAnchor(new BMap.Size(15,30));
	//显示地图路径
	function drawTrail(start,end,num){
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		$.ajax({
			async:true,
			type: 'POST',
			data:{start:start,end:end},
			dataType : "json",
			url: basePath + "/mapTrail/selectMapTrail.do",//请求的action路径
			error: function () {//请求失败处理函数
				layer.msg('地图路径查询失败！');
			},
			success:function(data){ //请求成功后处理函数。
				if(data != null && data != "null"){
				    var mapTrail = data;
					var ll = mapTrail.coordinate.split(";");//经纬度array
					var pts = new Array();//路径point集合
					for(var i = 0;i < ll.length;i++){
						var arr = ll[i].split(",");
						var point3 = new BMap.Point(arr[0],arr[1]);
						pts.push(point3);
					}
					var polyline = new BMap.Polyline(pts); 
					if(num == 1){
						polyline.setStrokeColor("red");//折线的颜色      
            		}else if(num == 2){
						polyline.setStrokeColor("green");//折线的颜色      
            		}
            		map.addOverlay(polyline);//路径
				}else{
					layer.msg("监测点没有坐标，无法显示");
				}
			}
		});
	}  
	
	//显示单个点
	function singlePoint(id,time,isLast){
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		$.ajax({
			async:false,
			type: 'POST',
			data:{id:id},
			dataType : "json",
			url: basePath + "/mapTrail/selectJcd.do",//请求的action路径
			error: function () {//请求失败处理函数
				layer.msg('监测点查询失败！');
			},
			success:function(data){ //请求成功后处理函数。
				if(data != null && data != "null"){
					var jcd = data;
					if(jcd.jd != null && jcd.jd != 0){
				    	for(var i = 0; i < points.length;i++){//查找坐标重合的点
				    		if(points[i].lng == jcd.jd && points[i].lat == jcd.wd){
								jcd.jd = jcd.jd+rd();
								jcd.wd = jcd.wd+rd();
				    			break;
				    		}
				    	}
				    	var point1 = new BMap.Point(jcd.jd,jcd.wd);
				    	points.push(point1);
				    	var marker1 = new BMap.Marker(point1);
				    	var label1 = new BMap.Label(markerNum);
						label1.setOffset(new BMap.Size(4,3));
						label1.setStyle({ backgroundColor : "rgba(0,0,0,0)",fontWeight : "bold",color : "white" , borderColor : "rgba(0,0,0,0)"});
						marker1.setLabel(label1);
				    	markerNum++;
// 				    	if(!hasFirst){//没有起始点
// 					    	marker1.setIcon(beginIcon);
// 					    	hasFirst = true;
// 				    	}else if(isLast){
// 					    	marker1.setIcon(endIcon);
// 				    	}else{
// 					    	marker1.setIcon(passIcon);
// 				    	}
				    	marker1.addEventListener("click", function(){
				    		var infoWin = new BMap.InfoWindow("监测点："+jcd.jcdmc+"<br/>通过时间："+time);
	                		this.openInfoWindow(infoWin);
// 	                        if(this.getLabel() == null || this.getLabel() == undefined){
// 								var label1 = new BMap.Label("监测点："+jcd.jcdmc+"<br/>通过时间："+time);
// 								label1.setOffset(new BMap.Size(10,25));
// 								this.setLabel(label1);
// 							}else{
// 								map.removeOverlay(this.getLabel()); 
// 							}
				         });
	            		marker1.addEventListener("mouseover", function(){
							this.setTop(true);
						});
						marker1.addEventListener("mouseout", function(){
							this.setTop(false);
						});
	            		map.addOverlay(marker1);//点
	            	}else{
	            		layer.msg("监测点没有坐标，无法显示");
	            	}
				}else{
					layer.msg("监测点ID为空");
				}
				if(isLast == true){
                  	setTimeout(function(){  
                      	if(points.length > 1){
			            	map.setViewport(points);          //调整到最佳视野  
				        }else if(points.length == 1){
				           	map.centerAndZoom(points[0],12);
				        } 
	                   	layer.closeAll('loading');
				    },1000);
        		}
			}
		});
	}  
	
	//日期转换
	function formatDateDay(date){
		var now = new Date(date);
		var year=now.getFullYear();     
	    var month=(now.getMonth()+1 < 10 ? '0'+(now.getMonth()+1) : now.getMonth()+1);     
	    var date=(now.getDate() < 10 ? '0'+ now.getDate() : now.getDate());     
	    var hour=(now.getHours() < 10 ? '0'+ now.getHours() : now.getHours());     
	    var minute=(now.getMinutes() < 10 ? '0'+ now.getMinutes() : now.getMinutes());     
	    var second=(now.getSeconds() < 10 ? '0'+ now.getSeconds() : now.getSeconds());     
	    return  year+"-"+month+"-"+date;
	}
	//生成一个随机数
    function rd(){
       	//var c = m-n+1;
	    var c = 300-50+1;
	    var result = Math.floor(Math.random() * c + 50)*0.000001;
	    if(Math.random()>0.5){
	    	return result;
	    }else{
	    	return -result;
	    }
	}
</script>
</html>