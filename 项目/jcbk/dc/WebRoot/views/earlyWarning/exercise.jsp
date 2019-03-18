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
	<title>预警演练</title>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
</head>

<body>
	<div class="content">
		<div class="content_wrap">
		<div style="width:1200px;height:400px;">
			<div style="width:600px;height:400px;border:1px solid gray;float:left" id="container"></div>
			<div style="width:550px;height:350px;float:left">
				<div class="slider_body">
		            <div class="slider_selected_left">
		                <span>预计车辆速度：</span>
		            </div>
		            <div class="slider_selected_right">
		                <div class="img_wrap">
		                   <div class="select_wrap select_input_wrap">
		                        <input id="speed" name="speed" type="text" class="slider_input" maxlength="3" placeholder="请输入预计速度，默认为${defaultSpeed}"
		                        onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9,]+/,'');}).call(this)" onblur="this.v();"/>
		                        <a id="speed" class="empty"></a>
		                   </div>
		                </div>  
		            </div>
		        </div>
		        <button class="submit_b" onclick="refresh()">重新计算</button>
				<div class="pg_result" style="height:290px;overflow :auto">
					<table id="generatedTable">
						<thead>
							<tr>
								<td width="150" align="center">卡点名称</td>
								<td width="100" align="center">预计到达时间</td>
								<td style="display:none"></td>
							</tr>
						</thead>
						<tbody id="result">
							<tr style="display:none"></tr>
							<tr id="cloneTr">
								<td></td>
								<td></td>
								<td style="display:none"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div>
	        <div class="clear_both">
<!-- 				<button class="submit_b" onclick="toSave()">指令下发</button> -->
<!-- 	        	<button class="submit_b" id="toClose">关闭</button> -->
	        </div>
		</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/MarkerClusterer_min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/GeoUtils_min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
