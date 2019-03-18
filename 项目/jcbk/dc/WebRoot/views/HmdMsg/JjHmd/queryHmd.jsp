<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <title>红名单查询</title>
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
    <link href="<%=basePath%>common/js/activiti/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
	<script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
	<script  src="<%=basePath%>common/js/layer/layer.js"></script>
	<style type="text/css">
		td a {
			color :#08f;
		}
	</style>
</head>
<body>
	<jsp:include page="/common/Head.jsp"/>
	<div id="divTitle">
		<span id="spanTitle">当前位置：红名单管理&gt;&gt;红名单查询</span>
	</div>
    <form name="form" action="" method="post">
    <div class="content">
    	<div class="content_wrap">
	        <div class="slider_body"> 
				<div class="slider_selected_left">
	            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌号码：</span>
	            </div>
                <div class="slider_selected_right" >
                    <div class="img_wrap">
                        <div class="select_wrap input_wrap_select">
                            <input id="cphid" class="slider_input" type="text" value="${Check_cpNo }" name="Check_cpNo" />
                            <a class="empty" href="javascript:doCplrUI()"></a>
                        </div>
                    </div>  
            	</div>
	        </div>
	        
	        <div class="slider_body">
		        <div class="slider_selected_left">
		            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车辆类型：</span>
		        </div>
				<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			     <input class="input_select xiala" id="xiala2" type="text" readonly="readonly" value="==请选择=="/>
			     <input type="hidden" id="xiala22" name="Check_cllx" value="${c.typeSerialNo }" /> 
		            <div class="ul"> 
		            	  <div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
		            	  <c:forEach items= "${cpysList }" var="c">
		            	  <c:if test="${c.typeSerialNo eq Check_cllx }">
		            	   	<script>
		            		 	$("#xiala2").val("${c.typeDesc }");
		            		 	$("#xiala22").val("${c.typeSerialNo }");
		            	   	</script>
		            	  </c:if> 
		                    <div class="li" data-value="${c.typeSerialNo }" onclick="sliders(this)"><a rel="2">${c.typeDesc }</a></div> 
						 </c:forEach>
		            </div>
	        	</div>
	        </div>
	        
	        <div class="slider_body">
	        	<div class="slider_selected_left">
	                 <span>&nbsp;&nbsp;&nbsp;&nbsp;车辆使用人：</span>
	            </div>
                <div class="slider_selected_right" >
                    <div class="img_wrap">
                        <div class="select_wrap select_input_wrap">
                            <input id="Check_clsyr" class="slider_input" type="text" value="${Check_clsyr }" name="Check_clsyr" />
                            <a id="Check_clsyr" class="empty"></a>
                        </div>
                    </div>  
               </div>
	        </div>
	        
	        <div class="slider_body"> 
	            <div class="slider_selected_left">
	                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;记录状态：</span>
	            </div>
				<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			     	<input class="input_select xiala" id="xiala1" type="text" readonly="readonly" value="==全部=="/>
			     	<input type="hidden" id="xiala11" name="Check_jlzt" value="${s.typeSerialNo }" /> 
	            	<div class="ul"> 
	            		<div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
	            	  	<c:forEach items= "${jlztList }" var="s">
		            	  	<c:if test="${s.typeSerialNo eq Check_jlzt }">
		            	   		<script>
		            		 		$("#xiala1").val("${s.typeDesc}");
		            		 		$("#xiala11").val("${s.typeSerialNo}");
		            	   		</script>
		            	  	</c:if> 
		                    <div class="li" data-value="${s.typeSerialNo }" onclick="sliders(this)"><a rel="2">${s.typeDesc }</a></div> 
					 	</c:forEach>
	            	</div>
	         	</div>
	         </div>
	
	        <div class="button_wrap clear_both">
		    	<input type="button" class="button_blue" value="查询" onclick="doSearch()">
		    	<input type="button" class="button_blue" value="重置" onclick="doReset()">
		    	<input type="button" class="button_blue" value="导出Excel" onclick="doExport()">
		    </div>
		    
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
                            <td align="center">序号</td>
                            <td align="center">车牌号码</td>
                            <td align="center">车辆类型</td>
                            <td align="center">车辆使用人</td>
                            <td align="center">申请人</td>
                            <td align="center">申请时间</td>
                            <td align="center">记录状态</td>
                            <td align="center">操作</td>
	                    </tr>
	                </thead>
	                <tbody>
                        <c:forEach var="r" items="${pageResult.items }" varStatus="status">
                        	<tr>
                        		<td>${status.index+1} </td>
                        		<td>${r.cphid }</td>
                        		<td>
                        			<c:forEach items="${cpysList }" var="c">
                        				<c:if test="${c.typeSerialNo eq  r.cplx }">${c.typeDesc }</c:if>
                        			</c:forEach>
                        		</td>
                        		<td>${r.clsyz }</td>
                        		<td>${r.sqrxm }</td>
                        		<td><fmt:formatDate value="${r.lrsj }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        		<td>
                        		    <c:forEach items="${jlztList }" var="c">
                        				<c:if test="${c.typeSerialNo eq  r.rwzt }">${c.typeDesc }</c:if>
                        			</c:forEach>
                        		</td>
                        		<td>
                        			<a href="javascript:doDetail(${r.id })">详情</a>
                        		</td>
                        	</tr>
                        </c:forEach>
	                </tbody>
	            </table>
				<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
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
<script type="text/javascript">
	//导出excel
	function doExport(){
		var url = "${pageContext.request.contextPath}/JjHmd/exportHmdToExcel.do";
		document.forms[0].action = url;
		document.forms[0].submit();
	}
	
	//查看详情弹出层
	function doDetail(id){
		var url = "${pageContext.request.contextPath}/JjHmd/getDetail.do?id="+id;
	    layer.open({
	           type: 2,
	           title: '红名单详情',
	           shadeClose: false,
	           shade: 0,
	           area: ['800px', '600px'],
	           content: url //iframe的url
	       }); 
	}
</script>
<script>
	var list_url = "JjHmd/queryHmd.do";
	//搜索..按钮
	function doSearch(){
	  	//重置页号(两种写法DOM,JQuery)
	  	//document.getElementById("pageNo").value=1;
	  	$("#pageNo").val(1);
	  	document.forms[0].action = list_url;
	  	document.forms[0].submit();	  		
	}

  	//重置..按钮
  	function doReset(){
  		$("#cphid").val("");
  		$("#Check_clsyr").val("");
  		$("#xiala1").val("==全部==");
  		$("#xiala11").val("");
  		$("#xiala2").val("==全部==");
  		$("#xiala22").val("");
  		$("#Check_startTime").val("");
  		$("#Check_endTime").val("");
  	}
 
	//根据页号查询...
	function doGoPage(pageNo) {
		document.getElementById("pageNo").value = pageNo;
		document.forms[0].action = list_url;
		document.forms[0].submit();
	}
</script>
</html>