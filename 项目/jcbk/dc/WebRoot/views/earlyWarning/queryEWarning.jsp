<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="zh-CN">
	<head>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
		<base href="<%=basePath%>">
		<title>预警综合查询</title>
	</head>

	<body>
		<jsp:include page="/common/Head.jsp"/>
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<div id="divTitle">
			<span id="spanTitle">预警综合查询</span>
		</div>
		<div class="content">
			<div class="content_wrap">
				<form name="form" action="" method="post">
					<div class="slider_body">
		                <div class="slider_selected_left">
		                	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号牌号码：</span>
		                </div>
		                <div class="slider_selected_right" style="">
		                    <div class="img_wrap">
		                        <div class="select_wrap input_wrap_select">
		                            <input id="cphid" name="hphm" type="text" class="slider_input" maxlength="8" value="${hphm}"/>
		                            <a class="empty" href="javascript:doCplrUI()"></a>
		                        </div>
		                    </div>  
		                </div>
		        	</div>
					<div class="slider_body">
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号牌种类：</span>
			            </div>
			            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
			                <input class="input_select xiala" id="hpzl" type="text" readonly="readonly" value="==全部=="/>
			                <input type="hidden" name="hpzl" value="${hpzl}" /> 
			                <div class="ul">
							    <div class="li" data-value="" onclick="sliders(this)">==全部==</div>
							    <c:forEach items= "${dicList}" var="dic">
			            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
				                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
			            	  			<c:if test="${dic.typeSerialNo eq hpzl }">
			            	  				<script>$("#hpzl").val('${dic.typeDesc}');</script>
			            	  			</c:if>
			            	  		</c:if> 
							 	</c:forEach>
							</div>
			            </div>
			        </div>
			        <div class="slider_body">
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌类型：</span>
			            </div>
			            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
			                <input class="input_select xiala" id="cplx" type="text" readonly="readonly" value="==全部=="/>
			                <input type="hidden" name="cplx" value="${cplx}" /> 
			                <div class="ul">
							    <div class="li" data-value="" onclick="sliders(this)">==全部==</div>
							    <c:forEach items= "${dicList}" var="dic">
			            	  		<c:if test="${dic.typeCode eq '0002' }">
				                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
			            	  			<c:if test="${dic.typeSerialNo eq cplx }">
			            	  				<script>$("#cplx").val('${dic.typeDesc}');</script>
			            	  			</c:if>
			            	  		</c:if> 
							 	</c:forEach>
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
						<button class="submit_b" onclick="doSearch()">查询</button>
						<button class="submit_b" onclick="doReset()">重置</button>
					</div>
	
					<div class="pg_result">
						<table>
							<thead>
								<tr>
									<td width="80" align="center">号牌号码</td>
									<td width="80" align="center">号牌种类</td>
									<td width="80" align="center">车牌类型</td>
									<td width="80" align="center">报警大类</td>
									<td width="80" align="center">报警小类</td>
									<td width="150" align="center">监测点</td>
									<td width="80" align="center">行驶车道</td>
									<td width="120" align="center">通过时间</td>
									<td width="120" align="center">上传时间</td>
									<td width="120" align="center">报警时间</td>
									<td width="100" align="center">操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="ewarning" items="${pageResults.items }" varStatus="status">
									<tr>
										<td>${ewarning.hphm}</td>
										<td>
											<c:forEach items="${dicList}" var="dic">
						            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
							                    	<c:if test="${dic.typeSerialNo eq ewarning.hpzl }">
							                    		${dic.typeDesc }
							                    	</c:if>
						            	  		</c:if> 
										 	</c:forEach>
										</td>
										<td>
											<c:forEach items="${dicList}" var="dic">
						            	  		<c:if test="${dic.typeCode eq '0002' }">
							                    	<c:if test="${dic.typeSerialNo eq ewarning.cplx }">
							                    		${dic.typeDesc }
							                    	</c:if>
						            	  		</c:if> 
										 	</c:forEach>
										</td>
										<td style="color: green;">
											<c:forEach items= "${dicList}" var="dic">
						            	  		<c:if test="${dic.typeCode eq 'BKDL' }">
							                    	<c:if test="${dic.typeSerialNo eq ewarning.bjdl }">
							                    		${dic.typeDesc }
							                    	</c:if>
						            	  		</c:if> 
										 	</c:forEach>
										</td>
										<td style="color: green;">
											<c:if test="${ewarning.bjdl eq '1'}">
												<c:forEach items= "${dicList}" var="dic">
							            	  		<c:if test="${dic.typeCode eq 'BKDL1' }">
								                    	<c:if test="${dic.typeSerialNo eq ewarning.bjlx }">
								                    		${dic.typeDesc }
								                    	</c:if>
							            	  		</c:if> 
											 	</c:forEach>
											</c:if>
											<c:if test="${ewarning.bjdl eq '2'}">
												<c:forEach items= "${dicList}" var="dic">
							            	  		<c:if test="${dic.typeCode eq 'BKDL2' }">
								                    	<c:if test="${dic.typeSerialNo eq ewarning.bjlx }">
								                    		${dic.typeDesc }
								                    	</c:if>
							            	  		</c:if> 
											 	</c:forEach>
											</c:if>
											<c:if test="${ewarning.bjdl eq '3'}">
												<c:forEach items= "${dicList}" var="dic">
							            	  		<c:if test="${dic.typeCode eq 'BKDL3' }">
								                    	<c:if test="${dic.typeSerialNo eq ewarning.bjlx }">
								                    		${dic.typeDesc }
								                    	</c:if>
							            	  		</c:if> 
											 	</c:forEach>
											</c:if>
										</td>
										<td>${ewarning.jcdmc}</td>
										<td>${ewarning.cdid}</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewarning.tgsj}" /></td>
										<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewarning.scsj}" /></td>
										<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ewarning.bjsj}" /></td>
										<td>
											<a onclick="showEWRecievesByBjxh('${ewarning.bjxh }');" style="font-size: 15px;">详情</a>
