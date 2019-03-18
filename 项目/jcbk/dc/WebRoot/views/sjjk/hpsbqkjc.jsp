<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map,java.util.*,com.dyst.base.utils.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>车牌识别情况监测</title>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp" />
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<div id="divTitle">
			<span id="spanTitle">当前位置：数据监控管理&gt;&gt;车牌识别情况监测</span>
		</div>
		<div class="content">
			<div class="content_wrap">
				<fieldset style="margin: 0px 0px 0px 0px;">
					<legend style="color:#FF3333">监测条件</legend>
					<div class="slider_body">
			            <div class="slider_selected_left">
			                <span id="jcd_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>监测点：</span>
			            </div>
			            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
			                <input class="input_select xiala" onclick="doChoseJcd();" id="jcdid1" name="jcdid1" type="text" value="==全部=="/> 
			                <input type="hidden" name="jcdid" id="jcdid" value="">
			            </div>
			        </div>
			        <div class="slider_body">
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行驶车道：</span>
						</div>
						<div id="xscd_allselect" class="slider_selected_right dropdown dropdown_all">
							<div class="input_select xiala">
								<div id="xscd_downlist" class='multi_select'>
									<input type="hidden" id="xscd_select" value=""/>
									<a class="xiala_duoxuan_a"></a>
								</div>
							</div>
						</div>
					</div>
					<div class="slider_body" style="position: relative; clear: both;">
						<div class="slider_selected_left">
					    	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询类型：</span>
						</div>
					    <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
					    	<c:choose>
				            	<c:when test="${empty cxlb }">
					         		<input class="input_select xiala cxlb" type="text" id="xiala" readonly="readonly" value="按天查询"/>
				            	</c:when>
					            <c:otherwise>
						        	<c:forEach items="${cxlxMap }" var="s">
						        		<c:if test="${s.key eq cxlb }">
					            			<input class="input_select xiala cxlb" type="text" id="xiala" readonly="readonly" value="${s.value }"/>
						      			</c:if>
						            </c:forEach>
					            </c:otherwise>
				            </c:choose>
				            <input type="hidden" id="cxlb" name="cxlb" value="${cxlb }"/>
				            <div class="ul"> 
					       		<div <c:if test="${cxlb eq '1'}">id="queryByDay"</c:if> class="li xiala_div" data-value="1" onclick="sliders(this)">按天查询</div> 
						        <div <c:if test="${cxlb eq '2'}">id="queryByDay"</c:if> class="li xiala_div" data-value="2" onclick="sliders(this)">按月查询</div> 
						      	<div <c:if test="${cxlb eq '3'}">id="queryByDay"</c:if> class="li xiala_div" data-value="3" onclick="sliders(this)">自定义查询</div>
				            </div>
				        </div>
				    </div>
					<div class="slider_body cxlb_style1" <c:if test="${cxlb ne '1'}">style="display:none;"</c:if>>
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作日期：</span>
						</div>
						<div class="slider_selected_right">
							<div class="demolist" style="">
								<input class="inline laydate-icon" id="cxrq" name="cxrq" value="${cxrq }" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" readonly="readonly"/>
							</div>
						</div>
					</div>
					<div class="slider_body cxlb_style2" <c:if test="${cxlb ne '2'}">style="display:none;"</c:if>>
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作月份：</span>
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
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作时间：</span>
						</div>
						<div class="slider_selected_right">
							<div class="demolist" style="">
								<input class="inline laydate-icon" id="kssj" name="kssj" value="${kssj }" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly="readonly"/>
							</div>
						</div>
					</div>
					<div class="slider_body cxlb_style3" <c:if test="${cxlb ne '3'}">style="display:none;"</c:if>>
						<div class="slider_selected_left">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至</span>
						</div>
						<div class="slider_selected_right">
							<div class="demolist" style="">
								<input class="inline laydate-icon" id="jssj" name="jssj" value="${jssj }" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly="readonly"/>
							</div>
						</div>
					</div>
					<div class="button_wrap clear_both" style="height: 50px;">
						<input id="query_button" name="query_button" type="button" class="button_blue" value="查询" onclick="doSearch();">
						<input id="export_button" name="export_button" type="button" class="button_blue" value="导出Excel" onclick="doExport();">
					</div>
				</fieldset>
				<fieldset style="margin: 20px 0px 0px 0px;">
			      	<legend style=" color:#FF3333">监测结果</legend>
					<div id="dataDiv" class="pg_result">
			        
			        </div>
				</fieldset>
				<form name="form" action="" method="post"></form>
			</div>
		</div>
		<jsp:include page="/common/Foot.jsp"/>
	</body>
		<script type="text/javascript" src="<%=basePath%>common/js/sb/simplefoucs.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
		<script type="text/javascript" src="<%=basePath%>dwr/engine.js"></script>
		<script type="text/javascript" src="<%=basePath%>dwr/util.js"></script>
		<script type="text/javascript" src="<%=basePath%>dwr/interface/pushMessageCompont.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
		<script type="text/javascript">
			//数据字典
	    	var dicJson;
			$(function(){
				dicJson = $.parseJSON('${dicJson}');
			
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
				
				//行驶车道
				var value = []; 
				var data = [];
				for(var i=dicJson.length-1;i >= 0;i--){
					if(dicJson[i].typeCode == 'CD'){
						value.push(dicJson[i].typeSerialNo);
						data.push(dicJson[i].typeDesc);
					}
				}
				$('#xscd_downlist').MSDL({
					'value': value,
				    'data': data
				});
				$("#xscd_select").val("");
				
				//车牌号提示tip
			    showTip("#jcd_span", "为了减少查询时间，每次查询请尽可能选择少量监测点（最大限制为1200个）。");
			});
			
			//显示车牌号码输入提示
			function showTip(id, message){
			    $(id).mouseover(function(){
			    	layer.open({
			           type: 4,
			           shade: 0,
			           time:16000,
			           closeBtn: 0,
			           tips: [3, '#758a99'],
			           content: [message, id]
			    	});
				});
			    $(id).mouseleave(function(){
					layer.closeAll('tips');
				});
			}
			
			//监控
			function doSearch() {
				//判断时间
				if(!changeTime()){
					return;
				}
				
				//判断监测点
				var jcdStrs = $.trim($("#jcdid").val());
				if(jcdStrs == ""){
					alert("请先选择监测点！");
					return;
				}
				//限制点数1200
				var jcdArr = jcdStrs.split(",");
				if(jcdArr.length > 1200){
					alert("选择的监测点不能超过1200个，请重新选择！");
					return;
				}
				
				//时间
				var kssj = $("#kssj").val();
				var jssj = $("#jssj").val();
				
				//车道
				var cd = $.trim($("#xscd_select").val()).replace(';', ',');
				
				//执行
				//显示进度条
				var index = layer.load();
				
				//提交
				$.ajax({
					url:'<%=basePath%>/sjjk/hpsbqkjc.do?' + new Date().getTime(),
					method:"post",
					data:{kssj:kssj, jssj:jssj, jcd:jcdStrs, cd:cd},
					success:function(data){
						//关闭进度条
					    layer.close(index);
						$('#dataDiv').html(data);
					},
					error: function () {//请求失败处理函数
						//关闭进度条
					    layer.close(index);
						alert('查询失败！');
					}
				});
			}
			
			//导出excel
			function doExport(){
				if($("#table1 tbody tr[id='tr_result']").length >= 1){
					var url = "${pageContext.request.contextPath}/sjjk/exportHpsbqkjcExcel.do";
					document.forms[0].action = url;
					document.forms[0].submit();
				} else{
					layer.confirm('监测结果为空，无法导出！',{btn:['确定']});
				}
			}
		
			//根据不同选择类型填充时间
			function changeTime(){
				var cxlb = $("#cxlb").val();
				if(cxlb == "1"){//按天
					var day = $("#cxrq").val();
					if(day != null && day != ""){
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
							$("#errSpan").text("错误提示：时间选择错误，不可超过当前日期！");
							return false;
						}
					}
					return true;
				} else if(cxlb == "2"){//按月
					var year = $("#year").val();
					var month = $("#month").val();
					if(year != null && year != "" && month != null && month != ""){
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
							$("#errSpan").text("错误提示：时间选择错误，不可超过当前月份！");
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
							$("#errSpan").text("错误提示：时间选择错误，起始时间不可大于截止时间！");
							return false;
						}
						if(!checkTime(kssj, now)){
							$("#errSpan").text("错误提示：时间选择错误，起始时间不可大于当前时间！");
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