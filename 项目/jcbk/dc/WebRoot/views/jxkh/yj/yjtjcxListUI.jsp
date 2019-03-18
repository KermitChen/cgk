<%@ page language="java" import="java.util.*,com.dyst.jxkh.entities.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
	<head>
    	<base href="<%=basePath%>">
    	<title>预警统计查询</title>
    	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<style type="text/css">
			td a {
				color :#08f;
			}
			#table1 {
				border: 1px solid #000;
				align:"center";
			}
			#table1 thead{
				background-color: #d0d0d0;
			}
			#table1 tr {
				border: 1px solid #000;
			}
			#table1 td {
				border:1px solid #000;
			}
			.zj{
				background-color: #faa755;
			}
		</style>
	</head>
	<body>
    	<jsp:include page="/common/Head.jsp"/>
    	<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
    	<div id="divTitle">
			<span id="spanTitle">当前位置：绩效考核&gt;&gt;预警统计情况查询</span>
		</div>
    	<form name="form" action="" method="post">
    		<div class="content">
    			<div class="content_wrap">
					<div class="slider_body" style="position: relative; clear: both;">
						<div class="slider_selected_left">
						    <span>查询日期类型：</span>
						</div>
						<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
						    <input class="input_select xiala cxlb" type="text" id="xiala" readonly="readonly" value="按天查询"/>
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
					<div class="slider_body"> 
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>统计部门：</span>
			            </div>
						<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
						     <input class="input_select xiala" id="xiala1" type="text" readonly="readonly" value="==请选择=="/>
						     <input type="hidden" id="xiala11" name="Check_tjbm" value="${s.deptNo }" /> 
				             <div class="ul"> 
				            	<div class="li" data-value="" onclick="sliders(this)"><a rel="2">==请选择==</a></div>
				            	  <c:forEach items= "${deptsList }" var="s" >
					            	  <c:if test="${s.deptNo eq Check_tjbm }">
					            	   	<script>
					            		 	$("#xiala1").val("${s.deptName}");
					            		 	$("#xiala11").val("${s.deptNo}");
					            	   	</script>
					            	  </c:if> 
				                    <div class="li" data-value="${s.deptNo }" onclick="sliders(this)"><a rel="2">${s.deptName }</a></div> 
								 </c:forEach>
				             </div>
				         </div>
			        </div>    
			           
			        <div class="button_wrap clear_both" style="height: 40px;">
				    	<input type="button" class="button_blue" value="查询" onclick="doSearch()">
				    	<input type="button" class="button_blue" value="导出Excel" onclick="doExport()">
				    </div>
				    
			        <div class="pg_result">
			            <table id="table1">
			                <thead>
			                    <tr>
			                    	<td>报警大类</td>
		                            <td>报警类型</td>
		                            <td>报警总数</td>
		                            <td>及时签收数</td>
		                            <td>超时签收数</td>
		                            <td>未签收数</td>
		                            <td>有效数</td>
		                            <td>无效数</td>
		                            
		                            <td>已下达拦截指令数</td>
		                            <td>拦截单位及时反馈数</td>
		                            <td>拦截单位超时反馈数</td>
		                            <td>拦截单位未反馈数</td>
			                    </tr>
			                </thead>
			                <tbody>
			                	<%
			                    	List<YjtjEntity> bjtjList = (List<YjtjEntity>)request.getAttribute("yjtjList");
			                    	if(bjtjList != null && bjtjList.size() > 0){
			                    		for(int i=0;i < bjtjList.size();i++){
			                    			YjtjEntity bjtjEntity = bjtjList.get(i);
			                    			for(int j=0;j < bjtjEntity.getBklbList().size();j++){
			                    				BklbEntity bklbEntity = bjtjEntity.getBklbList().get(j);
			                    %>
			                    				<tr id="tr_result">
			                    				    <%
			                    				    	if(j == 0){
			                    				    %>
			                    							<td rowspan="<%=bjtjEntity.getBklbList().size()+1 %>" bgcolor="#D0D02F"><%=bjtjEntity.getTypeDesc() %></td>
			                    					<%
			                    						}
			                    					%>
			                    					<td bgcolor="#D0D02F"><%=bklbEntity.getTypeDesc() %></td>
			                    					<td bgcolor="#D0D02F"><%=bklbEntity.getBjzs() %></td>
			               							<td bgcolor="#D0D02F"><%=bklbEntity.getBjjsqs() %></td>
			               							<td bgcolor="#D0D02F"><%=bklbEntity.getBjcsqs() %></td>
			               							<td bgcolor="#D0D02F"><%=bklbEntity.getBjwqs() %></td>
			               							<td bgcolor="#D0D02F"><%=bklbEntity.getBjyxs() %></td>
			               							<td bgcolor="#D0D02F"><%=bklbEntity.getBjwxs() %></td>
			               							<td bgcolor="#D0D02F"><%=bklbEntity.getZlzs() %></td>
			               							<td bgcolor="#D0D02F"><%=bklbEntity.getZljsfks() %></td>
			               							<td bgcolor="#D0D02F"><%=bklbEntity.getZlwjsfks() %></td>
			               							<td bgcolor="#D0D02F"><%=bklbEntity.getZlwfks() %></td>
			               						</tr>
		                    		<%	
		                    				}
		                   	 		%>
		                   	 			<tr id="tr_result">
		                   	 				<td>小计</td>
		                   	 				<td><%=bjtjEntity.getSubTotalList().get("xj_bjzs") %></td>
		                   	 				<td><%=bjtjEntity.getSubTotalList().get("xj_bjjsqs") %></td>
		                   	 				<td><%=bjtjEntity.getSubTotalList().get("xj_bjcsqs") %></td>
		                   	 				<td><%=bjtjEntity.getSubTotalList().get("xj_bjwqs") %></td>
		                   	 				<td><%=bjtjEntity.getSubTotalList().get("xj_bjyxs") %></td>
		                   	 				<td><%=bjtjEntity.getSubTotalList().get("xj_bjwxs") %></td>
		                   	 				<td><%=bjtjEntity.getSubTotalList().get("xj_zlzs") %></td>
		                   	 				<td><%=bjtjEntity.getSubTotalList().get("xj_zljsfks") %></td>
		                   	 				<td><%=bjtjEntity.getSubTotalList().get("xj_zlwjsfks") %></td>
		                   	 				<td><%=bjtjEntity.getSubTotalList().get("xj_zlwfks") %></td>
		                   	 			</tr>
		                   	<%
		                    		}
		                    %>
		                    		<tr id="tr_result">
		                   	 			<td colspan="2" bgcolor="#999999">总计</td>
		                   	 			<td bgcolor="#999999">${total['zj_bjzs'] }</td>
		                   	 			<td bgcolor="#999999">${total['zj_bjjsqs'] }</td>
		                   	 			<td bgcolor="#999999">${total['zj_bjcsqs'] }</td>
		                   	 			<td bgcolor="#999999">${total['zj_bjwqs'] }</td>
		                   	 			<td bgcolor="#999999">${total['zj_bjyxs'] }</td>
		                   	 			<td bgcolor="#999999">${total['zj_bjwxs'] }</td>
		                   	 			<td bgcolor="#999999">${total['zj_zlzs'] }</td>
		                   	 			<td bgcolor="#999999">${total['zj_zljsfks'] }</td>
		                   	 			<td bgcolor="#999999">${total['zj_zlwjsfks'] }</td>
		                   	 			<td bgcolor="#999999">${total['zj_zlwfks'] }</td>
		                   	 		</tr>
		                    <%
		                    	}
		                    %>
			                </tbody>
			            </table>
			        </div>
				</div>
       	 	</div>
    	</form>
    	<jsp:include page="/common/Foot.jsp"/>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/simplefoucs.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
		$(function() {
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
		});
		
		//查询
		function doSearch(){
			//检查并组装时间
			if(!changeTime()){
				return;
			}
			
			if($("#xiala11").val() == null || $.trim($("#xiala11").val()) == ""){
				alert("请先选择统计部门！");
				return;
			}
			
			var list_url = "yjqs/findYjtj.do";
	  		document.forms[0].action = list_url;
			document.forms[0].submit();	
		}
		
		//导出excel
		function doExport(){
			if($("#table1 tbody tr[id='tr_result']").length>=1){
				url = "${pageContext.request.contextPath}/yjqs/exportExcelForYjtj.do";
				document.forms[0].action = url;
				document.forms[0].submit();
			} else{
				layer.confirm('查询结果为空，无法导出！',{btn:['确定']});
			}
		}
		
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
		<%
			String result = (String)request.getAttribute("result");
			if(result != null && !"1".equals(result.trim())){
				
		%>
				<SCRIPT type="text/javascript">
					$(document).ready(function(){
						alert("统计失败！");
					});
				</SCRIPT>
		<%
			}
		%>
</html>