<!-- 											|&nbsp;<a onclick="eWarningDetail('${ewarning.bjxh }');" style="font-size: 15px;">详情</a> -->
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<jsp:include page="/common/pageNavigators.jsp"></jsp:include>
					</div>
				</form>
			</div>
		</div>
		<jsp:include page="/common/Foot.jsp" />
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/MarkerClusterer_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/TextIconOverlay_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/simplefoucs.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
		var list_url = "earlyWarning/queryEWRecieveForEWarning.do";
		//搜索..按钮
		function doSearch() {
			//检查并组装时间
			if(!changeTime()){
				return;
			}
			document.getElementById("pageNo").value = 1;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		
		//重置..按钮
		function doReset() {
			$("input[name='hpzl']").val("");
			$("#hpzl").val("==全部==");
			$("input[name='cplx']").val("");
			$("#cplx").val("==全部==");
			$("input[name='hphm']").val("");

			$('#queryByDay').trigger("click");//时间类型
		  	$("#cxrq").val("");//日期
		  			
		  	//月份
		  	$("#yearShow").val("");
		  	$("#year").val("");
		  	$("#monthShow").val("");
		  	$("#month").val("");
		  			
		  	//时间范围
		  	$("#kssj").val("");
		  	$("#jssj").val("");
		}
		
		//根据页号查询
		function doGoPage(pageNo) {
			document.getElementById("pageNo").value = pageNo;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		
		function eWarningDetail(bjxh){
			var url = "${pageContext.request.contextPath}/earlyWarning/loadEWarningDetail.do?bjxh="+bjxh;
		    layer.open({
		           type: 2,
		           title: '预警详情窗口',
		           shadeClose: true,
		           shade: 0.8,
		           area: ['860px', '500px'],
		           content: url //iframe的url
		       }); 	
		}
		
		function showEWRecievesByBjxh(bjxh){
			var url = "${pageContext.request.contextPath}/earlyWarning/showEWRecievesByBjxh.do?bjxh="+bjxh;
		    var index = layer.open({
		           type: 2,
		           title: '预警签收情况窗口',
		           //shadeClose: true,
		           //shade: 0.8,
		           area: ['1180px', '500px'],
		           content: url,//iframe的url
		           maxmin: true
		       }); 	
		    layer.full(index);
		}
 
		$(document).ready(function(){
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