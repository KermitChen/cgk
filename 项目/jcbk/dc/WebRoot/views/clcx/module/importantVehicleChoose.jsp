<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重点车辆选择</title>
<link rel="stylesheet" href="<%=basePath%>common/css/sb/importChoose.css" type="text/css">
<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
<script type="text/javascript" src="<%=basePath%>common/js/sb/importChoose.js"></script>
</head>
<body>
	<div class="container">
		<div class="header">
			<div class="slider_body">
				<input class="input_text grayTip" type="text" id="hphm" value="按车牌号模糊查询">
			</div>
			<div class="slider_body1">
				<input type="radio" id="ck1" name="searchFlag" value="0" checked="checked"/>假牌&nbsp;&nbsp;
				<input type="radio" id="ck2" name="searchFlag" value="1"/> 套牌&nbsp;&nbsp;
				<input type="radio" id="ck3" name="searchFlag" value="2"/> 布控&nbsp;&nbsp;
			</div>
			<div class="slider_body2">
				<input class="button_blue" type="button" value="查询" id="find_button">
			</div>
		</div>
		<div class="bodyer">
			<div id="left_data" class="lefter">
			
			</div>
			<div class="centre">
				<div class="right_arrows" id="right_arrows"></div>
				<div class="left_arrows" id="left_arrows"></div>
				<div class="double_right_arrows" id="double_right_arrows"></div>
				<div class="double_left_arrows" id="double_left_arrows"></div>
			</div>
			<div id="right_data" class="righter">
			
			</div>
		</div>
		<div class="footer">
			<input id="ensure" class="button_blue" type="button" value="确定">
			<input id="cancel" class="button_white" type="button" value="取消">
		</div>
	</div>
</body>
</html>