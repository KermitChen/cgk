<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
	<base href="<%=basePath%>">
	<title>预警查询</title>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
</head>

<body>
	<jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">预警查询</span>
	</div>
	<div class="content">
		<div class="content_wrap">
			<div style="width:100%;height:600px;border:1px solid gray" id="container"></div>
		</div>
	</div>
	<jsp:include page="/common/Foot.jsp" />
	
</body>
<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/MarkerClusterer_min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript">
	$(function(){
		initMap();
	});
	
	function initMap(){
		var map = new BMap.Map("container");      
		var point = new BMap.Point(114.063081, 22.547416);    // 创建点坐标 
		map.centerAndZoom(point,11);                     // 初始化地图,设置中心点坐标和地图级别。
		map.addControl(new BMap.NavigationControl());
		map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT})); // 右下角，添加比例尺
		map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
		map.enableKeyboard();                         // 启用键盘操作。
		map.setMinZoom(11);
		//监测点图标
		var jcdIcon = new BMap.Icon("<%=basePath%>images/k1.png", new BMap.Size(20,30));
		jcdIcon.setAnchor(new BMap.Size(10,30));
		//聚合
// 		var markers = [];
		var jcd= jQuery.parseJSON('${jcd}');
		for(var i=0;i<jcd.length;i++){
			var point1 = new BMap.Point(jcd[i].jd, jcd[i].wd);
			var marker = new BMap.Marker(point1);
			
            (function(i){
	            marker.addEventListener("click", function(){
	                var infoWin = new BMap.InfoWindow(jcd[i].jcdmc+"<br/>");
	                this.openInfoWindow(infoWin);
	            });
	        })(i);
			marker.setIcon(jcdIcon);
// 			marker.setTitle(jcd[i].jcdmc+"经度："+jcd[i].jd+"，纬度："+jcd[i].wd);
// 			markers.push(marker);
			map.addOverlay(marker); 
		}
// 		var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});
		//map.setCurrentCity("深圳");          // 设置地图显示的城市
		//路线
		//var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
		//driving.search(new BMap.Point(114.074650, 22.538975), new BMap.Point(114.067392, 22.550490));

    
      
//     var myP1 = new BMap.Point(114.074650,22.538975);    
//     var myP2 = new BMap.Point(114.067392,22.550490);    
//     var myP3 = new BMap.Point(114.062631,22.554162);    
      
//         map.clearOverlays();                        //清除地图上所有的覆盖物  
//         var driving = new BMap.DrivingRoute(map);    //创建驾车实例  
//         driving.search(myP1, myP2);                 //第一个驾车搜索  
//         driving.search(myP2, myP3);                 //第二个驾车搜索  
        
//         driving.setSearchCompleteCallback(function(){  
//             var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过驾车实例，获得一系列点的数组  
      
//             var polyline = new BMap.Polyline(pts);       
//             map.addOverlay(polyline);  
              
//             var m1 = new BMap.Marker(myP1);         //创建3个marker  
//             var m2 = new BMap.Marker(myP2);  
//             var m3 = new BMap.Marker(myP3);  
//             map.addOverlay(m1);  
//             map.addOverlay(m2);  
//             map.addOverlay(m3);  
              
//             var lab1 = new BMap.Label("起点",{position:myP1});        //创建3个label  
//             var lab2 = new BMap.Label("途径点",{position:myP2});  
//             var lab3 = new BMap.Label("终点",{position:myP3});     
//             map.addOverlay(lab1);  
//             map.addOverlay(lab2);  
//             map.addOverlay(lab3);  
              
//             setTimeout(function(){  
//                 map.setViewport([myP1,myP2,myP3]);          //调整到最佳视野  
//             },1000);  
              
//         });  
	}

	
</script>
</html>