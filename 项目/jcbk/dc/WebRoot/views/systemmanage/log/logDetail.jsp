<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志详情</title>
<style type="text/css">
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
	<table width="100%">
		<c:choose>
			<c:when test="${log == null }">
				<tr>
					<td align="center" ><font size="3" color="red">查询失败！</font></td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td width="100px" style="border: 1px solid green;text-align: right;">操作人用户名：</td>
					<td style="border: 1px solid green;text-align: left;">${log.operator }</td>
					<td width="80px" style="border: 1px solid green;text-align: right;">操作人姓名：</td>
					<td style="border: 1px solid green;text-align: left;">${log.operatorName }</td>
				</tr>
				<tr>
					<td width="100px" style="border: 1px solid green;text-align: right;">模块名称：</td>
					<td style="border: 1px solid green;text-align: left;">${log.moduleName }</td>
					<td width="80px" style="border: 1px solid green;text-align: right;">操作方法：</td>
					<td style="border: 1px solid green;text-align: left;">${log.operateName }</td>
				</tr>
				<tr>
					<td width="100px" style="border: 1px solid green;text-align: right;">客户端IP：</td>
					<td style="border: 1px solid green;text-align: left;">${log.ip }</td>
					<td width="80px" style="border: 1px solid green;text-align: right;">操作时间：</td>
					<td style="border: 1px solid green;text-align: left;">${log.operateDate }</td>
				</tr>
				<tr height="90px">
					<td width="100px" style="border: 1px solid green;text-align: right;">参数：</td>
					<td colspan="3" style="border: 1px solid green;text-align: left;">
						<textarea id="cs" style="width: 430px;height: 120px;" readonly="readonly">${log.args }</textarea>
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</body>
</html>