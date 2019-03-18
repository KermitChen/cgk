<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>初次入城车辆</title>
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
		<span id="spanTitle">初次入城</span>
	</div>
    <div class="content">
    	<div class="content_wrap">
    	 	<form id="form1" name="form1" action="<%=basePath%>gjfx/ccrc.do" method="post">
    	 	<input type="hidden" id="resFlag" name="resFlag" value="${resFlag }">
	        <div class="slider_body">
				<div class="slider_selected_left">
					<span>车牌颜色：</span>
				</div>
				<div id="dropdown_quanxuan" class="slider_selected_right dropdown dropdown_all">
					<div class="input_select xiala">
						<div id="role_type_downlist" class='multi_select'>
							<input type="hidden" name="cplx" id="cplx" value="${cplx }"/>
							<a class="xiala_duoxuan_a"></a>
						</div>
					</div>
				</div>
			</div>
	       
	        <div class="slider_body" >
			  <div class="slider_selected_left">
				<span>查询时间：<span style="color:red;">*</span></span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="kssj" name="kssj" value="${kssj }" 
			onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
				  </div>
				</div>
			</div>
			<div class="slider_body" >
			  <div class="slider_selected_left">
				<span>至<span style="color:red;">*</span></span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="jssj" name="jssj" value="${jssj }" 
			onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
				  </div>
				</div>
			</div>
			<div class="button_wrap clear_both">
		        <div class="button_div">
		        	<input id="query_button" name="query_button" type="button" class="button_blue" value="查询" onclick="doSubmit();">
			    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
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
	                        <td>车牌号码</td>
	                        <td>车辆类型</td>
	                        <td>入城时间</td>
	                        <td>抓拍地点</td>
	                        <td>详情</td>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach items="${pageResult.items }" var="s" varStatus="c">
	                    	<tr>
	                       <td>${c.index + 1 }</td>
	                        <td>${s.cphm }</td>
	                       	<td>${cplxMap[s.cplx] }</td>
	                        <td>${s.tgsj_str}</td>
	                        <td>${jcdMap[s.jcdid] }</td>
	                        <td><a href="javascript:showSb('${s.tpid1 }');">过车详情</a></td>
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
		var list_url = "<%=basePath%>gjfx/ccrc.do";
		//根据页号查询
		function doGoPage(pageNo) {
			layer.load();
			document.getElementById("pageNo").value = pageNo;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		$(function(){
			//车牌颜色多选下拉
			var value = []; 
			var data = [];
		    var dicJson = ${cplxList };
			for(var i=0;i < dicJson.length;i++){
				value.push(dicJson[i].typeSerialNo);
				data.push(dicJson[i].typeDesc);
			}
		    $('.multi_select').MSDL({
				'value': value,
		      	  'data': data
		    });
		    //条件回显，勾选上次选定的项
		    var bkfwValue = '${cplx}'.split(";"); 
		    $('.multi_select').children($('.container')).children($('.contents')).children().each(function(){
				for(var i in bkfwValue){
					if(bkfwValue[i] == $(this).children("input").val()){
						$(this).children("input").attr("checked","checked");
						$(this).children("input").trigger("change");
					};
				};
			});
		});
		function initTime(){
			$("#kssj").val(moment().subtract(30,"days").format("YYYY-MM-DD hh:mm:ss"));
			$("#jssj").val(moment().format("YYYY-MM-DD hh:mm:ss"));
		}
		//提交表单
		function doSubmit(){
			if(!commonCheck(false,true)){//通用验证
				return;
			}
			var kssj = $.trim($("#kssj").val());//起始时间
			var jssj = $.trim($("#jssj").val());//截止时间
			layer.load();
			$("#errSpan").text("");
			$("#pageNo").val("1");
			$("#form1").submit();
		}
		function doReset(){
			$("#errSpan").text("");
			$("#cplx").val("");
			$(".container input").prop("checked", false);
			initTime();
		}
		function showSb(tpid){
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];  
			var url = basePath + "/clcx/getSb.do?tpid="+tpid;
			layer.open({
			  type: 2,
			  title:false,
			  area:['826px','600px'],
			  closeBtn:2,
			  shadeClose:true,
			  content: url
			});
		}
	</script>
</html>