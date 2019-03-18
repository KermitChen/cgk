<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map,com.dyst.systemmanage.entities.*,com.dyst.utils.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort() + path + "/";
			//获取用户信息
	User userObj = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>详细信息</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
		<style type="text/css">
		.slider_body_textarea {
			width: 95%;
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
			<legend style="color:#333; font-weight:bold; font-size: large; margin-left: 30px;">确认信息</legend>
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
			<legend style="color:#333; font-weight:bold; font-size: large; margin-left: 30px;">签收指令</legend>
			<div class="slider_body">
				<div class="slider_selected_left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签收人：</div>
				<div class="slider_selected_right" style="">
					<div class="img_wrap">
						<div class="select_wrap select_input_null">
							<input id="qsr" name="qsr" type="text" class="slider_input" readonly="readonly" value="<%=userObj.getUserName() %>" />
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
							<input id="qsbm" name="qsbm" type="text" class="slider_input" readonly="readonly" value="<%=userObj.getDeptName() %>" />
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
				    	<div class="select_wrap input_wrap_select">
				        	<input id="qrrlxdh" name="qrrlxdh" type="text" class="slider_input"/>
				        	<a id="qrrlxdh" class="empty"></a>
				        </div>
				    </div>  
				 </div>
		    </div>
		    <div class="slider_body_textarea">
				<div class="slider_selected_left">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">*</span>签收描述：</span>
				</div>
				<div class="slider_selected_right">
				    <textarea name="qrjg" id="qrjg" rows="3" style="width:951px"></textarea>
				</div>
			</div>
		</fieldset>
		<div class="clear_both">
			<input type="button" class="button_blue" value="签收" onclick="sign();">
		</div>
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
		});
		
		function sign() {
			if($.trim($("#qrjg").val()) == ""){
				alert("请先填写签收描述！");
				return false;
			}
			
			//显示进度条
			var index = layer.load();
			$.ajax({
				url : '${pageContext.request.contextPath}/earlyWarning/insSigned.do',
				data : {zlqsid : '${insSign.id }', qsms:$.trim($("#qrjg").val()), qsrlxdh:$.trim($("#qrrlxdh").val())},
				dataType : "json",
				type : "POST", //请求方式
				success : function(data) {
					//关闭进度条
					layer.close(index);
					if(data == "success"){
						alert("签收成功！");
						parent.doSearch();
						closeLayer();
					} else if(data == "repeat"){
			    		alert("已被签收！");
			    		parent.doSearch();
						closeLayer();
			    	} else if(data == "fail"){
			    		alert("签收失败！");
			    	}
				}
			});
		}
		
		function closeLayer() {
			//获取窗口索引关闭窗口
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
	</script>
</html>