<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
	    <title>数据字典管理</title> 
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
	</head>

	<body>
		<jsp:include page="/common/Head.jsp" />
	    <div id="divTitle">
			<span id="spanTitle">当前位置：基础数据管理&gt;&gt;数据字典管理</span>
		</div>
		<div class="content">
	    	<div class="content_wrap">
		    	<form name="form" action="" method="post">
			        <div class="slider_body"> 
			            <div class="slider_selected_left">
			                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字典大类：</span>
			            </div>
						<div class="slider_selected_right dropdown dropdowns" id="cp_1" onclick="slider(this)">
				            <input class="input_select xiala" id="xiala2" type="text" value="==全部==">
				            <input type="hidden" name="kind" id="xiala22" value="${d.typeCode }" />
				            <div class="ul"> 
				            	<div id="queryAll" class="li" data-value="" onclick="sliders(this)"><a rel="2">==全部==</a></div>
				            	<c:forEach items= "${kindSet}" var="d">
					        		<c:if test="${d.typeCode eq kind }">
					        			<script>
					            		 	$("#xiala2").val("${d.memo }");
					            		 	$("#xiala22").val("${d.typeCode }");
					            	   	</script>
					        		</c:if> 
				            	<div class="li" data-value="${d.typeCode }" onclick="sliders(this)"><a rel="2">${d.memo}</a></div> 
								</c:forEach>
				            </div>
			        	</div>
			        </div>
			        <div class="slider_body">
			            <div class="slider_selected_wrap">
			                <div class="slider_selected_left">
			                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;大类描述：</span>
			                </div>
			                <div class="slider_selected_right" style="z-index:0;">
			                    <div class="img_wrap">
			                        <div class="select_wrap select_input_wrap">
			                            <input id="memo" name="memo" class="slider_input" type="text" value="${memo }"/>
			                            <a id="memo" class="empty"></a>
			                        </div>
			                    </div>  
			                </div>
			            </div>
					</div>
		         	<div class="slider_body">
			            <div class="slider_selected_wrap">
			                <div class="slider_selected_left">
			                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;小类序号：</span>
			                </div>
			                <div class="slider_selected_right" style="z-index:0;">
			                    <div class="img_wrap">
			                        <div class="select_wrap select_input_wrap">
			                            <input id="typeSerialNo" name="typeSerialNo" class="slider_input" type="text" value="${typeSerialNo }"/>
			                            <a id="typeSerialNo" class="empty"></a>
			                        </div>
			                    </div>  
			                </div>
			            </div>
					</div>
					<div class="slider_body">
			            <div class="slider_selected_wrap">
			                <div class="slider_selected_left">
			                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;小类描述：</span>
			                </div>
			                <div class="slider_selected_right" style="z-index:0;">
			                    <div class="img_wrap">
			                        <div class="select_wrap select_input_wrap">
			                            <input id="typeDesc" name="typeDesc" class="slider_input" type="text" value="${typeDesc }"/>
			                            <a id="typeDesc" class="empty"></a>
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
									<td width="30" align="center"><input type="checkbox" id="selectAll" onclick="doSelectAll()" /></td>
		                            <td align="center">序号</td>
		                            <td align="center">大类编号</td>
		                            <td align="center">大类描述</td>
		                            <td align="center">小类编号</td>
		                            <td align="center">小类描述</td>
		                            <td align="center">备注</td>
		                            <td align="center">是否可编辑</td>
		                            <td align="center">操作</td>
			                    </tr>
			                </thead>
			                <tbody>
		                        <c:forEach var="d" items="${pageResult.items }" varStatus="status">
		                        	<tr>
		                        		<td width="30" align="center"><input type="checkbox" id="ids" name="ids" value="${d.dictionaryid }"/></td>
		                        		<td>${status.index+1} </td>
		                        		<td>${d.typeCode }</td>
		                        		<td>${d.memo }</td>
		                        		<td>${d.typeSerialNo }</td>
		                        		<td>${d.typeDesc }</td>
		                        		<td>${d.remark }</td>
		                        		<td>
		                        			<c:choose>
				                        		<c:when test="${d.editFlag eq 1 }">可编辑</c:when>
				                        		<c:otherwise>不可编辑</c:otherwise>
			                        		</c:choose>
		                        		</td>
		                                <td align="center">
		                                	<c:choose>
			                                	<c:when test="${d.editFlag eq 1 }">
			                                    	<a href="javascript:doEdit(${d.dictionaryid })" >编辑</a>
			                                    	|
			                                    	<a href="javascript:doDelete(${d.dictionaryid })" >删除</a>                               		
			                                	</c:when>
			                                	<c:otherwise>
			                                		<a href="javascript:void(0)" class="huifang">不可操作</a>
			                                	</c:otherwise>
		                                	</c:choose>
		                                </td>
		                        	</tr>
		                        </c:forEach>
			                </tbody>
			            </table>
						<jsp:include page="/common/pageNavigator.jsp"></jsp:include>
			        </div>
		    	</form>  
	        </div>
	    </div>
	    <jsp:include  page="/common/Foot.jsp"/>
	</body>
	<script type="text/javascript">
		var list_url = "dic/findDic.do";
		//搜索..按钮
	  	function doSearch(){
	  		$("#pageNo").val(1);
	  		document.forms[0].action = list_url;
	  		document.forms[0].submit();
	  	}
	  	
	  	//添加..按钮
	  	function toAddUI(){
	  		document.forms[0].action = "dic/addDicUI.do";
	  		document.forms[0].submit();	
	  	}
	  	
	  	//编辑..按钮
  		function doEdit(id){
  			document.forms[0].action = "${pageContext.request.contextPath}/dic/updateDicUI.do?id=" + id;
  			document.forms[0].submit();
  		}
  		
  		//重置..按钮
  		function doReset(){
  			$('#queryAll').trigger("click");//时间类型
  			$("#memo").val("");
  			$("#typeSerialNo").val("");
  			$("#typeDesc").val("");
  		}
  		//删除..某一条数据右侧..删除 链接
  		function doDelete(id){
			var msg = "您真的确定要删除吗？\n请确认！";
			if (confirm(msg) == true){
	  			///进行删除操作  
	  			document.forms[0].action = "${pageContext.request.contextPath}/dic/deleteDic.do?id="+id;
	  			document.forms[0].submit();
			} else{
				return;
			}
  		}
  		
  		//批量删除
  		function doBatchesDelete(){
  			//判断是否有checkbox选中，没有checkbox选中返回错误信息
			var msg = "您真的确定要删除吗？\n请确认！";
			if (confirm(msg)==true){
	  			///进行删除操作  
  				document.forms[0].action = "${pageContext.request.contextPath}/dic/deleteDics.do";
  				document.forms[0].submit();
			} else{
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