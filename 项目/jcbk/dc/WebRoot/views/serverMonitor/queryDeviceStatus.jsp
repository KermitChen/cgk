<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
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
		<title>设备状态监控</title>
		<script src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
	</head>
	<body>
		<jsp:include page="/common/Head.jsp" />
		<div id="divTitle">
			<span id="spanTitle">当前位置：服务器运维监控&gt;&gt;设备状态监控</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
			<form name="form" action="" method="post">
		        <div class="slider_body"> 
					<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点ID：</span>
		            </div>
	                <div class="slider_selected_right" style="z-index:0;">
	                    <div class="img_wrap">
	                        <div class="select_wrap select_input_wrap">
	                            <input id="Check_JcdID" class="slider_input" type="text" value="${Check_JcdID }" name="Check_JcdID" />
	                            <a id="Check_JcdID" class="empty"></a>
	                        </div>
	                    </div>  
	               </div>
		        </div>
		        <div class="slider_body">
		        	<div class="slider_selected_left">
		            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点名称：</span>
		            </div>
		            <div class="slider_selected_right" style="z-index:0;">
		                <div class="img_wrap">
		                	<div class="select_wrap select_input_wrap">
		                    	<input id="Check_JcdName" class="slider_input" type="text" value="${Check_JcdName }" name="Check_JcdName" />
		                    	<a id="Check_JcdName" class="empty"></a>
		                    </div>
		                </div>  
		            </div>
		        </div>
				<div class="slider_body"> 
		            <div class="slider_selected_left">
		                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;隶属城区：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" id="dropdown">
		            	<c:choose>
		            		<c:when test="${empty Check_areaName }">
					            <input class="input_select xiala" id="cqMc" type="text" name="Check_areaName" onclick="javascript:showTree()" value="==全部=="/>
			            	</c:when>
			            	<c:otherwise>
			            		<input class="input_select xiala" id="cqMc" type="text" name="Check_areaName" onclick="javascript:showTree()" value="${Check_areaName }"/>
			            	</c:otherwise>
			            </c:choose>
		                <input type="hidden" id="cqNo" name="Check_areaNo" value="${Check_areaNo }" />
		                <div class="ul">
							<div>
								<ul id="tree" class="zTree"></ul>
							</div>
		                </div> 
		            </div>
				</div>
				<div class="slider_body"> 
		            <div class="slider_selected_left">
		                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;隶属道路：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			            <c:choose>
			            	<c:when test="${empty Check_roadNo }">
					            <input class="input_select xiala" id="xiala1" type="text" readonly="readonly" value="==全部=="/>
			            	</c:when>
			            	<c:otherwise>
			            		<c:forEach items="${roads }" var="r">
			            			<c:if test="${r.roadNo eq Check_roadNo }">
				            			<input class="input_select xiala" id="xiala1" type="text" readonly="readonly" value="${r.roadName }"/> 
			            			</c:if>
			            		</c:forEach>
			            	</c:otherwise>
			            </c:choose>
			            <input type="hidden" id="xiala11" name="Check_roadNo" value="${Check_roadNo }" />
			            <div class="ul"> 
			            	<div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
			            	<c:forEach items= "${roads }" var="r" >
			           			<div class="li" data-value="${r.roadNo }" onclick="sliders(this)"><a rel="2">${r.roadName }</a></div> 
							</c:forEach>
			            </div>
			         </div>
				</div>
				<div class="slider_body"> 
		            <div class="slider_selected_left">
		                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点状态：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
			            <input class="input_select xiala" type="text" id="jcdzt" readonly="readonly" value="==全部=="/>
			            <input type="hidden" name="jcdzt" value="${jcdzt }" />
			            <div class="ul"> 
			            	<script>
			            		if('${jcdzt}' == "1"){
			            	  		$("#jcdzt").val("连接正常");
			            		} else if ('${jcdzt}' == "0"){
			            	 		$("#jcdzt").val("连接异常");
			            		}
			            	</script>
			            	<div class="li" data-value="" onclick="sliders(this)">==全部==</div>
			          		<div class="li" data-value="1" onclick="sliders(this)">连接正常</div> 
			            	<div class="li" data-value="0" onclick="sliders(this)">连接异常</div> 
			            </div>
			         </div>
		        </div>
           		<div class="button_wrap clear_both">
		        	<div class="slider_body show_style_control" style="width:120px">
			        	<input type="radio" name="tabMap" value="0" <c:if test="${tabMap eq '0' }">checked="checked"</c:if>/>列表&nbsp;&nbsp;
			        	<input type="radio" name="tabMap" value="1" <c:if test="${tabMap eq '1' }">checked="checked"</c:if>/>地图
			    	</div>
				    <input type="button" class="button_blue" value="查询" onclick="doSearch()">
				   	<input type="button" class="button_blue" value="重置" onclick="doReset()">
				   	<input type="button" class="button_blue" value="导出Excel" onclick="ExcelExport()">
			    </div>
		        <div class="pg_result" id="list_result">
		            <table>
		                <thead>
		                    <tr>
	                            <td align="center">序号</td>
	                            <td align="center">监测点ID</td>
	                            <td align="center">监测点名称</td>
	                            <td align="center">监测点类型</td>
	                            <td align="center">方向</td>
	                            <td align="center">所属城区</td>
	                            <td align="center">所属道路</td>
	                            <td align="center">经度</td>
	                            <td align="center">纬度</td>
	                            <td align="center">状态</td>
		                    </tr>
		                </thead>
		                <tbody>
	                        <c:forEach var="r" items="${pageResults.items }" varStatus="status">
	                        	<tr>
	                        		<td>${status.index+1} </td>
	                        		<td>${r[0].id }</td>
	                        		<td>${r[0].jcdmc }</td>
	                        		<c:forEach items="${jcdKind }" var="j">
		                        		<c:if test="${j.typeSerialNo eq r[0].jcdxz}">
		                        			<td>${j.typeDesc }</td>
		                        		</c:if>
	                        		</c:forEach>
	                        		<td>${jcdFx[r[0].xsfx]}</td>
	                        		<td>${cq[r[0].cqid]}</td>
	                        		<td>${roadMap[r[0].dlid]}</td>
	                        		<td>${r[0].jd }</td>
	                        		<td>${r[0].wd }</td>
	                        		<td>
	                        			<c:if test="${empty r[1].id  }">连接正常</c:if>
	                        			<c:if test="${not empty r[1].id }"><span style="color:red">连接异常</span></c:if>
	                        		</td>
	                        	</tr>
	                        </c:forEach>
		                </tbody>
		            </table>
					<jsp:include page="/common/pageNavigators.jsp"></jsp:include>
			        </div>
	    		</form>
				<div style="width:100%;height:600px;border:1px solid gray" id="container"></div>
			</div>
		</div>
		<jsp:include page="/common/Foot.jsp" />
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/MarkerClusterer_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/TextIconOverlay_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/GeoUtils_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
		//导出Excel
		function ExcelExport(){
			var url = "${pageContext.request.contextPath}/deviceStatus/excelExportForSbzt.do";
			document.forms[0].action = url;
			document.forms[0].submit();
		}
		
		var markers = [];//存放marker
		var init = false;
		$(function(){
			if('${tabMap}' == "1"){
				showMap();
			} else{
				hideMap();
			}
			$("input:radio[name='tabMap']").change(function(){
				var value = $("input[name='tabMap']:checked").val();
				if(value == "1"){
					showMap();
				}else{
					hideMap();
				}
			});
		});
		
		var map = new BMap.Map("container");      
		var point = new BMap.Point(114.063081, 22.547416);    // 创建点坐标 
		map.centerAndZoom(point,14);                     // 初始化地图,设置中心点坐标和地图级别。
		map.addControl(new BMap.NavigationControl());
		map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT})); // 右下角，添加比例尺
		map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
		map.enableKeyboard();                         // 启用键盘操作。
		map.setMinZoom(11);
		map.addEventListener("tilesloaded", addMymarkers);
		map.addEventListener("zoomend", addMymarkers);
		map.addEventListener("moveend", addMymarkers);
		
		function initMap(){
			//监测点图标
			var jcdIcon = new BMap.Icon("<%=basePath%>images/d1.png", new BMap.Size(20,30));
			var jcdIcon3 = new BMap.Icon("<%=basePath%>images/d3.png", new BMap.Size(20,30));
			jcdIcon.setAnchor(new BMap.Size(10,30));
			jcdIcon3.setAnchor(new BMap.Size(10,30));
			var list = jQuery.parseJSON('${jcd}');
			var c = new Convertor();
			for(var i=0;i < list.length;i++){
				var jcd = list[i];
				var r1 = c.WGS2BD09({lng:jcd[0].jd, lat:jcd[0].wd});//坐标转换
				var point1 = new BMap.Point(r1.lng, r1.lat);
				var marker = new BMap.Marker(point1);
				
	            (function(i){
		            marker.addEventListener("click", function(){
		            	var jcd = list[i];
		                var infoWin = new BMap.InfoWindow(jcd[0].jcdmc+"<br/>");
		                infoWin.setWidth(220);
					    infoWin.setHeight(60);
		                this.openInfoWindow(infoWin);
		            });
		        })(i);
		        marker.addEventListener("mouseover", function(){
					this.setTop(true);
				});
				if(jcd[1] != null){
					marker.setIcon(jcdIcon3);
				} else{
					marker.setIcon(jcdIcon);
				}
				markers.push(marker);
			}
		}
		function addMymarkers(){//显示可视区域内的标注
			var gb = map.getBounds();
		    for(var m in markers){
		        var res = BMapLib.GeoUtils.isPointInRect(markers[m].point, gb);
		        if(res == true) {
		        	map.addOverlay(markers[m]);
		            markers.splice(m, 1);
				}
		    }
		}
		
		//地图JS代码截止
		var list_url = "deviceStatus/queryDeviceStatus.do";
			//搜索..按钮
		  	function doSearch(){
		  		//重置页号(两种写法DOM,JQuery)
		  		//document.getElementById("pageNo").value=1;
		  		$("#pageNo").val(1);
		  		document.forms[0].action = list_url;
		  		document.forms[0].submit();	  		
		  	}
	
	  		//重置..按钮
	  		function doReset(){
	  			$("#Check_JcdID").val("");//监测点ID
	  			$("#Check_JcdName").val("");//监测点名称
	  			$("#cqMc").val("");//城区名称
	  			$("#cqNo").val("");//城区编码
	  			$("#xiala1").val("==全部==");//道路
	  			$("#xiala11").val("");//道路隐藏ID
	  			$("#jcdzt").val("==全部==");//道路
	  			$("input[name='jcdzt']").val("");//道路隐藏ID
	  		}
	 
			//根据页号查询...
			function doGoPage(pageNo) {
				document.getElementById("pageNo").value = pageNo;
				document.forms[0].action = list_url;
				document.forms[0].submit();
			}
			
			//加载Ztree...
			var setting = {
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				callback: {
					onCheck:onCheck
				},
				data: {
					key :{
						//name:"areaname",
					},
					simpleData: {
						enable: true,
						//idKey: "areano",
						//pIdKey: "suparea",
						rootPid:"00"
					},
				}
			};
			var zTree;
			var treeNodes;
			
			$(function(){
				//异步请求城区信息树
				$.ajax({
					async: false,//同步方法
					cache: false,
					type: 'POST',
					dataType: "json",
					url: "${pageContext.request.contextPath}/jcd/getTreeJson2.do",//请求的action路径
					error: function () {//请求失败处理函数
						alert('请求城区信息树失败！');
					},
					success:function(data){ //请求成功后处理函数。
						treeNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
					}
				});
				$.fn.zTree.init($("#tree"), setting, treeNodes);
			});
			
			//checkbox oncheck回调函数
			function onCheck(event,treeId,treeNode){
				//遍历树种所有的选中节点，把名字显示在input中去
				//获取选中的节点的id，保存ids
				var treeObj = $.fn.zTree.getZTreeObj("tree");
				var nodes = treeObj.getCheckedNodes(true);	
				
				//清空
				$("#cqMc").val("");
				var flag = 0;
				var ids = [];
				for(var node in nodes){
					if(nodes[node].isParent){
					
					} else{
						ids.push(nodes[node].id);
						
						if(flag == 0){
							$("#cqMc").val(nodes[node].name);
							flag = 1;
						} else if(flag == 1){
							var cqmc = $("#cqMc").val();
							var temp = "," + nodes[node].name;
							cqmc += temp;
							$("#cqMc").val(cqmc);
						};
					};
					$("#cqNo").val(ids);
				}
			};
			
			//控制树的显示
			function showTree(){
				var ul = $("#dropdown .ul"); 
		        if(ul.css("display") == "none"){ 
		            ul.slideDown("fast"); 
		            $(".mask").show();
		        } else{ 
		            ul.slideUp("fast"); 
		            $(".mask").hide();
		        } 
			}
			
		//显示地图
		function showMap(){
			$("#container").show();
			$("#list_result").hide();
			if(!init){
				initMap();
				setTimeout(function(){
					addMymarkers();
				}, 2000);
				init = true;
			}
		}
		function hideMap(){
			$("#container").hide();
			$("#list_result").show();
		}
	</script>
</html>