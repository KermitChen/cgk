<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <title>消息中心</title>
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
    <script  src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
	<script  src="<%=basePath%>common/js/layer/layer.js"></script>
	<style type="text/css">
		td a {
			color :#08f;
		}
		.sixin423-right-markall {
			  display: block;
			  float: left;
			  text-align: center;
			  width: 116px;
			  text-decoration: none;
			  background: #427cc4;
			  color: #fff;
			  height: 23px;
			  line-height: 23px;
			  border: 1px solid #386eb1;
		}
		a:visited {
  			text-decoration: none;
  			
		}
		.sixin423-head{
		  width: 99%;
		  margin: 0 auto;
		  margin-top: 21px;
		  background: #282d45;
		  line-height: 1.5;
		  font-family: 'Microsoft YaHei UI','Microsoft YaHei',SimSun,'Segoe UI','Lucida Grande',Verdana,Arial,Helvetica,sans-serif;
		  font-style: normal;
		  font-variant: normal;
		  font-weight: 400;
		}
		.sixin423-head-right{
		  padding-top: 15px;
		  padding-bottom: 12px;
		  padding-left: 26px;
		  padding-right: 20px;
		  zoom: 1;
		  height: 31px;
		  border: 1px solid #cfcfcf;
		  background: #fff;
		  position: relative;
		  z-index: 100;
		}
	</style>
</head>
<body>
	<jsp:include page="/common/Head.jsp"/>
	<div id="divTitle">
		<span id="spanTitle">当前位置：消息中心</span>
	</div>
    <form name="form" action="" method="post">
    <div class="content">
    	<div class="content_wrap">
	        
	        <div class="sixin423-head">
	        	<div class="sixin423-head-right">
		    		<a class="sixin423-right-markall" href="javascript:markAllHasRed();" id="markAllHasRed">全部标为已读</a>
	        	</div>
		    </div>
		    
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
                            <td align="center">序号</td>
                            <td align="center">主题</td>
                            <td align="center">发送时间</td>
                            <td align="center">是否已读</td>
                            <td align="center">操作</td>
	                    </tr>
	                </thead>
	                <tbody>
	                	<c:forEach items="${pageResult.items }" var="r" varStatus="status">
	                		<tr>
	                		<td>${status.index+1 }</td>
	                		<td>${r.topic }</td>
	                		<td>
	                			<c:choose>
		                			<c:when test="${r.hasread eq 1 }">已读</c:when>
		                			<c:otherwise>未读</c:otherwise>
	                			</c:choose>
	                		</td>
	                		<td>${r.sendtime }</td>
	                		<td>
	                			<a href="javascript:getDetail(${r.messageid })">详情</a>
	                			<a href="javascript:doCheck('${r.url }')">查看</a>
	                		</td>
	                		</tr>
	                	</c:forEach>
	                </tbody>
                        
	            </table>
			<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
	        </div>
			</div>
        </div>
    </form>
    <jsp:include  page="/common/Foot.jsp"/>
</body>

<script type="text/javascript">

  	//根据页号查询
  	var list_url = "${pageContext.request.contextPath}/sysAnn/toTzzxUI.do";
	function doGoPage(pageNo) {
		document.getElementById("pageNo").value = pageNo;
		document.forms[0].action = list_url;
		document.forms[0].submit();
	}
	//全部标为已读
	function markAllHasRed(){
		var url = "${pageContext.request.contextPath}/sysAnn/markAllTzzxHasRed.do";
		document.forms[0].action = url;
		document.forms[0].submit();
	}
	//根据通知id查询通知消息的详情
	function getDetail(id){
		var url ="${pageContext.request.contextPath}/sysAnn/getMessageDetail.do?id="+id;
		layer.open({
			type:2,
			title:'编辑系统公告',
			shadeClose:false,
			shade:0,
			area:['800px','600px'],
			content:url
		});
	}
	//查看消息
	function doCheck(url){
		window.open(url,"_blank");
	}
</script>
</html>