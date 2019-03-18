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
    <title>订阅审批</title>
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
     <link href="<%=basePath%>common/js/activiti/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
</head>
<body>
    <div class="head">
        <div class="head_wrap">
            <img src="<%=basePath%>common/images/police.png" alt="">
            <h1>深圳市公安局缉查布控系统</h1>
        </div>
    </div>
    <div id="divTitle">
		<span id="spanTitle">当前位置：订阅审批</span>
	</div>
    <form name="form" action="" method="post">
    <div class="content">
    	<div class="content_wrap">
		    
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
                            <td align="center">序号</td>
                            <td align="center">订阅人姓名</td>
                            <td align="center">申请时间</td>
                            <td align="center">录入时间</td>
                            <td align="center">订阅类型</td>
                            <td align="center">记录状态</td>
                            <td align="center">审批结果</td>
                            <td align="center">当前节点</td>
                            <td align="center">操作</td>
	                    </tr>
	                </thead>
	                <tbody>
                        <c:forEach var="dyxxsp" items="${pageResult.items }" varStatus="status">
                        	<c:set var="dyxx" value="${dyxxsp.dyxx }"></c:set>
                        	<c:set var="task" value="${dyxxsp.task }" />
							<c:set var="pi" value="${dyxxsp.processInstance }" />
                        	<tr>
                        		<td>${status.index+1} </td>
                        		<td>${dyxx.jyxm }</td>
                        		<td>${dyxxsp.sqsj }</td>
                        		<td>${dyxx.lrsj }</td>
                        		<td>
                        			<c:forEach items="${dylxList }" var="c">
                        				<c:if test="${c.typeSerialNo eq dyxx.dylx }">${c.typeDesc }</c:if>
                        			</c:forEach>                        			
                        		</td>
                        		<td>
                        			<c:forEach items="${jlztList }" var="c">
                        				<c:if test="${c.typeSerialNo eq  dyxx.jlzt }">${c.typeDesc }</c:if>
                        			</c:forEach>                        			
                        		</td>
                        		<td>
                        			<c:forEach items="${spjgList }" var="c">
                        				<c:if test="${c.typeSerialNo eq  dyxxsp.spjg }">${c.typeDesc }</c:if>
                        			</c:forEach>                        			
                        		</td>
                        		<td>
									<a class="trace" pid="${pi.id }" pdid="${pi.processDefinitionId}" title="点击查看流程图">${task.name }</a>
								</td>
								<td>
									<c:if test="${empty task.assignee }">
										<a onclick="block()" href="dysp/taskClaim.do?taskId=${task.id}">签收</a>
									</c:if>
									<c:if test="${not empty task.assignee}">
										<a id="detail${id }" href="javascript:doSp(${dyxx.id },${task.id })"
										style="color:red" onmouseover="this.style.cssText='color:black;'" onmouseout="this.style.cssText='color:red;'">审批</a>
									</c:if>
								</td>
								
                        	</tr>
                        </c:forEach>
	                </tbody>
	            </table>
			<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
	        </div>
			</div>
        </div>
        <input type="hidden" id="jcdid" value="">
    </form>
    <jsp:include  page="/common/Foot.jsp"/>
</body>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
<script  src="<%=basePath%>common/js/layer/layer.js"></script>
<script type="text/javascript">
	$(function() {
	    // 跟踪
	    $('.trace').click(graphTrace);
	    if('${success}'=="success"){
			layer.msg("签收成功");
		}else if('${success}'=="error"){
			alert("该任务已被签收，请选择其他任务");
		}
	});
	//文档加载时
	$(function(){
		//订阅类型多选下拉框赋值
		dylxInit();
		//时间框初始化
		initTime();
		
	});
	
	//时间框...初始化
	function initTime(){
		var startTime = moment().add('days',-7).format('YYYY-MM-DD HH:mm:ss');
		if($("#Check_startTime").val()==''){
			$("#Check_startTime").val(startTime);
		}
		var endTime = moment().format('YYYY-MM-DD HH:mm:ss');;
		if($("#Check_endTime").val()==''){
			$("#Check_endTime").val(endTime);
		}
	}
	
	//查看详情弹出层
	function doDetail(id){
		var url = "${pageContext.request.contextPath}/dy/getDetail.do?id="+id;
	    layer.open({
	           type: 2,
	           title: '订阅信息详情',
	           shadeClose: false,
	           shade: 0,
	           area: ['800px', '600px'],
	           content: url //iframe的url
	       }); 
	}
	
	//弹出审批页面
	function doSp(id,taskid){
		var url = "${pageContext.request.contextPath}/dysp/getSpUI.do?id="+id+"&taskId="+taskid;
	    layer.open({
	           type: 2,
	           title: '订阅信息详情',
	           shadeClose: false,
	           shade: 0,
	           area: ['800px', '600px'],
	           content: url //iframe的url
	       }); 
	       //window.open(url,"_blank");
	}

	var list_url = "${pageContext.request.contextPath}/dy/DySp.do";
	//搜索..按钮
  	function doSearch(){
  		//重置页号(两种写法DOM,JQuery)
  		//document.getElementById("pageNo").value=1;
  		$("#pageNo").val(1);
  		document.forms[0].action = "${pageContext.request.contextPath}/dysp/findDySp.do";
  		document.forms[0].submit();	  		
  	}

	//重置..按钮
	function doReset(){
		//订阅类型清空
		$('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
			$(this).children("input").attr("checked",false);
			$(this).children("input").trigger("change");
		});
		$("#xiala1").val("==全部==");
		$("#xiala11").val("");
		$("#xiala2").val("==全部==");
		$("#xiala22").val("");
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
		var url_addUI = "${pageContext.request.contextPath}/dy/toAddUI.do";
		layer.open({
	           type: 2,
	           title: '<font style="border:16px">新增订阅信息</font>',
	           shadeClose: false,
  			   shade: [0],
	           area: ['1000px', '600px'],
	           content: url_addUI //iframe的url
	    }); 
	}
	
	//文档加载的时候，给订阅类型多选下拉框赋值
	function dylxInit(){
	    var value = []; 
		var data = [];
	    var dicJson = $.parseJSON('${spjgList1 }');
		for(var i=0;i < dicJson.length;i++){
			value.push(dicJson[i].typeSerialNo);
			data.push(dicJson[i].typeDesc);
		}
	    $('.multi_select').MSDL({
			'value': value,
	      	  'data': data
	    });
	    //条件回显，勾选上次选定的项
	    var bkfwValue = '${Check_dylx}'.split(";"); 
	    $('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
			for(var i in bkfwValue){
				if(bkfwValue[i] == $(this).children("input").val()){
					$(this).children("input").attr("checked","checked");
					$(this).children("input").trigger("change");
				};
			};
		});	
	}
		
</script>
</html>