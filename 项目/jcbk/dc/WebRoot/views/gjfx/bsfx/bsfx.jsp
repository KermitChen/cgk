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
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
	<base href="<%=basePath%>">
<title>伴随分析</title>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<link rel="stylesheet" href="<%=basePath%>common/css/sb/bsfx.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>common/css/barline.css" type="text/css">	
	<div id="divTitle">
		<span id="spanTitle">伴随分析</span>
	</div>
    <div class="content">
    	<div class="content_wrap">
    	 	<form id="form1" name="form1" action="<%=basePath%>gjfx/bsfx.do" method="post">
    	 		<div class="slider_body">
	                <div class="slider_selected_left">
	                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌号码：<span style="color:red;">*</span></span>
	                </div>
	                <div class="slider_selected_right">
	                    <div class="img_wrap">
	                        <div class="select_wrap input_wrap_select">
	                            <input id="cphid" name="hphm" type="text" class="slider_input" value="${hphm }">
	                            <a class="empty" href="javascript:doCplrUI()"></a>
	                        </div>
	                   </div>
	                </div>   
	              </div>
	      
	        <div class="slider_body" >
			  <div class="slider_selected_left">
				<span>分析起始时间：<span style="color:red;">*</span></span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="kssj" name="kssj" value="${kssj }" />
				  </div>
				</div>
			</div>
			<div class="slider_body">
			  <div class="slider_selected_left">
				<span>至</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="jssj" name="jssj" value="${jssj }"/>
				  </div>
				</div>
			</div>
	         <div class="slider_body">
	            <div class="slider_selected_left">
	                <span id="sjld_span" class="layer_span" style="color:#900b09">时间粒度(m)：<span style="color:red;">*</span></span>
	            </div>
	            <div class="slider_selected_right dropdown dropdowns" id="cp_2" onclick="slider(this)">
	            	<input class="input_select xiala" id="sjld_xiala" readonly="readonly" type="text" value="${sjld }"/> 
	                <input type="hidden" id="sjld" name="sjld" value="${sjld }" />
	                <div class="ul"> 
		                    <div class="li" data-value="1" onclick="sliders(this)">1</div>
		                    <div class="li" data-value="2" onclick="sliders(this)">2</div>
		                    <div class="li" data-value="3" onclick="sliders(this)">3</div>
		                    <div class="li" data-value="4" onclick="sliders(this)">4</div>
	                 		<div class="li" data-value="5" onclick="sliders(this)">5</div>
	                </div> 
	            </div>
	        </div>
	        <div class="slider_body"> 
				<div class="slider_selected_left">
	                    <span id="txyz_span" class="layer_span" style="color:#900b09">&nbsp;同行阈值(次)：</span>
	            </div>
                <div class="slider_selected_right" style="z-index:0;">
                    <div class="img_wrap">
                        <div class="select_wrap select_input_wrap">
                            <input id="txyz" class="slider_input" type="text" value="${txyz }" name="txyz" />
                            <a id="txyz" class="empty"></a>
                        </div>
                    </div>  
               </div>
	        </div>	      	        
	        <div class="slider_body">
	                <p><font color="red" size="2">提示：只能输入一个车牌号!分析时长默认为1天!同行阈值不可小于2!</font></p>
	        </div>
	        <div class="button_wrap clear_both">
			    	<input id="query_button" name="query_button" type="button" class="button_blue" value="分析" onclick="doSubmit();">
			    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
			    	<!-- 错误信息提示 -->
					<div>
						<span id="errSpan"  style="color:red;margin-left:auto;margin-right:auto" >${pageResult.errorMessage }</span>
			    	</div>
		    </div>
    	 	
	        <div class="pg_result">
	        	<div class="show_style1">
		        	<div class="data_div">
		        		<table>
							 <thead>
			                    <tr>
			                    	<td>序号</td>
			                        <td>车牌号</td>
			                        <td>同行监测点个数</td>
			                        <td>操作</td>
			                    </tr>
			                </thead>
			                <tbody>
			                    <c:forEach items="${pageResult.items }" var="s" varStatus="c">
			                    	<tr>
			                    		<td>${c.index + 1 }</td>
				                        <td>${s.cphm }</td>
				                        <td>${s.jcdgs }</td>
				                        <td><a href="javascript:showBsPath('${s.cphm }','${s.resFlag }');">伴随轨迹</a></td>
			                    	</tr>
			                    </c:forEach>
			                </tbody>
						</table>
		        	</div>
		        	<div class="map_div" id="container"></div>
	        	</div>
	        </div>
			</form>
        </div>
    </div>
    <div id="bar_content" class="bar_content">
		<!-- 进度条 -->
		<div class="barline" id="probar">
			<div id="percent"></div>
			<div id="line" w="100" style="width:0px;"></div>	
			<div id="msg" style=""></div>			
		</div>
	</div>
    <jsp:include page="/common/Foot.jsp" />
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
	<script type="text/javascript">
		var hasFirst = false;//起始点
		var markerNum = 1;//标记物序号
		var calTime = 5;//计算一次所需时间分钟
		var barlineIndex;
		laydate({
		    elem: '#kssj',
		    format: 'YYYY-MM-DD hh:mm:ss', // 分隔符可以任意定义，该例子表示只显示年月
			istime: true,
		    choose: function(datas){ //选择日期完毕的回调
		    	//$("#jssj").val(moment(datas,'YYYY-MM-DD HH:mm:ss').add(1,'days').format('YYYY-MM-DD HH:mm:ss'));
		    }
		});
		laydate({
		    elem: '#jssj',
		    format: 'YYYY-MM-DD hh:mm:ss', // 分隔符可以任意定义，该例子表示只显示年月
			istime: true,
		    choose: function(datas){ //选择日期完毕的回调
		    }
		});
		$(function(){
			layer.closeAll('loading');
			$("#sjld_span").mouseover(function(){
				showSjldTip();
			});
			$("#sjld_span").mouseleave(function(){
				layer.closeAll('tips');
			});
			$("#txyz_span").mouseover(function(){
				showTxyzTip();
			});
			$("#txyz_span").mouseleave(function(){
				layer.closeAll('tips');
			});
		});
		//提交表单
		function doSubmit(){
			if(!commonCheck(true,true)){//通用验证
				return;
			}
			var cphid = $.trim($("#cphid").val());
			var reg = /^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$/;
			if(!reg.test(cphid)){
				$("#errSpan").text("错误提示：请输入正确号牌号码！");
				$("#cphid").val("");
				return;
			}
			var kssj = $.trim($("#kssj").val());//起始时间
			var jssj = $.trim($("#jssj").val());
			if(moment(jssj).diff(moment(kssj)) < 0){
				$("#errSpan").text("错误提示：时间选择错误,起始时间不可大于结束时间！");
				return;
			}
			if(moment(jssj).diff(moment(kssj)) > 86400000){
				$("#errSpan").text("错误提示：时间选择错误,分析时长不可超过1天！");
				return;
			}
			var txyz = $.trim($("#txyz").val());//同行阈值
			var reg2 = /^[0-9]*[1-9][0-9]*$/;
			if(!reg2.test(txyz) || parseInt(txyz) < 2){
				$("#errSpan").text("错误提示：请输入正确同行阈值！不可小于2");
				$("#txyz").val("");
				return;
			}
			$("#errSpan").text("");
			$.ajax({
				async:false,
				type: 'POST',
				data: {key:'1'},
				dataType : "json",
				url: '<%=basePath%>/gjfx/getCalCount.do',//请求的action路径
				error: function () {//请求失败处理函数
					layer.confirm('获取计算任务数量失败,是否继续计算？',{icon: 7, title:'提示',btn: ['继续','取消']}
					  , function(index){
						layer.close(index);
						showBarLine(1);
						$("#resFlag").val("");
						$("#pageNo").val("1");
						$("#form1").submit();
					}, function(index){
						layer.close(index);
					});
				},
				success:function(data){ //请求成功后处理函数。
					layer.confirm('当前有'+data+'个计算任务,是否继续计算？',{icon: 7, title:'提示',btn: ['继续','取消']}
						  , function(index){
							layer.close(index);
							showBarLine(parseInt(data)+1);
							$("#resFlag").val("");
							$("#pageNo").val("1");
							$("#form1").submit();
						}, function(index){
							layer.close(index);
						});
				}
			});
		}
		//重置..按钮
		function doReset(){
			$("#errSpan").text("");
			$("#cphid").val("");
			$("#sjld_xiala").val("1");
			$("#sjld").val("1");
			$("#txyz").val(3);
			$("#kssj").val(moment().subtract(1,"days").format("YYYY-MM-DD HH:mm:ss"));
			$("#jssj").val(moment().format("YYYY-MM-DD HH:mm:ss"));
		}
		//显示伴随车辆轨迹
		function showBsPath(hphm,resFlag){
			var txyz = $.trim($("#txyz").val());
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			map.clearOverlays();
			//请求伴随车辆轨迹
			$.ajax({
				async:true,
				type: 'POST',
				data:{hphm:hphm,resFlag:resFlag,txyz:txyz},
				dataType : "json",
				url: basePath + "/gjfx/bsPath.do",//请求的action路径
				error: function () {//请求失败处理函数
					layer.msg('查询伴随车辆轨迹失败！');
				},
				success:function(data){ //请求成功后处理函数。
					console.log(data);
					points = new Array();
					hasFirst = false;
					markerNum = 1;
			    	for(var i = 0 ;i < data.length;i++){
			    		var jcds = data[i];
			    		for(var j = 0 ;j < jcds.length-1;j++){
			    			drawTrail(jcds[j].jcdid,jcds[j+1].jcdid);
			    			singlePoint(jcds[j].jcdid,jcds[j].tgsj,false);
			    			if(j == jcds.length-2){
				    			singlePoint(jcds[j+1].jcdid,jcds[j+1].tgsj,true);
			    			}
			    		}
			    	}
				}
			});
		}
		//显示时间粒度输入提示
		function showSjldTip(){
			layer.open({
		           type: 4,
		           shade: 0,
		           time:8000,
		           closeBtn: 0,
		           tips: [3, '#758a99'],
		           content: ['伴随车辆跟随已知车辆经过同一监测点的时间差（单位：分钟）','#sjld_span']
		       });
		}
		//显示同行阈值输入提示
		function showTxyzTip(){
			layer.open({
		           type: 4,
		           shade: 0,
		           time:8000,
		           closeBtn: 0,
		           tips: [3, '#758a99'],
		           content: ['伴随车辆伴随已知车辆的最少监测点个数','#txyz_span']
		       });
		}
		
		//初始化地图
		var map = new BMap.Map("container");      
		var point = new BMap.Point(114.063081, 22.547416);    // 创建点坐标 
		map.centerAndZoom(point,11);                     // 初始化地图,设置中心点坐标和地图级别。
		map.addControl(new BMap.NavigationControl());
		map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
		map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
		map.enableKeyboard();                         // 启用键盘操作。
		map.setMinZoom(11);
		var points = new Array();
		var passIcon = new BMap.Icon("<%=basePath%>images/pass1.png", new BMap.Size(30,30));
		var beginIcon = new BMap.Icon("<%=basePath%>images/begin.png", new BMap.Size(30,30));
		var endIcon = new BMap.Icon("<%=basePath%>images/end.png", new BMap.Size(30,30));
		passIcon.setAnchor(new BMap.Size(15,30));
		beginIcon.setAnchor(new BMap.Size(15,30));
		endIcon.setAnchor(new BMap.Size(15,30));
		
		//显示地图路径
		function drawTrail(start,end){
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
						polyline.setStrokeColor("red");//折线的颜色     
	            		map.addOverlay(polyline);//路径
// 						var point1 = new BMap.Point(data[0].jd,data[0].wd);
// 						var point2 = new BMap.Point(data[1].jd,data[1].wd);
// 						var marker1 = new BMap.Marker(point1);
// 				    	var marker2 = new BMap.Marker(point2);
// 						var label1 = new BMap.Label("id:"+data[0].id+"经度："+data[0].jd+"<br/>监测点："+data[0].jcdmc+"通过时间："+formatDate(startTime));
// 						label1.setOffset(new BMap.Size(10,25));
// 						marker1.setLabel(label1); 
// 						var label2 = new BMap.Label("id2:"+data[0].id+"经度："+data[1].jd+"<br/>监测点："+data[1].jcdmc+"通过时间："+formatDate(endTime));
// 						label2.setOffset(new BMap.Size(10,25));
// 						marker2.setLabel(label2); 
// 						map.addOverlay(marker1); 
// 						map.addOverlay(marker2); 
// 						var driving = new BMap.DrivingRoute(map);
// 						driving.search(point1, point2);
// 						driving.setPolicy(BMAP_DRIVING_POLICY_LEAST_DISTANCE);//计算方式，最短时间BMAP_DRIVING_POLICY_LEAST_TIME
// 						driving.setSearchCompleteCallback(function(){  
// 				            var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过驾车实例，获得一系列点的数组  
// 				            var polyline = new BMap.Polyline(pts);       
// 				            map.addOverlay(polyline);  
// 				        });
					}else{
//						layer.msg("没有查询到行驶路径");
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
							for(var i = 0; i < points.length;i++){
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
								var infoWin = new BMap.InfoWindow("监测点："+jcd.jcdmc+"<br/>通过时间："+formatDate(time));
	                			this.openInfoWindow(infoWin);
// 	                            if(this.getLabel() == null || this.getLabel() == undefined){
// 									var label1 = new BMap.Label("监测点："+jcd.jcdmc+"<br/>通过时间："+formatDate(time));
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
				        },1000);
            		}
				}
			});
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