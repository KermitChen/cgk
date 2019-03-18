<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	//获取用户信息
	User userObj = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
	<base href="<%=basePath%>">
	<title>指令下发</title>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>common/css/index_selectMenu.css" type="text/css">
</head>
	<body>
		<div class="content">
			<div class="content_wrap" style="width:1150px;">
				<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
					<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">勤务卡口预案</legend>
					<div style="width:550px;height:320px;border:1px solid gray;float:left" id="container"></div>
					<div style="width:550px;height:320px;float:left">
						<div class="slider_body" style="display:none">
					    	<div class="slider_selected_left">
					        	<span>预计车辆速度：</span>
					        </div>
					        <div class="slider_selected_right">
					            <div class="img_wrap">
					            	<div class="select_wrap select_input_wrap">
					                	<input id="speed" name="speed" type="text" class="slider_input" maxlength="3" placeholder="请输入预计速度，默认为${defaultSpeed}"
					                        onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9,]+/,'');}).call(this)" onblur="this.v();" style="display:none"/>
					               	</div>
					          	</div>  
					        </div>
					    </div>
		<!-- 		        <button class="submit_b" onclick="refresh()">重新计算</button> -->
						<div class="pg_result" style="height:250px;overflow :auto">
							<table id="generatedTable">
								<thead>
									<tr>
										<td width="120" align="center">卡点名称</td>
										<td width="120" align="center">隶属部门</td>
										<td width="100" align="center">预计到达时间</td>
										<td style="display:none"></td>
										<td style="display:none"></td>
									</tr>
								</thead>
								<tbody id="result">
									<tr style="display:none"></tr>
									<tr id="cloneTr">
										<td></td>
										<td></td>
										<td></td>
										<td style="display:none"></td>
										<td style="display:none"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</fieldset>
				<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
					<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">添加指令信息</legend>
					<div class="slider_body">
				        <div class="slider_selected_left">
				           	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>预警级别：</span>
				        </div>
				        <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
							<input class="input_select xiala" id="jqjb_downlist" readonly="readonly" type="text" value="==请选择=="/>
							<input type="hidden" id="jqjbSelect" name="property" value=""/>
							<div class="ul">
								<div class="li" data-value="" onclick="sliders(this)">==请选择==</div>
								<div class="li" data-value="1" onclick="sliders(this)">一级预警</div>
								<div class="li" data-value="2" onclick="sliders(this)">二级预警</div>
								<div class="li" data-value="3" onclick="sliders(this)">三级预警</div>
							</div>
						</div>
				    </div>
				    <div class="slider_body">
						<div class="slider_selected_left">
							  <span><span style="color: red;">*</span>指令接收单位：</span>
						</div>
						<div class="slider_selected_right dropdown dropdown_all" id="jxkhDept_dropdown_quanxuan">
							  <div class="input_select xiala">
							      <div id='jxkhDeptDiv' class='multi_select'>
							      	<input type="hidden" id="jxkhDept" name="jxkhDept" value="" />
									<a class="xiala_duoxuan_a"></a>
							      </div>
							  </div>
						</div>
					</div>
				    <div class="slider_body">
				        <div class="slider_selected_left">
				            <span><span style="color: red;visibility: hidden;">*</span>下达指令方式：</span>
				        </div>
				        <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
							<input class="input_select xiala" id="zlfs_downlist" readonly="readonly" type="text" value="对讲机"/>
							<input type="hidden" id="zlfsSelect" name="property" value="1"/>
							<div class="ul">
								<div class="li" data-value="1" onclick="sliders(this)">对讲机</div>
								<div class="li" data-value="2" onclick="sliders(this)">电话</div>
								<div class="li" data-value="3" onclick="sliders(this)">其他方式</div>
							</div>
						</div>
				    </div>
				    <div class="slider_body" style="position: relative; clear: both;">
				        <div class="slider_selected_left">
				            <span>&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>下达指令人 ：</span>
				        </div>
				        <div class="slider_selected_right" style="">
				        	<div class="img_wrap">
				        		<div class="select_wrap select_input_null">
				                   	<input id="UserName" name="userName" type="text" class="slider_input" value="<%=userObj.getUserName() %>">
				                </div>
				            </div>  
				        </div>
				    </div>
				    <div class="slider_body">
				        <div class="slider_selected_left">
				            <span><span style="color: red;visibility: hidden;">*</span>下达指令单位：</span>
				        </div>
				        <div class="slider_selected_right" style="">
				        	<div class="img_wrap">
				        		<div class="select_wrap select_input_null">
				                    <input id="DeptName" name="deptName" type="text" class="slider_input" value="<%=userObj.getDeptName() %>">
				                </div>
				            </div>  
				        </div>
				    </div>
					<div class="slider_body_textarea">
				        <div class="slider_selected_left">
				           	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>预案内容：</span>
				        </div>
				        <div class="slider_selected_right">
				            <textarea name="yanr" id="yanr" rows="2" style="width:951px" maxlength="500"></textarea>
				        </div>
				    </div>
				    <div class="clear_both">
						<button class="submit_b" onclick="toSave()">下发指令</button>
						<button class="submit_b" onclick="sendToPGIS()">发送PGIS</button>
				        <button class="submit_b" id="toClose" style="display: none;">关闭</button>
				    </div>
				</fieldset>
			</div>
		</div>
		<div class="mask"></div>
	</body>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/MarkerClusterer_min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/map/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.dev.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/time/moment.js"></script>
