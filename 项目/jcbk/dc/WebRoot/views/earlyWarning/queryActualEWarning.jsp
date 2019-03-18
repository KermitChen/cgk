<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
	<head>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
		<base href="<%=basePath%>">
		<title>实时预警</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>

	<body>
		<jsp:include page="/common/Head.jsp" />
		<div id="divTitle">
			<span id="spanTitle">当前位置：预警管理&gt;&gt;实时预警</span>
		</div>
		<div class="content">
			<div class="content_wrap">
				<div class="slider_body">
	                <div class="slider_selected_left">号牌号码：</div>
	                <div class="slider_selected_right" style="">
	                    <div class="img_wrap">
	                        <div class="select_wrap input_wrap_select">
	                            <input id="cphid" name="hphm" type="text" class="slider_input" maxlength="8"/>
	                            <a class="empty" href="javascript:doCplrUI()"></a>
	                        </div>
	                    </div>  
	                </div>
	        	</div>
	        	<div class="button_wrap1">
					<button class="submit_b" onclick="refresh()">查询</button>
					<button id="sMap" class="submit_b" onclick="showMap()">显示地图</button>
					<button id="hMap" class="submit_b" onclick="hideMap()" style="display:none">隐藏地图</button>
				</div>
				<div style="clear:both;width:100%;height:280px;border:1px solid gray;" id="container"></div>
				<div class="pg_result">
					<table id="generatedTable">
						<thead>
							<tr>
								<td width="80" align="center">号牌号码</td>
								<td width="80" align="center">号牌种类</td>
								<td width="100" align="center">报警大类</td>
