<%@ page language="java" import="java.util.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+path+"/";
%>
<html lang="en">
	<head>
	</head>
	<body>
		<fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
				<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">
					布控通知
				</legend>
			<div class="slider_body" style="height:20px">
	                <div class="slider_selected_left">
	                    	号牌号码：
	                </div>
	                <div class="slider_selected_right" style="">
	                        ${disReceive.hphm }
	                </div>
	        </div>
			<div class="slider_body" style="height:20px">
	                <div class="slider_selected_left">
	                    	号牌种类：
	                </div>
	                <div class="slider_selected_right" style="">
	                            <c:forEach items= "${dicList}" var="dic">
			            	  		<c:if test="${dic.typeCode eq 'HPZL' }">
						            	<c:if test="${dic.typeSerialNo eq disReceive.hpzl }">
							                	${dic.typeDesc }
							            </c:if>
						      	  	</c:if> 
								</c:forEach>
	                </div>
	        </div>
			<div class="slider_body" style="height:20px">
	                <div class="slider_selected_left">
	                    	布控类别：
	                </div>
	                <div class="slider_selected_right" style="">
	                       		<c:forEach items= "${bklb}" var="b">
					          		<c:if test="${b.ID eq disReceive.bklb }">
						                  ${b.NAME }
					          	  	</c:if> 
								</c:forEach>
	                </div>
	        </div>
	        <div class="slider_body" style="height:20px">
	                <div class="slider_selected_left">
	                    	下发通知人:
	                </div>
	                <div class="slider_selected_right" style="">
	                        ${disReceive.tzr }
	                </div>
	        </div>
	        <div class="slider_body" style="height:20px">
	                <div class="slider_selected_left">
	                    	下发通知单位:
	                </div>
	                <div class="slider_selected_right" style="">
	                        ${disReceive.xfdwmc }
	                </div>
	        </div>
	        <div class="slider_body" style="height:20px">
	                <div class="slider_selected_left">
	                    	下发通知时间：
	                </div>
	                <div class="slider_selected_right" style="">
	                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${disReceive.xfsj }" />
	                </div>
	        </div>
	        <div class="slider_body_textarea" style="height:70px;">
				<div class="slider_selected_left">
					简要案情：
				</div>
				<div class="slider_selected_right">
					<textarea rows="2" style="width:900px" disabled="disabled">${dispatched.jyaq }</textarea>
				</div>
			</div>
			<div class="slider_body_textarea" style="height:70px;">
				<div class="slider_selected_left">
					通知内容：
				</div>
				<div class="slider_selected_right">
					<textarea rows="2" style="width:900px" disabled="disabled">${disReceive.tznr }</textarea>
				</div>
			</div>
		</fieldset>
	    <fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
			<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">布控通知签收情况</legend>
			<div class="pg_result">
				<table>
					<thead>
						<tr>
							<td width="20%" align="center">布控签收单位</td>
							<td width="10%" align="center">签收状态</td>
							<td width="15%" align="center">签收时间</td>
							<td width="10%" align="center">签收人</td>
							<td width="15%" align="center">签收人联系电话</td>
							<td width="30%" align="center">签收内容</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="d" items="${receiveList }" varStatus="status">
							<tr>
								<td>${d.qrdwmc}</td>
								<td>
									<c:if test="${d.qrzt eq '1'}">已签收</c:if>
									<c:if test="${d.qrzt eq '0'}">未签收</c:if>
								</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${d.qrsj }" /></td>
								<td>${d.qrr}</td>
								<td>${d.qrdwlxdh}</td>
								<td>${d.qrjg}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</fieldset>
	</body>
</html>