<script type="text/javascript">
	var ewarning = jQuery.parseJSON('${ewarning}');
	var jcd = jQuery.parseJSON('${jcd}');
	var selectKD = new Array();//选中卡点数组，并扩展Array原型
	var points = [];//监测点集合
	var rangeX = '${rangeX}';//默认选择范围
	var defaultSpeed = '${defaultSpeed}';//默认速度
	
	//计算平均速度总数
	var allSpeed = 0;
	var num = 1;//计算平均速度计数
	if(ewarning.sd != null){
		allSpeed += ewarning.sd;
	}
	
	$(function(){
		var jxkhDepts = jQuery.parseJSON('${jxkhDepts}');
		//通知单位多选框初始化
		var value = []; 
		var data = []; 
		for(var i=0;i < jxkhDepts.length;i++){
			if(jxkhDepts[i].dept_no != '440300010100'){
				value.push(jxkhDepts[i].dept_no + ":" + jxkhDepts[i].dept_name);
				data.push(jxkhDepts[i].dept_name);
			}
		}
	    $('#jxkhDeptDiv').MSDL({
		  'value': value,
	      'data': data
	    });
	    
	    //初始化地图
	    initMap();
	    
		//赋值预案内容
		$("#yanr").val(ewarning.dispatched.by2);
		//关闭layer弹出层
		$("#toClose").click(function(){
			closeLayer();
		});
	});
	
	var map = new BMap.Map("container");      
	map.addControl(new BMap.NavigationControl());
	map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT})); // 右下角，添加比例尺
	map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
	map.enableKeyboard();                         // 启用键盘操作。
	map.setMinZoom(11);
	function initMap(){
		//监测点图标
		var jcdIcon = new BMap.Icon("<%=basePath%>images/l1.png", new BMap.Size(20, 30));
		var jcdIcon2 = new BMap.Icon("<%=basePath%>images/l2.png", new BMap.Size(30, 30));
		var jcdIcon3 = new BMap.Icon("<%=basePath%>images/l3.png", new BMap.Size(20, 30));
		var ewIcon = new BMap.Icon("<%=basePath%>images/map-warning.png", new BMap.Size(32, 30));
		jcdIcon.setAnchor(new BMap.Size(10, 30));
		jcdIcon2.setAnchor(new BMap.Size(10, 30));
		jcdIcon3.setAnchor(new BMap.Size(10, 30));
		ewIcon.setAnchor(new BMap.Size(16, 28));
		
		var preJcdId = jcd.id;
		//判断有无坐标，无坐标则不考虑显示预警点
		if(jcd.jd != 0 && jcd.wd != 0){
			var ePoint = new BMap.Point(jcd.jd, jcd.wd);
			points.push(ePoint);
			var ewMarker = new BMap.Marker(ePoint);
			ewMarker.setIcon(ewIcon);
			var sd = (ewarning.sd == null || ewarning.sd == 0 ? '无':ewarning.sd);
			var lab1 = new BMap.Label("车牌号码：" + ewarning.hphm + "<br/>监测点：" + jcd.jcdmc + "<br/>通过时间：" + formatDate(ewarning.tgsj) + "<br/>通过速度：" + sd, {position:ePoint});
			lab1.setOffset(new BMap.Size(16, 28));
			ewMarker.setLabel(lab1); 
			ewMarker.setTop(true);
			map.addOverlay(ewMarker); 
			map.centerAndZoom(ePoint, 12);                     // 初始化地图,设置中心点坐标和地图级别。
		} else {
			alert("预警点位坐标不全，地图无法显示具体预警位置！");
		}
		
		//添加该车其他预警记录
		var ewList = jQuery.parseJSON('${ewList}');
		if(ewList.length > 0){
		    var yjid = "";
			for(var i = 0;i < ewList.length;i++){
			    if(ewList[i].jd != 0 && ewList[i].wd != 0 && yjid != ewList[i].bjxh){//坐标不能为0
			    	//只取一条预警
			    	yjid = ewList[i].bjxh;
			    	
			    	//查找坐标重合的点， 使其偏离一点，目的是避免重叠
			    	for(var j = 0;j < points.length;j++){
					    if(points[j].lng == ewList[i].jd && points[j].lat == ewList[i].wd){
							ewList[i].jd = ewList[i].jd + rd();
							ewList[i].wd = ewList[i].wd + rd();
					    	break;
					    }
					}
					
					//添加点
					var ePoint2 = new BMap.Point(ewList[i].jd, ewList[i].wd);
					points.push(ePoint2);
					var ewMarker2 = new BMap.Marker(ePoint2);
					var sd2 = (ewList[i].sd == null || ewList[i].sd == 0 ?'无':ewList[i].sd);
					//var lab2 = new BMap.Label("监测点：" + ewList[i].jcdmc + "<br/>通过时间：" + formatDate(ewList[i].tgsj) + "<br/>通过速度：" + sd2, {position:ePoint2});
					//lab2.setOffset(new BMap.Size(16, 28));
					//ewMarker2.setLabel(lab2);
					ewMarker2.setTitle("车牌号码：" + ewarning.hphm + "\n监测点：" + ewList[i].jcdmc + "\n通过时间：" + formatDate(ewList[i].tgsj) + "\n通过速度：" + sd2, {position:ePoint2});
					ewMarker2.addEventListener("mouseover", function(){
						this.setTop(true);
					});
					ewMarker2.addEventListener("mouseout", function(){
						this.setTop(false);
					});
					map.addOverlay(ewMarker2); 
					
					//速度增加
					if(ewList[i].sd != null && ewList[i].sd > 0){
						num++;
						allSpeed += ewList[i].sd;
					}
			    }
			}
			setTimeout(function(){
               	if(points.length > 1){
		           	map.setViewport(points);          //调整到最佳视野  
		        } else if(points.length == 1){
		          	map.centerAndZoom(points[0], 12);
		        } 
			}, 200);
		}
		
		//计算平均速度
		$("#speed").val(allSpeed/num);
		
		//显示卡点
		var zakd = jQuery.parseJSON('${zakd}');//卡点
		for(var i=0;i < zakd.length;i++){
			var point1 = new BMap.Point(zakd[i].X, zakd[i].Y);
			var marker = new BMap.Marker(point1);
            (function(i){
	            marker.addEventListener("click", function(){
					if(this.getIcon() == jcdIcon){
						//设置图标
						this.setIcon(jcdIcon2);
						//获取时间
						var time = calTime(jcd.jd, jcd.wd, zakd[i].X, zakd[i].Y, jcd.id, zakd[i].ID);
						//封装信息
						var o = new Object();
						o.ID = zakd[i].ID;
						o.POSTION = zakd[i].POSTION;
						o.ORGCODENAME = zakd[i].ORGCODENAME;
						o.X = zakd[i].X;
						o.Y = zakd[i].Y;
						o.aX = zakd[i].aX;
						o.aY = zakd[i].aY;
						o.CODE = zakd[i].CODE;
						o.CALLNUM = zakd[i].CALLNUM;
						o.POSTCODE = zakd[i].POSTCODE;
						o.TIME = time;
						//放入列表
						if(!checkSelectKdExits(zakd[i].ID)){
							selectKD.push(o);
						}
						
						var label = new BMap.Label("预计时间：" + time);
						label.setOffset(new BMap.Size(10,30));
						this.setLabel(label); 
						refresh();
					} else if(this.getIcon() == jcdIcon2){
						this.setIcon(jcdIcon);
						//从所选列表中删除
						removeObjFromSelectKd(zakd[i].ID);
						map.removeOverlay(this.getLabel()); 
						refresh();
					} else {
						alert("没有勤务人员值守！");
					}
	            });
	        })(i);
	        
	        zakd[i].worknum > 0 ? marker.setIcon(jcdIcon) : marker.setIcon(jcdIcon3);
			marker.setTitle("卡点名称：" + zakd[i].POSTION + "\n隶属部门：" + zakd[i].ORGCODENAME + "\n对讲机呼号：" + zakd[i].CALLNUM);
			marker.addEventListener("mouseover", function(){
				this.setTop(true);
			});
			marker.addEventListener("mouseout", function(){
				this.setTop(false);
			});
			
			//在地图上显示
			map.addOverlay(marker);
			
			//选择指定范围内的卡点，并计算预计到达时间并在表格中显示
			if(baiduDistance(jcd.jd,jcd.wd,zakd[i].X, zakd[i].Y) <= parseInt(rangeX) * 1000){//方圆X公里内，这里请勿用drawTrail方法，否则会造成大量查询数据库
				if((((jcd.xsfx == "1" || jcd.xsfx == "5" || jcd.xsfx == "8") && jcd.jd > zakd[i].X) || 
				((jcd.xsfx == "2" || jcd.xsfx == "6" || jcd.xsfx == "7") && jcd.jd < zakd[i].X) ||
				((jcd.xsfx == "3" || jcd.xsfx == "5" || jcd.xsfx == "7") && jcd.wd < zakd[i].Y) || 
				((jcd.xsfx == "4" || jcd.xsfx == "6" || jcd.xsfx == "8") && jcd.wd > zakd[i].Y)) && zakd[i].worknum > 0){
					marker.setIcon(jcdIcon2);
					
					//获取时间
					var time = calTime(jcd.jd, jcd.wd, zakd[i].X, zakd[i].Y, jcd.id, zakd[i].ID);
					//封装信息
					var o = new Object();
					o.ID = zakd[i].ID;
					o.POSTION = zakd[i].POSTION;
					o.ORGCODENAME = zakd[i].ORGCODENAME;
					o.X = zakd[i].X;
					o.Y = zakd[i].Y;
					o.aX = zakd[i].aX;
					o.aY = zakd[i].aY;
					o.CODE = zakd[i].CODE;
					o.CALLNUM = zakd[i].CALLNUM;
					o.POSTCODE = zakd[i].POSTCODE;
					o.TIME = time;
					//放入列表
					selectKD.push(o);
					
					var label = new BMap.Label("预计时间：" + time);
					label.setOffset(new BMap.Size(10, 30));
					marker.setLabel(label); 
					refresh();
				}
			}
			//
			if(i == zakd.length-1 && selectKD.length == 0){
				layer.msg(rangeX + "公里内没有符合条件的卡点，请手动选择合适的卡点！");
			}
		}
	}
	
	function refresh(){
		//删除旧数据 
		$("#result tr:gt(1)").remove(); 
		//1,获取上面id为cloneTr的tr元素  
		$("#cloneTr").show();
		var tr = $("#cloneTr");  
		$.each(selectKD, function(index, item){ 
             //克隆tr，每次遍历都可以产生新的tr   
               var clonedTr = tr.clone();  
               var _index = index;  
               
               var id = item.ID;
               var name = item.POSTION;
               var bm = item.ORGCODENAME;
               var x = item.X;
               var y = item.Y;
               var time = item.TIME;
               var other = item.aX + ";" + item.aY + ";" + item.CODE + ";" + item.CALLNUM + ";" + item.POSTCODE;
               
               //循环遍历cloneTr的每一个td元素，并赋值  
               clonedTr.children("td").each(function(inner_index){  
                      //根据索引为每一个td赋值  
                           switch(inner_index){  
                                  case(0):   
                                     $(this).html(name);  
                                     break;
                                  case(1):   
                                     $(this).html(bm);  
                                     break;  
                                  case(2):  
                                     $(this).html(time);  
                                     break;  
                                  case(3):  
                                     $(this).html(id);
                                     break;
                                  case(4):  
                                     $(this).html(other);  
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
	function calTime(lat_a, lng_a, lat_b, lng_b, start, end){
		//距离
		var len = drawTrail(start, end);
		if(len == 0 && lat_a != null && lat_a > 0 && lng_a != null && lng_a > 0
			&& lat_b != null && lat_b > 0 && lng_b != null && lng_b > 0){
			len = distance(lat_a, lng_a, lat_b, lng_b);//计算是否有问题
		}
		//速度
		var speed = $("#speed").val();
		if(speed == "" || speed == 0){
			speed = defaultSpeed;
		}
		//时间
		if(len != null && len != 0 && speed != null && speed != 0){
			var time = len/1000/speed;
			var nowtime = Date.parse(new Date());
			return formatDateMinute(nowtime + time*3600*1000);	
		} else {
			return "";
		}
	}
	
	//计算距离
	function distance(lat_a, lng_a, lat_b, lng_b){
// 		var len = distance1(lat_a, lng_a, lat_b, lng_a)+distance1(lat_b, lng_b, lat_b, lng_a);//计算距离为2点的正方形规则边长
		var len = parseInt(baiduDistance(lat_a, lng_a, lat_b, lng_a)) + parseInt(baiduDistance(lat_b, lng_b, lat_b, lng_a));//计算距离为2点的正方形规则边长
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
		var pointA = new BMap.Point(lat_a, lng_a);
		var pointB = new BMap.Point(lat_b, lng_b);
		return (map.getDistance(pointA, pointB)).toFixed(0);
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
	    return year+"-"+month+"-"+date+"   "+hour+":"+minute;
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
                              //kd += $(this).text(); 
                              break; 
                           case(2):  
                              kd += ";" + $(this).text();
                              break;  
                           case(3):  
                              kd += ";" + $(this).text();
                              break;
                           case(4):  
                              kd += ";" + $(this).text();
                              break; 
                    }
				});
				lastKD.push(kd);
			});
			$.ajax({
			    url: '${pageContext.request.contextPath}/earlyWarning/sendInstruction.do',
			    dataType: "json",  
			    data:{bjxh:ewarning.bjxh, qsid:ewarning.qsid, hphm:ewarning.hphm, hpzl:ewarning.hpzl, jqjb:$.trim($("#jqjbSelect").val()), zlfs:$.trim($("#zlfsSelect").val()), yanr:$("#yanr").val(), lastKD:lastKD, zldw:$.trim($("#jxkhDept").val())},
			    type: "POST",   //请求方式
				traditional: true,
			    success: function(data) {
			    	$.unblockUI();
			    	if(data == "success"){
			    		parent.layer.msg("下发指令成功！");
			    		parent.refresh();
			    		closeLayer();
			    	} else if(data == "repeat"){
			    		parent.layer.msg("该预警已下发指令！");
			    		parent.refresh();
			    		closeLayer();
			    	} else if(data == "fail"){
			    		parent.layer.msg("下发指令失败！");
			    	}
			    }
			});
		}
	}
	
	function validate(){
		if($.trim($("#jqjbSelect").val()) == ""){
			alert("请先选择预警级别！");
			return false;
		} else if($.trim($("#jxkhDept").val()) == ""){//部门ID:部门名称
			alert("请先选择指令接收单位！");
			return false;
		} else if($("#yanr").val().trim() == ""){
			alert("请先输入预案内容！");
			return false;
		} else{
			return true;
		}
	}
	
	function closeLayer(){
		var index = parent.layer.getFrameIndex(window.name); 
		parent.layer.close(index);
	}
	
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
	
	//获取路径长度
	function drawTrail(start, end){
		var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		var trailLength = 0;
		$.ajax({
			async:false,
			type: 'POST',
			data:{start:start, end:end},
			dataType : "json",
			url: basePath + "/mapTrail/selectMapTrailKD.do",//请求的action路径
			error: function () {//请求失败处理函数
// 				layer.msg('路径查询失败！');
			},
			success:function(data){ //请求成功后处理函数。
				var mapTrail = data;
				if(mapTrail != null && mapTrail.jcdids != null && mapTrail.jcdids != ""){
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
				} else{
// 					layer.msg("没有两点之间的路径");
				}
			}
		});
		
		//返回
		return trailLength;
	}
	
	//从selectKd中移除对象
	function removeObjFromSelectKd(id){
		var newSelectKD = new Array();//选中
		$.each(selectKD, function(index, item){
			if(id != item.ID){
				newSelectKD.push(item);
			}
        });
        //重新赋值
        selectKD.splice(0, selectKD.length);//清空
        Array.prototype.push.apply(selectKD, newSelectKD);
	}
	
	//检查卡点是否已经选择
	function checkSelectKdExits(id){
	    var flag = false;
		$.each(selectKD, function(index, item){
			if(id == item.ID && !flag){
				flag = true;
			}
        });
        return flag;
	}
	
	//推送至PGIS
	function sendToPGIS() {
		//判断预案内容是否为空
		if($("#yanr").val().trim() == ""){
			alert("请先输入预案内容！");
			return;
		}
		//if(validate()){
			//显示进度条
			var index = layer.load();
			
			//获取卡点
			var lastKD = new Array();
			$("#result tr:gt(1)").each(function(){
				var kd = "";
				$(this).children("td").each(function(inner_index){
					switch(inner_index){  
                           case(0):   
                              kd += $(this).text(); 
                              break;
                           case(1):  
                              //kd += ";" + $(this).text();
                              break;
                           case(2):  
                              kd += ";" + $(this).text();
                              break;  
                           case(3):  
                              kd += ";" + $(this).text();
                              break;
                           case(4):  
                              kd += ";" + $(this).text();
                              break; 
                    }
				});
				lastKD.push(kd);
			});
			
			//提交
			$.ajax({
				url:'${pageContext.request.contextPath}/earlyWarning/sendToPGIS.do?' + new Date().getTime(),
				type: "POST", //请求方式
				dataType: "json",
				traditional: true,
				data:{hphm:ewarning.hphm, hpzl:ewarning.hpzl, tgsj:ewarning.tgsj, jcdid:ewarning.jcdid, jcdmc:ewarning.jcdmc, cdid:ewarning.cdid, jwd:jcd.py, yanr:$.trim($("#yanr").val()), kd:lastKD},
				success:function(data){
					//关闭进度条
					layer.close(index);
					if(data.result == "1"){ //添加成功！
						alert('发送成功！');
					} else {
				    	alert('发送失败！');
				    }
				},
				error: function () {//请求失败处理函数
					//关闭进度条
					layer.close(index);
					alert('发送失败！');
			    }
			});
		//}
	}
</script>
</html>