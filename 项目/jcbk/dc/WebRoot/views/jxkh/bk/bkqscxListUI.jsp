<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <title>布控签收查询</title>
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
    <div class="head">
        <div class="head_wrap">
            <img src="<%=basePath%>common/images/police.png" alt="">
            <h1>深圳市公安局缉查布控系统</h1>
        </div>
    </div>
    <div id="divTitle">
		<span id="spanTitle">当前位置：绩效考核>>布控统计查询</span>
	</div>
    <form name="form" action="" method="post">
    <div class="content">
    	<div class="content_wrap">
    	
	        <div class="slider_body"> 
				<div class="slider_selected_left">
	                    <span>车牌号码：</span>
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
		            <span>车牌颜色：</span>
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
	                 <span>车辆使用人：</span>
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
	                <span>记录状态：</span>
	            </div>
			<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
			     <input class="input_select xiala" id="xiala1" type="text" readonly="readonly" value="==全部=="/>
			     <input type="hidden" id="xiala11" name="Check_jlzt" value="${s.typeSerialNo }" /> 
	            <div class="ul"> 
	            	<div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
	            	  <c:forEach items= "${jlztList }" var="s" >
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
		    	<input type="button" class="button_blue" value="新增" onclick="doAddUI()">
		    	<input type="button" class="button_blue" value="导出Excel" onclick="doExport()">
		    </div>
		    
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
                            <td align="center">序号</td>
                            <td align="center">车牌号码</td>
                            <td align="center">车牌颜色</td>
                            <td align="center">车辆使用人</td>
                            <td align="center">申请人</td>
                            <td align="center">记录状态</td>
                            <td align="center">审批结果</td>
                            <td align="center">操作</td>
                            <td align="center">当前节点</td>
	                    </tr>
	                </thead>
	                <tbody>
                        <c:forEach var="rs" items="${pageResult.items }" varStatus="status">
                        	<c:set var="r" value="${rs.jjhomd }"></c:set>
                        	<c:set var="task" value="${rs.task }" />
							<c:set var="pi" value="${rs.processInstance }" />
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
                        		<td>
                        		    <c:forEach items="${jlztList }" var="c">
                        				<c:if test="${c.typeSerialNo eq  r.jlzt }">${c.typeDesc }</c:if>
                        			</c:forEach>
                        		</td>
                        		<td>
                        			<c:forEach items="${spjgList }" var="c">
                        				<c:if test="${c.typeSerialNo eq  r.zt }">${c.typeDesc }</c:if>
                        			</c:forEach>                        		
                        		</td>
                        		<td>
                        			<c:if test="${r.jlzt eq '001'}">
	                        			<a id="detail${id }" href="javascript:doCancelHmd(${r.id })">取消申请</a>
	                        			|
                        			</c:if>
                        			<c:if test="${r.jlzt eq '002'}">
	                        			<a id="detail${id }" href="javascript:doRevokeHmd(${r.id })">撤销红名单</a>
	                        			|                        			
                        			</c:if>
                        			<a id="detail${id }" href="javascript:doDetail(${r.id })">详情</a>
                        		</td>
                        		<td align="center">
									<a class="trace"  pid="${pi.id }" pdid="${pi.processDefinitionId}" title="点击查看流程图">${task.name }</a>
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
	$(function() {
	    // 跟踪
	    $('.trace').click(graphTrace);
	    
	});
	
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
	//选择监测点
	function doChoseJcd(){
		//alert('hao!');
		var url = "${pageContext.request.contextPath}/hmd/getJcd.do";
	    layer.open({
	           type: 2,
	           title: '监测点筛选窗口',
	           shadeClose: false,
	           shade: 0,
	           area: ['800px', '600px'],
	           content: url //iframe的url
	       }); 		
	}
	//撤销红名单
	function doRevokeHmd(id){
		layer.confirm('您确定撤销该红名单？', {
  			btn: ['想好了','再想想'] //按钮
		},function(){
			var url ="${pageContext.request.contextPath}/JjHmd/revokeHmd.do";
			$.ajax({
				data:{hmdId:id},
				type:'POST',
				dataType:"text",
				url:url,
				error:function(message){
					layer.msg(message);
				},
				success:function(message){
					layer.msg(message);
					//重新加载当前页面
					window.location.reload();
				}
			});
		},function(){
			layer.msg('您取消了操作！',{time:1000});
		});
	}
	
	//取消申请   红名单
	function doCancelHmd(id){
		layer.confirm('您确定取消申请红名单？', {
  			btn: ['想好了','再想想'] //按钮
		},function(){
			var url ="${pageContext.request.contextPath}/JjHmd/cancelHmd.do";
			$.ajax({
				data:{hmdId:id},
				type:'POST',
				dataType:"text",
				url:url,
				error:function(message){
					layer.msg(message);
				},
				success:function(message){
					layer.msg(message);
					window.location.reload();
				}
			});
		},function(){
			layer.msg('您取消了操作！',{time:1000});
		});
	}
</script>
<script>
		var list_url = "JjHmd/findJjHmd.do";
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
		
		//新增..按钮
		function doAddUI(){
			//跳转到新增页面
			document.forms[0].action = "JjHmd/toAddUI.do";
	  		document.forms[0].submit();
		}
		
		//车牌号录入弹出层
		function doCplrUI(){
			var url = "${pageContext.request.contextPath}/views/HmdMsg/cplr.jsp";
		    layer.open({
		           type: 2,
		           title: '车牌号码录入窗口',
		           shadeClose: true,
		           shade: 0.8,
		           area: ['800px', '500px'],
		           content: url //iframe的url
		       });	
		}
		
	</script>
</html>