<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通知详情</title>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<style type="text/css">
		.button_blue{
			padding: 6px 18px;
		    border: none;
		    color: #fff;
		    background: url("${pageContext.request.contextPath}/common/images/submit_b.jpg") center no-repeat;
		    border-radius: 6px;
		    outline: none;
		    cursor: pointer;
		    margin: 4px 10px;
		    border: 1px solid #fff;
		}
		.button_blue:hover{
			color: #555;
			background: url("${pageContext.request.contextPath}/common/images/submit_g.jpg") center no-repeat;
		}
		input{
			border-style:none;
			readonly:readonly;
			color:blue;
		}
		span{
			color:blue;
			font-size:11pt;
			font-family:"Microsoft YaHei";
		}
		td{
			align:left;
			font-size:11pt;
			font-family:"Microsoft YaHei";
		}
		th{
			align:center;
		}
		.button_div{
			display: block;
		    float: left;
		    height: 40px;
		    margin-top: 8px;
		    position: relative;
		    width: 100%;
		    text-align: center;
		}
	</style>
</head>
<body>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">
					<c:if test="${disReceive.bkckbz eq '1' }">布控通知</c:if>
					<c:if test="${disReceive.bkckbz eq '2' }">撤控通知</c:if>
				</legend>
			<table width="100%">
				<tr>
					<td>
						号牌号码:&nbsp;&nbsp;<span>${disReceive.hphm }</span>
					</td>
					<td>
						号牌种类:&nbsp;&nbsp;<span>
							<c:forEach items= "${dicList}" var="dic">
		            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
					            	<c:if test="${dic.typeSerialNo eq disReceive.hpzl }">
						                		${dic.typeDesc }
						            </c:if>
					      	  	</c:if> 
							</c:forEach>
						</span>
					</td>
					<td>
						布控类别:&nbsp;&nbsp;<span>
							<c:forEach items= "${bklb}" var="b">
					           	  		<c:if test="${b.ID eq disReceive.bklb }">
						                   		${b.NAME }
					          	  		</c:if> 
							</c:forEach>
						</span>
					</td>
				</tr>
				<tr>
					<td>
						通知人:&nbsp;&nbsp;<span>${disReceive.tzr }</span>
					</td>
					<td>
						通知单位:&nbsp;&nbsp;
						<span>
							${disReceive.xfdwmc }
						</span>
					</td>
					<td>
						通知时间:&nbsp;&nbsp;<span>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${disReceive.xfsj }" />
						</span>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						通知内容:&nbsp;&nbsp;<span>${disReceive.tznr }</span>
					</td>
				</tr>
			</table>
		</fieldset>
	<c:if test="${disReceive.qrzt eq '1' }">
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">签收信息</legend>
			<table width="100%">
				<tr>
					<td>
						签收人:&nbsp;&nbsp;<span>${disReceive.qrr }</span>
					</td>
					<td>
						签收单位:&nbsp;&nbsp;<span>${disReceive.qrdwmc }</span>
					</td>
					<td>
						签收时间:&nbsp;&nbsp;<span>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${disReceive.qrsj }" />
						</span>
					</td>
				</tr>
				<tr>
					<td>
						联系电话:&nbsp;&nbsp;<span>${disReceive.qrdwlxdh }</span>
					</td>
					<td colspan="2">
						签收状态:&nbsp;&nbsp;
						<span>
							<c:if test="${disReceive.qrzt eq '1'}">已签收</c:if>
							<c:if test="${disReceive.qrzt eq '0'}">未签收</c:if>
						</span>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						确认结果简述:&nbsp;&nbsp;<span>${disReceive.qrjg }</span>
					</td>
				</tr>
			</table>
		</fieldset>
	</c:if>
	<div class="button_div">
		<input type="button" class="button_blue" value="返回" onclick="closeLayer()">
	</div>
</body>

<script type="text/javascript">
	function closeLayer() {
		//获取窗口索引关闭窗口
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
</script>
</html>