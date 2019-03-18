<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>操作日志查询</title>
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
		</style>
		<div id="divTitle">
			<span id="spanTitle">当前位置：系统管理&gt;&gt;操作日志查询</span>
		</div>
    <div class="content">
    	<div class="content_wrap">
    	 	<form id="form1" name="form1" action="<%=basePath%>log/searchBusinessLog.do" method="post">
	        <div class="slider_body">
		        <div class="slider_selected_left">
		        	<span>操作人用户名：</span>
		        </div>
		        <div class="slider_selected_right" style="">
		        	<div class="img_wrap">
		    			<div class="select_wrap select_input_wrap">
		     				<input id="operator" name="operator" type="text" class="slider_input" value="${operator }">
		                    <a id="operator" class="empty"></a>
		                </div>
		            </div>  
		    	</div>
		    </div>
		    <div class="slider_body">
		        <div class="slider_selected_left">
		        	<span>&nbsp;&nbsp;&nbsp;&nbsp;操作人姓名：</span>
		        </div>
		        <div class="slider_selected_right" style="">
		        	<div class="img_wrap">
		    			<div class="select_wrap select_input_wrap">
		     				<input id="operaterName" name="operaterName" type="text" class="slider_input" value="${operaterName }">
		                    <a id="operaterName" class="empty"></a>
		                </div>
		            </div>  
		    	</div>
		    </div>
		    <div class="slider_body">
		        <div class="slider_selected_left">
		        	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户端IP：</span>
		        </div>
		        <div class="slider_selected_right" style="">
		        	<div class="img_wrap">
		    			<div class="select_wrap select_input_wrap">
		     				<input id="operatorIp" name="operatorIp" type="text" class="slider_input" value="${operatorIp }">
		                    <a id="operatorIp" class="empty"></a>
		                </div>
		            </div>  
		    	</div>
		    </div>
		    <div class="slider_body">
		        <div class="slider_selected_left">
		        	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;模块名称：</span>
		        </div>
		        <div class="slider_selected_right" style="">
		        	<div class="img_wrap">
		    			<div class="select_wrap select_input_wrap">
		     				<input id="moduleName" name="moduleName" type="text" class="slider_input" value="${moduleName }">
		                    <a id="moduleName" class="empty"></a>
		                </div>
		            </div>  
		    	</div>
		    </div>
		    <div class="slider_body">
		        <div class="slider_selected_left">
		        	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;模块内容：</span>
		        </div>
		        <div class="slider_selected_right" style="">
		        	<div class="img_wrap">
		    			<div class="select_wrap select_input_wrap">
		     				<input id="operateName" name="operateName" type="text" class="slider_input" value="${operateName }">
		                    <a id="operateName" class="empty"></a>
		                </div>
		            </div>  
		    	</div>
		    </div>
	        <div class="slider_body" style="position: relative; clear: both;">
				<div class="slider_selected_left">
			    	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询类型：</span>
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
			<div class="button_wrap clear_both">
		        <div class="button_div">
		        	<input id="query_button" name="query_button" type="button" class="button_blue" value="查询" onclick="doSubmit();">
			    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
		        </div>
		    	<!-- 错误信息提示 -->
				<div>
					<span id="errSpan" style="color:red;margin-left:auto;margin-right:auto" ></span>
		    	</div>
    	 	</div>
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
	                    	<td>序号</td>
	                        <td>操作人用户名</td>
	                        <td>操作人姓名</td>
	                        <td>客户端IP</td>
	                        <td>模块名称</td>
	                        <td>操作内容</td>
	                        <td>操作时间</td>
	                        <td>详情</td>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach items="${pageResult.items }" var="s" varStatus="c">
	                    	<tr>
	                    		<td>${c.index+1}</td>
		                      	<td>${s.operator }</td>
		                      	<td>${s.operatorName }</td>
		                        <td>${s.ip }</td>
		                       	<td>${s.moduleName }</td>
		                        <td>${s.operateName}</td>
		                        <td>${s.operateDate }</td>
		                        <td><a href="javascript:showLogDetail('${s.id }')">日志详情</a></td>
	                    	</tr>
	                    </c:forEach>
	                </tbody>
	            </table>
            	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
	        </div>
			</form>
        </div>
    </div>
    <jsp:include page="/common/Foot.jsp" />
</body>
		<script type="text/javascript" src="<%=basePath%>common/js/sb/simplefoucs.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
		<script type="text/javascript">
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
		
			var list_url = "<%=basePath%>log/searchBusinessLog.do";
			//根据页号查询
			function doGoPage(pageNo) {
				layer.load();
				document.getElementById("pageNo").value = pageNo;
				document.forms[0].action = list_url;
				document.forms[0].submit();
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
			});
			
			//提交表单
			function doSubmit(){
				if(!changeTime()){
					return;
				}
				if(!commonCheck(false,true)){//通用验证
					return;
				}
				layer.load();
				$("#errSpan").text("");
				$("#pageNo").val("1");
				$("#form1").submit();
			}
			
			function doReset(){
				$("#operator").val("");
				$("#operaterName").val("");
				$("#operatorIp").val("");
				$("#moduleName").val("");
				$("#operateName").val("");
				$("#errSpan").text("");
			}
			
			//查询单条日志
			function showLogDetail(id){
				var location = (window.location+'').split('/');  
				var basePath = location[0]+'//'+location[2]+'/'+location[3];  
				var url = basePath + "/log/getLogById.do?id="+id;
				layer.open({
				  type: 2,
				  title:false,
				  area:['600px','250px'],
				  closeBtn:2,
				  shadeClose:true,
				  content: url
				});
			}
		</script>
</html>