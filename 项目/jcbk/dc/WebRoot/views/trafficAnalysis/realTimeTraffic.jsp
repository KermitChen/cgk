<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实时流量监控</title>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<style type="text/css">
		.chart_view{
			width: 1000px;
			height:550px;
			position: relative;
		    display: block;
		    float: left;
		    margin:5px;
		    padding:10px;
		    border:#D2691E 0.5px solid;
		}
		.control-panel{
			width: 1000px;
			height:20px;
			position: relative;
		    display: block;
		    float: left;
		}
		.chart_container{
			width: 1000px;
			height:530px;
			position: relative;
		    display: block;
		    float: left;
		}
		.control-panel a{
			float: right;
			top:0; 
			right:0; 
			z-index:99;
			color: #900b09;
		}
		.control-panel a:hover {
			color:#FF0000;
		}
		.pg_result{
			height:auto !important;
			height:200px;
			min-height:200px
		}
	</style>
	<div id="divTitle">
		<span id="spanTitle">实时流量监控</span>
	</div>
	<div class="content">
		<div class="content_wrap">
			<div class="slider_body">
	            <div class="slider_selected_left">
	                <span>取样间隔：</span>
	            </div>
	            <div class="slider_selected_right dropdown dropdowns" id="cp_2" onclick="slider(this)">
		             <input class="input_select xiala" id="qyjg_xiala" readonly="readonly" type="text" value="1分钟"/> 
	                 <input type="hidden" id="qyjg" value="1" />
	                <div class="ul"> 
		                <div class="li" data-value="1" onclick="sliders(this)">1分钟</div>
		                <div class="li" data-value="5" onclick="sliders(this)">5分钟</div>
		                <div class="li" data-value="10" onclick="sliders(this)">10分钟</div>
		                <div class="li" data-value="15" onclick="sliders(this)">15分钟</div>
	                </div> 
	            </div>
	        </div>
	        <div class="slider_body">
	            <div class="slider_selected_left">
	                <span>监测点：</span>
	            </div>
	            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
	                <input class="input_select xiala" onclick="doChoseJcd();" id="jcdid1" type="text" value="==请选择=="/> 
	                <input type="hidden" name="jcdid" id="jcdid" value="">
	            </div>
	        </div>
	        <div class="slider_body">
	        	<p>
					<font size="2" color="red">提示：一次只能选一个监测点!</font>
				</p>
	        </div>
	        <div class="button_wrap clear_both">
		    	<input id="jiankong" name="query_button" type="button" class="button_blue" value="监控">
		    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置">
	    	</div>
	    	<div id="data_div" class="pg_result">
	        </div>
		</div>
	</div>
	<jsp:include page="/common/Foot.jsp" />
