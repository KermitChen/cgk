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
<title>出行时间分析</title>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<link rel="stylesheet" href="<%=basePath%>common/css/sb/footHold.css" type="text/css">	
	<div id="divTitle">
		<span id="spanTitle">出行时间分析</span>
	</div>
    <div class="content">
    	<div class="content_wrap">
    		<form id="data_form">
   	 		<div class="slider_body">
                <div class="slider_selected_left">
                    <span id="hphm_span"  class="layer_span" style="color:#900b09">车牌号码：<span style="color:red;">*</span></span>
                </div>
                <div class="slider_selected_right">
                    <div class="img_wrap">
                        <div class="select_wrap input_wrap_select">
                            <input id="cphid" name="hphm" type="text" class="slider_input" value="">
                            <a class="empty" href="javascript:doCplrUI()"></a>
                        </div>
                   </div>
                </div>   
              </div>
	        <div class="slider_body" >
			  <div class="slider_selected_left">
				<span>分析时间：<span style="color:red;">*</span></span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="kssj" name="kssj" value="" 
			onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
				  </div>
				</div>
			</div>
			<div class="slider_body" >
			  <div class="slider_selected_left">
				<span>至</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="jssj" name="jssj" value="" 
			onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
				  </div>
				</div>
			</div>
	        </form>	      	        
	        <div class="button_wrap clear_both">
		    	<input id="query_button" name="query_button" type="button" class="button_blue" value="分析" onclick="doSubmit();">
		    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
		    	<input id="remap_button" name="remap_button" type="button" class="button_blue" value="清空地图" onclick="doReMap();">
		    	<!-- 错误信息提示 -->
				<div>
					<span id="errSpan"  style="color:red;margin-left:auto;margin-right:auto" ></span>
		    	</div>
		    </div>
    	 	
	        <div class="pg_result">
	        	<div class="data_div">
	        		<table>
						 <thead>
		                    <tr>
		                        <td>序号</td>
		                        <td>地点</td>
		                        <td>次数</td>
		                        <td>操作</td>
		                    </tr>
		                </thead>
		                <tbody id="data_table"></tbody>
					</table>
	        	</div>
	        	<div class="map_div" id="container">
	        	</div>
	        </div>
        </div>
    </div>
    <jsp:include page="/common/Foot.jsp" />
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
	<script type="text/javascript">
		var resJcd = [];//存放监测点
		$(function(){
			initTime();
			$("#cphid").change(function(){
				var reg = /^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$/;
				if(!reg.test($.trim($("#cphid").val()))){
					$("#errSpan").text("错误提示：请输入正确号牌号码！");
					$("#cphid").val("");
				}else{
					$("#errSpan").text("");
				}
			});
		 	$("#hphm_span").mouseover(function(){
				showTip();
			});
			$("#hphm_span").mouseleave(function(){
				layer.closeAll('tips');
			});
		});
		function showTip(){
			layer.open({
		           type: 4,
		           shade: 0,
		           time:8000,
		           closeBtn: 0,
		           tips: [3, '#758a99'],
		           content: ['只能输入一个车牌号码!','#hphm_span']
		       });
		}
		function initTime(){
			var end = moment().format('YYYY-MM-DD');
			var start = moment().subtract(30, 'days').format('YYYY-MM-DD');
			$("#kssj").val(start);
			$("#jssj").val(end);
		}
		//提交表单
		function doSubmit(){
			if(!commonCheck(true,true)){//通用验证
				return;
			}
			var reg = /^-?[1-9]\d*:-?[1-9]\d*$/;
			$("#data_table tr").remove();
			var reqData = $("#data_form").serialize();
			resJcd = [];//清空
			map.clearOverlays();
			layer.load();
			$.ajax({
				async:true,
				type: 'POST',
				data:reqData,
				dataType : "json",
				url: "${pageContext.request.contextPath}/gjfx/footHold.do",//请求的action路径
				error: function () {//请求失败处理函数
					layer.closeAll("loading");
					layer.msg("分析结果失败！");
				},
				success:function(data){ //请求成功后处理函数。
					layer.closeAll("loading");
					if(data != null && data.length > 1 && data[0] != null){
						appendData(data[0]);
						getJcd(data[1],data[0]);
					}else{
						layer.msg("分析结果失败！");
					}
				}
			});
		}
		//将分析数据显示到页面
		function appendData(data){
			for(var i=0;i<data.length;i++){
				$("#data_table").append("<tr id="+data[i].jcdid+">"+
                        					"<td>"+(i+1)+"</td>"+
                        					"<td>"+getJcdName(data[i].jcdid)+"</td>"+
                        					"<td>"+data[i].times+"</td>"+
                        					"<td><a onclick=\"countDetail('"+data[i].jcdid+"')\">详情</a></td>"+
                    					"</tr>");
			}
			$("table > tbody > tr").click(function () {
		       showJcd($(this).attr("id"));
		    });
		}
		//得到需要标记的监测点
		function getJcd(jcdList,resList){
			for(i=0;i<resList.length;i++){
				for(j=0;j<jcdList.length;j++){
					if(resList[i].jcdid == jcdList[j].id){
						resJcd.push({id:jcdList[j].id,jd:jcdList[j].jd,wd:jcdList[j].wd,jcdmc:jcdList[j].jcdmc,times:resList[i].times});
					}
				}
			}
// 			return resJcd;
		}
		//点击详情对单个监测点数据进行统计
		function countDetail(jcdid){
			var hphm = $.trim($("#cphid").val());
			var kssj = $.trim($("#kssj").val());
			var jssj = $.trim($("#jssj").val());
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];  
			var url = basePath + "/gjfx/footHoldDetail.do?hphm="+hphm+"&kssj="+kssj+"&jssj="+jssj+"&jcdid="+jcdid;
			layer.open({
			  type: 2,
			  title:false,
			  area:['700px','460px'],
			  closeBtn:2,
			  shadeClose:true,
			  content: url
			});
		}
		//将监测点编号翻译成监测点名称
		function getJcdName(jcdid){
			var jcdName = "";
			$.ajax({
				async:false,
				type: 'POST',
				data:{jcdid:jcdid},
				dataType : "json",
				url: "${pageContext.request.contextPath}/bdController/tranJcdId.do",//请求的action路径
				error: function () {//请求失败处理函数
					alert("翻译监测点失败！");
				},
				success:function(data){ //请求成功后处理函数。
					jcdName = data;
				}
			});
			return jcdName;
		}
		function doReset(){
			$("#errSpan").text("");
			$("#cphid").val("");
			initTime();
		}
		//初始化地图
		var map = new BMap.Map("container");      
		var point = new BMap.Point(114.063081, 22.547416);    // 创建点坐标 
		map.centerAndZoom(point,12);                     // 初始化地图,设置中心点坐标和地图级别。
		map.addControl(new BMap.NavigationControl());
		map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT})); // 右下角，添加比例尺
		map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
		map.enableKeyboard();                         // 启用键盘操作。
		map.setMinZoom(11);
		var points = [];
		var jcds = [];
		//监测点图标
		var jcdIcon = new BMap.Icon("<%=basePath%>images/marker_red_hd.png", new BMap.Size(20,30));
		jcdIcon.setAnchor(new BMap.Size(10,30));
		function showJcd(id){
			for(var i=0;i<resJcd.length;i++){
				if(id == resJcd[i].id){
					if(!checkArr(id,jcds)){
						jcds.push(id);
						var jd = resJcd[i].jd;
						var wd = resJcd[i].wd;
						for(var j = 0; j < points.length;j++){//查坐标重合的点
						   	if(points[j].lng == resJcd[i].jd && points[j].lat == resJcd[i].wd){
								jd = resJcd[i].jd+rd();
								wd = resJcd[i].wd+rd();
						   		break;
						   	}
						}
						var point1 = new BMap.Point(jd, wd);
						points.push(point1);
						var marker = new BMap.Marker(point1);
						marker.addEventListener("click", function(){
                            if(this.getLabel() == null || this.getLabel() == undefined){
								var label = new BMap.Label("监测点:"+resJcd[i].jcdmc+"<br/>"+"通行次数:"+resJcd[i].times);
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
						marker.setIcon(jcdIcon);
						map.addOverlay(marker);
						setTimeout(function(){  
	                      	if(points.length > 1){
				            	map.setViewport(points);          //调整到最佳视野  
					        }else if(points.length == 1){
					           	map.centerAndZoom(points[0],12);
					        } 
					    },200);
						setTimeout(function(){  
	                       	if(marker != null){
	                       		marker.setAnimation(BMAP_ANIMATION_BOUNCE);
	                       		setTimeout(function(){  
		                       		marker.setAnimation(null);
			                  	},2000); 
	                  		}
	                  	},500); 
                  	}
                  	break;
				}else if(i == resJcd.length-1){
					layer.msg("该监测点没有坐标，无法显示");
				}
			}
		}
		//清空地图
		function doReMap(){
			map.clearOverlays();
			points = [];
			jcds = [];
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
		function checkArr(t, arryAll){
			for(var i = 0;i < arryAll.length; i++){  
				if(t == arryAll[i]){
					return true;
				}
			}
			return false;
		}
	</script>
</html>