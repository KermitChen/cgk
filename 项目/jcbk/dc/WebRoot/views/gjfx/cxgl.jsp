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
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
		<base href="<%=basePath%>">
		<title>出行规律分析</title>
		<style type="text/css">
			#container{
				height: 480px;
				width: 100%;
			}
			#pages{
				height:30px;
				width:50%;
				margin-top:20px;
				margin-left:300px;
			}
			.error_div{
				margin: 0;
			    position: relative;
			    display: block;
			    float: left;
			    width: 100%;
			    height: 20px; 
			    text-align: center;
			}
		</style>
		<style type="text/css">
			.layer_span:hover {
				color:#FF0000 !important;
			}
		</style>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp" />
		<div id="divTitle">
			<span id="spanTitle">当前位置：高级检索分析&gt;&gt;出行规律分析</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
	    	 	<form id="form1">
	    	 		<div class="slider_body">
		                <div class="slider_selected_left">
		                    <span id="cphid_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;">*</span>车牌号码：</span>
		                </div>
		                <div class="slider_selected_right">
		                    <div class="img_wrap">
		                        <div class="select_wrap input_wrap_select">
		                            <input id="cphid" name="hphm" type="text" class="slider_input" onchange="checkCph();">
		                            <a class="empty" href="javascript:doCplrUI()"></a>
		                        </div>
		                   </div>
		                </div>   
		            </div>
		       		<div class="slider_body" >
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;">*</span>分析时间：</span>
					  	</div>
						<div class="slider_selected_right">
							<div class="demolist" style="">
						    	<input class="inline laydate-icon" id="kssj" name="kssj"/>
						  	</div>
						</div>
					</div>
					<div class="slider_body" >
					  	<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至</span>
					  	</div>
						<div class="slider_selected_right">
						  	<div class="demolist" style="">
						    	<input class="inline laydate-icon" id="jssj" name="jssj"/>
						  	</div>
						</div>
					</div>
		        </form>
		        	<div class="button_wrap clear_both">
		        		<input id="query_button" name="query_button" type="button" class="button_blue" value="查询" onclick="doGoPage(1);">
			    		<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
			    		&nbsp;&nbsp;&nbsp;&nbsp;<span id="showTime"></span>
			    	</div>
			        <div class="pg_result">
			            <div id="container"></div>
			            <div id="pages"></div>
			        </div>
	        </div>
	    </div>
	    <jsp:include page="/common/Foot.jsp" />
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/laypage/laypage.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
	<script src="<%=basePath%>common/js/time/moment.js"></script>
	<script type="text/javascript">
		var hasFirst = false;//起始点
		var markerNum = 1;//标记物序号
		var startTime = laydate({//开始时间
		    elem: '#kssj', 
		    event: 'click', 
		    max:laydate.now(),
		    istime: true, 
		    format: 'YYYY-MM-DD',
		    choose:function(datas){
		    	endTime.min = datas;
		    	$("#pages").empty();
		    }
		});
		var endTime = laydate({//结束时间
		    elem: '#jssj', 
		    event: 'click', 
		    max:laydate.now(),
		    istime: true, 
		    format: 'YYYY-MM-DD',
		    choose:function(datas){
		    	startTime.max = datas;
		    	$("#pages").empty();
		    }
		});
		
		//显示提示
		function showTip(message, id){
			layer.open({
			     type: 4,
			     shade: 0,
			     time:30000,
			     closeBtn: 0,
			     tips: [3, '#758a99'],
			     content: [message, id]
			});
		}
		
		//分页查询
		function doGoPage(pageNo) {
			if(!commonCheck(true,true)){//通用验证
				return;
			}
			var hphm = $.trim($("#cphid").val());
			var kssj = $.trim($("#kssj").val());//起始时间
			var jssj = $.trim($("#jssj").val());//截止时间
			if(moment(jssj).diff(moment(kssj)) < 3*24*60*60*1000){
				layer.msg("错误提示：时间间隔不可小于三天！");
				return;
			}
			layer.load();
			$.ajax({
				url:'<%=basePath%>gjfx/cxgl.do?' + new Date().getTime(),
				method:"post",
				data:{hphm:hphm,kssj:kssj,jssj:jssj,pageNo:pageNo},
				dataType:'json',
				success:function(data){
					$("#showTime").text("轨迹展示时间："+data.begin);
					laypage({
			            cont: 'pages', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
			            pages: data.totalPageCount, //通过后台拿到的总页数
			            curr:  data.pageNo, //当前页
			            skip:false,
			            jump: function(obj, first){ //触发分页后的回调
			                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
			                	doGoPage(obj.curr);
			                }
			            }
			        });
					
					map.clearOverlays();
					if(data.hasOwnProperty('errorMessage')){
						layer.msg(data.errorMessage);
						//layer.msg(data.errorMessage);
						layer.closeAll('loading');
					}else{
						console.info(data);
						points = new Array();
						hasFirst = false;
						markerNum = 1;
						var arr = data.items;
						if(arr.length == 1){//只有一个点
							singlePoint(arr[0].jcdid,arr[0].sbsj,true);
						}else if(arr.length > 1){
							if(arr.length == 1){
								singlePoint(arr[0].jcdid,arr[0].sbsj,true);
							}else if(arr.length > 1){
								for(var i = 0;i < arr.length-1;i++){
									drawTrail(arr[i].jcdid,arr[i+1].jcdid,1);
									singlePoint(arr[i].jcdid,arr[i].sbsj,false);
					    			if(i == arr.length-2){
						    			singlePoint(arr[i+1].jcdid,arr[i+1].sbsj,true);
					    			}
								}
							}
						}else{
							layer.msg("没有过车记录！");
							layer.closeAll('loading');
						}
					}
				},
				error: function () {//请求失败处理函数
					layer.closeAll("loading");
					layer.msg('查询失败！');
				}
			});
		}
		//检查车牌号输入是否正确
		function checkCph(){
			$("#pages").empty();
			var reg = /^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$/;
			if(!reg.test($.trim($("#cphid").val()))){
				layer.msg("错误提示：请输入正确号牌号码！");
				$("#cphid").val("");
			}
		}
		$(function(){
			//提示信息
			$("#cphid_span").mouseover(function(){
				showTip("仅可以输入一个车牌号码！", "#cphid_span");
			});
			$("#cphid_span").mouseleave(function(){
				layer.closeAll('tips');
			});
				
			initTime();
		});
		//初始化时间
		function initTime(){
			var end = moment().format('YYYY-MM-DD');
			var start = moment().subtract(30, 'days').format('YYYY-MM-DD');
			$("#kssj").val(start);
			$("#jssj").val(end);
		}
		//重置分析条件
		function doReset(){
			$("#cphid").val("");
			initTime();
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
						layer.msg("监测点没有坐标，无法显示！");
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
// 					    	if(!hasFirst){//没有起始点
// 						    	marker1.setIcon(beginIcon);
// 						    	hasFirst = true;
// 					    	}else if(isLast){
// 						    	marker1.setIcon(endIcon);
// 					    	}else{
// 						    	marker1.setIcon(passIcon);
// 					    	}
					    	marker1.addEventListener("click", function(){
					    		var infoWin = new BMap.InfoWindow("监测点："+jcd.jcdmc+"<br/>通过时间："+time);
	                			this.openInfoWindow(infoWin);
// 	                            if(this.getLabel() == null || this.getLabel() == undefined){
// 									var label1 = new BMap.Label("监测点："+jcd.jcdmc+"<br/>通过时间："+time);
// 									label1.setOffset(new BMap.Size(10,25));
// 									this.setLabel(label1);
// 								}else{
// 									map.removeOverlay(this.getLabel()); 
// 								}
				            });
		            		marker1.addEventListener("mouseover", function(){
								this.setTop(true);
							});
							marker1.addEventListener("mouseout", function(){
								this.setTop(false);
							});
		            		map.addOverlay(marker1);//点
		            	}else{
		            		layer.msg("监测点没有坐标，无法显示！");
		            	}
					}else{
						layer.msg("监测点ID为空！");
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