</body>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/echarts.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
<script type="text/javascript">
	var countChartNum = 0;
	var timeTicket = [];//定时器数组
	var chartInstances = [];//图表数组
	
	$(function(){
		$("#jiankong").click(function(){
			if(chartInstances.length < 4){
				newChart();
			}else{
				layer.msg('最多添加四个图表!');
			}
		});
		$("#reset_button").click(function(){
			$("#qyjg_xiala").val("1分钟");
			$("#qyjg").val("1");
			$("#jcdid1").val("==请选择==");
			$("#jcdid").val("");
		});
	});
	//删除图表
	function delChart(id){
		clearInterval(timeTicket[id.substring(4)]);//清除定时器
		var chart = echarts.getInstanceByDom(document.getElementById(id));//获取图表对象
		chartInstances.remove(chart);
		echarts.dispose(chart);//销毁echarts实例
		$("#"+id).parents('.chart_view').remove();
	}
	//新增图表
	function newChart(){
		//初始化首次显示的数据
		var legend = [];//图例
		var series = [];//数据
		//请求数据
		var	interval = $.trim($("#qyjg").val());
		var beginTime = moment().subtract(15*parseInt(interval)+3,'minutes').format("YYYY-MM-DD HH:mm:ss");
		var	endTime = moment().subtract(3,'minutes').format("YYYY-MM-DD HH:mm:ss");
		var	jcd = $.trim($("#jcdid").val());
		if(isEmpty(jcd) || (jcd.indexOf(',') > -1)){
			layer.msg('选择监测点错误！');
			return;
		}
		//获取后台数据
		$.ajax({
			url:'<%=basePath%>traffic/getChartData.do?' + new Date().getTime(),
			method:"post",
			data:{beginTime:beginTime,endTime:endTime,jcd:jcd,interval:interval},
			dataType:'json',
			success:function(data){
				if(data != null && data.date != null && data.legend != null && data.date.length > 0 && data.legend.length > 0){
					//添加图表容器
					$("#data_div").prepend("<div class=\"chart_view\"><div class=\"control-panel\"><a  target=\"#\" onclick=\"delChart('main"+countChartNum+"');\">关闭</a></div><div id=\"main"+countChartNum+"\" class=\"chart_container\"></div></div>");
					//初始化图表
					var myChart = echarts.init(document.getElementById("main"+countChartNum));
					parseData(data, series);
					//配置项参数
					var option = {
						    title: {
						        text: '车流量统计',
						        subtext:getJcdName(jcd)
						    },
						    legend:{
						    	data:data.legend,
						    	left:200,
						    	right:100,
						    	selected:{
						    		'车道一':true,
						    		'车道二':true,
						    		'车道三':true,
						    		'车道四':false,
						    		'车道五':false,
						    		'车道六':false,
						    		'车道七':false,
						    		'车道八':false
						    	}
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    toolbox:{
						    	show:true,
						    	feature:{
						    		magicType: {
						                type: ['stack', 'tiled']
						            },
						    		saveAsImage:{},
						    		dataView:{}
						    	}
						    },
						    xAxis: {
						    	position:'bottom',
						        type: 'category',
						        name:'时间',
						        nameLocation:'end',
						        boundaryGap:false,
						        axisLabel:{
						        	interval:0,
						        	rotate:-30
						        },
						        data:data.date
						    },
						    yAxis: {
						        type: 'value',
						        name:'流量',
						        nameLocation:'end',
						        boundaryGap: [0, '50%']
						    },
						    series: series
						};
					myChart.setOption(option);
					chartInstances.push(myChart);
					//添加定时器
					var ticket = setInterval(function () {
						beginTime = moment(beginTime,"YYYY-MM-DD HH:mm:ss").add(parseInt(interval),'m').format("YYYY-MM-DD HH:mm:ss");
						endTime = moment(endTime,"YYYY-MM-DD HH:mm:ss").add(parseInt(interval),'m').format("YYYY-MM-DD HH:mm:ss");
						series = [];
						$.ajax({
							url:'<%=basePath%>traffic/getChartData.do?' + new Date().getTime(),
							method:"post",
							data:{beginTime:beginTime,endTime:endTime,jcd:jcd,interval:interval},
							dataType:'json',
							success:function(data){
								if(data.date != null &&data.date.length > 0){
									parseData(data, series);
									//配置项参数
									var option = {
										    xAxis: {
										        name:'时间',
										        data:data.date
										    },
										    series: series
										};
									myChart.setOption(option);
								}else{
									layer.msg('查询失败！');
									return;
								}
							},
							error: function () {//请求失败处理函数
								layer.msg('查询失败！');
								return;
							}
						});
					},parseInt(interval)*1000*60);//1000为1秒钟
					//向定时器集合中添加定时器
					timeTicket.push(ticket);
					countChartNum += 1;
					console.info(countChartNum);
				}else{
					layer.msg('查询失败！');
					return;
				}
			},
			error: function () {//请求失败处理函数
				layer.msg('查询失败！');
				return;
			}
		});
	}

	function parseData(data,series){
		if(data.data1 != null && data.data1.length > 0){
			series.push({
		        name: '车道一',
		        type: 'line',
		        data: data.data1
	    	});
		}
		if(data.data2 != null && data.data2.length > 0){
			series.push({
		        name: '车道二',
		        type: 'line',
		        data: data.data2
		    });
		}
		if(data.data3 != null && data.data3.length > 0){
			series.push({
		        name: '车道三',
		        type: 'line',
		        data: data.data3
		    });
		}
		if(data.data4 != null && data.data4.length > 0){
			series.push({
		        name: '车道四',
		        type: 'line',
		        data: data.data4
		    });
		}
		if(data.data5 != null && data.data5.length > 0){
			series.push({
		        name: '车道五',
		        type: 'line',
		        data: data.data5
		    });
		}
		if(data.data6 != null && data.data6.length > 0){
			series.push({
		        name: '车道六',
		        type: 'line',
		        data: data.data6
		    });
		}
		if(data.data7 != null && data.data7.length > 0){
			series.push({
		        name: '车道七',
		        type: 'line',
		        data: data.data7
		    });
		}
		if(data.data8 != null && data.data8.length > 0){
			series.push({
		        name: '车道八',
		        type: 'line',
		        data: data.data8
		    });
		}
	}
</script>
</html>