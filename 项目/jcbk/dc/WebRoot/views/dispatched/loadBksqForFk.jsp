<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map,com.dyst.systemmanage.entities.*,com.dyst.utils.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User userObj = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>详细信息</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/activiti/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/laydate-master/laydate.dev.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jqueryauto.js"></script>
		<style type="text/css">
			.slider_body_textarea{
				width:95%;
			}
		</style>
	</head>
	<body>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style="color:#333; font-weight:bold; font-size: large; margin-left: 30px;">拦截反馈</legend>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;反馈人：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="fkrmc" name="fkrmc" type="text" class="slider_input" readonly="readonly" value="<%=userObj.getUserName() %>"/>
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;反馈单位：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="fkbmmc" name="fkbmmc" type="text" class="slider_input" readonly="readonly" value="<%=userObj.getDeptName() %>" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap input_wrap_select">
							<input id="fkrlxdh" name="fkrlxdh" type="text" class="slider_input"/>
							<a id="fkrlxdh" class="empty"></a>
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
			    <div class="slider_selected_left">
			        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>处置结果：</span>
			    </div>
			    <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
			        <input class="input_select xiala" id="czjg" type="text" readonly="readonly" value="==请选择=="/>
			        <input type="hidden" name="czjg" value=""/> 
			        <div class="ul">
			        	<div class="li" data-value="" onclick="sliders(this);czjg();">==请选择==</div>
					    <div class="li" data-value="1" onclick="sliders(this);czjg();">无效报警</div>
			            <div class="li" data-value="2" onclick="sliders(this);czjg();">移交处置</div>
					</div>
			    </div>
			</div>
			<div id="sfljDiv" class="slider_body">
			    <div class="slider_selected_left">
			        <span>&nbsp;&nbsp;&nbsp;<span id="sfljId" style="color: red;">*</span>是否拦截到：</span>
			    </div>
			    <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
			        <input class="input_select xiala" id="sflj" type="text" readonly="readonly" value="==请选择=="/>
			        <input type="hidden" name="sflj" value="" /> 
			        <div class="ul">
			        	<div class="li" data-value="" onclick="sliders(this);sflj();">==请选择==</div>
					    <c:forEach items= "${dicList}" var="dic">
			    	  		<c:if test="${dic.typeCode eq 'SFLJ' }">
			                	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);sflj();">${dic.typeDesc}</div>
			    	  		</c:if> 
					 	</c:forEach>
					</div>
			    </div>
			</div>
			<div id="wljdyyDiv" class="slider_body">
			    <div class="slider_selected_left">
			        <span><span id="wljdyyId" style="color: red;">*</span>未拦截到原因：</span>
			    </div>
			    <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
			        <input class="input_select xiala" id="wljdyy" type="text" readonly="readonly" value="==请选择=="/>
			        <input type="hidden" name="wljdyy" value="" /> 
			        <div class="ul">
			        	<div class="li" data-value="" onclick="sliders(this);">==请选择==</div>
					    <c:forEach items="${dicList}" var="dic">
			    	  		<c:if test="${dic.typeCode eq 'WLJDYY' }">
			                	<div class="li" data-value="${dic.typeSerialNo}" onclick="sliders(this);">${dic.typeDesc}</div>
			    	  		</c:if> 
					 	</c:forEach>
					</div>
			    </div>
			</div>
			<div id="ddrDiv" class="slider_body">
				<div class="slider_selected_left">
					<span id="ddr_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;<span id="ddrId" style="color: red;">*</span>负责人警号：</span>
				</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap input_wrap_select">
							<input id="ddr" name="ddr" type="text" class="slider_input"/>
							<a id="ddr" class="empty"></a>
						</div>
					</div>
				</div>
			</div>
			<div id="xbrDiv" class="slider_body">
				<div class="slider_selected_left">
					<span id="xbr_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;<span id="xbrId" style="color: red;">*</span>协办人警号：</span>
				</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap input_wrap_select">
							<input id="xbr" name="xbr" type="text" class="slider_input"/>
							<a id="xbr" class="empty"></a>
						</div>
					</div>
				</div>
			</div>
			<div id="zhrsDiv" class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="zhrsId" style="color: red;">*</span>抓获人数：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap input_wrap_select">
							<input id="zhrs" name="zhrs" type="text" class="slider_input" maxlength="10" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
							<a id="zhrs" class="empty"></a>
						</div>
					</div>
				</div>
			</div>
			<div id="phajsDiv" class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;<span id="phajsId" style="color: red;">*</span>破获案件数：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap input_wrap_select">
							<input id="phajs" name="phajs" type="text" class="slider_input" maxlength="10" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
							<a id="phajs" class="empty"></a>
						</div>
					</div>
				</div>
			</div>
			<div id="chsjDiv" class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="chsjId" style="color: red;">*</span>查获时间：
				</div>
				<div class="slider_selected_right" style="">
					<div class="demolist">
						<input class="inline laydate-icon" id="chsj" name="chsj" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
					</div>
				</div>
			</div>
			<div id="chddDiv" class="slider_body">
				<div class="slider_selected_left">
					<span id="chdd_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="chddId" style="color: red;">*</span>查获地点：</span>
				</div>
				<div class="slider_selected_right" style="z-index: 122;">
					<div class="input_select xiala" style="z-index: 122;">
						<input id="jcdid" name="jcdid" type="text" style="display: none;"/>
						<input id="word" name="word" type="text" class="slider_input" maxlength="100"/>
	                    <div id="auto" style="height:200px; z-index: 122; overFlow:hidden;background-color: white; display: none;"></div>
					</div>
				</div>
			</div>
			<div class="slider_body_textarea">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>处置描述：
				</div>
				<div class="slider_selected_right">
				    <textarea name="fknr" id="fknr" rows="3" style="width:951px"></textarea>
				</div>
			</div>
			<div class="clear_both">
				<input type="button" class="button_blue" value="确定" onclick="sign()">
				<input type="button" class="button_blue" value="返回" onclick="closeLayer()">
			</div>
		</fieldset>
		<input id="fkPath" name="fkPath" type="text" style="display: none;" value="<%=basePath + "jcd/getJcdListByJcdmc.do" %>"/>
		<div class="mask"></div>
	</body>
	<script type="text/javascript">
		$(function() {
			$("#ddr_span").mouseover(function(){
				showTip("#ddr_span", "多个警号之间以半角逗号隔开！");
			});
			$("#ddr_span").mouseleave(function(){
				layer.closeAll('tips');
			});
			$("#xbr_span").mouseover(function(){
				showTip("#xbr_span", "多个警号之间以半角逗号隔开！");
			});
			$("#xbr_span").mouseleave(function(){
				layer.closeAll('tips');
			});
			$("#chdd_span").mouseover(function(){
				showTip("#chdd_span", "输入监测点名称部分内容可查询相关内容的点位，如输入‘深南’，可获取包含‘深南’字样的所有点位信息！");
			});
			$("#chdd_span").mouseleave(function(){
				layer.closeAll('tips');
			});
		});
		
		//显示车牌号码输入提示
		function showTip(id, message){
			layer.open({
				type: 4,
				shade: 0,
				time:16000,
				closeBtn: 0,
				tips: [3, '#758a99'],
				content: [message, id]
			});
		}
		
		function sign() {
			var fkrlxdh = $("#fkrlxdh").val();
			var czjg = $("input[name='czjg']").val();
			var sflj = $("input[name='sflj']").val();
			var wljdyy = $("input[name='wljdyy']").val();
			var ddr = $("#ddr").val();
			var xbr = $("#xbr").val();
			var zhrs = $("#zhrs").val();
			var phajs = $("#phajs").val();
			var fknr = $("#fknr").val();
			var chsj = $("#chsj").val();
			var chdd = $("#word").val();
			
			if(czjg == ""){
				alert("请选择处置结果！");
				return;
			} else if(czjg == '2'){
				if(sflj == ""){
					alert("请选择是否拦截到！");
					return;
				}
				if(sflj == "0"){
					if(wljdyy == ""){
						alert("请选择未拦截到原因！");
						return;
					}
				} else {//拦截到，该项为kong
					wljdyy = "";
				}
				
				if(ddr == ""){
					alert("请填写负责人警号！");
					return;
				}
				if(xbr == ""){
					alert("请填写协办人警号！");
					return;
				}
				
				if(sflj == "1"){
					if(zhrs == ""){
						alert("请填写抓获人数！");
						return;
					}
					if(phajs == ""){
						alert("请填写破获案件数！");
						return;
					}
					if(chsj == ""){
						alert("请填写查获时间！");
						return;
					}
					if(chdd == ""){
						alert("请填写查获地点！");
						return;
					}
				} else {//未拦截到，这两项为空
					zhrs = "";
					phajs = "";
					chsj = "";
					chdd = "";
				}
			} else if(czjg == '1'){//无效预警，其他为空
				sflj = "";
				wljdyy = "";
				ddr = "";
				xbr = "";
				zhrs = "";
				phajs = "";
				chsj = "";
				chdd = "";
			}
			
			if(fknr == ""){
				alert("请填写处置描述！");
				return;
			}
			
			//显示进度条
			var index = layer.load();
			
			$.ajax({
				url : '${pageContext.request.contextPath}/dispatched/feedBack.do',
				data : {
					bkid : '${bkid }',
					fkrlxdh : fkrlxdh,
					czjg : czjg,
					sflj : sflj,
					wljdyy : wljdyy,
					ddr : ddr,
					xbr : xbr,
					zhrs : zhrs,
					phajs : phajs,
					fknr : fknr,
					chsj : chsj,
					chdd : chdd
				},
				dataType : "json",
				type : "POST", //请求方式
				success : function(data) {
					//关闭进度条
					layer.close(index);
					if(data == "success"){
						alert("反馈成功！");
						parent.doSearch();
						closeLayer();
					} else if(data == "repeat"){
						alert("该布控车辆已被拦截，请查看详情！");
				    	parent.doSearch();
						closeLayer();
				    } else if(data == "fail"){
				    	alert("反馈失败！");
				    }
				}
			});
		}
		
		function closeLayer() {
			//获取窗口索引关闭窗口
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
		
		function czjg(){
			var czjg = $("input[name='czjg']").val();
			if(czjg == '1'){
				$("#sfljDiv").hide();
				$("#ddrDiv").hide();
				$("#xbrDiv").hide();
				$("#zhrsDiv").hide();
				$("#phajsDiv").hide();
				$("#wljdyyDiv").hide();
				$("#chsjDiv").hide();
				$("#chddDiv").hide();
			} else {
				$("#sfljDiv").show();
				$("#ddrDiv").show();
				$("#xbrDiv").show();
				$("#zhrsDiv").show();
				$("#phajsDiv").show();
				$("#wljdyyDiv").show();
				$("#chsjDiv").show();
				$("#chddDiv").show();
			}
			
			sflj();
		}
		
		function sflj(){
			var sflj = $("input[name='sflj']").val();
			var czjg = $("input[name='czjg']").val();
			if(sflj == '1' && czjg == '2'){
				//$("#zhrsId").css("visibility","hidden");
				$("#wljdyyDiv").hide();
				$("#zhrsDiv").show();
				$("#phajsDiv").show();
				$("#chsjDiv").show();
				$("#chddDiv").show();
			} else if(sflj == '0' && czjg == '2'){
				//$("#zhrsId").css("visibility","visible");
				$("#wljdyyDiv").show();
				$("#zhrsDiv").hide();
				$("#phajsDiv").hide();
				$("#chsjDiv").hide();
				$("#chddDiv").hide();
			}
		}
	</script>
</html>