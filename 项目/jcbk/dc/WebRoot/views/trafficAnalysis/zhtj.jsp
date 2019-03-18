<%@page import="java.util.Date,java.text.SimpleDateFormat,org.springframework.ui.Model,java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>综合数据统计</title>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp" />
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<style type="text/css">
			.pg_result{
				height:auto !important;
				height:200px;
				min-height:200px
			}
			#data_div{
				display:block;
				float:left;
				width:100%;
				height:300px;
				OVERFLOW: auto; 
			}
			td.p0{
				width:15%;
			}
			td.p1{
				width:35%;
			}
			td.p2{
				width:35%;
			}
			td.p3{
				width:15%;
			}
		</style>
		<div id="divTitle">
			<span id="spanTitle">当前位置：统计分析&gt;&gt;综合数据统计</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
		        <div class="slider_body" style="position: relative; clear: both;">
					<div class="slider_selected_left">
				    	<span><span style="color:red;">*</span>查询时间类型：</span>
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
				       		<div id="queryByDay" class="li xiala_div" data-value="1" onclick="sliders(this)">按天查询</div> 
				        	<div class="li xiala_div" data-value="2" onclick="sliders(this)">按月查询</div> 
				      		<div class="li xiala_div" data-value="3" onclick="sliders(this)">自定义查询</div> 
			            </div>
			        </div>
			    </div>
				<div class="slider_body cxlb_style1" <c:if test="${cxlb ne '1'}">style="display:none;"</c:if>>
					<div class="slider_selected_left">
						<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;visibility: hidden;">*</span>操作日期：</span>
					</div>
					<div class="slider_selected_right">
						<div class="demolist" style="">
							<input class="inline laydate-icon" id="cxrq" name="cxrq" value="${cxrq }" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
						</div>
					</div>
				</div>
				<div class="slider_body cxlb_style2" <c:if test="${cxlb ne '2'}">style="display:none;"</c:if>>
					<div class="slider_selected_left">
						<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;visibility: hidden;">*</span>操作月份：</span>
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
						<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;visibility: hidden;">*</span>操作时间：</span>
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
		                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;visibility: hidden;">*</span>统计字段：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
	            		<input class="input_select xiala" type="text" id="operatorId" readonly="readonly" value="按操作人员"/>
		                <input type="hidden" name="tjWord" id="tjWord" value="operator" />
		                <div class="ul"> 
		                    <div class="li" data-value="operator" onclick="sliders(this)">按操作人员</div>
		                    <div class="li" data-value="ip" onclick="sliders(this)">按客户端IP</div>
		                    <div class="li" data-value="operateName" onclick="sliders(this)">按操作内容</div> 
		                </div> 
		            </div>
		        </div>
		        <div class="slider_body"> 
					<div class="slider_selected_left">
		            	<span id="gltj_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;visibility: hidden;">*</span>过滤条件：</span>
		            </div>
	                <div class="slider_selected_right" style="z-index:0;">
	                    <div class="img_wrap">
	                        <div class="select_wrap select_input_wrap">
	                            <input id="gltj" class="slider_input" type="text" name="gltj" />
	                            <a id="gltj" class="empty"></a>
	                        </div>
	                    </div>  
	               </div>
		        </div>
		        <div class="button_wrap clear_both">
		        	<div class="button_div"> 
		        		<input id="query_button" name="query_button" type="button" class="button_blue" value="查询" onclick="doSubmit();">
			    		<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
					</div>
					<div>
						<span id="errSpan"  style="color:red;margin-left:auto;margin-right:auto" ></span>
			    	</div>
		        </div>
		        <div class="pg_result">
		            <table>
		                <thead>
		                    <tr>
		                        <td class="p0">序号</td>
		                        <td class="p1">功能分类</td>
		                        <td class="p2">操作人员</td>
		                        <td class="p3">操作次数</td>
		                    </tr>
		                </thead>
		            </table>
	            	<div id="data_div">
			            <table>
			            	<tbody id="tbody">
			                
			                </tbody>
			            </table>
	                </div>
		        </div>
	        </div>
	    </div>
	    <jsp:include page="/common/Foot.jsp" />
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript">
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
				
			$("#gltj_span").mouseover(function(){
				layer.open({
			           type: 4,
			           shade: 0,
			           time:8000,
			           closeBtn: 0,
			           tips: [3, '#758a99'],
			           content: ['输入和统计字段相统一的过滤条件,支持模糊过滤,？代表一个字符,*代表多个字符.','#gltj_span']
			    });
			});
			$("#gltj_span").mouseleave(function(){
				layer.closeAll('tips');
			});
			
			$(".li").click(function(){
				var val = $(this).attr('data-value');
				if(val == 'ip'){
					$("table thead tr td:eq(2)").text('客户端IP');
				}else if(val == 'operator'){
					$("table thead tr td:eq(2)").text('操作人员');
				}else if(val == 'operateName'){
					$("table thead tr td:eq(2)").text('操作内容');
				}
			});
		});
		
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
		
		//提交表单
		function doSubmit(){
			if(!changeTime()){
				return;
			}
				
			if(!commonCheck(false,true)){//通用验证
				return;
			}
			
			var kssj = $.trim($("#kssj").val());
			var jssj = $.trim($("#jssj").val());
			var tjWord = $.trim($("#tjWord").val());
			var ip = '';
			var operator = '';
			var operateName = '';
			if(tjWord == 'ip'){
				ip = $.trim($("#gltj").val());
				$("table thead tr td:eq(2)").text('客户端IP');
			} else if(tjWord == 'operator'){
				operator = $.trim($("#gltj").val());
				$("table thead tr td:eq(2)").text('操作人员');
			} else if(tjWord == 'operateName'){
				operateName = $.trim($("#gltj").val());
				$("table thead tr td:eq(2)").text('操作内容');
			}
			
			layer.load();
			$.ajax({
				async:true,
				method:'post',
				data:{kssj:kssj,jssj:jssj,tjWord:tjWord,ip:ip,operator:operator,operateName:operateName},
				dataType:'json',
				url:'<%=basePath%>/logAgg/tjBusLog.do',
				success:function(data){
					layer.closeAll('loading');
					console.log(data);
					if(data != null && data != '' && data.length > 0){
						$("#tbody").html('');
						addData(data);
					} else{
						layer.msg('查询失败！');
					}
				},
				error:function(){
					layer.closeAll('loading');
					layer.msg('查询失败！');
				}
			});
		}
		
		//动态添加data
		function addData(data){
			var str;
			for(i=0;i<data.length;i++){
				str = "<tr><td class='p0'>"+(i+1)+"</td><td class='p1'>"+data[i].moduleName+"</td><td class='p2'>"+data[i].tjWord+"</td><td class='p3'>"+data[i].count+"</td></tr>";
				$("#tbody").append(str);
			}
		}
		
		function doReset(){
			$("#errSpan").text("");
			$("#operatorId").val('按操作人员');
			$("#tjWord").val('operator');
			$("#gltj").val('');
		}
	</script>
</html>