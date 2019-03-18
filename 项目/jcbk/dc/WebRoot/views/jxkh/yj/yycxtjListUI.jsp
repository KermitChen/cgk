<%@page language="java" import="java.util.*,com.dyst.BaseDataMsg.entities.Dictionary,com.dyst.jxkh.entities.*,com.dyst.dispatched.entities.*,com.dyst.earlyWarning.entities.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
	<head>
	    <base href="<%=basePath%>">
	    <title>应用成效统计</title>
	    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
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
			.beizhu{
				background-color: #fff;
				text-align: left;
			}
		</style>
		<style type="text/css">
			.STYLE1 {
				font-family: "宋体";
				font-weight: bold;
				font-size:17px;
			}
			.STYLE2 {
				font-family: "宋体";
				font-weight: bold;
				font-size:14px;
				text-align:center;
				
			}
			.STYLE3 {
				font-family: "宋体";
				font-size:14px;
				text-align:center;	 
				background-color:#d0d0d0;
			}
			.STYLE4 {
				font-family: "宋体";
				font-size:14px;
				text-align:center;	 
				height: 28px;
			}
		</style>
	</head>
	<body>
    	<jsp:include page="/common/Head.jsp"/>
    	<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
	    <div id="divTitle">
			<span id="spanTitle">当前位置：绩效考核&gt;&gt;应用成效统计</span>
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
			        <div class="button_wrap clear_both" style="height: 90px;">
				    	<input type="button" class="button_blue" value="查询" onclick="doSearch()">
				    	<input type="button" class="button_blue" value="导出Excel" onclick="doExport()">
				    </div>
			        <div class="pg_result">
			        	<%
			        		List listss = (List) request.getAttribute("listss");
	    					List listsh = (List) request.getAttribute("listsh");
		    				List listwf = (List) request.getAttribute("listwf");
		    				List<Dictionary> dicList = (List<Dictionary>)request.getAttribute("dicList");
		    				if(listss != null && listsh != null && listwf != null){
		       					int inRow = (listss.size() != 0 ?listss.size():2) + (listsh.size() != 0 ?listsh.size():2) + (listwf.size() != 0 ?listwf.size():2) + 3;//行合并总数;
						%>
			            <table id="table1">
			                <tr id="tr_result">
							    <td width="5%" rowspan="<%=inRow %>">
								    <div align="center">
								      <p class="STYLE1">成</p>
								      <p class="STYLE1">效</p>
								      <p class="STYLE1">类</p>
								      <p class="STYLE1">别</p>
								    </div>	
							    </td>
							    <td width="6%" rowspan="<%=(listss.size() != 0 ?listss.size():2) + 1 %>">
									<p class="STYLE2">实时</p>
							      	<p class="STYLE2">拦截</p>
							      	<p class="STYLE2">涉案</p>
							      	<p class="STYLE2">车</p>
							    </td>
							    <td width="5%" class="STYLE3">序号</td>
							    <td width="8%" class="STYLE3">车辆牌号</td>
							    <td width="12%" class="STYLE3">布控时间</td>
							    <td width="12%" class="STYLE3">预警时间</td>
							    <td width="14%" class="STYLE3">预警卡口名称</td>
							    <td width="12%" class="STYLE3">查获单位</td>
							    <td width="10%" class="STYLE3">目标车辆属性</td>
							    <td width="8%" class="STYLE3">抓获嫌疑人数</td>
							    <td width="8%" class="STYLE3">破获案件数</td>
					  		</tr>
					  	<%
					  		for(int i=0;i < listss.size();i++){
					    		Object[] obj = (Object[])listss.get(i);
         						Dispatched dispatched = (Dispatched)obj[0];
         						InstructionSign inSign = (InstructionSign)obj[1];
					  	%>
							  	<tr class="STYLE4">
							    	<td><%=i+1%></td>
							    	<td><%=dispatched.getHphm() %></td>
							    	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="<%=dispatched.getBksj() %>" /></td>
							    	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="<%=inSign.getInstruction().getEwrecieve().getBjsj() %>" /></td>
							    	<td><%=inSign.getInstruction().getEwrecieve().getJcdmc() %></td>
							    	<td><%=inSign.getFkbmmc() %></td>
							    	<td>
							    		<%
							    			for(int j=0;j < dicList.size();j++){
							    				Dictionary dic = dicList.get(j);
							    				if(dic.getTypeCode() != null && dic.getTypeSerialNo() != null 
							    					&& "BKDL1".equals(dic.getTypeCode()) && dic.getTypeSerialNo().trim().equals(dispatched.getBjlx())){
							    		%>	
							    					<%=dic.getTypeDesc() %>
							    		<%			
							    				}
							    			}
							    		%>
							    	</td>
							    	<td><%=inSign.getZhrs() %></td>
							    	<td><%=inSign.getPhajs() %></td>
							  	</tr>
					  	<%
					  		}
					  		
					  		//空行
					  		if(listss.size() == 0){
					  	%>
					  			<tr class="STYLE4">
							    	<td>1</td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							  	</tr>
							  	<tr class="STYLE4">
							    	<td>2</td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							  	</tr>
					  	<%
					  		}
					  	%>
					  		<tr>
					  			<td rowspan="<%=(listsh.size() != 0 ?listsh.size():2) + 1%>"> 
								  	<p class="STYLE2">事后</p>
								  	<p class="STYLE2">查获</p>
								  	<p class="STYLE2">涉案</p>
								  	<p class="STYLE2">车</p>
								</td>
							    <td class="STYLE3">序号</td>
							    <td class="STYLE3">车辆牌号</td>
							    <td class="STYLE3">布控时间</td>
							    <td class="STYLE3">查获时间</td>
							    <td class="STYLE3">获取关键线索卡口</td>
							    <td class="STYLE3">查获单位</td>
							    <td class="STYLE3">目标车辆属性</td>
							    <td class="STYLE3">抓获嫌疑人数</td>
							    <td class="STYLE3">破获案件数</td>
					  		</tr>
					   	<%
					  		for(int i=0;i < listsh.size();i++){
					    		Object[] obj = (Object[])listsh.get(i);
         						Dispatched dispatched = (Dispatched)obj[0];
         						InstructionSign inSign = (InstructionSign)obj[1];
					  	%>
							  	<tr class="STYLE4">
							    	<td><%=i+1%></td>
							    	<td><%=dispatched.getHphm() %></td>
							    	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="<%=dispatched.getBksj() %>" /></td>
							    	<td><%=inSign.getChsj() %></td>
							    	<td><%=inSign.getChdd() %></td>
							    	<td><%=inSign.getFkbmmc() %></td>
							    	<td>
							    		<%
							    			for(int j=0;j < dicList.size();j++){
							    				Dictionary dic = dicList.get(j);
							    				if(dic.getTypeCode() != null && dic.getTypeSerialNo() != null 
							    					&& "BKDL1".equals(dic.getTypeCode()) && dic.getTypeSerialNo().trim().equals(dispatched.getBjlx())){
							    		%>	
							    					<%=dic.getTypeDesc() %>
							    		<%			
							    				}
							    			}
							    		%>
							    	</td>
							    	<td><%=inSign.getZhrs() %></td>
							    	<td><%=inSign.getPhajs() %></td>
							  	</tr>
					  	<%
					  		}
					  		
					  		//空行
					  		if(listsh.size() == 0){
					  	%>
					  			<tr class="STYLE4">
							    	<td>1</td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							  	</tr>
							  	<tr class="STYLE4">
							    	<td>2</td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							  	</tr>
					  	<%
					  		}
					  	%>
					  		<tr>
					    		<td rowspan="<%=(listwf.size() != 0 ?listwf.size():2) + 1%>">
						  			<p class="STYLE2">查获</p>
					      			<p class="STYLE2">交通</p>
					      			<p class="STYLE2">违法</p>
					      			<p class="STYLE2">车</p>
						      	</td>
							    <td class="STYLE3">序号</td>
							    <td class="STYLE3">车辆牌号</td>
							    <td class="STYLE3">布控时间</td>
							    <td class="STYLE3">查获时间</td>
							    <td class="STYLE3">获取关键线索卡口</td>
							    <td class="STYLE3">查获单位</td>
							    <td class="STYLE3">目标车辆属性</td>
							    <td class="STYLE3">抓获嫌疑人数</td>
							    <td class="STYLE3">破获案件数 </td>
					  		</tr>
					  	<%
					  		for(int i=0;i < listwf.size();i++){
					    		Object[] obj = (Object[])listwf.get(i);
         						Dispatched dispatched = (Dispatched)obj[0];
         						InstructionSign inSign = (InstructionSign)obj[1];
					  	%>
							  	<tr class="STYLE4">
							    	<td><%=i+1%></td>
							    	<td><%=dispatched.getHphm() %></td>
							    	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="<%=dispatched.getBksj() %>" /></td>
							    	<td><%=inSign.getChsj() %></td>
							    	<td><%=inSign.getChdd() %></td>
							    	<td><%=inSign.getFkbmmc() %></td>
							    	<td>
							    		<%
							    			for(int j=0;j < dicList.size();j++){
							    				Dictionary dic = dicList.get(j);
							    				if(dic.getTypeCode() != null && dic.getTypeSerialNo() != null 
							    					&& "BKDL2".equals(dic.getTypeCode()) && dic.getTypeSerialNo().trim().equals(dispatched.getBjlx())){
							    		%>	
							    					<%=dic.getTypeDesc() %>
							    		<%			
							    				}
							    			}
							    		%>
							    	</td>
							    	<td><%=inSign.getZhrs() %></td>
							    	<td><%=inSign.getPhajs() %></td>
							  	</tr>
					  	<%
					  		}
					  		
					  		//空行
					  		if(listwf.size() == 0){
					  	%>
					  			<tr class="STYLE4">
							    	<td>1</td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							  	</tr>
							  	<tr class="STYLE4">
							    	<td>2</td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							  	</tr>
					  	<%
					  		}
					  	%>
			            </table>
			        <%
			          	}
					%>
			        </div>
				</div>
	        </div>
	    </form>
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
	
		//点击查询按钮
		function doSearch(){
			//检查并组装时间
			if(!changeTime()){
				return;
			}
			
			var url = "yjqs/findYycxtj.do";
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	
		//导出excel
		function doExport(){
			if($("#table1 tbody tr[id='tr_result']").length>=1){
				url = "${pageContext.request.contextPath}/yjqs/exportExcelForYycx.do";
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