<%@page import="java.util.List"%>
<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">   
<title>多维碰撞分析</title>
</head>
<body>
	<jsp:include page="/common/Head.jsp" />
	<link rel="stylesheet" href="<%=basePath%>common/css/sb/dwpz.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>common/css/barline.css" type="text/css">		
	<div id="divTitle">
		<span id="spanTitle">多维碰撞分析</span>
	</div>
    <div class="content">
    	<div class="content_wrap">
    	
    		<form action="" method="POST">
    			<input id="form_input_queryCondition" name="queryCondition" type="hidden" value=""/>
    			<input id="form_input_queryFlag" name="queryFlag" type="hidden" value=""/>
    		</form>
    	
    	 		 <div class="left_slider_body">
    	 		 <fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;">
					<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">条件选择</legend>
    	 		 	<div class="slider_body">
				            <div class="slider_selected_left">
				                <span>监测点：</span>
				            </div>
				            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
				                <input class="input_select xiala" onclick="doChoseJcd();" id="jcdid1" type="text" value="==全部=="/> 
				                <input type="hidden" name="jcdid" id="jcdid">
				            </div>
				        </div>
			        <div class="slider_body" >
					  <div class="slider_selected_left">
						<span>分析时间：</span>
					  </div>
						<div class="slider_selected_right">
						  <div class="demolist">
						    <input class="inline laydate-icon" id="kssj" name="kssj" value="${kssj }" 
					onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
						  </div>
						</div>
					</div>
					<div class="slider_body" >
					  <div class="slider_selected_left">
						<span>至</span>
					  </div>
						<div class="slider_selected_right">
						  <div class="demolist">
						    <input class="inline laydate-icon" id="jssj" name="jssj" value="${jssj }"  
					onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
						  </div>
						</div>
					</div>
					</fieldset>
    	 		 </div>
    	 		 <div class="centre_slider_body">
    	 		 	<div class="button_div" id="add_con"></div>
    	 		 </div>
    	 		 	 <fieldset style="-moz-border-radius:8px;border:#D2691E 1px solid;height:188px;">
					<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">碰撞条件</legend>
	    	 		 <div class="right_slider_body">
	    	 		 	
						 <table>
			                <thead>
			                    <tr>
			                        <td width="270px;">监测点(<sapn  style="color:red;">一组条件仅能包含一个</sapn>)</td>
			                        <td width="140px;">开始时间</td>
			                        <td width="140px;">结束时间</td>
									<td>操作</td>
			                    </tr>
			                </thead>
			            </table>
			            <div class="table_data">
				            <table id="cons_table">
				            	<tbody>
				                </tbody>
				            </table>
			            </div>
			            
    	 			 </div>		
					</fieldset>
			<div>
		    	<div class="slider_body clear_both">
	                <p><font color="red" size="2">提示：一个条件只能包含一个监测点!时间间隔不可大于6小时!最多选择5个条件!</font></p>
	       		</div>
		        <div class="slider_body" style="text-align: center;">
			    	<input id="query_button" name="query_button" type="button" class="button_blue" value="分析" onclick="doSubmit();">
			    	<input id="reset_button" name="reset_button" type="button" class="button_blue" value="重置" onclick="doReset();">
			    </div>
		    	<!-- 错误信息提示 -->
		    	<div class="slider_body">
					<span id="errSpan"  style="color:red;z-index:-1;" ></span>
		    	</div>
	    	</div>
       		<div class="pg_result" id="pa_result">
		    	<fieldset id="result_field" style="-moz-border-radius:8px;border:#D2691E 1px solid;display:block;">
					<legend style=" color:#333; font-weight:bold; font-size: large; margin-left: 30px;">碰撞结果</legend>
		        </fieldset>
	        </div>
        </div>
    </div>
    <div id="bar_content" class="bar_content">
		<!-- 进度条 -->
		<div class="barline" id="probar">
			<div id="percent"></div>
			<div id="line" w="100" style="width:0px;"></div>	
			<div id="msg" style=""></div>			
		</div>
	</div>
    <jsp:include page="/common/Foot.jsp" />
