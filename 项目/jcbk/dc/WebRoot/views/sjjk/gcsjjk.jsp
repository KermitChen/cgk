<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.Map,java.util.*,com.dyst.base.utils.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>过车实时监控</title>
	</head>
	<body>
		<jsp:include page="/common/Head.jsp" />
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<div id="divTitle">
			<span id="spanTitle">当前位置：数据监控管理&gt;&gt;过车实时监控</span>
		</div>
		<div class="content">
			<div class="content_wrap">
				<fieldset style="margin: 0px 0px 0px 0px;">
					<legend style="color:#FF3333">过滤条件</legend>
				<div class="slider_body">
	                <div class="slider_selected_left">
	                    <span id="hphm_span" class="layer_span" style="color:#900b09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌号码：</span>
	                </div>
	                <div class="slider_selected_right">
	                    <div class="img_wrap">
	                        <div class="select_wrap input_wrap_select">
	                            <input id="cphid" name="hphm" type="text" class="slider_input" value="${hphm }">
	                            <a class="empty" href="javascript:doCplrUI()"></a>
	                        </div>
	                    </div>  
	                </div>
	        	</div>
				<div class="slider_body">
					<div class="slider_selected_left">
						<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌颜色：</span>
					</div>
					<div id="hpys_allselect" class="slider_selected_right dropdown dropdown_all">
						<div class="input_select xiala">
							<div id="hpys_downlist" class='multi_select'>
								<input type="hidden" id="hpys_select" value=""/>
								<a class="xiala_duoxuan_a"></a>
							</div>
						</div>
					</div>
				</div>
				<div class="slider_body">
		            <div class="slider_selected_left">
		                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测点：</span>
		            </div>
		            <div class="slider_selected_right dropdown" id="dropdown_quanxuan">
		                <input class="input_select xiala" onclick="doChoseJcd();" id="jcdid1" name="jcdid1" type="text" value="==全部=="/> 
		                <input type="hidden" name="jcdid" id="jcdid" value="">
		            </div>
		        </div>
				<div class="slider_body">
					<div class="slider_selected_left">
						<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行驶车道：</span>
					</div>
					<div id="xscd_allselect" class="slider_selected_right dropdown dropdown_all">
						<div class="input_select xiala">
							<div id="xscd_downlist" class='multi_select'>
								<input type="hidden" id="xscd_select" value=""/>
								<a class="xiala_duoxuan_a"></a>
							</div>
						</div>
					</div>
				</div>
				<div class="slider_body">
			        <div class="slider_selected_left">
			            <span>最大显示记录数：</span>
			        </div>
					<div class="slider_selected_right dropdown dropdowns" onclick="slider(this)">
						<input class="input_select xiala" id="maxnum_downlist" readonly="readonly" type="text" value="10"/>
						<input type="hidden" id="maxnum_select" name="property" value="10"/>
						<div class="ul">
							<div class="li" data-value="10" onclick="sliders(this)">10</div>
						    <%
		                		for(int i=20;i <= 200;){
		                	%>
			                    	<div class="li" data-value="<%=i %>" onclick="sliders(this)"><%=i %></div>
			                <%
			                		i = i + 20;
			                    }
			                %>
						</div>
					</div>
		        </div>
				<div class="button_wrap clear_both" style="height: 50px;">
					<input id="query_button" name="query_button" type="button" class="button_blue" value="启动监控" onclick="doSearch();">
					<input id="clear_button" name="clear_button" type="button" class="button_blue" value="清空数据" onclick="doClear();">
				</div>
				</fieldset>
				<fieldset style="margin: 20px 0px 0px 0px;">
			      	<legend style=" color:#FF3333">监控结果</legend>
					<div class="pg_result">
						<div class="show_style1 show_data_div">
							<table id="table_result">
								<thead>
									<tr>
										<td>序号</td>
										<td>车牌号码</td>
									    <td>车牌颜色</td>
										<td>识别时间</td>
										<td>上传时间</td>
										<td>监测点</td>
										<td>行驶车道</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody>
		
								</tbody>
							</table>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<jsp:include page="/common/Foot.jsp"/>
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/simplefoucs.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/engine.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/interface/pushMessageCompont.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript">
	    //数据字典
	    var dicJson;
		$(function(){
			dicJson = $.parseJSON('${dicJson}');
			
			//车牌颜色
			var value = []; 
			var data = [];
			for(var i=dicJson.length-1;i >= 0;i--){
				if(dicJson[i].typeCode == '0002'){
					value.push(dicJson[i].typeSerialNo);
					data.push(dicJson[i].typeDesc);
				}
			}
			$('#hpys_downlist').MSDL({
				'value': value,
			    'data': data
			});
			$("#hpys_select").val("");
			//行驶车道
			var value = []; 
			var data = [];
			for(var i=dicJson.length-1;i >= 0;i--){
				if(dicJson[i].typeCode == 'CD'){
					value.push(dicJson[i].typeSerialNo);
					data.push(dicJson[i].typeDesc);
				}
			}
			$('#xscd_downlist').MSDL({
				'value': value,
			    'data': data
			});
			$("#xscd_select").val("");
			
			dwr.engine.setActiveReverseAjax(true);
			dwr.engine.setNotifyServerOnPageUnload(true);
			onPageLoad();
			
			//车牌号提示tip
		    $("#hphm_span").mouseover(function(){
				showTip();
			});
			$("#hphm_span").mouseleave(function(){
				layer.closeAll('tips');
			});
		});
		
		var engineFlag = '0';
		//监控控制启动与停止
		function doSearch() {
			if(engineFlag == '0'){
				engineFlag = '1';
				$("#query_button").val("停止监控");
			} else {
				engineFlag = '0';
				$("#query_button").val("启动监控");
			}
		}
		
		var userId = "<%=(user != null?user.getLoginName():"") %>";
		function onPageLoad() {
			pushMessageCompont.onPageLoad("gcsjjk@" + userId);
		}
		
		var len = 0;
		function showMessage(sendMessages, clickEvent) {
			//获取数据并解析
			if(engineFlag == '1'){
				var $tbody = $("#table_result tbody");
				var $tr = $("#table_result tbody tr").eq(0);
				var data = sendMessages.split("#");
				
				//根据条件过滤数据
				//数据长度
				if(data.length <= 16){
					return;
				}
				//车牌号码
				var cphid = "," + $.trim($("#cphid").val()) + ",";
				if($.trim(cphid) != ",," && $.trim(cphid).indexOf("," + $.trim(data[4]) + ",") < 0 
					&& $.trim(data[4]).indexOf($.trim($("#cphid").val())) < 0){//如果不是所选的车牌颜色，则返回
					return;
				}
				//号牌颜色
				var hpys = ";" + $.trim($("#hpys_select").val()) + ";";
				if($.trim(hpys) != ";;" && $.trim(hpys).indexOf(";" + $.trim(data[6]) + ";") < 0){//如果不是所选的车牌颜色，则返回
					return;
				}
				//过滤监测点
				var jcdStr = "," + $.trim($("#jcdid").val()) + ",";
				if($.trim(jcdStr) != ",," && $.trim(jcdStr).indexOf("," + $.trim(data[9]) + ",") < 0){//如果不是所选的监测点，则返回
					return;
				}
				//过滤车道
				var xscd = ";" + $.trim($("#xscd_select").val()) + ";";
				if($.trim(xscd) != ";;" && $.trim(xscd).indexOf(";" + $.trim(data[10]) + ";") < 0){//如果不是所选的车道，则返回
					return;
				}
				//图片ID解析
				var tpidArr = $.trim(data[12]).split(",");
				if(tpidArr == null || tpidArr.length < 0){
					return;
				}
				
				//车牌颜色翻译
				var cpysStr = transforJson(dicJson, '0002', data[6]);
				var msg = data[4] + ";" + data[6] + ";" + cpysStr + ";" + data[8] + ";" + data[3] + ";" + data[10] + ";" + data[9] + ";" + data[16] + ";" + data[12];
				
				//加入列表中
				if(len == 0){
				    len++;
					$tbody.append('<tr style="text-align: center;" id="'+len+'">'
									+ '<td>' + len + '</td>'
									+ '<td>' + data[4] + '</td>'
									+ '<td>' + cpysStr + '</td>'
									+ '<td>' + data[8] + '</td>'
									+ '<td>' + data[3] + '</td>'
									+ '<td>' + data[16] + '</td>'
									+ '<td>' + data[10] + '</td>'
									+ "<td><a href=\"javascript:showImage('" + msg + "')\">图片</a></td>");
				} else {
				    len++;
					$tr.before('<tr style="text-align: center;" id="'+len+'">'
									+ '<td>' + len + '</td>'
									+ '<td>' + data[4] + '</td>'
									+ '<td>' + cpysStr + '</td>'
									+ '<td>' + data[8] + '</td>'
									+ '<td>' + data[3] + '</td>'
									+ '<td>' + data[16] + '</td>'
									+ '<td>' + data[10] + '</td>'
									+ "<td><a href=\"javascript:showImage('" + msg + "')\">图片</a></td>");
				}
				
				var num = parseInt($("#maxnum_select").val());
				var tableRow = $("#table_result tbody tr");
				var tableRowNum = parseInt($("#table_result tbody tr").length);
				if(tableRowNum > num){
					//tableRow.eq(tableRowNum - 1).remove();
					$("#table_result tbody tr:gt(" + (num-1) + ")").remove();
				}
			}
		}
		
		//显示车牌号码输入提示
		function showTip(){
			layer.open({
		           type: 4,
		           shade: 0,
		           time:8000,
		           closeBtn: 0,
		           tips: [3, '#758a99'],
		           content: ['多个精确的车牌号码以半角逗号分隔.','#hphm_span']
		       });
		}
		
		//清空数据
		function doClear(){
		    len = 0;
			$("#table_result tbody tr").remove();
		}
		
		//重写错误方法
		dwr.engine._errorHandler = function(message, ex) {
			alert("访问不到服务，页面将关闭！");
			window.close();
		};
		
		//把各种json进行...按代码->转出相应的描述    的function
		function transforJson(json, typeCodeStr, serialNoStr){
			for(var j in json){
				var typeCode = json[j].typeCode;
				var serialNo = json[j].typeSerialNo;
				if(typeCode == typeCodeStr && serialNo == serialNoStr){
					return json[j].typeDesc;
				}
			}
			return serialNoStr;
		}
		
		function showImage(gcInfo){
			var url = "${pageContext.request.contextPath}/sjjk/loadGcPic.do?gcInfo=" + gcInfo;
		    layer.open({
		           type: 2,
		           title: '过车详情',
		           shadeClose: true,
		           shade: 0.8,
		           area: ['860px', '500px'],
		           content: url //iframe的url
		       }); 	
		}
	</script>
</html>