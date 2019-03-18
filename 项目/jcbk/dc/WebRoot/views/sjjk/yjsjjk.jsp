<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@page import="java.util.Map,java.util.*,com.dyst.base.utils.*,com.dyst.utils.*,com.dyst.systemmanage.entities.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	User user = (User)request.getSession().getAttribute(StaticUtils.SESSION_NAME_USER_DATA);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
	<head>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
		<base href="<%=basePath%>">
		<title>实时预警监控</title>
	</head>

	<body>
		<jsp:include page="/common/Head.jsp" />
		<link rel="stylesheet" href="<%=basePath%>common/css/sb/clcx.css" type="text/css">
		<div id="divTitle">
			<span id="spanTitle">当前位置：数据监控管理&gt;&gt;实时预警监控</span>
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
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;布控类型：</span>
						</div>
						<div id="bklx_allselect" class="slider_selected_right dropdown dropdown_all">
							<div class="input_select xiala">
								<div id="bklx_downlist" class='multi_select'>
									<input type="hidden" id="bklx_select" value=""/>
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
										<td width="50" align="center">序号</td>
										<td width="80" align="center">车牌号码</td>
										<td width="80" align="center">车牌颜色</td>
										<td width="100" align="center">报警大类</td>
										<td width="100" align="center">报警小类</td>
										<td width="150" align="center">监测点</td>
										<td width="120" align="center">通过时间</td>
										<td width="120" align="center">报警时间</td>
										<td width="100" align="center">车辆速度</td>
										<td width="100" align="center">操作</td>
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
		<jsp:include page="/common/Foot.jsp" />
	</body>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/simplefoucs.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/sb/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/engine.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/util.js"></script>
	<script type="text/javascript" src="<%=basePath%>dwr/interface/pushMessageCompont.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/layer/layer.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/jquery.jplayer.min.js"></script>
	<script type="text/javascript">
		var hpysList = new Array();
		var hpzlList = new Array();
		var bkdlList = new Array();
		var bkdl1List = new Array();
		var bkdl2List = new Array();
		var bkdl3List = new Array();
		
		$(function(){
			var bklbList = jQuery.parseJSON('${bklbList}');
			var dicList = jQuery.parseJSON('${dicList}');
			for(var i in dicList){
				if(dicList[i].typeCode == "0002"){
					hpysList.push(dicList[i]);
				} else if(dicList[i].typeCode == "HPZL"){
					hpzlList.push(dicList[i]);
				} else if (dicList[i].typeCode == "BKDL"){
					bkdlList.push(dicList[i]);
				} else if (dicList[i].typeCode == "BKDL1"){
					bkdl1List.push(dicList[i]);
				} else if (dicList[i].typeCode == "BKDL2"){
					bkdl2List.push(dicList[i]);
				} else if (dicList[i].typeCode == "BKDL3"){
					bkdl3List.push(dicList[i]);
				}
			}
		
			//车牌颜色
			var value = []; 
			var data = [];
			for(var i=hpysList.length-1;i >= 0;i--){
				if(hpysList[i].typeCode == '0002'){
					value.push(hpysList[i].typeSerialNo);
					data.push(hpysList[i].typeDesc);
				}
			}
			$('#hpys_downlist').MSDL({
				'value': value,
			    'data': data
			});
			$("#hpys_select").val("");
			
			//布控类型
			var value = []; 
			var data = [];
			for(var i=bkdlList.length-1;i >= 0;i--){
				if(bkdlList[i].typeSerialNo == '1'){
					for(var j=bkdl1List.length-1;j >= 0;j--){
						value.push(bkdl1List[j].typeSerialNo);
						data.push(bkdlList[i].typeDesc + '-' + bkdl1List[j].typeDesc);
					}
				} else if(bkdlList[i].typeSerialNo == '2'){
					for(var j=bkdl2List.length-1;j >= 0;j--){
						value.push(bkdl2List[j].typeSerialNo);
						data.push(bkdlList[i].typeDesc + '-' + bkdl2List[j].typeDesc);
					}
				} else if(bkdlList[i].typeSerialNo == '3'){
					for(var j=bkdl3List.length-1;j >= 0;j--){
						value.push(bkdl3List[j].typeSerialNo);
						data.push(bkdlList[i].typeDesc + '-' + bkdl3List[j].typeDesc);
					}
				}
			}
			$('#bklx_downlist').MSDL({
				'value': value,
			    'data': data
			});
			$("#bklx_select").val("");
			
			//车牌号提示tip
		    $("#hphm_span").mouseover(function(){
				showTip();
			});
			$("#hphm_span").mouseleave(function(){
				layer.closeAll('tips');
			});
			
			dwr.engine.setActiveReverseAjax(true);
			dwr.engine.setNotifyServerOnPageUnload(true);
			onPageLoad();
		});
		
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
			pushMessageCompont.onPageLoad("yjsjjk@" + userId);
		}
		
		var len = 0;
		function showMessage(sendMessages, clickEvent) {
			//获取数据并解析
			if(engineFlag == '1'){
				var $tbody = $("#table_result tbody");
				var $tr = $("#table_result tbody tr").eq(0);
				var data = sendMessages.split(";");
				//bkid;bjxh;hphm;hpzl;cplx;jcdid;jcdmc;cdid;tpid;sd;tgsj;scsj;bjsj;bjdl;bjlx
				//根据条件过滤数据
				//数据长度
				if(data.length < 15){
					return;
				}
				//车牌号码
				var cphid = "," + $.trim($("#cphid").val()) + ",";
				if($.trim(cphid) != ",," && $.trim(cphid).indexOf("," + $.trim(data[2]) + ",") < 0 
					&& $.trim(data[2]).indexOf($.trim($("#cphid").val())) < 0){//如果不是所选的车牌颜色，则返回
					return;
				}
				//号牌颜色
				var hpys = ";" + $.trim($("#hpys_select").val()) + ";";
				if($.trim(hpys) != ";;" && $.trim(hpys).indexOf(";" + $.trim(data[4]) + ";") < 0){//如果不是所选的车牌颜色，则返回
					return;
				}
				//过滤监测点
				var jcdStr = "," + $.trim($("#jcdid").val()) + ",";
				if($.trim(jcdStr) != ",," && $.trim(jcdStr).indexOf("," + $.trim(data[5]) + ",") < 0){//如果不是所选的监测点，则返回
					return;
				}
				//过滤布控类型
				var bklx = ";" + $.trim($("#bklx_select").val()) + ";";
				if($.trim(bklx) != ";;" && $.trim(bklx).indexOf(";" + $.trim(data[14]) + ";") < 0){//如果不是所选的布控类型，则返回
					return;
				}
				//图片ID解析
				var tpidArr = $.trim(data[8]).split(",");
				if(tpidArr == null || tpidArr.length < 0){
					return;
				}
				
				//车牌颜色翻译
				var cpysStr = transforJson(hpysList, '0002', data[4]);
				var bjdlStr = transforJson(bkdlList, 'BKDL', data[13]);
				var bjlxStr = "";
				if(data[13] == "1"){
					for(var i=0;i < bkdl1List.length;i++){
		            	if(data[14] == bkdl1List[i].typeSerialNo){
		        			bjlxStr = bkdl1List[i].typeDesc;
		                }
		            }
				} else if(data[13] == "2"){
					for(var i=0;i < bkdl2List.length;i++){
		            	if(data[14] == bkdl2List[i].typeSerialNo){
		            		bjlxStr = bkdl2List[i].typeDesc;
		                }
		            }
				} else if(data[13] == "3"){
					for(var i=0;i < bkdl3List.length;i++){
		            	if(data[14] == bkdl3List[i].typeSerialNo){
		                 	bjlxStr = bkdl3List[i].typeDesc;
		                }
		            }
				}
				
				//加入列表中
				if(len == 0){
				    len++;
				    //bkid;bjxh;hphm;hpzl;cplx;jcdid;jcdmc;cdid;tpid;sd;tgsj;scsj;bjsj;bjdl;bjlx
					$tbody.append('<tr style="text-align: center;" id="'+len+'">'
									+ '<td>' + len + '</td>'
									+ '<td>' + data[2] + '</td>'
									+ '<td>' + cpysStr + '</td>'
									+ '<td>' + bjdlStr + '</td>'
									+ '<td>' + bjlxStr + '</td>'
									+ '<td>' + data[6] + '</td>'
									+ '<td>' + data[10] + '</td>'
									+ '<td>' + data[12] + '</td>'
									+ '<td>' + data[9] + '</td>'
									+ "<td><a href=\"javascript:eWarningDetail('" + data[1] + "')\">图片</a></td>");
				} else {
				    len++;
					$tr.before('<tr style="text-align: center;" id="'+len+'">'
									+ '<td>' + len + '</td>'
									+ '<td>' + data[2] + '</td>'
									+ '<td>' + cpysStr + '</td>'
									+ '<td>' + bjdlStr + '</td>'
									+ '<td>' + bjlxStr + '</td>'
									+ '<td>' + data[6] + '</td>'
									+ '<td>' + data[10] + '</td>'
									+ '<td>' + data[12] + '</td>'
									+ '<td>' + data[9] + '</td>'
									+ "<td><a href=\"javascript:eWarningDetail('" + data[1] + "')\">图片</a></td>");
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
		
		//清空数据
		function doClear(){
		    len = 0;
			$("#table_result tbody tr").remove();
		}
		
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
		
		function eWarningDetail(bjxh){
			var url = "${pageContext.request.contextPath}/earlyWarning/loadEWarningDetail.do?bjxh="+bjxh;
		    layer.open({
		           type: 2,
		           title: '预警详情窗口',
		           shadeClose: true,
		           shade: 0.8,
		           area: ['860px', '500px'],
		           content: url //iframe的url
		       }); 	
		}
		 
		//重写错误方法
		dwr.engine._errorHandler = function(message, ex) {
			alert("访问不到服务，页面将关闭！");
			window.close();
		};
	</script>
</html>