<!-- 								<td width="100" align="center">布控类别</td> -->
								<td width="100" align="center">报警小类</td>
								<td width="150" align="center">监测点</td>
								<td width="120" align="center">通过时间</td>
								<td width="120" align="center">报警时间</td>
								<td width="100" align="center">车辆速度</td>
								<td width="100" align="center">操作</td>
							</tr>
						</thead>
						<tbody id="result">
							<tr style="display:none"></tr>
							<tr id="cloneTr">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="jplayer"></div>
		<jsp:include page="/common/Foot.jsp" />
	</body>
	<script type="text/javascript" src="<%=basePath%>dwr/engine.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/interface/pushMessageCompont.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/MarkerClusterer_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/TextIconOverlay_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<!-- <script type="text/javascript" src="<%=basePath%>common/js/jquery.jplayer.js"></script> -->
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.jplayer.min.js"></script>
	<script type="text/javascript">
		var map = new BMap.Map("container");      
		var point = new BMap.Point(114.063081, 22.547416);// 创建点坐标 
		map.centerAndZoom(point, 11);                     // 初始化地图,设置中心点坐标和地图级别。
		map.addControl(new BMap.NavigationControl());
		map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT})); // 右下角，添加比例尺
		map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
		map.enableKeyboard();                         // 启用键盘操作。
		map.setMinZoom(11);
		
		var showmap = false;
		//监测点图标
		var jcdIcon = new BMap.Icon("<%=basePath%>images/k1.png", new BMap.Size(20,30));
		jcdIcon.setAnchor(new BMap.Size(10,30));
		hideMap();
		
		var points = [];//坐标点集合
		var markerBak = null;
		var bklbList = jQuery.parseJSON('${bklbList}');
		var dicList = jQuery.parseJSON('${dicList}');
		var hpzlList = new Array();
		var bkdlList = new Array();
		var bkdl1List = new Array();
		var bkdl2List = new Array();
		var bkdl3List = new Array();
		for(var i in dicList){
			if(dicList[i].typeCode == "HPZL"){
				hpzlList.push(dicList[i]);
			} else if (dicList[i].typeCode == "BKDL"){
				bkdlList.push(dicList[i]);
			} else if (dicList[i].typeCode == "BKDL1"){
				bkdl1List.push(dicList[i]);
			} else if (dicList[i].typeCode == "BKDL2"){
				bkdl2List.push(dicList[i]);
			} else if (dicList[i].typeCode == "BKDL3"){
				bkdl3List.push(dicList[i]);
			}
		}
		
		$(function(){
			onPageLoad();
			dwr.engine.setActiveReverseAjax(true);
			dwr.engine.setNotifyServerOnPageUnload(true);
			refresh();
			
			//警报声
			$("#jplayer").jPlayer({
		      swfPath: "./common/js/",
		      ready: function () {
		        $(this).jPlayer("setMedia", {
					mp3: "./common/warning.mp3",
		        });
		      },
		      supplied: "mp3"
		    }); 
		});
		
		function onPageLoad() {
			pushMessageCompont.onPageLoad("${userId }");
		}
		
		function showMessage(sendMessages, clickEvent) {
			layer.msg("来预警信息啦！");
			$("#jplayer").jPlayer('play');
			refresh();
		}
		
		//获取最新预警
		function refresh(){
	// 		block();
			map.clearOverlays();
			points = [];
			layer.load();
			$.ajax({
			    url: '${pageContext.request.contextPath}/earlyWarning/loadAEW.do',
			    dataType: "json",  
			    data:{hphm:$("#cphid").val()},
			    type: "POST",   //请求方式
			    success: function(data) {
	// 		    	$.unblockUI();
			    	//删除旧数据 
			    	$("#result tr:gt(1)").remove(); 
			    	//1,获取上面id为cloneTr的tr元素  
			    	$("#cloneTr").show();
			    	var tr = $("#cloneTr");  
			    	if(data == ""){
			    		var clonedTr = tr.clone(); 
			    		var c1 = clonedTr.children("td").eq(0);
			    		c1.attr("colSpan", 9).siblings().remove();
			    		c1.html("暂无符合条件的预警信息");
			    		clonedTr.insertAfter(tr); 
			    		$("#cloneTr").hide();
			    	}else {
			    		$.each(data, function(index,item){   
	                    	//克隆tr，每次遍历都可以产生新的tr                              
	                  		var clonedTr = tr.clone();  
	                        //循环遍历cloneTr的每一个td元素，并赋值  
	                        clonedTr.children("td").each(function(inner_index){  
	                        //根据索引为每一个td赋值  
	                        	switch(inner_index){  
	                            	case(0):   
	                            		$(this).html(item.hphm);  
	                            		break;  
	                                case(1):  
	                                    for(var i=0;i < hpzlList.length;i++){
	                                    	if(item.hpzl == hpzlList[i].typeSerialNo){
	                                        	$(this).html(hpzlList[i].typeDesc);
	                                        }
	                                    }
	                                    break;  
	                                case(2):  
	                                	for(var i=0;i < bkdlList.length;i++){
	                                    	if(item.bjdl == bkdlList[i].typeSerialNo){
	                                         	$(this).html(bkdlList[i].typeDesc);
	                                    	}
	                                    }
	                                    break;  
	                                case(3):  
	                                	//for(var i=0;i< bklbList.length;i++){
										//	if(bklbList[i].ID == item.bklb){
										//		$(this).html(bklbList[i].NAME);
										//	}
										//}
										if(item.bjdl == "1"){
											for(var i=0;i < bkdl1List.length;i++){
		                                    	if(item.bjlx == bkdl1List[i].typeSerialNo){
		                                         	$(this).html(bkdl1List[i].typeDesc);
		                                    	}
		                                    }
										} else if(item.bjdl == "2"){
											for(var i=0;i < bkdl2List.length;i++){
		                                    	if(item.bjlx == bkdl2List[i].typeSerialNo){
		                                         	$(this).html(bkdl2List[i].typeDesc);
		                                    	}
		                                    }
										} else if(item.bjdl == "3"){
											for(var i=0;i < bkdl3List.length;i++){
		                                    	if(item.bjlx == bkdl3List[i].typeSerialNo){
		                                         	$(this).html(bkdl3List[i].typeDesc);
		                                    	}
		                                    }
										}
	                                    break;  
	                                case(4):  
	                                	$(this).html(item.jcdmc);  
	                                    break;  
	                                case(5):  
	                                	$(this).html(formatDate(item.tgsj));  
	                                    break;
	                                case(6):  
	                                	$(this).html(formatDate(item.bjsj));  
	                                    break; 
	                                case(7):
	                                    $(this).html(item.sd);  
	                                    break; 
	                                case(8):
	                                	if(item.qrzt == "0"){
		                    				if(item.bjdl != "1"){//不是涉案类
		                    					$(this).html("<a onclick='eWarningConfirm("+item.qsid+", 0)' style='font-size: 15px;'>预警签收</a>");
		                    				} else {//涉案类
		                    					if('${isCity }' == "false" && item.bklb == "02"){//分局
		                    						$(this).html("<a onclick='eWarningConfirm("+item.qsid+", 1)' style='font-size: 15px;'>预警签收</a>");
		                    					} else if('${isCity }' == "true"){//市局
	                                            	$(this).html("<a onclick='eWarningConfirm("+item.qsid+", 1)' style='font-size: 15px;'>预警签收</a>");
	                                            } else{
	                                              $(this).html("<a onclick='eWarningConfirm("+item.qsid+", 0)' style='font-size: 15px;'>预警签收</a>");
	                                            }
		                    				}
		                           		} else{
		                             		//已签收，这一条记录不显示
		                           			clonedTr.hide();
		                           		}
	                 					break;
	      							}//end switch                          
	                            });//end children.each  
	                            
	                            //把克隆好的tr追加原来的tr后面  
	                            clonedTr.insertAfter(tr);  
	                            if(showmap && item.jd != null && item.jd != 0){
		                            //添加地图标记
		                            for(var i = 0; i < points.length;i++){//查找坐标重合的点
							    		if(points[i].lng == item.jd && points[i].lat == item.wd){
											item.jd = item.jd+rd();
											item.wd = item.wd+rd();
							    			break;
							    		}
							    	}
		                            var point1 = new BMap.Point(item.jd, item.wd);
		                            points.push(point1);
		                            var marker = new BMap.Marker(point1);
	// 								marker.setLabel(label); 
	// 				                var infoWin = new BMap.InfoWindow("监测点："+item.jcdmc+"<br/>号牌："+item.hphm+"<br/>通过时间："+formatDate(item.tgsj));
	// 				                infoWin.setWidth(220);
	// 				                infoWin.setHeight(60);
	// 				                infoWin.disableCloseOnClick();
									marker.addEventListener("click", function(){
	// 					                this.openInfoWindow(infoWin);
			                            if(this.getLabel() == null || this.getLabel() == undefined){
				                            var label = new BMap.Label("车牌号牌：" + item.hphm + "<br/>监测点：" + item.jcdmc + "<br/>行驶车道：" + item.cdid + "<br/>通过时间：" + formatDate(item.tgsj));
											label.setOffset(new BMap.Size(10,25));
											this.setLabel(label);
										}else{
											map.removeOverlay(this.getLabel()); 
										}
						            });
				            		marker.addEventListener("mouseover", function(){
										this.setTop(true);
									});
									marker.addEventListener("mouseout", function(){
										this.setTop(false);
									});
				            		map.addOverlay(marker);//点
				            		if(index == 0){
				            			markerBak = marker;
				            		}
	                        	}
	                        });//end $each  
	                        if(showmap && points.length > 0){
	                       		if(points.length > 1){
					              	map.setViewport(points);          //调整到最佳视野  
					            } else if(points.length == 1){
					            	map.centerAndZoom(points[0], 12);
					            }
	                        }
	                        setTimeout(function(){  
		                       	if(markerBak != null){
		                       		markerBak.setAnimation(BMAP_ANIMATION_BOUNCE);
		                       		setTimeout(function(){  
			                       		markerBak.setAnimation(null);
				                  	}, 2000); 
		                  		}
		                  	}, 500); 
	                        $("#cloneTr").hide();//隐藏id=clone的tr，因为该tr中的td没有数据，不隐藏起来会在生成的table第一行显示一个空行  
			    	}
	                layer.closeAll('loading');
			    }
			});
		}
		//签收确认窗口
		function eWarningConfirm(qsid, isSign){
			var url = "${pageContext.request.contextPath}/earlyWarning/loadEWarningConfirm.do?qsid="+qsid+"&isSign=" + isSign;
		    var index = layer.open({
		           type: 2,
		           title: '预警签收确认窗口',
		           //shadeClose: false,
		           //shade: 0.8,
		           area: ['1180px', '500px'],
		           content: url,//iframe的url
		           maxmin: true
		       });
		    layer.full(index);
		}
		
		function sendInstruction(qsid){
			var url = "${pageContext.request.contextPath}/earlyWarning/loadInstruction.do?qsid=" + qsid;
		    var index = layer.open({
		           type: 2,
		           title: '指令下发窗口',
	// 	           shadeClose: false,
	// 	           shade: 0.8,
		           area: ['1000px', '500px'],
		           content: url,//iframe的url
		           maxmin: true
		       }); 	
		    layer.full(index);
		}
		
		function block() {
			$.blockUI({
			   message: '<h2><img src="<%=basePath%>common/images/ajax/loading.gif" align="absmiddle"/>正在加载数据……</h2>'
			});
		}
		
		//重写错误方法
		dwr.engine._errorHandler = function(message, ex) {
			dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);
			alert("访问不到服务，页面将关闭！");
			window.close();
		};
		
		//生成一个随机数
	    function rd(){
	       	//var c = m-n+1;
		    var c = 300-50+1;
		    var result = Math.floor(Math.random() * c + 50)*0.000001;
		    if(Math.random() > 0.5){
		    	return result;
		    } else{
		    	return -result;
		    }
		}
		//显示地图
		function showMap(){
			showmap = true;
			$("#container").show();
			$("#sMap").hide();
			$("#hMap").show();
			refresh();
		}
		function hideMap(){
			showmap = false;
			$("#container").hide();
			$("#sMap").show();
			$("#hMap").hide();
		}
	</script>
</html>