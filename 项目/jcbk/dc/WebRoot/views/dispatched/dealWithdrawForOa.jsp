<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="zh-CN">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>撤控审批</title>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>
	<body>
		<div class="content">
	    	<div class="content_wrap" style="width:560px;">
	    		<div id="print">
	                <h1 align="center" style="font-weight: bold"><span style='font-size:24.0pt;font-family:黑体'>深圳市公安局车辆撤控审批表</span></h1>
	                <c:set var="position2" value="${fn:substring(user.position,0,2)}" />
	    			<p class=MsoNormal align=center style='text-align:center;tab-stops:232.5pt'>
	    				<span lang=EN-US style='font-size:18.0pt'>[ </span>
    					<span style='font-size:18.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
		    				<c:if test="${fn:length(user.position) == 2 && user.position lt '80' && user.position gt '70'}">派出所</c:if>  
		          			<c:if test="${fn:length(user.position) == 3 }">其他</c:if>  
		          			<c:if test="${fn:length(user.position) == 2 && user.position gt '80' && user.position lt '90'}">分局</c:if>  
		               	 	<c:if test="${fn:length(user.position) == 2 && user.position gt '90' }">市局</c:if> 
		               	</span>
		               	<span lang=EN-US style='font-size:18.0pt'> ]</span> 
		            </p>
		            <p align="right" id="dh">单号：
		            	<c:if test="${position2 lt '80' && position2 gt '70'}">${dispatched.pcsdh }</c:if>  
	<!-- 	          		<c:if test="${fn:length(user.position) == 3}">${dispatched.sjdh }</c:if>   -->
		          		<c:if test="${position2 gt '80' && position2 lt '90'}">${dispatched.fjdh }</c:if>  
		                <c:if test="${position2 gt '90' }">${dispatched.sjdh }</c:if>  
		            </p>
		            <table id="table1" style="border: solid windowtext 2.25pt;">
		                <tbody>
	                        <tr>
	                        	<td rowspan="6" style="border:solid windowtext 2.25pt;width: 18%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>布控信息</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>布控单位</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 62%;text-align: left;" colspan="3">
	                        	  	<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${dispatched.bkjgmc }
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;";>
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>布控申请人</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${dispatched.bkr }
										</span>
									</p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>布控人警号</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${dispatched.bkrjh }
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>布控大类</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span id="bkdl" style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											
										</span>
									</p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>布控类别</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span id="bklb" style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr>
								<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>报警类型</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span id="bjlx" style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											
										</span>
									</p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>联系电话</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${dispatched.bkjglxdh }
										</span>
									</p>
	                        	</td>
	                        </tr>
	                         <tr>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>布控起始时间</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											<fmt:formatDate pattern="yyyy-MM-dd" value="${dispatched.bkqssj }" />
										</span>
									</p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>布控终止时间</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											<fmt:formatDate pattern="yyyy-MM-dd" value="${dispatched.bkjzsj }" />
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr style="height:80px">
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>简要案情</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 60%;text-align: left" colspan="3">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${dispatched.jyaq }
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr>
	                        	<td style="border:solid windowtext 2.25pt;width: 18%;" rowspan="2">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>车辆信息</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;" >
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>号牌号码</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;" >
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${dispatched.hphm }
										</span>
									</p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;" >
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>号牌颜色</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span id="hpys" style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>号牌种类</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span id="hpzl" style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											
										</span>
									</p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>车辆品牌</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${dispatched.clpp }
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr>
	                        	<td style="border:solid windowtext 2.25pt;width: 18%;" rowspan="4">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>撤控信息</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>撤控单位</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 62%;text-align: left;" colspan="3">
	                        	  	<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${withdraw.cxsqdwmc }
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;" >
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>撤控申请人</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;" >
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${withdraw.cxsqr }
										</span>
									</p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>撤控人警号</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${withdraw.cxsqrjh }
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;" >
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>联系电话</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;" >
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${withdraw.cxsqrdh }
										</span>
									</p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>撤控申请时间</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 19%;">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${withdraw.cxsqsj }" />
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr style="height:80px">
	                        	<td style="border:solid windowtext 2.25pt;width: 22%;">
	                        		<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:normal'><span style='font-size:12.0pt;font-family:宋体;'>撤控原因描述</span></b></p>
	                        	</td>
	                        	<td style="border:solid windowtext 2.25pt;width: 60%;text-align: left" colspan="3">
	                        		<p class=MsoNormal align="left" style='text-align:left;'>
	                        	  		<span style='font-size:12.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>
											${withdraw.ckyyms }
										</span>
									</p>
	                        	</td>
	                        </tr>
	                        <tr id="pcsld" style="height:100px;display:none">
	                        	<td style="border:solid windowtext 2.25pt;width: 18%;">
	                        		<p class=MsoNormal align=center style='text-align:center'>
	                        			<b style='mso-bidi-font-weight:normal'>
	                        				<span style='font-size:12.0pt;font-family:宋体;'>
	                        					<c:if test="${user.position eq '72' }">派出所领导<br/><br/>意见</c:if> 
	                        					<c:if test="${fn:length(user.position) == 3}">一级领导<br/><br/>意见</c:if>
	                        				</span>
	                        			</b>
	                        		</p>
	                        	</td>
	                        	<td colspan="4" style="border:solid windowtext 2.25pt;width: 82%;">
	                        		<textarea id='pcsldAdvice' name='pcsldAdvice' rows='3' style='width: 99%;font-size: 16px; font-weight: bold;display:none;'></textarea>
	                        		<p class=MsoNormal style='text-align:right'>
	                        			<span id='pcsldAdviceApan' style="font-size: 12pt;"></span>
	                        		</p>
	                        	</td>
	                        </tr>
	                        <tr id="jy" style="height:100px;display:none">
	                        	<td style="border:solid windowtext 2.25pt;width: 18%;">
	                        		<p class=MsoNormal align=center style='text-align:center'>
	                        			<b style='mso-bidi-font-weight:normal'>
	                        				<span style='font-size:12.0pt;font-family:宋体;'>
	                        					警员意见
	                        				</span>
	                        			</b>
	                        		</p>
	                        	</td>
	                        	<td colspan="4" style="border:solid windowtext 2.25pt;width: 82%;">
	                        		<textarea id='jyAdvice' name='jyAdvice' rows='3' style='width: 99%;font-size: 16px; font-weight: bold;display:none;'></textarea>
	                        		<p class=MsoNormal style='text-align:right'>
	                        			<span id='jyAdviceApan' style="font-size: 12pt;"></span>
	                        		</p>
	                        	</td>
	                        </tr>
	                        <tr id="kld" style="height:100px;display:none">
	                        	<td style="border:solid windowtext 2.25pt;width: 18%;">
	                        		<p class=MsoNormal align=center style='text-align:center'>
	                        			<b style='mso-bidi-font-weight:normal'>
	                        				<span style='font-size:12.0pt;font-family:宋体;'>
	                        					<c:if test="${fn:length(user.position) == 2}">科领导<br/><br/>意见</c:if> 
	                        					<c:if test="${fn:length(user.position) == 3}">一级领导<br/><br/>意见</c:if>
	                        				</span>
	                        			</b>
	                        		</p>
	                        	</td>
	                        	<td colspan="4" style="border:solid windowtext 2.25pt;width: 82%;">
	                        		<textarea id='kldAdvice' name='kldAdvice' rows='3' style='width: 99%;font-size: 16px; font-weight: bold;display:none;'></textarea>
	                        		<p class=MsoNormal style='text-align:right'>
	                        			<span id='kldAdviceApan' style="font-size: 12pt;"></span>
	                        		</p>
	                        	</td>
	                        </tr>
	                        <tr id="cld" style="height:100px;display:none">
	                        	<td style="border:solid windowtext 2.25pt;width: 18%;">
	                        		<p class=MsoNormal align=center style='text-align:center'>
	                        			<b style='mso-bidi-font-weight:normal'>
	                        				<span style='font-size:12.0pt;font-family:宋体;'>
	                        					<c:if test="${fn:length(user.position) == 2}">处领导<br/><br/>意见</c:if> 
	                        					<c:if test="${fn:length(user.position) == 3}">二级领导<br/><br/>意见</c:if>
	                        				</span>
	                        			</b>
	                        		</p>
								</td>
	                        	<td colspan="4" style="border:solid windowtext 2.25pt;width: 82%;">
	                        		<textarea id='cldAdvice' name='cldAdvice' rows='3' style='width: 99%;font-size: 16px; font-weight: bold;display:none;'></textarea>
	                        		<p class=MsoNormal style='text-align:right'>
	                        			<span id='cldAdviceApan' style="font-size: 12pt;"></span>
	                        		</p>
	                        	</td>
	                        </tr>
	                        <tr id="jld" style="height:100px;display:none">
	                        	<td style="border:solid windowtext 2.25pt;width: 18%;">
	                        		<p class=MsoNormal align=center style='text-align:center'>
	                        			<b style='mso-bidi-font-weight:normal'>
	                        				<span style='font-size:12.0pt;font-family:宋体;'>
	                        					<c:if test="${fn:length(user.position) == 2}">局领导<br/><br/>意见</c:if> 
	                        					<c:if test="${fn:length(user.position) == 3}">三级领导<br/><br/>意见</c:if>
	                        				</span>
	                        			</b>
	                        		</p>
	                        	</td>
	                        	<td colspan="4" style="border:solid windowtext 2.25pt;width: 82%;">
	                        		<textarea id='jldAdvice' name='jldAdvice' rows='3' style='width: 99%;font-size: 16px; font-weight: bold;display:none;'></textarea>
	                        		<p class=MsoNormal style='text-align:right'>
	                        			<span id='jldAdviceApan' style="font-size: 12pt;"></span>
	                        		</p>
	                        	</td>
	                        </tr>
		                </tbody>
		            </table>
		        </div>
	        </div>
		        <c:if test="${!empty dispatchedList }">
		       		<div id="xtzzDiv" style="width:100%;">
			        	<jsp:include page="/views/dispatched/equalDispatched.jsp" />
			        </div>
			    </c:if>
		        <form name="form" action="" method="post">
		        	<div id="operateDiv" style="width:100%;height:50px;font-size: 14px;">
			        	<c:if test="${withdraw.zjck eq '0' }">
							<div class="slider_body" style="width:450px">
					            <div class="slider_selected_left">
					            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批操作：</span>
					            </div>
					            <div class="slider_selected_right" style="width:400px" id="spcz">
					               	<input type="radio" name="spcz" value="1" checked="checked"/>     
					                <c:if test="${user.position eq '81' || user.position eq '91'}">                      
				                                                                            确认撤控
				                    </c:if>
				                    <c:if test="${user.position ne '81' && user.position ne '91'}">                      
				                                                                            同意(提交上级)
				                    </c:if>
				                         &nbsp;&nbsp;
				                    <c:if test="${isDeal eq true }">
						                <input type="radio" name="spcz" value="0"/>
						                     	不同意  &nbsp;&nbsp;
						                <c:if test="${isComplete eq true }">
						                      <input type="radio" name="spcz" value="4"/>
						                         	    同意(结束审批)
						                </c:if>
				                    </c:if>
				                </div>
					        </div>
					    </c:if>
					    <c:if test="${withdraw.zjck eq '1' }">
							<div class="slider_body" style="width:380px">
					                <div class="slider_selected_left">
					                    <span>操作：</span>
					                </div>
					                <div class="slider_selected_right" style="width:300px" id="spcz">
					                	<input type="radio" name="spcz" value="1" checked="checked"/>                              
				                                                                                         确认撤控&nbsp;&nbsp;
				                    </div>
					        </div>
					    </c:if>
						<button class="button_blue" type="button" onclick="agree();">提交</button>
				        <button class="button_blue" type="button" onclick="toClose();">关闭</button>
				        <button class="button_blue" type="button" onclick="toPrint();">打印</button> 
					</div>
		        </form>
	    </div>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
	<script>
		var adviceFlg = "";
		$(function(){
			setTimeout(function(){
				$("#table1").find("tr").css('background-color','#ffffff');//消除所有行背景
			}, 100);
			
			//布控范围多选框初始化
			var dic = jQuery.parseJSON('${dicListJSON}');
			for(var i=0;i < dic.length;i++){
				//下拉框赋值
				if(dic[i].typeCode == "HPZL" && '${dispatched.hpzl }' == dic[i].typeSerialNo){
					$("#hpzl").html(dic[i].typeDesc);
				} else if(dic[i].typeCode == "0002" && '${hpys }' == dic[i].typeSerialNo){
					$("#hpys").html(dic[i].typeDesc);
				} else if(dic[i].typeCode == "BKDL" && '${dispatched.bkdl }' == dic[i].typeSerialNo){
					$("#bkdl").html(dic[i].typeDesc);
				}
			}
			var dicBklb= jQuery.parseJSON('${bklbList}');
			for(var i=0;i< dicBklb.length;i++){
				if('${dispatched.bklb }' == dicBklb[i].ID){
					$("#bklb").html(dicBklb[i].NAME);
				}
			}
			
			//报警类型赋值
			var bkdl = "";
			if('${dispatched.bkdl }' == "1"){
				bkdl = "BKDL1";
			} else if('${dispatched.bkdl }' == "2"){
				bkdl = "BKDL2";
			} else if('${dispatched.bkdl }' == "3"){
				bkdl = "BKDL3";
			}
			for(var i=0;i < dic.length;i++){
				if(dic[i].typeCode == bkdl && dic[i].typeSerialNo == '${dispatched.bjlx }'){
					$("#bjlx").html(dic[i].typeDesc);
				}
			}
			
			//给意见赋值
			var zw = '${user.position }'.substring(0,1);//职位第一位
			var zw1 = '${user.position }'.substring(1,2);//职位第二位
			var zwNum = '${user.position }'.length;//职位长度
			var zw2 = '${user.position }'.substring(0,2);//职位前2位
				//审批记录
			var commentList = jQuery.parseJSON('${commentList}');
			for(var i=0;i<commentList.length;i++){
				var bbrjs = commentList[i].czrjslx.substring(1,2);
				var bbrjs1 = commentList[i].czrjslx.substring(0,1);
				var bbrjsNum = commentList[i].czrjslx.length;
				var spr = commentList[i].czr;
				var spsj = commentList[i].czsj;
				if(bbrjs == '2' && bbrjs1 == zw && bbrjsNum == zwNum){
					$("#kldAdvice").show();
					$("#kldAdvice").attr("readonly", true);
					$("#kldAdvice").val(commentList[i].ms);
					$("#kld").children('td').eq(1).children('p').eq(0).children('span').eq(0).html(spr+" / "+formatDate(spsj));
				} else if(bbrjs == '3' && bbrjs1 == zw && bbrjsNum == zwNum){
					$("#cldAdvice").show();
					$("#cldAdvice").attr("readonly", true);
					$("#cldAdvice").val(commentList[i].ms);
					$("#cld").children('td').eq(1).children('p').eq(0).children('span').eq(0).html(spr+" / "+formatDate(spsj));	
				} else if(bbrjs == '4' && bbrjsNum == zwNum){
					$("#jldAdvice").show();
					$("#jldAdvice").attr("readonly", true);
					$("#jldAdvice").val(commentList[i].ms);
					$("#jld").children('td').eq(1).children('p').eq(0).children('span').eq(0).html(spr+" / "+formatDate(spsj));	
				}
			}	
						
			//显示相应审批人
			if(zw2 > '81' && zw2 < '90'){
				$("#kld").show();
				$("#cld").show();
			} else if(zw2 == '81'){
				$("#kld").show();
				$("#cld").show();
			} else if(zw2 == '91'){
				$("#kld").show();
				$("#cld").show();
				$("#jld").show();
			} else if(zw2 == '72'){
				$("#pcsld").show();
			} else{
				$("#kld").show();
				$("#cld").show();
				$("#jld").show();
			} 
			
			//在相应位置生成advice
			if(zw2 == '72'){
				adviceFlg = "1";
				$("#pcsldAdvice").show();
				$("#pcsldAdvice").val("同意撤控。");
				$("#pcsld").children('td').eq(1).children('p').eq(0).children('span').eq(0).html('${user.userName }'+" / "+formatDate(new Date()));	
			} else if(zw1 == '2'){
				adviceFlg = "2";
				$("#kldAdvice").show();
				$("#kldAdvice").val("同意撤控。");
				$("#kld").children('td').eq(1).children('p').eq(0).children('span').eq(0).html('${user.userName }'+" / "+formatDate(new Date()));
			} else if(zw1 == '3'){
				adviceFlg = "3";
				$("#cldAdvice").show();
				$("#cldAdvice").val("同意撤控。");
				$("#cld").children('td').eq(1).children('p').eq(0).children('span').eq(0).html('${user.userName }'+" / "+formatDate(new Date()));
			} else if(zw1 == '4'){
				adviceFlg = "4";
				$("#jldAdvice").show();
				$("#jldAdvice").val("同意撤控。");
				$("#jld").children('td').eq(1).children('p').eq(0).children('span').eq(0).html('${user.userName }'+" / "+formatDate(new Date()));
			} else{
				adviceFlg = "5";
				$("#jyAdvice").show();
				$("#jyAdvice").val("已确认撤控信息。");
				$("#jy").children('td').eq(1).children('p').eq(0).children('span').eq(0).html('${user.userName }'+" / "+formatDate(new Date()));
			}
			
			//审批操作赋值
			if('${withdraw.zjck}' == '0' && '${isDeal}' == 'true'){
				$("#spcz :radio").click(function(){
					var msg = "";
					if($('input:radio[name=spcz]:checked').val() == "1"){
						msg = "同意撤控。";
					} else if($('input:radio[name=spcz]:checked').val() == "4"){
						msg = "同意撤控，并结束审批流程。";
					} else{
						msg = "不同意撤控。";
					}
					
					if(adviceFlg == "1"){
						$("#pcsldAdvice").val(msg);
					} else if(adviceFlg == "2"){
						$("#kldAdvice").val(msg);
					} else if(adviceFlg == "3"){
						$("#cldAdvice").val(msg);
					} else if(adviceFlg == "4"){
						$("#jldAdvice").val(msg);
					} else if(adviceFlg == "5"){
						$("#jyAdvice").val(msg);
					}
				});
			}
		});
		
		//取消..按钮
		function toClose() {
			//关闭
			window.close();
		}
		
		//同意..按钮
		function agree(){
			var adviceMsg = "";
			if(adviceFlg == "1"){
				adviceMsg = $("#pcsldAdvice").val();
			} else if(adviceFlg == "2"){
				adviceMsg = $("#kldAdvice").val();
			} else if(adviceFlg == "3"){
				adviceMsg = $("#cldAdvice").val();
			} else if(adviceFlg == "4"){
				adviceMsg = $("#jldAdvice").val();
			} else if(adviceFlg == "5"){
				adviceMsg = $("#jyAdvice").val();
			}
			
			if(adviceMsg == ""){
				alert("请填写审批意见！");
				return;
			}
			
			if($('input:radio[name=spcz]:checked').val() == "1"){
				completeForOa('${task.id }', '${task.processInstanceId }', '${passKey }', '1', adviceMsg, '${conPath }', true, '${withdraw.ckid }', '${user.loginName }');
			} else if($('input:radio[name=spcz]:checked').val() == "4"){
				completeForOa('${task.id }', '${task.processInstanceId }', '${passKey }', '4', adviceMsg, '${conPath }', true, '${withdraw.ckid }', '${user.loginName }');
			} else{
				completeForOa('${task.id }', '${task.processInstanceId }', '${passKey }', '0', adviceMsg, '${conPath }', true, '${withdraw.ckid }', '${user.loginName }');
			}
		}
		
		/**
		 * 完成任务
		 * @param {Object} taskId
		 */
		function completeForOa(taskId, taskProInstId, key, value, advice, conPath, withdraw, id, loginName) {
			// 发送任务完成请求
			var url = '';
			if(withdraw){
				url = '<%=basePath %>withdraw/completeForOa.do?taskId=' + taskId + '&key=' + key + '&value=' + value + '&taskProInstId=' + taskProInstId + '&advice=' + encodeURI(encodeURI(advice)) + '&id=' + id + "&loginName=" + loginName + "&login=1";;
			} else{
				url = '<%=basePath %>dispatched/completeForOa.do?taskId=' + taskId + '&key=' + key + '&value=' + value + '&taskProInstId=' + taskProInstId + '&advice=' + encodeURI(encodeURI(advice)) + '&id=' + id + "&loginName=" + loginName + "&login=1";;
			}
 			
 			//隐藏
 			$("#operateDiv").hide();
 			
			//提交
			$.ajax({
				url: url,
				method:"post",
				data:{},
				success:function(data){
					if(data.result == "1"){//添加成功！
						//刷新父页面
						window.opener.reRefresh();
						alert('审批成功！');
						//关闭
						window.close();
					} else {
						//显示
						$("#operateDiv").show();
						alert('审批失败！');
					}
				},
				error: function () {//请求失败处理函数
					//显示
					$("#operateDiv").show();
					alert('审批失败！');
				}
			});
		}
		
		//点击打印
		function toPrint(){
			$("#operateDiv").hide();
			$("#xtzzDiv").hide();
	      	window.print();
	      	$("#operateDiv").show();
	      	$("#xtzzDiv").show();
		}
	</script>
</html>