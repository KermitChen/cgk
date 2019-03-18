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
	<title>
		<c:if test="${conPath eq 'findDispatched'}">布控申请管理</c:if>
		<c:if test="${conPath ne 'findDispatched'}">布控综合查询</c:if>
	</title>
	<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<link href="<%=basePath%>common/js/activiti/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
</head>

<body>
	<jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">
			<c:if test="${conPath eq 'findDispatched'}">布控申请管理</c:if>
			<c:if test="${conPath ne 'findDispatched'}">布控综合查询</c:if>
		</span>
	</div>
	<div class="content">
		<div class="content_wrap">
			<form name="form" action="" method="post">
				<div id="cxlx_div" class="slider_body" style="position:relative;clear:both;">
		            <div class="slider_selected_left">
		                <span>查询类型：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
		                <input class="input_select xiala" id="cxlx" type="text" readonly="readonly" value="布控信息"/>
		                <input type="hidden" name="cxlx" value="${cxlx}" /> 
		                <div class="ul">
		                	<div class="li" data-value="1" onclick="sliders(this);changeCxlx()">布控信息</div>
		                	<div class="li" data-value="0" onclick="sliders(this);changeCxlx()">撤控信息</div>
		                	<c:if test="${cxlx eq '1' }">
		            	  		<script>$("#cxlx").val('布控信息');</script>
		            	  	</c:if>
		                	<c:if test="${cxlx eq '0' }">
		            	  		<script>$("#cxlx").val('撤控信息');</script>
		            	  	</c:if>
						</div>
		            </div>
		        </div>
				<div id="bkdl_div" class="slider_body" style="position:relative;">
		            <div class="slider_selected_left">
		                <span>布控大类：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
		                <input class="input_select xiala" id="bkdl" type="text" readonly="readonly" value="==全部=="/>
		                <input type="hidden" name="bkdl" value="${dispatched.bkdl}" /> 
		                <div class="ul">
		                	<div class="li" data-value="" onclick="sliders(this);selectBklb('')">==全部==</div>
						    <c:forEach items= "${dicList}" var="dic">
		            	  		<c:if test="${dic.typeCode eq 'BKDL' }">
			                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);selectBklb('${dic.typeSerialNo}');">${dic.typeDesc}</div>
		            	  			<c:if test="${dic.typeSerialNo eq dispatched.bkdl }">
		            	  				<script>$("#bkdl").val('${dic.typeDesc}');</script>
		            	  			</c:if>
		            	  		</c:if> 
						 	</c:forEach>
						</div>
		            </div>
		        </div>
				<div id="bklb_div" class="slider_body">
		            <div class="slider_selected_left">
		                <span>布控类别：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
		                <input class="input_select xiala" id="bklb" type="text" readonly="readonly" value="==全部=="/>
		                <input type="hidden" name="bklb" value="${dispatched.bklb} " /> 
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
		                <input type="hidden" name="hpzl" value="${dispatched.hpzl}" /> 
		                <div class="ul">
						    <div class="li" data-value="" onclick="sliders(this)">==全部==</div>
						    <c:forEach items= "${dicList}" var="dic">
		            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
			                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this)">${dic.typeDesc}</div>
		            	  			<c:if test="${dic.typeSerialNo eq dispatched.hpzl }">
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
	                            <input id="cphid" name="hphm" type="text" class="slider_input" maxlength="8" value="${dispatched.hphm}"/>
	                            <a class="empty" href="javascript:doCplrUI()"></a>
	                        </div>
	                    </div>  
	                </div>
	        	</div>
				<div id="bkzt_div" class="slider_body">
		            <div class="slider_selected_left">
		                <span>布控状态：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
		                <input class="input_select xiala" id="ywzt" type="text" readonly="readonly" value="==全部=="/>
		                <input type="hidden" name="ywzt" value="${dispatched.ywzt}" /> 
		                <div class="ul">
		                	<div class="li" data-value="" onclick="sliders(this)">==全部==</div>
						    <c:forEach items= "${dicList}" var="dic">
		            	  		<c:if test="${dic.typeCode eq 'BKYWZT' && (dic.typeSerialNo eq '1' || dic.typeSerialNo eq '5' || dic.typeSerialNo eq '7' || dic.typeSerialNo eq '8')}">
			                    	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);">${dic.typeDesc}</div>
		            	  			<c:if test="${dic.typeSerialNo eq dispatched.ywzt }">
		            	  				<script>$("#ywzt").val('${dic.typeDesc}');</script>
		            	  			</c:if>
		            	  		</c:if> 
						 	</c:forEach>
						</div>
		            </div>
		        </div>
				<div class="slider_body">
					<div class="slider_selected_left">
						<span>申请时间：</span>
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
				
		        <div id="zjbk_div" class="slider_body">
		            <div class="slider_selected_left">
		                <span>直接布控：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
		                <input class="input_select xiala" id="zjbk" type="text" readonly="readonly" value="==全部=="/>
		                <input type="hidden" name="zjbk" value="${dispatched.zjbk }" /> 
		                <div class="ul">
		                	<div class="li" data-value="" onclick="sliders(this)">==全部==</div>
		                	<div class="li" data-value="1" onclick="sliders(this)">是</div>
		                	<div class="li" data-value="0" onclick="sliders(this)">否</div>
		                	<c:if test="${'0' eq dispatched.zjbk }">
		            	  		<script>$("#zjbk").val("否");</script>
		            	  	</c:if>
		                	<c:if test="${'1' eq dispatched.zjbk }">
		            	  		<script>$("#zjbk").val("是");</script>
		            	  	</c:if>
						</div>
		            </div>
		        </div>
		        <div id="bkr_div" class="slider_body">
	                <div class="slider_selected_left">
	                    	申请人：
	                </div>
	                <div class="slider_selected_right" style="">
	                    <div class="img_wrap">
	                        <div class="select_wrap select_input_wrap">
	                            <input id="bkr" name="bkr" type="text" class="slider_input" maxlength="8" value="${dispatched.bkr}"/>
	                            <a id="bkr" class="empty"></a>
	                        </div>
	                    </div>  
	                </div>
	        	</div>
				<div id="button_array" class="button_wrap1">
					<button class="submit_b" onclick="doSearch()">查询</button>
					<button class="submit_b" onclick="doReset()">重置</button>
					<c:if test="${conPath eq 'findDispatched'}">
						<button class="submit_b" onclick="toAddUI()">新增</button>
					</c:if>
					<button class="submit_b" onclick="doExportExcel()">导出Excel</button>
				</div>
				<div class="pg_result">
				<c:if test="${cxlx eq '1'}">
					<table>
						<thead>
							<tr>
								<td width="80" align="center">号牌号码</td>
								<td width="80" align="center">号牌种类</td>
								<td width="100" align="center">布控大类</td>
								<td width="100" align="center">布控类别</td>
								<td width="100" align="center">布控申请人</td>
								<td width="120" align="center">布控申请单位</td>
								<td width="120" align="center">布控申请时间</td>
