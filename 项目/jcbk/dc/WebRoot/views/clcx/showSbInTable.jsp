<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>过车记录</title>
<style type="text/css">
	.pg_result {
		clear: both;
	    /*margin-top: 20px;*/
	    /*border: 1px solid #d5d5d5;*/
	    background: #fff;
	}
	table {
	    border-collapse: collapse;
	    width: 100%;
	}
	tr {
	    border-bottom: 1px solid #d5d5d5;
	}
	td {
	    text-align: center;
	    padding: 6px 10px;
	}
	tbody {
	    font-size: 12px;
	}
	tbody tr:nth-child(odd) {
	    background: #f4f4f4;
	}
</style>
</head>
<body>
	 <div class="pg_result">
       	 <table>
            <thead>
                <tr>
                    <td>序号</td>
                    <td>车牌号码</td>
                    <td>车牌颜色</td>
                    <td>识别时间</td>
                    <td>监测点名称</td>
                    <td>操作</td>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${fn:length(list) le 0}">
            		<tr>
            			<td colspan="6" style="color:red;font-size:16px;">未查询到任何数据！</td>
            		</tr>
            	</c:if>
                <c:forEach items="${list }" var="s" varStatus="c">
                	<tr>
                    <td>${c.index + 1 }</td>
                    <td>${s.hphm }</td>
                   	<td>${cplxMap[s.cplx] }</td>
                    <td>${s.sbsj }</td>
                    <td>${jcdMap[s.jcdid] }</td>
                    <td><a href="javascript:showSbDetailInParent('${s.tp1 }');">详细信息</a></td>
                	</tr>
                </c:forEach>
            </tbody>
        </table>
       </div>
</body>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
</html>