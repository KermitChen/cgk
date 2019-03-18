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
    <title>城区基础数据查询</title> 
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
	<style type="text/css">
		td a {
			color :#08f;
		}
	</style>
</head>
<body>
    <div class="head">
        <div class="head_wrap">
            <img src="<%=basePath%>common/images/police.png" alt="">
            <h1>深圳市公安局缉查布控系统</h1>
        </div>
    </div>
    <div id="divTitle">
		<span id="spanTitle">当前位置：城区管理</span>
	</div>
    <form name="form1" action="" method="post">
    <div class="content">
    	<div class="content_wrap">
	        <div class="slider_body"> 
				<div class="slider_selected_left">
	                    <span>城区名称：</span>
	            </div>
                <div class="slider_selected_right" style="z-index:0;">
                    <div class="img_wrap">
                        <div class="select_wrap select_input_wrap">
                            <input id="areaName" class="slider_input" type="text" value="${Check_areaName }" name="Check_areaName" />
                            <a id="areaName" class="empty"></a>
                        </div>
                    </div>  
               </div>
	        </div>
	        <div class="slider_body">
	            <div class="slider_selected_wrap">
	                <div class="slider_selected_left">
	                    <span>城区代码：</span>
	                </div>
	                <div class="slider_selected_right" style="z-index:0;">
	                    <div class="img_wrap">
	                        <div class="select_wrap select_input_wrap">
	                            <input id="areano" class="slider_input" type="text" value="${Check_areaNo }" name="Check_areaNo" />
	                            <a id="areano" class="empty"></a>
	                        </div>
	                    </div>  
	                </div>
	            </div>
	        </div>
	
	        <div class="button_wrap clear_both">
		    	<input type="button" class="button_blue" value="查询" onclick="doSearch()">
		    	<input type="button" class="button_blue" value="重置" onclick="doReset()">
		    	<input type="button" class="button_blue" value="新增" onclick="toAddUI()">
		    	<input type="button" class="button_blue" value="批量删除" onclick="doBatchesDelete()">
		    </div>
		    
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
							<td align="center"><input type="checkbox" id="selectAll" onclick="doSelectAll()" /></td>
                            <td align="center">序号</td>
                            <td align="center">区域代码</td>
                            <td align="center">区域名称</td>
                            <td width="140" align="center">隶属城区代码</td>
                            <td width="120" align="center">操作</td>
	                    </tr>
	                </thead>
	                <tbody>
                        <c:forEach var="a" items="${pageResult.items }" varStatus="status">
                        	<tr>
                        		<td width="30" align="center"><input type="checkbox" id="ids" name="ids" value="${a.id }"/></td>
                        		<td>${status.index+1} </td>
                        		<td>${a.areano }</td>
                        		<td>${a.areaname }</td>
                        		<td>${a.suparea }</td>
                                <td align="center">
                                   	<a href="javascript:doEdit(${a.id })" >编辑</a>
                                   	|
                                   	<a href="javascript:doDelete(${a.id })" >删除</a>                               		
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

	var list_url = "area/findArea.do";
	//搜索..按钮
  	function doSearch(){
  		//重置页号(两种写法DOM,JQuery)
  		//document.getElementById("pageNo").value=1;
  		$("#pageNo").val(1);
  		document.forms[0].action = list_url;
  		document.forms[0].submit();	  		
  	}
  	//添加..按钮
  	function toAddUI(){
  		document.forms[0].action = "area/addAreaUI.do";
  		document.forms[0].submit();	
  	}
  	//编辑..按钮
 		function doEdit(id){
 		document.forms[0].action = "${pageContext.request.contextPath}/area/editAreaUI.do?id=" + id;
 		document.forms[0].submit();
 		}
 		//重置..按钮
 		function doReset(){
 			$("#areaName").val("");
 			$("#areano").val("");
 		}
 		//删除..某一条数据右侧..删除 链接
 		function doDelete(id){
		var msg = "您真的确定要删除吗？\n请确认！";
		if (confirm(msg)==true){
  			///进行删除操作  
  			document.forms[0].action = "${pageContext.request.contextPath}/area/deleteArea.do?id="+id;
  			document.forms[0].submit();
		}else{
			return ;
		}
 		}

		//批量删除...
		function doBatchesDelete() {
			//判断是否有checkbox选中，没有checkbox选中返回错误信息
			var checkboxs = $('input[name="ids"]');
			if (checkboxs != null) {
				var hasChecked = false;
				for ( var i = 0; i < checkboxs.length; i++) {
					if (checkboxs[i].checked) {
						hasChecked = true;
						break;
					}
				}
				if (hasChecked) {
					var msg = "您真的确定要删除吗？\n请确认！";
					if (confirm(msg) == true) {
						///进行删除操作  
						document.forms[0].action = "${pageContext.request.contextPath}/area/deleteAreas.do";
						document.forms[0].submit();
					} else {
						return;
					}
				} else {
					return alert("请您选择要删除的对象！");
				}
			}
		}
		
		//全选、反选总checkbox...
		function doSelectAll() {
			//JQuery1.6以后建议使用prop
			//selectedAll(checkbox):总复选框;ids(checkbox):每一条基础数据对应的复选框
			$("input[name=ids]").prop("checked",$("#selectAll").is(":checked"));
		}
		
		//根据页号查询...
		function doGoPage(pageNo) {
			document.getElementById("pageNo").value = pageNo;
			document.forms[0].action = list_url;
			document.forms[0].submit();
		}
	</script>
</html>