<!--								<td width="100" align="center">布控起始时间</td>-->
<!--								<td width="100" align="center">布控终止时间</td>-->
								<td width="80" align="center">布控性质</td>
								<td width="80" align="center">直接布控</td>
								<td width="80" align="center">布控状态</td>
								<td width="100" align="center">操作</td>
								<td width="80" align="center">当前节点</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="d" items="${pageResults.items }" varStatus="status">
								<c:set var="task" value="${d.task }" />
								<c:set var="pi" value="${d.processInstance }" />
								<tr id="${d.bkid }" tid="${task.id }">
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
									 	<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKDL' }">
						                    	<c:if test="${dic.typeSerialNo eq d.bkdl }">
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
									<td>${d.bkr }</td>
									<td>${d.bkjgmc }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${d.bksj }" /></td>
<!--									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${d.bkqssj }" /></td>-->
<!--									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${d.bkjzsj }" /></td>-->
									<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKXZ' }">
						                    	<c:if test="${dic.typeSerialNo eq d.bkxz }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
									</td>
									<td>
										<c:if test="${d.zjbk eq '1' }">
											是
										</c:if>
										<c:if test="${d.zjbk eq '0' }">
											否
										</c:if>
									</td>
									<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKYWZT' }">
						                    	<c:if test="${dic.typeSerialNo eq d.ywzt }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
									</td>
									<td align="center">
										<a href="dispatched/detailDispatched.do?rowId=${d.bkid }&taskId=${task.id }&conPath=${conPath }" >详情</a>
										<c:if test="${(conPath eq 'findDispatched') && (d.ywzt eq '1') && (d.zjbk eq '0')}">
											| <a onclick="doWithdraw('${d.bkid}',false)" >撤控</a>
										</c:if>
										<c:if test="${(conPath eq 'findDispatched') && (d.ywzt eq '1') && (d.zjbk eq '1')}">
											| <a onclick="doWithdraw('${d.bkid}',true)" >直接撤控</a>
										</c:if>
										<c:if test="${conPath eq 'findDispatched' && user.loginName eq d.bkrjh}">
