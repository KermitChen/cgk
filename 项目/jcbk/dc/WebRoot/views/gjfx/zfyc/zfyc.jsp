<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>昼伏夜出车辆分析</title>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<style type="text/css">
		.pg_result{
			height:auto !important;
			height:200px;
			min-height:200px
		}
		#data_div{
			display:block;
			float:left;
			width:100%;
			height:450px;
			OVERFLOW: auto; 
		}
		.layer_span:hover {
			color:#FF0000 !important;
		}
	</style>
	<div id="divTitle">
		<span id="spanTitle">昼伏夜出车辆分析</span>
	</div>
    <div class="content">
    	<div class="content_wrap">
    	 	<form id="form1" name="form1" action="<%=basePath%>gjfx/zfyc.do" method="post">
    	 	<div class="slider_body">
	                <div class="slider_selected_left">
	                    <span id="hphm_span" class="layer_span" style="color:#900b09">车牌号码：</span>
	                </div>
	                <div class="slider_selected_right">
	                    <div class="img_wrap">
	                        <div class="select_wrap input_wrap_select">
	                            <input id="cphid" name="hphm" type="text" class="slider_input" value="${hphm }">
	                            <a class="empty" href="javascript:doCplrUI()"></a>
	                        </div>
	                    </div>  
	                </div>
	        	</div>
	        
	       
	        <div class="slider_body" >
			  <div class="slider_selected_left">
				<span>分析时间：<span style="color:red;">*</span></span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="kssj" name="kssj" value="${kssj }" 
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
				  </div>
				</div>
			</div>
			<div class="slider_body" >
			  <div class="slider_selected_left">
				<span>至</span>
			  </div>
				<div class="slider_selected_right">
				  <div class="demolist" style="">
				    <input class="inline laydate-icon" id="jssj" name="jssj" value="${jssj }" 
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
				  </div>
				</div>
			</div>
	        <div class="slider_body"> 
					<div class="slider_selected_left">
		                    <span id="zybl_span" class="layer_span" style="color:#900b09">昼夜比例：<span style="color:red;">*</span></span>
		            </div>
	                <div class="slider_selected_right" style="z-index:0;">
	                    <div class="img_wrap">
	                        <div class="select_wrap select_input_wrap">
	                            <input id="zybl" class="slider_input" type="text" value="${zybl }" name="zybl" />
	                            <a id="zybl" class="empty"></a>
	                        </div>
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
					<span id="errSpan"  style="color:red;margin-left:auto;margin-right:auto" >${pageResult.errorMessage }</span>
		    	</div>
	    	</div>
	        <div class="pg_result">
	        	<div id="data_div">
	            <table>
	                <thead>
	                    <tr>
	                        <td>序号</td>
	                        <td>车牌号码</td>
	                        <td>昼伏次数</td>
	                        <td>夜出次数</td>
	                        <td>详情</td>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach items="${pageResult.items }" var="s" varStatus="c">
	                    	<tr>
	                       <td>${c.index + 1 }</td>
	                        <td>${s.cphm }</td>
	                       	<td>${s.zfcs }</td>
	                        <td>${s.yccs }</td>
	                        <td><a href="javascript:showSbTable('${s.cphm }')">过车详情</a></td>
	                    	</tr>
	                    </c:forEach>
	                </tbody>
	            </table>
	            </div>
	        </div>
			</form>
        </div>
    </div>
    <jsp:include page="/common/Foot.jsp" />
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript">
		var list_url = "<%=basePath%>gjfx/zfyc.do";
		//根据页号查询
		function doGoPage(pageNo) {
			layer.load();
			document.getElementById("pageNo").value = pageNo;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		$(function(){
			$("#hphm_span").mouseover(function(){
				layer.open({
			           type: 4,
			           shade: 0,
			           time:8000,
			           closeBtn: 0,
			           tips: [3, '#758a99'],
			           content: ['多个车牌号码查询以半角逗号分隔;车牌模糊查询（单个车牌号码）单个字符以?代替,多个字符以*代替.','#hphm_span']
			       });
			});
			$("#hphm_span").mouseleave(function(){
				layer.closeAll('tips');
			});
			$("#zybl_span").mouseover(function(){
				layer.open({
			           type: 4,
			           shade: 0,
			           time:8000,
			           closeBtn: 0,
			           tips: [3, '#758a99'],
			           content: ['白天出行次数比夜晚出行的次数,值越小说明车辆夜晚活动越频繁.','#zybl_span']
			       });
			});
			$("#zybl_span").mouseleave(function(){
				layer.closeAll('tips');
			});
		});
		function initTime(){
			$("#kssj").val(moment().subtract(30,"days").format("YYYY-MM-DD"));
			$("#jssj").val(moment().format("YYYY-MM-DD"));
		}
		//提交表单
		function doSubmit(){
			if(!commonCheck(false,true)){//通用验证
				return;
			}
			var zybl = $.trim($("#zybl").val());
			var reg = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/;
			if(isEmpty(zybl) || !reg.test(zybl) ||parseFloat(zybl) <= 0 || parseFloat(zybl) >= 1){
				layer.msg("昼夜比例填写错误,应大于0小于1!");
				return;
			}
			layer.load();
			$("#errSpan").text("");
			$("#pageNo").val("1");
			$("#form1").submit();
		}
		function doReset(){
			$("#errSpan").text("");
			$("#cqid").val("");
			$("#cq_xiala").val("==全部==");
			initTime();
		}
		//列表展示过车数据
		function showSbTable(hphm){
			var kssj = $.trim($("#kssj").val());
			var jssj = $.trim($("#jssj").val());
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			var url = basePath + "/clcx/showZfycSb.do?hphm="+hphm+"&kssj="+kssj+"&jssj="+jssj;
			layer.open({
		           type: 2,
		           title: '过车详情',
		           shadeClose: true,
		           shade: 0.1,
		           area: ['850px', '650px'],
		           content: url
		       });
		}
	</script>
</html>