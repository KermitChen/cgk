<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>轨迹</title>
<style type="text/css">
	html,body{
		margin: 0;
		padding: 0;
		height:100%; 
	}
	.map_div{
		height: 100%;
		width: 100%;
	}
</style>
</head>
<body>
	<div class="map_div" id="container"></div>
</body>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/TextIconOverlay_min.js"></script>
<script type="text/javascript">
	var hasFirst = false;//起始点
	var markerNum = 1;//标记物序号
	var styled = function(url,size){
	 	this.url = '<%=basePath%>images/pass3.png';
	 	this.size = new BMap.Size(30, 30);
	 	this.textSize = 14;
		this.textColor = "white";
	};
	$(function(){
		var resList = jQuery.parseJSON('${sbList }');
		hasFirst = false;
		markerNum = 1;
		if(resList.length == 1){
			singlePoint(resList[0].jcdid,resList[0].sbsj,true);
		}else if(resList.length > 1){
			for(var i = 0;i < resList.length-1;i++){
				drawTrail(resList[i].jcdid,resList[i+1].jcdid,1);
				singlePoint(resList[i].jcdid,resList[i].sbsj,false);
    			if(i == resList.length-2){
	    			singlePoint(resList[i+1].jcdid,resList[i+1].sbsj,true);
    			}
			}
		}
	});
	
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
// 								label1.setPosition(point1);
// 								var label1 = new BMap.Label(markerNum);
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