<!-- 											<c:if test="${pi.suspended eq true}"> -->
<!-- 												|<a onclick="updateState('active','${pi.id}','${d.bkid}')">激活</a> -->
<!-- 											</c:if> -->
<!-- 											<c:if test="${pi.suspended eq false}"> -->
<!-- 												|<a onclick="updateState('suspend','${pi.id}','${d.bkid}')">挂起</a> -->
<!-- 											</c:if> -->
											<c:if test="${pi.suspended eq false}">
												| <a onclick="updateState('suspend','${pi.id}','${d.bkid}')">取消申请</a>
											</c:if>
											<c:if test="${d.ywzt eq '6' && not empty task.id}">
												| <a href="dispatched/editDispatched.do?rowId=${d.bkid }&taskId=${task.id }&conPath=${conPath }" >调整申请</a>
											</c:if>
										</c:if>
									</td>
									<td align="center">
										<a class="trace"  pid="${pi.id }" pdid="${pi.processDefinitionId}" title="点击查看流程图">${task.name }</a>
<!-- 										<a href="javascript:doEdit(${d.bkid })" >审批流程</a> -->
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</c:if>
					<c:if test="${cxlx eq '0'}">
						<table>
						<thead>
							<tr>
								<td width="80" align="center">号牌号码</td>
								<td width="100" align="center">号牌种类</td>
