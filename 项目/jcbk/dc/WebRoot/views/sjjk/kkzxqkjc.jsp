<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="zh-CN">
	<head>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
		<base href="<%=basePath%>">
		<title>卡口在线情况监测</title>
		<script src="<%=basePath%>common/js/1.9.0-jquery.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>common/js/jquery.ztree.excheck-3.5.js"></script>
		<link rel="stylesheet" href="<%=basePath%>common/css/style.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>common/css/zTreeStyle/zTreeStyle.css" type="text/css">
	</head>
	<body>
		<jsp:include page="/common/Head.jsp" />
		<div id="divTitle">
			<span id="spanTitle">当前位置：数据监控管理&gt;&gt;卡口在线情况监测</span>
		</div>
	    <div class="content">
	    	<div class="content_wrap">
	    		<fieldset style="margin: 0px 0px 0px 0px;">
					<legend style="color:#FF3333">监测条件</legend>
					<form name="form" action="" method="post">
				        <div class="slider_body"> 
							<div class="slider_selected_left">
				            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点ID：</span>
				            </div>
			                <div class="slider_selected_right" style="z-index:0;">
			                    <div class="img_wrap">
			                        <div class="select_wrap select_input_wrap">
			                            <input id="Check_JcdID" class="slider_input" type="text" name="Check_JcdID" />
			                            <a id="Check_JcdID" class="empty"></a>
			                        </div>
			                    </div>  
			               </div>
				        </div>
				        <div class="slider_body">
				        	<div class="slider_selected_left">
				            	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点名称：</span>
				            </div>
				            <div class="slider_selected_right" style="z-index:0;">
				                <div class="img_wrap">
				                	<div class="select_wrap select_input_wrap">
				                    	<input id="Check_JcdName" class="slider_input" type="text" name="Check_JcdName" />
				                    	<a id="Check_JcdName" class="empty"></a>
				                    </div>
				                </div>  
				            </div>
				        </div>
						<div class="slider_body"> 
				            <div class="slider_selected_left">
				                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;连接状态：</span>
				            </div>
				            <div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
					            <input class="input_select xiala" type="text" id="jcdzt" readonly="readonly" value="==全部=="/>
					            <input type="hidden" name="jcdzt" value="${jcdzt }" />
					            <div class="ul"> 
					            	<script>
					            		if('${jcdzt}' == "1"){
					            	  		$("#jcdzt").val("连接正常");
					            		} else if ('${jcdzt}' == "0"){
					            	 		$("#jcdzt").val("连接异常");
					            		}
					            	</script>
					            	<div class="li" data-value="" onclick="sliders(this)">==全部==</div>
					          		<div class="li" data-value="1" onclick="sliders(this)">连接正常</div> 
					            	<div class="li" data-value="0" onclick="sliders(this)">连接异常</div> 
					            </div>
					         </div>
				        </div>
					</form>
	           		<div class="button_wrap clear_both" style="height: 50px;">
					    <input id="searchBt" type="button" class="button_blue" value="查询">
					   	<input type="button" class="button_blue" value="重置" onclick="doReset()">
					   	<input type="button" class="button_blue" value="导出Excel" onclick="ExcelExport();">
					   	<%
					   		String zxl = (String)request.getSession().getAttribute("zxl");
					   	%>
					   	昨天卡口在线率：<input id="kkzxlInput" class="slider_input" type="text" readonly="readonly" value="<%=(zxl != null && !"".equals(zxl.trim())? zxl:"统计失败，请刷新页面重新统计！") %>"/>
				    </div>
				</fieldset>
				<fieldset style="margin: 20px 0px 0px 0px;">
			      	<legend style="color:#FF3333">监测结果（昨天卡口在线情况）</legend>
				    <div id="dataDiv" class="pg_result">
			        
			        </div>
				</fieldset>
			</div>
		</div>
		<jsp:include page="/common/Foot.jsp" />
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/map/apiv2.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/MarkerClusterer_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/TextIconOverlay_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/map/GeoUtils_min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
		//导出Excel
		function ExcelExport(){
			if($("#table1 tbody tr[id='tr_result']").length >= 1){
				var url = "${pageContext.request.contextPath}/sjjk/exportKkzxqkjcExcel.do";
				document.forms[0].action = url;
				document.forms[0].submit();
			} else{
				layer.confirm('监测结果为空，无法导出！',{btn:['确定']});
			}
		}
		
		//搜索..按钮
		function doGoPage(pageNo){
			var jcdid = $.trim($("#Check_JcdID").val());//监测点ID
	  		var jcdmc = $.trim($("#Check_JcdName").val());//监测点名称
	  		var jcdzt = $.trim($("input[name='jcdzt']").val());//
	  		
	  		//显示进度条
			var index = layer.load();
				
		  	//提交
			$.ajax({
				url:'<%=basePath%>/sjjk/kkzxqkjc.do?' + new Date().getTime(),
				method:"post",
				data:{jcdid:jcdid, jcdmc:jcdmc, cqNo:"", roadNo:"", jcdzt:jcdzt, pageNo:pageNo},
				success:function(data){
					//关闭进度条
					layer.close(index);
					$('#dataDiv').html(data);
				},
				error: function () {//请求失败处理函数
					//关闭进度条
					layer.close(index);
					alert('查询失败！');
				}
			});	  		
		}
	
	  	//重置..按钮
	  	function doReset(){
	  		$("#Check_JcdID").val("");//监测点ID
	  		$("#Check_JcdName").val("");//监测点名称
	  		$("#jcdzt").val("==全部==");//监测点状态
	  		$("input[name='jcdzt']").val("");
	  	}
		 
		$(function(){
			//根据条件获取符合条件的数据
			$("#searchBt").click(function() {
				doGoPage(1);
			});
		});
	</script>
</html>