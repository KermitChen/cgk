<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>套牌阈值管理</title>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<style type="text/css">
		.pg_result{
			height:auto !important;
			height:200px;
			min-height:200px
		}
	</style>
	<div id="divTitle">
		<span id="spanTitle">套牌阈值管理</span>
	</div>
    <div class="content">
    	<div class="content_wrap">
    	 	<form id="form1" name="form1" action="<%=basePath%>tpyz/find.do" method="post">
    	 	<input type="hidden" name="distance" id="distance">
    	 	<input type="hidden" name="speed" id="speed">
    	 	<input type="hidden" name="isUpdate" id="isUpdate">
    	 	<div class="slider_body">
	            <div class="slider_selected_left">
	                <span>监测点：</span>
	            </div>
	            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
	            	<c:set value="${fn:split(jcdid, ',') }" var="str1" />
	            	
	                <input class="input_select xiala" onclick="doChoseJcd();" id="jcdid1" type="text" 
	                <c:choose>
	            		<c:when test="${jcdid ne '' && jcdid ne null && fn:length(str1) > 0}">value="您选中了${fn:length(str1) }个监测点"</c:when>
	            		<c:otherwise>value="==全部=="</c:otherwise>
	            	</c:choose>
	            	/> 
	                <input type="hidden" name="jcdid" id="jcdid"  value="${jcdid }">
	            </div>
	        </div>
			<div class="slider_body">
		        <div class="button_div">
		        	<input id="query_button" name="query_button" type="button" class="button_blue" value="查询">
			    	<input id="update_button" name="reset_button" type="button" class="button_blue" value="更新" onclick="goParam();">
		        </div>
    	 	</div>
		    	<!-- 错误信息提示 -->
				<div>
					<span id="errSpan"  style="color:red;margin-left:auto;margin-right:auto" ></span>
		    	</div>
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
	                        <td>监测点1ID</td>
	                        <td>监测点1名称</td>
	                        <td>监测点2ID</td>
	                        <td>检测点2名称</td>
	                        <td>套牌时间阈值(秒)</td>
	                        <td>距离(米)</td>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach items="${pageResult.items }" var="s" varStatus="c">
	                    	<tr>
	                        <td>${s.jcdid1 }</td>
	                        <td>${jcdMap[s.jcdid1] }</td>
	                       	<td>${s.jcdid2 }</td>
	                        <td>${jcdMap[s.jcdid2]}</td>
	                        <td>${s.tpsjyz }</td>
	                        <td>${s.jl }</td>
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
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript">
		var list_url = "<%=basePath%>tpyz/find.do";
		var index;
		//根据页号查询
		function doGoPage(pageNo) {
			layer.load();
			document.getElementById("pageNo").value = pageNo;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		//更新套牌阈值
		function goParam(){
			index = layer.open({
		        type: 2,
		        title: '请填写参数',
		        shade:0.2,
		        shadeClose: false, //点击遮罩关闭层
		        area : ['300px' , '160px'],
		        content: '<%=basePath%>/tpyz/param.do'
		    });
		}
		//更新套牌阈值
		function doUpdate(){
			layer.load();
			var distance = $.trim($("#distance").val());
			var speed = $.trim($("#speed").val());
			var url = '<%=basePath%>tpyz/update.do?'+new Date().getTime();
			$.ajax({
				aysnc:true,
				method:'post',
				data:{distance:distance,speed:speed},
				dataType:'text',
				url:url,
				success:function(data){
					layer.closeAll('loading');
					layer.msg(data);
				},
				error:function(){
					layer.closeAll('loading');
					layer.msg('更新失败');
				}
			});
		}
		
		$(function(){
			$("#isUpdate").change(function(){
				layer.close(index);
				doUpdate();
			});
			$("#query_button").click(function(){
				layer.load();
				document.forms[0].action = list_url;
				document.forms[0].submit();
			});
		});
	</script>
</html>