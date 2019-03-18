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
    <title>预案管理页面</title> 
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script  src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/xiala.js"></script>
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
		<span id="spanTitle">当前位置：预案参数管理</span>
	</div>
    <form name="form" action="" method="post">
    <div class="content">
    	<div class="content_wrap">
    	
    		<div class="slider_body">
	            <div class="slider_selected_wrap">
	                <div class="slider_selected_left">
	                    <span>预案名称：</span>
	                </div>
	                <div class="slider_selected_right" style="z-index:0;">
	                    <div class="img_wrap">
	                        <div class="select_wrap select_input_wrap">
	                            <input id="Check_Yamc" class="slider_input" type="text" value="${Check_Yamc }" name="Check_Yamc" />
	                            <a id="Check_Yamc" class="empty"></a>
	                        </div>
	                    </div>  
	                </div>
	            </div>
	        </div>
    		
	        <div class="slider_body"> 
	            <div class="slider_selected_left">
	                <span>预案种类：</span>
	            </div>
	            <div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
		            <input class="input_select xiala" id="xiala2" type="text" value="==全部==">
		            <input type="hidden" name="Check_Yazl" id="xiala22" value="${d.typeSerialNo }" />
		            <div class="ul"> 
		            	  <div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
		            	  <c:forEach items= "${Ya_kinds}" var="d">
			            	  <c:if test="${d.typeSerialNo eq Check_Yazl }">
			            	   	<script>
			            		 	$("#xiala2").val("${d.typeDesc }");
			            		 	$("#xiala22").val("${d.typeSerialNo }");
			            	   	</script>
			            	  </c:if> 
		                    <div class="li" data-value="${d.typeSerialNo }" onclick="sliders(this)"><a rel="2">${d.typeDesc}</a></div> 
						 </c:forEach>
		            </div>
	        	</div>
	        </div>
	        
	        <div class="slider_body"> 
	            <div class="slider_selected_left">
	                <span>预案类别：</span>
	            </div>
	            <div class="slider_selected_right dropdown dropdowns" id="cp_1"onclick="slider(this)">
		            <input class="input_select xiala" id="xiala1" type="text" value="==全部==">
		            <input type="hidden" name="Check_Yadj" id="xiala11" value="${d.typeSerialNo }" />
		            <div class="ul"> 
		            	  <div class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
		            	  <c:forEach items= "${Ya_lbs}" var="d">
		            	  		<c:if test="${d.ID eq Check_Yadj }">
				            	   	<script>
				            		 	$("#xiala1").val("${d.typeDesc }");
				            		 	$("#xiala11").val("${d.typeSerialNo }");
				            	   	</script>
			            	  </c:if> 
		                    <div class="li" data-value="${d.ID }" onclick="sliders(this)"><a rel="2">${d.NAME}</a></div> 
						 </c:forEach>
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
							<td width="30" align="center"><input type="checkbox" id="selectAll" onclick="doSelectAll()" /></td>
                            <td align="center">序号</td>
                            <td align="center">预案名称</td>
                            <td align="center">预案描述</td>
                            <td align="center">预案种类</td>
                            <td align="center">预案类别</td>
                            <td align="center">报警类型</td>
                            <td align="center">操作</td>
	                    </tr>
	                </thead>
	                <tbody>
                        <c:forEach var="d" items="${pageResult.items }" varStatus="status">
                        	<tr>
                        		<td width="30" align="center"><input type="checkbox" id="ids" name="ids" value="${d.id }"/></td>
                        		<td>${status.index+1} </td>
                        		<td>${d.yamc} </td>
                        		<td>${d.yams }</td>
                        		<td>
                        			<c:forEach items="${Ya_kinds }" var="kind">
                        				<c:if test="${kind.typeSerialNo eq d.yazl }">${kind.typeDesc}</c:if>
                        			</c:forEach>
                        		</td>
                        		<td>
                        			<c:forEach items="${Ya_lbs }" var="lb">
                        				<c:if test="${lb.ID eq d.yadj }">${lb.NAME }</c:if>
                        			</c:forEach>
                        		</td>
                        		<td>
                        			<c:forEach items="${list_dicList }" var="dic">
                        				<c:if test="${dic.typeCode eq 'BKDL1' || dic.typeCode eq 'BKDL2' || dic.typeCode eq 'BKDL3' }">
                        					<c:if test="${dic.typeSerialNo eq d.bjlx }">${dic.typeDesc }</c:if>
                        				</c:if>
                        			</c:forEach>
                        		</td>
                                <td align="center">
                                   	<a href="javascript:doEdit(${d.id })" >编辑</a>
                                   	|
                                   	<a href="javascript:doDelete(${d.id })" >删除</a>                               		
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

	//预案参数管理listUI页面,请求地址
	var list_url = "yagl/findYa.do";
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
  		document.forms[0].action = "yagl/addYaUI.do";
  		document.forms[0].submit();	
  	}
  	//编辑..按钮
 	function doEdit(id){
 		document.forms[0].action = "${pageContext.request.contextPath}/yagl/editYaUI.do?id=" + id;
 		document.forms[0].submit();
 		}
 	//重置..按钮
 	function doReset(){
  			$("#Check_Yamc").val("");
  			$("#xiala1").val("");
  			$("#xiala11").val("");
  			$("#xiala2").val("");
  			$("#xiala22").val("");
 		}
 	//删除..某一条数据右侧..删除 链接
 	function doDelete(id){
		var msg = "您真的确定要删除吗？\n请确认！";
		if (confirm(msg)==true){
  			///进行删除操作  
  			document.forms[0].action = "${pageContext.request.contextPath}/yagl/deleteYa.do?id="+id;
  			document.forms[0].submit();
		}else{
			return ;
		}
 	}
 	//批量删除
 	function doBatchesDelete(){
 			//判断是否有checkbox选中，没有checkbox选中返回错误信息
		var msg = "您真的确定要删除吗？\n请确认！";
		if (confirm(msg)==true){
  			///进行删除操作  
 			document.forms[0].action = "${pageContext.request.contextPath}/yagl/deleteYas.do";
 			document.forms[0].submit();
		}else{
			return ;
		}
 	}
 		//全选、反选总checkbox
 		function doSelectAll(){
 			//JQuery1.6以后建议使用prop
 			//selectedAll(checkbox):总复选框;ids(checkbox):每一条基础数据对应的复选框
 			$("input[name=ids]").prop("checked", $("#selectAll").is(":checked"));
 		}
 		//根据页号查询
	function doGoPage(pageNo) {
		document.getElementById("pageNo").value = pageNo;
		document.forms[0].action = list_url;
		document.forms[0].submit();
}
</script>
</html>