<!-- 								<td width="100" align="center">布控类别</td> -->
								<td width="100" align="center">布控性质</td>
								<td width="80" align="center">撤销申请人</td>
								<td width="120" align="center">撤销申请时间</td>
								<td width="80" align="center">直接撤控</td>
								<td width="80" align="center">撤销状态</td>
								<td width="80" align="center">操作</td>
								<td width="80" align="center">当前节点</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="w" items="${pageResults.items }" varStatus="status">
								<c:set var="d" value="${w.dispatched }" />
								<c:set var="task" value="${w.task }" />
								<c:set var="pi" value="${w.processInstance }" />
								<tr id="${w.ckid }" tid="${task.id }">
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
<!-- 								 	<td> -->
<!-- 								 	<c:forEach items= "${bklb}" var="b"> -->
<!-- 				            	  		<c:if test="${b.ID eq d.bklb }"> -->
<!-- 					                    		${b.NAME } -->
<!-- 				            	  		</c:if>  -->
<!-- 								 	</c:forEach> -->
<!-- 								 	</td> -->
									<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'BKXZ' }">
						                    	<c:if test="${dic.typeSerialNo eq d.bkxz }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
									</td>
									<td>${w.cxsqr }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${w.cxsqsj }" /></td>
									<td>
										<c:if test="${w.zjck ne '1' }">否</c:if>
										<c:if test="${w.zjck eq '1' }">是</c:if>
									</td>
									<td>
										<c:forEach items= "${dicList}" var="dic">
					            	  		<c:if test="${dic.typeCode eq 'CKYWZT' }">
						                    	<c:if test="${dic.typeSerialNo eq w.ywzt }">
						                    		${dic.typeDesc }
						                    	</c:if>
					            	  		</c:if> 
									 	</c:forEach>
									</td>
									<td align="center">
										<a href="withdraw/detailWithdraw.do?rowId=${w.ckid }&taskId=${task.id }&conPath=${conPath }" >详情</a>
									</td>
									<td align="center">
										<a class="trace"  pid="${pi.id }" pdid="${pi.processDefinitionId}" title="点击查看流程图">${task.name }</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</c:if>
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
// 		var bklbList= jQuery.parseJSON('${bklbList}');
// 		$("#bklbUl").empty();
// 		$("#bklbUl").append('<div class="li" data-value="" onclick="sliders(this)">==全部==</div>');
// 		for(var i=0;i< bklbList.length;i++){
// 			$("#bklbUl").append('<div class="li" data-value='+bklbList[i].ID+' onclick="sliders(this)">'+bklbList[i].NAME+'</div>');
// 			if(bklbList[i].ID == '${dispatched.bklb }'){
// 				$("#bklb").val(bklbList[i].NAME);
// 			}
// 		}
		if('${conPath }' == "findDispatched"){
			$("#button_array").removeClass("button_wrap1");
			$("#button_array").addClass("clear_both");
			$("#cxlx_div").hide();
		}
		selectBklb('${dispatched.bkdl}');
		changeCxlx();
	});
	//查询布控类别
	function selectBklb(value) {
		if(value.trim() == ''){
			$("#bklbUl").empty();
			$("#bklb").val("==全部==");
			$("input[name='bklb']").val("");
			$("#bklbUl").append('<div class="li" data-value="" onclick="sliders(this)">==全部==</div>');
			return;
		}
		var dic= jQuery.parseJSON('${bklbList}');
		$("#bklbUl").empty();
		$("#bklb").val("==全部==");
		$("input[name='bklb']").val("");
		$("#bklbUl").append('<div class="li" data-value="" onclick="sliders(this)">==全部==</div>');
		for(var i=0;i< dic.length;i++){
			if(dic[i].SUPERID == value){
				$("#bklbUl").append('<div class="li" data-value='+dic[i].ID+' onclick="sliders(this)">'+dic[i].NAME+'</div>');
				if('${dispatched.bklb}'.trim() != '' && '${dispatched.bklb}' == dic[i].ID){
					$("#bklb").val(dic[i].NAME);
				}
			}
		}
	}
	var list_url = "<%=basePath%>dispatched/${conPath }.do";
	//搜索..按钮
	function doSearch() {
		if(!compareDate($("input[name='qssj']").val(),$("input[name='jzsj']").val())){
			alert("起始时间不能大于结束时间");
			return ;
		}
		if($("input[name='bkdl']").val() == ''){
			$("input[name='bklb']").val('');
		}
		document.getElementById("pageNo").value = 1;
		document.forms[0].action = list_url;
		document.forms[0].submit();
	}
	//新增..按钮
	function toAddUI() {
		document.forms[0].action = "dispatched/loadDispatchedAdd.do";
		document.forms[0].submit();
	}

	//重置..按钮
	function doReset() {
		$("input[name='hpzl']").val("");
		$("#hpzl").val("==全部==");
		$("input[name='bklb']").val("");
		$("#bklb").val("==全部==");
		$("input[name='hphm']").val("");
		$("input[name='qssj']").val("");
		$("input[name='jzsj']").val("");
		$("input[name='bkdl']").val("");
		$("#bkdl").val("==全部==");
		$("input[name='ywzt']").val("");
		$("#ywzt").val("==全部==");
		$("input[name='zjbk']").val("");
		$("#zjbk").val("==全部==");
	}
	//根据页号查询
	function doGoPage(pageNo) {
		document.getElementById("pageNo").value = pageNo;
		document.forms[0].action = list_url;
		document.forms[0].submit();
	}
	
	//取消申请
	function updateState(state,processInstanceId,bkid){
		if(!confirm("确认要取消布控申请吗？")){
			return;
		}
		$.blockUI({
	        message: '<h2><img src="' + 'common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
	    });
		// 发送请求更新流程状态
	    $.post('${pageContext.request.contextPath}/dispatched/update.do?state=' + state+'&processInstanceId='+processInstanceId+'&bkid='+bkid
	    , function(resp) {
			$.unblockUI();
	        if (resp == 'success') {
// 	        	if(state=="active"){
// 	            	alert('激活成功');
// 	            }else{
// 	            	alert('挂起成功');
// 	            }
				alert("布控申请取消成功");
				$.blockUI({
		 	        message: '<h2><img src="' + 'common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
		 	    });
	            location.reload();
	        } else {
	            alert('操作失败,请联系管理员');
	        }
	    });
	}
	//撤控
	function doWithdraw(bkid,isZjck){
		var url = "${pageContext.request.contextPath}/withdraw/loadWithdrawAdd.do?bkid="+bkid+"&isZjck="+isZjck;
	    var titles = "";
	    isZjck == true ? titles = "直接撤控窗口" : titles = "撤控申请窗口";
	    layer.open({
	           type: 2,
	           title: titles,
	           shadeClose: false,
	           shade: 0.8,
	           area: ['530px', '500px'],
	           content: url //iframe的url
	       }); 	
	}
	function changeCxlx(){
		if($("input[name='cxlx']").val() == "1"){
			$("#bkdl_div").show();
			$("#bklb_div").show();
			$("#bkzt_div").show();
			$("#zjbk_div").show();
			$("#hphm_div").show();
			$("#hpzl_div").show();
		}else{
			$("#bkdl_div").hide();
			$("#bklb_div").hide();
			$("#bkzt_div").hide();
			$("#zjbk_div").hide();	
			$("#hphm_div").hide();	
			$("#hpzl_div").hide();	
		}
	}
	function detail(bkid,taskId,conPath){
		
	}
	
	//===========@author liuqiang===============
	var conPath = '${conPath}';
	function doExportExcel(){
		var url;
		if(conPath=='findOpenDispatched'){
			url = "${pageContext.request.contextPath}/dispatched/excelExportForDispatchedGK.do";
		}else if(conPath=='findDispatched'){
			url = "${pageContext.request.contextPath}/dispatched/excelExportForDispatched.do";
		}else if(conPath=='findAllDispatched'){
			url = "${pageContext.request.contextPath}/dispatched/excelExportForDispatchedMM.do";
		}
		document.forms[0].action = url ;
		document.forms[0].submit();
	}
</script>
</html>