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
	<base href="<%=basePath%>">
	<title>布控撤控签收查询</title>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<link href="<%=basePath%>common/js/activiti/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
</head>

<body>
	<jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">布控撤控签收查询</span>
	</div>
	<div class="content">
		<div class="content_wrap">
			<form name="form" action="" method="post">
				<div id="cxlx_div" class="slider_body" style="position:relative;clear:both;">
		            <div class="slider_selected_left">
		                <span>布控撤控通知：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
		                <input class="input_select xiala" id="bkckbz" type="text" readonly="readonly" value="==全部=="/>
		                <input type="hidden" name="bkckbz" value="${disReceive.bkckbz}" /> 
		                <div class="ul">
		                	<div class="li" data-value="" onclick="sliders(this);">==全部==</div>
		                	<div class="li" data-value="1" onclick="sliders(this);">布控通知</div>
		                	<div class="li" data-value="2" onclick="sliders(this);">撤控通知</div>
		                	<c:if test="${disReceive.bkckbz eq '1' }">
		            	  		<script>$("#bkckbz").val('布控通知');</script>
		            	  	</c:if>
		                	<c:if test="${disReceive.bkckbz eq '2' }">
		            	  		<script>$("#bkckbz").val('撤控通知');</script>
		            	  	</c:if>
						</div>
		            </div>
		        </div>
				<div id="bklb_div" class="slider_body">
		            <div class="slider_selected_left">
		                <span>布控类别：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
		                <input class="input_select xiala" id="bklb" type="text" readonly="readonly" value="==全部=="/>
		                <input type="hidden" name="bklb" value="${disReceive.bklb}" /> 
		                <div class="ul" id="bklbUl">
							<div class="li" data-value="" onclick="sliders(this)">==全部==</div>
						</div>
		            </div>
		        </div>
				<div id="hpzl_div" class="slider_body">
		            <div class="slider_selected_left">
		                <span>号牌种类：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
		                <input class="input_select xiala" id="hpzl" type="text" readonly="readonly" value="==全部=="/>
		                <input type="hidden" name="hpzl" value="${disReceive.hpzl}" /> 
		                <div class="ul">
						    <div class="li" data-value="" onclick="sliders(this)">==全部==</div>
						    <c:forEach items= "${dicList}" var="dic">
		            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
			                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
		            	  			<c:if test="${dic.typeSerialNo eq disReceive.hpzl }">
		            	  				<script>$("#hpzl").val('${dic.typeDesc}');</script>
		            	  			</c:if>
		            	  		</c:if> 
						 	</c:forEach>
						</div>
		            </div>
		        </div>
				<div id="hphm_div" class="slider_body">
	                <div class="slider_selected_left">
	                    	号牌号码：
	                </div>
	                <div class="slider_selected_right" style="">
	                    <div class="img_wrap">
	                        <div class="select_wrap input_wrap_select">
	                            <input id="cphid" name="hphm" type="text" class="slider_input" maxlength="8" value="${disReceive.hphm}"/>
	                            <a class="empty" href="javascript:doCplrUI()"></a>
	                        </div>
	                    </div>  
	                </div>
	        	</div>
				
				<div class="slider_body">
					<div class="slider_selected_left">
						<span>通知时间：</span>
					</div>
					<div class="slider_selected_right">
						<div class="demolist">
							<input class="inline laydate-icon" name="qssj" value="${qssj}" readonly="readonly"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" />
						</div>
					</div>
				</div>
				<div class="slider_body">
					<div class="slider_selected_left">
						<span>至</span>
					</div>
					<div class="slider_selected_right">
						<div class="demolist">
							<input class="inline laydate-icon" name="jzsj" value="${jzsj}" readonly="readonly"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" />
						</div>
					</div>
				</div>
				<div id="cxlx_div" class="slider_body" style="position:relative;clear:both;">
		            <div class="slider_selected_left">
		                <span>签收状态：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
		                <input class="input_select xiala" id="qrzt" type="text" readonly="readonly" value="==全部=="/>
		                <input type="hidden" name="qrzt" value="${disReceive.qrzt}" /> 
		                <div class="ul">
		                	<div class="li" data-value="" onclick="sliders(this);">==全部==</div>
		                	<div class="li" data-value="1" onclick="sliders(this);">已签收</div>
		                	<div class="li" data-value="0" onclick="sliders(this);">未签收</div>
		                	<c:if test="${disReceive.qrzt eq '1' }">
		            	  		<script>$("#qrzt").val('已签收');</script>
		            	  	</c:if>
		                	<c:if test="${disReceive.qrzt eq '0' }">
		            	  		<script>$("#qrzt").val('未签收');</script>
		            	  	</c:if>
						</div>
		            </div>
		        </div>
				<div id="button_array" class="button_wrap1">
					<button class="submit_b" onclick="doSearch()">查询</button>
					<button class="submit_b" onclick="doReset()">重置</button>
				</div>
				<div class="pg_result">
					<table>
						<thead>
							<tr>
								<td width="120" align="center">布控撤控通知</td>
								<td width="80" align="center">号牌号码</td>
								<td width="80" align="center">号牌种类</td>
								<td width="100" align="center">布控类别</td>
								<td width="150" align="center">通知单位</td>
								<td width="150" align="center">通知时间</td>
								<td width="150" align="center">签收单位</td>
								<td width="80" align="center">签收状态</td>
								<td width="60" align="center">操作</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="d" items="${pageResults.items }" varStatus="status">
								<tr id="${d.id }">
									<td>
										<c:if test="${d.bkckbz eq '1'}">布控通知</c:if>
										<c:if test="${d.bkckbz eq '2'}">撤控通知</c:if>
									</td>
									<td>${d.hphm}</td>
									<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
						                    	<c:if test="${dic.typeSerialNo eq d.hpzl }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
								 	</td>
								 	<td>
									 	<c:forEach items= "${bklb}" var="b">
					            	  		<c:if test="${b.ID eq d.bklb }">
						                    		${b.NAME }
					            	  		</c:if> 
									 	</c:forEach>
								 	</td>
									<td>${d.xfdwmc }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${d.xfsj }" /></td>
									<td>${d.qrdwmc }</td>
									<td>
										<c:if test="${d.qrzt eq '1'}">已签收</c:if>
										<c:if test="${d.qrzt eq '0'}">未签收</c:if>
									</td>
									<td align="center">
										<a onclick="detail('${d.id}')">详情</a>
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
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/activiti/dispatched-todo.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript">
	$(function (){
		//布控类别初始化
		var bklbList= jQuery.parseJSON('${bklbList}');
		$("#bklbUl").empty();
		$("#bklbUl").append('<div class="li" data-value="" onclick="sliders(this)">==全部==</div>');
		for(var i=0;i< bklbList.length;i++){
			$("#bklbUl").append('<div class="li" data-value='+bklbList[i].ID+' onclick="sliders(this)">'+bklbList[i].NAME+'</div>');
			if(bklbList[i].ID == '${disReceive.bklb }'){
				$("#bklb").val(bklbList[i].NAME);
			}
		}
	});
	var list_url = "dispatched/queryDisReceive.do";
	//搜索..按钮
	function doSearch() {
		if(!compareDate($("input[name='qssj']").val(),$("input[name='jzsj']").val())){
			alert("起始时间不能大于结束时间");
			return ;
		}
		document.getElementById("pageNo").value = 1;
		document.forms[0].action = list_url;
		document.forms[0].submit();
	}

	//重置..按钮
	function doReset() {
		$("input[name='hpzl']").val("");
		$("#hpzl").val("==全部==");
		$("input[name='bklb']").val("");
		$("#bklb").val("==全部==");
		$("input[name='bkckbz']").val("");
		$("#bkckbz").val("==全部==");
		$("input[name='qrzt']").val("");
		$("#qrzt").val("==全部==");
		$("input[name='hphm']").val("");
		$("input[name='qssj']").val("");
		$("input[name='jzsj']").val("");
	}
	//根据页号查询
	function doGoPage(pageNo) {
		document.getElementById("pageNo").value = pageNo;
		document.forms[0].action = list_url;
		document.forms[0].submit();
	}
	
	//详情
	function detail(id){
		var url = "${pageContext.request.contextPath}/dispatched/detailDisReceive.do?id="+id;
	    layer.open({
	           type: 2,
	           title: "通知详情",
	           shadeClose: false,
	           shade: 0.8,
	           area: ['900px', '500px'],
	           content: url //iframe的url
	       }); 	
	}
</script>
</html>