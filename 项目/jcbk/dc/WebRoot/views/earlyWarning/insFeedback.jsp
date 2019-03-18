<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map,com.dyst.systemmanage.entities.*,com.dyst.utils.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<style type="text/css">
			.slider_body_textarea{
				width:95%;
			}
		</style>
	</head>
	<body>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style="color:#333; font-weight:bold; font-size: large; margin-left: 30px;">预警信息</legend>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号牌号码：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="hphm" name="hphm" type="text" class="slider_input"
								readonly="readonly"
								value="${insSign.instruction.ewrecieve.hphm }"
								style="background-color:#FFFFFF;" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号牌种类：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="hpzlInput" name="hpzlInput" type="text" class="slider_input"
								readonly="readonly"
								value="${insSign.instruction.ewrecieve.hpzl }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行驶车道：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="cdid" name="cdid" type="text" class="slider_input"
								readonly="readonly"
								value="${insSign.instruction.ewrecieve.cdid }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;通过时间：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="sbsj" name="sbsj" type="text" class="slider_input"
								readonly="readonly"
								value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${insSign.instruction.ewrecieve.tgsj }"/>" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上传时间：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="scsj" name="scsj" type="text" class="slider_input"
								readonly="readonly"
								value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${insSign.instruction.ewrecieve.scsj }"/>" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报警时间：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="bjsj" name="bjsj" type="text" class="slider_input"
								readonly="readonly"
								value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${insSign.instruction.ewrecieve.bjsj }"/>" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行驶速度：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="sd" name="sd" type="text" class="slider_input"
								readonly="readonly"
								value="<c:if test="${ewRecieve.sd ne '0.0'}">${insSign.instruction.ewrecieve.sd }</c:if>" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点：
				</div>
				<div class="slider_selected_right">
					<textarea id="jcdid" style="width: 580px;height: 20px;"
						readonly="readonly">${insSign.instruction.ewrecieve.jcdmc }</textarea>
				</div>
			</div>
		</fieldset>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style="color:#333; font-weight:bold; font-size: large; margin-left: 30px;">预警确认信息</legend>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确认人：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="qrr" name="qrr" type="text" class="slider_input" readonly="readonly" value="${insSign.instruction.ewrecieve.qrr }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确认单位：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="qrdwdmmc" name="qrdwdmmc" type="text" class="slider_input" readonly="readonly" value="${insSign.instruction.ewrecieve.qrdwdmmc }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="qrdwlxdh" name="qrdwlxdh" type="text" class="slider_input" readonly="readonly" value="${insSign.instruction.ewrecieve.qrdwlxdh }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确认状态：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="qrztmc" name="qrztmc" type="text" class="slider_input" readonly="readonly" value="${insSign.instruction.ewrecieve.qrztmc }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拦截条件：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="ljtj" name="ljtj" type="text" class="slider_input" readonly="readonly" value="<c:if test="${insSign.instruction.ewrecieve.jyljtj eq '0' }">不具备拦截条件</c:if><c:if test="${insSign.instruction.ewrecieve.jyljtj eq '1' }">具备拦截条件</c:if>" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body_textarea">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>确认描述：</span>
				</div>
				<div class="slider_selected_right">
				    <textarea name="yjqrjg" id="yjqrjg" rows="3" style="width:951px" readonly="readonly">${insSign.instruction.ewrecieve.qrjg }</textarea>
				</div>
			</div>
		</fieldset>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style="color:#333; font-weight:bold; font-size: large; margin-left: 30px;">指令信息</legend>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;下发指令单位：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="zlxfbmmc" name="zlxfbmmc" type="text" class="slider_input" readonly="readonly" value="${insSign.instruction.zlxfbmmc }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预警级别：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="jqjb" name="jqjb" type="text" class="slider_input" readonly="readonly" value="${insSign.instruction.jqjb }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;下发指令人：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="zlxfrmc" name="zlxfrmc" type="text" class="slider_input" readonly="readonly" value="${insSign.instruction.zlxfrmc }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;下发指令时间：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="xfsj" name="xfsj" type="text" class="slider_input" readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${insSign.instruction.xfsj }"/>" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;预计达到卡口：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="kdmc" name="kdmc" type="text" class="slider_input" readonly="readonly" value="${insSign.kdmc }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;预计达到时间：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="yjsj" name="yjsj" type="text" class="slider_input" readonly="readonly" value="${insSign.yjsj }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body_textarea">
				<div class="slider_selected_left">
				 	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预案内容：
				</div>
				<div class="slider_selected_right">
					<textarea name="yanr" id="yanr" rows="3" style="width:951px" readonly="readonly">${insSign.instruction.yanr }</textarea>
				</div>
			</div>
		</fieldset>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style="color:#333; font-weight:bold; font-size: large; margin-left: 30px;">指令签收信息</legend>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签收状态：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="qsztmc" name="qsztmc" type="text" class="slider_input" readonly="readonly" value="${insSign.qsztmc }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签收人：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="qsr" name="qsr" type="text" class="slider_input" readonly="readonly" value="${insSign.qsrmc }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签收单位：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="qsbm" name="qsbm" type="text" class="slider_input" readonly="readonly" value="${insSign.qsbmmc }" />
						</div>
					</div>
				</div>
			</div>
			<div class="slider_body">
				<div class="slider_selected_left">
				    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话：
				</div>
				<div class="slider_selected_right" style="">
				    <div class="img_wrap">
				        <div class="select_wrap select_input_null">
							<input id="qrrlxdh" name="qrrlxdh" type="text" class="slider_input" readonly="readonly" value="${insSign.qsrlxdh }" />
						</div>
				    </div>  
				 </div>
		    </div>
		    <div class="slider_body_textarea">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;visibility: hidden;">*</span>签收描述：</span>
				</div>
				<div class="slider_selected_right">
				    <textarea name="qrjg" id="qrjg" rows="3" style="width:951px" readonly="readonly">${insSign.qsms }</textarea>
				</div>
			</div>
		</fieldset>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style="color:#333; font-weight:bold; font-size: large; margin-left: 30px;">指令反馈</legend>
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
		<div class="mask"></div>
	</body>
	<script type="text/javascript">
		$(function() {
			var dic = jQuery.parseJSON('${dicListJSON}');
			for(var i=0;i < dic.length;i++){
				if(dic[i].typeCode == "HPZL" && '${insSign.instruction.ewrecieve.hpzl }' == dic[i].typeSerialNo){
					$("#hpzlInput").val(dic[i].typeDesc);
				} 
				if(dic[i].typeCode == "BKJB" && '${insSign.instruction.jqjb }' == dic[i].typeSerialNo){
					$("#jqjb").val(dic[i].typeDesc);
				}
			}
			
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
				} else {//未拦截到，这两项为空
					zhrs = "";
					phajs = "";
				}
			} else if(czjg == '1'){//无效预警，其他为空
				sflj = "";
				wljdyy = "";
				ddr = "";
				xbr = "";
				zhrs = "";
				phajs = "";
			}
			
			if(fknr == ""){
				alert("请填写处置描述！");
				return;
			}
			
			//显示进度条
			var index = layer.load();
			
			$.ajax({
				url : '${pageContext.request.contextPath}/earlyWarning/insFeedback.do',
				data : {
					zlqsid : '${insSign.id }',
					fkrlxdh : fkrlxdh,
					czjg : czjg,
					sflj : sflj,
					wljdyy : wljdyy,
					ddr : ddr,
					xbr : xbr,
					zhrs : zhrs,
					phajs : phajs,
					fknr : fknr
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
						alert("已被反馈！");
				    	parent.doSearch();
						closeLayer();
				    } else if(data == "yjzh"){
						alert("该布控车辆已被其他单位成功拦截并记入应用成效，\n一布控预警车辆不允许有多次拦截成功的反馈，具\n体拦截情况请在‘已布控车拦截反馈’中查看详情！");
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
			} else {
				$("#sfljDiv").show();
				$("#ddrDiv").show();
				$("#xbrDiv").show();
				$("#zhrsDiv").show();
				$("#phajsDiv").show();
				$("#wljdyyDiv").show();
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
			} else if(sflj == '0' && czjg == '2'){
				//$("#zhrsId").css("visibility","visible");
				$("#wljdyyDiv").show();
				$("#zhrsDiv").hide();
				$("#phajsDiv").hide();
			}
		}
	</script>
</html>