</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js" ></script>
	<script type="text/javascript">
		var calTime = 5;//计算一次所需时间分钟
		var barlineIndex;
		var arr = new Array();
		var conArr = new Array();
		$(document).ready(function(){
			$("#add_con").click(function(){
				addCon();
			});
		});
		//提交表单
		function doSubmit(){
			if(arr.length < 2){
				$("#errSpan").text("请添加碰撞条件,分析至少需要两个条件!");
				return;
			}
			if(conArr.indexOf(arr.join(";")) != -1){
				layer.msg("相同碰撞条件已进行过分析，请重新选择条件后再进行分析!");
				return;
			}
			var conStr = getCons();
			$.ajax({
				async:true,
				type: 'POST',
				data:{key:'2'},
				dataType : "json",
				url: '<%=basePath%>/gjfx/getCalCount.do?' + new Date().getTime(),//请求的action路径
				error: function () {//请求失败处理函数
					layer.confirm('获取计算任务数量失败,是否继续计算？',{icon: 7, title:'提示',btn: ['继续','取消']}
					  , function(index){
						layer.close(index);
						showBarLine(1);
						$.ajax({
							async:true,
							type: 'POST',
							data:{dwConGroup:arr.join(";")},
							dataType : "json",
							url: "${pageContext.request.contextPath}/gjfx/dwpz.do?" + new Date().getTime(),//请求的action路径
							error: function () {//请求失败处理函数
								layer.close(barlineIndex);
								layer.msg("分析结果失败！");
							},
							success:function(data){ //请求成功后处理函数。
								layer.close(barlineIndex);
								if(data.hasOwnProperty("errorMessage")){
									layer.msg(data.errorMessage);
								}else{
									addDataDiv(data,conStr);
									conArr[conArr.length] = arr.join(";");
								}
							}
						});
						$("#errSpan").text("");
					}, function(index){
						layer.close(index);
					});
				},
				success:function(data){ //请求成功后处理函数。
					layer.confirm('当前有'+data+'个计算任务,是否继续计算？',{icon: 7, title:'提示',btn: ['继续','取消']}
						  , function(index){
							layer.close(index);
							showBarLine(parseInt(data)+1);
							$.ajax({
								async:true,
								type: 'POST',
								data:{dwConGroup:arr.join(";")},
								dataType : "json",
								url: "${pageContext.request.contextPath}/gjfx/dwpz.do?" + new Date().getTime(),//请求的action路径
								error: function () {//请求失败处理函数
									layer.close(barlineIndex);
									layer.msg("分析结果失败！");
								},
								success:function(data){ //请求成功后处理函数。
									layer.close(barlineIndex);
									if(data.hasOwnProperty("errorMessage")){
										layer.msg(data.errorMessage);
									}else{
										addDataDiv(data,conStr);
										conArr[conArr.length] = arr.join(";");
									}
								}
							});
							$("#errSpan").text("");
						}, function(index){
							layer.close(index);
						});
				}
			});
			
		}
		//将请求返回数据进行添加
		function addDataDiv(data,conStr){
			var list = data.items;
			var queryFlag = data.dwpzQueryFlag;
			var data = "<div class=\"result_box\">"+
		            		"<table>"+
		    					"<thead>"+
		    						"<tr><td ><a onmouseover=\"showConMess($(this));\" onmouseout=\"closeAllTips();\">条件</a></td>"+
		            					"<td ><input type=\"hidden\" value='"+conStr+"'> <input id='queryFlag' type='hidden' value='"+queryFlag+"'/></td>"+
		    							"<td><a onclick=\"delDataDiv($(this));\">删除</a><br><a onclick='doExport($(this))'>导出Excel</a></td>"+
		    						"</tr>"+
		    					
		    						"<tr>"+
		    							"<td width=\"40px\">序号</td>"+
		    							"<td width=\"70px\">车牌号</td>"+
		    							"<td>操作</td>"+
		    						"</tr>"+
		    					"</thead>"+
		    				"</table>"+
		    				"<div class=\"result_box_data\">"+	
		    					"<table>"+
		        					"<tbody>";
			for(i=0;i<list.length;i++){
				data += "<tr>"+
							"<td width=\"40px\">"+(i+1)+"</td>"+
							"<td width=\"70px\">"+list[i].hphm+"</td>"+
							"<td><a href=\"javascript:getSbByTpids('"+list[i].tpids+"');\">过车详情</a></td>"+
						"</tr>";
			}
			data += "</tbody></table></div></div>";
			$("#result_field").append(data);
		}
		//列表展示过车数据
		function getSbByTpids(tpids){
			var location = (window.location+'').split('/');  
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			var url = basePath + "/clcx/getSbByTpids.do?tpids="+tpids;
			layer.open({
		           type: 2,
		           title: '过车详情',
		           shadeClose: true,
		           shade: 0.1,
		           area: ['850px', '650px'],
		           content: url
		       });
		}
		//获取条件
		function getCons(){
			var trList = $("#cons_table").find("tr");
			var res = "";
			for(var i=0;i<trList.length;i++){
				var tr = trList.eq(i);
				var tds = tr.children("td");
				res += "条件"+(i+1)+":{"+tds.eq(0).text()+","+tds.eq(1).text()+","+tds.eq(2).text()+"};";
			}
			return res.substring(0, res.length-1);
		}
		//添加条件
		function addCon(){
			var jcdid = $.trim($("#jcdid").val());
			var kssj = $.trim($("#kssj").val());
			var jssj = $.trim($("#jssj").val());
			var length = $("#cons_table tr").length;
			if(isEmpty(jcdid)){
				$("#errSpan").text("请选择监测点!");
				return;
			}
			if(jcdid.indexOf(",") != -1){
				$("#errSpan").text("只能选择一个监测点!");
				$("#jcdid1").val("==全部==");
				$("#jcdid").val("");
				return;
			}
			if(!commonCheck(false,true)){
				return;
			}
			if(moment(jssj).diff(moment(kssj)) > 1000*60*60*6){
				$("#errSpan").text("错误提示：时间间隔不可大于6小时！");
				return;
			}
			if(length == 5){
				layer.msg("最多选择5个条件！");
				return;
			}
        	var val = jcdid+","+kssj+","+jssj;
			var index = arr.indexOf(val);
			if (index > -1) {
				layer.msg("已包含相同条件，请勿重复添加！");
				return;
			}
			var jcdName = getJcdName(jcdid);
			var row = "<tr id='"+(length+1)+"'>"+
                		"<td  width='270px;' class="+jcdid+">"+jcdName+"</td>"+
                		"<td  width='140px;'>"+kssj+"</td>"+
                		"<td  width='140px;'>"+jssj+"</td>"+
						"<td><a onclick=\"delCon('"+(length+1)+"');\">删除</a></td>"
        			"</tr>";
        	$("#cons_table").append(row);
			arr[length] = val;
			$("#errSpan").text("");
			$("#jcdid1").val("==全部==");
			$("#jcdid").val("");
		}
		//删除条件
		function delCon(val){
			var tr = $("#"+val);
			tr.remove();
			var jcdid = tr.find("td:eq(0)").attr("class");
			var kssj = tr.find("td:eq(1)").text();
			var jssj = tr.find("td:eq(2)").text();
			arr.remove(jcdid+","+kssj+","+jssj);
			//性能太差
			//var jcdid = val.parent().parent().find("td:eq(0)").attr("class");
			//var kssj = val.parent().parent().find("td:eq(1)").text();
			//var jssj = val.parent().parent().find("td:eq(2)").text();
			//val.parent().parent().remove();
			//arr.remove(jcdid+";"+kssj+";"+jssj);
		}
		
		//删除结果
		function delDataDiv(a){
			a.parents("div.result_box").remove();
		}
		//显示查询条件
		function showConMess(aDom){
			var val = aDom.parent().next("td").children("input").val();
			layer.open({
		           type: 4,
		           shade: 0,
		           shadeClose:true,
		           time:30000,
		           closeBtn: 0,
		           tips: [2, '#758a99'],
		           content: [val,aDom]
		       });
		}
		//关闭所有弹出层
		function closeAllTips(){
			layer.closeAll("tips");
		}
		//重置..按钮
		function doReset(){
			$("#errSpan").text("");
			$("#jcdid").val("");
			$("#jcdid1").val("==全部==");
			$("#kssj").val("");
			$("#jssj").val("");
			conArr = new Array();
		}
		//导出excel
		function doExport(aDom){
			//获取查询条件
			var val_queryCondition = aDom.parent().prev("td").children("input").val();
			var val_queryFlag = aDom.parent().prev("td").children(":eq(1)").val();
			$("#form_input_queryCondition").val(val_queryCondition);
			$("#form_input_queryFlag").val(val_queryFlag);
			alert(val_queryCondition);
			var url = "${pageContext.request.contextPath}/gjfx/excelExportForDwpz.do";
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	</script>
</html>