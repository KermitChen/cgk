<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.chariotesttube.entities.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	
	String result = (String)request.getAttribute("result");
	Vehicle vehicle = (Vehicle)request.getAttribute("vehicle");
	if("0".equals(result)){
%>
		<SCRIPT type="text/javascript">
			window.location = '<%=basePath%>/common/errorPage/error500.jsp';
		</SCRIPT>
<%
	} else if("1".equals(result) && vehicle == null){
%>
		<SCRIPT type="text/javascript">
			alert("没有找到符合条件的数据！");
		</SCRIPT>
<%
	} else if("2".equals(result)){
%>
		<SCRIPT type="text/javascript">
			alert("您的身份信息不完善，机动车信息查询需要提供个人身份证号，\n请先提供身份证号给管理员，以便完善身份信息！");
		</SCRIPT>
<%
	} else{
%>
		<SCRIPT type="text/javascript">
			$(document).ready(function(){
				var csys = csysFunction($("#csys").val());
				var clzt = clztFunction($("#jdczt").val());
				$("#csys").val(csys);//车身颜色
				$("#jdczt").val(clzt);//车辆状态
			});
		</SCRIPT>
<%
	}
%>

<div class="slider_body">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中文品牌：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="zwpp" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['clpp1'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车辆型号：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="clxh" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['clxh'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发动机号：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="fdjh" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['fdjh'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body" style="position: relative; clear: both;">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车驾号：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="cjh" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['clsbdh'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车身颜色：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="csys" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['csys'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车辆状态：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="jdczt" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['jdczt'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body" style="position: relative; clear: both;">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车辆类型：</span>
	</div>
	<div class="slider_selected_right" style="">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="cllx" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['cllx'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发牌机关：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="fpjg" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['fpjg'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发证机构：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="fzjg" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['fzjg'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body" style="position: relative; clear: both;">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;车辆所有人：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="jdcsyr" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['jdcsyr'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="syrdh" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['lxfs'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身份证号：</span>
	</div>
	<div class="slider_selected_right">
		<div class="img_wrap">
			<div class="select_wrap select_input_wrap">
				<input id="sfzh" type="text" class="slider_input" readonly="readonly" value="<c:if test="${not empty vehicle}">${vehicle['sfzh'] }</c:if>">
			</div>
		</div>
	</div>
</div>
<div class="slider_body" style="position: relative; clear: both;">
	<div class="slider_selected_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;详细住址：</span>
	</div>
	<div class="slider_selected_right">
		<textarea id="syrxxdz" style="width: 580px;height: 20px;" readonly="readonly"><c:if test="${not empty vehicle}">${vehicle['djzzxz'] }</c:if></textarea>
	</div>
</div>