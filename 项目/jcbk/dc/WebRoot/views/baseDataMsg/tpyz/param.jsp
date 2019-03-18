<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>参数</title>
<style type="text/css">
	*{margin: 0;padding: 0;}
	body{font-size: 14px;color: #333;font-family: "Microsoft YaHei";overflow:hidden; }
	input, button, textarea, select {font-family: inherit;font-size: inherit;font-weight: inherit;vertical-align: middle;}
	
	.button_blue{
		padding: 2px 8px;
		border: none;
		color: #fff;
		background: #08f;
		/*behavior: url(ie-css3.htc);*/
		border-radius: 6px;
		outline: none;
		cursor: pointer;
		border: 1px solid #fff;
	}
	.button_blue:hover{
		background: #fff;
		border: 1px solid #ccc;
		color: #555;
	}
	.button_white{
		padding: 2px 8px;
		border: none;
		color: #555;
		background: #F2F2F2;
		/*behavior: url(ie-css3.htc);*/
		border-radius: 6px;
		outline: none;
		cursor: pointer;
		border: 1px solid #ccc;
	}
	.button_white:hover{
		background: #fff;
		border: 1px solid #ccc;
		color: #555;
	}
	.input_text{
		border: 1px solid #CDC28D; /* 输入框表框颜色*/
		height: 22px; /*输入框高度*/
		width: 180px; /*输入框宽度*/
	}
	table{
		margin: 8px;
	}
	table td{
		padding: 2px 0;
	}
</style>
</head>
<body>
	<table>
		<tr>
			<td>最小距离(km):</td>
			<td><input type="text" class="input_text" id="distance" name="distance"></td>
		</tr>
		<tr>
			<td>速度(km/h):</td>
			<td><input type="text" class="input_text" id="speed" name="speed"></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input id="ensure" class="button_blue" type="button" value="确定">
				<input id="cancel" class="button_white" type="button" value="取消">
			</td>
		</tr>
	</table>
	</div>
</body>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
<script type="text/javascript">
	var reg = /^\+?[1-9][0-9]*$/;
	$(function(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$("#ensure").click(function(){
			if(!reg.test($("#distance").val())){
				layer.msg('请输入正确的距离!');
				$("#distance").val('');
				return;
			}
			if(!reg.test($("#speed").val())){
				layer.msg('请输入正确的速度!');
				$("#speed").val('');
				return;
			}
			parent.$("#distance").val($.trim($("#distance").val()));
			parent.$("#speed").val($.trim($("#speed").val()));
			parent.$("#isUpdate").val(Math.random());
			parent.$("#isUpdate").trigger("change");
		});
		$("#cancel").click(function(){
			parent.layer.close(index);
		});
		$("#distance").change(function(){
			if(!reg.test($(this).val())){
				layer.msg('请输入正确的距离!');
				$(this).val('');
			}
		});
		$("#speed").change(function(){
			if(!reg.test($(this).val())){
				layer.msg('请输入正确的速度!');
				$(this).val('');
			}
		});
	});
</script>
</html>