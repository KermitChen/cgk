<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>漫游轨迹查询</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="缉查,布控,缉查布控,缉查布控系统">
		<meta http-equiv="description" content="深圳市公安局缉查布控系统-漫游轨迹查询">
		
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript">
			window.moveTo(0, 0);//最大化打开窗体
    		window.resizeTo(screen.width, screen.height);
    		
			$(document).ready(function(){
				//城市
				var value = []; 
				var data = [];
				var dicJson = jQuery.parseJSON('${dicJson}');
				for(var i=0;i < dicJson.length;i++){
					if(dicJson[i].typeCode == 'MYGJDZ'){
						value.push(dicJson[i].typeSerialNo);
						data.push(dicJson[i].typeDesc);
					}
				}
				$('#city_downlist').MSDL({
					'value': value,
				    'data': data
				});
			});
		</script>
  	</head>
  
  	<body>
    	<jsp:include page="/common/Head.jsp"/>
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<div id="divTitle">
			<span id="spanTitle">当前位置：车辆综合查询&gt;&gt;漫游轨迹查询</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
	    		<fieldset style="margin: 0px 0px 0px 0px;">
					<legend style="color:#FF3333">查询条件</legend>
		    		<div class="slider_body">
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌号码：</span>
						</div>
			            <div class="slider_selected_right">
							<div class="img_wrap">
								<div class="select_wrap input_wrap_select">
									<input id="cphid" name="hphm" type="text" class="slider_input" onfocus="showTip();">
									<a class="empty" href="javascript:doCplrUI()"></a>
								</div>
							</div>  
						</div>
			        </div>
					<div class="slider_body">
		                <div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌颜色：</span>
						</div>
		                <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
						    <input class="input_select xiala" id="cpxl_downlist" readonly="readonly" type="text" value="==请选择=="/>
						    <input type="hidden" id="cplx_select" name="property" value=""/>
						    <div class="ul">
						    	<div id="cplx_div" class="li" data-value="" onclick="sliders(this)">==请选择==</div>
						       	<c:forEach items="${dicList}" var="dic">
						       		<c:if test="${dic['typeCode'] eq '0002'}">
				                    	<div class="li" data-value="${dic['typeSerialNo']}" onclick="sliders(this)">${dic['typeDesc']}</div>
			                    	</c:if>
						 		</c:forEach>
						    </div>
						</div>
		        	</div>
					<div class="slider_body">
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>城市：</span>
						</div>
						<div id="dropdown_quanxuan" class="slider_selected_right dropdown dropdown_all">
							<div class="input_select xiala">
								<div id="city_downlist" class='multi_select'>
									<input type="hidden" id="city_select"/>
									<a class="xiala_duoxuan_a"></a>
								</div>
							</div>
						</div>
					</div>
					<div class="slider_body">
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询类型：</span>
			            </div>
			            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			            	<input class="input_select xiala cxlb" type="text" id="xiala" readonly="readonly" value="按天查询"/>
		               		<input type="hidden" id="cxlb" name="cxlb" value="${cxlb }"/>
		                	<div class="ul"> 
			                    <div id="queryByDay" class="li xiala_div" data-value="1" onclick="sliders(this)">按天查询</div> 
			                    <div class="li xiala_div" data-value="2" onclick="sliders(this)">按月查询</div> 
			                    <div class="li xiala_div" data-value="3" onclick="sliders(this)">自定义查询</div> 
		                	</div> 
		            	</div>
		        	</div>
					<div class="slider_body cxlb_style1" <c:if test="${cxlb ne '1'}">style="display:none;"</c:if>>
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;">*</span>查询日期：</span>
						</div>
				 		<div class="slider_selected_right">
				  			<div class="demolist" style="">
				    			<input class="inline laydate-icon" id="cxrq" name="cxrq" value="${cxrq }" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
				  			</div>
						</div>
					</div>
					<div class="slider_body cxlb_style2" <c:if test="${cxlb ne '2'}">style="display:none;"</c:if>>
				  		<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;">*</span>月份选择：</span>
				  		</div>
				  		<div class="slider_selected_right dropdown dropdowns little_slider_selected_left" id="cp_2" onclick="slider(this)">
		                	<input class="input_select little_xiala" id="xiala1" readonly="readonly" style="text-align: center;" type="text" value="${year }"/> 
		               	 	<input type="hidden" id="year" name="year" value="${year }" />
		                	<div class="ul"> 
		                		<%
		                			for(int i=Integer.parseInt((String)request.getAttribute("year"));i >= 2014;i--){
		                		%>
			                    		<div class="li" data-value="<%=i %>" onclick="sliders(this)"><%=i %></div>
			                    <%
			                    	}
			                    %>
		              	  	</div> 
	           	 		</div>
	           	 		<div class="font_year">年</div>
	           	 		<div class="slider_selected_right dropdown dropdowns little_slider_selected_right" id="cp_2" onclick="slider(this)">
		                	<input class="input_select little_xiala" id="xiala1" readonly="readonly" style="text-align: center;" type="text" value="${month }"/> 
		                	<input type="hidden" id="month" name="month" value="${month }" />
		                	<div class="ul">
		                		<%
		                			for(int i=1;i <= 12;i++){
		                		%>
			                    		<div class="li" data-value="<%=i >= 10? i : "0" + i%>" onclick="sliders(this)"><%=i >= 10? i : "0" + i%></div>
			                    <%
			                    	}
			                    %>
		                	</div> 
	           	 		</div>
	           	 		<div class="font_month">月</div>
					</div>
					<div class="slider_body cxlb_style3" <c:if test="${cxlb ne '3'}">style="display:none;"</c:if>>
				  		<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;">*</span>查询时间：</span>
				  		</div>
						<div class="slider_selected_right">
					  		<div class="demolist" style="">
					    		<input class="inline laydate-icon" id="kssj" name="kssj" value="${kssj }" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
					  		</div>
						</div>
					</div>
					<div class="slider_body cxlb_style3" <c:if test="${cxlb ne '3'}">style="display:none;"</c:if>>
				  		<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至</span>
				  		</div>
						<div class="slider_selected_right">
					  		<div class="demolist" style="">
					    		<input class="inline laydate-icon" id="jssj" name="jssj" value="${jssj }" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
					  		</div>
						</div>
					</div>
			        <div class="button_wrap clear_both" style="height: 60px;">
				        <div class="slider_body show_style_control">
		           			&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="ck1" name="showStyle" value="1" checked="checked"/>详情&nbsp;&nbsp;
							<input type="radio" id="ck2" name="showStyle" value="2"/>列表&nbsp;&nbsp;
							<input type="radio" id="ck2" name="showStyle" value="3"/>图片
				    	</div>
				    	<input id="searchBt" type="button" class="button_blue" value="查询">
				    	<input id="resetBt" type="button" class="button_blue" value="重置">
				    </div>
	        	</fieldset>
	        	<fieldset style="margin: 20px 0px 0px 0px;">
			      	<legend style="color:#FF3333">查询结果（注：双击记录可查看图片）</legend>
			        <div id="dataDiv" class="pg_result">
			        	
			        </div>
			   </fieldset>
	    	</div>
	    </div>
	    <jsp:include page="/common/Foot.jsp"/>
	    <input type="hidden" id="preOrNext" value="nextPage"/>
  	</body>
  	<script type="text/javascript" src="<%=basePath%>common/js/sb/simplefoucs.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
  	<script type="text/javascript">
  	    //1翻页查询 2翻页查询
  	    var pageQuery = "1";
  	    
  		//根据页号查询
		function doGoPage(pageNo) {
		    //检查并组装时间
			if(!changeTime()){
				return;
			}
				
			//获取条件
			var cphidStr = $.trim($("#cphid").val());//车牌号码
			var cpysStr = $.trim($("#cplx_select").val());//车牌颜色
			var cityStr = $.trim($("#city_select").val());//城市
			var startTime = $.trim($("#kssj").val());//起始时间
			var endTime = $.trim($("#jssj").val());//截止时间
			var showStyle = $.trim($("input[name='showStyle']:checked").val());//显示方式
			if(cityStr == null || cityStr == ""){
				alert("请先选择城市！");
				return;
			}
			
			//显示进度条
			var index = layer.load();
			
			//提交
			$.ajax({
				url:'<%=basePath%>/clcx/getRoamOrbits.do?' + new Date().getTime(),
				method:"post",
				data:{cphid:cphidStr, cpys:cpysStr, city:cityStr, startTime:startTime, endTime:endTime, showStyle:showStyle, pageQuery:pageQuery, pageNo:pageNo},
				success:function(data){
				    //关闭进度条
				    layer.close(index);
				    
				    //默认翻页
				    pageQuery = "1";
				    //显示结果
					$('#dataDiv').html(data);
				},
				error: function () {//请求失败处理函数
					//关闭进度条
				    layer.close(index);
				    
					alert('查询失败！');
				}
			});
		}
  		
  		//根据不同选择类型填充时间
		function changeTime(){
			var cxlb = $("#cxlb").val();
			if(cxlb == "1"){//按天
				var day = $("#cxrq").val();
				var now = new Date().Format('yyyy-MM-dd');
				var flag = compareTime(now,day);
				if(flag == '1'){
					$("#kssj").val(day + " 00:00:00");
					$("#jssj").val(day + " 23:59:59");
					return true;
				} else if(flag == '2'){
					$("#kssj").val(day + " 00:00:00");
					$("#jssj").val(new Date().Format('yyyy-MM-dd HH:mm:ss'));
					return true;
				} else{
					alert("时间选择错误，不可超过当前日期！");
					return false;
				}
			} else if(cxlb == "2"){//按月
				var year = $("#year").val();
				var month = $("#month").val();
				var now = new Date().Format('yyyy-MM-dd');
				var flag = compareTime(now,year+"-"+month);
				if(flag == '1'){
					var firstDate = new Date(year + "/" + month + "/01 00:00:00");
					var endDate = new Date(year + "/" + month + "/31 23:59:59");
					$("#kssj").val(new Date(firstDate).Format('yyyy-MM-dd HH:mm:ss'));
					$("#jssj").val(new Date(endDate).Format('yyyy-MM-dd HH:mm:ss'));
					return true;
				} else if(flag == '2'){
					var firstDate = new Date(year+"/"+month+"/01 00:00:00");
					$("#kssj").val(new Date(firstDate).Format('yyyy-MM-dd HH:mm:ss'));
					$("#jssj").val(new Date().Format('yyyy-MM-dd HH:mm:ss'));
					return true;
				} else{
					alert("时间选择错误，不可超过当前月份！");
					return false;
				}
			} else{
				var kssj = $("#kssj").val();
				var jssj = $("#jssj").val();
				var now = new Date().Format('yyyy-MM-dd HH:mm:ss');
				if(!checkTime(kssj, jssj)){
					alert("时间选择错误，起始时间不可大于截止时间！");
					return false;
				}
				if(!checkTime(kssj, now)){
					alert("时间选择错误，起始时间不可大于当前时间！");	
					return false;
				}
				if(checkTime(now, jssj)){
					$("#jssj").val(now);
				}
				return true;
			}
		}
		
		//单条过车记录
		function showSbDetail(id){
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];  
			var url = basePath + "/clcx/getRoamOrbitById.do?id=" + id;
			layer.open({
			  type: 2,
			  title:false,
			  area:['826px', '500px'],
			  closeBtn:2,
			  shadeClose:true,
			  content: url
			});
		}
		
  		$(function(){
  			$(".xiala_div").click(function(){
				var value = $(this).attr("data-value");
				if(value == "1"){
					$(".cxlb_style1").show();
					$(".cxlb_style2").hide();
					$(".cxlb_style3").hide();
				} else if(value == "2"){
					$(".cxlb_style1").hide();
					$(".cxlb_style2").show();
					$(".cxlb_style3").hide();
				} else{
					$(".cxlb_style1").hide();
					$(".cxlb_style2").hide();
					$(".cxlb_style3").show();
				}
			});
			
			//根据条件获取符合条件的数据
			$("#searchBt").click(function(){
				//非默认翻页
				pageQuery = "2";
				//
				$("#preOrNext").val("nextPage");
				//查询
				doGoPage(1);
			});
			
			//重置..按钮
			$("#resetBt").click(function() {
				$("#cphid").val("");//车牌号码
		  		$('#cplx_div').trigger("click");//车牌颜色
		  		$('#queryByDay').trigger("click");//时间类型
			});
			
			//显示方式改变
			$("input:radio[name='showStyle']").change(function(){
				doGoPage(1);
			});
  		});
	</script>
</html>