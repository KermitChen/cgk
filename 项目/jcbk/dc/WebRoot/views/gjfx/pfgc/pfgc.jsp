<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>频繁过车分析</title>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>common/css/sb/pfgc.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>common/css/barline.css" type="text/css">
	<div id="divTitle">
		<span id="spanTitle">频繁过车分析</span>
	</div>
    <div class="content">
    	<form id="form1" name="form1" action="<%=basePath%>gjfx/pfgc.do" method="post">
    	<input type="hidden" id="resFlag" name="resFlag" value="${resFlag }">
    	<div class="content_wrap">
	                <div class="slider_body">
		            <div class="slider_selected_left">
		                <span>监测点：<span style="color:red;">*</span></span>
		            </div>
		            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
		           		<c:set value="${fn:split(jcdid, ',') }" var="str1" />
		                <input class="input_select xiala" onclick="doChoseJcd();" id="jcdid1" type="text" 
		                	<c:choose>
			            		<c:when test="${jcdid ne '' && jcdid ne null && fn:length(str1) > 0}">value="您选中了${fn:length(str1) }个监测点"</c:when>
			            		<c:otherwise>value="==全部=="</c:otherwise>
			            	</c:choose>
		                /> 
		                <input type="hidden" name="jcdid" id="jcdid" value="${jcdid }">
		            </div>
		        </div>
		        
				<div class="slider_body">
				  <div class="slider_selected_left">
					<span>时间段：<span style="color:red;">*</span></span>
				  </div>
				  <div class="slider_selected_right dropdown dropdowns little_slider_selected_left" id="cp_time1" onclick="slider(this)">
		                <input class="input_select little_xiala" id="xiala1" readonly="readonly" type="text" value="${time1 }"/> 
		                <input type="hidden" id="time1" name="time1" value="${time1 }" />
		                <div class="ul"> 
			                    <div class="li" data-value="00" onclick="sliders(this)">00</div>
			                    <div class="li" data-value="01" onclick="sliders(this)">01</div>
			                    <div class="li" data-value="02" onclick="sliders(this)">02</div>
			                    <div class="li" data-value="03" onclick="sliders(this)">03</div>
			                    <div class="li" data-value="04" onclick="sliders(this)">04</div>
			                    <div class="li" data-value="05" onclick="sliders(this)">05</div>
			                    <div class="li" data-value="06" onclick="sliders(this)">06</div>
			                    <div class="li" data-value="07" onclick="sliders(this)">07</div>
			                    <div class="li" data-value="08" onclick="sliders(this)">08</div>
			                    <div class="li" data-value="09" onclick="sliders(this)">09</div>
			                    <div class="li" data-value="10" onclick="sliders(this)">10</div>
			                    <div class="li" data-value="11" onclick="sliders(this)">11</div>
			                    <div class="li" data-value="12" onclick="sliders(this)">12</div>
			                    <div class="li" data-value="13" onclick="sliders(this)">13</div>
			                    <div class="li" data-value="14" onclick="sliders(this)">14</div>
			                    <div class="li" data-value="15" onclick="sliders(this)">15</div>
			                    <div class="li" data-value="16" onclick="sliders(this)">16</div>
			                    <div class="li" data-value="17" onclick="sliders(this)">17</div>
			                    <div class="li" data-value="18" onclick="sliders(this)">18</div>
			                    <div class="li" data-value="19" onclick="sliders(this)">19</div>
			                    <div class="li" data-value="20" onclick="sliders(this)">20</div>
			                    <div class="li" data-value="21" onclick="sliders(this)">21</div>
		              	  </div> 
	           	 	</div><div class="font_year">点</div>
	           	 	<div class="slider_selected_right dropdown dropdowns little_slider_selected_right"  id="cp_time2" onclick="slider(this)">
		                <input class="input_select little_xiala" id="xiala_time2" readonly="readonly" type="text" value="${time2 }"/> 
		                <input type="hidden" id="time2" name="time2" value="${time2 }" />
		                <div class="ul"> 
			                    <div class="li" data-value="03" onclick="sliders(this)">03</div>
			                    <div class="li" data-value="04" onclick="sliders(this)">04</div>
			                    <div class="li" data-value="05" onclick="sliders(this)">05</div>
			                    <div class="li" data-value="06" onclick="sliders(this)">06</div>
			                    <div class="li" data-value="07" onclick="sliders(this)">07</div>
			                    <div class="li" data-value="08" onclick="sliders(this)">08</div>
			                    <div class="li" data-value="09" onclick="sliders(this)">09</div>
			                    <div class="li" data-value="10" onclick="sliders(this)">10</div>
			                    <div class="li" data-value="11" onclick="sliders(this)">11</div>
			                    <div class="li" data-value="12" onclick="sliders(this)">12</div>
			                    <div class="li" data-value="13" onclick="sliders(this)">13</div>
			                    <div class="li" data-value="14" onclick="sliders(this)">14</div>
			                    <div class="li" data-value="15" onclick="sliders(this)">15</div>
			                    <div class="li" data-value="16" onclick="sliders(this)">16</div>
			                    <div class="li" data-value="17" onclick="sliders(this)">17</div>
			                    <div class="li" data-value="18" onclick="sliders(this)">18</div>
			                    <div class="li" data-value="19" onclick="sliders(this)">19</div>
			                    <div class="li" data-value="20" onclick="sliders(this)">20</div>
			                    <div class="li" data-value="21" onclick="sliders(this)">21</div>
			                    <div class="li" data-value="22" onclick="sliders(this)">22</div>
			                    <div class="li" data-value="23" onclick="sliders(this)">23</div>
			                    <div class="li" data-value="24" onclick="sliders(this)">24</div>
		                </div> 
	           	 	</div><div class="font_month">点</div>
				</div>
	        	<div class="slider_body"> 
					<div class="slider_selected_left">
		                    <span>过车次数(次)：<span style="color:red;">*</span></span>
		            </div>
	                <div class="slider_selected_right" style="z-index:0;">
	                    <div class="img_wrap">
	                        <div class="select_wrap select_input_wrap">
	                            <input id="gccs" class="slider_input" type="text" value="${gccs }" name="gccs" />
	                            <a id="gccs" class="empty"></a>
	                        </div>
	                    </div>  
	               </div>
		        </div>
		       
				<div class="slider_body cxlb_style3">
				  <div class="slider_selected_left">
					<span>分析时间：<span style="color:red;">*</span></span>
				  </div>
					<div class="slider_selected_right">
					  <div class="demolist" style="">
					    <input class="inline laydate-icon" id="kssj" name="kssj" value="${kssj }" />
					  </div>
					</div>
				</div>
				<div class="slider_body cxlb_style3">
				  <div class="slider_selected_left">
					<span>至</span>
				  </div>
					<div class="slider_selected_right">
					  <div class="demolist" style="">
					    <input class="inline laydate-icon" id="jssj" name="jssj" value="${jssj }" />
					  </div>
					</div>
				</div>
				
		        <div class="slider_body">
	               <span style="color:red;">提示：只能选择一个监测点!时间段不可大于3小时!过车次数应大于1!时间最长为7天!</span>
	        	</div>
		        
	        	<div class="button_wrap clear_both">
			    	<input id="query_button" name="query_button" type="button" class="button_blue" value="查询" onclick="doSubmit();">
			    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
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
							<td>车牌号</td>
							<td>车牌颜色</td>
							<td>监测点</td>
							<td>过车次数</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.items }" varStatus="status">
						<tr ondblclick="javascript:showPfgcDetail('${data.hphm}','${data.jcdid }')">
							<td>${status.index+1}</td>
							<td>${data.hphm }</td>
							<td>${cplxMap[data.cplx] }</td>
							<td>${jcdMap[data.jcdid] }</td>
							<td>${data.gccs }</td>
							<td width="180px">
				              	<a href="javascript:showSb('${data.hphm}','${data.jcdid }')" >详情</a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
            	<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
	        </div>
        </div>
        </form>
    </div>
    <div id="bar_content" class="bar_content">
		<!-- 进度条 -->
		<div class="barline" id="probar">
			<div id="percent"></div>
			<div id="line" w="100" style="width:0px;"></div>	
			<div id="msg" style=""></div>			
		</div>
	</div>
    
    <jsp:include page="/common/Foot.jsp" />
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript">
		var list_url = "<%=basePath%>gjfx/pfgc.do";
		var calTime = 5;//计算一次所需时间分钟
		var barlineIndex;
		//根据页号查询
		function doGoPage(pageNo) {
			layer.load();
			document.getElementById("pageNo").value = pageNo;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
		var start = {
		    elem: '#kssj',
		    format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
		    min: '2000-01-01', //设定最小日期为当前日期
		    max: '2099-01-01', //最大日期
			istime: true,
		    choose: function(datas){ //选择日期完毕的回调
		    	//$("#jssj").val(moment(datas,'YYYY-MM-DD').add(7,'days').format('YYYY-MM-DD'));
		    	end.min = moment(datas,'YYYY-MM-DD').add(1,'days').format('YYYY-MM-DD');
		    	end.max = moment(datas,'YYYY-MM-DD').add(7,'days').format('YYYY-MM-DD');
		    }
		};
		var end = {
		    elem: '#jssj',
		    format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
		    min: '2000-01-01', //设定最小日期为当前日期
		    max: '2099-01-01', //最大日期
			istime: true,
		    choose: function(datas){ //选择日期完毕的回调
		    	start.min = moment(datas,'YYYY-MM-DD').subtract(7,'days').format('YYYY-MM-DD');
		    	start.max = moment(datas,'YYYY-MM-DD').subtract(1,'days').format('YYYY-MM-DD');
		    }
		};
		laydate(start);
		laydate(end);
		$(function(){
			$("#cp_time1 .li").click(function(){
				var value = $(this).attr("data-value");
				if(value != null && value != '' && parseInt(value) < 21){
					if((parseInt(value)+3) < 10){
						$("#time2").val('0'+(parseInt(value)+3));
						$("#xiala_time2").val('0'+(parseInt(value)+3));
					}else{
						$("#time2").val((parseInt(value)+3));
						$("#xiala_time2").val((parseInt(value)+3));
					}
				}
			});
			$("#cp_time2 .li").click(function(){
				var time2 = $(this).attr("data-value");
				var time1 = $("#time1").val();
				var t2 = parseInt(time2);
				var t1 = parseInt(time1);
				if((t2 - t1) < 1){
					layer.msg('不可小于起始值!');
					if((t1+1) < 10){
						$("#time2").val('0'+(t1+1));
						$("#xiala_time2").val('0'+(t1+1));
					}else{
						$("#time2").val(t1+1);
						$("#xiala_time2").val(t1+1);
					}
				}else if((t2 - t1) > 3){
					layer.msg('不可大于3小时!');
					if((t1+3) < 10){
						$("#time2").val('0'+(t1+3));
						$("#xiala_time2").val('0'+(t1+3));
					}else{
						$("#time2").val(t1+3);
						$("#xiala_time2").val(t1+3);
					}
				}
			});
		});
		//提交表单
		function doSubmit(){
			var jcdid = $.trim($("#jcdid").val());
			//判断空值
			if((jcdid == "" || jcdid == null)){
				$("#errSpan").text("错误提示：请选择监测点！");
				return;
			}
			if(jcdid.indexOf(',') > 0){
				$("#errSpan").text("错误提示：只能选择一个监测点！");
				return;
			}
			if(!commonCheck(false,true)){
				return;
			}
			var kssj = $("#kssj").val();
			if(moment().diff(moment(kssj)) < 86400000){
				$("#errSpan").text("错误提示：时间选择错误，起始时间不可大于或等于当前日期！");
				return;
			}
			
			var gccs = $("#gccs").val();
			if(gccs == null || gccs == "" || parseInt(gccs) <= 1){
				$("#errSpan").text("错误提示：过车次数输入错误！");
				return;
			}
			$("#errSpan").text("");
			$("#pageNo").val("1");
			$.ajax({
				async:false,
				type: 'POST',
				data:{key:'3'},
				dataType : "json",
				url: '<%=basePath%>/gjfx/getCalCount.do',//请求的action路径
				error: function () {//请求失败处理函数
					layer.confirm('获取计算任务数量失败,是否继续计算？',{icon: 7, title:'提示',btn: ['继续','取消']}
					  , function(index){
						layer.close(index);
						showBarLine(1);
						$("#resFlag").val("");
						$("#form1").submit();
					}, function(index){
						layer.close(index);
					});
				},
				success:function(data){ //请求成功后处理函数。
					layer.confirm('当前有'+data+'个计算任务,是否继续计算？',{icon: 7, title:'提示',btn: ['继续','取消']}
						  , function(index){
							layer.close(index);
							showBarLine(parseInt(data)+1);
							$("#resFlag").val("");
							$("#form1").submit();
						}, function(index){
							layer.close(index);
						});
				}
			});
		}
		
		//列表展示过车数据
		function showSb(hphm,jcdid){
			var kssj = $.trim($("#kssj").val());
			var jssj = $.trim($("#jssj").val());
			var time1 = $.trim($("#time1").val());
			var time2 = $.trim($("#time2").val());
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			var url = basePath + "/clcx/showPfgcSb.do?hphm="+hphm+"&kssj="+kssj+"&jssj="+jssj+"&jcdid="+jcdid+"&time1="+time1+"&time2="+time2;
			layer.open({
		           type: 2,
		           title: '过车详情',
		           shadeClose: true,
		           shade: 0.1,
		           area: ['850px', '600px'],
		           content: url
		       });
		}
		function doReset(){
			$("#errSpan").text("");
			$("#gccs").val("5");
			$("#jcdid").val("");
			$("#jcdid1").val("==全部==");
			$("#kssj").val(moment().subtract(7,"days").format("YYYY-MM-DD hh:mm:ss"));
			$("#jssj").val(moment().add(7,"days").format("YYYY-MM-DD hh:mm:ss"));
		}
	</script>
</html>