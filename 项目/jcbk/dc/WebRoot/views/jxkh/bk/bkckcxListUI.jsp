<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	    <title>布控撤控通知统计查询</title>
	    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	    <link href="<%=basePath%>common/js/activiti/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
		<style type="text/css">
			#table1 {
				border: 1px solid #000;
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
			.beizhu{
				background-color: #fff;
				text-align: left;
			}
		</style>
	</head>
	<body>
	    <jsp:include page="/common/Head.jsp"/>
	    	<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
	    <div id="divTitle">
			<span id="spanTitle">当前位置：绩效考核&gt;&gt;布控撤控通知统计查询</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
	    		<form name="form" action="" method="post">
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
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;统计部门：</span>
			            </div>
						<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
						     <input class="input_select xiala" id="xiala1" type="text" readonly="readonly" value="==全部=="/>
						     <input type="hidden" id="xiala11" name="Check_tjbm" value="${s.deptNo }" /> 
				             <div class="ul"> 
				            	<div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
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
			        <div class="button_wrap clear_both">
				    	<input type="button" class="button_blue" value="查询" onclick="doSearch()">
				    	<input type="button" class="button_blue" value="导出Excel" onclick="doExport()">
				    </div>
			    </form>
		        <div class="pg_result">
		            <table id="table1">
		                <thead>
		                    <tr>
	                            <td align="center" rowspan="2">序号</td>
	                            <td align="center" rowspan="2">单位</td>
	                            <td align="center" colspan="6">布控通知</td>
	                            <td align="center" colspan="6">撤控通知</td>
		                    </tr>
		                    <tr>
		                    	<td>布控通知总数</td>
		                    	<td>按时签收数</td>
		                    	<td>超时签收数</td>
		                    	<td>未签收数</td>
		                    	<td>总签收率</td>
		                    	<td>按时签收率</td>
		                    	
		                    	<td>撤控通知总数</td>
		                    	<td>按时签收数</td>
		                    	<td>超时签收数</td>
		                    	<td>未签收数</td>
		                    	<td>总签收率</td>
		                    	<td>按时签收率</td>
		                    </tr>
		                </thead>
		                <tbody>
		                	<c:forEach items="${bean.list }" var="b" varStatus="status">
		                		<tr id="tr_result">
		                			<td>${status.index +1 }</td>
		                			<td>${b.deptName }</td>
		                			<td><a href="javascript:getBkInfo('1', '1', '${b.deptID}');" style="text-decoration: none;">${b.bktzzs }</a></td>
		                			<td><a href="javascript:getBkInfo('1', '2', '${b.deptID}');" style="text-decoration: none;">${b.bktz_asqs }</a></td>
		                			<td><a href="javascript:getBkInfo('1', '3', '${b.deptID}');" style="text-decoration: none;">${b.bktz_csqs}</a></td>
		                			<td><a href="javascript:getBkInfo('1', '4', '${b.deptID}');" style="text-decoration: none;">${b.bktz_wqs }</a></td>
		                			<td>${b.bktz_zqsl }</td>
		                			<td>${b.bktz_asqsl }</td>
		                			
		                			<td><a href="javascript:getCkInfo('2', '1', '${b.deptID}');" style="text-decoration: none;">${b.cktzzs }</a></td>
		                			<td><a href="javascript:getCkInfo('2', '2', '${b.deptID}');" style="text-decoration: none;">${b.cktz_asqs }</a></td>
		                			<td><a href="javascript:getCkInfo('2', '3', '${b.deptID}');" style="text-decoration: none;">${b.cktz_csqs }</a></td>
		                			<td><a href="javascript:getCkInfo('2', '4', '${b.deptID}');" style="text-decoration: none;">${b.cktz_wqs }</a></td>
		                			<td>${b.cktz_zqsl }</td>
		                			<td>${b.cktz_asqsl }</td>
		                		</tr>
		                	</c:forEach>
		                	<c:set value="${bean.total }" var="map"></c:set>
		                	<tr style="background-color: #d0d0d0;">
	                        	<td class="zj" colspan="2">总计</td>
	                        	<td class="zj">${map['bktz_zs'] }</td>
	                        	<td class="zj">${map['bktz_asqs'] }</td>
	                        	<td class="zj">${map['bktz_csqs'] }</td>
	                        	<td class="zj">${map['bktz_wqs'] }</td>
	                        	<td class="zj">${map['bktz_zqsl'] }</td>
	                        	<td class="zj">${map['bktz_asqsl'] }</td>
	                        	
	                        	<td class="zj">${map['cktz_zs'] }</td>
	                        	<td class="zj">${map['cktz_asqs'] }</td>
	                        	<td class="zj">${map['cktz_csqs'] }</td>
	                        	<td class="zj">${map['cktz_wqs'] }</td>
	                        	<td class="zj">${map['cktz_zqsl'] }</td>
	                        	<td class="zj">${map['cktz_asqsl'] }</td>
	                        </tr>
	                        <tr>
	                        	<td colspan="14" class="beizhu">备注：1. 布控按时签收率=按时签收布控总数/布控指令接收总数×100%, 按时签收时间以接收到指令时间10分钟内计算.</td>
	                        </tr>
	                        <tr>
	                        	<td colspan="14" class="beizhu" style="text-indent: 35px;">2. 撤控按时签收率=按时签收撤控总数/撤控指令接收总数×100%, 按时签收时间以接收到指令时间10分钟内计算.</td>
	                        </tr>
		                </tbody>
		            </table>
		        </div>
			</div>
	    </div>
	    <jsp:include  page="/common/Foot.jsp"/>
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
		});
		
		//查询
		function doSearch(){
			//检查并组装时间
			if(!changeTime()){
				return;
			}
			
			var list_url = "bktj/findBkck.do";
	  		document.forms[0].action = list_url;
			document.forms[0].submit();	
		}
		 
		//导出excel
		function doExport(){
			var sum;
			sum = $("#table1 tbody tr[id='tr_result']").length;
			if(sum>=1){
				var url = "${pageContext.request.contextPath}/bktj/excelExportForBkck.do";
				document.forms[0].action = url;
				document.forms[0].submit();
			}else{
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
		
		//显示信息详情
	  	function getBkInfo(infodl, infoxl, jxbm){
	  		var url ="${pageContext.request.contextPath}/bktj/getBkckInfo.do?kssj=" + $("#kssj").val() + "&jssj=" + $("#jssj").val() + "&infodl=" + infodl + "&infoxl=" + infoxl + "&jxbm=" + jxbm;
			layer.open({
				type: 2,
				title: '布控通知签收情况清单',
				area:['1024px', '600px'],
				//shade: 0,
				shadeClose:true,
				content: url
			});
	  	}
	  	
	  	//显示信息详情
	  	function getCkInfo(infodl, infoxl, jxbm){
	  		var url ="${pageContext.request.contextPath}/bktj/getBkckInfo.do?kssj=" + $("#kssj").val() + "&jssj=" + $("#jssj").val() + "&infodl=" + infodl + "&infoxl=" + infoxl + "&jxbm=" + jxbm;
			layer.open({
				type: 2,
				title: '撤控通知签收情况清单',
				area:['1024px', '600px'],
				//shade: 0,
				shadeClose:true,
				content: url
			});
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