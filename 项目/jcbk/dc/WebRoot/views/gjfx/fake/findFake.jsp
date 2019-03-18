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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>套牌车辆查询</title>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp" />
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<div id="divTitle">
			<span id="spanTitle">当前位置：高级检索分析&gt;&gt;套牌车辆查询</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
				<form action="" id="form1">
					<div class="slider_body">
						<div class="slider_selected_left">
						    <span>查询日期类型：</span>
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
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作日期：</span>
						</div>
						<div class="slider_selected_right">
							<div class="demolist" style="">
								<input class="inline laydate-icon" id="cxrq" name="cxrq" value="${cxrq }" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
							</div>
						</div>
					</div>
					<div class="slider_body cxlb_style2" <c:if test="${cxlb ne '2'}">style="display:none;"</c:if>>
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作月份：</span>
						</div>
						<div class="slider_selected_right dropdown dropdowns little_slider_selected_left" id="cp_2" onclick="slider(this)">
					    	<input class="input_select little_xiala" id="yearShow" readonly="readonly" style="text-align: center;" type="text" value="${year }"/> 
							<input type="hidden" id="year" name="year" value="${year }"/>
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
					        <input class="input_select little_xiala" id="monthShow" readonly="readonly" style="text-align: center;" type="text" value="${month }"/> 
					        <input type="hidden" id="month" name="month" value="${month }"/>
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
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作时间：</span>
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
					<div class="slider_body" style="position: relative; clear: both;">
		                <div class="slider_selected_left">
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌号码：</span>
		                </div>
		                <div class="slider_selected_right">
		                    <div class="img_wrap">
		                        <div class="select_wrap input_wrap_select">
		                            <input id="cphid" name="hphm" type="text" class="slider_input">
		                            <a class="empty" href="javascript:doCplrUI()"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
		       	  	<div class="slider_body">
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点：</span>
			            </div>
			            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
			                <input class="input_select xiala" id="jcdid1" onclick="doChoseJcd();" type="text" value="==全部=="/> 
			                <input type="hidden" name="jcdid" id="jcdid" value="">
			            </div>
			        </div>    	        
			        <div class="slider_body">
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;道路：</span>
						</div>
						<div id="dropdown_quanxuan" class="slider_selected_right dropdown dropdown_all">
							<div class="input_select xiala">
								<div id="role_type_downlist" class='multi_select2'>
									<input type="hidden" name="roadNo" id="dlid"/>
									<a class="xiala_duoxuan_a"></a>
								</div>
							</div>
						</div>
					</div>
				</form>
				
		        <div class="button_wrap clear_both" style="height: 60px;">
			    	<input id="query_button" name="query_button" type="button" class="button_blue" value="查询">
			    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
			    	<input id="export_button" name="export_button" type="button" class="button_blue" value="导出Excel" onclick="doExport();">
			    </div>
			    
		        <div id="data_div" class="pg_result">
	
		        </div>
	        </div>
	    </div>
	    <jsp:include page="/common/Foot.jsp" />
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript">
		//根据页号查询
		function doGoPage(pageNo) {
			//检查并组装时间
			if(!changeTime()){
				return;
			}
			
			//获取条件
			var hphm = $.trim($("#cphid").val());
			var jcdid = $.trim($("#jcdid").val());//监测点id
			var kssj = $.trim($("#kssj").val());//起始时间
			var jssj = $.trim($("#jssj").val());//截止时间
			var areaNo = "";//城区id
			var roadNo = $.trim($("#dlid").val());//道路id
			
			var index = layer.load();
			//提交
			$.ajax({
				url:'<%=basePath%>fake/getFakeForPage.do?' + new Date().getTime(),
				method:"post",
				data:{hphm:hphm,jcdid:jcdid,kssj:kssj,jssj:jssj,pageNo:pageNo,areaNo:areaNo,roadNo:roadNo},
				success:function(data){
					layer.close(index);
					$('#data_div').html(data);
				},
				error: function () {//请求失败处理函数
					layer.close(index);
					layer.msg('查询失败！');
				}
			});
		}
		
		//根据id查询详细信息
		function showFakeDetail(id,index){
			var pageNo = $.trim($("#pageNo").val());
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];  
			var url = basePath + "/fake/getFakeById.do?id="+id+"&index="+index+"&pageNo="+pageNo;
			layer.open({
			  type: 2,
			  title:false,
			  area:['930px','630px'],
			  closeBtn:2,
			  shadeClose:true,
			  content: url
			});
		}
		
		$(function(){
			//时间查询方式
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
			$("#queryByDay").trigger("click");//时间类型
		
		  	//道路多选下拉
			var value2 = []; 
			var data2 = [];
		    var dicJson2 = ${roadList };
			for(var i=0;i < dicJson2.length;i++){
				value2.push(dicJson2[i].roadNo);
				data2.push(dicJson2[i].roadName);
			}
		    $('.multi_select2').MSDL({
				'value': value2,
		      	'data': data2
		    });
		    
			//根据条件获取符合条件的数据
			$("#query_button").click(function() {
				doGoPage(1);
			});
			
			//首次查询
			doGoPage(1);
		});
		
		function doReset(){
			$("#cphid").val("");
			$("#jcdid").val("");
			$("#jcdid1").val("==全部==");
			$("#dlid").val("");
			
			//刷新页面
			window.location.reload();
		}
		
		function doExport(){
			var url = "${pageContext.request.contextPath}/fake/doExportFakeHphm.do";
			document.forms[0].action = url;
			document.forms[0].submit();
		};
		
		//根据不同选择类型填充时间
		function changeTime(){
			var cxlb = $("#cxlb").val();
			if(cxlb == "1"){//按天
				var day = $("#cxrq").val();
				if(day != null && day != ""){
					var now = new Date().Format('yyyy-MM-dd');
					var flag = compareTime(now, day);
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
				}
				return true;
			} else if(cxlb == "2"){//按月
				var year = $("#year").val();
				var month = $("#month").val();
				if(year != null && year != "" && month != null && month != ""){
					var now = new Date().Format('yyyy-MM');
					var flag = compareTime(now, year + "-" +month);
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
				}
				return true;
			} else{
				var kssj = $("#kssj").val();
				var jssj = $("#jssj").val();
				if(kssj != null && kssj != "" && jssj != null && jssj != ""){
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
				}
				return true;
			}
		}
	</script>
</html>