<script type="text/javascript">
	var selectKD = new Array();//选中卡点数组，并扩展Array原型
	var jcd = null;
	var jcdMarker = null;
	var markers = [];
	var rangeX = '${rangeX}';//默认选择范围
	var defaultSpeed = '${defaultSpeed}';//默认速度
	Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) 
			return i;
		}
		return -1;
	};
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	
	$(function(){
		$("#toClose").click(function(){
			window.close();
		});
		initMap();
	});
	
	var map = new BMap.Map("container");      
	map.addControl(new BMap.NavigationControl());
	map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT})); // 右下角，添加比例尺
	map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
	map.enableKeyboard();                         // 启用键盘操作。
	map.setMinZoom(11);
	var point = new BMap.Point(114.063081, 22.547416);    // 创建点坐标 
	map.centerAndZoom(point,14);                     // 初始化地图,设置中心点坐标和地图级别。
	map.addEventListener("tilesloaded", addMymarkers);
	map.addEventListener("zoomend", addMymarkers);
	map.addEventListener("moveend", addMymarkers);
	function initMap(){
		//监测点图标
		var jcdIcon = new BMap.Icon("<%=basePath%>images/l1.png", new BMap.Size(20,30));
		var jcdIcon2 = new BMap.Icon("<%=basePath%>images/l2.png", new BMap.Size(30,30));
		var jcdIcon3 = new BMap.Icon("<%=basePath%>images/d1.png", new BMap.Size(20,30));
		var jcdIcon4 = new BMap.Icon("<%=basePath%>images/d2.png", new BMap.Size(30,30));
		jcdIcon.setAnchor(new BMap.Size(10,30));
		jcdIcon2.setAnchor(new BMap.Size(10,30));
		jcdIcon3.setAnchor(new BMap.Size(10,30));
		jcdIcon4.setAnchor(new BMap.Size(10,30));
		
		var jcdList = jQuery.parseJSON('${jcdList}');//监测点
		var zakd= jQuery.parseJSON('${zakd}');//卡点
		
		for(var i =0;i<jcdList.length;i++){
			var point1 = new BMap.Point(jcdList[i].jd, jcdList[i].wd);
			var marker = new BMap.Marker(point1);
            (function(i){
	            marker.addEventListener("click", function(){
					if(this.getIcon() == jcdIcon3){
						this.setIcon(jcdIcon4);
						if(jcdMarker != null){
							jcdMarker.setIcon(jcdIcon3);
						}
						jcd = jcdList[i];
						jcdMarker = this;
					}
					refresh();
	            });
	        })(i);
			marker.setIcon(jcdIcon3);
			marker.setTitle(jcdList[i].jcdmc);
			marker.addEventListener("mouseover", function(){
				this.setTop(true);
			});
			marker.addEventListener("mouseout", function(){
				this.setTop(false);
			});
// 			map.addOverlay(marker);
			markers.push(marker);
		}
		
		//显示卡点
		for(var i=0;i<zakd.length;i++){
			var point1 = new BMap.Point(zakd[i].X, zakd[i].Y);
			var marker = new BMap.Marker(point1);
            (function(i){
	            marker.addEventListener("click", function(){
	            	if(jcd != null){
						if(this.getIcon() == jcdIcon){
							this.setIcon(jcdIcon2);
							selectKD.push(this.getTitle()+";"+zakd[i].X+";"+zakd[i].Y);
// 							var label = new BMap.Label("预计时间："+calTime(jcd.jd, jcd.wd, zakd[i].X, zakd[i].Y));
// 							label.setOffset(new BMap.Size(10,30));
// 							this.setLabel(label); 
						}else{
							this.setIcon(jcdIcon);
							selectKD.remove(this.getTitle()+";"+zakd[i].X+";"+zakd[i].Y);
// 							map.removeOverlay(this.getLabel()); 
						}
						refresh();
					}else{
						layer.msg("请先选择一个监测点");
					}
	            });
	        })(i);
			marker.setIcon(jcdIcon);
			marker.setTitle(zakd[i].ID+";"+zakd[i].POSTION);
			marker.addEventListener("mouseover", function(){
				this.setTop(true);
			});
			marker.addEventListener("mouseout", function(){
				this.setTop(false);
			});
			map.addOverlay(marker);
		}
	}
	function refresh(){
		//删除旧数据 
		$("#result tr:gt(1)").remove(); 
		//1,获取上面id为cloneTr的tr元素  
		$("#cloneTr").show();
		var tr = $("#cloneTr");  
		$.each(selectKD, function(index,item){   
             //克隆tr，每次遍历都可以产生新的tr   
               var clonedTr = tr.clone();  
               var _index = index;  
               title = item.split(";",4);
               var id = title[0];
               var name = title[1];
               var x = title[2];
               var y = title[3];
               //循环遍历cloneTr的每一个td元素，并赋值  
               clonedTr.children("td").each(function(inner_index){  
                      //根据索引为每一个td赋值  
                           switch(inner_index){  
                                  case(0):   
                                     $(this).html(name);  
                                     break;  
                                  case(1):  
                                     $(this).html(calTime(jcd.jd,jcd.wd,x,y,jcd.id,id));  
                                     break;  
                                  case(2):  
                                     $(this).html(id);  
                                     break; 
                           }//end switch                          
            });//end children.each  
          
           //把克隆好的tr追加原来的tr后面  
           clonedTr.appendTo($("#result"));  
        });//end $each  
        $("#cloneTr").hide();//隐藏id=clone的tr，因为该tr中的td没有数据，不隐藏起来会在生成的table第一行显示一个空行  
        //更改地图里的时间
        
	}
	//计算时间
	function calTime(lat_a, lng_a, lat_b, lng_b,start,end){
		var len = drawTrail(start,end);
		if(len == 0){
			len = distance(lat_a, lng_a, lat_b, lng_b);
		}
		var speed = $("#speed").val();
		if(speed == "" || speed == 0){
			speed = defaultSpeed;
		}
		var time = len/1000/speed;
		var nowtime = Date.parse(new Date());
		return formatDateMinute(nowtime + time*3600*1000);
	}
	//计算距离
	function distance(lat_a, lng_a, lat_b, lng_b){
// 		var len = distance1(lat_a, lng_a, lat_b, lng_a)+distance1(lat_b, lng_b, lat_b, lng_a);//计算距离为2点的正方形规则边长
		var len = parseInt(baiduDistance(lat_a, lng_a, lat_b, lng_a))+parseInt(baiduDistance(lat_b, lng_b, lat_b, lng_a));//计算距离为2点的正方形规则边长
		return len;
	}
	//计算距离1
	function distance1(lat_a, lng_a, lat_b, lng_b){
		pk = 180 / Math.PI  ;
		a1 = lat_a / pk  ;
		a2 = lng_a / pk  ;
		b1 = lat_b / pk  ;
		b2 = lng_b / pk  ;
		t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2)  ;
		t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2)  ;
		t3 = Math.sin(a1) * Math.sin(b1)  ;
		tt = Math.acos(t1 + t2 + t3)  ;
		return Math.round(6366000 * tt);
	}
	
	function baiduDistance(lat_a, lng_a, lat_b, lng_b){
		var pointA = new BMap.Point(lat_a,lng_a);
		var pointB = new BMap.Point(lat_b,lng_b);
		return (map.getDistance(pointA,pointB)).toFixed(0);
	}
	
	//日期转换
	function formatDateMinute(date){
		var now = new Date(date);
		var year=now.getFullYear();     
	    var month=(now.getMonth()+1 < 10 ? '0'+(now.getMonth()+1) : now.getMonth()+1);     
	    var date=(now.getDate() < 10 ? '0'+ now.getDate() : now.getDate());     
	    var hour=(now.getHours() < 10 ? '0'+ now.getHours() : now.getHours());     
	    var minute=(now.getMinutes() < 10 ? '0'+ now.getMinutes() : now.getMinutes());     
	    var second=(now.getSeconds() < 10 ? '0'+ now.getSeconds() : now.getSeconds());     
	    return  year+"-"+month+"-"+date+"   "+hour+":"+minute;
	}
	//保存..按钮
	function toSave() {
		if(validate()){
			$.blockUI({
		        message: '<h2><img src="<%=basePath%>common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求,请稍候……</h2>'
		    });
			var lastKD = new Array();
			$("#result tr:gt(1)").each(function(){
				var kd = "";
				$(this).children("td").each(function(inner_index){
					switch(inner_index){  
                           case(0):   
                              kd += $(this).text(); 
                              break;  
                           case(1):  
                              kd += ";"+$(this).text();
                              break;  
                           case(2):  
                              kd += ";"+$(this).text();
                              break; 
                    }
				});
				lastKD.push(kd);
			});
			$.ajax({
			    url: '${pageContext.request.contextPath}/earlyWarning/sendInstruction.do',
			    dataType: "json",  
			    data:{bjxh:ewarning.bjxh,yanr:$("#yanr").val(),lastKD:lastKD},
			    type: "POST",   //请求方式
				traditional: true,
			    success: function(data) {
			    	$.unblockUI();
			    	if(data == "success"){
			    		parent.layer.msg("指令下发成功");
			    	}else if(data == "repeat"){
			    		alert("该预警已被其他人员下发指令");
			    	}
			    	parent.refresh();
			    	closeLayer();
			    }
			});
		}
	}
	function validate(){
		if($("#yanr").val().trim() == ""){
			alert("请输入预案内容");
			return false;
		}else if(selectKD.length < 1){
			alert("请选择卡点");
			return false;
		}else{
			return true;
		}
	}
	
	function addMymarkers(){//显示可视区域内的标注
		var gb = map.getBounds();
	    for(var m in markers){
	        var res = BMapLib.GeoUtils.isPointInRect(window.markers[m].point, gb);
	        if(res == true) {
	        	map.addOverlay(window.markers[m]);
	            markers.splice(m,1);
			}
	    }
	}
	//获取路径长度
	function drawTrail(start,end){
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		var trailLength = 0;
		$.ajax({
			async:false,
			type: 'POST',
			data:{start:start,end:end},
			dataType : "json",
			url: basePath + "/mapTrail/selectMapTrailKD.do",//请求的action路径
			error: function () {//请求失败处理函数
// 				layer.msg('路径查询失败！');
			},
			success:function(data){ //请求成功后处理函数。
				if(data != null && data != "null"){
				    var mapTrail = data;
				    trailLength = mapTrail.len;
// 					var ll = mapTrail.coordinate.split(";");//经纬度array
// 					var pts = new Array();//路径point集合
// 					for(var i = 0;i < ll.length;i++){
// 						var arr = ll[i].split(",");
// 						var point3 = new BMap.Point(arr[0],arr[1]);
// 						pts.push(point3);
// 					}
// 					var polyline = new BMap.Polyline(pts); 
// 					polyline.setStrokeColor("red");//折线的颜色      
//             		map.addOverlay(polyline);//路径
				}else{
// 					layer.msg("没有两点之间的路径");
				}
			}
		});
		return trailLength;
	} 
</script>
</html>