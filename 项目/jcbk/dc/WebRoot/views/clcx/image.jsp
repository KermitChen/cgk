<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ZoomImg</title>
<script type="text/javascript"
	src="<%=basePath%>common/js/iviewer/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>common/js/iviewer/jqueryui.js"></script>
<script type="text/javascript"
	src="<%=basePath%>common/js/iviewer/jquery.mousewheel.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>common/js/iviewer/jquery.iviewer.js"></script>
<script type="text/javascript">
	var $ = jQuery;
	$(document).ready(function() {
		var iviewer = {};
		$("#viewer2").iviewer({
			src : "${tpUrl }",
			initCallback : function() {
				iviewer = this;
			}
		});
		$("#opt1").click(function() {
			iviewer.loadImage("IMG_8755.jpg");
			return false;
		});
	});
	function option(num) {
		$("#viewer2").html("");
		var iviewer = {};
		$("#viewer2").iviewer({
			src : "test_image.jpg",
			initCallback : function() {
				iviewer = this;
			}
		});
	}
	function option2(num) {
		iviewer.loadImage("IMG_8755.JPG");
		return false;
	}
</script>
<link rel="stylesheet" href="<%=basePath%>common/css/iviewer/jquery.iviewer.css" />
<style>
*{margin: 0;padding: 0;}
body{font-size: 14px;color: #333;font-family: "Microsoft YaHei"; }
ul,li{list-style: none;}
a{text-decoration: none;cursor: pointer;color: #333;}
h1,h2,h3,h4,h5,h6{font-weight: normal !important;}
input, button, textarea, select {font-family: inherit;font-size: inherit;font-weight: inherit;vertical-align: middle;}

.viewer {
	width: 800px;
	height: 600px;
	border: 1px solid black;
	position: relative;
}

.wrapper {
	overflow: hidden;
}
</style>
</head>
<body>
	<!-- wrapper div is needed for opera because it shows scroll bars for reason -->
	<div class="wrapper">
		<div id="viewer2" class="viewer iviewer_cursor"></div>
		<br />
	</div>
</body>
</html>
