<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <title>布控审批</title>
    <link rel="stylesheet" href="<%=basePath%>common/css/style2.css" type="text/css">
    <script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
	<script  src="<%=basePath%>common/js/layer/layer.js"></script>
	<style type="text/css">
		#table1 {
			border: 1px solid #000;
		}
		#table1 thead tr td{
			border: 0px solid #fff;
		}
		#table1 tbody tr {
			border: 1px solid #000;
		}
		#table1 tbody td {
			border:1px solid #000;
			width: 70px;
		}
	</style>
</head>
<body>
    
    <div class="content">
    	<div class="content_wrap">
    	
	        <div class="pg_result" style="width: 700px;">
	            <table id="table1">
	                <thead >
	                    <tr >
                            <td align="center" colspan="5">深圳市公安局车辆布控审批表</td>
	                    </tr>
	                </thead>
	                <tbody>
                        <tr>
                        	<td></td>
                        	<td></td>
                        	<td>[市局]</td>
                        	<td></td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td></td>
                        	<td></td>
                        	<td></td>
                        	<td>单号：</td>
                        	<td id="formID"></td>
                        </tr>
                        <tr>
                        	<td rowspan="6">布控申请信息</td>
                        	<td>布控申请单位</td>
                        	<td colspan="3"></td>
                        </tr>
                        <tr>
                        	<td>布控申请人</td>
                        	<td>${bk.bkr }</td>
                        	<td>警号</td>
                        	<td>${bkrjh }</td>
                        </tr>
                        <tr>
                        	<td>布控级别</td>
                        	<td></td>
                        	<td>联系电话</td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td>布控范围</td>
                        	<td colspan="3"></td>
                        </tr>
                        <tr>
                        	<td>布控单位</td>
                        	<td colspan="3">${bk.bkjg }</td>
                        </tr>
                         <tr>
                        	<td>布控起始时间</td>
                        	<td></td>
                        	<td>布控终止时间</td>
                        	<td></td>
                        </tr>
                        <tr>
                        	<td>简要案情</td>
                        	<td colspan="4">
                        		<textarea rows="3" style="width: 99%;"></textarea>
                        	</td>
                        </tr>
                        <tr>
                        	<td>科领导意见</td>
                        	<td colspan="4">
                        		<textarea rows="3" style="width: 99%;"></textarea>
                        	</td>
                        </tr>
                        <tr>
                        	<td>处领导意见</td>
                        	<td colspan="4">
                        		<textarea rows="3" style="width: 99%;"></textarea>
                        	</td>
                        </tr>
                        <tr>
                        	<td>局领导意见</td>
                        	<td colspan="4">
                        		<textarea rows="3" style="width: 99%;"></textarea>
                        	</td>
                        </tr>
	                </tbody>
	            </table>
	            
	        </div>
			</div>
        </div>
</body>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/jui/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/qtip/jquery.qtip.pack.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/html/jquery.outerhtml.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/plugins/blockui/jquery.blockUI.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/activiti/workflow.js"></script>
<script type="text/javascript">

</script>
</html>