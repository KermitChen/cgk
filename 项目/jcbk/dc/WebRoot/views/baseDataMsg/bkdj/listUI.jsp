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
    <title>布控等级查询</title> 
    <link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<style type="text/css">
		td a {
			color :#08f;
		}
	</style>
</head>
<body>
    <jsp:include page="/common/Head.jsp" />
	<div id="divTitle">
		<span id="spanTitle">布控等级查询</span>
	</div>
    <form name="form1" action="" method="post">
    <div class="content">
    	<div class="content_wrap">
	        <div class="slider_body">
	            <div class="slider_selected_left">
	                <span class="layer_span" style="color:#900b09">布控大类：</span>
	            </div>
	            <div class="slider_selected_right dropdown dropdowns" id="cp_2" onclick="slider(this)">
	            	<c:choose>
	            		<c:when test="${empty bkdl }">
	           	 				<input class="input_select xiala" id="bkdl_xiala" readonly="readonly" type="text" value=""/> 
	            		</c:when>
           	 			<c:otherwise>
			           	 	<c:forEach items="${bkdlList }" var="s">
			           	 		<c:choose>
			           	 			<c:when test="${s.typeSerialNo eq bkdl }">
				            			<input class="input_select xiala" id="bkdl_xiala" readonly="readonly" type="text" value="${s.typeDesc }"/> 
			           	 			</c:when>
			           	 		</c:choose>
			                </c:forEach>
           	 			</c:otherwise>
	            	</c:choose>
	                <input type="hidden" id="bkdl" name="bkdl" value="${bkdl }" />
	                <div class="ul"> 
	                	<div class="li" data-value="" onclick="sliders(this)">全选</div>
	                	<c:forEach items="${bkdlList }" var="s">
		                    <div class="li" data-value="${s.typeSerialNo }" onclick="sliders(this)">${s.typeDesc }</div>
	                	</c:forEach>
	                </div> 
	            </div>
	        </div>
	
	        <div class="slider_body">
		    	<input type="button" class="button_blue" value="查询" onclick="doSearch()">
		    	<input type="button" class="button_blue" value="重置" onclick="doReset()">
		    	<input type="button" class="button_blue" value="新增" onclick="toAddUI()">
		    </div>
		    
	        <div class="pg_result">
	            <table>
	                <thead>
	                    <tr>
                            <td>序号</td>
                            <td>名称</td>
                            <td>布控大类</td>
                            <td>显示顺序</td>
                            <td>级别</td>
                            <td>操作</td>
	                    </tr>
	                </thead>
	                <tbody>
                        <c:forEach var="s" items="${pageResult.items }" varStatus="status">
                        	<tr>
                        		<td>${status.index+1} </td>
                        		<td>${s.name }</td>
                        		<td>
                        			<c:forEach items="${bkdlList }" var="a">
                        				<c:if test="${s.superid eq a.typeSerialNo }">
                        					${a.typeDesc }
                        				</c:if>
                        			</c:forEach>
                        		</td>
                        		<td>${s.showOrder }</td>
                        		<td>${s.level }</td>
                                <td>
                                   	<a href="javascript:doEdit('${s.id }')" >编辑</a>
                                   	|
                                   	<a href="javascript:doDelete('${s.id }')" >删除</a>                               		
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
	var list_url = "bkdj/findBkdj.do";
	//搜索..按钮
  	function doSearch(){
  		$("#pageNo").val(1);
  		document.forms[0].action = list_url;
  		document.forms[0].submit();	  		
  	}
  	//根据页号查询...
	function doGoPage(pageNo) {
		document.getElementById("pageNo").value = pageNo;
		document.forms[0].action = list_url;
		document.forms[0].submit();
	}
  	//添加..按钮
  	function toAddUI(){
  		document.forms[0].action = "bkdj/preAdd.do";
  		document.forms[0].submit();	
  	}
  	//编辑..按钮
	function doEdit(id){
		document.forms[0].action = "bkdj/preUpdate.do?id="+id;
 		document.forms[0].submit();
	}
		//重置..按钮
	function doReset(){
		$("#bkdl_xiala").val("");
		$("#bkdl").val("");
	}
	//删除..某一条数据右侧..删除 链接
	function doDelete(id){
		layer.confirm('确定删除？', {
			  btn: ['确定','取消'] //按钮
			}, function(index){
				var location = (window.location+'').split('/');  
				var basePath = location[0]+'//'+location[2]+'/'+location[3];  
				$.ajax({
					async:true,
					type: 'POST',
					data:{id:id},
					dataType : "json",
					url: basePath + "/bkdj/delete.do",//请求的action路径
					error: function () {//请求失败处理函数
						layer.msg('删除失败!');
					},
					success:function(data){ //请求成功后处理函数。
						console.log(data);
						if(data.res == null || data.res == "" || data.res == '0'){
							layer.msg('删除失败!');
						}else if(data.res == '1'){
							layer.msg('删除成功!');
							doGoPage($("#pageNo").val());
						}
					}
				});
				layer.close(index);
			}, function(index){
			  layer.close(index);
			});
	}
	
</script>
</html>