<%@page import="java.util.*,com.dyst.utils.*,com.dyst.base.utils.*,com.dyst.systemmanage.entities.*"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String filePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + "/" + Config.getInstance().getBugPicPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问题反馈</title>
<style>
	.pg_result{
		height:auto !important;
		height:200px;
		min-height:200px
	}
	.mess_desc_td{
		color: #900b09;
	}
	.mess_desc_td:HOVER{
		color: #ff0000 !important;
		cursor: pointer;
	}
</style>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">问题反馈</span>
	</div>
    <div class="content">
    	<div class="content_wrap">
    	 	<form id="form1" name="form1" action="" method="post">
	      		 <div class="slider_body">
		            <div class="slider_selected_left">
		                <span>是否处理：</span>
		            </div>
		            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
		                <c:choose>
		            		<c:when test="${isDeal eq '0' }">
			            		<input class="input_select xiala isDeal" type="text" id="xiala" readonly="readonly" value="未处理"/>
		            		</c:when>
		            		<c:when test="${isDeal eq '1' }">
			            		<input class="input_select xiala isDeal" type="text" id="xiala" readonly="readonly" value="已处理"/>
		            		</c:when>
		            		<c:otherwise>
								<input class="input_select xiala isDeal" type="text" id="xiala" readonly="readonly" value="==请选择=="/>
		            		</c:otherwise>
		            	</c:choose>
		               <input type="hidden" id="isDeal" name="isDeal" value="${isDeal }" />
		                <div class="ul"> 
		                	<div class="li xiala_div" data-value="" onclick="sliders(this)">==请选择==</div> 
		                    <div class="li xiala_div" data-value="0" onclick="sliders(this)">未处理</div> 
		                    <div class="li xiala_div" data-value="1" onclick="sliders(this)">已处理</div> 
		                </div> 
		            </div>
		        </div>
	       
	        <div class="slider_body" >
			  <div class="slider_selected_left">
				<span>提交时间：</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="beginTime" name="beginTime" value="${beginTime }" 
			onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
				  </div>
				</div>
			</div>
			<div class="slider_body" >
			  <div class="slider_selected_left">
				<span>至</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="endTime" name="endTime" value="${endTime }" 
			onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
				  </div>
				</div>
			</div>
			<div class="button_wrap clear_both">
		        <div class="button_div">
		        	<input id="query_button" name="query_button" type="button" class="button_blue" value="查询" onclick="doSubmit();">
			    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
			    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="新增" onclick="doAdd();">
		        </div>
		    	<!-- 错误信息提示 -->
				<div>
					<span id="errSpan"  style="color:red;margin-left:auto;margin-right:auto" ></span>
		    	</div>
    	 	</div>
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
	                        <td>序号</td>
	                        <td>提交用户</td>
	                        <td>提交时间</td>
	                        <td>反馈内容</td>
	                        <td>是否处理</td>
	                        <td>处理时间</td>
	                        <td>操作</td>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach items="${pageResult.items }" var="s" varStatus="c">
	                    	<tr>
	                       <td>${c.index + 1 }</td>
	                         <td>${s.userName }</td>
	                        <td>${fn:substringBefore(s.submitTime,".") }</td>
	                        <td class="mess_desc_td">${fn:substring(s.problemDesc, 0, 5)}...
	                        	<input type="hidden" value="${s.problemDesc }">
	                        </td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${s.isDeal eq '1' }">已处理</c:when>
	                        		<c:otherwise>未处理</c:otherwise>
	                        	</c:choose>
	                        </td>
	                        <td>${fn:substringBefore(s.dealTime,".") }</td>
	                        <td>
	                        	<c:if test="${!empty s.picAddress && s.picAddress ne null}">
		                        	<a href="<%=filePath %>${s.picAddress}">下载附件</a>|
	                        	</c:if>
	                        	<a href="javascript:deleteReport('${s.id }');">删除</a>|
	                        	<c:choose>
	                        		<c:when test="${s.isDeal eq '1' }"></c:when>
	                        		<c:otherwise><a href="javascript:dealReport('${s.id }');">处理</a></c:otherwise>
	                        	</c:choose>
	                        	
	                        </td>
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
		var list_url = "<%=basePath%>bugReport/find.do";
		//根据页号查询
		function doGoPage(pageNo) {
			layer.load();
			document.getElementById("pageNo").value = pageNo;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		function doAdd(){
			document.forms[0].action = "<%=basePath%>bugReport/preAdd.do";
			document.forms[0].submit();
		}
		$(function(){
			$("td.mess_desc_td").each(function () {
				 $(this).mouseover(function(){
					 var txt = $(this).find('input:first').val();
					 layer.open({
				           type: 4,
				           shade: 0,
				           time:60000,
				           closeBtn: 0,
				           tips: [3, '#758a99'],
				           content: [txt,$(this)]
				       });
				 });
                 $(this).mouseout(function(){
                	 layer.closeAll('tips');
                 });
             });
		});
		
		//提交表单
		function doSubmit(){
			layer.load();
			document.forms[0].action = list_url;
			$("#errSpan").text("");
			$("#pageNo").val("1");
			$("#form1").submit();
		}
		function doReset(){
			$("#errSpan").text("");
			$("#xiala").val("==请选择==");
			$("#isDeal").val("");
			$("#beginTime").val(moment().subtract(30,'days').format('YYYY-MM-DD HH:mm:ss'));
			$("#endTime").val(moment().format('YYYY-MM-DD HH:mm:ss'));
		}
		
		function deleteReport(id){
			layer.confirm('确定删除？', {
				  btn: ['确定','取消'] //按钮
				}, function(index){
					var location = (window.location+'').split('/');  
					var basePath = location[0]+'//'+location[2]+'/'+location[3];  
					$.ajax({
						async:true,
						type: 'POST',
						data:{id:id},
						dataType : "json",
						url: basePath + "/bugReport/delete.do",//请求的action路径
						error: function () {//请求失败处理函数
							layer.msg('删除失败!');
						},
						success:function(data){ //请求成功后处理函数。
							console.log(data);
							if(data.res == null || data.res == "" || data.res == '0'){
								layer.msg('删除失败!');
							}else if(data.res == '1'){
								layer.msg('删除成功!');
								doGoPage($("#pageNo").val());
							}
						}
					});
					layer.close(index);
				}, function(index){
				  layer.close(index);
				});
		}
		function dealReport(id){
			layer.confirm('确定标记为已处理状态？', {
				  btn: ['确定','取消'] //按钮
				}, function(index){
					var location = (window.location+'').split('/');  
					var basePath = location[0]+'//'+location[2]+'/'+location[3];  
					$.ajax({
						async:true,
						type: 'POST',
						data:{id:id},
						dataType : "json",
						url: basePath + "/bugReport/deal.do",//请求的action路径
						error: function () {//请求失败处理函数
							layer.msg('标记失败!');
						},
						success:function(data){ //请求成功后处理函数。
							console.log(data);
							if(data.res == null || data.res == "" || data.res == '0'){
								layer.msg('标记失败!');
							}else if(data.res == '1'){
								layer.msg('标记成功!');
								doGoPage($("#pageNo").val());
							}
						}
					});
					layer.close(index);
				}, function(index){
				  layer.close(index);
				});
		}
</script>
</html>